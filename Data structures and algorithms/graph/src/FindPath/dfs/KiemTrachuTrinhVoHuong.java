import java.util.*;

public class Main {
    static List<List<Integer>> graph;
    static boolean[] visited;

    static boolean hasCycleDFS(int u, int parent) {
        visited[u] = true;

        for (int v : graph.get(u)) {
            if (!visited[v]) {
                if (hasCycleDFS(v, u)) {
                    return true;
                }
            } else if (v != parent) {
                return true;
            }
        }

        return false;
    }

    static boolean hasCycle(int n) {
        visited = new boolean[n];

        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                if (hasCycleDFS(i, -1)) {
                    return true;
                }
            }
        }

        return false;
    }

    static void addEdge(int u, int v) {
        graph.get(u).add(v);
        graph.get(v).add(u);
    }

    public static void main(String[] args) {
        int n = 4;
        graph = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }

        addEdge(0, 1);
        addEdge(1, 2);
        addEdge(2, 3);
        addEdge(3, 0);

        if (hasCycle(n)) {
            System.out.println("Đồ thị có chu trình");
        } else {
            System.out.println("Đồ thị không có chu trình");
        }
    }
}