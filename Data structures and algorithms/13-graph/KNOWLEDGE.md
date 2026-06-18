# Kiến thức - Graph

Graph gồm các đỉnh và cạnh. Graph có thể có hướng/vô hướng, có trọng số/không trọng số.

## Biểu diễn

- Adjacency list: phổ biến, tiết kiệm bộ nhớ, `O(V + E)`.
- Adjacency matrix: kiểm tra cạnh nhanh, tốn `O(V^2)`.
- Edge list: tiện cho Kruskal/Bellman-Ford.

## Thuật toán chính

- DFS: duyệt sâu, connected components, cycle detection.
- BFS: shortest path trong graph không trọng số.
- Topological sort: graph có hướng không chu trình.
- Dijkstra: shortest path trọng số không âm.
- Bellman-Ford: shortest path có cạnh âm.

## Complexity

DFS/BFS với adjacency list: `O(V + E)` time, `O(V)` space.

