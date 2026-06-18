# Kiến thức - Linked List

Linked list gồm nhiều node, mỗi node giữ value và reference tới node tiếp theo. Khác array, linked list không truy cập index trong `O(1)`.

## Complexity

| Operation | Complexity |
|---|---|
| Access by index | `O(n)` |
| Search | `O(n)` |
| Insert/delete khi đã có node | `O(1)` |
| Insert/delete theo vị trí | `O(n)` |

## Pattern quan trọng

- Dummy node: xử lý head dễ hơn.
- Fast/slow pointers: tìm middle, phát hiện cycle.
- Reverse pointers: đảo chiều danh sách.
- Merge: nối hai list đã sort.

## Lỗi hay gặp

- Mất reference tới node tiếp theo khi reverse.
- Không xử lý list rỗng hoặc một node.
- Vòng lặp vô hạn khi có cycle.
- Cập nhật `head` sai sau khi xóa node đầu.

