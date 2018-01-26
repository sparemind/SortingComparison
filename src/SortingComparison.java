import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

public class SortingComparison {
    private static final int GRID_WIDTH = 60;
    private static final int GRID_HEIGHT = 100;
    private static final int CELL_SIZE = 3;
    private static final int MARGIN_X = 3;
    private static final int MARGIN_Y = 3;
    private static final int GRIDS_HORZ = 6;
    private static final int GRIDS_VERT = 1;
    private static final int BORDER = 3;

    private SimpleGrid grid;
    private SubGrid[] subGrids;
    private List<Class<? extends Sort>> algorithms;
    private Map<Class<? extends Sort>, Integer> algorithmDelays;
    private volatile Map<SubGrid, Sort[]> sorts;
    private volatile boolean doSort;
    private ColorScheme colorScheme;
    private JComboBox[] selectionBoxes;

    /**
     * Creates a new sorting comparison visualizes with the given color scheme.
     *
     * @param colorScheme The color scheme to use.
     */
    public SortingComparison(ColorScheme colorScheme) {
        // Create main grid
        int fullWidth = MARGIN_X * 2 + GRIDS_HORZ * GRID_WIDTH + (GRIDS_HORZ - 1) * BORDER;
        int fullHeight = MARGIN_Y * 2 + GRIDS_VERT * GRID_HEIGHT + (GRIDS_VERT - 1) * BORDER;
        this.grid = new SimpleGrid(fullWidth, fullHeight, CELL_SIZE, 0, "Sorting Algorithm Comparison");
        this.grid.setGridlineColor(Color.GRAY);
        this.grid.fill(-1);

        // Create sub-grids
        this.subGrids = new SubGrid[GRIDS_HORZ * GRIDS_VERT];
        this.sorts = new IdentityHashMap<>();
        for (int i = 0; i < this.subGrids.length; i++) {
            int x = MARGIN_X + i % GRIDS_HORZ * (GRID_WIDTH + BORDER);
            int y = MARGIN_Y + i / GRIDS_HORZ * (GRID_HEIGHT + BORDER);
            this.subGrids[i] = new SubGrid(this.grid, x, y, GRID_WIDTH, GRID_HEIGHT);

            this.sorts.put(this.subGrids[i], null);
        }

        // Initialize other fields
        this.colorScheme = colorScheme;
        this.doSort = false;
        this.selectionBoxes = new JComboBox[GRIDS_HORZ * GRIDS_VERT];
        this.algorithmDelays = new HashMap<>();
        this.algorithms = new ArrayList<>();
        this.algorithms.add(BubbleSort.class);
        this.algorithms.add(SelectionSort.class);
        this.algorithms.add(InsertionSort.class);
        this.algorithms.add(MergeSortTopDown.class);
        this.algorithms.add(MergeSortBottomUp.class);
        this.algorithms.add(QuickSort.class);
        this.algorithms.add(None.class);
        this.algorithmDelays.put(BubbleSort.class, 70);
        this.algorithmDelays.put(SelectionSort.class, 70);
        this.algorithmDelays.put(InsertionSort.class, 68);
        this.algorithmDelays.put(MergeSortTopDown.class, 10);
        this.algorithmDelays.put(MergeSortBottomUp.class, 10);
        this.algorithmDelays.put(QuickSort.class, 12);
        this.algorithmDelays.put(None.class, 0);

        // Set grid colors
        this.grid.setColor(-1, Color.GRAY);
        for (int i = 0; i < GRID_HEIGHT; i++) {
            this.grid.setColor(i, valueToColor(i, 0, GRID_HEIGHT));
        }

        initGui();
        for (SubGrid subGrid : this.subGrids) {
            shuffle(subGrid, ShuffleType.RANDOM);
        }
    }

    /**
     * Initializes the user controls and interface.
     */
    private void initGui() {
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());

        JPanel algorithmsPanel = new JPanel();
        algorithmsPanel.setLayout(new GridLayout(1, GRIDS_HORZ * GRIDS_VERT));
        topPanel.add(algorithmsPanel, BorderLayout.NORTH);

        String[] algorithmOptions = new String[this.algorithms.size()];
        for (int i = 0; i < this.algorithms.size(); i++) {
            algorithmOptions[i] = this.algorithms.get(i).getCanonicalName();
        }

        for (int i = 0; i < this.subGrids.length; i++) {
            JComboBox<String> selectionBox = new JComboBox<>(algorithmOptions);
            // If there are more grids than algorithms, assign any extras to be
            // "None", which is the last in the this.algorithms list.
            selectionBox.setSelectedIndex(i < this.algorithms.size() ? i : this.algorithms.size() - 1);
            algorithmsPanel.add(selectionBox);
            this.selectionBoxes[i] = selectionBox;
        }

        JPanel controlPanel = new JPanel();
        JButton sortButton = new JButton("Sort");
        sortButton.addActionListener(e -> SortingComparison.this.doSort = true);
        controlPanel.add(sortButton);

        JFrame frame = this.grid.getFrame();
        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(controlPanel, BorderLayout.SOUTH);
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
            case BLUE:
                return new Color(Color.HSBtoRGB(0.55f, 0.6f, 1 - trueValue));
            case RED:
                return new Color(Color.HSBtoRGB(0.f, 0.6f, 1 - trueValue));
            case GREEN:
                return new Color(Color.HSBtoRGB(0.3f, 0.6f, 1 - trueValue));
        }

        return Color.MAGENTA;
    }

    /**
     * Shuffles a grid's data according to the given shuffle type.
     *
     * @param subGrid     The grid to shuffle.
     * @param shuffleType How to shuffle the data
     */
    private void shuffle(SubGrid subGrid, ShuffleType shuffleType) {
        List<Integer> columnValues = new ArrayList<>();
        switch (shuffleType) {
            case RANDOM:
                for (int i = 0; i < GRID_HEIGHT; i++) {
                    columnValues.add(i);
                }
                for (int x = 0; x < GRID_WIDTH; x++) {
                    Collections.shuffle(columnValues);

                    for (int y = 0; y < GRID_HEIGHT; y++) {
                        this.grid.set(subGrid.convert(x, y), columnValues.get(y));
                    }
                }
                break;
            case SORTED:
                for (int i = 0; i < GRID_HEIGHT; i++) {
                    columnValues.add(i);
                }
                for (int x = 0; x < GRID_WIDTH; x++) {
                    for (int y = 0; y < GRID_HEIGHT; y++) {
                        this.grid.set(subGrid.convert(x, y), columnValues.get(y));
                    }
                }
                break;
            case REVERSE_SORTED:
                for (int i = 0; i < GRID_HEIGHT; i++) {
                    columnValues.add(GRID_HEIGHT - 1 - i);
                }
                for (int x = 0; x < GRID_WIDTH; x++) {
                    for (int y = 0; y < GRID_HEIGHT; y++) {
                        this.grid.set(subGrid.convert(x, y), columnValues.get(y));
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
        for (SubGrid subGrid : this.subGrids) {
            shuffle(subGrid, ShuffleType.RANDOM);
        }

        for (int i = 0; i < GRIDS_HORZ * GRIDS_VERT; i++) {
            initializeSorters(i);
        }

        // Start sorts
        for (SubGrid subGrid : this.sorts.keySet()) {
            for (Sort sort : this.sorts.get(subGrid)) {
                sort.start();
            }
        }
        // Wait for all sorts to finish
        for (SubGrid subGrid : this.sorts.keySet()) {
            for (Sort sort : this.sorts.get(subGrid)) {
                try {
                    sort.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void initializeSorters(int index) {
        JComboBox selectionBox = this.selectionBoxes[index];
        SubGrid subGrid = this.subGrids[index];

        int selectedAlgorithmIndex = selectionBox.getSelectedIndex();
        Class<? extends Sort> algorithm = this.algorithms.get(selectedAlgorithmIndex);
        Constructor<?> constructor = algorithm.getConstructors()[0];

        Sort[] sorters = new Sort[GRID_WIDTH];
        for (int i = 0; i < sorters.length; i++) {
            try {
                sorters[i] = (Sort) constructor.newInstance(this.grid, subGrid, i, this.algorithmDelays.get(algorithm));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        this.sorts.put(subGrid, sorters);
    }

    /**
     * The possible color schemes of the visualization.
     */
    public enum ColorScheme {
        RAINBOW, BLUE, RED, GREEN
    }

    /**
     * How the data is initially shuffled.
     */
    private enum ShuffleType {
        RANDOM, SORTED, REVERSE_SORTED
    }
}
