# Big-O Complexity

## 1. Big-O la gi?

Big-O la cach mo ta toc do tang truong cua thuat toan khi kich thuoc input `n` tang len.

Trong phong van, Big-O thuong dung de tra loi:

- Thuat toan chay nhanh hay cham khi du lieu lon?
- Dung bao nhieu bo nho phu?
- Co cach nao toi uu hon khong?

Vi du:

```java
for (int i = 0; i < n; i++) {
    System.out.println(i);
}
```

Vong lap chay `n` lan, nen time complexity la `O(n)`.

## 2. Big-O, Big-Omega, Big-Theta

| Ky hieu | Y nghia | Cach hieu nhanh |
| --- | --- | --- |
| `O(...)` | Upper bound | Toi te khong vuot qua muc nay |
| `Omega(...)` | Lower bound | Tot nhat it nhat muc nay |
| `Theta(...)` | Tight bound | Tang truong chinh xac ve mat bac |

Trong phong van, khi noi "complexity la O(n)", thuong nguoi ta dang noi den upper bound hoac worst case.

## 3. Thu tu do phuc tap pho bien

Tu tot den xau hon:

| Complexity | Ten goi | Vi du |
| --- | --- | --- |
| `O(1)` | Constant | Truy cap `arr[i]`, push/pop stack |
| `O(log n)` | Logarithmic | Binary search |
| `O(n)` | Linear | Duyet mang mot lan |
| `O(n log n)` | Linearithmic | Merge sort, heap sort |
| `O(n^2)` | Quadratic | Hai vong lap long nhau |
| `O(n^3)` | Cubic | Ba vong lap long nhau |
| `O(2^n)` | Exponential | Thu tat ca subset |
| `O(n!)` | Factorial | Thu tat ca hoan vi |

Quy tac nho: khi `n` lon, `O(n log n)` thuong con chap nhan duoc, `O(n^2)` can can nhac, `O(2^n)` va `O(n!)` chi dung duoc voi `n` rat nho.

## 4. Cac quy tac tinh Big-O

### Bo hang so

```java
for (int i = 0; i < 2 * n; i++) {
    // O(1)
}
```

Chay `2n` lan nhung Big-O la `O(n)`, khong phai `O(2n)`.

### Bo thanh phan nho hon

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

Tong la `O(n + n^2)`, rut gon thanh `O(n^2)`.

### Hai input khac nhau thi khong gop thanh mot bien

```java
for (int i = 0; i < a; i++) {
    // O(1)
}

for (int j = 0; j < b; j++) {
    // O(1)
}
```

Complexity la `O(a + b)`, khong nen viet thanh `O(n)`.

Neu long nhau:

```java
for (int i = 0; i < a; i++) {
    for (int j = 0; j < b; j++) {
        // O(1)
    }
}
```

Complexity la `O(a * b)`.

## 5. Phan tich vong lap

### Mot vong lap

```java
for (int i = 0; i < n; i++) {
    sum += arr[i];
}
```

Time: `O(n)`

Space: `O(1)`

### Hai vong lap noi tiep

```java
for (int i = 0; i < n; i++) {
    // O(1)
}

for (int j = 0; j < n; j++) {
    // O(1)
}
```

Time: `O(n + n) = O(n)`

### Hai vong lap long nhau

```java
for (int i = 0; i < n; i++) {
    for (int j = 0; j < n; j++) {
        // O(1)
    }
}
```

Time: `O(n^2)`

### Vong lap tang gap doi

```java
for (int i = 1; i < n; i *= 2) {
    // O(1)
}
```

Time: `O(log n)`

Vi moi lan `i` gap doi: `1, 2, 4, 8, ...`

### Vong lap giam mot nua

```java
while (n > 1) {
    n /= 2;
}
```

Time: `O(log n)`

## 6. Phan tich de quy

### De quy tuyen tinh

```java
int factorial(int n) {
    if (n <= 1) return 1;
    return n * factorial(n - 1);
}
```

Moi lan goi giam `n` di 1.

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

Moi lan loai bo mot nua input.

Time: `O(log n)`

Space: `O(log n)` neu dung de quy, `O(1)` neu dung vong lap.

### Fibonacci naive

```java
int fib(int n) {
    if (n <= 1) return n;
    return fib(n - 1) + fib(n - 2);
}
```

Moi call tach thanh 2 call con.

Time: `O(2^n)`

Space: `O(n)` do do sau call stack.

Neu dung memoization:

Time: `O(n)`

Space: `O(n)`

## 7. Time complexity va space complexity

### Time complexity

Do so phep tinh tang nhu the nao theo input.

Vi du:

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

Time: `O(n)` vi duyet mang mot lan, thao tac `HashSet` trung binh `O(1)`.

### Space complexity

Do bo nho phu tang nhu the nao theo input.

Trong vi du tren:

Space: `O(n)` vi `HashSet` co the luu toi da `n` phan tu.

## 8. Complexity cua data structure pho bien

| Data structure | Access | Search | Insert | Delete |
| --- | --- | --- | --- | --- |
| Array | `O(1)` | `O(n)` | `O(n)` | `O(n)` |
| Linked List | `O(n)` | `O(n)` | `O(1)` neu da co node | `O(1)` neu da co node |
| Stack | `O(n)` | `O(n)` | `O(1)` push | `O(1)` pop |
| Queue | `O(n)` | `O(n)` | `O(1)` enqueue | `O(1)` dequeue |
| HashMap | Khong ap dung | `O(1)` trung binh | `O(1)` trung binh | `O(1)` trung binh |
| Binary Search Tree can bang | `O(log n)` | `O(log n)` | `O(log n)` | `O(log n)` |
| Heap | `O(1)` peek min/max | `O(n)` | `O(log n)` | `O(log n)` poll |

Luu y: `HashMap` worst case co the la `O(n)`, nhung trong phong van thuong lay average case la `O(1)` neu hash function tot.

## 9. Complexity cua thuat toan sap xep

| Algorithm | Best | Average | Worst | Space |
| --- | --- | --- | --- | --- |
| Bubble sort | `O(n)` | `O(n^2)` | `O(n^2)` | `O(1)` |
| Selection sort | `O(n^2)` | `O(n^2)` | `O(n^2)` | `O(1)` |
| Insertion sort | `O(n)` | `O(n^2)` | `O(n^2)` | `O(1)` |
| Merge sort | `O(n log n)` | `O(n log n)` | `O(n log n)` | `O(n)` |
| Quick sort | `O(n log n)` | `O(n log n)` | `O(n^2)` | `O(log n)` average |
| Heap sort | `O(n log n)` | `O(n log n)` | `O(n log n)` | `O(1)` |

## 10. Mot so mau phan tich hay gap

### Two pointers

Thuong la `O(n)` vi moi con tro chi di qua mang mot so lan huu han.

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

Thuong la `O(n)` vi moi phan tu vao window mot lan va ra window mot lan.

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

Time: `O(n)`, khong phai `O(n^2)`, vi `left` va `right` moi con tro chi tien toi.

### BFS / DFS tren graph

Voi adjacency list:

Time: `O(V + E)`

Space: `O(V)`

Trong do:

- `V` la so dinh.
- `E` la so canh.

### Dynamic programming

Complexity thuong bang:

```text
so_luong_state * chi_phi_tinh_moi_state
```

Vi du `dp[i]` voi `i = 0..n` va moi state tinh `O(1)`:

Time: `O(n)`

Space: `O(n)` hoac `O(1)` neu toi uu rolling variables.

## 11. Cach tra loi trong phong van

Khi duoc hoi complexity, co the tra loi theo format:

```text
Time complexity la O(...), vi ...
Space complexity la O(...), vi ...
```

Vi du:

```text
Time complexity la O(n), vi ta duyet qua mang mot lan va moi thao tac HashSet trung binh la O(1).
Space complexity la O(n), vi trong worst case ta luu tat ca phan tu vao HashSet.
```

## 12. Cac loi thuong gap

- Noi `O(2n)` thay vi rut gon thanh `O(n)`.
- Gop hai input khac nhau thanh `O(n)` thay vi `O(a + b)` hoac `O(a * b)`.
- Thay nested loop la ket luan ngay `O(n^2)`, trong khi two pointers/sliding window co the van la `O(n)`.
- Quen tinh space complexity cua recursion call stack.
- Quen phan biet average case va worst case cua `HashMap`, `QuickSort`.
- Chi noi Big-O ma khong giai thich vi sao.

## 13. Checklist nhanh

Truoc khi chot dap an, hay tu hoi:

- Input size la gi? `n`, `m`, `V`, `E`?
- Co bao nhieu vong lap? Noi tiep hay long nhau?
- Vong lap tang/giam theo cong thuc nao? `+1`, `*2`, `/2`?
- Co de quy khong? Moi call sinh bao nhieu call con?
- Co dung bo nho phu khong? Array, HashMap, Set, Queue, Stack?
- Co call stack do de quy khong?
- Dang noi best, average hay worst case?

