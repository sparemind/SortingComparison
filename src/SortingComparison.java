import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SortingComparison {
    public enum ColorScheme {
        RAINBOW
    }

    private enum ShuffleType {
        RANDOM, REVERSE_SORTED
    }

    private static final int GRID_WIDTH = 60;
    private static final int GRID_HEIGHT = 60;
    private static final int CELL_SIZE = 10;
    private SimpleGrid grid;
    private ColorScheme colorScheme;

    public SortingComparison(ColorScheme colorScheme) {
        this.colorScheme = colorScheme;
        grid = new SimpleGrid(GRID_WIDTH, GRID_HEIGHT, CELL_SIZE, 0, "Sorting Algorithm Comparison");

        for(int i = 0; i < GRID_HEIGHT; i++) {
            grid.setColor(i, valueToColor(i, 0, GRID_HEIGHT));
            grid.fillRow(i, i);
        }
    }

    private Color valueToColor(int value, int minValue, int maxValue) {
        if(minValue >= maxValue) {
            throw new IllegalArgumentException("Minimum value must be less than maximum value.");
        }
        if(value < minValue || value > maxValue) {
            throw new IllegalArgumentException("Value must be within min and max range.");
        }

        int range = maxValue - minValue;
        float trueValue = ((float)(value - minValue)) / range;

        switch(colorScheme) {
            case RAINBOW:
                return new Color(Color.HSBtoRGB(trueValue, 1.f, 1.f));
        }

        return Color.MAGENTA;
    }

    private void shuffle(ShuffleType shuffleType) {
        List<Integer> columnValues = new ArrayList<>();
        switch (shuffleType) {
            case RANDOM:
                for(int i = 0; i < GRID_HEIGHT; i++) {
                    columnValues.add(i);
                }
                for(int x = 0; x < GRID_WIDTH; x++) {
                    Collections.shuffle(columnValues);
                    for(int y = 0; y < GRID_HEIGHT; y++) {
                        grid.set(x, y, columnValues.get(y));
                    }
                }
                break;
            case REVERSE_SORTED:
                break;
        }
    }

    public void run() {
        shuffle(ShuffleType.RANDOM);
        while (true) {

        }
    }
}
