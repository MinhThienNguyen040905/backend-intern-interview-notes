# Độ phức tạp Big-O

## 1. Big-O là gì?

Big-O là cách mô tả tốc độ tăng trưởng của thuật toán khi kích thước input `n` tăng lên.

Trong phỏng vấn, Big-O thường dùng để trả lời:

- Thuật toán chạy nhanh hay chậm khi dữ liệu lớn?
- Thuật toán dùng bao nhiêu bộ nhớ phụ?
- Có cách nào tối ưu hơn không?

Ví dụ:

```java
for (int i = 0; i < n; i++) {
    System.out.println(i);
}
```

Vòng lặp chạy `n` lần, nên time complexity là `O(n)`.

## 2. Big-O, Big-Omega, Big-Theta

| Ký hiệu | Ý nghĩa | Cách hiểu nhanh |
| --- | --- | --- |
| `O(...)` | Upper bound | Trường hợp xấu không vượt quá mức này |
| `Omega(...)` | Lower bound | Trường hợp tốt ít nhất đạt mức này |
| `Theta(...)` | Tight bound | Tăng trưởng chính xác về mặt bậc |

Trong phỏng vấn, khi nói "complexity là O(n)", thường người ta đang nói đến upper bound hoặc worst case.

## 3. Thứ tự độ phức tạp phổ biến

Từ tốt đến xấu hơn:

| Complexity | Tên gọi | Ví dụ |
| --- | --- | --- |
| `O(1)` | Constant | Truy cập `arr[i]`, push/pop stack |
| `O(log n)` | Logarithmic | Binary search |
| `O(n)` | Linear | Duyệt mảng một lần |
| `O(n log n)` | Linearithmic | Merge sort, heap sort |
| `O(n^2)` | Quadratic | Hai vòng lặp lồng nhau |
| `O(n^3)` | Cubic | Ba vòng lặp lồng nhau |
| `O(2^n)` | Exponential | Thử tất cả subset |
| `O(n!)` | Factorial | Thử tất cả hoán vị |

Quy tắc nhớ: khi `n` lớn, `O(n log n)` thường còn chấp nhận được, `O(n^2)` cần cân nhắc, `O(2^n)` và `O(n!)` chỉ dùng được với `n` rất nhỏ.

## 4. Các quy tắc tính Big-O

### Bỏ hằng số

```java
for (int i = 0; i < 2 * n; i++) {
    // O(1)
}
```

Chạy `2n` lần nhưng Big-O là `O(n)`, không phải `O(2n)`.

### Bỏ thành phần nhỏ hơn

```java
for (int i = 0; i < n; i++) {
    // O(1)
}

for (int i = 0; i < n; i++) {
    for (int j = 0; j < n; j++) {
        // O(1)
    }
}
```

Tổng là `O(n + n^2)`, rút gọn thành `O(n^2)`.

### Hai input khác nhau thì không gộp thành một biến

```java
for (int i = 0; i < a; i++) {
    // O(1)
}

for (int j = 0; j < b; j++) {
    // O(1)
}
```

Complexity là `O(a + b)`, không nên viết thành `O(n)`.

Nếu lồng nhau:

```java
for (int i = 0; i < a; i++) {
    for (int j = 0; j < b; j++) {
        // O(1)
    }
}
```

Complexity là `O(a * b)`.

## 5. Phân tích vòng lặp

### Một vòng lặp

```java
for (int i = 0; i < n; i++) {
    sum += arr[i];
}
```

Time: `O(n)`

Space: `O(1)`

### Hai vòng lặp nối tiếp

```java
for (int i = 0; i < n; i++) {
    // O(1)
}

for (int j = 0; j < n; j++) {
    // O(1)
}
```

Time: `O(n + n) = O(n)`

### Hai vòng lặp lồng nhau

```java
for (int i = 0; i < n; i++) {
    for (int j = 0; j < n; j++) {
        // O(1)
    }
}
```

Time: `O(n^2)`

### Vòng lặp tăng gấp đôi

```java
for (int i = 1; i < n; i *= 2) {
    // O(1)
}
```

Time: `O(log n)`

Vì mỗi lần `i` gấp đôi: `1, 2, 4, 8, ...`

### Vòng lặp giảm một nửa

```java
while (n > 1) {
    n /= 2;
}
```

Time: `O(log n)`

## 6. Phân tích đệ quy

### Đệ quy tuyến tính

```java
int factorial(int n) {
    if (n <= 1) return 1;
    return n * factorial(n - 1);
}
```

Mỗi lần gọi giảm `n` đi 1.

Time: `O(n)`

Space: `O(n)` do call stack.

### Binary search

```java
int binarySearch(int[] arr, int target, int left, int right) {
    if (left > right) return -1;

    int mid = left + (right - left) / 2;

    if (arr[mid] == target) return mid;
    if (arr[mid] < target) {
        return binarySearch(arr, target, mid + 1, right);
    }
    return binarySearch(arr, target, left, mid - 1);
}
```

Mỗi lần loại bỏ một nửa input.

Time: `O(log n)`

Space: `O(log n)` nếu dùng đệ quy, `O(1)` nếu dùng vòng lặp.

### Fibonacci naive

```java
int fib(int n) {
    if (n <= 1) return n;
    return fib(n - 1) + fib(n - 2);
}
```

Mỗi call tách thành 2 call con.

Time: `O(2^n)`

Space: `O(n)` do độ sâu call stack.

Nếu dùng memoization:

Time: `O(n)`

Space: `O(n)`

## 7. Time complexity và space complexity

### Time complexity

Đo số phép tính tăng như thế nào theo input.

Ví dụ:

```java
boolean containsDuplicate(int[] nums) {
    Set<Integer> seen = new HashSet<>();

    for (int num : nums) {
        if (seen.contains(num)) return true;
        seen.add(num);
    }

    return false;
}
```

Time: `O(n)` vì duyệt mảng một lần, thao tác `HashSet` trung bình là `O(1)`.

### Space complexity

Đo bộ nhớ phụ tăng như thế nào theo input.

Trong ví dụ trên:

Space: `O(n)` vì `HashSet` có thể lưu tối đa `n` phần tử.

## 8. Complexity của data structure phổ biến

| Data structure | Access | Search | Insert | Delete |
| --- | --- | --- | --- | --- |
| Array | `O(1)` | `O(n)` | `O(n)` | `O(n)` |
| Linked List | `O(n)` | `O(n)` | `O(1)` nếu đã có node | `O(1)` nếu đã có node |
| Stack | `O(n)` | `O(n)` | `O(1)` push | `O(1)` pop |
| Queue | `O(n)` | `O(n)` | `O(1)` enqueue | `O(1)` dequeue |
| HashMap | Không áp dụng | `O(1)` trung bình | `O(1)` trung bình | `O(1)` trung bình |
| Binary Search Tree cân bằng | `O(log n)` | `O(log n)` | `O(log n)` | `O(log n)` |
| Heap | `O(1)` peek min/max | `O(n)` | `O(log n)` | `O(log n)` poll |

Lưu ý: `HashMap` worst case có thể là `O(n)`, nhưng trong phỏng vấn thường lấy average case là `O(1)` nếu hash function tốt.

## 9. Complexity của thuật toán sắp xếp

| Algorithm | Best | Average | Worst | Space |
| --- | --- | --- | --- | --- |
| Bubble sort | `O(n)` | `O(n^2)` | `O(n^2)` | `O(1)` |
| Selection sort | `O(n^2)` | `O(n^2)` | `O(n^2)` | `O(1)` |
| Insertion sort | `O(n)` | `O(n^2)` | `O(n^2)` | `O(1)` |
| Merge sort | `O(n log n)` | `O(n log n)` | `O(n log n)` | `O(n)` |
| Quick sort | `O(n log n)` | `O(n log n)` | `O(n^2)` | `O(log n)` average |
| Heap sort | `O(n log n)` | `O(n log n)` | `O(n log n)` | `O(1)` |

## 10. Một số mẫu phân tích hay gặp

### Two pointers

Thường là `O(n)` vì mỗi con trỏ chỉ đi qua mảng một số lần hữu hạn.

```java
int left = 0;
int right = nums.length - 1;

while (left < right) {
    if (nums[left] + nums[right] == target) return true;
    if (nums[left] + nums[right] < target) left++;
    else right--;
}
```

Time: `O(n)`

Space: `O(1)`

### Sliding window

Thường là `O(n)` vì mỗi phần tử vào window một lần và ra window một lần.

```java
int left = 0;
int sum = 0;

for (int right = 0; right < nums.length; right++) {
    sum += nums[right];

    while (sum > target) {
        sum -= nums[left];
        left++;
    }
}
```

Time: `O(n)`, không phải `O(n^2)`, vì `left` và `right` mỗi con trỏ chỉ tiến tới.

### BFS / DFS trên graph

Với adjacency list:

Time: `O(V + E)`

Space: `O(V)`

Trong đó:

- `V` là số đỉnh.
- `E` là số cạnh.

### Dynamic programming

Complexity thường bằng:

```text
số_lượng_state * chi_phí_tính_mỗi_state
```

Ví dụ `dp[i]` với `i = 0..n` và mỗi state tính `O(1)`:

Time: `O(n)`

Space: `O(n)` hoặc `O(1)` nếu tối ưu rolling variables.

## 11. Cách trả lời trong phỏng vấn

Khi được hỏi complexity, có thể trả lời theo format:

```text
Time complexity là O(...), vì ...
Space complexity là O(...), vì ...
```

Ví dụ:

```text
Time complexity là O(n), vì ta duyệt qua mảng một lần và mỗi thao tác HashSet trung bình là O(1).
Space complexity là O(n), vì trong worst case ta lưu tất cả phần tử vào HashSet.
```

## 12. Các lỗi thường gặp

- Nói `O(2n)` thay vì rút gọn thành `O(n)`.
- Gộp hai input khác nhau thành `O(n)` thay vì `O(a + b)` hoặc `O(a * b)`.
- Thấy nested loop là kết luận ngay `O(n^2)`, trong khi two pointers/sliding window có thể vẫn là `O(n)`.
- Quên tính space complexity của recursion call stack.
- Quên phân biệt average case và worst case của `HashMap`, `QuickSort`.
- Chỉ nói Big-O mà không giải thích vì sao.

## 13. Checklist nhanh

Trước khi chốt đáp án, hãy tự hỏi:

- Input size là gì? `n`, `m`, `V`, `E`?
- Có bao nhiêu vòng lặp? Nối tiếp hay lồng nhau?
- Vòng lặp tăng/giảm theo công thức nào? `+1`, `*2`, `/2`?
- Có đệ quy không? Mỗi call sinh bao nhiêu call con?
- Có dùng bộ nhớ phụ không? Array, HashMap, Set, Queue, Stack?
- Có call stack do đệ quy không?
- Đang nói best, average hay worst case?

