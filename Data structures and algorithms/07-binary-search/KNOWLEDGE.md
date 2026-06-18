# Kiến thức - Binary Search

Binary search dùng khi dữ liệu đã sort hoặc đáp án có tính đơn điệu. Mỗi bước loại bỏ một nửa không gian tìm kiếm.

## Template lower bound

```java
int left = 0, right = nums.length;
while (left < right) {
    int mid = left + (right - left) / 2;
    if (nums[mid] >= target) right = mid;
    else left = mid + 1;
}
```

## Khi dùng binary search trên đáp án

Bạn cần một hàm `can(x)`:

- Nếu `x` thỏa, mọi giá trị lớn hơn cũng thỏa, hoặc
- Nếu `x` thỏa, mọi giá trị nhỏ hơn cũng thỏa.

## Lỗi hay gặp

- Overflow khi tính `(left + right) / 2`.
- Sai điều kiện dừng.
- Không xác định rõ tìm first, last hay exact match.
- Không chứng minh được tính đơn điệu.

