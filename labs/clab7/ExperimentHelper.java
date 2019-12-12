import javax.swing.plaf.IconUIResource;

/**
 * Created by hug.
 */
public class ExperimentHelper {

    /** Returns the internal path length for an optimum binary search tree of
     *  size N. Examples:
     *  N = 1, OIPL: 0
     *  N = 2, OIPL: 1
     *  N = 3, OIPL: 2
     *  N = 4, OIPL: 4
     *  N = 5, OIPL: 6
     *  N = 6, OIPL: 8
     *  N = 7, OIPL: 10
     *  N = 8, OIPL: 13
     */
    public static int optimalIPL(int N) {
        if (N <= 0) return 0;
        int count = 0;
        for (int i = 1; i <=  N; i++) {
            int j = i;
            while (j >> 1 != 0) {
                count += 1;
                j = j >> 1;
            }
        }
        return count;
    }

    /** Returns the average depth for nodes in an optimal BST of
     *  size N.
     *  Examples:
     *  N = 1, OAD: 0
     *  N = 5, OAD: 1.2
     *  N = 8, OAD: 1.625
     * @return
     */
    public static double optimalAverageDepth(int N) {
        if (N <= 0) return 0;
        return ExperimentHelper.optimalIPL(N) / (double) N;
    }

    public static void randomAsymmetric(BST bst) {
        bst.deleteTakingSuccessor(bst.getRandomKey());
        while (true) {
            int item = RandomGenerator.getRandomInt(10000000);
            if (bst.contains(item)) continue;
            else {
                bst.add(item);
                break;
            }
        }
    }

    public static void randomSymmetric(BST bst) {
        bst.deleteTakingRandom(bst.getRandomKey());
        while (true) {
            int item = RandomGenerator.getRandomInt(10000000);
            if (bst.contains(item)) continue;
            else {
                bst.add(item);
                break;
            }
        }
    }
}
