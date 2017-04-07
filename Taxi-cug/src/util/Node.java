package util;

public class Node {
	public int id;
	public int pId;
	public String name = null;
	public Node(int id, int pid, String name){
		this.id = id;
		this.pId = pid;
		this.name = name;
	}
	public Node(){
	}
	public int getId(){
		return id;
	}
	public int getpId(){
		return pId;
	}
	public String getName(){
		return name;
	}
	
	public void setId(int id){
		this.id =  id;
	}
	public void setpId(int pid){
		this.pId = pid;
	}
	public void setName(String Name){
		this.name = Name;
		
	}
}
