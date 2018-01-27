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
        // Make a max heap
        heapify();

        int end = length() - 1;
        while (end > 0) {
            // Move the largest item to the end
            swap(end, 0);
            // "Shorten" the heap so already sorted elements stay that way
            end--;
            // Repair the heap, since a value was swapped to the top
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

    /**
     * Turns the data into a max heap using Floyd's heap construction method.
     */
    private void heapify() {
        // Begin with start as the last parent node, since it would be
        // unnecessary to sift leaf nodes on their own.
        int start = parentIndex(length() - 1);

        // Repair all sub-heaps
        while (start >= 0) {
            siftDown(start, length() - 1);
            start--;
        }
    }

    /**
     * Repair the heap from a given start index to a given end index, swapping
     * out of place elements down until they're in a proper place.
     *
     * @param start The index of the first element to start repairing the heap
     *              from.
     * @param end   The index of the last element in end repairing the heap at.
     */
    private void siftDown(int start, int end) {
        // Begin at the top of this heap
        int root = start;

        // Repeat as long as at least one child still exists
        while (root * DEGREE + 1 <= end) {
            int leftChild = root * DEGREE + 1;
            // Holds the index of the node to be swapped with root
            int swap = root;

            // If the left or right child is greater than root, set swap to
            // whichever is the greater.
            // Compare leftChild to swap
            if (get(swap) < get(leftChild)) {
                swap = leftChild;
            }
            // Compare rightChild (leftChild + 1) to swap, if it exists
            if (leftChild + 1 <= end && get(swap) < get(leftChild + 1)) {
                swap = leftChild + 1;
            }

            if (swap == root) {
                // If nothing needs to be swapped, then the heap is repaired
                return;
            } else {
                // Otherwise, swap the root down and repeat the process for the
                // next heap
                swap(root, swap);
                root = swap;
            }

            delay();
        }
    }
}
