/**
 *  Tests calcForceExertedByXY
 */
public class TestCalcForceExertedByXY {

    /**
     *  Tests calcForceExertedByXY.
     */
    public static void main(String[] args) {
        checkCalcForceExertedByXY();
    }


    /** Checks whether two doubles are approximately equal.
     *  @param  expected    Expected double
     *  @param  actual      Double received
     *  @param  eps         Tolerance for the double comparison.
     */
    private static boolean approxEqual(double actual, double expected, double eps) {
        if (Double.isNaN(actual) || Double.isInfinite(actual)) {
            return false;
        }
        return Math.abs(expected - actual) <= eps * Math.max(expected, actual);
    }

    /**
     *  Checks whether or not two Doubles are equal and prints the result.
     *
     *  @param  expected    Expected double
     *  @param  actual      Double received
     *  @param  label       Label for the 'test' case
     *  @param  eps         Tolerance for the double comparison.
     */
    private static void checkEquals(double actual, double expected, String label, double eps) {
        if (approxEqual(actual, expected, eps)) {
            System.out.println("PASS: " + label
                    + ": Expected " + expected + " and you gave " + actual);
        } else {
            System.out.println("FAIL: " + label
                    + ": Expected " + expected + " and you gave " + actual);
            if (approxEqual(actual, expected, eps)) {
                System.out.println(
                        "      Hint: Your answer is exactly opposite of the correct answer."
                );
            }
        }
    }

    /**
     *  Checks the Body class to make sure calcForceExertedByXY works.
     */
    private static void checkCalcForceExertedByXY() {
        System.out.println("Checking calcForceExertedByX and calcForceExertedByY...");

        Body b1 = new Body(1.0, 1.0, 3.0, 4.0, 5.0, "jupiter.gif");
        Body b2 = new Body(2.0, 1.0, 3.0, 4.0, 4e11, "jupiter.gif");
        Body b3 = new Body(4.0, 5.0, 3.0, 4.0, 5.0, "jupiter.gif");

        checkEquals(b1.calcForceExertedByX(b2), 133.4, "calcForceExertedByX()", 0.01);
        checkEquals(b1.calcForceExertedByX(b3), 4.002e-11, "calcForceExertedByX()", 0.01);
//        checkEquals(b1.calcForceExertedByX(b3), 8.004e-12, "calcForceExertedByX()", 0.01);
        checkEquals(b1.calcForceExertedByY(b2), 0.0, "calcForceExertedByY()", 0.01);
        checkEquals(b1.calcForceExertedByY(b3), 5.336e-11, "calcForceExertedByY()", 0.01);
//        checkEquals(b1.calcForceExertedByY(b3), 1.0672e-11, "calcForceExertedByY()", 0.01);
    }
}
