# Kiến thức - Tree và BST

Tree là cấu trúc phân cấp gồm node cha và node con. Binary tree là cây mà mỗi node có tối đa hai con. Binary search tree có tính chất `left < root < right`.

## Traversal

- Preorder: root, left, right.
- Inorder: left, root, right.
- Postorder: left, right, root.
- Level order: duyệt từng tầng bằng queue.

## BST complexity

| Operation | Balanced BST | Worst case |
|---|---:|---:|
| Search | `O(log n)` | `O(n)` |
| Insert | `O(log n)` | `O(n)` |
| Delete | `O(log n)` | `O(n)` |

## Pattern

- DFS trả về thông tin từ subtree.
- BFS cho bài theo level.
- Dùng min/max bound để validate BST.
- LCA dựa vào vị trí node trong cây.

