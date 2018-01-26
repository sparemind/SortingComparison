public class MergeSortBottomUp extends Sort {
    /**
     * {@inheritDoc}
     */
    public MergeSortBottomUp(SimpleGrid grid, SubGrid subGrid, int column, int delay) {
        super(grid, subGrid, column, delay);
    }

    /**
     * (Bottom Up) Merge Sort.
     */
    @Override
    public void run() {
        // The array to sort
        int[] source = new int[length()];
        // The work array
        int[] work = new int[length()];
        for (int i = 0; i < work.length; i++) {
            source[i] = get(i);
        }

        // Beginning with "runs" of width 1, create and sort progressively
        // longer runs by doubling the width each iteration (1, 2, 4, 8, ...)
        for (int width = 1; width < length(); width *= 2) {

            // Going in, source[] has sorted runs of length "width". Now we
            // merge runs into new runs of length 2*width.
            for (int i = 0; i < length(); i += 2 * width) {
                // Merge runs: source[i, i+width) and work[i+width, i+2*width)
                // and put result in work[]. If i+width >= length(), instead
                // copy source[i, length()) to work[].
                merge(source, i, Math.min(i + width, length()), Math.min(i + 2 * width, length()), work);
            }
            // Coming out, work[] has sorted runs of length 2*width

            // Swap the arrays for the next iteration so source[] has the runs
            int[] tmp = work;
            work = source;
            source = tmp;
        }
    }

    /**
     * Merges two contiguous sorted segments into a single sorted segment.
     * <p>
     * [start, middle) is the first segment.
     * [middle, end) is the second segment.
     *
     * @param source The array with the data segments to merge. Each segment
     *               must be sorted.
     * @param start  Index of the first element in the first segment.
     * @param middle Index after the last element in the first segment and the
     *               first element in the second segment.
     * @param end    Index after the last element in the second segment.
     * @param result The array to store the results into. The sorted segments
     *               will be merged into a single segment [start, end) in this
     *               array.
     */
    private void merge(int[] source, int start, int middle, int end, int[] result) {
        // Keep track of the next element to process in each segment
        int i = start;
        int j = middle;

        // Merge the segments and store into result
        for (int k = start; k < end; k++) {
            // Sort elements from the segments into the result. If one segment
            // runs out of elements, add the rest of the elements of the other.
            if (i < middle && (j >= end || source[i] <= source[j])) {
                // If the next element in the first segment is less than the
                // next element in the second segment, add it to the result.
                result[k] = source[i];
                i++;
            } else {
                // If instead the next element in the second segment is less,
                // add it to the result
                result[k] = source[j];
                j++;
            }
            // Update visualization
            updateGrid(result, start, end);
            delay();
        }
    }

    /**
     * Updates a segment of the grid based on the corresponding values in a
     * given array.
     *
     * @param values The array with the values to update the grid with.
     * @param start  Index of the first element in the segment.
     * @param end    Index after the last element in the segment.
     */
    private void updateGrid(int[] values, int start, int end) {
        for (int i = start; i < end; i++) {
            set(i, values[i]);
        }
    }
}
