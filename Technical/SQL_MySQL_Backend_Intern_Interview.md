# SQL/MySQL cho phỏng vấn Intern Backend

Tài liệu này tổng hợp kiến thức cơ sở dữ liệu quan hệ, SQL và MySQL thường gặp khi phỏng vấn intern backend. Mục tiêu là hiểu bản chất để giải thích được, viết được query cơ bản, đọc được schema và tránh các lỗi phổ biến khi làm backend.

## 1. Cơ sở dữ liệu quan hệ là gì?

Cơ sở dữ liệu quan hệ, hay relational database, lưu dữ liệu dưới dạng các bảng có quan hệ với nhau.

Ví dụ hệ thống bán hàng:

- `users`: lưu người dùng.
- `products`: lưu sản phẩm.
- `orders`: lưu đơn hàng.
- `order_items`: lưu từng sản phẩm trong đơn hàng.

Mỗi bảng có:

- **Column**: cột, mô tả thuộc tính dữ liệu.
- **Row/record**: dòng, một bản ghi cụ thể.
- **Primary key**: khóa chính, định danh duy nhất một dòng.
- **Foreign key**: khóa ngoại, liên kết sang bảng khác.

Ví dụ:

```sql
CREATE TABLE users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    email VARCHAR(255) NOT NULL UNIQUE,
    full_name VARCHAR(255) NOT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);
```

## 2. SQL là gì?

SQL, viết tắt của Structured Query Language, là ngôn ngữ dùng để làm việc với cơ sở dữ liệu quan hệ.

SQL thường được chia thành các nhóm:

| Nhóm | Ý nghĩa | Ví dụ |
|---|---|---|
| DDL | Định nghĩa cấu trúc database | `CREATE`, `ALTER`, `DROP` |
| DML | Thao tác dữ liệu | `SELECT`, `INSERT`, `UPDATE`, `DELETE` |
| DCL | Phân quyền | `GRANT`, `REVOKE` |
| TCL | Giao dịch | `COMMIT`, `ROLLBACK` |

Intern backend thường cần chắc nhất:

- `SELECT`
- `INSERT`
- `UPDATE`
- `DELETE`
- `JOIN`
- `GROUP BY`
- `ORDER BY`
- `LIMIT`
- `INDEX`
- `TRANSACTION`

## 3. MySQL là gì?

MySQL là một hệ quản trị cơ sở dữ liệu quan hệ, hay RDBMS.

Nói ngắn:

- SQL là ngôn ngữ.
- MySQL là hệ quản trị database dùng SQL.

Các hệ quản trị khác:

- PostgreSQL
- SQL Server
- Oracle
- SQLite

Trong phỏng vấn, nếu hỏi MySQL, thường họ muốn biết:

- Cách thiết kế bảng.
- Cách viết query.
- Khóa chính/khóa ngoại.
- Join.
- Index.
- Transaction.
- Isolation level cơ bản.
- Tối ưu query đơn giản.

## 4. Table, Row, Column

Ví dụ bảng `users`:

| id | email | full_name | created_at |
|---|---|---|---|
| 1 | a@example.com | Alice | 2026-06-01 10:00:00 |
| 2 | b@example.com | Bob | 2026-06-02 09:00:00 |

Trong đó:

- `users` là table.
- `id`, `email`, `full_name`, `created_at` là column.
- Mỗi dòng là một row/record.

## 5. Data type thường dùng trong MySQL

## 5.1 Numeric

| Type | Dùng khi |
|---|---|
| `TINYINT` | Số nhỏ, boolean dạng 0/1 |
| `INT` | Số nguyên thông thường |
| `BIGINT` | ID lớn, số lượng lớn |
| `DECIMAL(p, s)` | Tiền tệ, số cần chính xác |
| `FLOAT`, `DOUBLE` | Số thực, không tuyệt đối chính xác |

Với tiền, nên dùng `DECIMAL` hoặc lưu bằng đơn vị nhỏ nhất như cents/VND integer.

Không nên dùng `FLOAT` cho tiền vì có sai số nhị phân.

## 5.2 String

| Type | Dùng khi |
|---|---|
| `CHAR(n)` | Chuỗi độ dài cố định |
| `VARCHAR(n)` | Chuỗi độ dài thay đổi |
| `TEXT` | Nội dung dài |

Ví dụ:

```sql
email VARCHAR(255) NOT NULL
```

## 5.3 Date and time

| Type | Dùng khi |
|---|---|
| `DATE` | Chỉ ngày |
| `TIME` | Chỉ giờ |
| `DATETIME` | Ngày giờ, không gắn timezone |
| `TIMESTAMP` | Ngày giờ, có xử lý timezone theo MySQL/session |

Trong backend, thường lưu:

- `created_at`
- `updated_at`
- `deleted_at` nếu soft delete.

Ví dụ:

```sql
created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
```

## 5.4 Boolean trong MySQL

MySQL không có boolean thật sự như một số ngôn ngữ lập trình. `BOOLEAN` thường là alias của `TINYINT(1)`.

Ví dụ:

```sql
is_active TINYINT(1) NOT NULL DEFAULT 1
```

## 6. Constraint

Constraint là ràng buộc dữ liệu để bảo vệ tính đúng đắn.

## 6.1 NOT NULL

Không cho phép giá trị `NULL`.

```sql
email VARCHAR(255) NOT NULL
```

## 6.2 UNIQUE

Đảm bảo giá trị không trùng.

```sql
email VARCHAR(255) NOT NULL UNIQUE
```

Dùng cho:

- Email user.
- Username.
- SKU sản phẩm.

## 6.3 PRIMARY KEY

Định danh duy nhất một dòng.

```sql
id BIGINT PRIMARY KEY AUTO_INCREMENT
```

Tính chất:

- Không được `NULL`.
- Không được trùng.
- Mỗi bảng thường có một primary key.

## 6.4 FOREIGN KEY

Khóa ngoại liên kết một bảng với bảng khác.

```sql
CREATE TABLE orders (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    total_amount DECIMAL(12, 2) NOT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_orders_user
        FOREIGN KEY (user_id) REFERENCES users(id)
);
```

Ý nghĩa:

- Không cho tạo order với `user_id` không tồn tại.
- Giúp đảm bảo referential integrity.

## 6.5 CHECK

Ràng buộc điều kiện dữ liệu.

```sql
price DECIMAL(12, 2) NOT NULL CHECK (price >= 0)
```

Lưu ý: MySQL các phiên bản cũ từng parse `CHECK` nhưng không enforce. Với MySQL 8.0.16 trở lên, `CHECK` được enforce.

## 7. Primary key, Foreign key, Unique key

| Loại key | Ý nghĩa |
|---|---|
| Primary key | Định danh duy nhất một row |
| Foreign key | Tham chiếu đến row ở bảng khác |
| Unique key | Đảm bảo giá trị không trùng |

Ví dụ:

```sql
CREATE TABLE users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    email VARCHAR(255) NOT NULL UNIQUE
);
```

Ở đây:

- `id` là primary key.
- `email` là unique key.

```sql
CREATE TABLE orders (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id)
);
```

Ở đây:

- `orders.user_id` là foreign key trỏ đến `users.id`.

## 8. CRUD cơ bản

## 8.1 INSERT

```sql
INSERT INTO users (email, full_name)
VALUES ('alice@example.com', 'Alice');
```

Insert nhiều dòng:

```sql
INSERT INTO users (email, full_name)
VALUES
    ('alice@example.com', 'Alice'),
    ('bob@example.com', 'Bob');
```

## 8.2 SELECT

```sql
SELECT id, email, full_name
FROM users;
```

Không nên dùng `SELECT *` trong code production nếu không cần tất cả cột.

Lý do:

- Tốn bandwidth.
- Dễ phụ thuộc vào schema.
- Có thể lấy nhầm field nhạy cảm.

## 8.3 UPDATE

```sql
UPDATE users
SET full_name = 'Alice Nguyen'
WHERE id = 1;
```

Luôn cẩn thận với `WHERE`.

Sai nguy hiểm:

```sql
UPDATE users
SET full_name = 'Unknown';
```

Query trên update toàn bộ bảng.

## 8.4 DELETE

```sql
DELETE FROM users
WHERE id = 1;
```

Luôn kiểm tra `WHERE`.

Trong backend, nhiều hệ thống dùng soft delete:

```sql
UPDATE users
SET deleted_at = CURRENT_TIMESTAMP
WHERE id = 1;
```

## 9. WHERE

`WHERE` dùng để lọc dòng.

```sql
SELECT id, email
FROM users
WHERE email = 'alice@example.com';
```

Các toán tử thường dùng:

```sql
SELECT * FROM products WHERE price > 100000;
SELECT * FROM products WHERE price BETWEEN 100000 AND 500000;
SELECT * FROM users WHERE email LIKE '%@gmail.com';
SELECT * FROM orders WHERE status IN ('PAID', 'SHIPPED');
SELECT * FROM users WHERE deleted_at IS NULL;
```

Lưu ý:

- So sánh với `NULL` phải dùng `IS NULL` hoặc `IS NOT NULL`.
- Không dùng `= NULL`.

Sai:

```sql
SELECT * FROM users WHERE deleted_at = NULL;
```

Đúng:

```sql
SELECT * FROM users WHERE deleted_at IS NULL;
```

## 10. ORDER BY, LIMIT, OFFSET

Sắp xếp:

```sql
SELECT id, email, created_at
FROM users
ORDER BY created_at DESC;
```

Giới hạn số dòng:

```sql
SELECT id, email
FROM users
ORDER BY created_at DESC
LIMIT 10;
```

Phân trang kiểu offset:

```sql
SELECT id, email
FROM users
ORDER BY created_at DESC
LIMIT 20 OFFSET 40;
```

Nghĩa là lấy 20 dòng, bỏ qua 40 dòng đầu.

Nhược điểm của offset pagination:

- Page càng sâu càng chậm.
- Dữ liệu thay đổi trong lúc phân trang có thể gây trùng hoặc thiếu bản ghi.

Keyset pagination:

```sql
SELECT id, email, created_at
FROM users
WHERE created_at < '2026-06-01 10:00:00'
ORDER BY created_at DESC
LIMIT 20;
```

Dùng tốt cho infinite scroll hoặc danh sách lớn.

## 11. JOIN

JOIN dùng để lấy dữ liệu từ nhiều bảng có quan hệ.

Ví dụ schema:

```sql
CREATE TABLE users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    email VARCHAR(255) NOT NULL
);

CREATE TABLE orders (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    total_amount DECIMAL(12, 2) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id)
);
```

## 11.1 INNER JOIN

Chỉ lấy dòng có match ở cả hai bảng.

```sql
SELECT o.id, o.total_amount, u.email
FROM orders o
INNER JOIN users u ON o.user_id = u.id;
```

Nếu order có `user_id` không match user, dòng đó không xuất hiện.

## 11.2 LEFT JOIN

Lấy toàn bộ dòng từ bảng bên trái, dù bảng bên phải không match.

```sql
SELECT u.id, u.email, o.id AS order_id
FROM users u
LEFT JOIN orders o ON o.user_id = u.id;
```

User chưa có order vẫn xuất hiện, `order_id` là `NULL`.

## 11.3 RIGHT JOIN

Lấy toàn bộ dòng từ bảng bên phải, dù bảng bên trái không match.

```sql
SELECT u.email, o.id AS order_id
FROM users u
RIGHT JOIN orders o ON o.user_id = u.id;
```

Trong thực tế, `RIGHT JOIN` ít dùng hơn vì có thể đổi thứ tự bảng và dùng `LEFT JOIN`.

## 11.4 CROSS JOIN

Tạo tích Descartes giữa hai bảng.

```sql
SELECT *
FROM colors
CROSS JOIN sizes;
```

Nếu `colors` có 3 dòng và `sizes` có 4 dòng, kết quả có 12 dòng.

## 11.5 Self join

Một bảng join với chính nó.

Ví dụ nhân viên có manager:

```sql
CREATE TABLE employees (
    id BIGINT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    manager_id BIGINT NULL
);
```

Query:

```sql
SELECT e.name AS employee_name, m.name AS manager_name
FROM employees e
LEFT JOIN employees m ON e.manager_id = m.id;
```

## 12. GROUP BY và aggregate function

Aggregate functions thường dùng:

- `COUNT()`
- `SUM()`
- `AVG()`
- `MIN()`
- `MAX()`

Ví dụ đếm số order của từng user:

```sql
SELECT user_id, COUNT(*) AS order_count
FROM orders
GROUP BY user_id;
```

Tính tổng tiền order theo user:

```sql
SELECT user_id, SUM(total_amount) AS total_spent
FROM orders
GROUP BY user_id;
```

## 13. HAVING vs WHERE

`WHERE` lọc trước khi group.

`HAVING` lọc sau khi group.

Ví dụ lấy user có từ 3 order trở lên:

```sql
SELECT user_id, COUNT(*) AS order_count
FROM orders
GROUP BY user_id
HAVING COUNT(*) >= 3;
```

Ví dụ chỉ tính order đã thanh toán:

```sql
SELECT user_id, COUNT(*) AS paid_order_count
FROM orders
WHERE status = 'PAID'
GROUP BY user_id
HAVING COUNT(*) >= 3;
```

Thứ tự logic:

1. `FROM`
2. `JOIN`
3. `WHERE`
4. `GROUP BY`
5. `HAVING`
6. `SELECT`
7. `ORDER BY`
8. `LIMIT`

## 14. Subquery

Subquery là query nằm trong query khác.

Ví dụ lấy user từng có order:

```sql
SELECT id, email
FROM users
WHERE id IN (
    SELECT user_id
    FROM orders
);
```

Ví dụ lấy sản phẩm đắt hơn giá trung bình:

```sql
SELECT id, name, price
FROM products
WHERE price > (
    SELECT AVG(price)
    FROM products
);
```

Lưu ý:

- Subquery dễ đọc trong một số trường hợp.
- Nhưng với dữ liệu lớn, cần xem execution plan vì có thể chậm.
- Nhiều subquery có thể viết lại bằng join.

## 15. EXISTS

`EXISTS` kiểm tra subquery có trả về dòng nào không.

```sql
SELECT u.id, u.email
FROM users u
WHERE EXISTS (
    SELECT 1
    FROM orders o
    WHERE o.user_id = u.id
);
```

So với `IN`, `EXISTS` thường phù hợp khi subquery phụ thuộc vào row bên ngoài hoặc chỉ cần kiểm tra tồn tại.

## 16. UNION và UNION ALL

`UNION` gộp kết quả từ nhiều query và loại bỏ dòng trùng.

```sql
SELECT email FROM customers
UNION
SELECT email FROM suppliers;
```

`UNION ALL` gộp kết quả và giữ cả dòng trùng.

```sql
SELECT email FROM customers
UNION ALL
SELECT email FROM suppliers;
```

`UNION ALL` thường nhanh hơn vì không cần loại trùng.

## 17. Index

Index giống như mục lục giúp database tìm dữ liệu nhanh hơn.

Ví dụ:

```sql
CREATE INDEX idx_users_email ON users(email);
```

Query:

```sql
SELECT id, email
FROM users
WHERE email = 'alice@example.com';
```

Nếu có index trên `email`, database có thể tìm nhanh hơn thay vì scan toàn bảng.

## 17.1 Khi nào nên tạo index?

Thường tạo index cho:

- Cột hay dùng trong `WHERE`.
- Cột hay dùng trong `JOIN`.
- Cột hay dùng trong `ORDER BY`.
- Foreign key.
- Unique field như `email`, `username`.

Ví dụ:

```sql
CREATE INDEX idx_orders_user_id ON orders(user_id);
CREATE INDEX idx_orders_status_created_at ON orders(status, created_at);
```

## 17.2 Nhược điểm của index

Index không miễn phí.

Nhược điểm:

- Tốn dung lượng lưu trữ.
- Làm `INSERT`, `UPDATE`, `DELETE` chậm hơn vì phải cập nhật index.
- Tạo quá nhiều index có thể làm optimizer khó chọn và lãng phí tài nguyên.

Câu trả lời phỏng vấn:

> Index giúp tăng tốc query đọc nhưng đánh đổi bằng dung lượng và chi phí ghi. Em thường tạo index dựa trên query thực tế, nhất là các cột dùng trong WHERE, JOIN, ORDER BY.

## 17.3 Composite index

Composite index là index trên nhiều cột.

```sql
CREATE INDEX idx_orders_user_status_created
ON orders(user_id, status, created_at);
```

MySQL dùng composite index theo nguyên tắc leftmost prefix.

Index `(user_id, status, created_at)` có thể hỗ trợ tốt:

```sql
WHERE user_id = 1
```

```sql
WHERE user_id = 1 AND status = 'PAID'
```

```sql
WHERE user_id = 1 AND status = 'PAID' ORDER BY created_at DESC
```

Nhưng thường không dùng tốt nếu chỉ query:

```sql
WHERE status = 'PAID'
```

vì `status` không phải cột đầu tiên của index.

## 17.4 Covering index

Covering index xảy ra khi query chỉ cần dữ liệu đã có trong index, không cần đọc thêm row từ table.

Ví dụ index:

```sql
CREATE INDEX idx_users_email_id ON users(email, id);
```

Query:

```sql
SELECT id
FROM users
WHERE email = 'alice@example.com';
```

Database có thể lấy `id` trực tiếp từ index.

## 17.5 Index và LIKE

Query có thể dùng index tốt:

```sql
SELECT *
FROM users
WHERE email LIKE 'alice%';
```

Query khó dùng index B-tree hiệu quả:

```sql
SELECT *
FROM users
WHERE email LIKE '%gmail.com';
```

Vì wildcard ở đầu làm database khó tìm theo thứ tự index.

## 18. EXPLAIN

`EXPLAIN` dùng để xem database định thực thi query như thế nào.

```sql
EXPLAIN
SELECT *
FROM orders
WHERE user_id = 1
ORDER BY created_at DESC
LIMIT 10;
```

Các thông tin cần chú ý:

- Query có dùng index không?
- Số row ước tính phải scan là bao nhiêu?
- Có `Using filesort` không?
- Có `Using temporary` không?
- Type là `ALL`, `ref`, `range`, `const`?

Hiểu đơn giản:

- `ALL`: scan toàn bảng, có thể chậm với bảng lớn.
- `const`: tìm được rất ít dòng, thường rất tốt.
- `ref`, `range`: thường ổn nếu dùng index hợp lý.

## 19. Transaction

Transaction là một nhóm thao tác database được thực hiện như một đơn vị logic.

Ví dụ chuyển tiền:

1. Trừ tiền tài khoản A.
2. Cộng tiền tài khoản B.

Hai thao tác này phải cùng thành công hoặc cùng rollback.

```sql
START TRANSACTION;

UPDATE accounts
SET balance = balance - 100
WHERE id = 1;

UPDATE accounts
SET balance = balance + 100
WHERE id = 2;

COMMIT;
```

Nếu có lỗi:

```sql
ROLLBACK;
```

Trong backend:

- Service method xử lý use case thường là transaction boundary.
- Với Spring, thường dùng `@Transactional`.

```java
@Transactional
public void transfer(Long fromId, Long toId, BigDecimal amount) {
    // debit
    // credit
}
```

## 20. ACID

ACID là 4 tính chất của transaction.

## 20.1 Atomicity

Tất cả thao tác trong transaction hoặc cùng thành công, hoặc cùng thất bại.

Ví dụ chuyển tiền: không được trừ tiền A thành công nhưng cộng tiền B thất bại.

## 20.2 Consistency

Transaction đưa database từ trạng thái hợp lệ này sang trạng thái hợp lệ khác.

Ví dụ:

- Balance không âm.
- Foreign key vẫn hợp lệ.
- Unique email không bị trùng.

## 20.3 Isolation

Các transaction đồng thời không được ảnh hưởng lẫn nhau theo cách làm sai dữ liệu.

Ví dụ hai request cùng mua một sản phẩm cuối cùng trong kho phải được xử lý đúng.

## 20.4 Durability

Sau khi commit, dữ liệu phải được lưu bền vững, kể cả khi hệ thống gặp sự cố.

## 21. Isolation level

Isolation level quy định mức độ cô lập giữa các transaction.

Các vấn đề concurrency:

| Vấn đề | Ý nghĩa |
|---|---|
| Dirty read | Đọc dữ liệu chưa commit của transaction khác |
| Non-repeatable read | Đọc cùng một row hai lần trong transaction nhưng kết quả khác |
| Phantom read | Query cùng điều kiện hai lần nhưng số row khác do transaction khác insert/delete |
| Lost update | Hai transaction cùng update, một update ghi đè update kia |

Các isolation level phổ biến:

| Level | Ý nghĩa đơn giản |
|---|---|
| Read Uncommitted | Có thể đọc dữ liệu chưa commit |
| Read Committed | Chỉ đọc dữ liệu đã commit |
| Repeatable Read | Trong một transaction, đọc lại cùng row cho kết quả ổn định hơn |
| Serializable | Cô lập mạnh nhất, giống chạy tuần tự hơn |

MySQL InnoDB mặc định thường là `REPEATABLE READ`.

Câu trả lời phỏng vấn:

> Isolation level càng cao thì càng giảm lỗi concurrency nhưng có thể giảm hiệu năng do lock nhiều hơn. MySQL InnoDB mặc định là Repeatable Read.

## 22. Lock cơ bản

Lock dùng để kiểm soát truy cập đồng thời.

## 22.1 Shared lock

Shared lock cho phép nhiều transaction đọc cùng lúc, nhưng thường chặn ghi.

## 22.2 Exclusive lock

Exclusive lock dùng khi ghi, thường chặn transaction khác đọc/ghi theo mức isolation và loại lock.

## 22.3 SELECT FOR UPDATE

`SELECT ... FOR UPDATE` khóa các row được chọn để chuẩn bị update.

Ví dụ xử lý trừ tồn kho:

```sql
START TRANSACTION;

SELECT stock
FROM products
WHERE id = 1
FOR UPDATE;

UPDATE products
SET stock = stock - 1
WHERE id = 1 AND stock > 0;

COMMIT;
```

Ý nghĩa:

- Tránh nhiều transaction cùng thấy stock còn 1 rồi cùng mua thành công.
- Cần chạy trong transaction.

## 23. Deadlock

Deadlock xảy ra khi các transaction chờ lock của nhau vòng tròn.

Ví dụ:

- Transaction A khóa row 1, chờ row 2.
- Transaction B khóa row 2, chờ row 1.

Cách giảm deadlock:

- Truy cập bảng/row theo thứ tự nhất quán.
- Giữ transaction ngắn.
- Có index tốt để lock ít row hơn.
- Retry transaction khi gặp deadlock.

Trong backend, gặp deadlock không phải lúc nào cũng là bug logic nghiêm trọng, nhưng hệ thống nên có cơ chế retry phù hợp cho thao tác có thể retry.

## 24. Normalization

Normalization là quá trình thiết kế bảng để giảm trùng lặp dữ liệu và tránh bất nhất.

## 24.1 First Normal Form - 1NF

Mỗi ô chứa giá trị nguyên tử, không chứa list lặp.

Không tốt:

| user_id | phones |
|---|---|
| 1 | 0901,0902 |

Tốt hơn:

| user_id | phone |
|---|---|
| 1 | 0901 |
| 1 | 0902 |

## 24.2 Second Normal Form - 2NF

Đạt 1NF và các cột không khóa phụ thuộc vào toàn bộ khóa chính, đặc biệt quan trọng với composite primary key.

## 24.3 Third Normal Form - 3NF

Đạt 2NF và các cột không khóa không phụ thuộc bắc cầu vào nhau.

Ví dụ không tốt:

| user_id | city_id | city_name |
|---|---|---|
| 1 | 10 | Hanoi |

Nếu `city_name` phụ thuộc vào `city_id`, nên tách bảng `cities`.

## 24.4 Có nên luôn normalize tối đa?

Không nhất thiết.

Trong hệ thống đọc nhiều, đôi khi denormalization có chủ đích để tăng tốc query.

Ví dụ:

- Lưu `order_total` trong bảng `orders` thay vì tính lại từ `order_items` mỗi lần.
- Lưu `comment_count` trong bảng `posts`.

Câu trả lời phỏng vấn:

> Normalize giúp giảm trùng lặp và bất nhất. Nhưng trong thực tế có thể denormalize có kiểm soát để tối ưu hiệu năng đọc.

## 25. Quan hệ one-to-one, one-to-many, many-to-many

## 25.1 One-to-one

Một user có một profile.

```sql
CREATE TABLE users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    email VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE user_profiles (
    user_id BIGINT PRIMARY KEY,
    full_name VARCHAR(255),
    avatar_url VARCHAR(500),
    FOREIGN KEY (user_id) REFERENCES users(id)
);
```

## 25.2 One-to-many

Một user có nhiều order.

```sql
CREATE TABLE orders (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id)
);
```

## 25.3 Many-to-many

Một student học nhiều course, một course có nhiều student.

Dùng bảng trung gian:

```sql
CREATE TABLE students (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE courses (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE student_courses (
    student_id BIGINT NOT NULL,
    course_id BIGINT NOT NULL,
    enrolled_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (student_id, course_id),
    FOREIGN KEY (student_id) REFERENCES students(id),
    FOREIGN KEY (course_id) REFERENCES courses(id)
);
```

## 26. Cascade

Foreign key có thể cấu hình hành vi khi row cha bị update/delete.

Ví dụ:

```sql
FOREIGN KEY (user_id) REFERENCES users(id)
ON DELETE CASCADE
```

Các loại thường gặp:

| Action | Ý nghĩa |
|---|---|
| `CASCADE` | Xóa/update cha thì xóa/update con |
| `SET NULL` | Xóa cha thì set khóa ngoại ở con thành `NULL` |
| `RESTRICT`/`NO ACTION` | Không cho xóa/update nếu còn row con |

Cẩn thận:

- `ON DELETE CASCADE` tiện nhưng nguy hiểm nếu xóa nhầm dữ liệu cha.
- Với dữ liệu quan trọng, backend thường dùng soft delete hoặc kiểm soát xóa ở service.

## 27. Soft delete

Soft delete là không xóa vật lý row, chỉ đánh dấu đã xóa.

```sql
ALTER TABLE users ADD COLUMN deleted_at DATETIME NULL;
```

Xóa:

```sql
UPDATE users
SET deleted_at = CURRENT_TIMESTAMP
WHERE id = 1;
```

Query dữ liệu còn hiệu lực:

```sql
SELECT *
FROM users
WHERE deleted_at IS NULL;
```

Ưu điểm:

- Có thể khôi phục.
- Giữ lịch sử.
- Tránh mất dữ liệu do xóa nhầm.

Nhược điểm:

- Query nào cũng phải nhớ lọc `deleted_at IS NULL`.
- Unique constraint phức tạp hơn nếu muốn cho đăng ký lại email đã xóa mềm.
- Bảng lớn dần nếu không archive/purge.

## 28. Migration

Migration là cách quản lý thay đổi schema database theo version.

Ví dụ:

- Tạo bảng mới.
- Thêm cột.
- Tạo index.
- Đổi type dữ liệu.

Tool thường gặp:

- Flyway
- Liquibase
- Prisma migration
- Sequelize migration
- TypeORM migration

Ví dụ migration:

```sql
ALTER TABLE users
ADD COLUMN phone VARCHAR(20) NULL;
```

Nguyên tắc:

- Không sửa tay database production mà không có migration.
- Migration nên có version và chạy được tự động.
- Cẩn thận migration nặng trên bảng lớn.
- Không xóa/đổi cột ngay nếu app cũ vẫn dùng.

## 29. Query optimization cơ bản

Khi query chậm, kiểm tra:

1. Query có lọc đúng điều kiện không?
2. Có index phù hợp không?
3. Có dùng function lên cột indexed không?
4. Có `SELECT *` không?
5. Có join bảng quá lớn không?
6. Có phân trang offset quá sâu không?
7. `EXPLAIN` cho thấy scan bao nhiêu row?

Ví dụ làm mất hiệu quả index:

```sql
SELECT *
FROM users
WHERE LOWER(email) = 'alice@example.com';
```

Nếu index trên `email`, việc dùng `LOWER(email)` có thể làm index khó được dùng hiệu quả.

Tốt hơn:

- Chuẩn hóa email thành lowercase trước khi lưu.
- Query trực tiếp:

```sql
SELECT *
FROM users
WHERE email = 'alice@example.com';
```

## 30. N+1 query problem

N+1 query xảy ra khi lấy danh sách N item rồi với mỗi item lại query thêm một lần.

Ví dụ:

1 query lấy orders:

```sql
SELECT * FROM orders LIMIT 100;
```

Sau đó 100 query lấy user cho từng order:

```sql
SELECT * FROM users WHERE id = ?;
```

Tổng cộng 101 query.

Cách xử lý:

- Dùng join.
- Batch query bằng `WHERE id IN (...)`.
- Dùng eager fetch hợp lý trong ORM.
- Dùng data loader/batching.

Ví dụ batch:

```sql
SELECT *
FROM users
WHERE id IN (1, 2, 3, 4);
```

## 31. SQL injection

SQL injection là lỗi bảo mật xảy ra khi user input được nối trực tiếp vào câu SQL.

Không tốt:

```java
String sql = "SELECT * FROM users WHERE email = '" + email + "'";
```

Nếu `email` là:

```text
' OR '1'='1
```

Query có thể bị biến thành logic sai và lộ dữ liệu.

Tốt hơn: dùng prepared statement/parameterized query.

```java
String sql = "SELECT * FROM users WHERE email = ?";
PreparedStatement stmt = connection.prepareStatement(sql);
stmt.setString(1, email);
```

Trong ORM/query builder, vẫn phải hiểu rằng parameter binding mới là điểm quan trọng.

Câu trả lời phỏng vấn:

> Không nối string trực tiếp từ input vào SQL. Dùng prepared statement hoặc parameterized query để database hiểu input là dữ liệu, không phải một phần của câu lệnh.

## 32. ORM và SQL

ORM, viết tắt của Object-Relational Mapping, giúp map object trong code với table trong database.

Ví dụ:

- Java: JPA/Hibernate, MyBatis.
- Node.js: Prisma, TypeORM, Sequelize.
- PHP: Eloquent.
- Python: SQLAlchemy, Django ORM.

Ưu điểm:

- Giảm boilerplate CRUD.
- Map entity dễ hơn.
- Hỗ trợ transaction, relation, migration tùy framework.

Nhược điểm:

- Dễ sinh query không tối ưu nếu không hiểu SQL.
- Có thể gặp N+1 query.
- Mapping relation phức tạp.
- Debug khó hơn nếu không xem SQL thực tế.

Câu trả lời phỏng vấn:

> ORM giúp làm việc với database thuận tiện hơn, nhưng vẫn cần hiểu SQL, index và transaction để tránh query chậm hoặc lỗi concurrency.

## 33. View

View là query được lưu lại như một bảng ảo.

```sql
CREATE VIEW paid_orders AS
SELECT id, user_id, total_amount, created_at
FROM orders
WHERE status = 'PAID';
```

Dùng:

```sql
SELECT *
FROM paid_orders;
```

Ưu điểm:

- Tái sử dụng query.
- Ẩn bớt độ phức tạp.
- Có thể giới hạn dữ liệu expose.

Lưu ý:

- View không nhất thiết làm query nhanh hơn.
- Performance vẫn phụ thuộc query bên trong và index.

## 34. Stored procedure và trigger

## 34.1 Stored procedure

Stored procedure là logic SQL được lưu trong database.

Ưu điểm:

- Chạy gần dữ liệu.
- Có thể gom logic thao tác dữ liệu.

Nhược điểm:

- Khó version/test/debug hơn code application.
- Logic nghiệp vụ bị phân tán giữa app và database.

## 34.2 Trigger

Trigger là logic tự chạy khi có event như insert/update/delete.

Ví dụ:

- Ghi audit log khi update user.
- Tự cập nhật timestamp.

Cẩn thận:

- Trigger chạy ngầm, khó debug.
- Có thể gây side effect bất ngờ.
- Không nên lạm dụng cho business logic phức tạp.

## 35. MySQL storage engine

Storage engine là thành phần quản lý cách MySQL lưu và truy cập dữ liệu.

Engine quan trọng nhất: **InnoDB**.

InnoDB hỗ trợ:

- Transaction.
- Row-level locking.
- Foreign key.
- Crash recovery.

MyISAM cũ hơn:

- Không hỗ trợ transaction tốt như InnoDB.
- Lock cấp table.
- Ít dùng cho backend hiện đại.

Câu trả lời phỏng vấn:

> Với MySQL backend hiện đại, thường dùng InnoDB vì hỗ trợ transaction, foreign key và row-level locking.

## 36. Replication cơ bản

Replication là sao chép dữ liệu từ database chính sang database phụ.

Mô hình thường gặp:

- Primary/master: nhận write.
- Replica/slave: phục vụ read.

Lợi ích:

- Tăng khả năng đọc.
- Backup/reporting.
- High availability ở mức nhất định.

Vấn đề:

- Replication lag: replica có thể chậm hơn primary.
- Sau khi write, đọc ngay từ replica có thể chưa thấy dữ liệu mới.

Với intern, chỉ cần hiểu khái niệm, chưa cần đi sâu vận hành.

## 37. Backup và restore

Backup là sao lưu dữ liệu để khôi phục khi có sự cố.

Loại backup:

- Full backup.
- Incremental backup.
- Logical backup, ví dụ `mysqldump`.
- Physical backup.

Điểm quan trọng:

- Backup mà chưa test restore thì chưa đủ an toàn.
- Cần xác định RPO và RTO.

RPO: có thể mất tối đa bao nhiêu dữ liệu.

RTO: mất bao lâu để khôi phục hệ thống.

## 38. Thiết kế schema ví dụ: Ecommerce mini

```sql
CREATE TABLE users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    email VARCHAR(255) NOT NULL UNIQUE,
    full_name VARCHAR(255) NOT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE products (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    price DECIMAL(12, 2) NOT NULL,
    stock INT NOT NULL DEFAULT 0,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CHECK (price >= 0),
    CHECK (stock >= 0)
);

CREATE TABLE orders (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    status VARCHAR(30) NOT NULL,
    total_amount DECIMAL(12, 2) NOT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id),
    INDEX idx_orders_user_created (user_id, created_at)
);

CREATE TABLE order_items (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    order_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    quantity INT NOT NULL,
    unit_price DECIMAL(12, 2) NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders(id),
    FOREIGN KEY (product_id) REFERENCES products(id),
    CHECK (quantity > 0),
    CHECK (unit_price >= 0),
    INDEX idx_order_items_order (order_id),
    INDEX idx_order_items_product (product_id)
);
```

Điểm thiết kế:

- `orders` liên kết `users` qua `user_id`.
- `order_items` là bảng con của `orders`.
- Lưu `unit_price` tại thời điểm mua để lịch sử order không đổi nếu giá sản phẩm thay đổi.
- `orders.total_amount` có thể là denormalized field để đọc nhanh.
- Index trên foreign key giúp join nhanh hơn.

## 39. Các lỗi intern hay mắc

- Dùng `SELECT *` mọi nơi.
- Quên `WHERE` khi `UPDATE` hoặc `DELETE`.
- Không phân biệt `WHERE` và `HAVING`.
- Dùng `= NULL` thay vì `IS NULL`.
- Không tạo index cho cột hay filter/join.
- Tạo quá nhiều index mà không biết query thực tế.
- Dùng `FLOAT` cho tiền.
- Trả entity chứa password/hash/token ra API.
- Không dùng transaction cho thao tác nhiều bước.
- Tự nối string SQL từ user input.
- Không hiểu N+1 query khi dùng ORM.
- Không biết foreign key dùng để làm gì.
- Thiết kế many-to-many nhưng thiếu bảng trung gian.
- Dùng soft delete nhưng quên lọc `deleted_at IS NULL`.

## 40. Câu hỏi phỏng vấn thường gặp

## 40.1 Câu hỏi SQL cơ bản

1. SQL là gì?
2. MySQL khác SQL như thế nào?
3. Primary key là gì?
4. Foreign key là gì?
5. Unique key khác primary key thế nào?
6. `WHERE` khác `HAVING` thế nào?
7. `INNER JOIN` khác `LEFT JOIN` thế nào?
8. `COUNT(*)` khác `COUNT(column)` thế nào?
9. `DELETE`, `TRUNCATE`, `DROP` khác nhau thế nào?
10. `NULL` trong SQL là gì?

## 40.2 Câu hỏi backend thực tế

1. Vì sao cần index?
2. Index có nhược điểm gì?
3. Composite index hoạt động thế nào?
4. Transaction là gì?
5. ACID là gì?
6. Isolation level là gì?
7. Làm sao tránh SQL injection?
8. ORM có thay thế việc học SQL không?
9. N+1 query là gì?
10. Khi nào dùng soft delete?
11. Vì sao không nên dùng `SELECT *`?
12. Thiết kế bảng cho quan hệ many-to-many như thế nào?
13. Vì sao nên lưu `unit_price` trong `order_items`?
14. Làm sao debug query chậm?
15. `EXPLAIN` dùng để làm gì?

## 41. Câu trả lời mẫu ngắn

### Primary key là gì?

Primary key là khóa chính dùng để định danh duy nhất một dòng trong bảng. Nó không được `NULL` và không được trùng.

### Foreign key là gì?

Foreign key là cột dùng để tham chiếu đến primary key hoặc unique key của bảng khác. Nó giúp đảm bảo dữ liệu liên kết hợp lệ, ví dụ order phải thuộc về một user tồn tại.

### INNER JOIN khác LEFT JOIN?

`INNER JOIN` chỉ trả về các dòng match ở cả hai bảng. `LEFT JOIN` trả về toàn bộ dòng ở bảng bên trái, nếu bảng bên phải không match thì các cột bên phải là `NULL`.

### WHERE khác HAVING?

`WHERE` lọc dữ liệu trước khi group. `HAVING` lọc kết quả sau khi `GROUP BY`, thường dùng với aggregate function như `COUNT`, `SUM`.

### Index là gì?

Index giống mục lục giúp database tìm dữ liệu nhanh hơn. Nó thường dùng cho cột hay filter, join hoặc sort. Nhưng index tốn dung lượng và làm thao tác ghi chậm hơn.

### Transaction là gì?

Transaction là một nhóm thao tác database được xử lý như một đơn vị. Nếu một bước lỗi thì rollback toàn bộ, tránh dữ liệu bị nửa đúng nửa sai.

### ACID là gì?

ACID gồm Atomicity, Consistency, Isolation và Durability. Đây là các tính chất giúp transaction đáng tin cậy.

### SQL injection là gì?

SQL injection xảy ra khi input của user bị ghép trực tiếp vào câu SQL, làm attacker có thể thay đổi ý nghĩa query. Cách tránh là dùng prepared statement hoặc parameterized query.

### N+1 query là gì?

N+1 query là khi lấy danh sách N record rồi lại query thêm từng record liên quan, tạo ra quá nhiều query. Có thể xử lý bằng join, batch query hoặc cấu hình fetch hợp lý trong ORM.

## 42. Bài tập tự luyện

## Bài 1: Query user và order

Cho bảng:

```sql
users(id, email, full_name)
orders(id, user_id, status, total_amount, created_at)
```

Viết query:

1. Lấy danh sách order của user có `id = 1`.
2. Lấy email user và tổng số order của từng user.
3. Lấy user chưa từng có order.
4. Lấy top 5 user có tổng tiền mua hàng cao nhất.

Gợi ý:

```sql
SELECT *
FROM orders
WHERE user_id = 1;
```

```sql
SELECT u.id, u.email, COUNT(o.id) AS order_count
FROM users u
LEFT JOIN orders o ON o.user_id = u.id
GROUP BY u.id, u.email;
```

```sql
SELECT u.id, u.email
FROM users u
LEFT JOIN orders o ON o.user_id = u.id
WHERE o.id IS NULL;
```

```sql
SELECT u.id, u.email, SUM(o.total_amount) AS total_spent
FROM users u
JOIN orders o ON o.user_id = u.id
GROUP BY u.id, u.email
ORDER BY total_spent DESC
LIMIT 5;
```

## Bài 2: Thiết kế schema blog

Yêu cầu:

- User viết nhiều post.
- Post có nhiều comment.
- Post có nhiều tag.
- Một tag có thể thuộc nhiều post.

Gợi ý bảng:

- `users`
- `posts`
- `comments`
- `tags`
- `post_tags`

Quan hệ:

- `users` one-to-many `posts`.
- `posts` one-to-many `comments`.
- `posts` many-to-many `tags` qua `post_tags`.

## Bài 3: Transaction đặt hàng

Yêu cầu:

- User đặt mua sản phẩm.
- Kiểm tra tồn kho.
- Trừ tồn kho.
- Tạo order.
- Tạo order item.

Gợi ý:

- Các bước nên nằm trong một transaction.
- Dùng điều kiện `stock >= quantity`.
- Cân nhắc lock hoặc update có điều kiện để tránh oversell.

Ví dụ:

```sql
START TRANSACTION;

UPDATE products
SET stock = stock - 2
WHERE id = 10 AND stock >= 2;

-- Kiểm tra số row affected. Nếu bằng 0 thì rollback.

INSERT INTO orders (user_id, status, total_amount)
VALUES (1, 'CREATED', 200000);

INSERT INTO order_items (order_id, product_id, quantity, unit_price)
VALUES (LAST_INSERT_ID(), 10, 2, 100000);

COMMIT;
```

## 43. Checklist trước khi đi phỏng vấn

Bạn nên tự trả lời được:

- SQL là gì, MySQL là gì.
- Table, row, column là gì.
- Primary key, foreign key, unique key khác nhau thế nào.
- Viết được `SELECT`, `INSERT`, `UPDATE`, `DELETE`.
- Dùng được `WHERE`, `ORDER BY`, `LIMIT`.
- Phân biệt `INNER JOIN`, `LEFT JOIN`.
- Dùng được `GROUP BY`, `HAVING`, aggregate function.
- Hiểu `NULL` và dùng `IS NULL`.
- Biết index là gì, khi nào dùng, nhược điểm là gì.
- Hiểu composite index và leftmost prefix ở mức cơ bản.
- Biết dùng `EXPLAIN` để xem query plan.
- Hiểu transaction và ACID.
- Biết isolation level là gì ở mức khái niệm.
- Biết tránh SQL injection bằng parameterized query.
- Biết N+1 query problem.
- Thiết kế được quan hệ one-to-many và many-to-many.
- Biết soft delete, migration, ORM ở mức thực tế backend.

## 44. Tóm tắt cực ngắn

- Relational database lưu dữ liệu theo bảng và quan hệ.
- SQL là ngôn ngữ truy vấn, MySQL là hệ quản trị database.
- Primary key định danh row, foreign key liên kết bảng, unique key chống trùng.
- Join dùng để lấy dữ liệu từ nhiều bảng.
- `WHERE` lọc trước group, `HAVING` lọc sau group.
- Index giúp đọc nhanh hơn nhưng làm ghi chậm hơn và tốn dung lượng.
- Transaction đảm bảo nhiều thao tác cùng thành công hoặc rollback.
- ACID giúp transaction đáng tin cậy.
- Backend cần tránh SQL injection, N+1 query, thiếu transaction và thiết kế schema sai quan hệ.
- Hiểu SQL vẫn rất quan trọng ngay cả khi dùng ORM.
