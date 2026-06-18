# Kiến thức - Matrix và Grid

Grid là graph đặc biệt, mỗi ô là một node và cạnh nối tới ô lân cận.

## Pattern

- DFS/BFS để duyệt vùng liên thông.
- Direction arrays để duyệt 4 hướng.
- DP trên grid cho đường đi tối ưu.
- Prefix sum 2D cho tổng hình chữ nhật.
- In-place marking để tiết kiệm visited array.

## Direction array

```java
int[][] dirs = {{1,0}, {-1,0}, {0,1}, {0,-1}};
```

## Lỗi hay gặp

- Lệch row/col.
- Không kiểm tra boundary.
- Quên đánh dấu visited trước khi đưa vào queue.
- Làm thay đổi input khi đề không cho phép.

