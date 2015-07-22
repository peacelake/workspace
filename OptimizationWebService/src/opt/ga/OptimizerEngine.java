package opt.ga;

import java.io.IOException;
import java.util.Arrays;

import shangj.OptResult;
import shangj.OptResultSet;
import shangj.OptDesign;
import weka.classifiers.Classifier;
import weka.core.Attribute;
import weka.core.Instances;

public class OptimizerEngine {
	
	//data that contains all the training data
	private Instances testData;
	//data record the byte length for each attribute
	private Integer[] byteLength;
	//store the total length of a chromesome
	private Integer totalLength;
	//fitness calculation method
	private FitnessCalc fitness;
	private Integer iteration = 100;//adjust iterations
	private Integer populationSize = 10;//adjust population size
	private StringBuffer csvBuffer;//output
	
	private OptResultSet resultSet = new OptResultSet();
	
	
	public OptimizerEngine(Instances tData, Classifier classifier) throws IOException{
		//1. Set-up basic variables
		testData = tData;		
		byteLength = new Integer[testData.numAttributes()-1];
		totalLength = 0;
		
		//2. Set up output header
		csvBuffer = new StringBuffer();
		csvBuffer.append(OptimizerUtil.getHeader(testData));
		
		//3. encode all the design variables
		for(int i=0; i<testData.numAttributes()-1; i++){
			Attribute tempAttr = testData.attribute(i);
			Integer numOfValue = tempAttr.numValues();
			byteLength[i] = numberOfBits(numOfValue);
			totalLength += byteLength[i];
		}
		
		//4. set-up fitness function
		fitness = new FitnessCalc(totalLength);
		fitness.setByteLength(byteLength);
		fitness.setClassifier(classifier);
		fitness.setTestData(testData);
		
		//5. generate the first group of population
		Population designPop = new Population(populationSize,true,totalLength);
		resultSet.cleanResults();
		//6. start optimizing
		int counter = 0;
		while(counter <iteration){
			designPop = Algorithm.evolvePopulation(designPop);
			counter++;

			//System.out.println("Generation: "+counter+" Fittest: ");
			Individual[] individuals = designPop.getTopThreeFittest();
			for(int i=0; i<individuals.length; i++){
				csvBuffer.append(counter);//append generation
				csvBuffer.append("|");
				csvBuffer.append(i);//append ranking
				csvBuffer.append("|");
				csvBuffer.append(individuals[i].getFitness());//append energy cost
				csvBuffer.append("|");
				String[] designs = FitnessCalc.decodeGene(individuals[i]);
				//append optimum design
				for(int j=0; j<designs.length-1; j++){
					csvBuffer.append(designs[j]);
					csvBuffer.append("|");
				}
				csvBuffer.append(designs.length-1);
				csvBuffer.append("\n");
				
				OptResult r = new OptResult(counter,i);
				r.setEUI(individuals[i].getFitness());
				resultSet.addResultSet(r);
				System.out.println("        --"+individuals[i].getFitness()+" Design: "+Arrays.toString(FitnessCalc.decodeGene(individuals[i])));
			}
		}
		Individual[] individuals = designPop.getTopThreeFittest();
		for(int top=0; top<3; top++){
			OptDesign d = new OptDesign(individuals[top].getFitness());
			setUpDesign(d, individuals[top]);
			resultSet.addBestSolutions(d);
		}
	}
	
	private void setUpDesign(OptDesign d, Individual i){
		String[] design =FitnessCalc.decodeGene(i);
		d.setHVAC(design[0]);
		d.setInfiltration(design[1]);
		d.setSchedule(design[2]);
		d.setLPD(design[3]);
		d.setDaylight(design[4]);
		d.setOccupant(design[5]);		
		d.setWindow(design[6]);
		d.setRoof(design[7]);
		d.setWall(design[8]);		
	}
	
	public String getOptimizationSummary(){
		return csvBuffer.toString();
	}
	
	public OptResultSet getOptimizationData(){
		return resultSet;
	}
	
	/**
	 * determine the number of bits for an integer
	 * @param num
	 * @return
	 */
	private Integer numberOfBits(Integer num){
		Integer count = 0;
		Integer value = num;
		while(value > 0){
			count++;
			value = value>>1;
		}
		return count;
	}

}
