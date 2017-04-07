package util;

public class MineGeohash {
	public String preEncoding;
	public MineGeohash(){
		this.preEncoding = new String();
	}
	public String calLongtitude(String longtitude){
		String longEncoding = new String();
		double longi = Double.valueOf(longtitude);
		double startlong = 0;
		double endlong = 360;
		while(Math.abs(startlong-endlong)>0.000002){
			if((startlong+endlong)/2>longi){
				longEncoding += "0";
				endlong = (startlong+endlong)/2;
			}else{
				longEncoding +="1";
				startlong = (startlong+endlong)/2;
			}
		}
		return longEncoding;
	}
	public String calAltitude(String altitude){
		String altiEncoding = new String();
		double alti = Double.valueOf(altitude);
		double startAlti = 0;
		double endAlti = 180;
		while(Math.abs(endAlti-startAlti)>0.000001){
			if((startAlti + endAlti)/2>alti){
				altiEncoding +="0";
				endAlti = (startAlti + endAlti)/2;
			}else{
				altiEncoding += "1";
				startAlti = (startAlti + endAlti)/2;
			}
		}
		return altiEncoding;
	}
	public void calEncoding(String longitude,String altitude){
		String longEncoding = calLongtitude(longitude);
		String altiEncoding = calAltitude(altitude);
		
		String preEncodingNumber = new String();
		for(int i = 0;i <longEncoding.length();i++){
			preEncodingNumber += longEncoding.charAt(i);
			preEncodingNumber += altiEncoding.charAt(i);
		}
//		System.out.println(preEncodingNumber.length());
		for(int i = 0;i<preEncodingNumber.length()/4;i++){
			this.preEncoding += changeEncoding(preEncodingNumber.substring(i*4, (i+1)*4));
	//		System.out.println(changeEncoding(preEncodingNumber.substring(i*4, (i+1)*4)));
		}
	}
	public String changeEncoding(String code){
		String encoding = new String();
		switch(code){
		case "0000" :
			encoding = "A";
			break;
		case "0001":
			encoding = "B";
			break;
		case "0010":
			encoding = "C";
			break;
		case "0011":
			encoding = "D";
			break;
		case "0100":
			encoding = "E";
			break;
		case "0101":
			encoding = "F";
			break;
		case "0110":
			encoding = "G";
			break;
		case "0111":
			encoding = "H";
			break;
		case "1000":
			encoding = "J";
			break;
		case "1001":
			encoding = "K";
			break;
		case "1010":
			encoding = "L";
			break;
		case "1011":
			encoding = "M";
			break;
		case "1100":
			encoding = "N";
			break;
		case "1101":
			encoding = "P";
			break;
		case "1110":
			encoding = "Q";
			break;
		case "1111":
			encoding = "R";
			break;
		}
		return encoding;
	}
	public void setEmpty(){
		this.preEncoding = new String();
	}
	//121.558130|31.228873
	public static void main(String[] args){
		MineGeohash geo = new MineGeohash();
		geo.calEncoding("121.558130", "31.228873");
		System.out.println(geo.preEncoding);
		System.out.println(geo.preEncoding.length());
		geo.setEmpty();
		geo.calEncoding("125.558130", "36.228873");
		System.out.println(geo.preEncoding);
		System.out.println(geo.preEncoding.length());
	}
}
