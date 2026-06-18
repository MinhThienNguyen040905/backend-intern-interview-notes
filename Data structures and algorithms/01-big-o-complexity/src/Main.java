public class Main {
    static int linearSum(int[] nums) {
        int sum = 0;
        for (int num : nums) {
            sum += num;
        }
        return sum;
    }

    static boolean binarySearch(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] == target) return true;
            if (nums[mid] < target) left = mid + 1;
            else right = mid - 1;
        }

        return false;
    }

    public static void main(String[] args) {
        int[] nums = {1, 2, 3, 4, 5, 6, 7};
        System.out.println("Sum O(n): " + linearSum(nums));
        System.out.println("Search O(log n): " + binarySearch(nums, 5));
    }
}

