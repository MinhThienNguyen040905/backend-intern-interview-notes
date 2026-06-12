# Cấu trúc dữ liệu & giải thuật cho phỏng vấn Intern Backend

Tài liệu này tổng hợp kiến thức cấu trúc dữ liệu và giải thuật thường gặp khi phỏng vấn intern backend. Mục tiêu là hiểu bản chất, phân tích độ phức tạp, chọn cấu trúc dữ liệu phù hợp và luyện cách giải bài toán coding cơ bản.

## 1. Vì sao backend intern cần học DSA?

DSA, viết tắt của Data Structures and Algorithms, giúp bạn:

- Viết code hiệu quả hơn.
- Biết chọn cấu trúc dữ liệu phù hợp.
- Phân tích được code chạy nhanh hay chậm.
- Tránh bug khi xử lý dữ liệu lớn.
- Vượt qua vòng coding interview.

Trong backend, DSA xuất hiện ở:

- Tìm kiếm user/order/product.
- Xử lý danh sách lớn.
- Cache bằng hash map.
- Queue xử lý job.
- Tree trong index/database.
- Graph trong quan hệ follow/friend/recommendation.
- Sorting/filtering/pagination.
- Deduplicate dữ liệu.
- Rate limiting.

## 2. Big O notation

Big O dùng để mô tả độ phức tạp của thuật toán khi input lớn dần.

Hai loại quan trọng:

- **Time complexity**: thời gian chạy.
- **Space complexity**: bộ nhớ dùng thêm.

Ví dụ:

```java
for (int i = 0; i < n; i++) {
    System.out.println(i);
}
```

Vòng lặp chạy `n` lần, time complexity là `O(n)`.

## 3. Các độ phức tạp thường gặp

| Big O | Tên | Ví dụ |
|---|---|---|
| `O(1)` | Constant | Truy cập array theo index |
| `O(log n)` | Logarithmic | Binary search |
| `O(n)` | Linear | Duyệt array |
| `O(n log n)` | Linearithmic | Merge sort, quick sort trung bình |
| `O(n^2)` | Quadratic | Hai vòng lặp lồng nhau |
| `O(2^n)` | Exponential | Brute force subset |
| `O(n!)` | Factorial | Sinh mọi hoán vị |

Thứ tự từ tốt đến xấu:

```text
O(1) < O(log n) < O(n) < O(n log n) < O(n^2) < O(2^n) < O(n!)
```

## 4. Cách phân tích Big O

## 4.1 Bỏ hằng số

```java
for (int i = 0; i < n; i++) {}
for (int i = 0; i < n; i++) {}
```

Chạy `2n` lần nhưng Big O là `O(n)`, không phải `O(2n)`.

## 4.2 Lấy phần tăng nhanh nhất

```java
for (int i = 0; i < n; i++) {}

for (int i = 0; i < n; i++) {
    for (int j = 0; j < n; j++) {}
}
```

Tổng là `O(n + n^2)`, rút gọn thành `O(n^2)`.

## 4.3 Vòng lặp nối tiếp

```java
for (int i = 0; i < n; i++) {}
for (int j = 0; j < m; j++) {}
```

Độ phức tạp là `O(n + m)`.

## 4.4 Vòng lặp lồng nhau

```java
for (int i = 0; i < n; i++) {
    for (int j = 0; j < m; j++) {}
}
```

Độ phức tạp là `O(n * m)`.

## 5. Array

Array là cấu trúc dữ liệu lưu các phần tử liên tiếp trong bộ nhớ và truy cập bằng index.

Ví dụ:

```java
int[] numbers = {1, 2, 3, 4};
System.out.println(numbers[0]); // 1
```

Độ phức tạp:

| Operation | Complexity |
|---|---|
| Access by index | `O(1)` |
| Search unsorted | `O(n)` |
| Insert/delete cuối array động | `O(1)` amortized |
| Insert/delete giữa array | `O(n)` |

Ưu điểm:

- Truy cập index nhanh.
- Cache-friendly.
- Dễ dùng.

Nhược điểm:

- Insert/delete ở giữa chậm vì phải dịch phần tử.
- Array cố định kích thước trong nhiều ngôn ngữ.

## 6. Dynamic array

Dynamic array là array có thể tự mở rộng, ví dụ:

- Java: `ArrayList`
- JavaScript: `Array`
- Python: `list`
- C++: `vector`

Khi đầy, dynamic array thường tạo array mới lớn hơn rồi copy dữ liệu sang.

Thêm cuối:

- Trung bình: `O(1)` amortized.
- Khi resize: `O(n)`.

Ý nghĩa `amortized O(1)`:

> Không phải lần nào cũng `O(1)`, nhưng trung bình qua nhiều lần append thì mỗi lần coi như `O(1)`.

## 7. String

String là chuỗi ký tự. Trong nhiều ngôn ngữ như Java, string là immutable.

Immutable nghĩa là không thay đổi trực tiếp sau khi tạo.

Ví dụ Java:

```java
String s = "hello";
s = s + " world";
```

Đoạn trên tạo string mới, không sửa string cũ.

Vấn đề:

```java
String result = "";
for (String word : words) {
    result += word;
}
```

Nếu có nhiều word, cách này có thể chậm vì tạo nhiều string trung gian.

Tốt hơn trong Java:

```java
StringBuilder sb = new StringBuilder();
for (String word : words) {
    sb.append(word);
}
String result = sb.toString();
```

## 8. Linked list

Linked list gồm các node, mỗi node chứa value và reference đến node tiếp theo.

```java
class Node {
    int value;
    Node next;

    Node(int value) {
        this.value = value;
    }
}
```

Độ phức tạp:

| Operation | Complexity |
|---|---|
| Access by index | `O(n)` |
| Search | `O(n)` |
| Insert/delete sau node đã biết | `O(1)` |
| Insert/delete đầu list | `O(1)` |

Ưu điểm:

- Insert/delete đầu hoặc sau node đã biết nhanh.
- Không cần vùng nhớ liên tiếp.

Nhược điểm:

- Truy cập index chậm.
- Tốn thêm bộ nhớ cho pointer/reference.
- Kém cache-friendly hơn array.

Câu trả lời phỏng vấn:

> Array truy cập index `O(1)` nhưng insert giữa chậm. Linked list truy cập index `O(n)` nhưng insert/delete ở vị trí đã biết có thể `O(1)`.

## 9. Stack

Stack là cấu trúc LIFO: Last In, First Out.

Operation chính:

- `push`: thêm vào đỉnh stack.
- `pop`: lấy khỏi đỉnh stack.
- `peek`: xem phần tử đỉnh.

Ví dụ:

```text
push 1
push 2
pop -> 2
pop -> 1
```

Ứng dụng:

- Call stack.
- Undo/redo.
- Kiểm tra ngoặc hợp lệ.
- DFS.
- Evaluate expression.

Độ phức tạp:

| Operation | Complexity |
|---|---|
| push | `O(1)` |
| pop | `O(1)` |
| peek | `O(1)` |

## 10. Queue

Queue là cấu trúc FIFO: First In, First Out.

Operation chính:

- `enqueue`: thêm vào cuối queue.
- `dequeue`: lấy khỏi đầu queue.
- `peek`: xem phần tử đầu.

Ứng dụng:

- Job queue.
- Message queue.
- BFS.
- Rate limiting.
- Request processing.

Độ phức tạp:

| Operation | Complexity |
|---|---|
| enqueue | `O(1)` |
| dequeue | `O(1)` |
| peek | `O(1)` |

Trong backend, queue xuất hiện ở:

- RabbitMQ, Kafka, SQS.
- Background job.
- Email sending.
- Event processing.

## 11. Deque

Deque, viết tắt của double-ended queue, cho phép thêm/xóa ở cả hai đầu.

Operation:

- Add/remove first.
- Add/remove last.

Ứng dụng:

- Sliding window maximum.
- Palindrome check.
- Implement queue/stack linh hoạt.

## 12. Hash table, HashMap, HashSet

Hash table lưu key-value và dùng hash function để tìm vị trí lưu.

Ví dụ Java:

```java
Map<String, Integer> count = new HashMap<>();
count.put("apple", 2);
count.put("banana", 3);

System.out.println(count.get("apple")); // 2
```

Độ phức tạp trung bình:

| Operation | Complexity |
|---|---|
| Insert | `O(1)` average |
| Search | `O(1)` average |
| Delete | `O(1)` average |

Worst case có thể là `O(n)` nếu nhiều key collision.

## 13. Hash collision

Hash collision xảy ra khi hai key khác nhau có cùng hash bucket.

Cách xử lý phổ biến:

- Chaining: mỗi bucket chứa list/tree các entry.
- Open addressing: tìm slot trống khác.

Trong phỏng vấn intern, chỉ cần hiểu:

> HashMap trung bình rất nhanh, nhưng phụ thuộc hash function và collision handling.

## 14. HashMap dùng khi nào?

Dùng khi cần:

- Tìm kiếm nhanh theo key.
- Đếm tần suất.
- Kiểm tra phần tử đã xuất hiện chưa.
- Deduplicate dữ liệu.
- Cache kết quả.

Ví dụ đếm tần suất:

```java
Map<String, Integer> freq = new HashMap<>();

for (String word : words) {
    freq.put(word, freq.getOrDefault(word, 0) + 1);
}
```

Ví dụ kiểm tra duplicate:

```java
Set<Integer> seen = new HashSet<>();

for (int x : nums) {
    if (seen.contains(x)) {
        return true;
    }
    seen.add(x);
}
return false;
```

## 15. Set

Set lưu các phần tử không trùng.

Ví dụ:

```java
Set<String> emails = new HashSet<>();
emails.add("a@example.com");
emails.add("a@example.com");
```

Kết quả set chỉ có một email.

Ứng dụng:

- Deduplicate.
- Check membership.
- Tìm giao/hợp/hiệu của tập.

## 16. Two Sum

Bài toán:

> Cho array `nums` và số `target`, tìm hai số có tổng bằng `target`.

Brute force:

```java
for (int i = 0; i < nums.length; i++) {
    for (int j = i + 1; j < nums.length; j++) {
        if (nums[i] + nums[j] == target) {
            return new int[] {i, j};
        }
    }
}
```

Time: `O(n^2)`.

Tối ưu bằng HashMap:

```java
Map<Integer, Integer> map = new HashMap<>();

for (int i = 0; i < nums.length; i++) {
    int need = target - nums[i];
    if (map.containsKey(need)) {
        return new int[] {map.get(need), i};
    }
    map.put(nums[i], i);
}
```

Time: `O(n)`.

Space: `O(n)`.

Ý tưởng:

- Khi duyệt số hiện tại `x`, cần tìm số trước đó bằng `target - x`.
- HashMap giúp tìm số đó nhanh.

## 17. Tree

Tree là cấu trúc dữ liệu phân cấp gồm các node.

Khái niệm:

- Root: node gốc.
- Parent: node cha.
- Child: node con.
- Leaf: node không có con.
- Height: chiều cao cây.
- Depth: độ sâu node.

Ví dụ:

```text
        A
      /   \
     B     C
    / \
   D   E
```

`A` là root, `D` và `E` là leaf.

## 18. Binary tree

Binary tree là cây mà mỗi node có tối đa hai con:

- Left child.
- Right child.

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

## 19. Binary search tree

BST, viết tắt của Binary Search Tree, là binary tree có tính chất:

- Node bên trái nhỏ hơn node hiện tại.
- Node bên phải lớn hơn node hiện tại.

Ví dụ:

```text
        8
      /   \
     3     10
    / \      \
   1   6      14
```

Search trong BST:

```java
boolean search(TreeNode root, int target) {
    TreeNode current = root;
    while (current != null) {
        if (current.val == target) {
            return true;
        }
        if (target < current.val) {
            current = current.left;
        } else {
            current = current.right;
        }
    }
    return false;
}
```

Độ phức tạp:

- Balanced BST: `O(log n)`.
- Skewed BST: `O(n)`.

## 20. Tree traversal

Các cách duyệt cây phổ biến:

## 20.1 Preorder

Thứ tự: root -> left -> right.

```java
void preorder(TreeNode root) {
    if (root == null) return;
    System.out.println(root.val);
    preorder(root.left);
    preorder(root.right);
}
```

## 20.2 Inorder

Thứ tự: left -> root -> right.

```java
void inorder(TreeNode root) {
    if (root == null) return;
    inorder(root.left);
    System.out.println(root.val);
    inorder(root.right);
}
```

Với BST, inorder traversal cho kết quả tăng dần.

## 20.3 Postorder

Thứ tự: left -> right -> root.

```java
void postorder(TreeNode root) {
    if (root == null) return;
    postorder(root.left);
    postorder(root.right);
    System.out.println(root.val);
}
```

Thường dùng khi cần xử lý con trước cha, ví dụ xóa cây hoặc tính size subtree.

## 20.4 Level order

Duyệt theo tầng, dùng queue.

```java
void levelOrder(TreeNode root) {
    if (root == null) return;

    Queue<TreeNode> queue = new LinkedList<>();
    queue.offer(root);

    while (!queue.isEmpty()) {
        TreeNode node = queue.poll();
        System.out.println(node.val);

        if (node.left != null) queue.offer(node.left);
        if (node.right != null) queue.offer(node.right);
    }
}
```

## 21. Heap và priority queue

Heap là cây nhị phân gần hoàn chỉnh thỏa mãn tính chất heap.

Min heap:

- Node cha nhỏ hơn hoặc bằng node con.
- Root là phần tử nhỏ nhất.

Max heap:

- Node cha lớn hơn hoặc bằng node con.
- Root là phần tử lớn nhất.

Priority queue thường được implement bằng heap.

Ứng dụng:

- Lấy top K phần tử.
- Scheduling.
- Dijkstra.
- Merge K sorted lists.

Độ phức tạp:

| Operation | Complexity |
|---|---|
| Peek min/max | `O(1)` |
| Insert | `O(log n)` |
| Remove min/max | `O(log n)` |

Ví dụ Java min heap:

```java
PriorityQueue<Integer> pq = new PriorityQueue<>();
pq.offer(5);
pq.offer(1);
pq.offer(3);

System.out.println(pq.poll()); // 1
```

Max heap:

```java
PriorityQueue<Integer> pq = new PriorityQueue<>((a, b) -> b - a);
```

Lưu ý: comparator kiểu `b - a` có thể overflow nếu số rất lớn. Cẩn thận hơn:

```java
PriorityQueue<Integer> pq = new PriorityQueue<>((a, b) -> Integer.compare(b, a));
```

## 22. Graph

Graph gồm:

- Vertex/node: đỉnh.
- Edge: cạnh.

Graph có thể:

- Directed: có hướng.
- Undirected: vô hướng.
- Weighted: có trọng số.
- Unweighted: không trọng số.

Ví dụ:

```text
A -- B -- C
|    |
D -- E
```

Ứng dụng backend:

- Mạng bạn bè/follow.
- Dependency giữa task.
- Route/path.
- Permission graph.
- Recommendation.

## 23. Biểu diễn graph

## 23.1 Adjacency matrix

Dùng ma trận `n x n`.

Ưu điểm:

- Kiểm tra cạnh giữa hai node `O(1)`.

Nhược điểm:

- Tốn `O(n^2)` bộ nhớ.
- Không tốt cho graph thưa.

## 23.2 Adjacency list

Dùng map/list lưu danh sách hàng xóm.

```java
Map<Integer, List<Integer>> graph = new HashMap<>();
```

Ưu điểm:

- Tiết kiệm bộ nhớ cho graph thưa.
- Duyệt neighbor tự nhiên.

Độ phức tạp bộ nhớ: `O(V + E)`.

Trong phỏng vấn, adjacency list thường dùng nhiều hơn.

## 24. BFS

BFS, viết tắt của Breadth-First Search, duyệt graph theo từng lớp, dùng queue.

Ứng dụng:

- Tìm đường đi ngắn nhất trong graph không trọng số.
- Duyệt level order tree.
- Tìm khoảng cách tối thiểu.

Code:

```java
void bfs(Map<Integer, List<Integer>> graph, int start) {
    Set<Integer> visited = new HashSet<>();
    Queue<Integer> queue = new LinkedList<>();

    visited.add(start);
    queue.offer(start);

    while (!queue.isEmpty()) {
        int node = queue.poll();
        System.out.println(node);

        for (int next : graph.getOrDefault(node, Collections.emptyList())) {
            if (!visited.contains(next)) {
                visited.add(next);
                queue.offer(next);
            }
        }
    }
}
```

Độ phức tạp:

- Time: `O(V + E)`.
- Space: `O(V)`.

## 25. DFS

DFS, viết tắt của Depth-First Search, đi sâu trước, có thể dùng recursion hoặc stack.

Ứng dụng:

- Detect cycle.
- Topological sort.
- Connected components.
- Backtracking.
- Duyệt tree.

Recursive DFS:

```java
void dfs(Map<Integer, List<Integer>> graph, int node, Set<Integer> visited) {
    if (visited.contains(node)) return;

    visited.add(node);
    System.out.println(node);

    for (int next : graph.getOrDefault(node, Collections.emptyList())) {
        dfs(graph, next, visited);
    }
}
```

Iterative DFS:

```java
void dfsIterative(Map<Integer, List<Integer>> graph, int start) {
    Set<Integer> visited = new HashSet<>();
    Stack<Integer> stack = new Stack<>();
    stack.push(start);

    while (!stack.isEmpty()) {
        int node = stack.pop();
        if (visited.contains(node)) continue;

        visited.add(node);
        System.out.println(node);

        for (int next : graph.getOrDefault(node, Collections.emptyList())) {
            if (!visited.contains(next)) {
                stack.push(next);
            }
        }
    }
}
```

Độ phức tạp:

- Time: `O(V + E)`.
- Space: `O(V)`.

## 26. Sorting

Sorting là sắp xếp dữ liệu theo thứ tự.

Các thuật toán thường gặp:

| Algorithm | Best | Average | Worst | Stable |
|---|---|---|---|---|
| Bubble sort | `O(n)` | `O(n^2)` | `O(n^2)` | Có |
| Selection sort | `O(n^2)` | `O(n^2)` | `O(n^2)` | Không mặc định |
| Insertion sort | `O(n)` | `O(n^2)` | `O(n^2)` | Có |
| Merge sort | `O(n log n)` | `O(n log n)` | `O(n log n)` | Có |
| Quick sort | `O(n log n)` | `O(n log n)` | `O(n^2)` | Không mặc định |
| Heap sort | `O(n log n)` | `O(n log n)` | `O(n log n)` | Không |

Trong phỏng vấn intern:

- Hiểu được bubble/selection/insertion ở mức cơ bản.
- Nắm merge sort, quick sort ý tưởng.
- Biết built-in sort thường đã tối ưu, không nên tự viết trong production nếu không cần.

## 27. Bubble sort

Ý tưởng: liên tục so sánh hai phần tử kề nhau và đổi chỗ nếu sai thứ tự.

```java
void bubbleSort(int[] a) {
    int n = a.length;
    for (int i = 0; i < n - 1; i++) {
        for (int j = 0; j < n - 1 - i; j++) {
            if (a[j] > a[j + 1]) {
                int temp = a[j];
                a[j] = a[j + 1];
                a[j + 1] = temp;
            }
        }
    }
}
```

Time: `O(n^2)`.

## 28. Selection sort

Ý tưởng: mỗi lần chọn phần tử nhỏ nhất còn lại đưa về đầu.

```java
void selectionSort(int[] a) {
    int n = a.length;
    for (int i = 0; i < n - 1; i++) {
        int minIndex = i;
        for (int j = i + 1; j < n; j++) {
            if (a[j] < a[minIndex]) {
                minIndex = j;
            }
        }
        int temp = a[i];
        a[i] = a[minIndex];
        a[minIndex] = temp;
    }
}
```

Time: `O(n^2)`.

## 29. Insertion sort

Ý tưởng: chia array thành phần đã sorted và chưa sorted, lần lượt chèn phần tử vào đúng vị trí.

```java
void insertionSort(int[] a) {
    for (int i = 1; i < a.length; i++) {
        int key = a[i];
        int j = i - 1;

        while (j >= 0 && a[j] > key) {
            a[j + 1] = a[j];
            j--;
        }

        a[j + 1] = key;
    }
}
```

Time:

- Best: `O(n)` nếu gần như đã sorted.
- Worst: `O(n^2)`.

## 30. Merge sort

Ý tưởng divide and conquer:

1. Chia array thành hai nửa.
2. Sort từng nửa.
3. Merge hai nửa đã sorted.

```text
[5, 2, 4, 1]
-> [5, 2] [4, 1]
-> [5] [2] [4] [1]
-> [2, 5] [1, 4]
-> [1, 2, 4, 5]
```

Độ phức tạp:

- Time: `O(n log n)`.
- Space: `O(n)`.

Ưu điểm:

- Worst-case tốt.
- Stable.

Nhược điểm:

- Tốn thêm bộ nhớ.

## 31. Quick sort

Ý tưởng:

1. Chọn pivot.
2. Partition array: phần nhỏ hơn pivot, phần lớn hơn pivot.
3. Đệ quy sort hai phần.

Độ phức tạp:

- Average: `O(n log n)`.
- Worst: `O(n^2)` nếu pivot xấu liên tục.
- Space: `O(log n)` trung bình do recursion stack.

Lưu ý:

- Quick sort thường nhanh trong thực tế.
- Chọn pivot tốt hoặc random pivot giúp giảm worst-case.

## 32. Binary search

Binary search tìm kiếm trên dữ liệu đã sorted.

Ý tưởng:

1. Kiểm tra phần tử giữa.
2. Nếu bằng target, trả kết quả.
3. Nếu target nhỏ hơn, tìm nửa trái.
4. Nếu target lớn hơn, tìm nửa phải.

Code:

```java
int binarySearch(int[] a, int target) {
    int left = 0;
    int right = a.length - 1;

    while (left <= right) {
        int mid = left + (right - left) / 2;

        if (a[mid] == target) {
            return mid;
        } else if (a[mid] < target) {
            left = mid + 1;
        } else {
            right = mid - 1;
        }
    }

    return -1;
}
```

Độ phức tạp:

- Time: `O(log n)`.
- Space: `O(1)`.

Cẩn thận:

```java
int mid = (left + right) / 2;
```

Có thể overflow nếu `left + right` quá lớn. Dùng:

```java
int mid = left + (right - left) / 2;
```

## 33. Two pointers

Two pointers dùng hai con trỏ để xử lý array/string hiệu quả.

Thường dùng khi:

- Array đã sorted.
- Cần tìm cặp phần tử.
- Reverse string/array.
- Remove duplicate.
- Palindrome.

Ví dụ kiểm tra palindrome:

```java
boolean isPalindrome(String s) {
    int left = 0;
    int right = s.length() - 1;

    while (left < right) {
        if (s.charAt(left) != s.charAt(right)) {
            return false;
        }
        left++;
        right--;
    }

    return true;
}
```

Time: `O(n)`.

Space: `O(1)`.

## 34. Sliding window

Sliding window dùng một cửa sổ trượt trên array/string để tối ưu bài toán subarray/substring.

Ví dụ tính tổng lớn nhất của subarray độ dài `k`.

Brute force: `O(n*k)`.

Tối ưu:

```java
int maxSum(int[] nums, int k) {
    int windowSum = 0;

    for (int i = 0; i < k; i++) {
        windowSum += nums[i];
    }

    int max = windowSum;

    for (int right = k; right < nums.length; right++) {
        windowSum += nums[right];
        windowSum -= nums[right - k];
        max = Math.max(max, windowSum);
    }

    return max;
}
```

Time: `O(n)`.

Ứng dụng:

- Longest substring.
- Max/min subarray fixed size.
- Rate limiting theo time window.

## 35. Prefix sum

Prefix sum lưu tổng tích lũy để query tổng đoạn nhanh.

Ví dụ:

```text
nums = [2, 4, 1, 3]
prefix = [0, 2, 6, 7, 10]
```

Tổng đoạn `[l, r]`:

```text
prefix[r + 1] - prefix[l]
```

Code:

```java
int[] buildPrefix(int[] nums) {
    int[] prefix = new int[nums.length + 1];
    for (int i = 0; i < nums.length; i++) {
        prefix[i + 1] = prefix[i] + nums[i];
    }
    return prefix;
}

int rangeSum(int[] prefix, int left, int right) {
    return prefix[right + 1] - prefix[left];
}
```

Build: `O(n)`.

Query: `O(1)`.

## 36. Recursion

Recursion là hàm gọi lại chính nó.

Một recursive function cần:

- Base case: điều kiện dừng.
- Recursive case: gọi lại với bài toán nhỏ hơn.

Ví dụ factorial:

```java
int factorial(int n) {
    if (n <= 1) return 1;
    return n * factorial(n - 1);
}
```

Lưu ý:

- Recursion dùng stack.
- Nếu recursion quá sâu có thể stack overflow.
- Cần đảm bảo tiến dần đến base case.

## 37. Backtracking

Backtracking thử các khả năng, nếu sai thì quay lui.

Ứng dụng:

- Sinh subset.
- Sinh permutation.
- N-Queens.
- Sudoku.
- Combination.

Ví dụ sinh subset:

```java
void backtrack(int[] nums, int index, List<Integer> current, List<List<Integer>> result) {
    if (index == nums.length) {
        result.add(new ArrayList<>(current));
        return;
    }

    backtrack(nums, index + 1, current, result);

    current.add(nums[index]);
    backtrack(nums, index + 1, current, result);
    current.remove(current.size() - 1);
}
```

Với `n` phần tử, số subset là `2^n`.

Time: `O(2^n)`.

## 38. Dynamic programming

Dynamic programming, hay DP, dùng khi bài toán có:

- Overlapping subproblems: bài toán con lặp lại.
- Optimal substructure: lời giải tối ưu có thể xây từ lời giải bài toán con.

Ví dụ Fibonacci naive:

```java
int fib(int n) {
    if (n <= 1) return n;
    return fib(n - 1) + fib(n - 2);
}
```

Time: `O(2^n)`, vì tính lại nhiều lần.

## 39. Memoization

Memoization là lưu kết quả đã tính để tránh tính lại.

```java
int fib(int n, Map<Integer, Integer> memo) {
    if (n <= 1) return n;
    if (memo.containsKey(n)) return memo.get(n);

    int result = fib(n - 1, memo) + fib(n - 2, memo);
    memo.put(n, result);
    return result;
}
```

Time: `O(n)`.

Space: `O(n)`.

## 40. Tabulation

Tabulation là DP bottom-up, tính từ bài toán nhỏ lên lớn.

```java
int fib(int n) {
    if (n <= 1) return n;

    int[] dp = new int[n + 1];
    dp[0] = 0;
    dp[1] = 1;

    for (int i = 2; i <= n; i++) {
        dp[i] = dp[i - 1] + dp[i - 2];
    }

    return dp[n];
}
```

Có thể tối ưu space:

```java
int fib(int n) {
    if (n <= 1) return n;

    int prev2 = 0;
    int prev1 = 1;

    for (int i = 2; i <= n; i++) {
        int current = prev1 + prev2;
        prev2 = prev1;
        prev1 = current;
    }

    return prev1;
}
```

Time: `O(n)`.

Space: `O(1)`.

## 41. Greedy

Greedy chọn phương án tốt nhất tại thời điểm hiện tại, hy vọng tạo ra lời giải tối ưu toàn cục.

Ứng dụng:

- Interval scheduling.
- Coin change với một số hệ coin chuẩn.
- Huffman coding.
- Minimum spanning tree.

Lưu ý:

- Không phải bài nào chọn local optimum cũng đúng.
- Cần chứng minh hoặc hiểu vì sao greedy đúng.

Ví dụ chọn nhiều meeting nhất không overlap:

1. Sort meeting theo end time tăng dần.
2. Luôn chọn meeting kết thúc sớm nhất có thể.

## 42. Divide and conquer

Divide and conquer chia bài toán lớn thành bài toán nhỏ, giải từng phần rồi combine.

Ví dụ:

- Merge sort.
- Quick sort.
- Binary search.

Mẫu:

```text
solve(problem):
    if problem small:
        return answer
    split problem
    solve subproblems
    combine results
```

## 43. Common patterns trong coding interview

## 43.1 HashMap/Set

Dùng khi thấy:

- "Tìm trong quá khứ đã gặp chưa?"
- "Đếm số lần xuất hiện."
- "Có duplicate không?"
- "Tìm cặp có tổng bằng target."

## 43.2 Two pointers

Dùng khi thấy:

- Sorted array.
- Palindrome.
- Cần tìm pair/triplet.
- Remove duplicate in-place.

## 43.3 Sliding window

Dùng khi thấy:

- Subarray/substring liên tiếp.
- Longest/shortest window.
- Fixed-size window.

## 43.4 BFS

Dùng khi thấy:

- Shortest path trong graph không trọng số.
- Level order.
- Minimum steps.

## 43.5 DFS/backtracking

Dùng khi thấy:

- Duyệt mọi khả năng.
- Tree/graph traversal.
- Connected components.
- Combination/permutation/subset.

## 43.6 Binary search

Dùng khi thấy:

- Array sorted.
- Tìm minimum/maximum thỏa điều kiện.
- Search space có tính monotonic.

## 44. Một số bài tập kinh điển

## 44.1 Valid Parentheses

Bài toán:

> Kiểm tra chuỗi ngoặc `()[]{}` có hợp lệ không.

Ý tưởng: dùng stack.

```java
boolean isValid(String s) {
    Stack<Character> stack = new Stack<>();

    for (char c : s.toCharArray()) {
        if (c == '(' || c == '[' || c == '{') {
            stack.push(c);
        } else {
            if (stack.isEmpty()) return false;
            char top = stack.pop();

            if (c == ')' && top != '(') return false;
            if (c == ']' && top != '[') return false;
            if (c == '}' && top != '{') return false;
        }
    }

    return stack.isEmpty();
}
```

Time: `O(n)`.

Space: `O(n)`.

## 44.2 Reverse linked list

Bài toán:

> Đảo chiều linked list.

```java
ListNode reverseList(ListNode head) {
    ListNode prev = null;
    ListNode current = head;

    while (current != null) {
        ListNode next = current.next;
        current.next = prev;
        prev = current;
        current = next;
    }

    return prev;
}
```

Time: `O(n)`.

Space: `O(1)`.

## 44.3 Maximum depth of binary tree

```java
int maxDepth(TreeNode root) {
    if (root == null) return 0;
    return 1 + Math.max(maxDepth(root.left), maxDepth(root.right));
}
```

Time: `O(n)`.

Space: `O(h)`, với `h` là chiều cao cây do recursion stack.

## 44.4 Contains duplicate

```java
boolean containsDuplicate(int[] nums) {
    Set<Integer> seen = new HashSet<>();

    for (int num : nums) {
        if (seen.contains(num)) {
            return true;
        }
        seen.add(num);
    }

    return false;
}
```

Time: `O(n)`.

Space: `O(n)`.

## 44.5 Merge two sorted lists

```java
ListNode mergeTwoLists(ListNode l1, ListNode l2) {
    ListNode dummy = new ListNode(0);
    ListNode current = dummy;

    while (l1 != null && l2 != null) {
        if (l1.val <= l2.val) {
            current.next = l1;
            l1 = l1.next;
        } else {
            current.next = l2;
            l2 = l2.next;
        }
        current = current.next;
    }

    if (l1 != null) current.next = l1;
    if (l2 != null) current.next = l2;

    return dummy.next;
}
```

Time: `O(n + m)`.

Space: `O(1)`.

## 45. Cấu trúc dữ liệu trong backend thực tế

## 45.1 HashMap cho cache

Ví dụ cache user theo id:

```java
Map<Long, User> userCache = new HashMap<>();
```

Nhưng trong production cần chú ý:

- Giới hạn size.
- Expiration.
- Thread safety.
- Distributed cache nếu nhiều instance.

Thường dùng Redis/Caffeine thay vì tự viết cache đơn giản.

## 45.2 Queue cho background job

Ví dụ:

- User đăng ký.
- API trả response nhanh.
- Gửi email welcome qua background job.

Ý tưởng:

```text
API -> enqueue job -> worker xử lý
```

## 45.3 Tree trong database index

Database index thường dùng cấu trúc dạng B-Tree/B+Tree.

Ý nghĩa:

- Tìm kiếm theo key nhanh hơn full table scan.
- Hỗ trợ range query tốt.

Ví dụ:

```sql
CREATE INDEX idx_users_email ON users(email);
```

## 45.4 Graph trong quan hệ social

Ví dụ:

- User follow user.
- Friend recommendation.
- Mutual friends.

Schema đơn giản:

```sql
CREATE TABLE follows (
    follower_id BIGINT NOT NULL,
    following_id BIGINT NOT NULL,
    PRIMARY KEY (follower_id, following_id)
);
```

Đây là graph có hướng.

## 46. Trade-off thường gặp

## 46.1 Time vs space

Dùng thêm bộ nhớ để chạy nhanh hơn.

Ví dụ Two Sum:

- Brute force: time `O(n^2)`, space `O(1)`.
- HashMap: time `O(n)`, space `O(n)`.

## 46.2 Read vs write

Tạo index giúp đọc nhanh nhưng ghi chậm hơn.

## 46.3 Simplicity vs performance

Không phải lúc nào cũng cần thuật toán phức tạp. Nếu input nhỏ, code dễ hiểu có thể tốt hơn.

Câu trả lời phỏng vấn:

> Em sẽ bắt đầu bằng giải pháp đúng và dễ hiểu, phân tích Big O, sau đó tối ưu nếu độ phức tạp chưa phù hợp với constraint.

## 47. Cách giải bài coding interview

Khung trả lời:

1. Làm rõ đề và input/output.
2. Hỏi constraint nếu chưa có.
3. Đưa brute force trước nếu cần.
4. Phân tích Big O.
5. Tìm pattern/cấu trúc dữ liệu phù hợp.
6. Viết code sạch.
7. Test bằng ví dụ thường và edge case.
8. Nói lại time/space complexity.

Ví dụ edge case cần nghĩ:

- Array rỗng.
- Một phần tử.
- Có duplicate.
- Số âm.
- Target không tồn tại.
- String rỗng.
- Tree null.
- Graph có cycle.
- Input rất lớn.

## 48. Câu hỏi phỏng vấn thường gặp

## 48.1 Lý thuyết

1. Big O là gì?
2. Time complexity và space complexity khác nhau thế nào?
3. Array khác linked list thế nào?
4. Stack và queue khác nhau thế nào?
5. HashMap hoạt động như thế nào?
6. Hash collision là gì?
7. HashSet dùng để làm gì?
8. Binary tree và binary search tree khác nhau thế nào?
9. BFS khác DFS thế nào?
10. Merge sort khác quick sort thế nào?
11. Binary search dùng khi nào?
12. Recursion cần điều kiện gì để không chạy vô hạn?
13. Dynamic programming dùng khi nào?
14. Greedy có luôn đúng không?
15. Heap/priority queue dùng khi nào?

## 48.2 Câu hỏi backend thực tế

1. Vì sao HashMap phù hợp để làm cache đơn giản?
2. Queue trong backend dùng để làm gì?
3. Vì sao database index giúp search nhanh hơn?
4. Vì sao index làm write chậm hơn?
5. Khi nào nên dùng set để deduplicate dữ liệu?
6. Làm sao tìm duplicate trong danh sách email?
7. Làm sao xử lý top K sản phẩm bán chạy?
8. Làm sao duyệt quan hệ follow/friend?
9. Rate limiting có thể dùng cấu trúc dữ liệu nào?
10. Khi nào cần trade-off time và space?

## 49. Câu trả lời mẫu ngắn

### Big O là gì?

Big O mô tả cách thời gian chạy hoặc bộ nhớ tăng theo kích thước input. Ví dụ duyệt một array có `n` phần tử là `O(n)`, binary search trên array sorted là `O(log n)`.

### Array khác linked list?

Array lưu phần tử liên tiếp và truy cập theo index `O(1)`, nhưng insert/delete giữa chậm `O(n)`. Linked list truy cập index `O(n)`, nhưng insert/delete sau node đã biết có thể `O(1)`.

### Stack khác queue?

Stack là LIFO, phần tử vào sau ra trước. Queue là FIFO, phần tử vào trước ra trước.

### HashMap hoạt động thế nào?

HashMap dùng hash function để map key vào bucket. Trung bình insert/search/delete là `O(1)`, nhưng nếu collision nhiều thì có thể chậm hơn.

### BFS khác DFS?

BFS duyệt theo từng lớp và dùng queue, phù hợp tìm shortest path trong graph không trọng số. DFS đi sâu trước, dùng recursion hoặc stack, phù hợp duyệt cây, backtracking, detect cycle.

### Khi nào dùng binary search?

Dùng khi dữ liệu sorted hoặc search space có tính monotonic, tức nếu một điều kiện đúng ở một điểm thì có thể suy ra hướng tìm tiếp.

### DP dùng khi nào?

DP dùng khi bài toán có bài toán con lặp lại và lời giải lớn có thể xây từ lời giải nhỏ. Ví dụ Fibonacci, climbing stairs, knapsack cơ bản.

## 50. Checklist trước khi đi phỏng vấn

Bạn nên tự trả lời được:

- Big O của một đoạn code có vòng lặp.
- Phân biệt `O(1)`, `O(log n)`, `O(n)`, `O(n log n)`, `O(n^2)`.
- Array, linked list, stack, queue, hash map, set dùng khi nào.
- Viết được Two Sum bằng HashMap.
- Viết được binary search.
- Viết được valid parentheses bằng stack.
- Viết được BFS/DFS cơ bản.
- Hiểu tree traversal: preorder, inorder, postorder, level order.
- Hiểu heap/priority queue dùng để lấy top K.
- Biết merge sort/quick sort ở mức ý tưởng.
- Biết two pointers, sliding window, prefix sum.
- Hiểu recursion, backtracking, DP cơ bản.
- Luôn phân tích time/space complexity sau khi giải bài.

## 51. Lộ trình luyện nhanh cho intern backend

Ưu tiên học theo thứ tự:

1. Big O.
2. Array/string.
3. HashMap/HashSet.
4. Stack/Queue.
5. Two pointers.
6. Sliding window.
7. Binary search.
8. Linked list.
9. Tree traversal.
10. BFS/DFS.
11. Sorting cơ bản.
12. Heap/PriorityQueue.
13. Recursion/backtracking.
14. DP cơ bản.

Bài nên luyện:

- Two Sum.
- Contains Duplicate.
- Valid Parentheses.
- Merge Two Sorted Lists.
- Reverse Linked List.
- Binary Search.
- Maximum Depth of Binary Tree.
- Level Order Traversal.
- Number of Islands.
- Top K Frequent Elements.
- Best Time to Buy and Sell Stock.
- Longest Substring Without Repeating Characters.

## 52. Tóm tắt cực ngắn

- DSA giúp chọn cách lưu và xử lý dữ liệu hiệu quả.
- Big O giúp phân tích code có chạy tốt khi input lớn không.
- Array truy cập nhanh theo index, linked list insert/delete theo node nhanh hơn.
- Stack là LIFO, queue là FIFO.
- HashMap/HashSet rất mạnh cho lookup, counting, deduplicate.
- Tree/graph cần biết BFS và DFS.
- Binary search cần dữ liệu sorted hoặc điều kiện monotonic.
- Sliding window và two pointers là pattern rất hay gặp với array/string.
- DP dùng khi có bài toán con lặp lại.
- Khi phỏng vấn, luôn giải thích ý tưởng, code, test edge case và phân tích time/space.
