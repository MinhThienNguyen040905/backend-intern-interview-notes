# Kiến thức - Prefix Sum

Prefix sum lưu tổng từ đầu mảng đến trước vị trí hiện tại. Sau khi xây `prefix`, tổng đoạn bất kỳ có thể tính nhanh.

## Công thức

Với `prefix[i]` là tổng các phần tử trước index `i`:

```text
sum(l, r) = prefix[r + 1] - prefix[l]
```

## Difference array

Difference array phù hợp khi có nhiều thao tác cộng một giá trị vào một đoạn `[l, r]`.

```text
diff[l] += value
diff[r + 1] -= value
```

Sau đó cộng dồn `diff` để lấy mảng cuối.

## Lỗi hay gặp

- Lệch index giữa `prefix[i]` và `nums[i]`.
- Quên dùng `long` khi tổng lớn.
- Với subarray sum, cần lưu số lần xuất hiện của prefix sum.

