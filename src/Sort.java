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
     * Returns the height of the column this sort is sorting.
     *
     * @return The height of the column this sort is sorting.
     */
    protected int length() {
        return this.subGrid.getHeight();
    }

    /**
     * Returns the value at a given index in this sort's column.
     *
     * @param index The index of the element in this sort's column to get.
     * @return The element at the given index in this sort's column.
     */
    protected int get(int index) {
        return this.grid.get(this.subGrid.convert(this.column, index));
    }

    /**
     * Sets a given index in this sort's column to the given value.
     *
     * @param index The index of the column to set.
     * @param value The value to set the index to.
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
