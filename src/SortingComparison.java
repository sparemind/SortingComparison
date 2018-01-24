import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SortingComparison {
    private static final int GRID_WIDTH = 50;
    private static final int GRID_HEIGHT = 50;
    private static final int CELL_SIZE = 10;
    private static final int DELAY = 100;

    private SimpleGrid grid;
    private ColorScheme colorScheme;
    private Sort[] sorts;
    private volatile boolean doSort;

    /**
     * Creates a new sorting comparison visualizes with the given color scheme.
     *
     * @param colorScheme The color scheme to use.
     */
    public SortingComparison(ColorScheme colorScheme) {
        this.grid = new SimpleGrid(GRID_WIDTH, GRID_HEIGHT, CELL_SIZE, 0, "Sorting Algorithm Comparison");
        this.colorScheme = colorScheme;
        this.sorts = new Sort[GRID_WIDTH];
        this.doSort = false;

        for (int i = 0; i < GRID_HEIGHT; i++) {
            this.grid.setColor(i, valueToColor(i, 0, GRID_HEIGHT));
            this.grid.fillRow(i, i);
        }

        initGui();
    }

    /**
     * Initializes the user controls and interface.
     */
    private void initGui() {
        JFrame frame = this.grid.getFrame();
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(10, 1));

        // Reset button
        // JButton resetButton = new JButton("Reset");
        // resetButton.addActionListener(e -> reset());
        // controlPanel.add(resetButton);

        JButton selectionSortButton = new JButton("Selection Sort");
        selectionSortButton.addActionListener(e -> {
            this.doSort = true;

            for (int i = 0; i < GRID_WIDTH; i++) {
                this.sorts[i] = new SelectionSort(this.grid, i, DELAY);
            }
        });
        controlPanel.add(selectionSortButton);

        frame.add(controlPanel, BorderLayout.EAST);
        frame.pack();
        frame.setLocationRelativeTo(null);
    }

    /**
     * Converts a value to a color depending on the set color scheme.
     *
     * @param value    The value to convert to a color.
     * @param minValue The minimum possible value a value can be.
     * @param maxValue The maximum possible value a value can be.
     * @return The Color representation of this value in the current color
     * scheme.
     * @throws IllegalArgumentException If the minimum value is greater than or
     *                                  equal to the maximum value. If the value
     *                                  does not fall in the range between the
     *                                  min and max values.
     */
    private Color valueToColor(int value, int minValue, int maxValue) {
        if (minValue >= maxValue) {
            throw new IllegalArgumentException("Minimum value must be less than maximum value.");
        }
        if (value < minValue || value > maxValue) {
            throw new IllegalArgumentException("Value must be within min and max range.");
        }

        int range = maxValue - minValue;
        float trueValue = ((float) (value - minValue)) / range;

        switch (this.colorScheme) {
            case RAINBOW:
                return new Color(Color.HSBtoRGB(trueValue, 1.f, 1.f));
        }

        return Color.MAGENTA;
    }

    /**
     * Shuffles the data to sort according to the given shuffle type.
     *
     * @param shuffleType How to shuffle the data
     */
    private void shuffle(ShuffleType shuffleType) {
        List<Integer> columnValues = new ArrayList<>();
        switch (shuffleType) {
            case RANDOM:
                for (int i = 0; i < GRID_HEIGHT; i++) {
                    columnValues.add(i);
                }
                for (int x = 0; x < GRID_WIDTH; x++) {
                    Collections.shuffle(columnValues);

                    for (int y = 0; y < GRID_HEIGHT; y++) {
                        this.grid.set(x, y, columnValues.get(y));
                    }
                }
                break;
            case REVERSE_SORTED:
                for (int i = 0; i < GRID_HEIGHT; i++) {
                    columnValues.add(GRID_HEIGHT - 1 - i);
                }
                for (int x = 0; x < GRID_WIDTH; x++) {
                    for (int y = 0; y < GRID_HEIGHT; y++) {
                        this.grid.set(x, y, columnValues.get(y));
                    }
                }
                break;
        }
    }

    /**
     * Begins the comparison application and enables running.
     */
    public void run() {
        while (true) {
            if (this.doSort) {
                sort();
                this.doSort = false;
            }
        }
    }

    /**
     * Runs a sort using the currently set sorting algorithm.
     */
    private void sort() {
        shuffle(ShuffleType.RANDOM);

        // Start sorts
        for (Sort sort : this.sorts) {
            sort.start();
        }
        // Wait for all sorts to finish
        for (Sort sort : this.sorts) {
            try {
                sort.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * The possible color schemes of the visualization.
     */
    public enum ColorScheme {
        RAINBOW
    }

    /**
     * How the data is initially shuffled.
     */
    private enum ShuffleType {
        RANDOM, REVERSE_SORTED
    }
}
