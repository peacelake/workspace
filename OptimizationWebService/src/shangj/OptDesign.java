package shangj;

public class OptDesign {
	
	String HVAC;
	String Infiltration;
	String Schedule;
	String LPD;
	String Daylight;
	String Occupancy;
	String Window;
	String Roof;
	String Wall;
	double eui;
	
	public OptDesign(double e){
		eui = e;
	}
	
	public void setHVAC(String h){
		HVAC = h;
	}
	
	public void setInfiltration(String i){
		Infiltration = i;
	}
	
	public void setSchedule(String s){
		Schedule = s;
	}
	
	public void setLPD(String l){
		LPD = l;
	}
	
	public void setDaylight(String d){
		Daylight = d;
	}
	
	public void setOccupant(String o){
		Occupancy = o;
	}
	
	public void setWindow(String w){
		Window = w;
	}
	
	public void setRoof(String r){
		Roof = r;
	}
	
	public void setWall(String w){
		Wall = w;
	}
	
}
