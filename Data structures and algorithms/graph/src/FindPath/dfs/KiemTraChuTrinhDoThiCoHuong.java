import java.util.*;

public class Main {
    static List<List<Integer>> graph;
    static int[] state;

    static boolean hasCycleDFS(int u) {
        state[u] = 1; // đang thăm

        for (int v : graph.get(u)) {
            if (state[v] == 0) {
                if (hasCycleDFS(v)) {
                    return true;
                }
            } else if (state[v] == 1) {
                return true; // gặp lại đỉnh đang nằm trong nhánh DFS hiện tại
            }
        }

        state[u] = 2; // xử lý xong
        return false;
    }

    static boolean hasCycle(int n) {
        state = new int[n];

        for (int i = 0; i < n; i++) {
            if (state[i] == 0) {
                if (hasCycleDFS(i)) {
                    return true;
                }
            }
        }

        return false;
    }

    static void addEdge(int u, int v) {
        graph.get(u).add(v); // đồ thị có hướng: chỉ thêm u -> v
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

        if (hasCycle(n)) {
            System.out.println("Đồ thị có chu trình");
        } else {
            System.out.println("Đồ thị không có chu trình");
        }
    }
}