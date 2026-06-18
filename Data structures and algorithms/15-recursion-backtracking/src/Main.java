import java.util.ArrayList;
import java.util.List;

public class Main {
    static List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        backtrack(nums, 0, new ArrayList<>(), result);
        return result;
    }

    static void backtrack(int[] nums, int index, List<Integer> path, List<List<Integer>> result) {
        if (index == nums.length) {
            result.add(new ArrayList<>(path));
            return;
        }

        backtrack(nums, index + 1, path, result);

        path.add(nums[index]);
        backtrack(nums, index + 1, path, result);
        path.remove(path.size() - 1);
    }

    public static void main(String[] args) {
        System.out.println(subsets(new int[]{1, 2, 3}));
    }
}

