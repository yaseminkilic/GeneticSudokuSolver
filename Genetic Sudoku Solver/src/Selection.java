
public class Selection {
	
    public Chromosome select(Population pop) {
        double random = Math.random();
        for (Chromosome c : pop) {
            double value = c.fitness()/pop.getTotalFitness();
            if (random <= value) { return c; }
            random -= value;
        }
        throw new RuntimeException("Selection Failed");
    }
}
