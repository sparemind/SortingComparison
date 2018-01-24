public class SelectionSort extends Sort {
    /**
     * {@inheritDoc}
     */
    public SelectionSort(SimpleGrid grid, int column, int delay) {
        super(grid, column, delay);
    }

    /**
     * Selection Sort.
     */
    @Override
    public void run() {
        for (int i = 0; i < this.grid.getHeight() - 1; i++) {
            int min = i;

            for (int j = i + 1; j < this.grid.getHeight(); j++) {
                if (this.grid.get(this.column, j) < this.grid.get(this.column, min)) {
                    min = j;
                }
            }

            if (min != i) {
                swap(i, min);
            }

            delay();
        }
    }
}
