/*
 * Yasemin KILIÇ
 * YBU Number: 185101131
 * ysmnkilic93@gmail.com
 */

public class _Main_ implements DefaultValues {

    public static void main(String[] args) {
        System.out.println("\t=> Genetic Sudoku Solver <=");
        
        Grid grid = new Grid(blocksize);
        grid.writeAll(sudokuInput);
        System.out.println("\nStarting ...");
        System.out.println(grid);
        
        Data.instance().set("grid", grid);
        Data.instance().set("rate-elitism", eRate);
        Data.instance().set("rate-mutation", mRate);
        Data.instance().set("size-population", pSize);
        Data.instance().set("empty-leave", emptyLeave);
        Data.instance().set("before-populations", beforePopulation);
        Data.instance().set("genetic", new Genetic(grid));
        Data.instance().set("crossover", new Crossover(nParent));
        Data.instance().set("selection", new Selection());
        Data.instance().set("mutation", new Mutation());

        System.out.println("\nAfter PreProcessing ...");
        System.out.println(grid);
        
        Environment env = new Environment();
        Chromosome solution = env.findSolution();

        System.out.println("\nSolution ...");
        System.out.println(solution);
        System.out.println("Conflicts Number: " + grid.getConflicts());
    }
}
