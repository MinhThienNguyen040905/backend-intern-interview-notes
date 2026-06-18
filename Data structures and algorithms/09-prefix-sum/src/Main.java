public class Main {
    static class NumArray {
        private final int[] prefix;

        NumArray(int[] nums) {
            prefix = new int[nums.length + 1];
            for (int i = 0; i < nums.length; i++) {
                prefix[i + 1] = prefix[i] + nums[i];
            }
        }

        int sumRange(int left, int right) {
            return prefix[right + 1] - prefix[left];
        }
    }

    public static void main(String[] args) {
        NumArray arr = new NumArray(new int[]{1, 2, 3, 4, 5});
        System.out.println(arr.sumRange(1, 3));
    }
}

