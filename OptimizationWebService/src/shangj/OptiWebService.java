package shangj;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import ml.engine.Engine;
import ml.engine.LearningEngine;
 

@Path("/OptiWebService")
public class OptiWebService {

	public OptiWebService() throws Exception {
	}
	
	@GET
	@Produces("application/json")
	public OptResultSet GetResults () throws Exception {
		LearningEngine engine = new Engine();	
		OptResultSet optResultSet = engine.performOptimizatioin();		
		return optResultSet;	
	}
	
 
	@Path("{id}")
	@GET
	@Produces("application/json")
	public Results GetResults(@PathParam("id") String id) throws Exception {
		LearningEngine engine = new Engine();		
		double eui = engine.getTestInstanceResult(id);
		String result = new DecimalFormat("#.##").format(eui);		
		List<Integer> settings = new ArrayList<Integer>();		
		return new Results(result, settings);
	}
}