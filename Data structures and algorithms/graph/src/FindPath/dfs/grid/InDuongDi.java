import java.util.*;

public class Main {
    static char[][] grid = {
            {'S', '.', '.', '#'},
            {'#', '.', '.', '.'},
            {'.', '.', '#', 'E'}
    };

    static int rows = grid.length;
    static int cols = grid[0].length;

    static boolean[][] visited = new boolean[rows][cols];

    static int[] dr = {-1, 1, 0, 0};
    static int[] dc = {0, 0, -1, 1};

    static List<int[]> path = new ArrayList<>();

    static boolean dfs(int r, int c) {
        if (r < 0 || r >= rows || c < 0 || c >= cols) {
            return false;
        }

        if (grid[r][c] == '#' || visited[r][c]) {
            return false;
        }

        visited[r][c] = true;
        path.add(new int[]{r, c});

        if (grid[r][c] == 'E') {
            return true;
        }

        for (int i = 0; i < 4; i++) {
            int nr = r + dr[i];
            int nc = c + dc[i];

            if (dfs(nr, nc)) {
                return true;
            }
        }

        path.remove(path.size() - 1);
        return false;
    }

    public static void main(String[] args) {
        boolean found = dfs(0, 0);

        if (found) {
            System.out.println("Đường đi:");

            for (int[] cell : path) {
                System.out.println("(" + cell[0] + ", " + cell[1] + ")");
            }
        } else {
            System.out.println("Không có đường đi");
        }
    }
}