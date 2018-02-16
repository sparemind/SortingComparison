public class Quicksort extends Sort {
    /**
     * {@inheritDoc}
     */
    public Quicksort(SimpleGrid grid, SubGrid subGrid, int column, int delay) {
        super(grid, subGrid, column, delay);
    }

    /**
     * Quicksort.
     */
    @Override
    public void run() {
        quicksort(0, length() - 1);
    }

    /**
     * Sorts the elements in the indexed range [lo, hi].
     *
     * @param lo The index of the lowest element of the range to sort.
     * @param hi The index of the highest element of the range to sort.
     */
    private void quicksort(int lo, int hi) {
        if (lo < hi) {
            int p = partition(lo, hi);
            quicksort(lo, p - 1);
            quicksort(p + 1, hi);
        }
    }

    /**
     * Partitions the elements in the given range into left and right
     * partitions. All elements < than a particular pivot value will be in the
     * left partition, and all elements >= than the pivot will be in the right
     * partition. The pivot value will be the first element in the right
     * partition.
     *
     * @param lo The index of the first element in the range to partition.
     * @param hi The index of the last element in the range to partition.
     * @return The index of the pivot value, which will be the first element in
     * the right partition.
     */
    private int partition(int lo, int hi) {
        // Get the index of the median element of the first, middle, and last
        // elements
        int mid = lo + (hi - lo) / 2;
        int median = median(lo, mid, hi);

        // Elements < this value will appear in the index range [lo, i]
        // Elements >= this value will appear in the index range (i, hi]
        int pivot = get(median);
        swap(median, hi);

        // Index just before the next available spot to swap (i.e. the index
        // of the last swapped value)
        int i = lo - 1;
        for (int j = lo; j < hi; j++) {
            // Move elements below pivot to the beginning of the [lo, hi] range
            if (get(j) < pivot) {
                i++;
                swap(i, j);
                delay();
            }
        }
        // Swap the pivot value into the middle
        swap(i + 1, hi);
        // Return the index of the first element in the right partition (the
        // pivot), which contains all elements >= the pivot value
        return i + 1;
    }

    /**
     * Returns the index of the median of the values at the given indices in
     * this sort's column.
     *
     * @param a Index of element 1.
     * @param b Index of element 2.
     * @param c Index of element 3.
     * @return The index of the median of the values at the given indices in
     * this sort's column.
     */
    private int median(int a, int b, int c) {
        int aValue = get(a);
        int bValue = get(b);
        int cValue = get(c);

        if (aValue < bValue) {
            // a < b
            if (aValue >= cValue) {
                // c <= a < b
                return a;
            } else if (bValue < cValue) {
                // a < b < c
                return b;
            }
        } else {
            // b <= a
            if (aValue < cValue) {
                // b <= a < c
                return a;
            } else if (bValue >= cValue) {
                // c <= b <= a
                return b;
            }
        }
        // a < c <= b || b < c <= a
        return c;
    }
}
