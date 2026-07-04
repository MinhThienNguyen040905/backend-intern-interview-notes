# Crow's Foot notation

## 1. Crow's Foot là gì?

`Crow's Foot` (ký pháp chân quạ) là cách biểu diễn các thực thể và mối quan hệ giữa chúng trong sơ đồ quan hệ thực thể (`ERD` — Entity Relationship Diagram).

Tên gọi “chân quạ” xuất phát từ ký hiệu ba nhánh dùng để biểu diễn số lượng **nhiều** (`many`).

Crow's Foot thường được dùng khi:

- Phân tích và thiết kế cơ sở dữ liệu quan hệ.
- Mô tả các bảng trước khi tạo database.
- Xác định khóa chính, khóa ngoại và ràng buộc giữa các bảng.
- Trao đổi thiết kế dữ liệu giữa developer, database engineer và business analyst.

## 2. Các thành phần cơ bản

### Entity — Thực thể

Entity đại diện cho một đối tượng hoặc khái niệm cần lưu dữ liệu, chẳng hạn:

- `Customer`
- `Order`
- `Product`

Khi chuyển sang cơ sở dữ liệu quan hệ, một entity thường trở thành một bảng.

```text
+-------------------+
| Customer          |
+-------------------+
| PK customer_id    |
|    name           |
|    email          |
+-------------------+
```

### Attribute — Thuộc tính

Attribute mô tả dữ liệu của một entity. Ví dụ, `Customer` có các thuộc tính:

- `customer_id`
- `name`
- `email`

### Primary Key — Khóa chính

`PK` (Primary Key) là thuộc tính hoặc nhóm thuộc tính dùng để định danh duy nhất một bản ghi.

```text
PK customer_id
```

### Foreign Key — Khóa ngoại

`FK` (Foreign Key) là thuộc tính tham chiếu đến khóa chính của một bảng khác, giúp hiện thực hóa mối quan hệ.

```text
Order.customer_id -> Customer.customer_id
```

## 3. Các ký hiệu số lượng

Mỗi đầu của đường quan hệ có hai thông tin:

1. Số lượng tối thiểu: `0` hoặc `1`.
2. Số lượng tối đa: `1` hoặc `nhiều`.

Các ký hiệu cơ bản:

| Ký hiệu | Ý nghĩa |
|---|---|
| `○` | Không bắt buộc, số lượng tối thiểu là `0` |
| `|` | Một |
| `<` | Nhiều, ký hiệu chân quạ |

Khi ghép ký hiệu tối thiểu và tối đa:

| Ký hiệu mô phỏng | Cách đọc | Số lượng |
|---|---|---|
| `○|` | Không hoặc một | `0..1` |
| `||` | Chính xác một | `1..1` |
| `○<` | Không hoặc nhiều | `0..N` |
| `|<` | Một hoặc nhiều | `1..N` |

> Trong các công cụ vẽ ERD, hình dáng có thể khác đôi chút nhưng ý nghĩa `0`, `1` và `many` không thay đổi.

## 4. Cách đọc một quan hệ

Hãy đọc quan hệ theo **cả hai chiều**.

Ví dụ:

```text
Customer ||----------○< Order
```

Đọc từ `Customer` sang `Order`:

- Một `Customer` có thể có `0` hoặc nhiều `Order`.

Đọc từ `Order` sang `Customer`:

- Mỗi `Order` phải thuộc về chính xác `1` `Customer`.

Mẹo đọc:

1. Chọn một bản ghi ở entity thứ nhất.
2. Nhìn ký hiệu nằm sát entity thứ hai.
3. Đọc số bản ghi của entity thứ hai có thể liên quan.
4. Làm lại theo chiều ngược lại.

## 5. Các loại quan hệ phổ biến

### One-to-One — Quan hệ một-một

```text
Person ||----------○| Passport
```

Ý nghĩa:

- Một `Person` có thể chưa có hoặc có một `Passport`.
- Một `Passport` phải thuộc về chính xác một `Person`.

Quan hệ một-một thường được hiện thực bằng một foreign key có ràng buộc `UNIQUE`.

### One-to-Many — Quan hệ một-nhiều

```text
Department ||----------○< Employee
```

Ý nghĩa:

- Một `Department` có thể có không hoặc nhiều `Employee`.
- Mỗi `Employee` thuộc về chính xác một `Department`.

Đây là quan hệ phổ biến nhất. Foreign key thường nằm ở phía “nhiều”:

```text
Employee.department_id -> Department.department_id
```

### Many-to-Many — Quan hệ nhiều-nhiều

```text
Student >○----------○< Course
```

Ý nghĩa:

- Một `Student` có thể học nhiều `Course`.
- Một `Course` có thể có nhiều `Student`.

Cơ sở dữ liệu quan hệ không hiện thực trực tiếp quan hệ nhiều-nhiều. Cần thêm một entity trung gian:

```text
Student ||----------○< Enrollment >○----------|| Course
```

```text
Enrollment
----------
PK, FK student_id
PK, FK course_id
       enrolled_at
       grade
```

Sau khi thêm `Enrollment`:

- `Student` và `Enrollment` có quan hệ một-nhiều.
- `Course` và `Enrollment` có quan hệ một-nhiều.
- Khóa chính của `Enrollment` có thể là cặp `(student_id, course_id)`.

## 6. Quan hệ bắt buộc và không bắt buộc

### Quan hệ bắt buộc

Ký hiệu `|` ở vị trí minimum có nghĩa là bản ghi bắt buộc phải tham gia quan hệ.

```text
Order ----------|| Customer
```

Mỗi `Order` bắt buộc phải có một `Customer`. Trong database, `Order.customer_id` thường có ràng buộc `NOT NULL`.

### Quan hệ không bắt buộc

Ký hiệu `○` có nghĩa là bản ghi có thể không tham gia quan hệ.

```text
Employee ----------○| ParkingSpace
```

Một `Employee` có thể chưa được cấp `ParkingSpace`. Foreign key tương ứng có thể cho phép `NULL`.

## 7. Identifying và non-identifying relationship

### Identifying relationship

Khóa chính của entity cha là một phần trong khóa chính của entity con.

Ví dụ:

```text
Order
PK order_id

OrderItem
PK, FK order_id
PK     line_number
```

`OrderItem` được định danh bằng `(order_id, line_number)`, nên nó phụ thuộc vào `Order` để được định danh.

Một số công cụ biểu diễn identifying relationship bằng đường liền.

### Non-identifying relationship

Entity con có khóa chính độc lập; foreign key tới entity cha không nằm trong khóa chính.

```text
Customer
PK customer_id

Order
PK order_id
FK customer_id
```

Một số công cụ biểu diễn non-identifying relationship bằng đường nét đứt.

> Quy ước đường liền và đường nét đứt phụ thuộc vào công cụ, vì vậy nên kiểm tra legend của sơ đồ.

## 8. Ví dụ hoàn chỉnh

```text
Customer ||----------○< Order
Order    ||----------|< OrderItem >○----------|| Product
```

Cách đọc:

- Một `Customer` có thể có `0..N` `Order`.
- Mỗi `Order` thuộc về đúng `1` `Customer`.
- Một `Order` phải có `1..N` `OrderItem`.
- Mỗi `OrderItem` thuộc về đúng `1` `Order`.
- Một `Product` có thể xuất hiện trong `0..N` `OrderItem`.
- Mỗi `OrderItem` tham chiếu đúng `1` `Product`.

Thiết kế bảng tương ứng:

```text
Customer
--------
PK customer_id
   name

Order
-----
PK order_id
FK customer_id
   ordered_at

Product
-------
PK product_id
   name
   price

OrderItem
---------
PK, FK order_id
PK, FK product_id
       quantity
       unit_price
```

## 9. Lỗi thường gặp

### Chỉ đọc quan hệ theo một chiều

Một đường quan hệ luôn mô tả hai quy tắc. Cần đọc cả `A -> B` và `B -> A`.

### Nhầm optionality với cardinality

- Vòng tròn `○` cho biết số lượng tối thiểu là `0`.
- Gạch `|` hoặc chân quạ `<` cho biết số lượng tối đa là `1` hay `nhiều`.

### Giữ nguyên quan hệ nhiều-nhiều khi tạo database

Quan hệ nhiều-nhiều phải được tách bằng bảng trung gian. Bảng này cũng là nơi phù hợp để lưu thuộc tính của chính mối quan hệ, ví dụ `quantity`, `grade` hoặc `enrolled_at`.

### Đặt foreign key ở sai phía

Trong quan hệ một-nhiều, foreign key thường nằm trong bảng ở phía “nhiều”.

### Không thể hiện quy tắc nghiệp vụ

`0..N` và `1..N` khác nhau về nghiệp vụ. Ví dụ:

- `Order ○< OrderItem`: đơn hàng có thể chưa có sản phẩm.
- `Order |< OrderItem`: đơn hàng phải có ít nhất một sản phẩm.

## 10. Tóm tắt nhanh

```text
○| = zero or one     = 0..1
|| = exactly one     = 1..1
○< = zero or many    = 0..N
|< = one or many     = 1..N
```

Quy trình đọc:

```text
Entity A -> nhìn ký hiệu sát Entity B -> đọc số lượng B
Entity B -> nhìn ký hiệu sát Entity A -> đọc số lượng A
```

Điểm cần nhớ nhất:

- `○` nghĩa là có thể không có.
- `|` nghĩa là một.
- Chân quạ `<` nghĩa là nhiều.
- Quan hệ nhiều-nhiều cần entity trung gian.
- Cardinality phải phản ánh đúng quy tắc nghiệp vụ, không chỉ cấu trúc kỹ thuật.
