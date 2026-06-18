import java.util.Arrays;

public class Main {
    static int coinChange(int[] coins, int amount) {
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, amount + 1);
        dp[0] = 0;

        for (int value = 1; value <= amount; value++) {
            for (int coin : coins) {
                if (value >= coin) {
                    dp[value] = Math.min(dp[value], dp[value - coin] + 1);
                }
            }
        }

        return dp[amount] > amount ? -1 : dp[amount];
    }

    public static void main(String[] args) {
        System.out.println(coinChange(new int[]{1, 2, 5}, 11));
    }
}

