public class SelectionSort extends Sort {
    /**
     * {@inheritDoc}
     */
    public SelectionSort(SimpleGrid grid, SubGrid subGrid, int column, int delay) {
        super(grid, subGrid, column, delay);
    }

    /**
     * Selection Sort.
     */
    @Override
    public void run() {
        for (int i = 0; i < length() - 1; i++) {
            int min = i;

            for (int j = i + 1; j < length(); j++) {
                if (get(j) < get(min)) {
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
