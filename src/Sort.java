public class Sort extends Thread {
    protected SubGrid subGrid;
    protected int column;

    private SimpleGrid grid;
    private int delay;

    /**
     * Create a new Sort that will operate on the given column of the given
     * grid.
     *
     * @param grid   The grid to operate on.
     * @param column The column of the grid to operate on.
     * @param delay  The delay between sorting steps, in milliseconds.
     */
    public Sort(SimpleGrid grid, SubGrid subGrid, int column, int delay) {
        this.grid = grid;
        this.subGrid = subGrid;
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
        int tmp = get(i);
        set(i, get(j));
        set(j, tmp);
    }

    /**
     * TODO
     *
     * @return
     */
    protected int length() {
        return this.subGrid.getHeight();
    }

    /**
     * TODO
     *
     * @param index
     * @return
     */
    protected int get(int index) {
        return this.grid.get(this.subGrid.convert(this.column, index));
    }

    /**
     * TODO
     *
     * @param index
     * @param value
     */
    protected void set(int index, int value) {
        this.grid.set(this.subGrid.convert(this.column, index), value);
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
