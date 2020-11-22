
public interface DefaultValues {

	public static int blocksize = 3;
	public static int[][] sudokuInput = 
		 {{0,0,6,0,0,0,0,0,0},
	      {0,8,0,0,5,4,2,0,0},
	      {0,4,0,0,9,0,0,7,0},
	      {0,0,7,9,0,0,3,0,0},
	      {0,0,0,0,8,0,4,0,0},
	      {6,0,0,0,0,0,1,0,0},
	      {2,0,3,0,0,0,0,0,1},
	      {0,0,0,5,0,0,0,4,0},
	      {0,0,8,3,0,0,5,0,2}};

	public static int nParent = 2;
	public static int pSize = 1000;
	public static int emptyLeave = 0;
    public static int beforePopulation = 20;
    
    /* remember the best solution found */
    public static double eRate = 0.002; 
    
    /* mRate nearly 0.015 to 0.02 
     * (random) jump in the location of the generated solutions 
     * too high mutation rate reduces the search ability of GA
	 * too small GA almost always fails to a local optimum. */
    public static double mRate = 0.1;
}
