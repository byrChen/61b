import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }
    @Test
    public void testisPalindrome() {
        assertTrue(palindrome.isPalindrome("a"));
        assertTrue(palindrome.isPalindrome(""));
        assertTrue(palindrome.isPalindrome("aka"));
        assertFalse(palindrome.isPalindrome("akd"));

        OffByOne one = new OffByOne();
        assertTrue(palindrome.isPalindrome("a", one));
        assertTrue(palindrome.isPalindrome("", one));
        assertTrue(palindrome.isPalindrome("abab", one));
        assertTrue(palindrome.isPalindrome("flake", one));
        assertTrue(palindrome.isPalindrome("fkale", one));
        assertFalse(palindrome.isPalindrome("blake", one));
        assertFalse(palindrome.isPalindrome("Abab", one));

        OffByN n = new OffByN(4);
        assertTrue(palindrome.isPalindrome("a", n));
        assertTrue(palindrome.isPalindrome("", n));
        assertTrue(palindrome.isPalindrome("abfe", n));
        assertTrue(palindrome.isPalindrome("afbe", n));
        assertFalse(palindrome.isPalindrome("acbd", n));
        assertFalse(palindrome.isPalindrome("Afbe", n));
    }
}
