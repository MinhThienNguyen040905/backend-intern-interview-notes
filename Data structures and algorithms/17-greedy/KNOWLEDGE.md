# Kiến thức - Greedy

Greedy chọn phương án tốt nhất tại thời điểm hiện tại. Không phải bài tối ưu nào cũng dùng greedy được; cần có lý do lựa chọn cục bộ dẫn tới tối ưu toàn cục.

## Khi nghĩ đến greedy

- Bài interval/scheduling.
- Bài chọn ít nhất/nhiều nhất phần tử.
- Bài có thể sort theo start/end/cost.
- Bài có invariant rõ ràng sau mỗi bước.

## Cách giải thích

- Nêu chiến lược chọn.
- Nêu invariant.
- Dùng exchange argument nếu cần: mọi lời giải tối ưu đều có thể đổi sang lựa chọn greedy mà không tệ hơn.

## Lỗi hay gặp

- Dùng greedy theo cảm tính.
- Sort sai tiêu chí.
- Không xử lý tie.
- Không chứng minh được vì sao lựa chọn local là đúng.

