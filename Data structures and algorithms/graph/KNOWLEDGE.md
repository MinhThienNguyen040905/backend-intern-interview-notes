# Đồ thị (Graph)

## 1. Đồ thị là gì?

Đồ thị là cấu trúc dữ liệu gồm:

- `Vertex` hoặc `node`: đỉnh.
- `Edge`: cạnh nối giữa các đỉnh.

Ký hiệu:

```text
G = (V, E)
```

Trong đó:

- `V` là tập hợp các đỉnh.
- `E` là tập hợp các cạnh.

Ví dụ:

```text
A ----- B
|       |
|       |
C ----- D
```

Đồ thị thường dùng để biểu diễn:

- Mạng xã hội.
- Bản đồ và đường đi.
- Mạng máy tính.
- Quan hệ phụ thuộc giữa các công việc.
- Liên kết giữa các trang web.

## 2. Các loại đồ thị

### Đồ thị vô hướng

Cạnh không có chiều:

```text
A ----- B
```

Nếu đi được từ `A` sang `B` thì cũng đi được từ `B` sang `A`.

### Đồ thị có hướng

Cạnh có chiều:

```text
A ----> B
```

Đi từ `A` sang `B` không có nghĩa là đi được từ `B` sang `A`.

### Đồ thị có trọng số

Mỗi cạnh có một giá trị, thường là khoảng cách hoặc chi phí:

```text
A --5-- B
```

### Đồ thị không trọng số

Các cạnh không có chi phí hoặc có thể xem mọi cạnh đều có trọng số bằng `1`.

### Đồ thị liên thông

Trong đồ thị vô hướng, mọi cặp đỉnh đều có đường đi nối với nhau.

### DAG

`DAG` là Directed Acyclic Graph: đồ thị có hướng không chứa chu trình.

DAG thường xuất hiện trong:

- Lập lịch công việc.
- Quan hệ prerequisite giữa các khóa học.
- Dependency giữa các module.

## 3. Các thuật ngữ quan trọng

| Thuật ngữ | Ý nghĩa |
| --- | --- |
| Path | Đường đi qua một chuỗi các đỉnh |
| Cycle | Chu trình, đường đi quay lại đỉnh ban đầu |
| Degree | Số cạnh nối với một đỉnh |
| In-degree | Số cạnh đi vào một đỉnh |
| Out-degree | Số cạnh đi ra từ một đỉnh |
| Connected component | Một thành phần liên thông |
| Neighbor | Đỉnh kề |

## 4. Biểu diễn bằng Adjacency List

Adjacency List lưu danh sách các đỉnh kề của mỗi đỉnh.

Ví dụ đồ thị vô hướng:

```text
0 ----- 1
|       |
2 ----- 3
```

Danh sách kề:

```text
0: [1, 2]
1: [0, 3]
2: [0, 3]
3: [1, 2]
```

Java:

```java
int n = 4;
List<List<Integer>> graph = new ArrayList<>();

for (int i = 0; i < n; i++) {
    graph.add(new ArrayList<>());
}

addUndirectedEdge(graph, 0, 1);
addUndirectedEdge(graph, 0, 2);
addUndirectedEdge(graph, 1, 3);
addUndirectedEdge(graph, 2, 3);
```

```java
static void addUndirectedEdge(
        List<List<Integer>> graph,
        int u,
        int v
) {
    graph.get(u).add(v);
    graph.get(v).add(u);
}
```

Độ phức tạp bộ nhớ:

```text
O(V + E)
```

Adjacency List phù hợp với đồ thị thưa, tức là số cạnh không quá lớn.

## 5. Biểu diễn bằng Adjacency Matrix

Adjacency Matrix dùng ma trận `V x V`.

```java
int[][] graph = new int[n][n];

graph[0][1] = 1;
graph[1][0] = 1;
```

Nếu `graph[u][v] == 1`, tồn tại cạnh từ `u` đến `v`.

Với đồ thị có trọng số:

```java
graph[u][v] = weight;
```

So sánh:

| Tiêu chí | Adjacency List | Adjacency Matrix |
| --- | --- | --- |
| Bộ nhớ | `O(V + E)` | `O(V^2)` |
| Kiểm tra cạnh `(u, v)` | Có thể là `O(degree(u))` | `O(1)` |
| Duyệt hàng xóm của `u` | `O(degree(u))` | `O(V)` |
| Phù hợp | Đồ thị thưa | Đồ thị dày |

## 6. Breadth-First Search (BFS)

BFS duyệt theo từng tầng và sử dụng Queue.

```java
public void bfs(List<List<Integer>> graph, int start) {
    boolean[] visited = new boolean[graph.size()];
    Queue<Integer> queue = new ArrayDeque<>();

    visited[start] = true;
    queue.offer(start);

    while (!queue.isEmpty()) {
        int node = queue.poll();
        System.out.println(node);

        for (int neighbor : graph.get(node)) {
            if (!visited[neighbor]) {
                visited[neighbor] = true;
                queue.offer(neighbor);
            }
        }
    }
}
```

Time:

```text
O(V + E)
```

Space:

```text
O(V)
```

Nên đánh dấu `visited` ngay khi đưa node vào queue để tránh một node bị thêm nhiều lần.

### Khi nào dùng BFS?

- Duyệt theo level.
- Tìm shortest path trong graph không trọng số.
- Tìm số bước ít nhất.
- Bài toán lan truyền theo từng lượt.

## 7. Depth-First Search (DFS)

DFS đi sâu theo một nhánh trước khi quay lại.

### DFS bằng đệ quy

```java
public void dfs(
        List<List<Integer>> graph,
        int node,
        boolean[] visited
) {
    visited[node] = true;
    System.out.println(node);

    for (int neighbor : graph.get(node)) {
        if (!visited[neighbor]) {
            dfs(graph, neighbor, visited);
        }
    }
}
```

### DFS bằng Stack

```java
public void dfsIterative(List<List<Integer>> graph, int start) {
    boolean[] visited = new boolean[graph.size()];
    Deque<Integer> stack = new ArrayDeque<>();

    stack.push(start);

    while (!stack.isEmpty()) {
        int node = stack.pop();

        if (visited[node]) continue;

        visited[node] = true;
        System.out.println(node);

        for (int neighbor : graph.get(node)) {
            if (!visited[neighbor]) {
                stack.push(neighbor);
            }
        }
    }
}
```

Time: `O(V + E)`

Space: `O(V)`

### Khi nào dùng DFS?

- Kiểm tra liên thông.
- Đếm connected components.
- Phát hiện chu trình.
- Backtracking.
- Topological sort.
- Duyệt toàn bộ một vùng trong matrix.

## 8. BFS và DFS khác nhau thế nào?

| Tiêu chí | BFS | DFS |
| --- | --- | --- |
| Cấu trúc sử dụng | Queue | Stack hoặc recursion |
| Cách duyệt | Theo từng tầng | Đi sâu trước |
| Shortest path không trọng số | Có | Không đảm bảo |
| Bộ nhớ | Có thể lớn nếu một tầng rộng | Có thể lớn nếu graph rất sâu |

Cả BFS và DFS đều có time complexity `O(V + E)` khi dùng adjacency list.

## 9. Duyệt đồ thị không liên thông

Nếu chỉ bắt đầu từ một đỉnh, ta có thể không thăm được toàn bộ graph.

Cần thêm vòng lặp qua tất cả đỉnh:

```java
public void traverseAll(List<List<Integer>> graph) {
    boolean[] visited = new boolean[graph.size()];

    for (int node = 0; node < graph.size(); node++) {
        if (!visited[node]) {
            dfs(graph, node, visited);
        }
    }
}
```

Mỗi lần bắt đầu DFS mới tương ứng với một connected component.

## 10. Đếm số thành phần liên thông

```java
public int countComponents(List<List<Integer>> graph) {
    boolean[] visited = new boolean[graph.size()];
    int count = 0;

    for (int node = 0; node < graph.size(); node++) {
        if (!visited[node]) {
            dfs(graph, node, visited);
            count++;
        }
    }

    return count;
}
```

Time: `O(V + E)`

Space: `O(V)`

## 11. Phát hiện chu trình trong đồ thị vô hướng

Khi DFS, nếu gặp một đỉnh đã thăm và đỉnh đó không phải parent hiện tại thì có chu trình.

```java
public boolean hasCycleUndirected(
        List<List<Integer>> graph,
        int node,
        int parent,
        boolean[] visited
) {
    visited[node] = true;

    for (int neighbor : graph.get(node)) {
        if (!visited[neighbor]) {
            if (hasCycleUndirected(graph, neighbor, node, visited)) {
                return true;
            }
        } else if (neighbor != parent) {
            return true;
        }
    }

    return false;
}
```

Time: `O(V + E)`

Space: `O(V)`

## 12. Phát hiện chu trình trong đồ thị có hướng

Ta cần phân biệt ba trạng thái:

```text
0: chưa thăm
1: đang nằm trên nhánh DFS hiện tại
2: đã xử lý xong
```

Nếu gặp lại node có trạng thái `1`, graph có chu trình.

```java
public boolean hasCycleDirected(
        List<List<Integer>> graph,
        int node,
        int[] state
) {
    state[node] = 1;

    for (int neighbor : graph.get(node)) {
        if (state[neighbor] == 1) return true;

        if (state[neighbor] == 0
                && hasCycleDirected(graph, neighbor, state)) {
            return true;
        }
    }

    state[node] = 2;
    return false;
}
```

## 13. Topological Sort

Topological sort sắp xếp các đỉnh sao cho với mọi cạnh:

```text
u -> v
```

thì `u` đứng trước `v`.

Chỉ áp dụng cho DAG.

### Kahn's Algorithm

Ý tưởng:

1. Tính in-degree của mọi đỉnh.
2. Đưa các đỉnh có in-degree bằng `0` vào queue.
3. Lấy từng đỉnh ra và giảm in-degree của các đỉnh kề.

```java
public List<Integer> topologicalSort(List<List<Integer>> graph) {
    int n = graph.size();
    int[] inDegree = new int[n];

    for (int node = 0; node < n; node++) {
        for (int neighbor : graph.get(node)) {
            inDegree[neighbor]++;
        }
    }

    Queue<Integer> queue = new ArrayDeque<>();

    for (int node = 0; node < n; node++) {
        if (inDegree[node] == 0) {
            queue.offer(node);
        }
    }

    List<Integer> order = new ArrayList<>();

    while (!queue.isEmpty()) {
        int node = queue.poll();
        order.add(node);

        for (int neighbor : graph.get(node)) {
            inDegree[neighbor]--;

            if (inDegree[neighbor] == 0) {
                queue.offer(neighbor);
            }
        }
    }

    if (order.size() != n) {
        return Collections.emptyList();
    }

    return order;
}
```

Time: `O(V + E)`

Space: `O(V)`

Nếu kết quả không chứa đủ `V` đỉnh thì graph có chu trình.

## 14. Shortest Path

Chọn thuật toán dựa trên loại graph:

| Loại graph | Thuật toán |
| --- | --- |
| Không trọng số | BFS |
| Trọng số không âm | Dijkstra |
| Có cạnh trọng số âm | Bellman-Ford |
| Mọi cặp đỉnh | Floyd-Warshall |
| DAG có trọng số | Topological order |

### Dijkstra

Dijkstra dùng `PriorityQueue` để luôn xử lý đỉnh có khoảng cách nhỏ nhất trước.

```java
record Edge(int to, int weight) {}
record State(int node, int distance) {}
```

```java
public int[] dijkstra(List<List<Edge>> graph, int start) {
    int n = graph.size();
    int[] distance = new int[n];
    Arrays.fill(distance, Integer.MAX_VALUE);

    PriorityQueue<State> pq = new PriorityQueue<>(
            Comparator.comparingInt(State::distance)
    );

    distance[start] = 0;
    pq.offer(new State(start, 0));

    while (!pq.isEmpty()) {
        State current = pq.poll();
        int node = current.node();
        int currentDistance = current.distance();

        if (currentDistance != distance[node]) continue;

        for (Edge edge : graph.get(node)) {
            int nextDistance = currentDistance + edge.weight();

            if (nextDistance < distance[edge.to()]) {
                distance[edge.to()] = nextDistance;
                pq.offer(new State(edge.to(), nextDistance));
            }
        }
    }

    return distance;
}
```

Time thường là:

```text
O((V + E) log V)
```

Dijkstra không hoạt động đúng với cạnh có trọng số âm.

## 15. Union-Find

Union-Find, còn gọi là Disjoint Set Union, dùng để quản lý các nhóm phần tử không giao nhau.

Hai thao tác chính:

- `find(x)`: tìm đại diện của nhóm chứa `x`.
- `union(a, b)`: gộp hai nhóm.

```java
class UnionFind {
    private final int[] parent;
    private final int[] rank;

    UnionFind(int n) {
        parent = new int[n];
        rank = new int[n];

        for (int i = 0; i < n; i++) {
            parent[i] = i;
        }
    }

    int find(int x) {
        if (parent[x] != x) {
            parent[x] = find(parent[x]);
        }
        return parent[x];
    }

    boolean union(int a, int b) {
        int rootA = find(a);
        int rootB = find(b);

        if (rootA == rootB) return false;

        if (rank[rootA] < rank[rootB]) {
            parent[rootA] = rootB;
        } else if (rank[rootA] > rank[rootB]) {
            parent[rootB] = rootA;
        } else {
            parent[rootB] = rootA;
            rank[rootA]++;
        }

        return true;
    }
}
```

Với path compression và union by rank, mỗi thao tác gần như `O(1)` amortized.

Union-Find thường dùng để:

- Phát hiện chu trình trong graph vô hướng.
- Đếm connected components.
- Kiểm tra hai node có cùng nhóm không.
- Thuật toán Kruskal tìm minimum spanning tree.

## 16. Graph dạng lưới

Matrix có thể được xem là graph:

- Mỗi ô là một đỉnh.
- Các ô kề nhau là hàng xóm.

Mảng hướng đi bốn phía:

```java
int[][] directions = {
    {1, 0},
    {-1, 0},
    {0, 1},
    {0, -1}
};
```

Các bài phổ biến:

- Number of Islands.
- Flood Fill.
- Rotting Oranges.
- Shortest Path in Binary Matrix.

Với grid có `rows * cols` ô, time complexity thường là:

```text
O(rows * cols)
```

## 17. Các bài phỏng vấn thường gặp

- Number of Islands.
- Clone Graph.
- Course Schedule.
- Pacific Atlantic Water Flow.
- Rotting Oranges.
- Word Ladder.
- Network Delay Time.
- Redundant Connection.
- Number of Connected Components.
- Cheapest Flights Within K Stops.

## 18. Lỗi thường gặp

- Quên mảng hoặc Set `visited`, dẫn đến lặp vô hạn.
- Với graph vô hướng, quên thêm cạnh theo cả hai chiều.
- Chỉ BFS/DFS từ node `0` rồi cho rằng đã duyệt toàn bộ graph.
- Đánh dấu `visited` quá muộn trong BFS, làm node vào queue nhiều lần.
- Dùng DFS để tìm shortest path trong graph không trọng số mà không có xử lý bổ sung.
- Dùng Dijkstra khi graph có cạnh âm.
- Nhầm `O(V + E)` thành `O(V * E)`.
- Quên rằng recursion DFS quá sâu có thể gây `StackOverflowError`.

## 19. Cách trả lời phỏng vấn

Nếu được hỏi cách biểu diễn graph:

```text
Graph thường được biểu diễn bằng adjacency list hoặc adjacency matrix. Adjacency list dùng O(V + E) bộ nhớ và phù hợp với graph thưa. Adjacency matrix dùng O(V^2) bộ nhớ nhưng kiểm tra một cạnh trong O(1).
```

Nếu được hỏi BFS và DFS khác nhau thế nào:

```text
BFS dùng queue và duyệt theo từng tầng, phù hợp để tìm shortest path trong graph không trọng số. DFS dùng recursion hoặc stack và đi sâu theo từng nhánh, thường dùng để kiểm tra liên thông, phát hiện chu trình và backtracking. Với adjacency list, cả hai có time complexity O(V + E).
```

