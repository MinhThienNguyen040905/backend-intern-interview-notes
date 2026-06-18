# Kiến thức - Sorting

Sorting thường là bước tiền xử lý giúp bài toán dễ hơn: gom phần tử giống nhau, xử lý interval, dùng two pointers hoặc greedy.

## Bảng complexity

| Algorithm | Average | Worst | Space |
|---|---:|---:|---:|
| Bubble sort | `O(n^2)` | `O(n^2)` | `O(1)` |
| Selection sort | `O(n^2)` | `O(n^2)` | `O(1)` |
| Insertion sort | `O(n^2)` | `O(n^2)` | `O(1)` |
| Merge sort | `O(n log n)` | `O(n log n)` | `O(n)` |
| Quick sort | `O(n log n)` | `O(n^2)` | `O(log n)` |
| Heap sort | `O(n log n)` | `O(n log n)` | `O(1)` |

## Ghi nhớ

- Java `Arrays.sort(int[])` dùng thuật toán tối ưu cho primitive.
- Java `Arrays.sort(Object[])` ổn định.
- Comparator cần tránh overflow: dùng `Integer.compare(a, b)` thay vì `a - b`.

