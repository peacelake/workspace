package ml.dataprocess;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import weka.core.Instances;
import weka.core.converters.CSVLoader;

/**
 * Load csv file into data instance
 * 
 * @author t_xuw
 *
 */
public final class CSV2Instance {

	private String csvSplitBy;

	public CSV2Instance(String splitSign) {
		csvSplitBy = splitSign;
	}
	
	
	/**
	 * Convert a '@' seperate csv file to 'comma' seperate CSV file. 
	 * And reads in the converted csv file to construct data instances.
	 * 
	 * @param csvFile
	 * @return
	 */
	public Instances getCSVInstances(String csvFile){
		//initialize objects and factors
		File targetFile = new File(csvFile);
		File modifiedFile = new File(targetFile.getParentFile().getAbsolutePath()+"\\modified_data.csv");
		BufferedReader br = null;
		String line = "";
		FileWriter writer;
		try{
			br = new BufferedReader(new FileReader(targetFile));
			writer = new FileWriter(modifiedFile);//read head first
			while((line = br.readLine()) != null){
				//use separator
				String[] dataList = line.split(csvSplitBy);
				for(int i=0; i<dataList.length; i++){
					String s = dataList[i].trim();
					if(isNumeric(dataList[i])){
						writer.append(s);//if numeric, append the number directly
					}else if (!s.isEmpty()){
						writer.append("'"+s+"'"); //if it is string, add quotes
					}
					//avoid extra comma at end of each row
					if(i<=dataList.length-2){
						writer.append(',');
					}else{
						writer.append('\n');
					}
				}
			}
			//write the file
		    writer.flush();
		    writer.close();
		    
		    //get instance from the newly created data file
			Instances data = getInstance(modifiedFile.getAbsolutePath());
			return data;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * test whether a string is an object or not.
	 * @param str
	 * @return
	 */
	private static boolean isNumeric(String str) {
		  try  
		  {  
		    double d = Double.parseDouble(str);  
		  }  
		  catch(NumberFormatException nfe)  
		  {  
		    return false;  
		  }  
		  return true; 
	}
	
	/**
	 * load CSV file and convert it into data instances
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public static Instances getInstance(String file) throws Exception{
	// read CSV file
		CSVLoader loader = new CSVLoader();
		loader.setSource(new File(file));
	// load the CSV and convert it into instances object
		return loader.getDataSet();
	 }
}
