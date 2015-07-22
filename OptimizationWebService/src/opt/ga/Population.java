package opt.ga;

public class Population {
	
	private int geneLength;
    private static final Integer TOP = 3;
    Individual[] individuals;
    Individual[] topThreeIndividuals = new Individual[TOP];

    /*
     * Constructors
     */
    // Create a population
    public Population(int populationSize, boolean initialise, int l) {
    	geneLength = l;
        individuals = new Individual[populationSize];
        // Initialise population
        if (initialise) {
            // Loop and create individuals
            for (int i = 0; i < size(); i++) {
                Individual newIndividual = new Individual(geneLength);
                newIndividual.generateIndividual();
                saveIndividual(i, newIndividual);
            }
        }
    }

    /* Getters */
    public Individual getIndividual(int index) {
        return individuals[index];
    }

    public Individual getFittest() {
        Individual fittest = individuals[0];
        // Loop through individuals to find fittest
        for (int i = 0; i < size(); i++) {
            if (fittest.getFitness() >= getIndividual(i).getFitness()) {
                fittest = getIndividual(i);
            }
        }
        return fittest;
    }
    
    public int getGeneLength(){
    	return geneLength;
    }
    
    /**
     * get the top three solutions. Save the elite solutions
     * @return
     */
    public Individual[] getTopThreeFittest(){
    	Individual[] tempIndividuals = sortIndividuals();
//    	for(Individual i: tempIndividuals){
//    		System.out.println("Individual: "+ i.getFitness());
//    	}
    	
    	for(int i=0; i<TOP; i++){
    		if(topThreeIndividuals[i]==null){
    			topThreeIndividuals[i] = tempIndividuals[i];
    		}else if(topThreeIndividuals[i].getFitness()>tempIndividuals[i].getFitness() && 
    				notDuplicated(tempIndividuals[i].getFitness())){
    			topThreeIndividuals[i] = tempIndividuals[i];
    		}
    	}
    	
//    	for(Individual j: topThreeIndividuals){
//    		System.out.println("topthree design: "+ j.getFitness());
//    	}
    	
    	return topThreeIndividuals;
    }
    
    /**
     * in case the top three design cases are same
     * @param fitness
     * @return
     */
    private boolean notDuplicated(double fitness){
    	for(Individual i: topThreeIndividuals){
    		if(fitness== i.getFitness()){
    			return false;
    		}
    	}
    	return true;
    }
    
	/**
	 * sort correlation using insertion sort so that variables with highest
	 * correlation is at front of array
	 */
	private Individual[] sortIndividuals(){
		Individual[] tempIndividuals = individuals;
		for(int i=0; i<individuals.length; i++){
			Individual tempIndividual = tempIndividuals[i];
			int j=i;
			while(j>0 && tempIndividuals[j-1].getFitness()>tempIndividual.getFitness()){
				tempIndividuals[j] = tempIndividuals[j-1];
				j = j-1;
			}
			tempIndividuals[j] = tempIndividual;
		}
		return tempIndividuals;
	}

    /* Public methods */
    // Get population size
    public int size() {
        return individuals.length;
    }

    // Save individual
    public void saveIndividual(int index, Individual indiv) {
        individuals[index] = indiv;
    }
}
