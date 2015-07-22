package opt.ga;

import weka.core.Instances;

public class OptimizerUtil {
		
	public static String getHeader(Instances data){
		StringBuffer sb = new StringBuffer();
		//manual add first element
		//shows the generation of this algorithm
		sb.append("Generation");
		sb.append("|");
		
		//index shows the ranking of generated simulation
		sb.append("Index");
		sb.append("|");
		
		//record the Energy Cost per square foot
		sb.append("EnergyCost per SquareFoot");
		sb.append("|");
		
		//add the attributes in the current database
		for(int i=0; i<data.numAttributes()-1; i++){
			sb.append(data.attribute(i));
			sb.append("|");
		}
		
		//add the last attribute to avoid an additional "|"
		sb.append(data.attribute(data.numAttributes()-1));
		sb.append("\n");
		return sb.toString();
	}

}
