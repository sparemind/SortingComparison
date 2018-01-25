public class QuickSort extends Sort {
    /**
     * {@inheritDoc}
     */
    public QuickSort(SimpleGrid grid, SubGrid subGrid, int column, int delay) {
        super(grid, subGrid, column, delay);
    }

    /**
     * Quick Sort.
     */
    @Override
    public void run() {
        quickSort(0, length() - 1);
    }

    /**
     * TODO
     *
     * @param lo
     * @param hi
     */
    private void quickSort(int lo, int hi) {
        if (lo < hi) {
            int p = partition(lo, hi);
            quickSort(lo, p - 1);
            quickSort(p + 1, hi);
        }
    }

    /**
     * TODO
     *
     * @param lo
     * @param hi
     * @return
     */
    private int partition(int lo, int hi) {
        int pivot = get(hi);
        int i = lo - 1;
        for (int j = lo; j < hi; j++) {
            if (get(j) < pivot) {
                i++;
                swap(i, j);
                delay();
            }
        }
        if (get(hi) < get(i + 1)) {
            swap(i + 1, hi);
        }
        return i + 1;
    }
}
