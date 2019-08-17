
public class OffByOne implements CharacterComparator {
    @Override
    public boolean equalChars(char x, char y) {
        int diff = (x - y < 0) ? (y - x) : (x - y);
        return diff == 1;
    }
}