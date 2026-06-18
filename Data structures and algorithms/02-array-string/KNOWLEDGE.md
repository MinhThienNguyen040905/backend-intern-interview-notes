# Kiến thức - Array và String

Array lưu phần tử liên tiếp trong bộ nhớ và truy cập theo index `O(1)`. String thường bất biến trong nhiều ngôn ngữ, nên nối chuỗi nhiều lần có thể tốn kém.

## Operation thường gặp

| Operation | Complexity |
|---|---|
| Access by index | `O(1)` |
| Search | `O(n)` |
| Insert/delete giữa mảng | `O(n)` |
| Duyệt toàn bộ | `O(n)` |

## Pattern quan trọng

- Two pointers: dùng cho palindrome, mảng đã sort, remove duplicate.
- Sliding window: dùng cho substring/subarray liên tiếp.
- Prefix sum: tính tổng đoạn nhanh.
- Hash map: đếm tần suất, tìm duplicate, anagram.
- Sorting: chuẩn hóa dữ liệu trước khi xử lý.

## Edge cases

- Input rỗng.
- Mảng chỉ có một phần tử.
- Có số âm hoặc số 0.
- String có khoảng trắng, ký tự đặc biệt.
- Duplicate nhiều.

