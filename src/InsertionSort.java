public class InsertionSort extends Sort {
    /**
     * {@inheritDoc}
     */
    public InsertionSort(SimpleGrid grid, int column, int delay) {
        super(grid, column, delay);
    }

    /**
     * Insertion Sort.
     */
    @Override
    public void run() {
        int i = 1;

        while (i < this.grid.getHeight()) {
            int j = i;
            while (j > 0 && this.grid.get(this.column, j - 1) > this.grid.get(this.column, j)) {
                swap(j, j - 1);
                j--;
            }
            i++;

            delay();
        }
    }
}
