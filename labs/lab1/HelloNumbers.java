public class HelloNumbers {
    public static void main(String[] args) {
        int x = 1;
        int sum = 0;
        while (sum < 46) {
            System.out.print(sum + " ");
            sum = x + sum;
            x = x + 1;
        }
    }
}