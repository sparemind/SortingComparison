public class HeapSort extends Sort {
    private static final int DEGREE = 2;

    /**
     * {@inheritDoc}
     */
    public HeapSort(SimpleGrid grid, SubGrid subGrid, int column, int delay) {
        super(grid, subGrid, column, delay);
    }

    /**
     * Heap Sort.
     */
    @Override
    public void run() {
        heapify();

        int end = length() - 1;
        while (end > 0) {
            swap(end, 0);
            end--;
            siftDown(0, end);

            delay();
        }
    }

    /**
     * Returns the array index of the parent of a given child.
     *
     * @param childIndex The index to get the parent index of.
     * @return The parent index of the given child index.
     */
    private int parentIndex(int childIndex) {
        return (childIndex - 1) / DEGREE;
    }

    private void heapify() {
        int start = parentIndex(length() - 1);

        while (start >= 0) {
            siftDown(start, length() - 1);
            start--;
        }
    }

    private void siftDown(int start, int end) {
        int root = start;

        while (root * DEGREE + 1 <= end) {
            int child = root * DEGREE + 1;
            int swap = root;

            if (get(swap) < get(child)) {
                swap = child;
            }

            if (child + 1 <= end && get(swap) < get(child + 1)) {
                swap = child + 1;
            }
            if (swap == root) {
                return;
            } else {
                swap(root, swap);
                root = swap;
            }

            delay();
        }
    }
}
