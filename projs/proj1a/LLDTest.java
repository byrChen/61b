import org.junit.Test;
import static org.junit.Assert.*;

public class LLDTest {

    @Test
    public void getTest() {
        LinkedListDeque<Integer> llD = new LinkedListDeque<>();
        llD.addFirst(0);
        llD.addLast(1);
        assertEquals(1, (long) llD.removeLast());
        assertEquals(0, (long) llD.removeLast());
        llD.addLast(5);
        assertEquals(5, (long) llD.get(0));
    }

    @Test
    public void getRecursiveTest() {
        LinkedListDeque<Integer> llD = new LinkedListDeque<>();
        assertNull(llD.getRecursive(0));
        assertNull(llD.getRecursive(1));
        llD.addLast(0);
        assertEquals(0, (long) llD.getRecursive(0));
        llD.removeLast();
        assertNull(llD.getRecursive(0));
        llD.addFirst(1);
        assertEquals(1, (long) llD.getRecursive(0));
        llD.removeFirst();
        assertNull(llD.getRecursive(0));
    }
}
