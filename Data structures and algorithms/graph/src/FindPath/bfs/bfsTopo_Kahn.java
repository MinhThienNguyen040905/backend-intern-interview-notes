import java.util.*;

public class Main {
    static List<List<Integer>> graph;

    static List<Integer> topologicalSort(int n) {
        int[] indegree = new int[n];

        for (int u = 0; u < n; u++) {
            for (int v : graph.get(u)) {
                indegree[v]++;
            }
        }

        Queue<Integer> queue = new ArrayDeque<>();

        for (int i = 0; i < n; i++) {
            if (indegree[i] == 0) {
                queue.add(i);
            }
        }

        List<Integer> result = new ArrayList<>();

        while (!queue.isEmpty()) {
            int u = queue.poll();
            result.add(u);

            for (int v : graph.get(u)) {
                indegree[v]--;

                if (indegree[v] == 0) {
                    queue.add(v);
                }
            }
        }

        // Nếu không đủ n đỉnh, nghĩa là có chu trình
        if (result.size() != n) {
            System.out.println("Đồ thị có chu trình, không topo sort được");
            return new ArrayList<>();
        }

        return result;
    }

    static void addEdge(int u, int v) {
        graph.get(u).add(v);
    }

    public static void main(String[] args) {
        int n = 5;
        graph = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }

        addEdge(0, 1);
        addEdge(1, 3);
        addEdge(2, 4);
        addEdge(3, 4);

        List<Integer> order = topologicalSort(n);

        System.out.println(order);
    }
}