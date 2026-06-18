# Stack

## 1. Stack là gì?

`Stack` là cấu trúc dữ liệu hoạt động theo nguyên tắc LIFO:

```text
Last In, First Out
```

Nghĩa là phần tử được thêm vào sau cùng sẽ được lấy ra đầu tiên.

Ví dụ đời thường: chồng đĩa. Cái đĩa đặt lên sau cùng sẽ được lấy ra trước.

## 2. Các thao tác cơ bản

| Thao tác | Ý nghĩa | Độ phức tạp |
| --- | --- | --- |
| `push(x)` | Thêm `x` vào đỉnh stack | `O(1)` |
| `pop()` | Lấy và xóa phần tử ở đỉnh stack | `O(1)` |
| `peek()` / `top()` | Xem phần tử ở đỉnh stack nhưng không xóa | `O(1)` |
| `isEmpty()` | Kiểm tra stack rỗng | `O(1)` |
| `size()` | Lấy số phần tử | `O(1)` |

Nếu stack đang rỗng mà gọi `pop()` hoặc `peek()`, chương trình có thể bị lỗi.

## 3. Stack trong Java

Java có class `Stack`, nhưng trong code hiện đại nên ưu tiên dùng `Deque`.

```java
Deque<Integer> stack = new ArrayDeque<>();

stack.push(10);
stack.push(20);

System.out.println(stack.peek()); // 20
System.out.println(stack.pop());  // 20
System.out.println(stack.pop());  // 10
```

Lý do thường dùng `ArrayDeque` thay vì `Stack`:

- `Stack` là class cũ, kế thừa từ `Vector`.
- `Stack` có synchronized không cần thiết trong nhiều trường hợp.
- `ArrayDeque` thường nhanh và gọn hơn cho bài thuật toán.

## 4. Stack hoạt động như thế nào?

Giả sử ta thực hiện:

```java
stack.push(1);
stack.push(2);
stack.push(3);
```

Stack sẽ như sau:

```text
Top
 3
 2
 1
Bottom
```

Nếu gọi:

```java
stack.pop();
```

Phần tử `3` được lấy ra trước.

## 5. Khi nào nên nghĩ đến Stack?

Stack thường hữu ích khi bài toán có một trong các dấu hiệu:

- Cần xử lý phần tử gần nhất chưa được ghép/cặp.
- Cần undo thao tác gần nhất.
- Cần kiểm tra dấu ngoặc.
- Cần duyệt biểu thức.
- Cần mô phỏng recursion.
- Cần tìm phần tử lớn hơn/nhỏ hơn tiếp theo.
- Cần xử lý theo kiểu "vào sau ra trước".

## 6. Ví dụ 1: Kiểm tra dấu ngoặc hợp lệ

Bài toán: Cho chuỗi gồm `(`, `)`, `{`, `}`, `[`, `]`. Kiểm tra chuỗi có hợp lệ không.

Ví dụ:

```text
"()[]{}" -> true
"([{}])" -> true
"(]"     -> false
"([)]"   -> false
```

Ý tưởng:

- Nếu gặp ngoặc mở, push vào stack.
- Nếu gặp ngoặc đóng, kiểm tra nó có khớp với ngoặc mở gần nhất không.
- Cuối cùng stack phải rỗng.

```java
public boolean isValid(String s) {
    Deque<Character> stack = new ArrayDeque<>();

    for (int i = 0; i < s.length(); i++) {
        char c = s.charAt(i);

        if (c == '(' || c == '[' || c == '{') {
            stack.push(c);
        } else {
            if (stack.isEmpty()) return false;

            char top = stack.pop();

            if (c == ')' && top != '(') return false;
            if (c == ']' && top != '[') return false;
            if (c == '}' && top != '{') return false;
        }
    }

    return stack.isEmpty();
}
```

Time: `O(n)`

Space: `O(n)`

Vì ta duyệt chuỗi một lần, và trong worst case stack có thể chứa toàn bộ ngoặc mở.

## 7. Ví dụ 2: Min Stack

Bài toán: Thiết kế stack hỗ trợ:

- `push(x)`
- `pop()`
- `top()`
- `getMin()`

Tất cả đều phải là `O(1)`.

Ý tưởng: dùng thêm một stack phụ để lưu giá trị nhỏ nhất tại mỗi thời điểm.

```java
class MinStack {
    private Deque<Integer> stack = new ArrayDeque<>();
    private Deque<Integer> minStack = new ArrayDeque<>();

    public void push(int val) {
        stack.push(val);

        if (minStack.isEmpty() || val <= minStack.peek()) {
            minStack.push(val);
        }
    }

    public void pop() {
        int removed = stack.pop();

        if (removed == minStack.peek()) {
            minStack.pop();
        }
    }

    public int top() {
        return stack.peek();
    }

    public int getMin() {
        return minStack.peek();
    }
}
```

Time: `O(1)` cho mỗi thao tác.

Space: `O(n)` vì cần stack phụ.

## 8. Ví dụ 3: Next Greater Element

Bài toán: Với mỗi phần tử trong mảng, tìm phần tử lớn hơn đầu tiên nằm bên phải nó.

Ví dụ:

```text
Input:  [2, 1, 2, 4, 3]
Output: [4, 2, 4, -1, -1]
```

Ý tưởng dùng monotonic stack:

- Duyệt từ phải sang trái.
- Stack lưu các phần tử có khả năng là đáp án.
- Loại bỏ các phần tử nhỏ hơn hoặc bằng `nums[i]`.
- Sau khi loại bỏ, đỉnh stack là next greater nếu stack không rỗng.

```java
public int[] nextGreaterElements(int[] nums) {
    int n = nums.length;
    int[] result = new int[n];
    Deque<Integer> stack = new ArrayDeque<>();

    for (int i = n - 1; i >= 0; i--) {
        while (!stack.isEmpty() && stack.peek() <= nums[i]) {
            stack.pop();
        }

        result[i] = stack.isEmpty() ? -1 : stack.peek();
        stack.push(nums[i]);
    }

    return result;
}
```

Time: `O(n)`

Space: `O(n)`

Mặc dù có `while` bên trong `for`, mỗi phần tử chỉ bị push một lần và pop tối đa một lần, nên tổng thể vẫn là `O(n)`.

## 9. Monotonic Stack

`Monotonic Stack` là stack được duy trì theo thứ tự tăng hoặc giảm.

Có 2 kiểu thường gặp:

| Kiểu | Ý nghĩa |
| --- | --- |
| Monotonic increasing stack | Stack tăng dần |
| Monotonic decreasing stack | Stack giảm dần |

Thường dùng cho các bài:

- Next Greater Element.
- Next Smaller Element.
- Daily Temperatures.
- Largest Rectangle in Histogram.
- Trapping Rain Water.
- Remove K Digits.

Điểm quan trọng: nhìn có vẻ `O(n^2)` vì có `while` trong `for`, nhưng thường là `O(n)` do mỗi phần tử chỉ vào stack một lần và ra stack một lần.

## 10. Stack và recursion

Đệ quy sử dụng call stack của chương trình.

Ví dụ:

```java
void print(int n) {
    if (n == 0) return;
    print(n - 1);
    System.out.println(n);
}
```

Khi gọi `print(3)`, call stack sẽ đi sâu:

```text
print(3)
print(2)
print(1)
print(0)
```

Sau đó mới quay ngược lại để in:

```text
1
2
3
```

Vì vậy, recursion có thể được mô phỏng bằng stack thủ công.

## 11. Stack overflow là gì?

`StackOverflowError` xảy ra khi call stack bị đầy, thường do đệ quy quá sâu hoặc thiếu base case.

Ví dụ lỗi:

```java
void infinite() {
    infinite();
}
```

Hàm này gọi chính nó mãi mãi, làm stack đầy.

## 12. Cài đặt Stack bằng array

```java
class MyStack {
    private int[] data;
    private int top;

    public MyStack(int capacity) {
        data = new int[capacity];
        top = -1;
    }

    public void push(int value) {
        if (top == data.length - 1) {
            throw new RuntimeException("Stack is full");
        }
        data[++top] = value;
    }

    public int pop() {
        if (isEmpty()) {
            throw new RuntimeException("Stack is empty");
        }
        return data[top--];
    }

    public int peek() {
        if (isEmpty()) {
            throw new RuntimeException("Stack is empty");
        }
        return data[top];
    }

    public boolean isEmpty() {
        return top == -1;
    }
}
```

Time:

- `push`: `O(1)`
- `pop`: `O(1)`
- `peek`: `O(1)`

Space: `O(n)` với `n` là capacity.

## 13. Cài đặt Stack bằng linked list

```java
class MyStack {
    private Node top;

    private static class Node {
        int value;
        Node next;

        Node(int value) {
            this.value = value;
        }
    }

    public void push(int value) {
        Node node = new Node(value);
        node.next = top;
        top = node;
    }

    public int pop() {
        if (isEmpty()) {
            throw new RuntimeException("Stack is empty");
        }

        int value = top.value;
        top = top.next;
        return value;
    }

    public int peek() {
        if (isEmpty()) {
            throw new RuntimeException("Stack is empty");
        }

        return top.value;
    }

    public boolean isEmpty() {
        return top == null;
    }
}
```

Time:

- `push`: `O(1)`
- `pop`: `O(1)`
- `peek`: `O(1)`

Space: `O(n)` với `n` là số phần tử trong stack.

## 14. Stack và Queue khác nhau thế nào?

| Cấu trúc | Nguyên tắc | Thêm | Lấy ra |
| --- | --- | --- | --- |
| Stack | LIFO | Thêm ở đỉnh | Lấy ở đỉnh |
| Queue | FIFO | Thêm ở cuối | Lấy ở đầu |

Stack:

```text
Last In, First Out
```

Queue:

```text
First In, First Out
```

## 15. Lỗi thường gặp

- Gọi `pop()` hoặc `peek()` khi stack rỗng.
- Dùng `Stack` cũ của Java thay vì `Deque`.
- Nhìn thấy `while` trong `for` của monotonic stack rồi kết luận nhầm là `O(n^2)`.
- Quên tính `O(n)` space khi stack có thể chứa nhiều phần tử.
- Quên rằng recursion cũng dùng call stack.
- Với bài dấu ngoặc, quên kiểm tra stack rỗng trước khi `pop()`.

## 16. Cách trả lời phỏng vấn

Nếu được hỏi Stack là gì:

```text
Stack là cấu trúc dữ liệu theo nguyên tắc LIFO, phần tử vào sau sẽ ra trước. Các thao tác chính là push, pop, peek, thường có độ phức tạp O(1). Stack thường dùng trong kiểm tra dấu ngoặc, undo, DFS, xử lý biểu thức, recursion và monotonic stack.
```

Nếu được hỏi vì sao valid parentheses dùng stack:

```text
Vì dấu ngoặc đóng phải khớp với dấu ngoặc mở gần nhất chưa được đóng. Đây đúng là hành vi LIFO, nên stack phù hợp để lưu các dấu ngoặc mở và kiểm tra khi gặp dấu ngoặc đóng.
```

