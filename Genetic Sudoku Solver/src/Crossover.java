
class Crossover {

    private int index;
    private Chromosome sculpt;
    private Chromosome[] parents;

    Crossover(int num) {
        index = 0;
        parents = new Chromosome[num];
    }
    
    Chromosome cross() {
        if (!shouldParent()) { 
	        for (int i = 0; i < sculpt.genes().length; i++) {
	            int ind = (int)(Math.random() * parents.length);
	            sculpt.setChromosome(i, parents[ind].getChromosome(i));
	        }
	        index = 0;
	        return sculpt;
        }
        throw new RuntimeException("Should be more parents!!!");
    }

    void addParent(Chromosome i) {
        if (i == null) { throw new IllegalArgumentException("Null-Parent in Crosover Class"); }
        parents[index] = i;
        index++;
    }
    void setSculpture(Chromosome s) { sculpt = s; }
    boolean shouldParent() { return (index<parents.length); }
}
