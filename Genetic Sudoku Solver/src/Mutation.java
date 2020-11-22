
class Mutation {

    private Genetic genetic;
    Mutation() {
        genetic = (Genetic) Data.instance().get("genetic");
    }
    
    Chromosome mutate(Chromosome chrom) {
        if (Math.random() < (double) Data.instance().get("rate-mutation")) {
            return chrom;
        }
        int row, f, tmp;
        Integer[] fields;
        for (int i = 0; i < (int)(Math.random()*chrom.getLength()); i++) {
            row = (int)(chrom.getLength()*Math.random());
            fields = genetic.row(row);
            if (fields.length > 0) {
                f = fields[(int)(Math.random()*fields.length)];
                for (int j = 0; j<fields.length; j++) {
                    if (chrom.get(f) != chrom.get(fields[j]) && genetic.isValidFor(f, chrom.get(fields[j])) && genetic.isValidFor(fields[j], chrom.get(f))) {
                        tmp = chrom.get(fields[j]);
                        chrom.set(f, chrom.get(fields[j]));
                        chrom.set(fields[j], tmp);
                    }
                }
            }
        }
        return chrom;
    }
}
    