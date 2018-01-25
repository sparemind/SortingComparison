public class MergeSortTopDown extends Sort {
    /**
     * {@inheritDoc}
     */
    public MergeSortTopDown(SimpleGrid grid, SubGrid subGrid, int column, int delay) {
        super(grid, subGrid, column, delay);
    }

    /**
     * (Top Down) Merge Sort.
     */
    @Override
    public void run() {
        int[] source = new int[length()];
        int[] work = new int[length()];
        for (int i = 0; i < work.length; i++) {
            source[i] = get(i);
            work[i] = get(i);
        }
        split(work, 0, length(), source);
    }

    /**
     * TODO
     *
     * @param source
     * @param start
     * @param end
     * @param work
     */
    private void split(int[] source, int start, int end, int[] work) {
        if (end - start < 2) {
            return;
        }
        int middle = (end + start) / 2;
        split(work, start, middle, source);
        split(work, middle, end, source);

        merge(source, start, middle, end, work);
    }

    /**
     * TODO
     *
     * @param source
     * @param start
     * @param middle
     * @param end
     * @param work
     */
    private void merge(int[] source, int start, int middle, int end, int[] work) {
        int i = start;
        int j = middle;

        for (int k = start; k < end; k++) {
            if (i < middle && (j >= end || source[i] <= source[j])) {
                work[k] = source[i];
                i++;
            } else {
                work[k] = source[j];
                j++;
            }
            updateGrid(work, start, end);
            delay();
        }
    }

    /**
     * TODO
     *
     * @param values
     * @param start
     * @param end
     */
    private void updateGrid(int[] values, int start, int end) {
        for (int i = start; i < end; i++) {
            set(i, values[i]);
        }
    }
}
