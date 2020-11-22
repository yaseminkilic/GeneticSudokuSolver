import java.util.Arrays;

class Chromosome extends Grid implements Comparable<Chromosome> {

    private Genetic genetic;
    private int[] genes;

    public Chromosome(Chromosome i) {
        super(i);
        genetic = i.genetic;
        genes = Arrays.copyOf(i.genes(), i.genes().length);
    }

    public Chromosome() {
        super((Grid)Data.instance().get("grid"));
    	int randomInd;
    	Integer[] valids;
        genetic = (Genetic)Data.instance().get("genetic");
        genes = new int[genetic.getVariables().length];
        for (int x = 0; x < this.genes.length; x++) {
            valids = genetic.valids(genetic.getVariables()[x]);
            randomInd = (int)(valids.length*Math.random());
            setChromosome(x, valids[randomInd]);
        }
    }
    
    public int[] genes() { return genes; }
    public double fitness() { return Math.pow((1.0/(getConflicts()+1)), 3); }
    public int getChromosome(int i) { return genes()[i]; }
    public void setChromosome(int i, int value) {
        set(genetic.getVariables()[i], value);
        genes[i] = value;
    }
    
    @Override
    public int compareTo(Chromosome o) {
        return (fitness() < o.fitness()) ? 1 : -1;
    }
}
