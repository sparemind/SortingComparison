import java.util.LinkedList;
import java.util.Queue;

public class MergeSort extends Sort {
    /**
     * {@inheritDoc}
     */
    public MergeSort(SimpleGrid grid, SubGrid subGrid, int column, int delay) {
        super(grid, subGrid, column, delay);
    }

    /**
     * Merge Sort.
     */
    @Override
    public void run() {
        Queue<Integer> list = new LinkedList<>();
        for (int i = 0; i < length(); i++) {
            list.add(get(i));
        }
        list = mergeSort(list);
        updateGrid(list);
    }

    /**
     * TODO
     *
     * @param list
     */
    private void updateGrid(Queue<Integer> list) {
        for (int i = 0; i < list.size(); i++) {
            int next = list.remove();
            set(i, next);
            list.add(next);
        }
    }

    /**
     * TODO
     *
     * @param list
     * @return
     */
    private Queue<Integer> mergeSort(Queue<Integer> list) {
        if (list.size() <= 1) {
            return list;
        }

        Queue<Integer> left = new LinkedList<>();
        Queue<Integer> right = new LinkedList<>();

        int originalSize = list.size();
        for (int i = 0; i < originalSize; i++) {
            int next = list.remove();
            if (i < originalSize / 2) {
                left.add(next);
            } else {
                right.add(next);
            }
        }

        left = mergeSort(left);
        right = mergeSort(right);

        return merge(left, right);
    }

    /**
     * TODO
     *
     * @param left
     * @param right
     */
    private Queue<Integer> merge(Queue<Integer> left, Queue<Integer> right) {
        Queue<Integer> result = new LinkedList<>();

        // Merge left and right
        while (!left.isEmpty() && !right.isEmpty()) {
            if (left.peek() <= right.peek()) {
                result.add(left.remove());
            } else {
                result.add(right.remove());
            }
            updateGrid(result);
            delay();
        }

        // Add left over elements to the end
        while (!left.isEmpty()) {
            result.add(left.remove());
        }
        while (!right.isEmpty()) {
            result.add(right.remove());
        }

        return result;
    }
}
