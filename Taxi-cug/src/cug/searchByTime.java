package cug;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;

import util.UtcTime;

public class searchByTime extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5980852915272977645L;
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		JsonArrayBuilder array= Json.createArrayBuilder();
//		List<String> arg1 = new ArrayList<String>();
		queryByTime("test", startTime, endTime,array);
		JsonArray jsonarray = array.build();
		request.setAttribute("result_output", jsonarray);
		request.getRequestDispatcher("/manager/result.jsp").forward(request, response);
	}
	
	public void queryByTime(String tableName, String startTime,String endTime, JsonArrayBuilder array) throws IllegalArgumentException, IOException{
		UtcTime utc = new UtcTime();
		utc.calAll(startTime);
		int starttime = utc.second;
		utc.setSecond();
		utc.calAll(endTime);
		int endtime = utc.second;
		queryByTime(tableName, starttime, endtime,array);
	}
	//126548902 
	public void queryByTime(String tableName, int startTime,int endTime, JsonArrayBuilder array) throws IllegalArgumentException, IOException{
		
		Table t = HbaseManager.admin.getConnection().getTable(TableName.valueOf(tableName.getBytes()));
		Scan scan = new Scan();		
		scan.addFamily("status".getBytes());
		scan.setStartRow((String.valueOf(startTime)+"ABCDE").getBytes());
		scan.setStopRow((String.valueOf(endTime)+"abcde").getBytes());
		
		ResultScanner rt = t.getScanner(scan);
		Date time = new Date();
		long t1 = time.getTime();
		List<String> rowkey = new ArrayList<String>();
		for(Result r : rt)
		{
			for (Cell cell : r.rawCells()) {
				if(CellUtil.cloneValue(cell).toString()!=null){
					rowkey.add(new String(CellUtil.cloneValue(cell)));						       
				}
            }
		}
		for(int i = 0;i<rowkey.size();i++){
			Get get = new Get(rowkey.get(i).getBytes());
			get.addColumn("event".getBytes(), "all".getBytes());
			Result result = t.get(get);
			if(result.containsColumn("event".getBytes(), "all".getBytes())){
				String middleValue = new String(result.getValue("event".getBytes(), "all".getBytes()));
				String[] value = middleValue.split(" ");
				System.out.println(new String(result.getValue("event".getBytes(), "all".getBytes())));
				System.out.println(value.length);
				System.out.println(value[0]);
				System.out.println(value[1]);
				System.out.println(value[2]);
				System.out.println(value[3]);
				System.out.println(value[4]);
				array.add(Json.createObjectBuilder().add("longitude", value[0]).add("altitude", value[2]).build());
		//	Out.add(new String(result.getValue("event".getBytes(), "all".getBytes())));
	//		System.out.print(new String(result.getValue("event".getBytes(), "all".getBytes())));
			}
		}
		time = new Date();
		long t2 = time.getTime();		
		System.out.println((t2-t1));
		System.out.println(rowkey.size());
		rt.close();
	}
}
