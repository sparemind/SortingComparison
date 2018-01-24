public class InsertionSort extends Sort {
    /**
     * {@inheritDoc}
     */
    public InsertionSort(SimpleGrid grid, SubGrid subGrid, int column, int delay) {
        super(grid, subGrid, column, delay);
    }

    /**
     * Insertion Sort.
     */
    @Override
    public void run() {
        int i = 1;

        while (i < length()) {
            int j = i;
            while (j > 0 && get(j - 1) > get(j)) {
                swap(j, j - 1);
                j--;
            }
            i++;

            delay();
        }
    }
}
