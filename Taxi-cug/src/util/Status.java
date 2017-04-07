package util;

public class Status {
	public static String CHANGE_DIRECTION="a";
	public static String NOT_CHANGE_DIRECTION="A";
	public static String SPEED_EVENT = "b";
	public static String NOT_SPEED_EVENT="B";
	public static String START_EVENT ="c";
	public static String STOP_EVENT="C";
	public static String HAS_PASSANGER="d";
	public static String HAS_BOT_PASSANGER="D";
	public static String BREAK_CAT_STATUS = "e";
	public static String NOT_BREAK_CAT_STATUS = "E";
	
	//geo-encoding longitude altitude speed direction time shache;
	//geo-encoding longitude altitude speed direction initialTypetime time shache passanger
	public String status = new String();
	public void calStatus(String[] nowValue, String[] preValue){
		if(Math.abs(Double.valueOf(nowValue[4])-Double.valueOf(preValue[4]))>60){
			this.status += CHANGE_DIRECTION;
		}else{
			this.status += NOT_CHANGE_DIRECTION;
		}
		
		if(Math.abs(Double.valueOf(nowValue[3])-Double.valueOf(preValue[3]))>10){
			this.status += SPEED_EVENT;
		}else{
			this.status += NOT_SPEED_EVENT;
		}				
		
		if(Double.valueOf(nowValue[3])==0){
			this.status += STOP_EVENT;
		}else{
			this.status += START_EVENT;
		}
		
		if(nowValue[8]=="1"){
			this.status += HAS_PASSANGER;
		}else{
			this.status += HAS_BOT_PASSANGER;
		}
		
		if(nowValue[7]=="1"){
			this.status += BREAK_CAT_STATUS;
		}else{
			this.status +=NOT_BREAK_CAT_STATUS;
		}
	}
	public void setStatus(){
		this.status = new String();
	}
}
