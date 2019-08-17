import org.junit.Test;
import static org.junit.Assert.*;

public class ADTest {
    @Test
    public void constructorTest() {
        ArrayDeque<String> aD = new ArrayDeque<>();
        assertEquals(8,  aD.itemsLength());
        assertEquals(7, aD.nextFirst());
        assertEquals(0, aD.nextLast());
        ArrayDeque<String> newaD = new ArrayDeque<>(aD);
        assertEquals(aD.itemsLength(), newaD.itemsLength());
        assertEquals(aD.nextFirst(), newaD.nextFirst());
        assertEquals(aD.nextLast(), newaD.nextLast());
        assertArrayEquals(aD.items(), newaD.items());
        assertNotEquals(aD, newaD);
    }
    @Test
    public void addTest() {
        ArrayDeque<Integer> aD = new ArrayDeque<>();
        assertNull(aD.get(0));
        aD.addFirst(1);
        aD.addFirst(0);
        aD.addLast(2);
        Integer[] expect = {2, null, null, null, null, null, 0, 1};
        assertArrayEquals(expect, aD.items());
        assertEquals(5, aD.nextFirst());
        assertEquals(1, aD.nextLast());
        assertEquals(3, aD.size());
    }
    @Test
    public void removeTest() {
        ArrayDeque<Integer> aD = new ArrayDeque<>();
        assertNull(aD.removeFirst());
        assertNull(aD.removeLast());
        for (int i = 0; i < 7; i++) {
            aD.addLast(i);
        }
        assertEquals(0, (long) aD.removeFirst());
        assertEquals(6, (long) aD.removeLast());
        Integer[] expect = {null, 1, 2, 3, 4, 5, null, null};
        assertArrayEquals(expect, aD.items());
        assertEquals(0, aD.nextFirst());
        assertEquals(6, aD.nextLast());
        assertEquals(5, aD.size());
    }
    @Test
    public void resizeTestF() {

        /* always addLast to full */
        ArrayDeque<Integer> aD1 = new ArrayDeque<>();
        for (int i = 0; i < 8; i++) {
            aD1.addLast(i);
        }
        Integer[] expectF = {0, 1, 2, 3, 4, 5, 6, 7, null, null,
                null, null, null, null, null, null};
        assertArrayEquals(expectF, aD1.items());
        aD1.addLast(8);
        expectF[8] = 8;
        assertArrayEquals(expectF, aD1.items());
        assertEquals(15, aD1.nextFirst());
        assertEquals(9, aD1.nextLast());
        assertEquals(9, aD1.size());

        /* always addFirst to full */
        ArrayDeque<Integer> aD2 = new ArrayDeque<>();
        for (int i = 7; i >= 0; i--) {
            aD2.addFirst(i);
        }
        aD2.addLast(8);
        assertArrayEquals(aD1.items(), aD2.items());
        assertEquals(aD1.nextFirst(), aD2.nextFirst());
        assertEquals(aD1.nextLast(), aD2.nextLast());
        assertEquals(aD1.size(), aD2.size());

        /* addLast and addFirst (nextFirst = nextLast - 1) to full */
        ArrayDeque<Integer> aD3 = new ArrayDeque<>();
        for (int i = 3; i >= 0; i--) {
            aD3.addFirst(i);
        }
        for (int i = 4; i < 8; i++) {
            aD3.addLast(i);
        }
        aD3.addLast(8);
        assertArrayEquals(aD1.items(), aD3.items());
        assertEquals(aD1.nextFirst(), aD3.nextFirst());
        assertEquals(aD1.nextLast(), aD3.nextLast());
        assertEquals(aD1.size(), aD3.size());
    }

    @Test
    public void resizeTestW() {

        Integer[] expectF = {0, 1, 2, 3, 4, 5, 6, 7, 8, null,
                null, null, null, null, null, null};
        ArrayDeque<Integer> aD3 = new ArrayDeque<>();
        for (int i = 0; i < 9; i++) {
            aD3.addLast(i);
        }

        /* nextFirst = 15 to waste, copy left */
        Integer[] expectW = {0, 1, 2, null, null, null, null, null};
        ArrayDeque<Integer> aD4 = new ArrayDeque<>(aD3);
        for (int i = 0; i < 6; i++) {
            aD4.removeLast();
        }
        assertArrayEquals(expectW, aD4.items());
        assertEquals(3, aD4.size());
        assertEquals(3, aD4.nextLast());
        assertEquals(7, aD4.nextFirst());

        /* nextLast = 0 to waste, copy right */
        ArrayDeque<Integer> aD5 = new ArrayDeque<>(aD3);
        for (int i = 0; i < 3; i++) {
            aD5.addFirst(2 - i);
            expectF[15 - i] = 2 - i;
        }
        assertArrayEquals(expectF, aD5.items());
        for (int i = 0; i < 9; i++) {
            aD5.removeLast();
        }
        assertArrayEquals(expectW, aD5.items());
        assertEquals(3, aD5.size());
        assertEquals(3, aD5.nextLast());
        assertEquals(7, aD5.nextFirst());

        /* nextLast != 0 and nextFirst !=0
        and nextFirst > nextLast, copy two sides */
        ArrayDeque<Integer> aD6 = new ArrayDeque<>(aD3);
        for (int i = 0; i < 2; i++) {
            aD6.addFirst(1 - i);
            expectF[15 - i] = 1 - i;
        }
        expectF[13] = null;
        assertArrayEquals(expectF, aD6.items());
        for (int i = 0; i < 8; i++) {
            aD6.removeLast();
        }
        expectW[2] = 0;
        assertArrayEquals(expectW, aD6.items());
        assertEquals(3, aD6.size());
        assertEquals(3, aD6.nextLast());
        assertEquals(7, aD6.nextFirst());

        /* nextLast != 0 and nextFirst !=0
        and nextFirst < nextLast, copy middle */
        ArrayDeque<Integer> aD7 = new ArrayDeque<>(aD3);
        for (int i = 0; i < 6; i++) {
            aD7.removeFirst();
        }
        expectW[0] = 6;
        expectW[1] = 7;
        expectW[2] = 8;
        assertArrayEquals(expectW, aD7.items());
        assertEquals(3, aD7.size());
        assertEquals(3, aD7.nextLast());
        assertEquals(7, aD7.nextFirst());
    }
}
