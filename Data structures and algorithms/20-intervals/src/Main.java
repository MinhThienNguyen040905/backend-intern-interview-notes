import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    static int[][] merge(int[][] intervals) {
        if (intervals.length == 0) return new int[0][0];

        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));
        List<int[]> result = new ArrayList<>();
        int[] current = intervals[0];

        for (int i = 1; i < intervals.length; i++) {
            if (intervals[i][0] <= current[1]) {
                current[1] = Math.max(current[1], intervals[i][1]);
            } else {
                result.add(current);
                current = intervals[i];
            }
        }
        result.add(current);

        return result.toArray(new int[result.size()][]);
    }

    public static void main(String[] args) {
        int[][] merged = merge(new int[][]{{1, 3}, {2, 6}, {8, 10}});
        for (int[] interval : merged) {
            System.out.println(Arrays.toString(interval));
        }
    }
}

