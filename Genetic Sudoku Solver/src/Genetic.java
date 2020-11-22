
import java.util.*;

class Genetic {

    private Grid grid;
    private Set<Integer> variables;
    private Map<Integer, Integer[]> varRow, varColumn, varBlock, arr;
    private Map<Integer, Set<Integer>> valids;

    Integer[] row(int r) { return varRow.get(r); }
    Integer[] column(int c) { return varColumn.get(c); }
    Integer[] block(int b) { return varBlock.get(b); }
    Integer[] valids(int i) { return arr.get(i); }
    Integer[] getVariables() { return variables.toArray(new Integer[variables.size()]); }
    boolean isValidFor(int number, int i) { 
    	if(valids.containsKey(i)) return valids.get(i).contains(number); 
    	return false;
    }
    
    Genetic(Grid g) {
        grid = g;
        variables = grid.getEmptys();
        
        boolean inserted = true;
        int leaveEmpty = (int) Data.instance().get("empty-leave");
        while (inserted && variables.size() > leaveEmpty) {
            checkValidNum();
            /* check valid values */
            Iterator<Integer> iterator = this.variables.iterator();
            while (iterator.hasNext()) {
                int index = iterator.next();
                inserted = insertResolved(index, grid.getRow(index));
                if (inserted) { iterator.remove(); break; }

                inserted = insertResolved(index, grid.getColumn(index));
                if (inserted) { iterator.remove(); break; }

                inserted = insertResolved(index, grid.getBlock(index));
                if (inserted) { iterator.remove(); break; }
            }
        }
        checkValidNum();

        varRow = new HashMap<>();
        varBlock = new HashMap<>();
        varColumn = new HashMap<>();
        
        ArrayList<Integer> tmpRows, tmpCols, tmpBlock;
        int row = 0, col = 0, block = 0;
        for (int j = 0; j < grid.getLength(); j++) {
            tmpRows = new ArrayList<>();
            tmpCols = new ArrayList<>();
            tmpBlock = new ArrayList<>();
            for (int i = 0; i < grid.getLength(); i++) {
                row = grid.getIndexWithRow(j, i);
                col = grid.getIndexWithColumn(j, i);
                block = grid.getIndexWithBlock(j, i);
                
                if (variables.contains(row)) { tmpRows.add(row); }
                if (variables.contains(col)) { tmpCols.add(col); }
                if (variables.contains(block)) { tmpBlock.add(block); }
            }
            varRow.put(j, tmpRows.toArray(new Integer[tmpRows.size()]));
            varColumn.put(j, tmpCols.toArray(new Integer[tmpCols.size()]));
            varBlock.put(j, tmpBlock.toArray(new Integer[tmpBlock.size()]));
        }
        
        /* Converts valids-set to arrays */
        arr = new HashMap<>();
        for (Map.Entry<Integer, Set<Integer>> validN : valids.entrySet()) {
            arr.put(validN.getKey(),
            validN.getValue().toArray(new Integer[validN.getValue().size()]));
        }
    }

    private void checkValidNum() {
        valids = new HashMap<>();
        Integer[] var = variables.toArray(new Integer[variables.size()]);
        for (int x=0; x<var.length; x++) {
            Set<Integer> validNumbers = new HashSet<>();
            for (int y=grid.min(); y<=grid.max(); y++) {
                grid.set(var[x], y);
                if (grid.getConflicts() == 0) { validNumbers.add(y); }
                grid.set(var[x], 0);
            }
            valids.put(var[x], validNumbers);
        }
    }

    private boolean insertResolved(int i, int[] arr) {
        Set<Integer> tmpValids = new HashSet<Integer>(valids.get(i));
        Iterator<Integer> ite;
        for (int value:arr) {
            if (variables.contains(value) && value!=i) {
                ite = tmpValids.iterator();
                while (ite.hasNext()) {
                    int val = ite.next();
                    if (valids.get(value).contains(val)) { ite.remove(); }
                }
            }
        }
        if (tmpValids.size()!=1) { return false; }
        grid.set(i, tmpValids.iterator().next());
        return true;
    }
}
