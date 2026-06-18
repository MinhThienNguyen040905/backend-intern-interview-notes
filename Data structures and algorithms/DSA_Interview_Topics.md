# DSA cho phỏng vấn

Tài liệu này tổng hợp các chủ đề Data Structures and Algorithms thường gặp trong phỏng vấn lập trình. Mục tiêu là giúp bạn biết cần học gì, nhận diện dạng bài, phân tích độ phức tạp và luyện cách trình bày lời giải rõ ràng.

## 1. Cách tiếp cận bài DSA trong phỏng vấn

Khi nhận một bài coding interview, nên đi theo thứ tự:

1. Làm rõ input, output và ràng buộc.
2. Hỏi về dữ liệu đặc biệt: rỗng, trùng lặp, số âm, overflow, case sensitivity.
3. Nêu brute force trước nếu cần.
4. Phân tích điểm nghẽn của brute force.
5. Đề xuất tối ưu bằng cấu trúc dữ liệu hoặc pattern phù hợp.
6. Phân tích time complexity và space complexity.
7. Code sạch, đặt tên biến rõ, xử lý edge cases.
8. Chạy thử bằng ví dụ nhỏ và một vài edge cases.

## 2. Big O và phân tích độ phức tạp

Big O mô tả tốc độ tăng chi phí khi kích thước input tăng.

| Complexity | Ý nghĩa | Ví dụ |
|---|---|---|
| `O(1)` | Hằng số | Truy cập array theo index |
| `O(log n)` | Logarithmic | Binary search |
| `O(n)` | Tuyến tính | Duyệt mảng một lần |
| `O(n log n)` | Tuyến tính log | Merge sort, heap sort |
| `O(n^2)` | Bình phương | Hai vòng lặp lồng nhau |
| `O(2^n)` | Hàm mũ | Sinh mọi subset bằng brute force |
| `O(n!)` | Giai thừa | Sinh mọi hoán vị |

Các quy tắc quan trọng:

- Bỏ hằng số: `O(2n)` thành `O(n)`.
- Giữ phần tăng nhanh nhất: `O(n + n^2)` thành `O(n^2)`.
- Vòng lặp nối tiếp thường cộng complexity.
- Vòng lặp lồng nhau thường nhân complexity.
- Đệ quy cần xét số nhánh và độ sâu cây đệ quy.

## 3. Array và String

### Kiến thức cần nắm

- Truy cập theo index.
- Duyệt trái sang phải, phải sang trái.
- Subarray, subsequence, substring.
- Prefix sum.
- Sliding window.
- Two pointers.
- In-place modification.
- Sorting trước khi xử lý.

### Complexity thường gặp

| Operation | Array | String |
|---|---|---|
| Access by index | `O(1)` | `O(1)` |
| Search | `O(n)` | `O(n)` |
| Insert/delete giữa mảng | `O(n)` | thường `O(n)` |
| Append động | trung bình `O(1)` | tùy ngôn ngữ |

### Dạng bài phổ biến

- Tìm phần tử lớn nhất, nhỏ nhất, lớn thứ hai.
- Xoay mảng.
- Gộp hai mảng đã sort.
- Remove duplicates.
- Maximum subarray.
- Product except self.
- Longest substring without repeating characters.
- Valid palindrome.
- Anagram.
- Encode/decode string.

## 4. Linked List

### Kiến thức cần nắm

- Singly linked list.
- Doubly linked list.
- Fast and slow pointers.
- Dummy node.
- Reverse linked list.
- Detect cycle.
- Merge linked lists.

### Complexity

| Operation | Complexity |
|---|---|
| Access by index | `O(n)` |
| Search | `O(n)` |
| Insert/delete nếu đã có node | `O(1)` |
| Insert/delete theo vị trí | `O(n)` |

### Dạng bài phổ biến

- Reverse linked list.
- Detect cycle.
- Find middle node.
- Merge two sorted lists.
- Remove nth node from end.
- Palindrome linked list.
- Add two numbers.
- Reorder list.
- Intersection of two linked lists.

## 5. Stack

Stack hoạt động theo nguyên tắc LIFO: phần tử vào sau ra trước.

### Khi nào nghĩ đến Stack?

- Cần quay lại trạng thái trước đó.
- Cần kiểm tra cặp mở/đóng.
- Cần xử lý phần tử gần nhất chưa được giải quyết.
- Cần mô phỏng recursion.
- Cần tìm next greater/smaller element.

### Dạng bài phổ biến

- Valid parentheses.
- Min stack.
- Evaluate reverse Polish notation.
- Daily temperatures.
- Next greater element.
- Largest rectangle in histogram.
- Decode string.
- Browser history, undo/redo.

## 6. Queue và Deque

Queue hoạt động theo nguyên tắc FIFO: vào trước ra trước.

### Kiến thức cần nắm

- Queue thường.
- Circular queue.
- Deque.
- Monotonic queue.
- BFS dùng queue.

### Dạng bài phổ biến

- Implement queue using stacks.
- Implement stack using queues.
- Number of islands bằng BFS.
- Level order traversal.
- Sliding window maximum.
- Rotten oranges.
- Task scheduling.

## 7. Hash Table, Hash Map và Hash Set

Hash map/hash set giúp tra cứu trung bình `O(1)`.

### Khi nào nghĩ đến Hash?

- Cần kiểm tra một phần tử đã xuất hiện chưa.
- Cần đếm tần suất.
- Cần ánh xạ key sang value.
- Cần tìm cặp có tổng bằng một giá trị.
- Cần nhóm các phần tử theo đặc điểm.

### Dạng bài phổ biến

- Two sum.
- Contains duplicate.
- Valid anagram.
- Group anagrams.
- Top K frequent elements.
- Longest consecutive sequence.
- Subarray sum equals K.
- First unique character.
- LRU cache kết hợp hash map và doubly linked list.

### Lưu ý phỏng vấn

- Trung bình `O(1)`, xấu nhất có thể `O(n)` nếu collision nhiều.
- Cần biết hash key phù hợp, nhất là với object hoặc pair.
- Dùng set khi chỉ cần tồn tại, dùng map khi cần lưu value hoặc count.

## 8. Sorting

### Thuật toán cần biết

| Algorithm | Average | Worst | Space | Stable |
|---|---:|---:|---:|---|
| Bubble sort | `O(n^2)` | `O(n^2)` | `O(1)` | Có |
| Selection sort | `O(n^2)` | `O(n^2)` | `O(1)` | Không |
| Insertion sort | `O(n^2)` | `O(n^2)` | `O(1)` | Có |
| Merge sort | `O(n log n)` | `O(n log n)` | `O(n)` | Có |
| Quick sort | `O(n log n)` | `O(n^2)` | `O(log n)` | Không |
| Heap sort | `O(n log n)` | `O(n log n)` | `O(1)` | Không |
| Counting sort | `O(n + k)` | `O(n + k)` | `O(k)` | Có thể |

### Dạng bài phổ biến

- Sort intervals.
- Merge intervals.
- Meeting rooms.
- Kth largest element.
- Sort colors.
- Custom comparator.
- Minimum number of arrows to burst balloons.

## 9. Binary Search

Binary search không chỉ dùng cho mảng đã sort, mà còn dùng cho không gian đáp án.

### Điều kiện

- Dữ liệu đã sort, hoặc
- Có hàm kiểm tra `can(x)` mang tính đơn điệu: nếu `x` đúng thì các giá trị lớn hơn hoặc nhỏ hơn cũng đúng.

### Dạng bài phổ biến

- Search in sorted array.
- Search insert position.
- First and last position of element.
- Search in rotated sorted array.
- Find minimum in rotated sorted array.
- Find peak element.
- Koko eating bananas.
- Capacity to ship packages.
- Median of two sorted arrays.

## 10. Two Pointers

Two pointers dùng hai con trỏ để giảm số lần duyệt.

### Mẫu thường gặp

- Hai đầu mảng tiến vào giữa.
- Một con trỏ đọc, một con trỏ ghi.
- Hai mảng đã sort.
- Slow/fast pointer trong linked list.

### Dạng bài phổ biến

- Two sum II.
- 3Sum.
- Container with most water.
- Trapping rain water.
- Valid palindrome.
- Remove duplicates from sorted array.
- Merge sorted array.
- Move zeroes.

## 11. Sliding Window

Sliding window xử lý subarray hoặc substring liên tiếp.

### Khi nào dùng?

- Cần tìm đoạn liên tiếp.
- Cần tối ưu tổng, độ dài, tần suất trong một cửa sổ.
- Có thể mở rộng và thu hẹp cửa sổ.

### Dạng bài phổ biến

- Maximum sum subarray of size K.
- Minimum size subarray sum.
- Longest substring without repeating characters.
- Longest repeating character replacement.
- Permutation in string.
- Minimum window substring.
- Sliding window maximum.

## 12. Prefix Sum và Difference Array

Prefix sum giúp tính tổng đoạn trong `O(1)` sau khi tiền xử lý.

### Công thức

Với `prefix[i]` là tổng các phần tử trước index `i`:

```text
sum(l, r) = prefix[r + 1] - prefix[l]
```

### Dạng bài phổ biến

- Range sum query.
- Subarray sum equals K.
- Product of array except self.
- Find pivot index.
- Corporate flight bookings.
- Range update bằng difference array.
- Matrix block sum.

## 13. Tree

Tree là cấu trúc phân cấp gồm node cha và node con.

### Kiến thức cần nắm

- Binary tree.
- Binary search tree.
- Balanced tree.
- Tree traversal: preorder, inorder, postorder, level order.
- DFS recursion.
- BFS queue.
- Height/depth.
- Lowest common ancestor.

### Complexity thường gặp

| Operation | Binary Search Tree trung bình | BST xấu nhất |
|---|---:|---:|
| Search | `O(log n)` | `O(n)` |
| Insert | `O(log n)` | `O(n)` |
| Delete | `O(log n)` | `O(n)` |

### Dạng bài phổ biến

- Maximum depth of binary tree.
- Same tree.
- Invert binary tree.
- Binary tree level order traversal.
- Validate binary search tree.
- Lowest common ancestor.
- Diameter of binary tree.
- Path sum.
- Serialize and deserialize binary tree.
- Kth smallest element in BST.

## 14. Heap và Priority Queue

Heap giúp lấy phần tử nhỏ nhất hoặc lớn nhất nhanh.

### Complexity

| Operation | Complexity |
|---|---|
| Peek min/max | `O(1)` |
| Push | `O(log n)` |
| Pop | `O(log n)` |
| Build heap | `O(n)` |

### Khi nào dùng?

- Cần lấy top K.
- Cần luôn xử lý phần tử ưu tiên cao nhất.
- Cần merge nhiều nguồn đã sort.
- Cần median động.

### Dạng bài phổ biến

- Kth largest element.
- Top K frequent elements.
- Merge K sorted lists.
- Find median from data stream.
- Task scheduler.
- Meeting rooms II.
- Last stone weight.
- Reorganize string.

## 15. Trie

Trie là cây tiền tố, thường dùng cho string.

### Khi nào dùng?

- Tìm kiếm từ theo prefix.
- Autocomplete.
- Dictionary word search.
- Cần xử lý nhiều chuỗi có chung tiền tố.

### Dạng bài phổ biến

- Implement trie.
- Word search II.
- Design add and search words data structure.
- Replace words.
- Longest word in dictionary.

## 16. Graph

Graph gồm vertex và edge, có thể có hướng hoặc vô hướng, có trọng số hoặc không trọng số.

### Cách biểu diễn

- Adjacency list: phổ biến, tiết kiệm bộ nhớ.
- Adjacency matrix: dễ kiểm tra cạnh, tốn `O(V^2)`.
- Edge list: tiện cho Kruskal hoặc Bellman-Ford.

### Thuật toán cần biết

- DFS.
- BFS.
- Topological sort.
- Union Find.
- Dijkstra.
- Bellman-Ford.
- Floyd-Warshall.
- Minimum spanning tree: Kruskal, Prim.

### Dạng bài phổ biến

- Number of islands.
- Clone graph.
- Course schedule.
- Pacific Atlantic water flow.
- Rotting oranges.
- Word ladder.
- Network delay time.
- Cheapest flights within K stops.
- Redundant connection.
- Number of connected components.

## 17. Union Find

Union Find, còn gọi là Disjoint Set Union, dùng để quản lý các tập rời nhau.

### Kỹ thuật tối ưu

- Path compression.
- Union by rank hoặc union by size.

### Dạng bài phổ biến

- Detect cycle trong graph vô hướng.
- Number of connected components.
- Redundant connection.
- Accounts merge.
- Kruskal minimum spanning tree.
- Friend circles.

## 18. Recursion và Backtracking

Backtracking thử từng lựa chọn, quay lui khi lựa chọn không còn phù hợp.

### Khi nào dùng?

- Cần sinh tổ hợp, hoán vị, subset.
- Cần thử nhiều trạng thái.
- Cần tìm tất cả lời giải.
- Constraint nhỏ.

### Dạng bài phổ biến

- Subsets.
- Permutations.
- Combinations.
- Combination sum.
- Generate parentheses.
- Letter combinations of a phone number.
- N-Queens.
- Sudoku solver.
- Word search.

### Cách trình bày

- Xác định state.
- Xác định choices.
- Xác định điều kiện dừng.
- Xác định cách undo lựa chọn.

## 19. Dynamic Programming

Dynamic Programming dùng khi bài toán có overlapping subproblems và optimal substructure.

### Cách nghĩ

1. Định nghĩa state.
2. Viết recurrence.
3. Xác định base cases.
4. Chọn top-down memoization hoặc bottom-up tabulation.
5. Tối ưu space nếu có thể.

### Dạng DP phổ biến

- 1D DP.
- 2D DP.
- Knapsack.
- Longest common subsequence.
- Longest increasing subsequence.
- Interval DP.
- Tree DP.
- Bitmask DP.

### Bài thường gặp

- Climbing stairs.
- House robber.
- Coin change.
- Longest increasing subsequence.
- Longest common subsequence.
- Edit distance.
- Unique paths.
- Word break.
- Partition equal subset sum.
- Palindromic substrings.
- Best time to buy and sell stock.

## 20. Greedy

Greedy chọn phương án tốt nhất tại thời điểm hiện tại.

### Khi nào dùng?

- Có thể chứng minh lựa chọn cục bộ dẫn đến tối ưu toàn cục.
- Bài có tính chất exchange argument.
- Bài về interval, scheduling, sorting theo tiêu chí.

### Dạng bài phổ biến

- Jump game.
- Gas station.
- Assign cookies.
- Non-overlapping intervals.
- Merge intervals.
- Minimum number of arrows.
- Partition labels.
- Queue reconstruction by height.
- Task scheduler.

## 21. Bit Manipulation

Bit manipulation dùng phép toán nhị phân để tối ưu hoặc biểu diễn trạng thái.

### Phép toán cần biết

| Operator | Ý nghĩa |
|---|---|
| `&` | AND |
| `|` | OR |
| `^` | XOR |
| `~` | NOT |
| `<<` | Shift left |
| `>>` | Shift right |

### Mẹo thường gặp

- `x & 1`: kiểm tra chẵn lẻ.
- `x & (x - 1)`: xóa bit 1 thấp nhất.
- `x ^ x = 0`.
- `x ^ 0 = x`.
- XOR dùng để tìm phần tử xuất hiện một lần.

### Dạng bài phổ biến

- Single number.
- Number of 1 bits.
- Counting bits.
- Reverse bits.
- Missing number.
- Sum of two integers.
- Subsets bằng bitmask.

## 22. Math và Number Theory

### Chủ đề cần biết

- GCD và LCM.
- Prime number.
- Sieve of Eratosthenes.
- Modular arithmetic.
- Fast exponentiation.
- Combinatorics cơ bản.
- Overflow integer.
- Randomization cơ bản.

### Dạng bài phổ biến

- Count primes.
- Pow(x, n).
- Happy number.
- Plus one.
- Add binary.
- Multiply strings.
- Excel sheet column number.
- Permutation/combination counting.

## 23. Interval Problems

Interval là dạng bài rất hay gặp trong phỏng vấn.

### Pattern chính

- Sort theo start hoặc end.
- Merge nếu overlap.
- Đếm số phòng hoặc tài nguyên cần dùng.
- Greedy chọn interval kết thúc sớm nhất.

### Dạng bài phổ biến

- Merge intervals.
- Insert interval.
- Meeting rooms.
- Meeting rooms II.
- Non-overlapping intervals.
- Minimum arrows to burst balloons.
- Employee free time.

## 24. Matrix và Grid

Matrix thường kết hợp với DFS, BFS, DP hoặc prefix sum 2D.

### Dạng bài phổ biến

- Spiral matrix.
- Rotate image.
- Set matrix zeroes.
- Search a 2D matrix.
- Number of islands.
- Max area of island.
- Word search.
- Shortest path in binary matrix.
- Unique paths.
- Minimum path sum.

## 25. Advanced Topics

Các chủ đề dưới đây ít gặp hơn ở intern/junior nhưng hữu ích nếu phỏng vấn công ty lớn hoặc vị trí thuật toán nặng:

- Segment tree.
- Fenwick tree/Binary Indexed Tree.
- Monotonic stack nâng cao.
- Monotonic queue nâng cao.
- Rolling hash.
- KMP string matching.
- Rabin-Karp.
- Manacher.
- Aho-Corasick.
- Reservoir sampling.
- Minimax.
- Alpha-beta pruning.
- Sweep line.
- Convex hull.
- Heavy-light decomposition.

## 26. Bảng nhận diện pattern nhanh

| Tín hiệu trong đề | Pattern nên nghĩ đến |
|---|---|
| Mảng/string liên tiếp | Sliding window, prefix sum |
| Mảng đã sort | Binary search, two pointers |
| Tìm cặp/tần suất/trùng lặp | Hash map, hash set |
| Top K/lấy min/max liên tục | Heap |
| Dấu ngoặc, undo, phần tử gần nhất | Stack |
| Duyệt theo từng tầng | BFS, queue |
| Tất cả đường đi/tổ hợp/hoán vị | Backtracking |
| Tối ưu có bài toán con lặp lại | Dynamic programming |
| Quan hệ kết nối | Graph, DFS, BFS, Union Find |
| Phụ thuộc trước sau | Topological sort |
| Chọn ít nhất/nhiều nhất interval | Greedy, sorting |
| Prefix của từ | Trie |
| Trạng thái bật/tắt nhỏ | Bitmask |

## 27. Checklist chủ đề cần ôn

- [ ] Big O, time complexity, space complexity.
- [ ] Array, string, linked list.
- [ ] Stack, queue, deque.
- [ ] Hash map, hash set.
- [ ] Sorting và custom comparator.
- [ ] Binary search.
- [ ] Two pointers.
- [ ] Sliding window.
- [ ] Prefix sum.
- [ ] Tree, BST, traversal.
- [ ] Heap, priority queue.
- [ ] Trie.
- [ ] Graph, DFS, BFS.
- [ ] Topological sort.
- [ ] Union Find.
- [ ] Recursion, backtracking.
- [ ] Dynamic programming.
- [ ] Greedy.
- [ ] Bit manipulation.
- [ ] Math cơ bản.
- [ ] Interval.
- [ ] Matrix/grid.
- [ ] Systematic debugging và test edge cases.

## 28. Edge cases hay bị hỏi

- Input rỗng.
- Chỉ có một phần tử.
- Tất cả phần tử giống nhau.
- Có số âm, số 0, số rất lớn.
- Có duplicate.
- Mảng đã sort tăng, sort giảm hoặc chưa sort.
- Graph không liên thông.
- Tree rỗng hoặc chỉ có root.
- String có khoảng trắng, chữ hoa/thường, ký tự đặc biệt.
- Overflow khi cộng/nhân số lớn.
- Không tìm thấy đáp án.

## 29. Cách luyện tập hiệu quả

### Giai đoạn 1: Nền tảng

- Big O.
- Array/string.
- Hash map.
- Two pointers.
- Stack/queue.
- Linked list.

Mục tiêu: giải được bài easy và một số medium đơn giản.

### Giai đoạn 2: Pattern chính

- Binary search.
- Sliding window.
- Prefix sum.
- Tree DFS/BFS.
- Heap.
- Backtracking.

Mục tiêu: nhận diện pattern trong 5-10 phút.

### Giai đoạn 3: Chủ đề khó

- Graph.
- Dynamic programming.
- Greedy.
- Union Find.
- Trie.
- Bit manipulation.

Mục tiêu: trình bày được ý tưởng, recurrence, proof hoặc invariant.

### Giai đoạn 4: Mock interview

- Giải bài trong 30-45 phút.
- Nói ra suy nghĩ khi code.
- Tự viết test case.
- Tự phân tích complexity.
- Sau khi giải xong, tìm cách tối ưu hoặc nêu trade-off.

## 30. Bộ bài luyện theo chủ đề

### Array/String

- Two Sum.
- Best Time to Buy and Sell Stock.
- Product of Array Except Self.
- Maximum Subarray.
- Merge Intervals.
- Valid Anagram.
- Group Anagrams.
- Longest Substring Without Repeating Characters.

### Linked List

- Reverse Linked List.
- Merge Two Sorted Lists.
- Linked List Cycle.
- Remove Nth Node From End of List.
- Reorder List.

### Stack/Queue

- Valid Parentheses.
- Min Stack.
- Daily Temperatures.
- Evaluate Reverse Polish Notation.
- Sliding Window Maximum.

### Tree

- Maximum Depth of Binary Tree.
- Invert Binary Tree.
- Validate Binary Search Tree.
- Binary Tree Level Order Traversal.
- Lowest Common Ancestor.

### Graph

- Number of Islands.
- Clone Graph.
- Course Schedule.
- Pacific Atlantic Water Flow.
- Rotting Oranges.

### Dynamic Programming

- Climbing Stairs.
- House Robber.
- Coin Change.
- Longest Increasing Subsequence.
- Longest Common Subsequence.
- Word Break.

### Greedy

- Jump Game.
- Gas Station.
- Non-overlapping Intervals.
- Partition Labels.

## 31. Câu hỏi phỏng vấn lý thuyết thường gặp

- Array khác linked list như thế nào?
- Hash map hoạt động ra sao?
- Collision trong hash table xử lý thế nào?
- Stack và queue khác nhau thế nào?
- BFS và DFS khác nhau thế nào?
- Khi nào dùng recursion, khi nào dùng iteration?
- Binary search cần điều kiện gì?
- BST khác binary tree thường như thế nào?
- Heap khác BST như thế nào?
- Stable sort là gì?
- Merge sort và quick sort khác nhau thế nào?
- Dynamic programming khác greedy như thế nào?
- Memoization khác tabulation như thế nào?
- Union Find dùng để làm gì?
- Topological sort dùng trong trường hợp nào?

## 32. Template trả lời khi được hỏi complexity

Khi phân tích, có thể trả lời theo mẫu:

```text
Gọi n là số phần tử của input.
Thuật toán duyệt mảng một lần, mỗi thao tác tra cứu hash map trung bình O(1).
Vì vậy time complexity là O(n).
Ta dùng thêm hash map lưu tối đa n phần tử, nên space complexity là O(n).
```

Với graph:

```text
Gọi V là số đỉnh và E là số cạnh.
DFS/BFS duyệt mỗi đỉnh và mỗi cạnh tối đa một lần.
Time complexity là O(V + E).
Space complexity là O(V) cho visited và stack/queue.
```

Với DP:

```text
Số state là n, mỗi state tính trong O(1).
Time complexity là O(n).
Mảng dp dùng O(n) bộ nhớ, có thể tối ưu xuống O(1) nếu chỉ phụ thuộc vài state trước đó.
```

## 33. Mẫu trình bày lời giải trong phỏng vấn

```text
1. Tôi sẽ bắt đầu với brute force để đảm bảo đúng.
2. Brute force có độ phức tạp O(...), chưa tốt vì ...
3. Quan sát thấy đề có đặc điểm ...
4. Vì vậy tôi dùng ...
5. Thuật toán:
   - ...
   - ...
6. Độ phức tạp là ...
7. Tôi sẽ kiểm tra với các edge case ...
```

## 34. Lộ trình ôn 4 tuần

### Tuần 1

- Big O.
- Array/string.
- Hash map.
- Two pointers.
- Sliding window.

### Tuần 2

- Stack/queue.
- Linked list.
- Binary search.
- Sorting.
- Prefix sum.

### Tuần 3

- Tree.
- Heap.
- Backtracking.
- Graph BFS/DFS.
- Union Find.

### Tuần 4

- Dynamic programming.
- Greedy.
- Trie.
- Bit manipulation.
- Mock interview và review bài sai.

## 35. Nguyên tắc quan trọng nhất

Không cần học thuộc mọi lời giải. Điều quan trọng là nhận diện được pattern, giải thích được vì sao chọn cấu trúc dữ liệu đó, code được bản đúng và phân tích complexity rõ ràng.
