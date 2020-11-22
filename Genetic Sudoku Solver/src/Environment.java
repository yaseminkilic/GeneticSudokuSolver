
import java.util.ArrayList;

class Environment {

    private Mutation mutation;
    private Crossover crossover;
    private Selection selection;
    private ArrayList<Double> bestFitness;
    private Population population, backup_population, restart_population, elites;
    private int generation, training_cycle, nElites, populationSize, beforePopulation;
    
    Environment() {
        generation = 1;
        training_cycle = 1;
        bestFitness = new ArrayList<>();
        crossover = (Crossover)Data.instance().get("crossover");
        selection = (Selection)Data.instance().get("selection");
        mutation = (Mutation)Data.instance().get("mutation");
        populationSize = (int)Data.instance().get("size-population");
        beforePopulation = (int)Data.instance().get("before-populations");
        nElites = (int)(populationSize*((double)Data.instance().get("rate-elitism")));
        
        population = new Population();
        population.createRandom();
        backup_population = new Population(population);
        restart_population = new Population(population);
        elites = new Population();
    }
    
    Chromosome findSolution() {
    	Double fitness = 0.0;
        while(population.getBest().getConflicts()>0) {
            /* evolve population, add elites to new generation, and descendants to new generation */
            Population newP = new Population();
            newP.add(population.get(nElites));
            int i = nElites;
            for (Chromosome ind : backup_population) {
                crossover.setSculpture(ind);
                while (crossover.shouldParent()){ 
                	Chromosome selected = selection.select(population); 
                	crossover.addParent(selected); 
                }
                newP.add(mutation.mutate(crossover.cross()));
                if(++i == populationSize) break;
            }
            
            backup_population = population;
            population = newP;
            generation++;
            
            fitness = population.getBest().fitness();
            bestFitness.add(fitness);
            if(bestFitness.size()>=20 && fitness <= bestFitness.get(bestFitness.size()-20)) {
                // Print then find again 
                Chromosome elite = population.getBest();
            	System.out.println("Current Best ...");
                System.out.println(elite);
                System.out.println("Training Cycles(epoch): " + training_cycle);
                System.out.println("Generation: " + generation);
                System.out.println("Fitness: " + elite.fitness());
                System.out.println("Avg Population-fitness: " + population.getAvgFitness());
                System.out.println("Conflicts: " + elite.getConflicts());
                System.out.println("Avg Population-conflicts: " + population.getAvgConflicts() + "\n\n");
            	
            	if (!elites.isFull()) {
            		elites.add(population.get(populationSize / beforePopulation));
                } 
            	else {
                    restart_population = new Population(elites);
                    elites = new Population();
                }
                generation = 1;
                training_cycle++;
                bestFitness = new ArrayList<>();
                population = new Population(restart_population);
            }
        }
        return population.getBest();
    }
}
