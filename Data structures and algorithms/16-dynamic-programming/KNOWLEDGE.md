# Kiến thức - Dynamic Programming

Dynamic Programming dùng khi bài toán có bài toán con lặp lại và lời giải tối ưu được xây từ lời giải tối ưu của bài toán nhỏ hơn.

## Quy trình

1. Định nghĩa state: `dp[i]` nghĩa là gì?
2. Xác định transition: từ state nào chuyển sang state nào?
3. Xác định base case.
4. Chọn top-down memoization hoặc bottom-up tabulation.
5. Phân tích complexity theo số state và chi phí mỗi state.

## Nhóm DP phổ biến

- 1D DP: climbing stairs, house robber.
- 2D DP: grid path, LCS.
- Knapsack: chọn/không chọn.
- Interval DP: xử lý đoạn.
- Tree DP: kết quả phụ thuộc subtree.

## Lỗi hay gặp

- State không đủ thông tin.
- Transition dùng giá trị chưa tính.
- Base case sai.
- Quên tối ưu space khi chỉ cần vài state trước.

