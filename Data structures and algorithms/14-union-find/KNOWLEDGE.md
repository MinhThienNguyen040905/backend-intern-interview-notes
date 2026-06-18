# Kiến thức - Union Find

Union Find, hay Disjoint Set Union, quản lý nhiều tập rời nhau và hỗ trợ kiểm tra hai phần tử có cùng tập hay không.

## Operation

- `find(x)`: tìm đại diện của tập chứa `x`.
- `union(a, b)`: gộp hai tập.
- `connected(a, b)`: kiểm tra cùng tập.

## Tối ưu

- Path compression: khi `find`, trỏ node trực tiếp về root.
- Union by size/rank: gộp cây nhỏ vào cây lớn.

Với hai tối ưu này, operation gần như `O(1)` trong thực tế.

## Khi dùng

- Connected components.
- Detect cycle trong graph vô hướng.
- Gộp account/user/entity.
- Kruskal minimum spanning tree.

