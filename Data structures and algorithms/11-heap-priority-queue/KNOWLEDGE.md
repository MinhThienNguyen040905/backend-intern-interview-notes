# Kiến thức - Heap và Priority Queue

Heap là cây nhị phân gần hoàn chỉnh, thường dùng để lấy phần tử nhỏ nhất hoặc lớn nhất nhanh.

## Complexity

| Operation | Complexity |
|---|---:|
| Peek | `O(1)` |
| Push | `O(log n)` |
| Pop | `O(log n)` |
| Build heap | `O(n)` |

## Java PriorityQueue

- `new PriorityQueue<>()`: min-heap.
- `new PriorityQueue<>((a, b) -> b - a)`: max-heap nhưng có nguy cơ overflow.
- Nên dùng `Comparator.reverseOrder()` hoặc `Integer.compare(b, a)`.

## Khi dùng

- Cần top K.
- Cần luôn lấy phần tử nhỏ/lớn nhất.
- Cần merge nhiều nguồn đã sort.
- Cần quản lý tài nguyên đang hoạt động.

