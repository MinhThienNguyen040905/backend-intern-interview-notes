# Kiến thức - Two Pointers và Sliding Window

Two pointers dùng hai vị trí di chuyển có kiểm soát. Sliding window là biến thể dành cho đoạn liên tiếp trong array/string.

## Two pointers

- Hai đầu tiến vào giữa: palindrome, container water.
- Một đọc một ghi: remove duplicate, move zeroes.
- Hai mảng đã sort: merge, intersection.

## Sliding window

- Fixed-size window: tìm tổng lớn nhất của đoạn dài `k`.
- Variable-size window: mở rộng `right`, thu hẹp `left` khi vi phạm điều kiện.

## Lỗi hay gặp

- Cập nhật left/right sai thứ tự.
- Quên xóa phần tử khỏi count khi thu hẹp window.
- Dùng sliding window cho bài không có tính liên tiếp.

