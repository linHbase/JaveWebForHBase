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
import org.apache.hadoop.hbase.filter.CompareFilter;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.RegexStringComparator;
import org.apache.hadoop.hbase.filter.RowFilter;

import util.UtcTime;

public class searchByMantics extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String manticSeconding = request.getParameter("semantic");
	//	List<String> arg1 = new ArrayList<String>();
		JsonArrayBuilder array= Json.createArrayBuilder();
		queryBysemantic("test", manticSeconding, startTime,endTime, array);
		JsonArray jsonarray = array.build();
		request.setAttribute("result_output", jsonarray);
		request.getRequestDispatcher("/manager/result.jsp").forward(request, response);
	}
	
	public String semanticChange(String sematic){
		String sematicEncoding = new String();
		if(sematic =="roadCongestion"){
			sematicEncoding = ".*AB[cC][dD]E.*";
		}
		if(sematic =="accidents"){
			sematicEncoding = ".*aB[cC][dD]E.*";
		}
		if(sematic =="taxiDistribution"){
			sematicEncoding = ".*D.*";
		}
		return sematicEncoding;
	}
	public void queryBysemantic(String tableName,String Semantic, String startTime, String endTime, JsonArrayBuilder array) throws IllegalArgumentException, IOException{
		UtcTime utc = new UtcTime();		
		utc.calAll(startTime);
		int starttime = utc.second;
		utc.setSecond();
		utc.calAll(endTime);
		int endtime = utc.second;
		Table t = HbaseManager.admin.getConnection().getTable(TableName.valueOf(tableName.getBytes()));
		Scan scan = new Scan();		
		scan.addFamily("status".getBytes());
		scan.setStartRow((String.valueOf(starttime)).getBytes());
		scan.setStopRow((String.valueOf(endtime)).getBytes());
		
		String semanticEncoding = semanticChange(Semantic);
		
		Filter filter = new RowFilter(CompareFilter.CompareOp.EQUAL, new RegexStringComparator(semanticEncoding));
		scan.setFilter(filter);
		ResultScanner rt = t.getScanner(scan);
		Date time = new Date();
		long t1 = time.getTime();
		List<String> rowkey = new ArrayList<String>();
		for(Result r : rt)
		{
			for (Cell cell : r.rawCells()) {
				if(CellUtil.cloneValue(cell).toString()!=null){
					rowkey.add(new String(CellUtil.cloneValue(cell)));
	                System.out.println("列:" + new String(CellUtil.cloneValue(cell)) + "  ====值:" + new String(CellUtil.cloneRow((cell))));
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
//				System.out.println(value.length);
				System.out.println(value[0]);
				System.out.println(value[1]);
				System.out.println(value[2]);
				System.out.println(value[3]);
				System.out.println(value[4]);
				array.add(Json.createObjectBuilder().add("longitude", value[0]).add("altitude", value[2]).build());
			}
		}
		time = new Date();
		long t2 = time.getTime();		
		System.out.println("time cost:"+(t2-t1));
		System.out.println("rowjey size" + "   "+rowkey.size());	
	}
}
