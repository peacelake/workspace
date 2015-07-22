package ml.engine;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import opt.ga.OptimizerEngine;
import weka.classifiers.Classifier;
import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;
import ml.dataprocess.CSV2Instance;
import ml.util.WekaUtil;

public class MainTest {
	
	public static void main(String[] args) throws Exception{
		CSV2Instance csv = new CSV2Instance("@");
		//Instances data = csv.getCSVInstances("C:\\Users\\t_xuw\\Documents\\Work\\4_Energy-Study\\3.Optimization\\test_data.csv");
		Instances data = CSV2Instance.getInstance("test_data.csv");
		data.setClass(data.attribute(data.numAttributes()-1));
		
//		Attribute a = data.attribute(0);
//		System.out.println(a.numValues());
		
	    //ArffSaver saver = new ArffSaver();
	    //saver.setInstances(data);
	    //saver.setFile(new File("C:\\Users\\t_xuw\\Documents\\Work\\4_Energy-Study\\3.Optimization\\Demo\\DemoTestSets\\test_data.arff"));
	    //saver.writeBatch();
	    
	    //String[] algorithm = {"NN","LR","SVM","M5P"};
	    //String[] algorithm = {"SVM"};
	    String[] algorithm = {"M5P","LR"};
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
//	    
//	    System.out.println(bestSelected);
	    Classifier bestClassifier = WekaUtil.buildClassifier(data, "M5P");
//	    System.out.println(bestClassifier);
//	    
	    
	    File write = new File("M5P_alg");
	    FileOutputStream fos = new FileOutputStream(write);
	    ObjectOutputStream oos = new ObjectOutputStream(fos);
	    oos.writeObject(bestClassifier);
	    oos.flush();
	    oos.close();
	    
	    
//	    FileInputStream fis = new FileInputStream(write);
//	    ObjectInputStream ois = new ObjectInputStream(fis);
//	    Classifier bestClassifier = (LibSVM)ois.readObject();
//	    ois.close();
	    
		Instances predict = CSV2Instance.getInstance("C:\\Users\\t_xuw\\Documents\\Work\\4_Energy-Study\\3.Optimization\\Demo\\DemoTestSets\\predict.csv");
		predict.setClass(data.attribute(data.numAttributes()-1));
//		
		Instance ins = predict.instance(0);
//		
		double index = predict.numInstances();
		//Instance pred = predict.instance(0);
		//System.out.println(bestClassifier.classifyInstance(pred));
		long classifierBuildStartTime = System.nanoTime();
		for(int j=0; j<index; j++){
			Instance in = predict.instance(j);
			System.out.println(bestClassifier.classifyInstance(in));
		}
		long classifierBuildEndTime = System.nanoTime();
		double classifierBuildElapsedTime = (
				(classifierBuildEndTime - classifierBuildStartTime) /
				 1000000000.0);
		
		System.out.println("SVM" + " classifier predict " +index+ " design combinations. The total time used: "+
				   classifierBuildElapsedTime + " seconds");
		
		classifierBuildStartTime = System.nanoTime();
		OptimizerEngine opt = new OptimizerEngine(data,bestClassifier);
		classifierBuildEndTime = System.nanoTime();
		classifierBuildElapsedTime = (
				(classifierBuildEndTime - classifierBuildStartTime) /
				 1000000000.0);
		System.out.println("Optimization has successfully completed in " + classifierBuildElapsedTime+" seconds");
	}
}
