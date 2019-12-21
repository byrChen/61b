import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Stack;

import java.util.Comparator;

public class QuickSort {
    /**
     * Returns a new queue that contains the given queues catenated together.
     *
     * The items in q2 will be catenated after all of the items in q1.
     *
     * @param q1  A Queue of items
     * @param q2  A Queue of items
     * @return    A Queue containing the items of 
     *            q1 followed by the items of q2.
     */
    private static <Item extends Comparable> Queue<Item> catenate(Queue<Item> q1, Queue<Item> q2) {
        Queue<Item> catenated = new Queue<Item>();
        for (Item item : q1) {
            catenated.enqueue(item);
        }
        for (Item item: q2) {
            catenated.enqueue(item);
        }
        return catenated;
    }

    /**
     * Returns a random item from the given queue.
     *
     * @param items  A Queue of items
     * @return       A random item from items
     */
    private static <Item extends Comparable> Item getRandomItem(Queue<Item> items) {
        int pivotIndex = (int) (Math.random() * items.size());
        Item pivot = null;
        // Walk through the queue to find the item at the given index.
        for (Item item : items) {
            if (pivotIndex == 0) {
                pivot = item;
                break;
            }
            pivotIndex--;
        }
        return pivot;
    }

    /**
     * Partitions the given unsorted queue by pivoting on the given item.
     *
     * @param unsorted  A Queue of unsorted items
     * @param pivot     The item to pivot on
     * @param less      An empty Queue. When the function completes, this queue will contain
     *                  all of the items in unsorted that are less than the given pivot.
     * @param equal     An empty Queue. When the function completes, this queue will contain
     *                  all of the items in unsorted that are equal to the given pivot.
     * @param greater   An empty Queue. When the function completes, this queue will contain
     *                  all of the items in unsorted that are greater than the given pivot.
     */
    private static <Item extends Comparable> void partition(
            Queue<Item> unsorted, Item pivot,
            Queue<Item> less, Queue<Item> equal, Queue<Item> greater) {
        // Your code here!
        for (Item item : unsorted) {
            int cmp = item.compareTo(pivot);
            if      (cmp > 0) greater.enqueue(item);
            else if (cmp < 0) less.enqueue(item);
            else              equal.enqueue(item);
        }
    }

    public static <Item extends Comparable> Queue<Item> quickSortR(
            Queue<Item> items) {
        if (items.size() <= 1) return items;
        Queue<Item> less = new Queue<>();
        Queue<Item> equal = new Queue<>();
        Queue<Item> greater = new Queue<>();
        partition(items, getRandomItem(items), less, equal, greater);
        less = quickSortR(less);
        greater = quickSortR(greater);
        return catenate(catenate(less, equal), greater);
    }

    /**
     * Returns a Queue that contains the given items sorted from least to greatest.
     *
     * @param items  A Queue of possibly unsorted items
     * @return       A Queue of sorted items
     */
    public static <Item extends Comparable> Queue<Item> quickSortI(
            Queue<Item> items) {
        // Your code here!
        // Iterative with stack
        Queue<Queue<Item>> sortedQueues = new Queue<>();
        Stack<Queue<Item>> stack = new Stack<>();

        stack.push(items);
        while (!stack.isEmpty()) {
            Queue<Item> currentQueue = stack.pop();
            Queue<Item> less = new Queue<>();
            Queue<Item> equal = new Queue<>();
            Queue<Item> greater = new Queue<>();

            partition(currentQueue, getRandomItem(currentQueue), less, equal, greater);

            if (less.isEmpty() && greater.isEmpty()) {
                sortedQueues.enqueue(currentQueue);
                continue;
            }

            if (!greater.isEmpty()) {
                stack.push(greater);
            }

            stack.push(equal);

            if (!less.isEmpty()) {
                stack.push(less);
            }
        }

        Queue<Item> sortedItems = new Queue<>();
        for (Queue<Item> q : sortedQueues) {
            while (!q.isEmpty()) {
                sortedItems.enqueue(q.dequeue());
            }
        }

        return sortedItems;
    }
}
