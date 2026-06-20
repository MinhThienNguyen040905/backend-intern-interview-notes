# Bảng băm (Hash Table)

## 1. Bảng băm là gì?

`Hash Table` là cấu trúc dữ liệu lưu các cặp `key-value` và sử dụng hàm băm để xác định vị trí lưu dữ liệu.

Trong Java, hai cấu trúc thường gặp nhất là:

- `HashMap<K, V>`: lưu cặp key-value.
- `HashSet<E>`: lưu các phần tử không trùng nhau.

Ví dụ:

```java
Map<String, Integer> ages = new HashMap<>();

ages.put("An", 20);
ages.put("Binh", 22);

System.out.println(ages.get("An")); // 20
```

## 2. Bảng băm hoạt động như thế nào?

Khi thêm một cặp key-value:

```java
map.put("An", 20);
```

Java thực hiện ý tưởng gần giống:

```text
key -> hashCode -> tính bucket -> lưu entry vào bucket
```

Ví dụ đơn giản:

```text
hash("An") = 12345
bucketIndex = 12345 % số_bucket
```

Khi gọi:

```java
map.get("An");
```

Java tính lại hash của key để tìm bucket tương ứng, sau đó tìm đúng key trong bucket đó.

## 3. Hash function là gì?

Hash function chuyển key thành một giá trị số gọi là hash code.

Trong Java, object cung cấp method:

```java
int hashCode()
```

Một hash function tốt nên:

- Cho cùng một kết quả với cùng một key.
- Phân bố key tương đối đều giữa các bucket.
- Tính toán nhanh.
- Hạn chế collision.

Hai key khác nhau vẫn có thể có cùng hash code.

## 4. Collision là gì?

`Collision` xảy ra khi hai key khác nhau được đưa vào cùng một bucket.

Ví dụ:

```text
hash("An")   -> bucket 3
hash("Binh") -> bucket 3
```

Collision không có nghĩa là dữ liệu bị ghi đè. Hash table phải lưu và phân biệt cả hai key trong bucket đó.

Các cách xử lý collision phổ biến:

- Separate chaining: mỗi bucket chứa nhiều entry.
- Open addressing: tìm một vị trí trống khác trong mảng.

`HashMap` trong Java sử dụng chaining. Khi một bucket có nhiều phần tử, nó có thể chuyển từ linked list sang balanced tree để tìm kiếm hiệu quả hơn.

## 5. `hashCode()` và `equals()`

`HashMap` và `HashSet` sử dụng cả `hashCode()` lẫn `equals()`.

Quy trình tìm key:

1. Dùng `hashCode()` để tìm bucket.
2. Dùng `equals()` để tìm đúng key trong bucket.

Quy tắc quan trọng:

```text
Nếu a.equals(b) là true thì a.hashCode() phải bằng b.hashCode().
```

Chiều ngược lại không bắt buộc:

```text
a.hashCode() == b.hashCode() chưa chắc a.equals(b) là true.
```

Ví dụ class dùng làm key:

```java
class User {
    private final int id;
    private final String name;

    User(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof User other)) return false;
        return id == other.id && Objects.equals(name, other.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
```

Nếu override `equals()` thì thông thường cũng phải override `hashCode()`.

## 6. Độ phức tạp

| Thao tác | Trung bình | Worst case |
| --- | --- | --- |
| Thêm | `O(1)` | `O(n)` |
| Tìm kiếm | `O(1)` | `O(n)` |
| Xóa | `O(1)` | `O(n)` |

Average case là `O(1)` vì hash function giúp đi gần như trực tiếp đến bucket chứa dữ liệu.

Worst case là `O(n)` khi rất nhiều key rơi vào cùng một bucket. Với `HashMap` Java hiện đại, bucket đủ lớn có thể được chuyển thành balanced tree, giúp thao tác trong bucket đạt khoảng `O(log n)` nếu các điều kiện cần thiết được đáp ứng.

Space complexity thường là `O(n)` vì cần lưu `n` phần tử.

## 7. Load factor và resize

`Load factor` thể hiện mức độ đầy của hash table:

```text
load factor = số_phần_tử / số_bucket
```

`HashMap` trong Java mặc định thường có load factor là `0.75`.

Khi số phần tử vượt ngưỡng:

```text
capacity * loadFactor
```

HashMap sẽ tăng capacity và phân bố lại các entry. Quá trình này gọi là resize hoặc rehash.

Một lần resize có thể tốn `O(n)`, nhưng `put()` vẫn được xem là `O(1)` amortized vì resize không xảy ra ở mọi lần thêm.

## 8. HashMap trong Java

```java
Map<String, Integer> scores = new HashMap<>();

scores.put("An", 90);
scores.put("Binh", 85);

int score = scores.get("An");
boolean exists = scores.containsKey("An");
scores.remove("Binh");
```

Các method hay dùng:

| Method | Ý nghĩa |
| --- | --- |
| `put(key, value)` | Thêm hoặc cập nhật giá trị |
| `get(key)` | Lấy giá trị theo key |
| `getOrDefault(key, defaultValue)` | Lấy value hoặc giá trị mặc định |
| `containsKey(key)` | Kiểm tra key tồn tại |
| `containsValue(value)` | Kiểm tra value tồn tại |
| `remove(key)` | Xóa theo key |
| `putIfAbsent(key, value)` | Chỉ thêm nếu key chưa tồn tại |
| `keySet()` | Lấy tập hợp key |
| `values()` | Lấy collection value |
| `entrySet()` | Lấy các cặp key-value |

## 9. Duyệt HashMap

Cách hiệu quả và rõ ràng khi cần cả key và value:

```java
for (Map.Entry<String, Integer> entry : scores.entrySet()) {
    String name = entry.getKey();
    int score = entry.getValue();

    System.out.println(name + ": " + score);
}
```

Không nên giả định `HashMap` duy trì thứ tự thêm phần tử.

Nếu cần thứ tự:

- `LinkedHashMap`: duy trì thứ tự thêm hoặc thứ tự truy cập.
- `TreeMap`: sắp xếp theo key, thao tác thường là `O(log n)`.

## 10. HashSet trong Java

`HashSet` lưu các phần tử duy nhất, không lưu cặp key-value trực tiếp.

```java
Set<Integer> numbers = new HashSet<>();

numbers.add(10);
numbers.add(20);
numbers.add(10);

System.out.println(numbers.size()); // 2
```

Các thao tác chính:

```java
set.add(value);
set.contains(value);
set.remove(value);
```

Average time của các thao tác trên là `O(1)`.

Về ý tưởng, `HashSet` được xây dựng dựa trên hash table và chỉ quan tâm đến key.

## 11. Ví dụ 1: Đếm tần suất

```java
public Map<Integer, Integer> countFrequency(int[] nums) {
    Map<Integer, Integer> frequency = new HashMap<>();

    for (int num : nums) {
        frequency.put(num, frequency.getOrDefault(num, 0) + 1);
    }

    return frequency;
}
```

Time: `O(n)` trung bình.

Space: `O(n)` trong worst case.

Ví dụ:

```text
Input:  [1, 2, 1, 3, 2, 1]
Output: {1=3, 2=2, 3=1}
```

## 12. Ví dụ 2: Contains Duplicate

```java
public boolean containsDuplicate(int[] nums) {
    Set<Integer> seen = new HashSet<>();

    for (int num : nums) {
        if (!seen.add(num)) {
            return true;
        }
    }

    return false;
}
```

`HashSet.add()` trả về `false` nếu phần tử đã tồn tại.

Time: `O(n)` trung bình.

Space: `O(n)`.

## 13. Ví dụ 3: Two Sum

Bài toán: tìm hai vị trí có tổng bằng `target`.

```java
public int[] twoSum(int[] nums, int target) {
    Map<Integer, Integer> indexByValue = new HashMap<>();

    for (int i = 0; i < nums.length; i++) {
        int needed = target - nums[i];

        if (indexByValue.containsKey(needed)) {
            return new int[] {indexByValue.get(needed), i};
        }

        indexByValue.put(nums[i], i);
    }

    return new int[0];
}
```

Time: `O(n)` trung bình.

Space: `O(n)`.

HashMap giúp tìm `target - nums[i]` trong `O(1)` trung bình, thay vì dùng hai vòng lặp `O(n^2)`.

## 14. Ví dụ 4: Kiểm tra Anagram

```java
public boolean isAnagram(String s, String t) {
    if (s.length() != t.length()) return false;

    Map<Character, Integer> frequency = new HashMap<>();

    for (char c : s.toCharArray()) {
        frequency.put(c, frequency.getOrDefault(c, 0) + 1);
    }

    for (char c : t.toCharArray()) {
        Integer count = frequency.get(c);
        if (count == null) return false;

        if (count == 1) {
            frequency.remove(c);
        } else {
            frequency.put(c, count - 1);
        }
    }

    return frequency.isEmpty();
}
```

Time: `O(n)` trung bình.

Space: `O(k)` với `k` là số ký tự khác nhau.

Nếu input chỉ gồm `a-z`, dùng mảng `int[26]` thường đơn giản và nhanh hơn HashMap.

## 15. Key mutable có thể gây lỗi

Không nên thay đổi những field tham gia vào `equals()` và `hashCode()` sau khi object đã được dùng làm key.

Ví dụ nguy hiểm:

```java
Map<User, String> map = new HashMap<>();
User user = new User(1, "An");

map.put(user, "data");

// Nếu thay đổi id hoặc name làm hashCode thay đổi,
// map có thể không tìm thấy key cũ nữa.
```

Vì vậy, key nên là immutable. `String` rất phù hợp làm key vì nó immutable.

## 16. HashMap có cho phép null không?

`HashMap` cho phép:

- Một key `null`.
- Nhiều value `null`.

```java
Map<String, Integer> map = new HashMap<>();

map.put(null, 1);
map.put("A", null);
```

Tuy nhiên, không phải mọi implementation của `Map` đều hỗ trợ `null`. Ví dụ `ConcurrentHashMap` không cho phép key hoặc value là `null`.

## 17. Khi nào nên nghĩ đến Hash Table?

Các dấu hiệu thường gặp:

- Cần kiểm tra phần tử đã xuất hiện chưa.
- Cần đếm tần suất.
- Cần tìm kiếm nhanh theo key.
- Cần loại bỏ phần tử trùng.
- Cần ánh xạ một giá trị sang thông tin khác.
- Bài toán có dạng tìm phần bù như Two Sum.
- Cần nhóm dữ liệu theo một đặc điểm, ví dụ Group Anagrams.

## 18. Lỗi thường gặp

- Dùng `containsValue()` và nghĩ rằng nó là `O(1)`; thao tác này thường phải duyệt các value nên là `O(n)`.
- Quên override `hashCode()` khi đã override `equals()`.
- Dùng object mutable làm key rồi thay đổi object.
- Giả định `HashMap` duy trì thứ tự.
- Nói mọi thao tác luôn là `O(1)` mà không đề cập average case và collision.
- Dùng `get(key) == null` để kết luận key không tồn tại, trong khi key có thể tồn tại với value `null`.

Để kiểm tra key chính xác:

```java
map.containsKey(key);
```

## 19. Cách trả lời phỏng vấn

Nếu được hỏi bảng băm là gì:

```text
Hash table lưu dữ liệu theo key và dùng hash function để xác định bucket chứa key. Nhờ đó, thêm, tìm kiếm và xóa có độ phức tạp O(1) trung bình. Khi nhiều key rơi vào cùng một bucket sẽ xảy ra collision và cấu trúc phải xử lý các entry trong bucket đó. Trong Java, HashMap dùng cả hashCode() để tìm bucket và equals() để xác định đúng key.
```

Nếu được hỏi tại sao `HashMap` có thể tìm nhanh:

```text
HashMap không cần duyệt toàn bộ phần tử. Nó tính hash của key để đi gần như trực tiếp đến bucket có thể chứa key, sau đó chỉ so sánh các entry trong bucket đó.
```

