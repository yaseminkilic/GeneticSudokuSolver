
import java.util.Iterator;
import java.util.TreeSet;

class Population implements Iterable<Chromosome> {

    private double sumFitness;
    private int sumConflicts;
    private TreeSet<Chromosome> presentation;
    private int size;

    Population() {
        sumFitness = 0.0;
        sumConflicts = 0;
        presentation = new TreeSet<>();
        size = (int) Data.instance().get("size-population");
    }

    Population(Population p) {
        this();
        sumFitness = p.sumFitness;
        sumConflicts = p.sumConflicts;
        for (Chromosome c : p) { presentation.add(new Chromosome(c)); }
    }
    
    Chromosome getBest() { return presentation.first(); }
    double getTotalFitness() { return sumFitness; }
    double getAvgConflicts() { return (sumConflicts/(double)presentation.size()); }
    double getAvgFitness() { return (sumFitness/(double)presentation.size()); }
    boolean isFull() { return (size==presentation.size()); }
    
    Chromosome[] get(int value) {
        if (value>size) throw new IllegalArgumentException("In population Get(int value) function");
        Chromosome[] ind_elite = new Chromosome[value];
        if (ind_elite.length > 0) {
            int i = 0;
            for (Chromosome ind : this) {
                ind_elite[i] = new Chromosome(ind);
                if (++i == ind_elite.length) break;
            }
        }
        return ind_elite;
    }
    
    void add(Chromosome[] chromosomes) { 
    	for (Chromosome i : chromosomes) { add(i); }
    }
    void add(Chromosome c) {
    	if(presentation.size() >= size){ return; }
        sumConflicts += c.getConflicts();
        sumFitness += c.fitness();
        presentation.add(c);
    }
    void createRandom() {
        for (int i=0; i<(int)Data.instance().get("size-population"); i++) {
            add(new Chromosome());
        }
    }

    @Override
    public Iterator<Chromosome> iterator() {
        return this.presentation.iterator();
    }
}
