package opt.ga;

import weka.classifiers.Classifier;
import weka.core.Instance;
import weka.core.Instances;

public class FitnessCalc {
	
    byte[] solution;
    static Instances testData;
	static Classifier classifier;
	static Integer[] byteLength;
	
	public FitnessCalc(Integer length){
		solution = new byte[length];
	}
	
	public void setTestData(Instances data){
		testData = data;
	}
	
	public void setClassifier(Classifier cls){
		classifier = cls;
	}
	
	public void setByteLength(Integer[] byteLength){
		this.byteLength = byteLength;
	}

    // Calculate inidividuals fittness by comparing it to our candidate solution
    static double getFitness(Individual individual) {
    	Instance testInstance = testData.instance(0);//get an instance
    	//decode individual gene
        double fitness = 0;
        int prevPointer = 0;//previous
        int postPointer = 0;//end pointer
        for(int i=0; i<byteLength.length; i++){
        	postPointer = prevPointer + byteLength[i];//update post pointer
        	//extract bits
        	String bit = "";
        	for(int k = prevPointer; k<postPointer; k++){
        		byte b = individual.getGene(k);
        		if(b==1){
        			bit = bit+"1";	
        		}else{
        			bit = bit+"0";
        		}
        	}
        	prevPointer = postPointer; //update previous pointer
        	int valueIndex = Integer.parseInt(bit,2); //this is the value
        	int numberOfValues = testData.attribute(i).numValues();
        	if(valueIndex>=numberOfValues){
        		valueIndex = 0;
        	}
        	testInstance.setValue(testData.attribute(i), testData.attribute(i).value(valueIndex));
        }
        //run through the classifier
        try {
			fitness = classifier.classifyInstance(testInstance);
		} catch (Exception e) {
			e.printStackTrace();
		}
        return fitness;
    }
    
    static String[] decodeGene(Individual individual){
    	Instance testInstance = testData.instance(0);//get an instance
    	//decode individual gene
        String[] designOptions = new String[byteLength.length];
        int prevPointer = 0;//previous
        int postPointer = 0;//end pointer
        for(int i=0; i<byteLength.length; i++){
        	postPointer = prevPointer + byteLength[i];//update post pointer
        	//extract bits
        	String bit = "";
        	for(int k = prevPointer; k<postPointer; k++){
        		bit = bit+individual.getGene(k);
        	}
        	prevPointer = postPointer; //update previous pointer
        	int valueIndex = Integer.parseInt(bit,2); //this is the value
        	int numberOfValues = testData.attribute(i).numValues();
        	if(valueIndex>=numberOfValues){
        		valueIndex = 0;
        	}
        	designOptions[i] = testData.attribute(i).value(valueIndex);
        }
        return designOptions;
        
    }
    
    // Get optimum fitness
    int getMaxFitness() {
        int maxFitness = solution.length;
        return maxFitness;
    }
}
