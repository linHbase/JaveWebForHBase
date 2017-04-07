package cug;

import java.io.IOException;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.Path;

public class HBaseLogin extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int ID;
	public static String rootdir;
	public static String zookeeper;
	public static void init(String rootdir, String zookeeper) throws IOException{
		HbaseManager hb = new HbaseManager();
		hb.createAdmin(rootdir, zookeeper);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{		
		ID = 0;
		if(rootdir == null&&zookeeper ==null){
			rootdir = request.getParameter("rootdir");
			zookeeper = request.getParameter("zookeeper");
			init(rootdir,zookeeper);
		}
		if(HbaseManager.admin == null){
			request.setAttribute("message", "you should set connect message!");
			request.getRequestDispatcher("/message.jsp").forward(request, response);
			return;			
		}
		request.setAttribute("admin", HbaseManager.admin);
		request.getRequestDispatcher("/manager/head.jsp").forward(request, response);
		/*JsonArrayBuilder array= Json.createArrayBuilder();
		getFile(new Path("hdfs://192.168.1.121:9000/hbase"),0,array);
		JsonArray jsonarray = array.build();
		for(int i=0;i<jsonarray.size();i++){
			if(jsonarray.getJsonObject(i).containsKey("id")){
				System.out.println(jsonarray.getJsonObject(i).getInt("id")+" "+jsonarray.getJsonObject(i).getInt("pid")+" "+jsonarray.getJsonObject(i).getString("name"));
			}
		}
		request.setAttribute("path_name", jsonarray);
		request.getRequestDispatcher("manager/tree.jsp").forward(request, response);*/

	}
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		doGet(request, response);
	}
	public void getFile(Path path, int pId, JsonArrayBuilder array) throws IllegalArgumentException, IOException{
		FileStatus[] ft = HbaseManager.fs.listStatus(path);
		int beforeLayer = this.ID;
		for(int i = 0;i<ft.length;i++){			
			String newpath = ft[i].getPath().getName();
			Path th = ft[i].getPath().getParent();
				if(ft[i].isDirectory()){
					this.ID++;
				//	System.out.println(this.ID+"  "+beforeLayer+"  "+newpath);
					array.add(Json.createObjectBuilder().add("id", this.ID).add("pid", beforeLayer).add("name", newpath).build());
					 getFile(new Path(th.toString()+"/"+newpath), beforeLayer, array);
				}else{
					this.ID++;
					array.add(Json.createObjectBuilder().add("id", this.ID).add("pid", beforeLayer).add("name", newpath).build());
			//		System.out.println(this.ID+"  "+beforeLayer+"  "+newpath);
				}				
		}
	}
}
