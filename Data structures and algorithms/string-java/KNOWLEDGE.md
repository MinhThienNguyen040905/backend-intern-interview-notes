# String trong Java

## 1. String là gì?

`String` trong Java là một class dùng để biểu diễn chuỗi ký tự.

Ví dụ:

```java
String name = "Java";
String message = "Hello World";
```

Điểm rất quan trọng: `String` là immutable, nghĩa là sau khi tạo ra thì nội dung của object `String` không thể bị thay đổi.

## 2. String immutable nghĩa là gì?

Ví dụ:

```java
String s = "Hello";
s = s + " Java";
```

Dòng `s = s + " Java"` không sửa object `"Hello"` ban đầu. Java tạo ra một object `String` mới là `"Hello Java"`, sau đó biến `s` trỏ sang object mới.

Vì vậy, nếu nối chuỗi nhiều lần bằng `+` trong vòng lặp, chương trình có thể rất tốn thời gian và bộ nhớ.

## 3. Vì sao String được thiết kế immutable?

Một số lý do chính:

- An toàn khi dùng trong nhiều thread.
- Có thể cache hash code để dùng hiệu quả trong `HashMap`, `HashSet`.
- Hỗ trợ String Pool.
- An toàn hơn khi dùng làm key, username, password path, class loading, network connection.

Ví dụ dùng `String` làm key:

```java
Map<String, Integer> scores = new HashMap<>();
scores.put("Alice", 90);
```

Nếu `String` có thể bị thay đổi sau khi làm key, cấu trúc hash có thể bị sai.

## 4. String Pool

String Pool là vùng nhớ đặc biệt trong heap dùng để lưu các string literal.

Ví dụ:

```java
String a = "Java";
String b = "Java";

System.out.println(a == b); // true
```

Cả `a` và `b` cùng trỏ đến một object `"Java"` trong String Pool.

Nhưng:

```java
String a = "Java";
String b = new String("Java");

System.out.println(a == b);      // false
System.out.println(a.equals(b)); // true
```

`new String("Java")` tạo object mới trong heap, nên `a == b` là `false`.

## 5. So sánh String: `==` và `.equals()`

### Dùng `==`

`==` so sánh reference, tức là hai biến có trỏ đến cùng một object hay không.

```java
String a = "Java";
String b = "Java";

System.out.println(a == b); // true
```

### Dùng `.equals()`

`.equals()` so sánh nội dung chuỗi.

```java
String a = new String("Java");
String b = new String("Java");

System.out.println(a == b);      // false
System.out.println(a.equals(b)); // true
```

Trong thực tế, khi cần so sánh nội dung chuỗi, luôn dùng `.equals()`.

## 6. Tránh NullPointerException khi so sánh String

Không nên viết:

```java
if (status.equals("ACTIVE")) {
    // ...
}
```

Nếu `status == null`, code sẽ bị `NullPointerException`.

Cách an toàn hơn:

```java
if ("ACTIVE".equals(status)) {
    // ...
}
```

Hoặc dùng:

```java
if (Objects.equals(status, "ACTIVE")) {
    // ...
}
```

## 7. StringBuilder và StringBuffer

Khi cần nối chuỗi nhiều lần, nên dùng `StringBuilder`.

```java
StringBuilder sb = new StringBuilder();

for (int i = 0; i < 1000; i++) {
    sb.append(i);
}

String result = sb.toString();
```

### So sánh nhanh

| Class | Mutable | Thread-safe | Khi nào dùng |
| --- | --- | --- | --- |
| `String` | Không | Có, vì immutable | Chuỗi ít thay đổi |
| `StringBuilder` | Có | Không | Nối chuỗi nhiều lần trong một thread |
| `StringBuffer` | Có | Có | Nối chuỗi trong môi trường nhiều thread |

Trong phỏng vấn hoặc code thông thường, `StringBuilder` được dùng nhiều hơn `StringBuffer`.

## 8. Độ phức tạp thường gặp

Giả sử chuỗi có độ dài `n`.

| Thao tác | Complexity |
| --- | --- |
| `s.length()` | `O(1)` |
| `s.charAt(i)` | `O(1)` |
| `s.equals(t)` | `O(n)` |
| `s.substring(...)` | `O(k)` với `k` là độ dài substring |
| `s.contains(t)` | Thường là `O(n * m)` trong cách hiểu đơn giản |
| Duyệt toàn bộ chuỗi | `O(n)` |
| Nối chuỗi bằng `+` trong loop | Có thể lên tới `O(n^2)` |
| Nối chuỗi bằng `StringBuilder` | `O(n)` tổng thể |

Ví dụ nối chuỗi không tốt:

```java
String result = "";

for (String word : words) {
    result += word;
}
```

Mỗi lần nối có thể tạo string mới và copy lại dữ liệu cũ.

Cách tốt hơn:

```java
StringBuilder sb = new StringBuilder();

for (String word : words) {
    sb.append(word);
}

String result = sb.toString();
```

## 9. Các method String hay dùng

```java
String s = "Hello Java";

s.length();          // 10
s.charAt(0);         // 'H'
s.substring(0, 5);   // "Hello"
s.toLowerCase();     // "hello java"
s.toUpperCase();     // "HELLO JAVA"
s.trim();            // xóa khoảng trắng đầu/cuối
s.contains("Java");  // true
s.startsWith("He");  // true
s.endsWith("va");    // true
s.indexOf("Java");   // 6
s.replace("Java", "World");
s.split(" ");
```

Lưu ý: các method như `toLowerCase()`, `replace()`, `trim()` không sửa chuỗi gốc, mà trả về chuỗi mới.

Ví dụ:

```java
String s = " Java ";
s.trim();

System.out.println(s); // vẫn là " Java "
```

Phải gán lại:

```java
s = s.trim();
```

## 10. Duyệt String

### Duyệt bằng index

```java
String s = "abc";

for (int i = 0; i < s.length(); i++) {
    char c = s.charAt(i);
    System.out.println(c);
}
```

Time: `O(n)`

Space: `O(1)`

### Chuyển sang char array

```java
char[] chars = s.toCharArray();

for (char c : chars) {
    System.out.println(c);
}
```

Time: `O(n)`

Space: `O(n)` vì tạo thêm mảng char.

## 11. Một số bài toán String hay gặp

### Đếm tần suất ký tự

```java
public int[] countFrequency(String s) {
    int[] count = new int[26];

    for (int i = 0; i < s.length(); i++) {
        char c = s.charAt(i);
        count[c - 'a']++;
    }

    return count;
}
```

Áp dụng khi chuỗi chỉ gồm chữ cái thường `a-z`.

Time: `O(n)`

Space: `O(1)` vì mảng có kích thước cố định 26.

### Kiểm tra palindrome

```java
public boolean isPalindrome(String s) {
    int left = 0;
    int right = s.length() - 1;

    while (left < right) {
        if (s.charAt(left) != s.charAt(right)) {
            return false;
        }
        left++;
        right--;
    }

    return true;
}
```

Time: `O(n)`

Space: `O(1)`

### Đảo ngược chuỗi

```java
public String reverse(String s) {
    StringBuilder sb = new StringBuilder(s);
    return sb.reverse().toString();
}
```

Time: `O(n)`

Space: `O(n)`

### Kiểm tra anagram

```java
public boolean isAnagram(String s, String t) {
    if (s.length() != t.length()) return false;

    int[] count = new int[26];

    for (int i = 0; i < s.length(); i++) {
        count[s.charAt(i) - 'a']++;
        count[t.charAt(i) - 'a']--;
    }

    for (int value : count) {
        if (value != 0) return false;
    }

    return true;
}
```

Time: `O(n)`

Space: `O(1)`

## 12. String trong Java và Unicode

`char` trong Java là 16-bit và dùng UTF-16 code unit. Với các ký tự đặc biệt như emoji hoặc một số ký tự ngoài BMP, một ký tự hiển thị có thể cần nhiều hơn một `char`.

Ví dụ:

```java
String s = "😊";

System.out.println(s.length());      // có thể là 2
System.out.println(s.codePointCount(0, s.length())); // 1
```

Trong hầu hết bài phỏng vấn cơ bản, input thường là ASCII hoặc chữ cái thường `a-z`. Nhưng nếu bài toán nhắc đến Unicode, cần cẩn thận với `charAt()`.

## 13. Lỗi thường gặp

- Dùng `==` để so sánh nội dung chuỗi.
- Nối chuỗi bằng `+` trong vòng lặp lớn.
- Quên rằng `String` là immutable.
- Gọi `s.equals(...)` khi `s` có thể là `null`.
- Dùng `substring()` hoặc `toCharArray()` mà quên tính thêm space complexity.
- Giả định mọi ký tự đều tương ứng đúng một `char`, trong khi Unicode có thể phức tạp hơn.

## 14. Cách trả lời phỏng vấn

Nếu được hỏi "`String` trong Java có gì đặc biệt?", có thể trả lời:

```text
String trong Java là immutable. Khi thay đổi hoặc nối chuỗi, Java thường tạo object String mới thay vì sửa object cũ. String literal được lưu trong String Pool nên hai literal giống nhau có thể trỏ đến cùng một object. Khi so sánh nội dung chuỗi, nên dùng equals(), không dùng ==. Nếu cần nối chuỗi nhiều lần, nên dùng StringBuilder để tránh tạo nhiều object không cần thiết.
```

Nếu được hỏi độ phức tạp khi xử lý chuỗi:

```text
Với chuỗi độ dài n, duyệt chuỗi là O(n), so sánh hai chuỗi thường là O(n), nối chuỗi nhiều lần bằng + trong loop có thể là O(n^2), còn dùng StringBuilder thì tổng thể thường là O(n).
```

