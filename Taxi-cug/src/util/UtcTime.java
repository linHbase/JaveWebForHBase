package util;

public class UtcTime {
	public int second = 0;
	
	public void calYear(String year){
		int Year = Integer.valueOf(year);
		for(int i = 1970;i<Year;i++){
			if(judge(i)){
				second += 366*24*3600;
			}else{
				second += 365*24*3600;
			}
		}		
	}
	public void calMonth(String year,String month){
		switch(month){
		case "01":
			break;
		case "02":
			second += 31*24*3600;
			break;
		case "03":
			second += 59*24*3600;
			break;
		case "04":
			second += 90*24*3600;
			break;
		case "05":
			second += 120*24*3600;
			break;
		case "06":
			second += 151*24*3600;
			break;
		case "07":
			second += 181*24*3600;
			break;			
		case "08":
			second += 212*24*3600;
			break;
		case "09":
			second += 243*24*3600;
			break;
		case "10":
			second += 273*24*3600;
			break;
		case "11":
			second += 304*24*3600;
			break;
		case "12":
			second += 334*24*3600;
			break;
		}
		if(judge(Integer.valueOf(year))&&month!="01"&&month!="02"){
			second += 24*3600;
		}
	}
	public void calDay(String day){
		int Day = Integer.valueOf(day);
		second += (Day-1)*24*3600;
	}
	public void calHourAndOther(String HourAndOther){
		String[] hour = HourAndOther.split(":");
		if(hour.length==3){
		second += (Integer.valueOf(hour[0]))*3600;
		second += (Integer.valueOf(hour[1]))*60;
		second += (Integer.valueOf(hour[2]));
		}else{
			System.out.println("error of the input format!");
		}
	}
	public boolean judge(int year){
		if((year % 4 == 0 && year % 100 != 0) || year % 400 ==0){
			return true;
		}else{
			return false;
		}
	}
	public void calAll(String year){
		String[] all =year.split(" ");		
		String[] Year = all[0].split("-");
		calMonth(Year[0], Year[1]);
		calDay(Year[2]);
		calYear(Year[0]);
		if(all.length==2){			
			calHourAndOther(all[1]);
		}
	}
	public void setSecond(){
		this.second = 0;
	}
	//1970-01-01 00:00:00
	//2016-03-01 00:00:01
	public static void main(String[] args){
		String year = "2016-03-01 00:00:00";
		UtcTime utc = new UtcTime();
		utc.calAll(year);
		System.out.println(utc.second);
		String year2 = "1971-04-01 00:00:00";
		utc.setSecond();
		utc.calAll(year2);
		System.out.println(utc.second);
	}
}
