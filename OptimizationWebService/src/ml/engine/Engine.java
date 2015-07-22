package ml.engine;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import opt.ga.OptimizerEngine;
import shangj.OptResultSet;
import ml.dataprocess.CSV2Instance;
import ml.dataprocess.SensitivityAnalysis;
import ml.util.WekaUtil;
import weka.classifiers.Classifier;
import weka.core.Instance;
import weka.core.Instances;

public class Engine implements LearningEngine{
	
	private String ClimateZone;
	private String buildingType;
	private String buildingForm;
	private Instances trainingData;
	private Instances testData;
	private Classifier classifier;
	private StringBuffer summary;
	
	/**
	 * Constructor
	 */
	public Engine(){
		summary = new StringBuffer();
	}
	
	@Override
	public void setClimateZone(String clz) {
		ClimateZone = clz;
	}

	@Override
	public void setBuildingType(String bldgT) {
		buildingType = bldgT;
	}

	@Override
	public void setBuildingForm(String form) {
		buildingForm = form;
	}

	@Override
	public double getTestInstanceResult(String values) throws Exception {
		//assume this is a new csv file and use @ as separator
	    File write = new File("D:\\workspace\\OptimizationWebService\\NN_alg");
	    FileInputStream fis = new FileInputStream(write);
	    ObjectInputStream ois = new ObjectInputStream(fis);
	    classifier = (weka.classifiers.functions.MultilayerPerceptron)ois.readObject();
	    trainingData = CSV2Instance.getInstance("D:\\workspace\\OptimizationWebService\\test_data.csv");
	    trainingData.setClass(trainingData.attribute(trainingData.numAttributes()-1));
		Instance predict = trainingData.instance(0);
		
		//String[] valueList = values.split("#");
		for(int i=0; i<values.length(); i++){
			System.out.print(values.charAt(i));
			System.out.println(predict.attribute(i));
			Integer index = Character.getNumericValue(values.charAt(i));
			System.out.println(predict.attribute(i).value(index));
			predict.setValue(predict.attribute(i), predict.attribute(i).value(index));
		}
		
		return classifier.classifyInstance(predict);
	}

	
	public void trainedModel() throws Exception {
		//1. select pre-simulated data by using climate and building type filters
		//2. pull out the data in a csv form and train models
		//CSV2Instance csv = new CSV2Instance("@");
		//assume this is the trained data pulled out from the database
		//Instances data = csv.getCSVInstances("C:\\Users\\t_xuw\\Documents\\Work\\4_Energy-Study\\3.Optimization\\Demo\\DemoTestSets\\test_data.csv");
		Instances data = CSV2Instance.getInstance("C:\\Users\\t_xuw\\Documents\\Work\\4_Energy-Study\\3.Optimization\\Demo\\DemoTestSets\\test_data.csv");
		data.setClass(data.attribute(data.numAttributes()-1)); // set class attribute (EUI)
		
		String[] algorithm = {"M5P","SVM","NN","LR"}; //algorithms
		//select a classifier from the above four algorithms
	    String bestSelected = null;
	    double minRootMeanError = Double.MAX_VALUE;
	    for(int i=0; i<algorithm.length; i++){
	    	System.out.println(algorithm[i]);
	    	String currentAlg = algorithm[i];
	    	Classifier temp = WekaUtil.getClassifier(currentAlg);
	    	double tempRME = WekaUtil.evaluateCrossValidate(data, temp);
	    	if(minRootMeanError>= tempRME){
	    		minRootMeanError = tempRME;
	    		bestSelected = currentAlg;
	    	}
	    }
	    System.out.println("The selected algorithm is: "+bestSelected);
//	    bestClassifierForClimateZoneA = WekaUtil.buildClassifier(data, bestSelected);
	}
	
	@Override
	public void setClassifier() throws Exception{
	    File write = new File("D:\\workspace\\OptimizationWebService\\SVM_alg");
	    FileInputStream fis = new FileInputStream(write);
	    ObjectInputStream ois = new ObjectInputStream(fis);
	    classifier = (LibSVM)ois.readObject();
	    trainingData = CSV2Instance.getInstance("D:\\workspace\\OptimizationWebService\\test_data.csv");
	    trainingData.setClass(trainingData.attribute(trainingData.numAttributes()-1));
	    ois.close();
	}

	@Override
	public void performSensitivityAnalysis() {
		//initialize analysis data
		SensitivityAnalysis sen = new SensitivityAnalysis(trainingData);
		try {
			//get correlation rankings
			sen.getCorrelationRank();
			//get decision trees
			sen.getDecisionTree();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public OptResultSet performOptimizatioin() throws Exception{
		
		//assume this is a new csv file and use @ as separator
	    File write = new File("D:\\workspace\\OptimizationWebService\\NN_alg");
	    FileInputStream fis = new FileInputStream(write);
	    ObjectInputStream ois = new ObjectInputStream(fis);
	    classifier = (weka.classifiers.functions.MultilayerPerceptron)ois.readObject();
	    trainingData = CSV2Instance.getInstance("D:\\workspace\\OptimizationWebService\\test_data.csv");
	    trainingData.setClass(trainingData.attribute(trainingData.numAttributes()-1));
	    
		long classifierBuildStartTime = System.nanoTime();
		OptimizerEngine opt = null;
		try {
			opt = new OptimizerEngine(trainingData,classifier);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long classifierBuildEndTime = System.nanoTime();
		double classifierBuildElapsedTime = (
				(classifierBuildEndTime - classifierBuildStartTime) /
				 1000000000.0);
		summary = new StringBuffer();
		summary.append(opt.getOptimizationSummary());
		summary = summary.append("Optimization has successfully completed in " + classifierBuildElapsedTime+" seconds/n");
		//System.out.println("Optimization has successfully completed in " + classifierBuildElapsedTime+" seconds");
		System.out.println(summary.toString());
		return opt.getOptimizationData();
	}
	
	@Override
	public String getDesignOption(){
		summary = new StringBuffer();
		for(int i=0; i<trainingData.numAttributes(); i++){
			summary.append(trainingData.attribute(i).name());
			summary.append(":");
			for(int j=0; i<trainingData.attribute(i).numValues()-1;j++){
				summary.append(trainingData.attribute(i).value(j));
				summary.append("|");
			}
			summary.append(trainingData.attribute(i).value(trainingData.attribute(i).numValues()-1));
			summary.append("/n");
		}
		return summary.toString();
	}
}
