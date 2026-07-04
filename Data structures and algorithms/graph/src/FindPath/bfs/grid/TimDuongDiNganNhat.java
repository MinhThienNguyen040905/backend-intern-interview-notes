import java.util.*;

public class Main {
    static char[][] grid = {
            {'S', '.', '.', '#'},
            {'#', '.', '.', '.'},
            {'.', '.', '#', 'E'}
    };

    static int rows = grid.length;
    static int cols = grid[0].length;

    static int[] dr = {-1, 1, 0, 0};
    static int[] dc = {0, 0, -1, 1};

    static class Cell {
        int r, c;

        Cell(int r, int c) {
            this.r = r;
            this.c = c;
        }
    }

    static List<Cell> bfs(int startR, int startC, int endR, int endC) {
        Queue<Cell> queue = new ArrayDeque<>();
        boolean[][] visited = new boolean[rows][cols];

        Cell[][] parent = new Cell[rows][cols];

        queue.add(new Cell(startR, startC));
        visited[startR][startC] = true;

        while (!queue.isEmpty()) {
            Cell current = queue.poll();

            if (current.r == endR && current.c == endC) {
                break;
            }

            for (int i = 0; i < 4; i++) {
                int nr = current.r + dr[i];
                int nc = current.c + dc[i];

                if (isValid(nr, nc, visited)) {
                    visited[nr][nc] = true;
                    parent[nr][nc] = current;
                    queue.add(new Cell(nr, nc));
                }
            }
        }

        if (!visited[endR][endC]) {
            return new ArrayList<>();
        }

        List<Cell> path = new ArrayList<>();

        Cell current = new Cell(endR, endC);

        while (current != null) {
            path.add(current);
            current = parent[current.r][current.c];
        }

        Collections.reverse(path);
        return path;
    }

    static boolean isValid(int r, int c, boolean[][] visited) {
        return r >= 0 && r < rows &&
                c >= 0 && c < cols &&
                grid[r][c] != '#' &&
                !visited[r][c];
    }

    public static void main(String[] args) {
        int startR = 0, startC = 0;
        int endR = 2, endC = 3;

        List<Cell> path = bfs(startR, startC, endR, endC);

        if (path.isEmpty()) {
            System.out.println("Không có đường đi");
        } else {
            System.out.println("Đường đi ngắn nhất:");

            for (Cell cell : path) {
                System.out.println("(" + cell.r + ", " + cell.c + ")");
            }

            System.out.println("Số bước: " + (path.size() - 1));
        }
    }
}