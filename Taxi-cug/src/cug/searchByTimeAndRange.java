package cug;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;

import util.MineGeohash;
import util.UtcTime;

public class searchByTimeAndRange extends HttpServlet{	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String queryRange =  request.getParameter("queryRange");
		String[] range = queryRange.split(",");
		if(range.length!=4){
			response.sendRedirect("/manager/error.jsp?error=yes");
		}
		List<String> arg1 = new ArrayList<String>();
		queryByTimeAndRange("test", startTime, endTime,range[0], range[1], range[2], range[3], arg1);
		
		request.setAttribute("result_output", arg1);
		request.getRequestDispatcher("/manager/result.jsp").forward(request, response);
	}
	public void queryByTimeAndRange(String tableName,String startLongitude, String endLongtitude, String startAltitude, String endAltitude, String startTime,String endTime,List<String> args) throws IllegalArgumentException, IOException{

		MineGeohash geohash = new MineGeohash();
		geohash.calEncoding(startLongitude, startAltitude);
		String startEncoding = geohash.preEncoding;
		geohash.setEmpty();
		geohash.calEncoding(endLongtitude, endAltitude);
		String endEncoding = geohash.preEncoding;
		
		UtcTime utc = new UtcTime();
		utc.calAll(startTime);
		int timeStartEncoding = utc.second;
		utc.setSecond();
		utc.calAll(endTime);
		int timeEndEncoding = utc.second;
		
		Table t = HbaseManager.admin.getConnection().getTable(TableName.valueOf(tableName.getBytes()));
		Scan scan = new Scan();		
		scan.addColumn("event".getBytes(),"all".getBytes());
		scan.setStartRow((startEncoding+String.valueOf(timeStartEncoding)).getBytes());
		scan.setStopRow((endEncoding+String.valueOf(timeEndEncoding)).getBytes());
		
		ResultScanner rt = t.getScanner(scan);
		Date time = new Date();
		long t1 = time.getTime();
		int number = 0 ;
		for(Result r : rt)
		{
			Cell cell = r.getColumnLatestCell("event".getBytes(), "all".getBytes());
			if(CellUtil.cloneValue(cell).toString()!=null){
				number++;
				args.add(new String(CellUtil.cloneFamily(cell))+new String(CellUtil.cloneValue(cell)));
//                System.out.println("列:" + new String(CellUtil.cloneValue(cell)) + "  ====值:" + new String(CellUtil.cloneRow((cell))));
			}
		}
		time = new Date();
		long t2 = time.getTime();
		System.out.println(t2-t1);
		System.out.println(number);
	}
}
