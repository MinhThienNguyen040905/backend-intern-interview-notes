import java.util.Arrays;

public class Main {
    static int[][] sortIntervals(int[][] intervals) {
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));
        return intervals;
    }

    public static void main(String[] args) {
        int[][] intervals = {{5, 7}, {1, 3}, {2, 4}};
        sortIntervals(intervals);

        for (int[] interval : intervals) {
            System.out.println(Arrays.toString(interval));
        }
    }
}

