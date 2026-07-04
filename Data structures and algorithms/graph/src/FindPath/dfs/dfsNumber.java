import java.util.*;

public class Main {
    static List<List<Integer>> graph;
    static boolean[] visited;
    static int[] parent;

    public static boolean dfs(int current, int end) {
        visited[current] = true;

        if (current == end) {
            return true;
        }

        for (int neighbor : graph.get(current)) {
            if (!visited[neighbor]) {
                parent[neighbor] = current;

                if (dfs(neighbor, end)) {
                    return true;
                }
            }
        }

        return false;
    }

    public static List<Integer> findPath(int start, int end) {
        visited = new boolean[graph.size()];
        parent = new int[graph.size()];

        Arrays.fill(parent, -1);

        boolean found = dfs(start, end);

        if (!found) {
            return new ArrayList<>();
        }

        List<Integer> path = new ArrayList<>();

        for (int cur = end; cur != -1; cur = parent[cur]) {
            path.add(cur);
        }

        Collections.reverse(path);
        return path;
    }

    public static void addEdge(int u, int v) {
        graph.get(u).add(v);
        graph.get(v).add(u);
    }

    public static void main(String[] args) {
        int n = 5;
        graph = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }

        addEdge(0, 1);
        addEdge(0, 2);
        addEdge(1, 3);
        addEdge(1, 4);
        addEdge(2, 4);

        List<Integer> path = findPath(0, 3);

        if (path.isEmpty()) {
            System.out.println("Không có đường đi");
        } else {
            System.out.println("Đường đi: " + path);
        }
    }
}