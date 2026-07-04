import java.util.*;

public class Main {
    static List<List<Integer>> graph;
    static int[] state;
    static List<Integer> result;

    static boolean dfs(int u) {
        state[u] = 1; // đang thăm

        for (int v : graph.get(u)) {
            if (state[v] == 0) {
                if (!dfs(v)) {
                    return false;
                }
            } else if (state[v] == 1) {
                // Gặp lại đỉnh đang nằm trong nhánh DFS hiện tại
                return false;
            }
        }

        state[u] = 2;     // đã xử lý xong
        result.add(u);    // thêm sau khi xử lý hết hàng xóm
        return true;
    }

    static List<Integer> topologicalSort(int n) {
        state = new int[n];
        result = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            if (state[i] == 0) {
                if (!dfs(i)) {
                    return new ArrayList<>();
                }
            }
        }

        Collections.reverse(result);
        return result;
    }

    static void addEdge(int u, int v) {
        graph.get(u).add(v);
    }

    public static void main(String[] args) {
        int n = 3;
        graph = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }

        addEdge(0, 1);
        addEdge(1, 2);
        addEdge(2, 0); // tạo chu trình

        List<Integer> order = topologicalSort(n);

        if (order.isEmpty()) {
            System.out.println("Đồ thị có chu trình, không topo sort được");
        } else {
            System.out.println(order);
        }
    }
}