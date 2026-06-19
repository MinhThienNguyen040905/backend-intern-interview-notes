# Queue

## 1. Queue là gì?

`Queue` là cấu trúc dữ liệu hoạt động theo nguyên tắc FIFO:

```text
First In, First Out
```

Nghĩa là phần tử được thêm vào trước sẽ được lấy ra trước.

Ví dụ đời thường: xếp hàng mua vé. Người vào hàng trước sẽ được phục vụ trước.

## 2. Các thao tác cơ bản

| Thao tác | Ý nghĩa | Độ phức tạp |
| --- | --- | --- |
| `offer(x)` / `add(x)` | Thêm `x` vào cuối queue | `O(1)` |
| `poll()` / `remove()` | Lấy và xóa phần tử ở đầu queue | `O(1)` |
| `peek()` / `element()` | Xem phần tử ở đầu queue nhưng không xóa | `O(1)` |
| `isEmpty()` | Kiểm tra queue rỗng | `O(1)` |
| `size()` | Lấy số phần tử | `O(1)` |

Trong Java, nên ưu tiên dùng:

- `offer()` thay vì `add()`.
- `poll()` thay vì `remove()`.
- `peek()` thay vì `element()`.

Vì `offer()`, `poll()`, `peek()` xử lý queue rỗng/đầy mềm hơn, thường trả về `false` hoặc `null` thay vì ném exception.

## 3. Queue trong Java

Ví dụ dùng `ArrayDeque`:

```java
Queue<Integer> queue = new ArrayDeque<>();

queue.offer(10);
queue.offer(20);
queue.offer(30);

System.out.println(queue.peek()); // 10
System.out.println(queue.poll()); // 10
System.out.println(queue.poll()); // 20
```

Queue lúc đầu:

```text
Front -> 10 -> 20 -> 30 <- Rear
```

Sau `poll()`:

```text
Front -> 20 -> 30 <- Rear
```

## 4. `Queue`, `Deque`, `ArrayDeque`, `LinkedList`

Trong Java, `Queue` là interface.

Một số implementation phổ biến:

| Implementation | Đặc điểm |
| --- | --- |
| `ArrayDeque` | Thường dùng nhất cho queue/stack thông thường |
| `LinkedList` | Có thể dùng làm queue nhưng thường không cần thiết |
| `PriorityQueue` | Hàng đợi ưu tiên, phần tử nhỏ/lớn nhất ra trước tùy comparator |

Khuyến nghị trong bài thuật toán:

```java
Queue<Integer> queue = new ArrayDeque<>();
```

Hoặc nếu cần thao tác hai đầu:

```java
Deque<Integer> deque = new ArrayDeque<>();
```

## 5. Khi nào nên nghĩ đến Queue?

Queue thường hữu ích khi bài toán có một trong các dấu hiệu:

- Cần xử lý theo thứ tự đến trước làm trước.
- Cần duyệt BFS.
- Cần xử lý từng level trong tree/graph.
- Cần mô phỏng hàng đợi.
- Cần xử lý task theo thứ tự.
- Cần shortest path trong graph không trọng số.

## 6. Ví dụ 1: BFS trên binary tree

Bài toán: Duyệt cây theo từng tầng.

```java
class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int val) {
        this.val = val;
    }
}
```

Code:

```java
public List<Integer> levelOrder(TreeNode root) {
    List<Integer> result = new ArrayList<>();
    if (root == null) return result;

    Queue<TreeNode> queue = new ArrayDeque<>();
    queue.offer(root);

    while (!queue.isEmpty()) {
        TreeNode node = queue.poll();
        result.add(node.val);

        if (node.left != null) queue.offer(node.left);
        if (node.right != null) queue.offer(node.right);
    }

    return result;
}
```

Time: `O(n)`

Space: `O(n)`

Vì mỗi node được đưa vào queue một lần và lấy ra một lần.

## 7. Ví dụ 2: BFS theo từng level

Bài toán: Trả về danh sách các tầng của binary tree.

```java
public List<List<Integer>> levelOrderByLevel(TreeNode root) {
    List<List<Integer>> result = new ArrayList<>();
    if (root == null) return result;

    Queue<TreeNode> queue = new ArrayDeque<>();
    queue.offer(root);

    while (!queue.isEmpty()) {
        int size = queue.size();
        List<Integer> level = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            TreeNode node = queue.poll();
            level.add(node.val);

            if (node.left != null) queue.offer(node.left);
            if (node.right != null) queue.offer(node.right);
        }

        result.add(level);
    }

    return result;
}
```

Điểm quan trọng:

```java
int size = queue.size();
```

Ta lấy size hiện tại trước khi duyệt level, vì trong lúc duyệt ta sẽ thêm node của level tiếp theo vào queue.

Time: `O(n)`

Space: `O(n)`

## 8. Ví dụ 3: BFS trên graph

Với graph dùng adjacency list:

```java
public void bfs(List<List<Integer>> graph, int start) {
    boolean[] visited = new boolean[graph.size()];
    Queue<Integer> queue = new ArrayDeque<>();

    visited[start] = true;
    queue.offer(start);

    while (!queue.isEmpty()) {
        int node = queue.poll();
        System.out.println(node);

        for (int next : graph.get(node)) {
            if (!visited[next]) {
                visited[next] = true;
                queue.offer(next);
            }
        }
    }
}
```

Time: `O(V + E)`

Space: `O(V)`

Trong đó:

- `V` là số đỉnh.
- `E` là số cạnh.

## 9. Ví dụ 4: Shortest path trong graph không trọng số

Với graph không trọng số, BFS có thể tìm khoảng cách ngắn nhất tính theo số cạnh.

```java
public int shortestPath(List<List<Integer>> graph, int start, int target) {
    int n = graph.size();
    boolean[] visited = new boolean[n];
    int[] distance = new int[n];
    Queue<Integer> queue = new ArrayDeque<>();

    Arrays.fill(distance, -1);

    visited[start] = true;
    distance[start] = 0;
    queue.offer(start);

    while (!queue.isEmpty()) {
        int node = queue.poll();

        if (node == target) return distance[node];

        for (int next : graph.get(node)) {
            if (!visited[next]) {
                visited[next] = true;
                distance[next] = distance[node] + 1;
                queue.offer(next);
            }
        }
    }

    return -1;
}
```

Time: `O(V + E)`

Space: `O(V)`

## 10. Circular Queue

`Circular Queue` là queue dùng mảng vòng tròn để tận dụng lại vị trí trống sau khi `poll()`.

Nếu dùng mảng thường:

```text
[_, _, 3, 4, 5]
```

Hai vị trí đầu đã trống nhưng nếu không dùng vòng tròn thì có thể bị lãng phí.

Ý tưởng:

```text
rear = (rear + 1) % capacity
front = (front + 1) % capacity
```

Cài đặt:

```java
class MyCircularQueue {
    private int[] data;
    private int front;
    private int size;

    public MyCircularQueue(int capacity) {
        data = new int[capacity];
        front = 0;
        size = 0;
    }

    public boolean offer(int value) {
        if (isFull()) return false;

        int rear = (front + size) % data.length;
        data[rear] = value;
        size++;
        return true;
    }

    public Integer poll() {
        if (isEmpty()) return null;

        int value = data[front];
        front = (front + 1) % data.length;
        size--;
        return value;
    }

    public Integer peek() {
        if (isEmpty()) return null;
        return data[front];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean isFull() {
        return size == data.length;
    }
}
```

Time:

- `offer`: `O(1)`
- `poll`: `O(1)`
- `peek`: `O(1)`

Space: `O(n)` với `n` là capacity.

## 11. Deque

`Deque` là double-ended queue, tức là có thể thêm/xóa ở cả hai đầu.

```java
Deque<Integer> deque = new ArrayDeque<>();

deque.offerFirst(10);
deque.offerLast(20);

System.out.println(deque.pollFirst()); // 10
System.out.println(deque.pollLast());  // 20
```

Các thao tác phổ biến:

| Thao tác | Ý nghĩa |
| --- | --- |
| `offerFirst(x)` | Thêm vào đầu |
| `offerLast(x)` | Thêm vào cuối |
| `pollFirst()` | Lấy ở đầu |
| `pollLast()` | Lấy ở cuối |
| `peekFirst()` | Xem đầu |
| `peekLast()` | Xem cuối |

`Deque` có thể dùng như stack:

```java
deque.push(1);
deque.pop();
```

Và cũng có thể dùng như queue:

```java
deque.offer(1);
deque.poll();
```

## 12. PriorityQueue

`PriorityQueue` không phải queue FIFO thông thường. Nó lấy phần tử theo độ ưu tiên.

Mặc định trong Java, `PriorityQueue<Integer>` là min-heap:

```java
PriorityQueue<Integer> pq = new PriorityQueue<>();

pq.offer(5);
pq.offer(1);
pq.offer(3);

System.out.println(pq.poll()); // 1
System.out.println(pq.poll()); // 3
System.out.println(pq.poll()); // 5
```

Muốn max-heap:

```java
PriorityQueue<Integer> pq = new PriorityQueue<>(Collections.reverseOrder());
```

Độ phức tạp:

| Thao tác | Complexity |
| --- | --- |
| `offer` | `O(log n)` |
| `poll` | `O(log n)` |
| `peek` | `O(1)` |

PriorityQueue thường dùng cho:

- Top K elements.
- Kth largest/smallest.
- Dijkstra.
- Merge K sorted lists.
- Scheduling.

## 13. Queue và Stack khác nhau thế nào?

| Cấu trúc | Nguyên tắc | Thêm | Lấy ra |
| --- | --- | --- | --- |
| Queue | FIFO | Thêm ở cuối | Lấy ở đầu |
| Stack | LIFO | Thêm ở đỉnh | Lấy ở đỉnh |

Queue:

```text
First In, First Out
```

Stack:

```text
Last In, First Out
```

## 14. Lỗi thường gặp

- Gọi `remove()` hoặc `element()` khi queue rỗng và bị exception.
- Quên kiểm tra `queue.isEmpty()` trong vòng lặp BFS.
- Dùng `queue.size()` trực tiếp trong điều kiện `for` khi đang thêm phần tử mới vào queue.
- Nhầm `PriorityQueue` là FIFO queue.
- Quên đánh dấu `visited` khi BFS graph, dẫn đến lặp vô hạn.
- Đánh dấu `visited` quá muộn, làm một node bị đưa vào queue nhiều lần.
- Quên tính `O(n)` space vì queue có thể chứa nhiều node cùng lúc.

## 15. Cách trả lời phỏng vấn

Nếu được hỏi Queue là gì:

```text
Queue là cấu trúc dữ liệu theo nguyên tắc FIFO, phần tử vào trước sẽ ra trước. Các thao tác chính là offer, poll, peek, thường có độ phức tạp O(1). Queue thường dùng trong BFS, duyệt tree theo level, xử lý task theo thứ tự và tìm shortest path trong graph không trọng số.
```

Nếu được hỏi vì sao BFS dùng Queue:

```text
BFS cần xử lý các node theo thứ tự khoảng cách tăng dần từ node bắt đầu. Queue phù hợp vì node được phát hiện trước sẽ được xử lý trước, giúp duyệt hết level hiện tại trước khi sang level tiếp theo.
```

