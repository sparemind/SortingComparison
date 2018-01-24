public class SelectionSort extends Sort {
    public SelectionSort(SimpleGrid grid, int column) {
        super(grid, column);
    }

    public void run() {
        for(int i = 0; i < grid.getHeight() - 1; i++) {
            int min = i;

            for(int j = i + 1; j < grid.getHeight(); j++) {
                if(grid.get(column, j) < grid.get(column, min)) {
                    min = j;
                }
            }

            if(min != i) {
                swap(i, min);
            }

            delay();
        }
    }
}
