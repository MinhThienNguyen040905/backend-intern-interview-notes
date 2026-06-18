# Kiến thức - Bit Manipulation

Bit manipulation xử lý trực tiếp biểu diễn nhị phân của số. Chủ đề này thường xuất hiện ở bài tối ưu hoặc bài dùng XOR.

## Toán tử

| Operator | Ý nghĩa |
|---|---|
| `&` | AND |
| `|` | OR |
| `^` | XOR |
| `~` | NOT |
| `<<` | Shift left |
| `>>` | Shift right |

## Mẹo quan trọng

- `x & 1`: kiểm tra chẵn/lẻ.
- `x & (x - 1)`: xóa bit 1 thấp nhất.
- `x ^ x = 0`.
- `x ^ 0 = x`.
- XOR toàn mảng giúp tìm phần tử xuất hiện một lần.

## Lỗi hay gặp

- Nhầm signed shift và unsigned shift.
- Không để ý số âm.
- Dùng bitmask khi số state quá lớn.

