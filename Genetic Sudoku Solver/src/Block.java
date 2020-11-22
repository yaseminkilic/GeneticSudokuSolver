
import java.util.Arrays;

class Block {

    private final int occurrences, min, max;
    private int conflicts;
    private int[] view, numUtilization, grid;

    Block(Block b) {
    	grid = b.grid;
        min = b.min();
        max = b.max();
        conflicts = b.conflicts();
        occurrences = b.occurrences();
        view = Arrays.copyOf(b.view, b.view.length);
        numUtilization = Arrays.copyOf(b.numUtilization, b.numUtilization.length);
    }
    
    Block(int rMin, int rMax, int occ) {
        conflicts = 0;
        min = rMin;
        max = rMax;
        occurrences = occ;
        view = new int[(1+rMax-rMin) * occ];
        numUtilization = new int[1+rMax-rMin];
        grid = new int[1+rMax-rMin];
    }

    int min() { return min; }
    int max() { return max; }
    int[] present() { return view; }
    int[] getGrid() { return grid; }
    int conflicts() { return conflicts; }
    int occurrences() { return occurrences; }
    int get(int i) { return view[i]; }
    void set(int i, int value) { grid[i] = value; }

    void write(int i, int value) {
        int old = view[i];
        if (old >= min()) {
        	if (numUtilization[old - 1] > 1) { conflicts--; }
            numUtilization[old-1] = (numUtilization[old-1]-1);
        }
        view[i] = value;
        if (value >= min()) {
            numUtilization[value-1] = (numUtilization[value-1]+1);
            if (numUtilization[value - 1] > 1){ conflicts++; }
        }
    }
    
    String print(int blockSize) {
        String toPrint = "";
        for (int i = 0; i < view.length; i++) {
            if (i % blockSize == 0) { toPrint += "| "; }
            toPrint += String.format("%" + (Math.log10(max())+1) + "d", view[i]) + " ";
        }
        return toPrint + "|";
    }
}
