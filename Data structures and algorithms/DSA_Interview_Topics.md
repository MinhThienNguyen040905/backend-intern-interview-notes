# DSA Interview Plan

File này là bản kế hoạch học Data Structures and Algorithms cho phỏng vấn. Mỗi chủ đề có một thư mục riêng, bên trong gồm ghi chú kiến thức và code Java minh họa.

## Cấu trúc thư mục

```text
Data structures and algorithms/
├── DSA_Interview_Topics.md
├── 01-big-o-complexity/
│   ├── README.md
│   ├── KNOWLEDGE.md
│   └── src/Main.java
├── 02-array-string/
│   ├── README.md
│   ├── KNOWLEDGE.md
│   └── src/Main.java
└── ...
```

## Quy ước trong mỗi chủ đề

- `README.md`: mục tiêu, khi nào dùng, bài nên luyện.
- `KNOWLEDGE.md`: kiến thức chi tiết, pattern, complexity, lỗi hay gặp.
- `src/Main.java`: ví dụ Java tối thiểu, có thể chạy độc lập.

## Lộ trình học đề xuất

1. Nền tảng: Big O, array/string, linked list, stack/queue, hash table.
2. Pattern cơ bản: sorting, binary search, two pointers, sliding window, prefix sum.
3. Cấu trúc dữ liệu nâng hơn: tree, heap, trie, graph, union find.
4. Kỹ thuật giải bài: recursion, backtracking, dynamic programming, greedy.
5. Chủ đề bổ sung: bit manipulation, math, intervals, matrix/grid, advanced topics.

## Danh sách chủ đề

| # | Chủ đề | Thư mục |
|---:|---|---|
| 1 | Big O và phân tích độ phức tạp | [01-big-o-complexity](./01-big-o-complexity/README.md) |
| 2 | Array và String | [02-array-string](./02-array-string/README.md) |
| 3 | Linked List | [03-linked-list](./03-linked-list/README.md) |
| 4 | Stack, Queue và Deque | [04-stack-queue](./04-stack-queue/README.md) |
| 5 | Hash Table, Hash Map và Hash Set | [05-hash-table](./05-hash-table/README.md) |
| 6 | Sorting | [06-sorting](./06-sorting/README.md) |
| 7 | Binary Search | [07-binary-search](./07-binary-search/README.md) |
| 8 | Two Pointers và Sliding Window | [08-two-pointers-sliding-window](./08-two-pointers-sliding-window/README.md) |
| 9 | Prefix Sum và Difference Array | [09-prefix-sum](./09-prefix-sum/README.md) |
| 10 | Tree và Binary Search Tree | [10-tree-bst](./10-tree-bst/README.md) |
| 11 | Heap và Priority Queue | [11-heap-priority-queue](./11-heap-priority-queue/README.md) |
| 12 | Trie | [12-trie](./12-trie/README.md) |
| 13 | Graph | [13-graph](./13-graph/README.md) |
| 14 | Union Find | [14-union-find](./14-union-find/README.md) |
| 15 | Recursion và Backtracking | [15-recursion-backtracking](./15-recursion-backtracking/README.md) |
| 16 | Dynamic Programming | [16-dynamic-programming](./16-dynamic-programming/README.md) |
| 17 | Greedy | [17-greedy](./17-greedy/README.md) |
| 18 | Bit Manipulation | [18-bit-manipulation](./18-bit-manipulation/README.md) |
| 19 | Math và Number Theory | [19-math-number-theory](./19-math-number-theory/README.md) |
| 20 | Intervals | [20-intervals](./20-intervals/README.md) |
| 21 | Matrix và Grid | [21-matrix-grid](./21-matrix-grid/README.md) |
| 22 | Advanced Topics | [22-advanced-topics](./22-advanced-topics/README.md) |

## Checklist ôn tập

- [ ] Hiểu Big O và phân tích được time/space complexity.
- [ ] Giải được các bài array/string bằng brute force và tối ưu.
- [ ] Biết khi nào dùng hash map, stack, queue, heap.
- [ ] Nhận diện được binary search trên mảng và trên đáp án.
- [ ] Phân biệt DFS, BFS, backtracking và dynamic programming.
- [ ] Trình bày được trade-off giữa time và space.
- [ ] Code Java rõ ràng, tự test được edge cases.

## Cách thêm một bài luyện mới

Trong thư mục chủ đề tương ứng:

1. Thêm mô tả bài vào `README.md`.
2. Ghi pattern/ý tưởng vào `KNOWLEDGE.md`.
3. Thêm code Java vào `src/`, ví dụ `TwoSum.java`.
4. Ghi complexity ngay trong comment hoặc sau lời giải.

## Mẫu trả lời khi phỏng vấn

```text
Gọi n là số phần tử input.
Cách brute force là ...
Điểm nghẽn là ...
Ta có thể dùng ... vì ...
Thuật toán duyệt ...
Time complexity: O(...)
Space complexity: O(...)
Edge cases cần kiểm tra: ...
```
