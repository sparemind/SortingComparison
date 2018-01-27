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
        // Begin with a sorted segment of length 0. Increase this segment 1
        // element at a time by finding the smallest element in the unsorted
        // part and moving it to the end of the sorted segment.
        for (int i = 0; i < length() - 1; i++) {
            int min = i;

            // Find the smallest element in the range [i, length())
            for (int j = i + 1; j < length(); j++) {
                if (get(j) < get(min)) {
                    min = j;
                }
            }

            // Move the smallest element to the end of the sorted segment,
            // lengthening it by 1
            if (min != i) {
                swap(i, min);
            }

            delay();
        }
    }
}
