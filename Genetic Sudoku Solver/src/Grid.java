
import java.util.HashSet;
import java.util.Set;

class Grid {

    private Block[] rows, columns, blocks;
    private int conflicts, length, nBlock, nEmpty;
    
    Grid(Grid g) {
        nEmpty = g.nEmpty;
        rows = clone(g.rows);
        columns = clone(g.columns);
        blocks = clone(g.blocks);
        nBlock = g.getNblock();
        length = g.getLength();
        conflicts = g.getConflicts();
    }
    
    Grid(int blockSize) {
        nBlock = blockSize;
        length = nBlock*nBlock;
        nEmpty = length*length;
        rows = new Block[blockSize*blockSize];
        blocks = new Block[blockSize*blockSize];
        columns = new Block[blockSize*blockSize];
        
        for (int i = 0; i < rows.length; i++){ rows[i] = new Block(1, getLength(), 1); }
        for (int i = 0; i < columns.length; i++){ columns[i] = new Block(1, getLength(), 1); }
        for (int i = 0; i < blocks.length; i++){ blocks[i] = new Block(1, getLength(), 1); }
    }

    private Block[] clone(Block[] b) {
        Block[] tmp = new Block[b.length];
        for (int i=0; i<b.length; i++) { tmp[i] = new Block(b[i]); }
        return tmp;
    }
    
    int getNblock() { return nBlock; }
    int getLength() { return length; }
    int getConflicts() { return conflicts; }
    int min() { return 1; }
    int max() { return getNblock() * getNblock(); }
    int get(int i) { return rows[i/(getLength())].get(i%getLength());}
    void set(int i, int value) { write(i/getLength(), i%getLength(), value); }
    
    int getIndexWithRow(int row, int i) {return row*getLength()+i; }
    int getIndexWithColumn(int col, int i) {return i*getLength()+col; }
    int getIndexWithBlock(int block, int i) {
        return ((block/getNblock())*getLength()*getNblock())+(i/getNblock())
        		*getLength()+((block%getNblock())*getNblock())+(i%getNblock());
    }

    int[] getRow(int i) { return rows[i/getLength()].getGrid(); }
    int[] getColumn(int i) { return columns[i%getLength()].getGrid(); }
    int[] getBlock(int i) {
    	/* (RowIndex/nBlock) * nBlock + (ColumnIndex/nBlock) */
        int indexOfBlock = ((i/getLength())/getNblock())* getNblock() +((i%getLength())/getNblock());
        return blocks[indexOfBlock].getGrid();
    }
    Set<Integer> getEmptys() {
    	HashSet<Integer> set = new HashSet<Integer>();
        for (int r = 0; r < rows.length; r++) {
            int[] arr = rows[r].present();
            for (int c = 0; c < arr.length; c++) {
            	int loc = (r*getLength())+c;
                if (arr[c] == 0){ set.add(loc); }
            }
        }
        return set;
    }

    void writeAll(int[][] array) {
    	for(int r=0; r<array.length; r++){
    		for(int col=0; col<array[r].length; col++){
    			write(r, col, array[r][col]);
    		}
    	}
    }
    
    void write(int r, int col, int number) {
    	int bIndex, bNum;
        if (number == 0) nEmpty += 1;
        if (rows[r].get(col) == 0) nEmpty -= 1;
        bIndex = ((r%nBlock)*nBlock) + (col%nBlock);
        bNum = ((r/nBlock)*nBlock) + ((col/nBlock));
        
        conflicts -= (rows[r].conflicts() + columns[col].conflicts() + blocks[bNum].conflicts());
        rows[r].write(col, number);
        rows[r].set(col, getIndexWithRow(r, col));
        columns[col].write(r, number);
        columns[col].set(r, getIndexWithColumn(col, r));
        blocks[bNum].write(bIndex, number);
        blocks[bNum].set(bIndex, getIndexWithBlock(bNum, bIndex));
        conflicts += (rows[r].conflicts() + columns[col].conflicts() + blocks[bNum].conflicts());
    }
    
    public String toString() {
    	String line = "", value = "";
    	for(int i = 0; i < 25; i++) { line += "_"; }
    	line += "\n";
    	
        for(int i = 0; i < rows.length; i++) {
            if (i%nBlock == 0) { value += line; }
            value += rows[i].print(nBlock) + '\n';
        }
        (value += line).trim();
        return value;
    }
}
