import java.util.*;

public class Main {
    static Map<String, List<String>> graph = new HashMap<>();
    static Set<String> visited = new HashSet<>();
    static Map<String, String> parent = new HashMap<>();

    public static boolean dfs(String current, String end) {
        visited.add(current);

        if (current.equals(end)) {
            return true;
        }

        for (String neighbor : graph.getOrDefault(current, new ArrayList<>())) {
            if (!visited.contains(neighbor)) {
                parent.put(neighbor, current);

                if (dfs(neighbor, end)) {
                    return true;
                }
            }
        }

        return false;
    }

    public static List<String> findPath(String start, String end) {
        visited.clear();
        parent.clear();

        parent.put(start, null);

        boolean found = dfs(start, end);

        if (!found) {
            return new ArrayList<>();
        }

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