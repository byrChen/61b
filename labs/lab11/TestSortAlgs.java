import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;

import org.junit.Test;
import static org.junit.Assert.*;

public class TestSortAlgs {

    @Test
    public void testQuickSort() {
        Queue<Integer> q = new Queue<>();
        for (int i = 0; i < 455; i++) {
            q.enqueue(454 - i);
        }
        q = QuickSort.quickSortR(q);
        assertTrue(isSorted(q));
    }

    @Test
    public void testMergeSort() {
//        Queue<String> tas = new Queue<String>();
//        tas.enqueue("Joe");
//        tas.enqueue("Omar");
//        tas.enqueue("Itai");
//        Queue<String> sorted = MergeSort.mergeSort(tas);
//        assertTrue(isSorted(sorted));
        Queue<Integer> q = new Queue<>();
        for (int i = 0; i < 455; i++) {
            q.enqueue(454 - i);
        }
        q = MergeSort.mergeSort(q);
        assertTrue(isSorted(q));
    }

    /**
     * Returns whether a Queue is sorted or not.
     *
     * @param items  A Queue of items
     * @return       true/false - whether "items" is sorted
     */
    private <Item extends Comparable> boolean isSorted(Queue<Item> items) {
        if (items.size() <= 1) {
            return true;
        }
        Item curr = items.dequeue();
        Item prev = curr;
        while (!items.isEmpty()) {
            prev = curr;
            curr = items.dequeue();
            if (curr.compareTo(prev) < 0) {
                return false;
            }
        }
        return true;
    }
}
