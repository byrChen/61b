package es.datastructur.synthesizer;
import edu.princeton.cs.algs4.In;
import org.junit.Test;

import java.security.spec.ECField;

import static org.junit.Assert.*;

/** Tests the ArrayRingBuffer class.
 *  @author Josh Hug
 */

public class TestArrayRingBuffer {
    @Test
    public void testConstructor() {
        try {
            ArrayRingBuffer arb1 = new ArrayRingBuffer(0);
            ArrayRingBuffer arb2 = new ArrayRingBuffer(-1);
            fail();
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("capacity should > 0"));
        }
        ArrayRingBuffer<Integer> arbI = new ArrayRingBuffer<>(10);
        Integer[] expectI = new Integer[10];
        assertEquals(10, arbI.capacity());
        assertEquals(0, arbI.fillCount());
        assertEquals(0, arbI.getfirst());
        assertEquals(0, arbI.getlast());
        assertArrayEquals(expectI, arbI.getrb());
    }
    
    @Test
    public void testEnqueue() {
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer<>(5);
        for (int i = 0; i < 5; i++) {
            arb.enqueue(i+1);
            assertEquals((i+1)%5, arb.getlast());
            assertEquals(i+1, arb.fillCount());
        }

        Integer[] expect = {1, 2, 3, 4, 5};
        assertArrayEquals(expect, arb.getrb());

        try {
            arb.enqueue(6);
            fail();
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("Ring Buffer overflow"));
        }

        assertEquals(0, arb.getlast());
        assertArrayEquals(expect, arb.getrb());
    }

    @Test
    public void testDequeue() {
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer<>(5);
        try {
            arb.dequeue();
            fail();
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("Ring Buffer underflow"));
        }

        for (int i = 0; i < 5; i++) {
            arb.enqueue(i + 1);
        }

        for (int i = 0; i < 5; i++) {
            assertEquals((Integer) (i+1), arb.dequeue());
            assertEquals(4-i, arb.fillCount());
            assertEquals((i+1)%5, arb.getfirst());
        }

        Integer[] expect = new Integer[5];
        assertArrayEquals(expect, arb.getrb());
    }
}
