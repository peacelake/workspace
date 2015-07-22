package ml.engine;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.ObjectInputStream;

import ml.dataprocess.CSV2Instance;
import weka.classifiers.Classifier;
import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;

public class Main {
	
	public static void main(String[] args) throws Exception{
		String sFileName = "C:\\Users\\t_xuw\\Documents\\Work\\4_Energy-Study\\3.Optimization\\Demo\\DemoTestSets\\combo.csv";
	    FileWriter writer = new FileWriter(sFileName);

		
	    File write = new File("C:\\Users\\t_xuw\\Documents\\Work\\4_Energy-Study\\3.Optimization\\Demo\\DemoTestSets\\SVM_alg");
	    FileInputStream fis = new FileInputStream(write);
	    ObjectInputStream ois = new ObjectInputStream(fis);
	    Classifier bestClassifier = (LibSVM)ois.readObject();
	    ois.close();
		
		
		Instances predict = CSV2Instance.getInstance("C:\\Users\\t_xuw\\Documents\\Work\\4_Energy-Study\\3.Optimization\\Demo\\DemoTestSets\\predict.csv");
		Attribute hvac = predict.attribute(0);
		Attribute infil = predict.attribute(1);
		Attribute schedule = predict.attribute(2);
		Attribute lpd = predict.attribute(3);
		Attribute daylight = predict.attribute(4);
		Attribute occupancy = predict.attribute(5);
		Attribute glaze = predict.attribute(6);
		Attribute roof = predict.attribute(7);
		Attribute wall = predict.attribute(8);
		
		predict.setClass(predict.attribute(predict.numAttributes()-1));

		
		Instances test = new Instances(predict,0,1);
		
		String hvacs = null;
		String infils = null;
		String schedules = null;
		String lpds = null;
		String daylights = null;
		String occupancys = null;
		String glazes = null;
		String roofs = null;
		String walls = null;
		
		StringBuffer sb = new StringBuffer();
		sb.append("Index|");
		sb.append("EUI|");
		sb.append("HVAC|");
		sb.append("Infiltration|");
		sb.append("Schedule|");
		sb.append("LPD|");
		sb.append("Daylights|");
		sb.append("Occupancy|");
		sb.append("Glaze|");
		sb.append("Roof|");
		sb.append("Walls\n");
		
		long classifierBuildStartTime = System.nanoTime();
		
		int counter = 0;
		for(int h=0; h<hvac.numValues();h++){
			hvacs = hvac.value(h);
			for(int i=0; i<infil.numValues();i++){
				infils = infil.value(i);
				for(int s=0; s<schedule.numValues(); s++){
					schedules = schedule.value(s);
					for(int l=0; l<lpd.numValues(); l++){
						lpds = lpd.value(l);
						for(int d=0; d<daylight.numValues(); d++){
							daylights = daylight.value(d);
							for(int o=0; o<occupancy.numValues();o++){
								occupancys = occupancy.value(o);
								for(int g=0; g<glaze.numValues(); g++){
									glazes = glaze.value(g);
									for(int r=0; r<roof.numValues(); r++){
										roofs = roof.value(r);
										for(int w=0; w<wall.numValues();w++){
											walls = wall.value(w);
											sb.append(counter);
											sb.append("|");
											Instance t = test.instance(0);
											t.setValue(t.attribute(0), hvacs);
											t.setValue(t.attribute(1), infils);
											t.setValue(t.attribute(2), schedules);
											t.setValue(t.attribute(3), lpds);
											t.setValue(t.attribute(4), daylights);
											t.setValue(t.attribute(5), occupancys);
											t.setValue(t.attribute(6), glazes);
											t.setValue(t.attribute(7), roofs);
											t.setValue(t.attribute(8), walls);
											double predictedValue = bestClassifier.classifyInstance(t);
											sb.append(predictedValue);
											sb.append("|");
											sb.append(hvacs+"|");
											sb.append(infils+"|");
											sb.append(schedules+"|");
											sb.append(lpds+"|");
											sb.append(daylights+"|");
											sb.append(occupancys+"|");
											sb.append(glazes+"|");
											sb.append(roofs+"|");
											sb.append(walls+"\n");
											counter++;
										}
									}//for roof
								}//for glazes
							}//for occupancy
						}//for daylight
					}//for lpds
				}//for schedule
			}//for infiltration
		}//for hvac
		long classifierBuildEndTime = System.nanoTime();
		double classifierBuildElapsedTime = (
				(classifierBuildEndTime - classifierBuildStartTime) /
				 1000000000.0);
		
		System.out.println("SVM" + " classifier predict " +counter+ " design combinations. The total time used: "+
				   classifierBuildElapsedTime + " seconds");
		
		writer.append(sb.toString());
		writer.flush();
		writer.close();
	}

}
