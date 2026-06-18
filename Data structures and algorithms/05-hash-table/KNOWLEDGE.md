# Kiến thức - Hash Table

Hash table ánh xạ key sang vị trí lưu trữ bằng hash function. Trong Java, `HashMap` lưu key-value, `HashSet` lưu tập key duy nhất.

## Khi nên dùng

- Cần kiểm tra phần tử đã xuất hiện chưa.
- Cần đếm tần suất.
- Cần tìm cặp theo phần bù, ví dụ `target - x`.
- Cần group theo key.
- Cần cache kết quả.

## Complexity

| Operation | Average | Worst |
|---|---:|---:|
| Put | `O(1)` | `O(n)` |
| Get | `O(1)` | `O(n)` |
| Contains | `O(1)` | `O(n)` |

## Lỗi hay gặp

- Quên xử lý duplicate.
- Dùng array count khi key không giới hạn.
- Không phân biệt set và map.
- Với object key, quên `equals` và `hashCode`.

