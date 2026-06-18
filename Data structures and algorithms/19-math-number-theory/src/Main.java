public class Main {
    static int gcd(int a, int b) {
        while (b != 0) {
            int temp = a % b;
            a = b;
            b = temp;
        }
        return Math.abs(a);
    }

    static long fastPow(long base, int exp) {
        long result = 1;
        while (exp > 0) {
            if ((exp & 1) == 1) {
                result *= base;
            }
            base *= base;
            exp >>= 1;
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(gcd(48, 18));
        System.out.println(fastPow(2, 10));
    }
}

