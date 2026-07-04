import java.util.*;

public class Main {
    public static List<Integer> findPath(List<List<Integer>> graph, int start, int end) {
        Queue<Integer> queue = new ArrayDeque<>();
        boolean[] visited = new boolean[graph.size()];
        int[] parent = new int[graph.size()];

        Arrays.fill(parent, -1);

        queue.add(start);
        visited[start] = true;

        while (!queue.isEmpty()) {
            int current = queue.poll();

            if (current == end) {
                break;
            }

            for (int neighbor : graph.get(current)) {
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    parent[neighbor] = current;
                    queue.add(neighbor);
                }
            }
        }

        if (!visited[end]) {
            return new ArrayList<>();
        }

        List<Integer> path = new ArrayList<>();

        for (int cur = end; cur != -1; cur = parent[cur]) {
            path.add(cur);
        }

        Collections.reverse(path);
        return path;
    }

    public static void main(String[] args) {
        int n = 5;
        List<List<Integer>> graph = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }

        addEdge(graph, 0, 1);
        addEdge(graph, 0, 2);
        addEdge(graph, 1, 3);
        addEdge(graph, 1, 4);
        addEdge(graph, 2, 4);

        List<Integer> path = findPath(graph, 0, 3);

        System.out.println(path);
    }

    static void addEdge(List<List<Integer>> graph, int u, int v) {
        graph.get(u).add(v);
        graph.get(v).add(u);
    }
}