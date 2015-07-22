package shangj;

import java.util.ArrayList;
import java.util.List;

public class OptResultSet {
	List<OptResult> resultSet;
	List<OptDesign> bestSolutions;
	
	public OptResultSet(){
		resultSet = new ArrayList<OptResult>();
		bestSolutions = new ArrayList<OptDesign>();
	}
	
	public void addResultSet(OptResult r){
		resultSet.add(r);
	}
	
	public List<OptResult> getResultSet(){
		return resultSet;
	}
	
	public void addBestSolutions(OptDesign d){
		bestSolutions.add(d);
	}
	
	public List<OptDesign> getBestSolutions(){
		return bestSolutions;
	}
	
	public void cleanResults(){
		resultSet.clear();
	}
}
