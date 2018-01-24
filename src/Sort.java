public class Sort extends Thread {
    protected SimpleGrid grid;
    protected int column;

    public Sort(SimpleGrid grid, int column) {
        this.grid = grid;
        this.column = column;
    }

    /**
     * Swaps two elements in the column.
     *
     * @param i The index of the first element.
     * @param j The index of the second element.
     */
    protected void swap(int i, int j) {
        int tmp = grid.get(column, i);
        grid.set(column, i, grid.get(column, j));
        grid.set(column, j, tmp);
    }

    /**
     * Delays the current thread.
     */
    protected void delay() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
