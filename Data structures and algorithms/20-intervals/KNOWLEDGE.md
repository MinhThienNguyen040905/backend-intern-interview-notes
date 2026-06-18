# Kiến thức - Intervals

Interval thường có dạng `[start, end]`. Hầu hết bài interval bắt đầu bằng sort.

## Pattern

- Merge: sort theo start, nếu overlap thì gộp.
- Meeting rooms: sort theo start hoặc dùng heap theo end time.
- Remove overlap: greedy chọn interval kết thúc sớm.
- Sweep line: biến event start/end thành điểm thay đổi.

## Điều kiện overlap

Với hai interval `[a, b]` và `[c, d]` đã sort theo start:

```text
c <= b thì overlap
c > b thì tách rời
```

## Lỗi hay gặp

- Nhầm interval đóng/mở.
- Sort sai tiêu chí.
- Không xử lý interval rỗng.
- Quên cập nhật end khi merge.

