package shangj;

public class OptResult {
	
	int generation;
	int rank;
	double eui;
	
	public OptResult(int g, int r){
		generation = g;
		rank = r;
		eui = Double.MAX_VALUE;
	}
	
	public void setEUI(double e){
		eui = e;
	}
}
