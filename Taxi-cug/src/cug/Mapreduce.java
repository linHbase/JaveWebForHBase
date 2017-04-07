package cug;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.mapreduce.TableOutputFormat;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import util.MineGeohash;
import util.Status;
import util.UtcTime;

public class Mapreduce {
	//16324|A|0|1|1|0|0|0|2016-03-01 00:00:01|2016-02-29 23:59:45|121.508828|31.201127|0.0|77.0|9|000
		public static class  mineMap extends Mapper<Object, Text, Text, Text>{
			public Text keyInfo = new Text();
			public Text valueInfo = new Text();
			public void map(Object key, Text value, Context context) throws IOException, InterruptedException{
				String[] oneline = value.toString().split("\\|");
				MineGeohash geohash = new MineGeohash();
				UtcTime utc = new UtcTime();
				utc.calAll(oneline[9]);
				geohash.calEncoding(oneline[10], oneline[11]);
				
				keyInfo.set(oneline[0]);
				valueInfo.set(geohash.preEncoding+"|"+oneline[10]+"| "+oneline[11]+"|"+oneline[12]+"|"+oneline[13]+ "|" +oneline[9]+"|"+String.valueOf(utc.second)+"|"+oneline[6]+"|"+oneline[3]);
				context.write(keyInfo, valueInfo);
			}
		}
		public static class mineReduce extends Reducer<Text, Text, NullWritable, Put>{
			//	ABDACDFAFA 121.508828 31.201127 0.0 77.0 1456790400
			//	geo-encoding longitude altitude speed direction initialTypetime time shache passanger
			//	public Status s = new Status();
			public Status s = new Status();	
			List<String> value = new ArrayList<String>();
			public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException{
				for(Text text : values){
					value.add(text.toString());
				}
				
				for(int i = 0;i < value.size();i++){
					String[] nowValue = value.get(i).split("\\|");
					
					Put put1 = new Put((nowValue[0]+nowValue[6]+key.toString()).getBytes());
					put1.addColumn("event".getBytes(), "all".getBytes(), (nowValue[1]+" "+nowValue[2]+" "+nowValue[3]+ " " +nowValue[4]).getBytes());
					context.write(NullWritable.get(), put1);
					
					boolean ifNot = true;				
					for(int j = 0;j<value.size();j++){
						if(!ifNot){
							break;
						}
						if(ifNot){
							String[] preValue = value.get(j).toString().split("\\|");
							Double number = Math.abs(Double.valueOf(preValue[6])-Double.valueOf(nowValue[6]));
							if(number<=2&&number!=0){
								s.calStatus(nowValue, preValue);
							}else if(number<=3){
								s.calStatus(nowValue, preValue);
								ifNot = false;
							}else if(number<=5){
								s.calStatus(nowValue, preValue);
								ifNot = false;
							}else if(number<=10){
								s.calStatus(nowValue, preValue);
								ifNot = false;
							}
						}
					}				
					if(!ifNot){
						if(s.status==""){
							s.status = "ABCDE";						
						}
						Put put2 = new Put((nowValue[6]+s.status).getBytes());
						put2.addColumn("status".getBytes(), key.copyBytes(), (nowValue[0]+nowValue[6]+key.toString()).getBytes());					
						context.write(NullWritable.get(), put2);
					}
					ifNot = true;
					s.setStatus();
				}
				value.clear();
			}
		}
		public void Import(String tableName, String inputPath, String outputPath) throws Exception{
			
			createTable(HbaseManager.admin,tableName, new String[]{"event","status"});
			
			Method addURL =  URLClassLoader.class.getDeclaredMethod("addURL",  
	                new Class[] { URL.class });  
	        addURL.setAccessible(true);  
		    URLClassLoader classloader =  (URLClassLoader)ClassLoader.getSystemClassLoader();  
	        String url = "file:///home/lin/Desktop/biye/biye.jar"; // 包路径定义 
	   //      System.out.println(url);  
	        URL classUrl = new URL(url);  
	        addURL.invoke(classloader, new Object[] { classUrl });
/*	         
	        String ur = "file:///home/lin/Desktop/biye/biye/WebContent/WEB-INF/lib/hadoop-mapreduce-client-core-2.6.0.jar";
	        URL classUrlMap = new URL(ur);  
	        addURL.invoke(classloader, new Object[] { classUrlMap });*/
	        
	     /*   File path = new File("/home/lin/Desktop/biye/biye/WebContent/WEB-INF/lib/");
	        File[] fileList = path.listFiles();
	        for(int i=0;i<fileList.length;i++){
	        	String urfl = "file://"+fileList[i];
	        	URL classUrlMp = new URL(urfl);  
		        addURL.invoke(classloader, new Object[] { classUrlMp });
	        }*/
			Job job = Job.getInstance(HbaseManager.admin.getConfiguration(), "mapreduce");
			job.setJarByClass(MapReduceInput.class);
			
			job.setMapperClass(mineMap.class);
			job.setReducerClass(mineReduce.class);
			job.setMapOutputKeyClass(Text.class);
			job.setMapOutputValueClass(Text.class);
			
			job.setOutputKeyClass(NullWritable.class);
			job.setOutputValueClass(Put.class);
			
			job.setOutputFormatClass(TableOutputFormat.class);

			if(HbaseManager.fs.exists(new Path(outputPath))){
				HbaseManager.fs.delete(new Path(outputPath), true);
			}
			FileInputFormat.addInputPath(job, new Path(inputPath));
			FileOutputFormat.setOutputPath(job, new Path(outputPath));
			job.waitForCompletion(true);
		}
		public void createTable(Admin admin,String tablename,String[] family)
		{
			if(tablename==null||tablename.isEmpty())
			 {
				 return;
			 }
			 if(family.length==0)
			 {
				 return;
			 }		 
			 try
			 {
				TableName name = TableName.valueOf(tablename);
				if(admin.tableExists(name))
				{
					admin.disableTable(name);
					admin.deleteTable(name);
					System.out.print("succeful to delete table"+"\n");
				}
				HTableDescriptor tableDesc = new HTableDescriptor(name); 
				 
				for(int i=0;i<family.length;i++)
				{
					tableDesc.addFamily(new HColumnDescriptor(family[i]));
				}
				admin.createTable(tableDesc);
				System.out.print("succeful to make table "+tablename+"\n");
			} 
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
}
