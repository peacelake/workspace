package ml.dataprocess;

import java.util.ArrayList;
import java.util.HashMap;

import weka.core.Attribute;
import weka.core.Instances;

public class OneHotInstances {
	
	/**
	 * hashmap stores the encoding mapping
	 * key is the encoded attributes header
	 * arrayList contains all attributes after expanded using one hot encoding method
	 */
	private HashMap<String, ArrayList<String>> encodeMap = new HashMap<String, ArrayList<String>>();
	
	private Instances dataset;
	
	public OneHotInstances(Instances data){
		dataset = data;
	}
	
	public void oneHotEncode(){
		Attribute a = dataset.attribute(0);
	}

}
