package ml.engine;

import java.io.IOException;

import shangj.OptResultSet;

public interface LearningEngine {
	/**
	 * set the climate zone for the engine
	 * The climate zone is the first layer
	 * of filter for extracting relevant 
	 * training data. The training data is
	 * used to select and train the learning
	 * model
	 */
	void setClimateZone(String clz);
	
	/**
	 * set the building type for the engine.
	 * The building type is the second layer
	 * of filter for extracting relevant training
	 * data. The training data is used to select and
	 * train the learning model
	 */
	void setBuildingType(String bldgT);
	
	/**
	 * could be a future feature, but currently
	 * not used
	 */
	void setBuildingForm(String form);
	
	/**
	 * set the classifier for engine. The classifier is based on client input
	 * of climate and building type
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	void setClassifier() throws Exception;
	
	/**
	 * Pass the user inputs to trained model
	 * This file contains one specific design
	 * package that user specified, and wants
	 * the model to predict
	 * @param csvFile
	 */
	double getTestInstanceResult(String values) throws Exception;
	
	/**
	 * Perform spearman's correlation rankings for each variable
	 * This will allow user to find out the most sensitivity variables 
	 * that affect the EUI
	 */
	void performSensitivityAnalysis();
	
	/**
	 * Perform optimization using the identified classifier.
	 * This classifier should be the model that is trained with
	 * pre-simulated data.
	 * 
	 * @param classifer
	 */
	OptResultSet performOptimizatioin() throws Exception;
	
	/**
	 * get design options, the option format is:
	 * HVAC: 1|2|3|4|5 \n
	 * @return
	 */
	String getDesignOption();

}
