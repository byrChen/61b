import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByN {
    // You must use this CharacterComparator and not instantiate
    // new ones, or the autograder might be upset.
    static CharacterComparator offByN = new OffByN(4);

    // Your tests go here.
    @Test
    public void equalCharsTest() {
        assertFalse(offByN.equalChars('a', 'a'));
        assertTrue(offByN.equalChars('a', 'e'));
        assertTrue(offByN.equalChars('e', 'a'));
    }
}
