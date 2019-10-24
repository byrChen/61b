package bearmaps;

import org.junit.Test;

import static org.junit.Assert.*;

public class ArrayHeapMinPQTest {
    
    @Test
    public void sanityGenericTest() {
        try {
            ArrayHeapMinPQ<String> a = new ArrayHeapMinPQ<String>();
            ArrayHeapMinPQ<String> b = new ArrayHeapMinPQ<String>();
            ArrayHeapMinPQ<Integer> c = new ArrayHeapMinPQ<Integer>();
            ArrayHeapMinPQ<Boolean> e = new ArrayHeapMinPQ<Boolean>();
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void sanityAddTest() {
        ArrayHeapMinPQ<String> a = new ArrayHeapMinPQ<>();
        try {
            a.add(null, 0.8);
            fail("No exception thrown.");
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("Items can't be null or duplicated."));
        }
        assertEquals(0, a.size());
        assertNull(a.getSmallest());
        for (int i = 0; i < 455; i++) {
            a.add("a" + i, 455 - i);
            assertEquals("a" + i, a.getSmallest());
            assertEquals(i + 1, a.size());
        }
        try {
            a.add("a0", 0.8);
            fail("No exception thrown.");
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("Items can't be null or duplicated."));
        }
    }

    @Test
    public void sanatyContainsTest() {
        ArrayHeapMinPQ<String> a = new ArrayHeapMinPQ<>();
        assertFalse(a.contains("a"));
        for (int i = 0; i < 455; i++) {
            a.add("a" + i, i);
            assertTrue(a.contains("a" + i));
        }
    }

    @Test
    public void sanityRemoveSamllestTest() {
        ArrayHeapMinPQ<String> a = new ArrayHeapMinPQ<>();
        try {
            a.removeSmallest();
            fail();
        } catch (Exception e) {
            e.getMessage().contains("Queue is empty.");
        }

        for (int i = 0; i < 455; i++) {
            a.add("a" + i, i);
        }
        for (int i = 0; i < 455; i++) {
            assertEquals("a" + i, a.removeSmallest());
            assertFalse(a.contains("a" + i));
            assertEquals(454 - i, a.size());
        }

        try {
            a.removeSmallest();
            fail();
        } catch (Exception e) {
            e.getMessage().contains("Queue is empty.");
        }
    }

    @Test
    public void sanityChangePriorityTest() {
        ArrayHeapMinPQ<String> a = new ArrayHeapMinPQ<>();
        ArrayHeapMinPQ<String> b = new ArrayHeapMinPQ<>();
        for (int i = 0; i < 455; i++) {
            a.add("a" + i, i);
            b.add("b" + i, i);
        }
        b.changePriority("b0", 455);
        for (int i = 0; i < 455; i++) {
            a.changePriority("a" + (454 - i), -1);
            assertEquals("a" + (454 - i), a.removeSmallest());
            if (i == 454) assertEquals("b0", b.removeSmallest());
            else assertEquals("b" + (i + 1), b.removeSmallest());
        }
    }
}
