import java.awt.Color;

public class SortingComparison {
    private static final int GRID_WIDTH = 10;
    private static final int GRID_HEIGHT = 10;
    private static final int CELL_SIZE = 40;
    private SimpleGrid grid;

    public SortingComparison() {
        grid = new SimpleGrid(GRID_WIDTH, GRID_HEIGHT, CELL_SIZE, 0, "Sorting Algorithm Comparison");

        for(int i = 0; i < GRID_HEIGHT; i++) {
            grid.setColor(i, valueToColor(i, 0, GRID_HEIGHT));
        }
    }

    private Color valueToColor(int value, int minValue, int maxValue) {
        return new Color(0, 0, 0);
    }

    public void run() {
        while (true) {

        }
    }
}
