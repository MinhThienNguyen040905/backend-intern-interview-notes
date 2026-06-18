# Kiến thức - Big O

Big O mô tả tốc độ tăng chi phí khi kích thước input tăng. Trong phỏng vấn, bạn cần nói rõ cả thời gian chạy và bộ nhớ dùng thêm.

## Các mức thường gặp

| Big O | Ý nghĩa | Ví dụ |
|---|---|---|
| `O(1)` | Hằng số | Lấy phần tử theo index |
| `O(log n)` | Logarithmic | Binary search |
| `O(n)` | Tuyến tính | Duyệt mảng |
| `O(n log n)` | Tuyến tính log | Merge sort |
| `O(n^2)` | Bình phương | Hai vòng lặp lồng nhau |
| `O(2^n)` | Hàm mũ | Sinh mọi subset |
| `O(n!)` | Giai thừa | Sinh mọi hoán vị |

## Quy tắc rút gọn

- Bỏ hằng số: `O(2n)` thành `O(n)`.
- Giữ phần tăng nhanh nhất: `O(n + n^2)` thành `O(n^2)`.
- Vòng lặp nối tiếp thường cộng.
- Vòng lặp lồng nhau thường nhân.
- Với recursion, xét số nhánh và độ sâu cây đệ quy.

## Mẫu trả lời

```text
Gọi n là số phần tử.
Thuật toán duyệt mảng một lần, mỗi thao tác là O(1).
Time complexity là O(n).
Không dùng cấu trúc dữ liệu phụ đáng kể nên space complexity là O(1).
```

