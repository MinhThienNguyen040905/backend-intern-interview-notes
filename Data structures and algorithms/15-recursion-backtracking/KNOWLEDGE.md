# Kiến thức - Recursion và Backtracking

Recursion giải bài bằng cách gọi lại chính nó trên bài toán nhỏ hơn. Backtracking thử các lựa chọn, quay lui khi lựa chọn không còn phù hợp.

## Thành phần cần xác định

- State hiện tại là gì?
- Choices tiếp theo là gì?
- Base case là gì?
- Khi nào prune?
- Sau khi thử một choice, cần undo gì?

## Template

```text
backtrack(state):
    if đạt điều kiện dừng:
        lưu đáp án
        return
    for choice in choices:
        chọn
        backtrack(state mới)
        bỏ chọn
```

## Lỗi hay gặp

- Quên copy list khi lưu kết quả.
- Quên undo state.
- Base case thiếu hoặc sai.
- Không prune nên bị TLE.

