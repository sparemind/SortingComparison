public class Sort extends Thread {
    protected SimpleGrid grid;
    protected int column;

    private int delay;

    /**
     * Create a new Sort that will operate on the given column of the given
     * grid.
     *
     * @param grid   The grid to operate on.
     * @param column The column of the grid to operate on.
     * @param delay  The delay between sorting steps, in milliseconds.
     */
    public Sort(SimpleGrid grid, int column, int delay) {
        this.grid = grid;
        this.column = column;
        this.delay = delay;
    }

    /**
     * Swaps two elements in the column.
     *
     * @param i The index of the first element.
     * @param j The index of the second element.
     */
    protected void swap(int i, int j) {
        int tmp = this.grid.get(this.column, i);
        this.grid.set(this.column, i, this.grid.get(this.column, j));
        this.grid.set(this.column, j, tmp);
    }

    /**
     * Delays the current thread for the set amount.
     */
    protected void delay() {
        try {
            Thread.sleep(this.delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
