
public class OffByN implements CharacterComparator {

    private int expect;
    public OffByN(int N) {
        expect = N;
    }
    @Override
    public boolean equalChars(char x, char y) {
        int diff = (x - y) < 0 ? (y - x) : (x - y);
        return diff == expect;
    }
}
