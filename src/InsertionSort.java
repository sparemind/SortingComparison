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
        // Begin with a sorted segment of length 1
        int i = 1;

        // Increase the length of this sorted segment 1 element at a time
        while (i < length()) {
            // Look at the next element
            int j = i;
            // Swap it down until it is in the proper sorted place
            while (j > 0 && get(j - 1) > get(j)) {
                swap(j, j - 1);
                j--;
            }
            // The sorted segment has now be lengthened by 1
            i++;

            delay();
        }
    }
}
