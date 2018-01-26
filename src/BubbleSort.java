public class BubbleSort extends Sort {
    /**
     * {@inheritDoc}
     */
    public BubbleSort(SimpleGrid grid, SubGrid subGrid, int column, int delay) {
        super(grid, subGrid, column, delay);
    }

    /**
     * Bubble Sort.
     */
    @Override
    public void run() {
        int n = length();
        boolean swapped = true;
        // Repeat until no more changes can be made
        while (swapped) {
            swapped = false;
            // Advance up the array, swapping any elements that are out of order
            for (int i = 1; i < n; i++) {
                if (get(i - 1) > get(i)) {
                    swap(i - 1, i);
                    swapped = true;
                }
            }
            // After each iteration the next greatest element has been moved
            // to the top, so we can decrease n since we know everything at and
            // above it is already sorted.
            n--;

            delay();
        }
    }
}
