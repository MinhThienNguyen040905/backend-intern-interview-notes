import java.util.*;

public class Main {
    static Map<String, List<String>> graph = new HashMap<>();

    public static List<String> findPath(String start, String end) {
        Queue<String> queue = new ArrayDeque<>();
        Set<String> visited = new HashSet<>();
        Map<String, String> parent = new HashMap<>();

        queue.add(start);
        visited.add(start);
        parent.put(start, null);

        while (!queue.isEmpty()) {
            String current = queue.poll();

            if (current.equals(end)) {
                break;
            }

            for (String neighbor : graph.getOrDefault(current, new ArrayList<>())) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    parent.put(neighbor, current);
                    queue.add(neighbor);
                }
            }
        }

        // Nếu không tìm thấy end
        if (!visited.contains(end)) {
            return new ArrayList<>();
        }

        // Dựng lại đường đi từ end về start
        List<String> path = new ArrayList<>();
        String current = end;

        while (current != null) {
            path.add(current);
            current = parent.get(current);
        }

        Collections.reverse(path);
        return path;
    }

    public static void main(String[] args) {
        graph.put("A", Arrays.asList("B", "C"));
        graph.put("B", Arrays.asList("A", "D", "E"));
        graph.put("C", Arrays.asList("A", "E"));
        graph.put("D", Arrays.asList("B"));
        graph.put("E", Arrays.asList("B", "C"));

        List<String> path = findPath("A", "D");

        if (path.isEmpty()) {
            System.out.println("Không có đường đi");
        } else {
            System.out.println("Đường đi: " + path);
        }
    }
}