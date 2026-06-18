# Kiến thức - Big O và phân tích độ phức tạp

Big O là cách mô tả tốc độ tăng của chi phí thuật toán khi kích thước input tăng. Chi phí thường được phân tích theo:

- `Time complexity`: thời gian chạy tăng như thế nào.
- `Space complexity`: bộ nhớ phụ tăng như thế nào.

Trong phỏng vấn, Big O không dùng để đo chính xác chương trình chạy bao nhiêu mili giây. Nó dùng để trả lời câu hỏi quan trọng hơn: nếu input lớn gấp 10 lần, thuật toán có còn chịu được không?

## 1. Vì sao cần Big O?

Giả sử có 2 thuật toán xử lý mảng `n` phần tử:

- Thuật toán A chạy `100n` bước.
- Thuật toán B chạy `n^2` bước.

Với `n = 10`, B có thể trông không quá tệ. Nhưng với `n = 1_000_000`, `n^2` gần như không thể chạy được trong thời gian hợp lý. Big O giúp ta nhìn vào xu hướng tăng trưởng thay vì bị nhiễu bởi hằng số, phần cứng, ngôn ngữ lập trình hoặc dữ liệu test nhỏ.

## 2. Định nghĩa ngắn gọn

Nếu nói thuật toán có độ phức tạp `O(f(n))`, nghĩa là khi `n` đủ lớn, chi phí của thuật toán bị chặn trên bởi một hằng số nhân với `f(n)`.

Nói đơn giản hơn:

- `O(1)`: gần như không phụ thuộc vào kích thước input.
- `O(n)`: input tăng gấp đôi thì chi phí xấp xỉ tăng gấp đôi.
- `O(n^2)`: input tăng gấp đôi thì chi phí xấp xỉ tăng gấp bốn.
- `O(log n)`: input tăng rất nhanh nhưng số bước chỉ tăng chậm.

Khi phỏng vấn, bạn không cần trình bày định nghĩa toán học dài. Điều quan trọng là đọc code, xác định phần chi phối, và giải thích rõ biến nào đại diện cho kích thước input.

## 3. Các ký hiệu thường gặp

Big O không phải ký hiệu duy nhất, nhưng là ký hiệu được hỏi nhiều nhất.

| Ký hiệu | Ý nghĩa | Khi dùng |
|---|---|---|
| `O(f(n))` | Chặn trên | Thuật toán không tăng nhanh hơn mức này, thường dùng nhất trong phỏng vấn |
| `Ω(f(n))` | Chặn dưới | Thuật toán ít nhất phải tốn mức này |
| `Θ(f(n))` | Chặn chặt | Thuật toán tăng đúng cùng bậc với mức này |

Ví dụ tìm kiếm tuyến tính trong mảng:

- Best case: phần tử ở đầu mảng, `Ω(1)`.
- Worst case: phần tử ở cuối hoặc không tồn tại, `O(n)`.
- Nếu nói chính xác về worst case thì là `O(n)`.
- Nếu nói chặt cho worst case thì là `Θ(n)`.

Trong thực tế phỏng vấn, nếu không nói gì thêm, complexity thường được hiểu là worst-case Big O.

## 4. Time complexity và space complexity

### Time complexity

Time complexity đếm số thao tác theo kích thước input. Một thao tác đơn giản như gán biến, so sánh, cộng trừ, truy cập mảng theo index thường được xem là `O(1)`.

Ví dụ:

```java
int getFirst(int[] nums) {
    return nums[0];
}
```

Time complexity: `O(1)` vì chỉ truy cập một phần tử.

```java
int sum(int[] nums) {
    int total = 0;
    for (int num : nums) {
        total += num;
    }
    return total;
}
```

Time complexity: `O(n)` vì duyệt qua toàn bộ `n` phần tử.

### Space complexity

Space complexity thường nói về bộ nhớ phụ, không tính input có sẵn, trừ khi người phỏng vấn yêu cầu tính tổng bộ nhớ.

Ví dụ:

```java
int sum(int[] nums) {
    int total = 0;
    for (int num : nums) {
        total += num;
    }
    return total;
}
```

Space complexity: `O(1)` vì chỉ dùng vài biến phụ.

```java
int[] copyArray(int[] nums) {
    int[] copy = new int[nums.length];
    for (int i = 0; i < nums.length; i++) {
        copy[i] = nums[i];
    }
    return copy;
}
```

Space complexity: `O(n)` vì tạo thêm mảng `copy` có `n` phần tử.

## 5. Bảng các mức Big O phổ biến

| Big O | Tên gọi | Ví dụ điển hình | Nhận xét |
|---|---|---|---|
| `O(1)` | Constant | Truy cập `arr[i]`, push vào stack | Rất tốt |
| `O(log n)` | Logarithmic | Binary search, thao tác trên balanced BST | Rất tốt với input lớn |
| `O(n)` | Linear | Duyệt mảng, tìm min/max | Thường chấp nhận được |
| `O(n log n)` | Linearithmic | Merge sort, heap sort, quicksort trung bình | Rất phổ biến trong sorting |
| `O(n^2)` | Quadratic | Hai vòng lặp lồng nhau, so sánh mọi cặp | Chỉ ổn với `n` vừa/nhỏ |
| `O(n^3)` | Cubic | Ba vòng lặp lồng nhau | Dễ timeout khi `n` vài trăm trở lên |
| `O(2^n)` | Exponential | Sinh tất cả subset, backtracking chọn/không chọn | Chỉ dùng khi `n` nhỏ |
| `O(n!)` | Factorial | Sinh mọi hoán vị | Rất đắt |

Thứ tự tăng trưởng từ tốt đến xấu:

```text
O(1) < O(log n) < O(n) < O(n log n) < O(n^2) < O(n^3) < O(2^n) < O(n!)
```

## 6. Ước lượng theo giới hạn input

Một mẹo phỏng vấn và thi thuật toán: nhìn vào `n` để đoán complexity cần đạt.

| Giới hạn `n` | Complexity thường có thể chấp nhận |
|---:|---|
| `n <= 10` | `O(n!)`, `O(2^n)` có thể cân nhắc |
| `n <= 20` | `O(2^n)` thường còn được |
| `n <= 100` | `O(n^3)` có thể được |
| `n <= 1_000` | `O(n^2)` thường được |
| `n <= 100_000` | `O(n log n)` hoặc `O(n)` |
| `n >= 1_000_000` | Ưu tiên `O(n)` hoặc `O(log n)` |

Bảng này chỉ là kinh nghiệm. Thời gian thật còn phụ thuộc vào hằng số, ngôn ngữ, môi trường chạy, số test case và thao tác bên trong vòng lặp.

## 7. Quy tắc rút gọn Big O

### 7.1 Bỏ hằng số

```text
O(2n) -> O(n)
O(100n) -> O(n)
O(n / 2) -> O(n)
```

Ví dụ:

```java
for (int x : nums) {
    System.out.println(x);
}

for (int x : nums) {
    System.out.println(x);
}
```

Hai vòng lặp nối tiếp, mỗi vòng `O(n)`, tổng là `O(2n)`, rút gọn thành `O(n)`.

### 7.2 Giữ phần tăng nhanh nhất

```text
O(n + n^2) -> O(n^2)
O(n log n + n) -> O(n log n)
O(n^3 + 50n^2 + 1000) -> O(n^3)
```

Khi `n` rất lớn, phần tăng nhanh nhất chi phối toàn bộ chi phí.

### 7.3 Vòng lặp nối tiếp thường cộng

```java
for (int i = 0; i < n; i++) {
    // O(1)
}

for (int j = 0; j < m; j++) {
    // O(1)
}
```

Time complexity: `O(n + m)`.

Không được tự động rút thành `O(n)` nếu `n` và `m` là hai kích thước độc lập.

### 7.4 Vòng lặp lồng nhau thường nhân

```java
for (int i = 0; i < n; i++) {
    for (int j = 0; j < m; j++) {
        // O(1)
    }
}
```

Time complexity: `O(n * m)`.

Nếu `m = n`, complexity là `O(n^2)`.

### 7.5 Không phải cứ 2 vòng lồng nhau là `O(n^2)`

Ví dụ:

```java
for (int i = 0; i < n; i++) {
    for (int j = i + 1; j < n; j++) {
        // O(1)
    }
}
```

Số lần chạy là:

```text
(n - 1) + (n - 2) + ... + 1 = n(n - 1) / 2
```

Rút gọn vẫn là `O(n^2)`.

Nhưng ví dụ sau lại khác:

```java
int j = 0;
for (int i = 0; i < n; i++) {
    while (j < n && condition(i, j)) {
        j++;
    }
}
```

Nếu `j` chỉ tăng từ `0` đến `n` trong toàn bộ chương trình, tổng số lần chạy của `while` là `O(n)`, không phải `O(n^2)`. Đây là kiểu phân tích thường gặp trong two pointers hoặc sliding window.

## 8. Phân tích vòng lặp thường gặp

### 8.1 Vòng lặp tăng từng bước

```java
for (int i = 0; i < n; i++) {
    // O(1)
}
```

Time: `O(n)`.

### 8.2 Vòng lặp nhảy theo hằng số

```java
for (int i = 0; i < n; i += 2) {
    // O(1)
}
```

Chạy khoảng `n / 2` lần, rút gọn thành `O(n)`.

### 8.3 Vòng lặp nhân đôi

```java
for (int i = 1; i < n; i *= 2) {
    // O(1)
}
```

Time: `O(log n)`.

Lý do: sau mỗi vòng, `i` nhân đôi. Số lần nhân đôi từ `1` đến `n` là `log2(n)`.

### 8.4 Vòng lặp chia đôi

```java
while (n > 1) {
    n /= 2;
}
```

Time: `O(log n)`.

Đây là trực giác của binary search.

### 8.5 Vòng lặp phụ thuộc vào căn bậc hai

```java
for (int i = 1; i * i <= n; i++) {
    // O(1)
}
```

Time: `O(sqrt(n))`.

Thường gặp khi kiểm tra số nguyên tố hoặc tìm ước.

### 8.6 Vòng lặp tam giác

```java
for (int i = 0; i < n; i++) {
    for (int j = 0; j <= i; j++) {
        // O(1)
    }
}
```

Số bước:

```text
1 + 2 + 3 + ... + n = n(n + 1) / 2
```

Time: `O(n^2)`.

## 9. Ví dụ phân tích code Java

### 9.1 Tìm tổng mảng

```java
int sum(int[] nums) {
    int total = 0;
    for (int num : nums) {
        total += num;
    }
    return total;
}
```

Phân tích:

- Gọi `n` là số phần tử của `nums`.
- Vòng lặp chạy `n` lần.
- Mỗi lần chỉ cộng và gán, là `O(1)`.
- Time complexity: `O(n)`.
- Space complexity: `O(1)`.

### 9.2 Tìm kiếm nhị phân

```java
boolean binarySearch(int[] nums, int target) {
    int left = 0;
    int right = nums.length - 1;

    while (left <= right) {
        int mid = left + (right - left) / 2;
        if (nums[mid] == target) return true;
        if (nums[mid] < target) left = mid + 1;
        else right = mid - 1;
    }

    return false;
}
```

Phân tích:

- Gọi `n` là số phần tử của mảng đã sort.
- Mỗi vòng lặp loại bỏ khoảng một nửa search space.
- Số lần chia đôi từ `n` về `1` là `log2(n)`.
- Time complexity: `O(log n)`.
- Space complexity: `O(1)`.

Điều kiện quan trọng: binary search chỉ đúng khi dữ liệu đã được sắp xếp theo thứ tự phù hợp.

### 9.3 So sánh mọi cặp phần tử

```java
boolean hasPairWithSum(int[] nums, int target) {
    for (int i = 0; i < nums.length; i++) {
        for (int j = i + 1; j < nums.length; j++) {
            if (nums[i] + nums[j] == target) {
                return true;
            }
        }
    }
    return false;
}
```

Phân tích:

- Gọi `n` là số phần tử.
- Vòng ngoài chạy `n` lần.
- Tổng số cặp là `n(n - 1) / 2`.
- Time complexity worst-case: `O(n^2)`.
- Space complexity: `O(1)`.

### 9.4 Dùng HashSet để tối ưu

```java
boolean hasPairWithSum(int[] nums, int target) {
    Set<Integer> seen = new HashSet<>();

    for (int num : nums) {
        int need = target - num;
        if (seen.contains(need)) {
            return true;
        }
        seen.add(num);
    }

    return false;
}
```

Phân tích:

- Duyệt mảng một lần.
- `HashSet.contains` và `HashSet.add` trung bình là `O(1)`.
- Time complexity trung bình: `O(n)`.
- Space complexity: `O(n)` vì có thể lưu gần như toàn bộ phần tử.

Trade-off: đổi thêm bộ nhớ để giảm thời gian từ `O(n^2)` xuống `O(n)`.

### 9.5 Sort rồi two pointers

```java
boolean hasPairWithSum(int[] nums, int target) {
    Arrays.sort(nums);

    int left = 0;
    int right = nums.length - 1;

    while (left < right) {
        int sum = nums[left] + nums[right];
        if (sum == target) return true;
        if (sum < target) left++;
        else right--;
    }

    return false;
}
```

Phân tích:

- Sort mảng tốn `O(n log n)`.
- Two pointers tốn `O(n)`.
- Tổng time: `O(n log n + n)`, rút gọn thành `O(n log n)`.
- Space phụ thuộc implementation của sort trong Java. Với primitive array, `Arrays.sort(int[])` dùng dual-pivot quicksort, thường tính thêm stack `O(log n)`. Với object array, sort ổn định TimSort có thể dùng thêm `O(n)`.

## 10. Nhiều biến input: đừng ép mọi thứ về `n`

Nếu bài toán có nhiều kích thước độc lập, hãy đặt tên rõ:

- `n`: số phần tử mảng A.
- `m`: số phần tử mảng B.
- `V`: số đỉnh của graph.
- `E`: số cạnh của graph.
- `L`: độ dài chuỗi.
- `k`: số lượng kết quả cần lấy.

Ví dụ:

```java
boolean containsCommon(int[] a, int[] b) {
    for (int x : a) {
        for (int y : b) {
            if (x == y) return true;
        }
    }
    return false;
}
```

Nếu `a` có `n` phần tử và `b` có `m` phần tử:

- Time complexity: `O(n * m)`.
- Không nên nói `O(n^2)` trừ khi biết `n = m`.
- Space complexity: `O(1)`.

## 11. Best case, average case, worst case

Một thuật toán có thể có complexity khác nhau tùy dữ liệu.

Ví dụ linear search:

```java
int indexOf(int[] nums, int target) {
    for (int i = 0; i < nums.length; i++) {
        if (nums[i] == target) {
            return i;
        }
    }
    return -1;
}
```

Phân tích:

- Best case: `target` ở index `0`, time `O(1)`.
- Worst case: `target` ở cuối hoặc không tồn tại, time `O(n)`.
- Average case: thường vẫn `O(n)` nếu vị trí xuất hiện phân bố đều.
- Space: `O(1)`.

Trong phỏng vấn, hãy nói rõ bạn đang phân tích case nào. Nếu không được hỏi cụ thể, ưu tiên worst case.

## 12. Recursion và độ phức tạp

Khi gặp recursion, hãy hỏi:

1. Mỗi lời gọi sinh ra bao nhiêu lời gọi con?
2. Độ sâu recursion là bao nhiêu?
3. Mỗi node trong cây recursion tốn bao nhiêu việc ngoài lời gọi con?
4. Có memoization không?

### 12.1 Recursion tuyến tính

```java
int factorial(int n) {
    if (n <= 1) return 1;
    return n * factorial(n - 1);
}
```

Phân tích:

- Mỗi lời gọi chỉ gọi tiếp một lời gọi nhỏ hơn.
- Độ sâu là `n`.
- Mỗi level làm `O(1)` việc.
- Time complexity: `O(n)`.
- Space complexity: `O(n)` do call stack.

### 12.2 Recursion chia đôi

```java
int binarySearch(int[] nums, int left, int right, int target) {
    if (left > right) return -1;

    int mid = left + (right - left) / 2;
    if (nums[mid] == target) return mid;
    if (nums[mid] < target) {
        return binarySearch(nums, mid + 1, right, target);
    }
    return binarySearch(nums, left, mid - 1, target);
}
```

Phân tích:

- Mỗi lần chỉ đi vào một nửa.
- Độ sâu recursion là `O(log n)`.
- Mỗi level làm `O(1)` việc.
- Time complexity: `O(log n)`.
- Space complexity: `O(log n)` do call stack.

### 12.3 Fibonacci đệ quy không memo

```java
int fib(int n) {
    if (n <= 1) return n;
    return fib(n - 1) + fib(n - 2);
}
```

Phân tích:

- Mỗi lời gọi sinh ra 2 lời gọi con.
- Có rất nhiều bài toán con bị tính lại.
- Time complexity: `O(2^n)` theo cách nói phổ biến trong phỏng vấn.
- Space complexity: `O(n)` do độ sâu call stack.

### 12.4 Fibonacci có memoization

```java
int fib(int n, int[] memo) {
    if (n <= 1) return n;
    if (memo[n] != 0) return memo[n];

    memo[n] = fib(n - 1, memo) + fib(n - 2, memo);
    return memo[n];
}
```

Phân tích:

- Mỗi state `fib(i)` chỉ tính một lần.
- Có `n + 1` state.
- Time complexity: `O(n)`.
- Space complexity: `O(n)` cho `memo` và call stack.

## 13. Master theorem cho divide and conquer

Nhiều thuật toán chia để trị có dạng:

```text
T(n) = aT(n / b) + O(n^d)
```

Trong đó:

- `a`: số bài toán con.
- `b`: mỗi bài toán con nhỏ đi bao nhiêu lần.
- `O(n^d)`: chi phí chia bài toán và gộp kết quả ở mỗi level.

Quy tắc nhanh:

| So sánh | Kết quả |
|---|---|
| `a < b^d` | `T(n) = O(n^d)` |
| `a = b^d` | `T(n) = O(n^d log n)` |
| `a > b^d` | `T(n) = O(n^(log_b a))` |

Ví dụ merge sort:

```text
T(n) = 2T(n / 2) + O(n)
```

Ở đây `a = 2`, `b = 2`, `d = 1`, nên `a = b^d`. Kết quả: `O(n log n)`.

Ví dụ binary search:

```text
T(n) = T(n / 2) + O(1)
```

Ở đây `a = 1`, `b = 2`, `d = 0`, nên `a = b^d`. Kết quả: `O(log n)`.

## 14. Amortized complexity

Amortized complexity là chi phí trung bình trên một chuỗi thao tác, không phải average case theo input ngẫu nhiên.

Ví dụ `ArrayList.add`:

- Hầu hết lần thêm phần tử là `O(1)`.
- Khi mảng nội bộ đầy, ArrayList phải cấp phát mảng lớn hơn và copy phần tử, tốn `O(n)`.
- Nhưng việc resize không xảy ra mỗi lần.
- Tính trên nhiều lần thêm, chi phí trung bình mỗi lần là `O(1)` amortized.

Vì vậy có thể nói:

```text
ArrayList add cuối danh sách: O(1) amortized.
Một lần add cụ thể khi resize: O(n).
```

## 15. Complexity của cấu trúc dữ liệu thường gặp

### 15.1 Array

| Operation | Time |
|---|---:|
| Truy cập theo index | `O(1)` |
| Tìm kiếm theo giá trị | `O(n)` |
| Thêm/xóa ở cuối | `O(1)` nếu còn chỗ |
| Thêm/xóa ở đầu hoặc giữa | `O(n)` |

Lý do thêm/xóa ở đầu hoặc giữa là `O(n)`: cần dịch chuyển phần tử.

### 15.2 ArrayList

| Operation | Time |
|---|---:|
| `get(index)` | `O(1)` |
| `add(value)` ở cuối | `O(1)` amortized |
| `add(index, value)` | `O(n)` |
| `remove(index)` | `O(n)` |
| `contains(value)` | `O(n)` |

### 15.3 LinkedList

| Operation | Time |
|---|---:|
| Truy cập index bất kỳ | `O(n)` |
| Thêm/xóa khi đã có node | `O(1)` |
| Tìm node theo giá trị | `O(n)` |
| Thêm đầu/cuối nếu có pointer phù hợp | `O(1)` |

LinkedList không tự động tốt hơn ArrayList. Nó chỉ có lợi khi thao tác nhiều ở đầu/cuối hoặc đã có sẵn reference tới node cần sửa.

### 15.4 Stack và Queue

| Operation | Time |
|---|---:|
| Push / pop stack | `O(1)` |
| Enqueue / dequeue queue | `O(1)` |
| Peek | `O(1)` |
| Tìm kiếm phần tử bất kỳ | `O(n)` |

### 15.5 HashMap và HashSet

| Operation | Average | Worst case |
|---|---:|---:|
| Insert | `O(1)` | `O(n)` |
| Search | `O(1)` | `O(n)` |
| Delete | `O(1)` | `O(n)` |

Trong Java hiện đại, khi bucket quá dài và key comparable, HashMap có thể treeify bucket để cải thiện worst-case bucket thành gần `O(log n)`. Tuy vậy khi trả lời phỏng vấn cơ bản, nói average `O(1)`, worst `O(n)` là an toàn.

### 15.6 Binary Search Tree

| Operation | Balanced BST | Skewed BST |
|---|---:|---:|
| Search | `O(log n)` | `O(n)` |
| Insert | `O(log n)` | `O(n)` |
| Delete | `O(log n)` | `O(n)` |

BST chỉ nhanh khi cây cân bằng. Nếu insert dữ liệu đã sort vào BST thường, cây có thể biến thành linked list.

### 15.7 Heap / PriorityQueue

| Operation | Time |
|---|---:|
| Peek min/max | `O(1)` |
| Insert | `O(log n)` |
| Poll min/max | `O(log n)` |
| Build heap từ mảng | `O(n)` |

Trong Java, `PriorityQueue` mặc định là min-heap.

### 15.8 Trie

Gọi `L` là độ dài chuỗi.

| Operation | Time |
|---|---:|
| Insert word | `O(L)` |
| Search word | `O(L)` |
| Check prefix | `O(L)` |

Space có thể lớn vì mỗi node chứa map/array các ký tự con.

## 16. Complexity của thuật toán phổ biến

### 16.1 Sorting

| Thuật toán | Best | Average | Worst | Space |
|---|---:|---:|---:|---:|
| Bubble sort | `O(n)` nếu tối ưu | `O(n^2)` | `O(n^2)` | `O(1)` |
| Selection sort | `O(n^2)` | `O(n^2)` | `O(n^2)` | `O(1)` |
| Insertion sort | `O(n)` | `O(n^2)` | `O(n^2)` | `O(1)` |
| Merge sort | `O(n log n)` | `O(n log n)` | `O(n log n)` | `O(n)` |
| Quick sort | `O(n log n)` | `O(n log n)` | `O(n^2)` | `O(log n)` stack trung bình |
| Heap sort | `O(n log n)` | `O(n log n)` | `O(n log n)` | `O(1)` |

### 16.2 Graph

Gọi:

- `V`: số đỉnh.
- `E`: số cạnh.

| Thuật toán | Time | Space |
|---|---:|---:|
| BFS adjacency list | `O(V + E)` | `O(V)` |
| DFS adjacency list | `O(V + E)` | `O(V)` |
| BFS/DFS adjacency matrix | `O(V^2)` | `O(V)` ngoài matrix |
| Dijkstra với priority queue | `O((V + E) log V)` | `O(V + E)` |
| Topological sort | `O(V + E)` | `O(V + E)` |

Lưu ý: nếu graph được lưu bằng adjacency matrix, chỉ riêng việc scan neighbors của một đỉnh đã có thể tốn `O(V)`.

### 16.3 Dynamic Programming

Quy tắc nhanh:

```text
Time = số lượng state * chi phí xử lý mỗi state
Space = số lượng state cần lưu
```

Ví dụ `dp[i][j]` với `i` chạy `0..n`, `j` chạy `0..m`, mỗi state tính `O(1)`:

- Time: `O(n * m)`.
- Space: `O(n * m)`.

Nếu chỉ cần hàng trước đó, có thể tối ưu space xuống `O(m)`.

## 17. Space complexity chi tiết hơn

Khi phân tích space, hãy tách rõ:

- Input space: dữ liệu đầu vào đã tồn tại.
- Auxiliary space: bộ nhớ phụ thuật toán tạo thêm.
- Output space: bộ nhớ để trả kết quả, nếu bắt buộc phải tạo.
- Recursion stack: bộ nhớ ngăn xếp do đệ quy.

Ví dụ:

```java
List<Integer> filterPositive(int[] nums) {
    List<Integer> result = new ArrayList<>();
    for (int num : nums) {
        if (num > 0) {
            result.add(num);
        }
    }
    return result;
}
```

Phân tích:

- Time: `O(n)`.
- Space phụ cho `result`: worst-case `O(n)`.
- Nếu người phỏng vấn không tính output bắt buộc, cần nói rõ: auxiliary space ngoài output là `O(1)`, nhưng output có thể là `O(n)`.

Ví dụ recursion:

```java
void printReverse(Node node) {
    if (node == null) return;
    printReverse(node.next);
    System.out.println(node.val);
}
```

Phân tích:

- Time: `O(n)`.
- Space: `O(n)` do call stack, dù không tạo array/list phụ.

## 18. Những lỗi hay gặp khi phân tích Big O

### Lỗi 1: Quên đặt biến input

Không nên nói:

```text
Time là O(n).
```

Nên nói:

```text
Gọi n là số phần tử của mảng nums. Thuật toán duyệt nums một lần nên time là O(n).
```

### Lỗi 2: Nhầm nested loop luôn là `O(n^2)`

Nếu hai vòng lặp chạy theo hai input độc lập, complexity là `O(n * m)`, không phải `O(n^2)`.

### Lỗi 3: Bỏ qua chi phí thao tác thư viện

Ví dụ:

```java
for (String s : list) {
    if (s.substring(0, k).equals(prefix)) {
        count++;
    }
}
```

Không chỉ có vòng lặp `O(n)`. Nếu `substring` hoặc `equals` tốn theo độ dài chuỗi, cần tính thêm chi phí theo `k` hoặc `L`.

### Lỗi 4: Tưởng HashMap luôn luôn `O(1)`

HashMap trung bình là `O(1)`, nhưng worst-case có thể xấu hơn do collision. Trong phỏng vấn, hãy nói "average `O(1)`" nếu dùng hash.

### Lỗi 5: Quên recursion stack

Đệ quy không tạo mảng phụ vẫn có thể tốn `O(depth)` space do call stack.

### Lỗi 6: Tính sort là `O(n)`

Các thuật toán sort so sánh phổ biến thường là `O(n log n)`, không phải `O(n)`. Nếu trong solution có sort, phần sort thường chi phối.

### Lỗi 7: Nhầm `contains` của List và Set

```java
list.contains(x) // O(n)
set.contains(x)  // average O(1)
```

Nếu gọi `list.contains` bên trong vòng lặp `n` lần, tổng có thể thành `O(n^2)`.

### Lỗi 8: Không phân biệt input đã sort và chưa sort

Binary search `O(log n)` chỉ áp dụng khi input đã sort. Nếu phải sort trước, tổng có thể là `O(n log n)`.

## 19. Mẫu trả lời phỏng vấn

Khi được hỏi complexity, trả lời theo mẫu:

```text
Gọi n là ...
Thuật toán làm ...
Phần tốn nhất là ...
Vì vậy time complexity là ...
Về space, thuật toán dùng thêm ...
Vì vậy space complexity là ...
```

Ví dụ:

```text
Gọi n là số phần tử của nums.
Thuật toán duyệt mảng một lần. Mỗi phần tử được xử lý bằng thao tác HashSet trung bình O(1).
Vì vậy time complexity trung bình là O(n).
HashSet có thể lưu tối đa n phần tử, nên space complexity là O(n).
```

Nếu có nhiều biến:

```text
Gọi n là độ dài chuỗi s và m là độ dài chuỗi t.
Ta duyệt qua cả hai chuỗi một lần, nên time là O(n + m).
Mảng đếm ký tự có kích thước cố định 26, nên space là O(1).
```

Nếu có recursion:

```text
Recursion có độ sâu n, mỗi level làm O(1) việc.
Time complexity là O(n).
Do call stack sâu n level, space complexity là O(n).
```

## 20. Cách tự phân tích một đoạn code

Checklist nhanh:

1. Xác định input size: `n`, `m`, `V`, `E`, `L`.
2. Tìm các vòng lặp và số lần chạy của từng vòng.
3. Nếu vòng lặp lồng nhau, kiểm tra chúng độc lập, phụ thuộc nhau, hay là two pointers.
4. Nếu có sort, thêm `O(n log n)` hoặc complexity tương ứng.
5. Nếu có HashMap/HashSet, nói rõ average và worst-case nếu cần.
6. Nếu có recursion, vẽ cây recursion hoặc viết recurrence.
7. Nếu có DP, đếm số state và chi phí mỗi state.
8. Với space, tính cấu trúc dữ liệu phụ, output và recursion stack.
9. Rút gọn bằng cách bỏ hằng số và giữ bậc tăng trưởng lớn nhất.
10. Trình bày rõ ràng bằng lời, không chỉ ném ra đáp án.

## 21. Bài tập tự luyện

Hãy tự phân tích time và space cho các đoạn sau.

### Bài 1

```java
int countEven(int[] nums) {
    int count = 0;
    for (int num : nums) {
        if (num % 2 == 0) count++;
    }
    return count;
}
```

Đáp án: time `O(n)`, space `O(1)`.

### Bài 2

```java
void printPairs(int[] nums) {
    for (int i = 0; i < nums.length; i++) {
        for (int j = i + 1; j < nums.length; j++) {
            System.out.println(nums[i] + ", " + nums[j]);
        }
    }
}
```

Đáp án: time `O(n^2)`, space `O(1)` nếu không tính output console.

### Bài 3

```java
int countCommon(int[] a, int[] b) {
    Set<Integer> set = new HashSet<>();
    for (int x : a) {
        set.add(x);
    }

    int count = 0;
    for (int y : b) {
        if (set.contains(y)) {
            count++;
        }
    }
    return count;
}
```

Đáp án:

- Gọi `n = a.length`, `m = b.length`.
- Time trung bình: `O(n + m)`.
- Space: `O(n)`.

### Bài 4

```java
for (int i = 1; i < n; i *= 3) {
    System.out.println(i);
}
```

Đáp án: time `O(log n)`, space `O(1)`.

### Bài 5

```java
void dfs(List<List<Integer>> graph, int node, boolean[] visited) {
    if (visited[node]) return;
    visited[node] = true;

    for (int nei : graph.get(node)) {
        dfs(graph, nei, visited);
    }
}
```

Đáp án:

- Với adjacency list, nếu chạy DFS từ một component và mỗi đỉnh/cạnh được xét tối đa một số lần hằng.
- Time: `O(V + E)`.
- Space: `O(V)` cho `visited` và recursion stack worst-case.

## 22. Tóm tắt cần nhớ

- Big O mô tả tốc độ tăng trưởng, không phải thời gian chạy tuyệt đối.
- Luôn nói rõ biến `n`, `m`, `V`, `E`, `L` nghĩa là gì.
- Vòng lặp nối tiếp thường cộng, vòng lặp lồng nhau thường nhân.
- Bỏ hằng số và giữ phần tăng nhanh nhất.
- Sort thường là `O(n log n)`.
- HashMap/HashSet trung bình `O(1)`, nhưng có trade-off về space.
- Recursion phải tính cả call stack.
- DP phân tích bằng số state nhân chi phí mỗi state.
- Trong phỏng vấn, ưu tiên worst-case nếu đề không yêu cầu case khác.
