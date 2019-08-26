import static org.junit.Assert.*;
import org.junit.Test;

public class TestArrayDequeGold {

    private StudentArrayDeque<Integer> stu = new StudentArrayDeque<>();
    private ArrayDequeSolution<Integer> sol = new ArrayDequeSolution<>();
    private String msg = "\n";
    private int size = 0;

    @Test
    public void testDeque() {
        // test addFirst
        for (int i = 0; i < 100; i++) {
            int flag = StdRandom.uniform(4);
            int random = StdRandom.uniform(10);
            switch (flag) {
                case 0:
                    size += 1;
                    sol.addFirst(random);
                    stu.addFirst(random);
                    msg = msg + "addFirst(" + random +")\n";
//                    assertEquals(msg, sol.get(0), stu.get(0));
                    break;
                case 1:
                    size += 1;
                    sol.addLast(random);
                    stu.addLast(random);
                    msg = msg + "addLast(" + random +")\n";
//                    assertEquals(msg, (Integer) random, stu.get(size - 1));
//                    assertEquals(msg, (Integer) random, sol.get(size - 1));
//                    assertEquals(msg, sol.get(size - 1), stu.get(size - 1));
                    break;
                case 2:
                    if (size == 0) {
                        break;
                    }
                    size -= 1;
                    msg = msg + "removeFirst()\n";
                    assertEquals(msg, sol.removeFirst(), stu.removeFirst());
                    break;
                case 3:
                    if (size == 0) {
                        break;
                    }
                    size -= 1;
                    msg = msg + "removeLast()\n";
                    assertEquals(msg, sol.removeLast(), stu.removeLast());
                    break;
            }
        }
    }
}
