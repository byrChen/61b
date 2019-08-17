public class Palindrome {

    public Deque<Character> wordToDeque(String word) {

        Deque<Character> deQue = new LinkedListDeque<>();
        for (int i = word.length() - 1; i >= 0; i--) {
            deQue.addFirst(word.charAt(i));
        }
        return deQue;
    }

    public boolean isPalindrome(String word) {
        if (word.length() == 1 || word.length() == 0) {
            return true;
        }

        for (int i = 0; i < (word.length() / 2); i++) {
            if (word.charAt(i) != word.charAt(word.length() - i - 1)) {
                return false;
            }
        }

        return true;

        // use wordToDeque
//        Deque<Character> d = wordToDeque(word);
//        String reverse = "";
//        for (int i = 0; i < word.length(); i++) {
//            reverse += d.removeLast();
//        }
//        return reverse.equals(word);
    }

    public boolean isPalindrome(String s, CharacterComparator cc) {

        if (s.length() == 0 || s.length() == 1) {
            return true;
        }

        for (int i = 0; i < s.length() / 2; i++) {
            if (!cc.equalChars(s.charAt(i), s.charAt(s.length() - i - 1))) {
                return false;
            }
        }

        return true;
    }
}
