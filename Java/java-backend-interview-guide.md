# Cẩm nang phỏng vấn Java Backend: hiểu kiến thức và bảo vệ project

> Đối tượng: sinh viên, Intern và Junior Java Backend.
>
> Cách dùng: đọc **Trả lời phỏng vấn**, hiểu ví dụ, sau đó tự trả lời câu hỏi đào sâu mà không nhìn tài liệu.
>
> Các câu trả lời xưng “em” là **mẫu minh họa**, không phải kinh nghiệm thật của người đọc. Chỉ sử dụng những chi tiết đúng với project mình đã làm.

## Mục lục

- [0. Cách trình bày và project minh họa](#s0)
- [1. Ba câu hỏi mở đầu](#s1)
- [2. Java: OOP, Collection, Exception, Interface và Abstract Class](#s2)
- [3. Database: JOIN, Index, Transaction và thiết kế](#s3)
- [4. Spring: IoC, DI, Bean và REST API](#s4)
- [5. JPA: quan hệ entity, Repository, JPQL và phân trang](#s5)
- [6. Security: Authentication, Authorization, JWT, token và role](#s6)
- [7. Test: JUnit, Mockito, Controller và Service](#s7)
- [8. Project: trách nhiệm, thiết kế, traffic, bug và Git](#s8)
- [9. Tình huống phỏng vấn tổng hợp](#s9)
- [10. Checklist tự luyện](#s10)
- [11. Tài liệu chính thức để đối chiếu](#s11)

<a id="s0"></a>
## 0. Cách trình bày và project minh họa

### 0.1. Trả lời theo bốn bước

1. **Định nghĩa:** khái niệm đó là gì, trong một hoặc hai câu.
2. **Cơ chế hoặc khác biệt:** nó hoạt động thế nào, khác khái niệm gần nhất ở đâu.
3. **Ví dụ:** áp dụng ở class, API, bảng hoặc luồng nào trong project.
4. **Đánh đổi:** giới hạn, lỗi thường gặp và cách kiểm chứng.

Ví dụ, thay vì chỉ nói “DI là tiêm phụ thuộc”, hãy nói:

> “DI là cách cung cấp dependency từ bên ngoài cho một đối tượng. Trong Spring, container tạo bean và truyền `OrderRepository` vào constructor của `OrderService`. Nhờ đó service không tự quyết định cách khởi tạo repository, và em có thể truyền mock repository khi unit test. Nếu có nhiều bean cùng kiểu, em phải chỉ rõ bean cần dùng.”

Câu trả lời đầu tiên nên gói trong khoảng 30–60 giây. Chỉ mở rộng khi người phỏng vấn hỏi tiếp hoặc cần một ví dụ để làm rõ.

### 0.2. Project dùng xuyên suốt

Giả sử xây dựng **hệ thống bán hàng** với:

- Người dùng đăng ký, đăng nhập, xem sản phẩm và tạo đơn hàng.
- Người dùng xem được đơn của chính mình; quản trị viên quản lý sản phẩm.
- Một đơn có nhiều dòng hàng; mỗi dòng lưu sản phẩm, số lượng và giá tại thời điểm mua.
- Database có `users`, `products`, `orders`, `order_items`, `roles`, `user_roles` và `refresh_tokens`.

Luồng xử lý một request:

```text
Client
  → Spring Security Filter Chain: xác thực và kiểm tra quyền ở mức request
  → Controller: nhận request, kiểm tra định dạng dữ liệu
  → Service: nghiệp vụ, quyền trên tài nguyên, ranh giới transaction
  → Repository / EntityManager
  → JPA provider, ví dụ Hibernate
  → JDBC driver
  → Database
```

Code tập trung vào ý tưởng, không phải một ứng dụng có thể copy toàn bộ rồi chạy ngay. Các đoạn có thể lược bỏ import, getter và phần triển khai phụ trợ. Ví dụ dùng cú pháp Java 17, namespace `jakarta.persistence`, JUnit Jupiter và cách cấu hình Spring Security bằng `SecurityFilterChain`; hãy đối chiếu dependency thực tế của project khi áp dụng.

<a id="s1"></a>
## 1. Ba câu hỏi mở đầu

### 1.1. “OOP có bốn tính chất gì? Cho ví dụ trong project của em.”

**Trả lời phỏng vấn:**

> “OOP có đóng gói, trừu tượng, kế thừa và đa hình. Trong ví dụ bán hàng, em đóng gói trạng thái đơn hàng và chỉ cho chuyển trạng thái qua các phương thức nghiệp vụ. Em dùng interface `PaymentGateway` để trừu tượng hóa thanh toán. Các loại thông báo có thể kế thừa một lớp cơ sở khi có hành vi chung phù hợp. Đa hình thể hiện khi service gọi cùng phương thức `pay()` nhưng implementation được truyền vào quyết định cách thanh toán.”

| Tính chất | Ý nghĩa | Ví dụ | Lỗi diễn giải thường gặp |
| --- | --- | --- | --- |
| Encapsulation — đóng gói | Giữ dữ liệu và hành vi cùng nhau, kiểm soát cách thay đổi trạng thái | `Order.cancel()` kiểm tra đơn còn được phép hủy | Có `private` và setter cho mọi field là đã đóng gói tốt |
| Abstraction — trừu tượng | Đưa ra hợp đồng cần dùng, che chi tiết không cần biết | `PaymentGateway.pay()` | Trừu tượng chỉ tồn tại khi dùng từ khóa `abstract` |
| Inheritance — kế thừa | Một kiểu con kế thừa thành viên được phép và có quan hệ “is-a” phù hợp | `EmailNotification extends Notification` | Dùng kế thừa chỉ để tránh copy vài dòng code |
| Polymorphism — đa hình | Cùng hợp đồng, hành vi thay đổi theo đối tượng cụ thể | `PaymentGateway` trỏ tới `CardPaymentGateway` hoặc `WalletPaymentGateway` | Nhầm đa hình với chỉ có nhiều method cùng tên |

Code và phân tích chi tiết ở mục 2.1.

### 1.2. “Authentication khác Authorization thế nào?”

**Trả lời phỏng vấn:**

> “Authentication xác minh danh tính: người gọi là ai. Authorization quyết định người đó được phép làm gì với tài nguyên nào. Ví dụ xác minh mật khẩu hoặc access token là xác thực; kiểm tra người dùng có quyền quản trị hay có sở hữu đơn hàng đang yêu cầu là phân quyền. Đăng nhập thành công không đồng nghĩa được xem đơn hàng của mọi người.”

| Tiêu chí | Authentication | Authorization |
| --- | --- | --- |
| Câu hỏi | Bạn là ai? | Bạn được phép làm gì? |
| Dữ liệu liên quan | Mật khẩu, phiên đăng nhập, token, chứng thư | Role, authority, scope, chủ sở hữu, chính sách |
| Ví dụ | Xác minh JWT hợp lệ và tạo principal | Chỉ cho chủ đơn hoặc người có quyền phù hợp đọc đơn |
| Lỗi HTTP phổ biến trong API | `401` khi thiếu hoặc sai thông tin xác thực | `403` khi đã xác thực nhưng không đủ quyền |

`401`/`403` là cách xử lý thường gặp; cấu hình ứng dụng có thể trả `404` để tránh tiết lộ tài nguyên, hoặc redirect đăng nhập với ứng dụng web. Không suy luận mọi `403` đều do role: CSRF cũng có thể bị từ chối bằng mã này.

### 1.3. “JDBC khác JPA ở đâu?”

**Trả lời phỏng vấn:**

> “JDBC là API Java để làm việc với database thông qua driver; với JDBC thuần em viết SQL, bind tham số và đọc `ResultSet`. JPA là đặc tả persistence và ORM, cho phép thao tác với entity, quan hệ và persistence context. Hibernate là một implementation của JPA; Spring Data JPA cung cấp abstraction repository phía trên JPA. Trong stack database quan hệ thông dụng, Hibernate vẫn dùng JDBC để giao tiếp với database.”

| Tiêu chí | JDBC thuần | JPA |
| --- | --- | --- |
| Mức làm việc | SQL, connection, statement, result set | Entity, persistence context, truy vấn đối tượng |
| Mapping dữ liệu | Tự map các cột sang object | Provider thực hiện mapping theo cấu hình |
| SQL | Chủ động viết SQL | Provider sinh SQL; vẫn dùng được native SQL |
| Theo dõi thay đổi object | Không tự theo dõi | Có quản lý entity và đồng bộ thay đổi |
| Quan hệ | Chủ động JOIN và map dữ liệu | Có metadata quan hệ, fetch và cascade |
| Đánh đổi | Nhiều code hạ tầng hơn, kiểm soát SQL rõ | Giảm code CRUD, cần hiểu SQL phát sinh và vòng đời entity |

Ví dụ JDBC, trong một phương thức đã xử lý hoặc khai báo `SQLException`:

```java
String sql = "SELECT id, name FROM products WHERE id = ?";
try (Connection connection = dataSource.getConnection();
     PreparedStatement statement = connection.prepareStatement(sql)) {
    statement.setLong(1, productId);
    try (ResultSet rs = statement.executeQuery()) {
        if (rs.next()) {
            return new ProductSummary(rs.getLong("id"), rs.getString("name"));
        }
        throw new ProductNotFoundException(productId);
    }
}
```

Đoạn JDBC trên sở hữu connection của riêng nó; khi tích hợp transaction Spring, ưu tiên abstraction quản lý connection phù hợp như `JdbcTemplate`, tránh mở một connection độc lập rồi tưởng nó cùng transaction.

Ví dụ JPA thuần:

```java
Product product = entityManager.find(Product.class, productId);
```

Ví dụ **Spring Data JPA**, không phải cú pháp bắt buộc của JPA:

```java
Product product = productRepository.findById(productId)
    .orElseThrow(() -> new ProductNotFoundException(productId));
```

**Hỏi đào sâu: “JPA có nhanh hơn JDBC không?”**

Không thể kết luận chỉ từ tên công nghệ. Hiệu năng phụ thuộc SQL, index, số lượt truy vấn, lượng dữ liệu và cách dùng. JPA dễ phát sinh N+1 hoặc tải dư dữ liệu; JDBC vẫn chậm nếu SQL hoặc thiết kế database kém. Đo query count, execution plan và thời gian trước khi tối ưu.

<a id="s2"></a>
## 2. Java

### 2.1. OOP trong code thực tế

#### Đóng gói: bảo vệ quy tắc nghiệp vụ

```java
enum OrderStatus { NEW, PAID, CANCELLED }

class Order {
    private OrderStatus status = OrderStatus.NEW;

    public void cancel() {
        if (status != OrderStatus.NEW) {
            throw new IllegalStateException("Only new orders can be cancelled");
        }
        status = OrderStatus.CANCELLED;
    }

    public OrderStatus getStatus() {
        return status;
    }
}
```

Nếu cung cấp `setStatus()` tùy ý, code bên ngoài có thể bỏ qua điều kiện hủy đơn. Đóng gói tốt làm cho những chuyển trạng thái không hợp lệ khó xảy ra hơn. Với collection bên trong object, cũng cần cân nhắc trả bản sao hoặc view không sửa được để tránh bị thay đổi ngoài kiểm soát.

#### Trừu tượng và đa hình

```java
interface PaymentGateway {
    PaymentResult pay(PaymentRequest request);
}

class CardPaymentGateway implements PaymentGateway {
    @Override
    public PaymentResult pay(PaymentRequest request) {
        // Minh họa: tích hợp nhà cung cấp thanh toán thẻ.
        return new PaymentResult("PENDING");
    }
}

class WalletPaymentGateway implements PaymentGateway {
    @Override
    public PaymentResult pay(PaymentRequest request) {
        // Minh họa: tích hợp ví điện tử.
        return new PaymentResult("PENDING");
    }
}

class CheckoutService {
    private final PaymentGateway gateway;

    CheckoutService(PaymentGateway gateway) {
        this.gateway = gateway;
    }

    PaymentResult checkout(PaymentRequest request) {
        return gateway.pay(request);
    }
}
```

`CheckoutService` biết hợp đồng `pay()` nhưng không cần biết giao thức của từng nhà cung cấp. Lời gọi được dispatch tới implementation thực tế là đa hình lúc chạy. Trong sản phẩm thật, kết quả thanh toán cần trạng thái rõ ràng và xác nhận từ nhà cung cấp; ví dụ trên không thực hiện giao dịch tiền.

#### Kế thừa

```java
abstract class Notification {
    protected final String recipient;

    protected Notification(String recipient) {
        this.recipient = recipient;
    }

    public abstract String render();
}

class EmailNotification extends Notification {
    EmailNotification(String recipient) {
        super(recipient);
    }

    @Override
    public String render() {
        return "Email to " + recipient;
    }
}
```

Quan hệ kế thừa phải hợp lý về hành vi: chỗ dùng lớp cha phải dùng được lớp con mà không phá kỳ vọng của hợp đồng. Nếu chỉ muốn tái sử dụng một bộ gửi email, thường nên truyền `EmailSender` vào service — composition — thay vì để mọi service kế thừa `EmailSender`.

**Hỏi đào sâu: overloading khác overriding?**

- **Overloading:** cùng tên method, khác danh sách tham số; lựa chọn chữ ký dựa trên thông tin kiểu tại thời điểm biên dịch. Chỉ đổi kiểu trả về không tạo overload hợp lệ.
- **Overriding:** lớp con cung cấp implementation cho instance method được kế thừa; implementation chạy phụ thuộc đối tượng thực tế. Dùng `@Override` để compiler kiểm tra.
- `static` method bị che khuất, không có dispatch đa hình như instance method; `final` method không cho override.

### 2.2. Collection: chọn cấu trúc theo nhu cầu

**Trả lời phỏng vấn:**

> “Collection Framework cung cấp các cấu trúc như List, Set, Queue và các implementation. Map là cấu trúc key–value trong framework nhưng không kế thừa interface Collection. Em chọn List khi cần thứ tự và chấp nhận trùng, Set khi cần tính duy nhất, Map khi cần tra theo khóa, Queue khi cần xử lý theo hàng đợi.”

| Cấu trúc | Đặc điểm | Ví dụ trong project |
| --- | --- | --- |
| `ArrayList` | Truy cập theo index nhanh, cho phần tử trùng, giữ thứ tự list | Danh sách DTO sản phẩm |
| `LinkedList` | Danh sách liên kết đôi, đồng thời là deque; truy cập index phải duyệt | Chỉ chọn khi thao tác thực tế phù hợp, không mặc định nhanh hơn |
| `HashSet` | Không giữ thứ tự iteration theo hợp đồng, loại trùng bằng equality/hash | Tập mã sản phẩm đã xử lý |
| `LinkedHashSet` | Loại trùng và giữ thứ tự chèn | Danh sách ID yêu thích không trùng |
| `TreeSet` | Sắp xếp theo thứ tự tự nhiên hoặc comparator | Tập dữ liệu cần duyệt có thứ tự |
| `HashMap` | Tra value theo key; không cam kết thứ tự iteration | Map `productId → quantity` |
| `LinkedHashMap` | Giữ thứ tự chèn hoặc thứ tự truy cập theo cấu hình | Map cần thứ tự dự đoán được |
| `TreeMap` | Key được sắp xếp | Tra cứu theo khoảng khóa |
| `ArrayDeque` | Thao tác hai đầu thuận tiện; không nhận `null` | Stack hoặc queue trong bộ nhớ |
| `PriorityQueue` | Lấy phần tử ưu tiên ở đầu; iterator không đảm bảo duyệt có thứ tự | Công việc ưu tiên trong một tiến trình |

Queue trong bộ nhớ mất dữ liệu khi tiến trình dừng và không tự chia sẻ giữa các instance. Không coi nó tương đương message broker.

**Độ phức tạp cần nắm:**

| Thao tác | Chi phí điển hình |
| --- | --- |
| `ArrayList.get(i)` | O(1) |
| `ArrayList.add(e)` ở cuối | O(1) amortized; một lần tăng dung lượng có thể O(n) |
| Chèn/xóa giữa `ArrayList` | O(n) do dịch phần tử |
| `LinkedList.get(i)` | O(n) |
| Chèn/xóa qua iterator đã ở vị trí cần thiết của `LinkedList` | O(1); tìm vị trí vẫn có thể O(n) |
| `HashMap.get/put`, `HashSet.contains/add` | Trung bình O(1) khi hash phân bố tốt; không phải bảo đảm mọi trường hợp |
| `TreeMap.get/put`, `TreeSet.contains/add` | O(log n) |

**Hỏi đào sâu: `equals()` và `hashCode()` liên quan thế nào?**

- Hai object `equals()` nhau phải có cùng `hashCode()`.
- Cùng hash không có nghĩa là equals; đó có thể là collision.
- Khi override `equals()`, cần override `hashCode()` tương ứng.
- Không thay đổi field tham gia equality/hash khi object đang làm key của `HashMap` hoặc phần tử của `HashSet`.
- Với `TreeSet`, tính trùng được quyết định bằng kết quả so sánh bằng `0`; comparator nên nhất quán với `equals()`.

Ví dụ Java record phù hợp cho key có các thành phần bất biến:

```java
record CartKey(Long userId, Long productId) {}

Map<CartKey, Integer> quantities = new HashMap<>();
quantities.merge(new CartKey(10L, 99L), 2, Integer::sum);
```

**Hỏi đào sâu: nhiều thread cùng dùng Map thì sao?**

`HashMap` không thread-safe. `ConcurrentHashMap` hỗ trợ truy cập đồng thời, nhưng chuỗi `get → tính toán → put` vẫn có thể race. Dùng thao tác nguyên tử phù hợp như `compute()` hoặc `merge()`; nếu value là object mutable thì việc sửa bên trong value vẫn cần thiết kế đồng bộ. `ConcurrentHashMap` không nhận key/value `null`. Hợp đồng về thứ tự, hiệu năng kỳ vọng và đồng bộ hóa của `HashMap` được mô tả trong [Java API: HashMap](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/HashMap.html).

### 2.3. Exception: phân loại và xử lý đúng tầng

**Trả lời phỏng vấn:**

> “Exception biểu diễn tình huống bất thường khi chạy. Checked exception buộc code bắt hoặc khai báo throws; unchecked exception không có yêu cầu đó. Em xử lý lỗi ở nơi có đủ ngữ cảnh để phục hồi, hoặc chuyển thành lỗi nghiệp vụ và để lớp xử lý lỗi tập trung tạo HTTP response.”

```text
Throwable
├── Error                     → unchecked
└── Exception
    ├── RuntimeException      → unchecked
    └── Các nhánh còn lại      → checked
```

| Nhóm | Ví dụ | Cách hiểu |
| --- | --- | --- |
| Checked | `IOException`, `SQLException` | Compiler yêu cầu bắt hoặc khai báo |
| Unchecked exception | `IllegalArgumentException`, `NullPointerException` | Thường liên quan vi phạm điều kiện hoặc lỗi lập trình; cũng được dùng cho lỗi nghiệp vụ |
| Error | `OutOfMemoryError`, `StackOverflowError` | Vấn đề nghiêm trọng; thường không thể xử lý như lỗi nghiệp vụ thông thường |

**Các từ khóa:**

- `throw`: ném một exception cụ thể.
- `throws`: khai báo exception có thể thoát khỏi method.
- `try/catch`: đặt phạm vi xử lý và bắt lỗi phù hợp.
- `finally`: thường dùng cho dọn dẹp; không đảm bảo chạy nếu tiến trình bị kết thúc cưỡng bức.
- `try-with-resources`: tự đóng tài nguyên `AutoCloseable`, theo thứ tự ngược khi khai báo.

Ví dụ lỗi nghiệp vụ và response tập trung:

```java
class ProductNotFoundException extends RuntimeException {
    ProductNotFoundException(Long id) {
        super("Product not found: " + id);
    }
}

record ApiError(String code, String message) {}

@RestControllerAdvice
class ApiExceptionHandler {
    @ExceptionHandler(ProductNotFoundException.class)
    ResponseEntity<ApiError> handleNotFound(ProductNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(new ApiError("PRODUCT_NOT_FOUND", ex.getMessage()));
    }
}
```

API thực tế có thể bổ sung `traceId` để đối chiếu log. Không đưa stack trace, SQL, mật khẩu hoặc token vào response. Lỗi xác thực/phân quyền xảy ra trong security filter chain thường cần `AuthenticationEntryPoint` hoặc `AccessDeniedHandler`, không nên cho rằng `@RestControllerAdvice` bắt được mọi lỗi.

**Những cách xử lý dễ mất điểm:**

- `catch (Exception e) {}` làm mất lỗi và khiến hệ thống tiếp tục với trạng thái không rõ.
- Bắt lỗi rồi luôn trả `200 OK`, khiến client không phân biệt được thành công/thất bại.
- Dùng `return` trong `finally`, có thể che mất exception hoặc kết quả trước đó.
- Log cùng một lỗi ở mọi tầng, tạo nhiều bản log trùng nhau.
- Khi bọc exception, bỏ mất nguyên nhân; nên giữ `cause` nếu cần chuyển loại lỗi.
- Bắt `InterruptedException` rồi bỏ qua; nếu không truyền tiếp được thì thường cần khôi phục cờ bằng `Thread.currentThread().interrupt()`.

**Hỏi đào sâu: lỗi nào làm transaction rollback?** Xem mục 3.3; checked/unchecked liên quan đến quy tắc rollback mặc định của Spring, nhưng có thể cấu hình lại.

### 2.4. Interface khác Abstract Class thế nào?

**Trả lời phỏng vấn:**

> “Interface mô tả một hợp đồng hoặc khả năng mà nhiều class có thể thực hiện. Abstract class phù hợp khi các lớp có quan hệ kế thừa và cần chia sẻ trạng thái hoặc implementation. Java cho phép một class implement nhiều interface, nhưng chỉ extends một class.”

| Tiêu chí | Interface | Abstract class |
| --- | --- | --- |
| Khởi tạo trực tiếp | Không | Không |
| Constructor | Không có constructor cho instance | Có constructor cho lớp con gọi |
| Trạng thái instance | Không có instance field; field khai báo là `public static final` | Có instance field và các mức truy cập |
| Method | Có abstract, default, static và private method theo phiên bản Java | Có abstract method và method có thân |
| Kế thừa | Một class implement nhiều interface | Một class chỉ extends một class |
| Dùng khi | Cần hợp đồng, khả năng thay implementation | Cần lớp cơ sở có hành vi và trạng thái chung |

Trong ví dụ, `PaymentGateway` phù hợp là interface vì các nhà cung cấp có cách tích hợp khác nhau. `Notification` có thể là abstract class nếu thật sự có trạng thái và hành vi chung.

**Hỏi đào sâu:**

- **Interface có code không?** Có: `default`, `static`, và private helper; “interface chỉ có abstract method” là nhận định lỗi thời.
- **Abstract class có bắt buộc chứa abstract method không?** Không.
- **Mọi service có cần một interface không?** Không. Tạo interface khi hợp đồng hoặc ranh giới phụ thuộc có ích; một implementation đơn giản không tự động cần một cặp `Service`/`ServiceImpl`.
- **Hai interface có default method cùng chữ ký?** Khi có xung đột không được quy tắc kế thừa giải quyết, class phải override để làm rõ lựa chọn.

<a id="s3"></a>
## 3. Database

### 3.1. JOIN là gì? Phân biệt các loại JOIN

**Trả lời phỏng vấn:**

> “JOIN kết hợp các dòng từ nhiều bảng theo một điều kiện. INNER JOIN lấy những dòng khớp ở cả hai phía; LEFT JOIN giữ mọi dòng bên trái và bổ sung dữ liệu bên phải nếu khớp. Em dùng JOIN để lấy đơn kèm thông tin khách hàng, hoặc thống kê cả những khách chưa có đơn.”

Giả sử:

```text
users                         orders
id | name                     id  | user_id | status
1  | An                       101 | 1       | PAID
2  | Bình                     102 | 1       | NEW
3  | Chi                      103 | 2       | NEW
```

```sql
SELECT u.id, u.name, o.id AS order_id
FROM users u
INNER JOIN orders o ON o.user_id = u.id;
```

Kết quả có An hai dòng và Bình một dòng; Chi không xuất hiện. Một quan hệ một–nhiều làm lặp dữ liệu phía “một” trong kết quả JOIN là bình thường.

```sql
SELECT u.id, u.name, o.id AS order_id
FROM users u
LEFT JOIN orders o ON o.user_id = u.id;
```

Kết quả thêm Chi với `order_id = NULL`.

| Loại | Kết quả |
| --- | --- |
| `INNER JOIN` | Các cặp dòng thỏa điều kiện nối |
| `LEFT JOIN` | Giữ mọi dòng trái, phía phải không khớp thì `NULL` |
| `RIGHT JOIN` | Giữ mọi dòng phải; thường đổi thứ tự bảng để viết thành LEFT JOIN |
| `FULL OUTER JOIN` | Giữ cả dòng không khớp của hai phía; cần kiểm tra DBMS có hỗ trợ cú pháp không |
| `CROSS JOIN` | Tích Descartes, có thể tạo số dòng bằng tích số dòng hai bảng |
| Self join | Một bảng JOIN với chính nó qua alias; ví dụ nhân viên và quản lý |

**Bẫy `ON` và `WHERE` khi LEFT JOIN:**

Muốn lấy **mọi người dùng**, chỉ nối những đơn đã thanh toán:

```sql
SELECT u.id, u.name, o.id AS paid_order_id
FROM users u
LEFT JOIN orders o
    ON o.user_id = u.id AND o.status = 'PAID';
```

Nếu chuyển điều kiện sang `WHERE o.status = 'PAID'`, các dòng có `NULL` bị loại. Khi đó mất người chưa có đơn đã thanh toán, trái với yêu cầu ban đầu.

**Đếm cả người có 0 đơn:**

```sql
SELECT u.id, u.name, COUNT(o.id) AS order_count
FROM users u
LEFT JOIN orders o ON o.user_id = u.id
GROUP BY u.id, u.name;
```

`COUNT(o.id)` bỏ qua `NULL`; `COUNT(*)` vẫn đếm dòng của người chưa có đơn thành 1. Khi JOIN thêm nhiều bảng một–nhiều, cần chú ý nhân số dòng gây đếm hoặc cộng tiền sai.

**Hỏi đào sâu: JOIN khác UNION?** JOIN ghép dữ liệu theo chiều cột trên các cặp dòng; UNION ghép các tập kết quả có cấu trúc cột tương thích theo chiều dòng. `UNION` loại dòng trùng; `UNION ALL` giữ dòng trùng.

### 3.2. Index là gì? Khi nào cần tạo?

**Trả lời phỏng vấn:**

> “Index là cấu trúc dữ liệu hỗ trợ tìm và truy cập bản ghi hiệu quả hơn cho một số kiểu truy vấn. Với API liệt kê đơn theo người dùng và thời gian, em cân nhắc index bắt đầu bằng `user_id`, rồi đến cột sắp xếp. Index tốn dung lượng và làm tăng chi phí ghi nên em chọn theo truy vấn thực tế, sau đó kiểm tra execution plan.”

Với B-tree index, database có thể tìm theo khóa hoặc khoảng khóa thay vì quét tất cả các dòng. Tuy nhiên optimizer vẫn có thể chọn scan nếu nó đánh giá scan rẻ hơn.

```sql
SELECT id, created_at, status
FROM orders
WHERE user_id = 10
ORDER BY created_at DESC, id DESC
LIMIT 20;

CREATE INDEX idx_orders_user_created_id
ON orders(user_id, created_at, id);
```

Index trên là một ứng viên cho mẫu truy vấn, không phải cam kết optimizer luôn dùng hoặc truy vấn luôn nhanh. `id` giúp thứ tự xác định khi nhiều đơn có cùng `created_at`.

**Composite index và leftmost prefix:** Với index `(user_id, status, created_at)`, các điều kiện bắt đầu từ `user_id` thường thuận lợi hơn. Query chỉ theo `status` không có cùng khả năng tra cứu theo prefix; một số DBMS có tối ưu bổ sung nên không kết luận tuyệt đối là “không thể dùng index”. Quy tắc này được giải thích trong [MySQL: Multiple-Column Indexes](https://dev.mysql.com/doc/refman/8.4/en/multiple-column-indexes.html).

**Các điểm cần giải thích được:**

- Index hỗ trợ `WHERE`, điều kiện JOIN, `ORDER BY` hoặc `GROUP BY` tùy truy vấn và cấu trúc index.
- Thứ tự cột quan trọng; không chỉ dựa vào “cột nào có nhiều giá trị khác nhau nhất”. Phải xét equality, range và sort của workload.
- `LIKE '%abc'` thường không tận dụng B-tree để tìm theo prefix; tìm kiếm nội dung có thể cần full-text index.
- Bọc cột bằng hàm hoặc ép kiểu có thể cản khả năng dùng index thông thường; có DBMS hỗ trợ expression index.
- Covering index chứa đủ dữ liệu query cần, có thể giảm đọc bảng; cơ chế phụ thuộc DBMS.
- Unique index/constraint còn giúp bảo vệ tính duy nhất, chẳng hạn email đã chuẩn hóa.
- Thêm index làm tăng công việc khi `INSERT`, `UPDATE`, `DELETE`, và tiêu thụ bộ nhớ/lưu trữ.

**“Em kiểm chứng index hiệu quả thế nào?”**

1. Lấy SQL và tham số đại diện, không đo chỉ trên vài dòng dữ liệu.
2. Dùng `EXPLAIN`; nếu DBMS hỗ trợ, dùng biến thể đo thực thi phù hợp trong môi trường an toàn. `EXPLAIN ANALYZE` thực sự chạy query.
3. Xem index được chọn, số dòng đọc, join strategy, sort và chênh lệch estimated/actual rows.
4. So sánh latency và chi phí ghi trước/sau với cùng điều kiện, đồng thời cân nhắc cache nóng/lạnh.

### 3.3. Transaction là gì? ACID là gì?

**Trả lời phỏng vấn:**

> “Transaction gom nhiều thao tác thành một đơn vị công việc. Khi tạo đơn, ghi order, ghi order item và cập nhật tồn kho cần nhất quán: nếu một bước lỗi, các thay đổi thuộc cùng transaction phải được rollback. Em đặt ranh giới transaction ở service vì đây là nơi biết toàn bộ nghiệp vụ.”

| Thuộc tính | Ý nghĩa | Ví dụ |
| --- | --- | --- |
| Atomicity | Các thay đổi trong transaction được commit hoặc rollback như một đơn vị | Không lưu đơn thành công nhưng thiếu các dòng hàng do một bước lỗi |
| Consistency | Transaction đưa dữ liệu giữa các trạng thái thỏa ràng buộc | FK hợp lệ, số lượng không âm; quy tắc vẫn phải được code/constraint thực thi |
| Isolation | Kiểm soát các transaction đồng thời nhìn thấy và ảnh hưởng nhau | Hành vi đọc/ghi phụ thuộc isolation level |
| Durability | Dữ liệu đã commit được bảo đảm bền vững theo cơ chế và cấu hình database | Phục hồi dữ liệu đã commit sau sự cố thuộc phạm vi bảo đảm của DB |

**Các hiện tượng đồng thời:**

- **Dirty read:** đọc thay đổi chưa commit của transaction khác.
- **Non-repeatable read:** đọc lại cùng bản ghi nhưng giá trị thay đổi do transaction khác đã commit.
- **Phantom read:** chạy lại điều kiện truy vấn và tập dòng khớp thay đổi.
- **Lost update:** một cập nhật ghi đè kết quả cập nhật khác do đọc–sửa–ghi thiếu kiểm soát.

| Isolation level | Cách mô tả cơ bản |
| --- | --- |
| Read Uncommitted | Có thể cho phép dirty read; một số DBMS ánh xạ sang mức mạnh hơn |
| Read Committed | Không đọc dữ liệu chưa commit; các lần đọc có thể thấy dữ liệu đã commit khác nhau |
| Repeatable Read | Ngăn non-repeatable read; xử lý phantom và xung đột phụ thuộc DBMS/cơ chế đọc |
| Serializable | Hành vi tương đương một thứ tự chạy tuần tự; có thể cần retry khi xung đột |

Không suy từ tên isolation rằng mọi DBMS có cùng cách khóa/MVCC. `@Transactional` cũng không tự động loại bỏ mọi race condition.

**Transaction trong Spring:**

```java
@Service
class OrderService {
    private final OrderRepository orderRepository;

    OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Transactional
    public void cancel(Long orderId, Long currentUserId) {
        Order order = orderRepository.findByIdAndUserId(orderId, currentUserId)
            .orElseThrow(() -> new OrderNotFoundException(orderId));
        order.cancel();
        // Entity managed: thay đổi được đồng bộ khi flush trong transaction.
    }
}
```

Đây là phiên bản entity/service dành cho persistence, mở rộng ví dụ OOP trước đó với ID, quan hệ user và mapping JPA.

**Những điểm hay bị hỏi xoáy:**

- Mặc định thông thường của Spring là rollback với `RuntimeException` và `Error`; checked exception không tự gây rollback. Có thể cấu hình `rollbackFor` hoặc thay đổi quy tắc mặc định.
- Trong proxy mode, `this.otherMethod()` không đi qua proxy nên annotation của method được gọi nội bộ không được áp dụng. Nếu method ngoài đã có transaction, lời gọi trong vẫn chạy trong transaction đó.
- `REQUIRED` tham gia transaction hiện có hoặc tạo mới; `REQUIRES_NEW` dùng transaction độc lập, có thể cần thêm connection.
- `readOnly = true` là tín hiệu tối ưu, không phải hàng rào tuyệt đối cấm ghi.
- Bắt exception rồi trả bình thường có thể khiến transaction commit; nếu đã bị đánh dấu rollback-only thì vẫn có thể rollback và báo lỗi lúc kết thúc.

Đối chiếu cơ chế và khả năng cấu hình theo phiên bản ở [Spring: Using @Transactional](https://docs.spring.io/spring-framework/reference/data-access/transaction/declarative/annotations.html).

**“Hai người mua sản phẩm cuối cùng thì sao?”**

Đọc `stock = 1` rồi mỗi request tự trừ trong Java có thể bán vượt tồn. Một phương án là cập nhật có điều kiện ngay tại DB:

```sql
UPDATE products
SET stock = stock - :quantity
WHERE id = :productId
  AND stock >= :quantity;
```

Service phải xác nhận `quantity > 0` và kiểm tra số dòng cập nhật: 1 là thành công, 0 là không đáp ứng điều kiện hoặc không tồn tại. Ghi đơn và trừ kho cần cùng transaction. Với nhiều sản phẩm, lỗi một dòng phải rollback toàn bộ và nên khóa/cập nhật theo thứ tự nhất quán để giảm deadlock. Nếu dùng bulk update bên cạnh entity managed, cần xử lý persistence context bị cũ và thống nhất với chiến lược version nếu có.

Phương án khác là optimistic locking bằng `@Version`, hoặc pessimistic locking. Chọn theo mức tranh chấp và cách xử lý retry; không chỉ thêm annotation rồi coi là xong.

**“Gọi thanh toán bên ngoài trong transaction có rollback được tiền không?”**

Không. Transaction database cục bộ không tự hoàn tác HTTP request hoặc email đã gửi. Có thể dùng trạng thái đơn, idempotency, webhook có xác minh và cơ chế bù trừ. Với việc ghi DB và phát event, transactional outbox lưu event cùng transaction rồi worker chuyển tiếp; consumer vẫn phải xử lý event trùng.

### 3.4. Thiết kế database cho project bán hàng

**Trả lời phỏng vấn:**

> “Em bắt đầu từ các thực thể, quan hệ và quy tắc dữ liệu. Em tách Order với OrderItem vì một đơn có nhiều sản phẩm; OrderItem giữ giá lúc mua để lịch sử không đổi khi giá sản phẩm thay đổi. Em dùng PK, FK, unique và check constraint phù hợp, rồi bổ sung index theo các query chính.”

| Bảng | Cột chính | Lý do |
| --- | --- | --- |
| `users` | `id`, `email`, `password_hash`, `status` | Danh tính và trạng thái tài khoản |
| `products` | `id`, `sku`, `name`, `price`, `stock`, `version` | Danh mục, giá hiện tại và tồn kho |
| `orders` | `id`, `user_id`, `status`, `total_amount`, `created_at` | Thông tin chung của đơn |
| `order_items` | `id`, `order_id`, `product_id`, `quantity`, `unit_price` | Các dòng hàng và giá lịch sử |
| `roles` | `id`, `name` | Các nhóm quyền |
| `user_roles` | `user_id`, `role_id` | Quan hệ nhiều–nhiều; khóa ghép hoặc unique trên cặp |
| `refresh_tokens` | `id`, `user_id`, `token_hash`, `expires_at`, `revoked_at`, `family_id` | Quản lý vòng đời phiên refresh và phát hiện tái sử dụng |

**Ràng buộc nên bảo vệ:**

- PK nhận diện duy nhất bản ghi; FK bảo vệ liên kết với bản ghi cha.
- Email/SKU có unique theo quy tắc chuẩn hóa đã chọn, không chỉ kiểm tra tồn tại trong code.
- `quantity > 0`, `stock >= 0`, giá không âm theo yêu cầu nghiệp vụ; đảm bảo DBMS thực sự thực thi constraint sử dụng.
- Tiền dùng `DECIMAL` và `BigDecimal`, thống nhất currency, scale và quy tắc làm tròn; tránh `float`/`double` cho giá trị tiền chính xác.
- Dữ liệu bắt buộc phải có `NOT NULL`; validation ở API không thay thế constraint DB.
- Chính sách xóa sản phẩm/người dùng phải giữ lịch sử đơn: cân nhắc ngừng hoạt động hoặc snapshot thay vì xóa dây chuyền.

**Chuẩn hóa ngắn gọn:**

- **1NF:** không nhét danh sách sản phẩm dạng chuỗi vào một cột; giá trị phù hợp miền dữ liệu và cấu trúc bảng.
- **2NF:** thuộc tính không khóa phụ thuộc toàn bộ khóa ứng viên, không chỉ một phần của khóa ghép.
- **3NF:** tránh phụ thuộc bắc cầu không phù hợp giữa thuộc tính không khóa; ví dụ tên role nằm ở `roles`, không lặp ở từng quan hệ user–role.

Denormalization có thể hữu ích khi có yêu cầu đọc cụ thể, nhưng phải xác định cách đồng bộ và nguồn dữ liệu chuẩn. `unit_price` của dòng đơn là giá lịch sử theo nghiệp vụ, không phải bản sao phải cập nhật theo `products.price`.

**Hỏi đào sâu: “Tại sao có `total_amount` khi tính được từ OrderItem?”**

Có thể lưu tổng như một snapshot gồm hàng hóa, giảm giá, thuế và phí vận chuyển. Khi lưu phải bảo đảm nó được tính ở server và nhất quán trong transaction; không tin tổng tiền từ client. Nếu không cần lưu, có thể tính khi đọc, với chi phí truy vấn tương ứng.

<a id="s4"></a>
## 4. Spring và Spring Boot

### 4.1. Spring khác Spring Boot thế nào?

**Trả lời phỏng vấn:**

> “Spring Framework cung cấp IoC container cùng các cơ chế như DI, AOP, transaction và web MVC. Spring Boot xây trên Spring để giảm cấu hình khởi đầu thông qua auto-configuration, starter và khả năng chạy ứng dụng với embedded server trong cấu hình web phù hợp. Boot không thay thế việc hiểu Spring hoặc nghiệp vụ.”

`@SpringBootApplication` kết hợp cấu hình ứng dụng, bật auto-configuration và component scanning. Theo mặc định, nên đặt class chính ở package cha của các thành phần ứng dụng để việc scan phù hợp.

Auto-configuration lựa chọn cấu hình theo điều kiện như dependency, bean đã tồn tại và property; không phải “Spring tự đoán mọi thứ”. Khi có vấn đề, kiểm tra cấu hình thực tế và báo cáo điều kiện thay vì thêm annotation ngẫu nhiên.

### 4.2. IoC là gì? DI liên quan thế nào?

**Trả lời phỏng vấn:**

> “IoC là đảo ngược quyền điều khiển. Với Spring IoC container, container quản lý việc tạo, kết nối và vòng đời các bean thay vì code nghiệp vụ tự làm hết. Dependency Injection là một cách thực hiện IoC: dependency được truyền vào đối tượng từ bên ngoài.”

Tự tạo dependency:

```java
class ReportService {
    private final CsvExporter exporter = new CsvExporter();
}
```

Nhận dependency qua constructor:

```java
@Service
class ReportService {
    private final Exporter exporter;

    ReportService(Exporter exporter) {
        this.exporter = exporter;
    }
}
```

DI không chỉ tồn tại trong Spring. Trong unit test, `new ReportService(fakeExporter)` cũng là constructor injection do code test thực hiện.

### 4.3. Các kiểu Dependency Injection

| Kiểu | Đặc điểm | Lựa chọn thực tế |
| --- | --- | --- |
| Constructor injection | Dependency bắt buộc hiển thị rõ, có thể dùng field `final` | Thường ưu tiên cho dependency bắt buộc |
| Setter injection | Có thể cung cấp hoặc thay dependency sau khi tạo | Dùng có chủ đích cho cấu hình/phụ thuộc tùy chọn |
| Field injection | Ngắn nhưng che dependency, khó khởi tạo thủ công | Không phải lựa chọn mặc định tốt cho code mới |

Với bean có một constructor, Spring có thể dùng constructor đó mà không cần ghi `@Autowired`.

**“Có hai bean cùng implement một interface thì sao?”**

```java
@Component("cardGateway")
class CardPaymentGateway implements PaymentGateway {
    // Triển khai pay() như ví dụ trước.
}

@Component("walletGateway")
class WalletPaymentGateway implements PaymentGateway {
    // Triển khai pay() như ví dụ trước.
}

@Service
class CheckoutService {
    private final PaymentGateway gateway;

    CheckoutService(@Qualifier("cardGateway") PaymentGateway gateway) {
        this.gateway = gateway;
    }
}
```

Đây là cấu hình thay thế cho các class ở mục 2.1, không khai báo thêm bản trùng tên trong cùng project. `@Qualifier` chỉ rõ ứng viên; `@Primary` đánh dấu bean ưu tiên khi phù hợp. Nếu muốn chọn cổng theo từng request, inject một tập implementation rồi chọn theo loại thanh toán đã kiểm tra, thay vì cố định một bean.

**“Circular dependency là gì?”**

`A` cần `B` và `B` lại cần `A`. Với constructor injection trực tiếp, container không thể hoàn tất tạo hai đối tượng như thiết kế đó. Nên xem lại trách nhiệm, tách phần dùng chung hoặc thêm một service điều phối; `@Lazy` có thể trì hoãn resolution trong vài trường hợp nhưng không tự giải quyết vấn đề thiết kế.

### 4.4. Bean là gì? Scope và vòng đời

**Trả lời phỏng vấn:**

> “Bean là đối tượng được Spring IoC container quản lý. Nó có thể được đăng ký qua component scanning hoặc method `@Bean`. Scope mặc định là singleton: một instance cho mỗi bean definition trong một container, không phải một instance duy nhất của class trên toàn JVM.”

| Annotation | Vai trò thường dùng |
| --- | --- |
| `@Component` | Thành phần được phát hiện qua scan |
| `@Service` | Thể hiện vai trò xử lý nghiệp vụ |
| `@Repository` | Thành phần truy cập dữ liệu; tham gia cơ chế dịch exception khi có hạ tầng phù hợp |
| `@Controller` | Thành phần Spring MVC xử lý web |
| `@RestController` | Controller với response body mặc định |
| `@Configuration` + `@Bean` | Đăng ký bean qua Java config, thuận tiện với class thư viện |

Ví dụ bean thời gian giúp test dễ kiểm soát:

```java
@Configuration
class TimeConfig {
    @Bean
    Clock clock() {
        return Clock.systemUTC();
    }
}
```

| Scope | Ý nghĩa |
| --- | --- |
| `singleton` | Một instance cho mỗi bean definition trong container |
| `prototype` | Tạo instance khi bean được yêu cầu từ container; không tự tạo mới ở mọi lời gọi method |
| `request` | Một instance trong một HTTP request, cần web context |
| `session` | Một instance trong một HTTP session, cần web context |

Inject một prototype trực tiếp vào singleton thường chỉ resolve khi singleton được tạo. Nếu cần instance mới cho mỗi lần dùng, cân nhắc `ObjectProvider` hoặc cơ chế lookup/proxy phù hợp.

Vòng đời giản lược: tạo instance → inject dependency → callback khởi tạo như `@PostConstruct` → sẵn sàng sử dụng → callback hủy như `@PreDestroy` khi container đóng. Bean post-processors có thể can thiệp và tạo proxy trong quá trình này. Spring không tự quản đầy đủ callback hủy của prototype sau khi trao instance cho bên gọi.

**“Singleton bean có thread-safe không?”**

Không tự động. Service singleton có thể phục vụ nhiều request đồng thời. Tránh field mutable chứa `currentUser`, giỏ hàng hoặc dữ liệu riêng của request; ưu tiên biến cục bộ và đối tượng bất biến. Chỉ có một instance không có nghĩa là các thread được chạy tuần tự.

### 4.5. REST API: thiết kế và giải thích

**Trả lời phỏng vấn:**

> “REST là một phong cách kiến trúc; trong API HTTP em biểu diễn tài nguyên bằng URI, dùng method và status code theo ngữ nghĩa, và giữ request đủ thông tin để server xử lý độc lập với ngữ cảnh hội thoại lưu ở server. API trả JSON chưa đủ để kết luận nó đáp ứng toàn bộ ràng buộc REST.”

Các ràng buộc REST gồm client–server, stateless, khả năng cache, giao diện thống nhất, hệ thống phân lớp và code-on-demand tùy chọn. Trong phỏng vấn thực hành cần giải thích được tài nguyên, HTTP semantics và những phần API của mình thực sự áp dụng.

| Nhu cầu | Endpoint minh họa | Kết quả phổ biến |
| --- | --- | --- |
| Danh sách sản phẩm | `GET /api/products?page=0&size=20` | `200 OK` |
| Chi tiết sản phẩm | `GET /api/products/{id}` | `200`, không tồn tại thì `404` |
| Tạo đơn | `POST /api/orders` | `201 Created`, thường kèm `Location` |
| Thay thế biểu diễn tài nguyên | `PUT /api/products/{id}` | `200` hoặc `204`; có thể `201` nếu hợp đồng cho tạo mới |
| Cập nhật một phần | `PATCH /api/products/{id}` | `200` hoặc `204` |
| Xóa tài nguyên | `DELETE /api/products/{id}` | Thường `204` khi hoàn tất |

**Safe và idempotent:**

- `GET` là safe: client không yêu cầu thay đổi trạng thái nghiệp vụ; việc ghi access log không làm mất tính safe.
- Idempotent nghĩa là lặp cùng request có tác động dự kiến lên server giống thực hiện một lần; không bắt buộc response giống nhau.
- `PUT`, `DELETE` có ngữ nghĩa idempotent. `DELETE` lần hai trả `404` vẫn không mâu thuẫn.
- `POST`, `PATCH` không mặc định idempotent. API tạo đơn có thể bổ sung idempotency key và lưu kết quả theo khóa để xử lý retry.

**Status code nên phân biệt:**

- `400`: request không hợp lệ; thường dùng cho parse/validation theo hợp đồng API.
- `401`: thiếu hoặc không có thông tin xác thực hợp lệ.
- `403`: bị từ chối quyền theo chính sách.
- `404`: không tìm thấy tài nguyên hoặc được dùng để che sự tồn tại theo chính sách.
- `409`: xung đột trạng thái, chẳng hạn đơn đã thanh toán không thể hủy theo luồng hiện tại.
- `422`: có thể dùng khi nội dung hợp lệ về cú pháp nhưng không xử lý được về ngữ nghĩa; cần thống nhất với client.
- `429`: vượt giới hạn request; `500`: lỗi server ngoài dự kiến.

**Controller cần mỏng:**

```java
public record CreateOrderItemRequest(
    @NotNull Long productId,
    @NotNull @Positive Integer quantity
) {}

public record CreateOrderRequest(
    @NotEmpty List<@NotNull @Valid CreateOrderItemRequest> items
) {}

@RestController
@RequestMapping("/api/orders")
class OrderController {
    private final OrderApplicationService orderService;

    OrderController(OrderApplicationService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    ResponseEntity<OrderResponse> create(
            @Valid @RequestBody CreateOrderRequest request,
            @AuthenticationPrincipal Jwt jwt) {
        OrderResponse result = orderService.create(jwt.getSubject(), request);
        return ResponseEntity
            .created(URI.create("/api/orders/" + result.id()))
            .body(result);
    }
}
```

Ví dụ giả định request đã được xác thực bằng JWT; service ánh xạ subject từ issuer tin cậy sang user nội bộ. Validation cần implementation Bean Validation trong dependency. Các record public được đặt ở file riêng. Không nhận `userId`, role hoặc tổng tiền từ client rồi tin đó là thông tin có thẩm quyền; phải lấy danh tính đã xác thực và tính giá ở server.

**“DTO khác Entity?”** DTO mô tả hợp đồng trao đổi dữ liệu; Entity mô tả mô hình persistence. Tách chúng giúp không lộ field nhạy cảm, tránh serialize quan hệ vòng/lazy ngoài ý muốn và cho phép thay đổi database mà ít ảnh hưởng API. Validation DTO kiểm tra cấu trúc đầu vào; service vẫn kiểm tra tồn kho, chủ sở hữu và trạng thái đơn.

<a id="s5"></a>
## 5. JPA và Spring Data JPA

### 5.1. Entity và các quan hệ

**Trả lời phỏng vấn:**

> “Entity là đối tượng được mapping để lưu trữ bằng JPA, có định danh và vòng đời được quản lý khi nằm trong persistence context. Em ánh xạ User–Order là một–nhiều, Order–OrderItem là một–nhiều và OrderItem–Product là nhiều–một. Quan hệ Java cần đi kèm hiểu biết FK và SQL ở database.”

| Mapping | Ví dụ | Vị trí FK thường gặp |
| --- | --- | --- |
| `@OneToOne` | User–Profile | FK có unique ở một phía hoặc dùng chung khóa |
| `@OneToMany` | Order–OrderItem | FK `order_id` ở bảng con |
| `@ManyToOne` | Order–User | FK `user_id` ở `orders` |
| `@ManyToMany` | User–Role | Bảng nối `user_roles` |

Một phần mapping cho quan hệ hai chiều, dùng `jakarta.persistence.*`:

```java
@Entity
@Table(name = "orders")
class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL,
               orphanRemoval = true)
    private List<OrderItem> items = new ArrayList<>();

    @Version
    private Long version;

    protected Order() {}

    public void addItem(OrderItem item) {
        items.add(item);
        item.setOrder(this);
    }
}

@Entity
@Table(name = "order_items")
class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    protected OrderItem() {}

    void setOrder(Order order) {
        this.order = order;
    }
}
```

Ví dụ lược bỏ trạng thái, tiền và các field nghiệp vụ; `IDENTITY` chỉ là lựa chọn minh họa, không phù hợp mọi DBMS/workload. `CascadeType.ALL` và `orphanRemoval` chỉ nên dùng khi vòng đời dòng hàng thực sự thuộc đơn; đơn đã chốt có thể cần quy tắc không cho sửa/xóa lịch sử.

**Owning side và `mappedBy`:** phía `OrderItem.order` giữ mapping FK và là owning side trong ví dụ này. `mappedBy = "order"` trỏ tới **tên thuộc tính Java** phía kia, không phải tên cột `order_id`. Cập nhật cả hai phía để mô hình trong bộ nhớ nhất quán.

**Cascade khác fetch:** cascade quyết định thao tác như persist/remove có lan tới entity liên quan hay không; fetch quyết định nhu cầu tải dữ liệu quan hệ. `orphanRemoval` hỗ trợ xóa entity con bị bỏ khỏi quan hệ sở hữu theo mapping. Không cascade remove từ đơn sang user hoặc từ dòng hàng sang sản phẩm dùng chung. Các thuật ngữ mapping được định nghĩa trong [Jakarta Persistence 3.1](https://jakarta.ee/specifications/persistence/3.1/jakarta-persistence-spec-3.1).

**Hỏi đào sâu: “ManyToMany có luôn phù hợp?”** Nếu quan hệ user–role có thêm `assignedAt`, `assignedBy` hoặc thời hạn hiệu lực, nên mô hình hóa `UserRole` thành entity riêng. Bảng nối có thông tin nghiệp vụ không còn chỉ là chi tiết liên kết.

### 5.2. Persistence context, dirty checking, flush và save

**Trả lời phỏng vấn:**

> “Persistence context quản lý các entity đang làm việc và duy trì danh tính của chúng trong phạm vi context. Khi em sửa một entity managed trong transaction, provider theo dõi thay đổi và đồng bộ xuống DB lúc flush. Vì vậy không phải mọi lần sửa entity managed đều cần gọi save lại.”

| Trạng thái | Ý nghĩa |
| --- | --- |
| New/transient | Object mới, chưa được quản lý và chưa có bản ghi tương ứng theo vòng đời persistence |
| Managed | Gắn với persistence context, thay đổi được theo dõi |
| Detached | Từng managed nhưng đã rời context; thay đổi không tự đồng bộ |
| Removed | Được đánh dấu xóa, sẽ đồng bộ xóa theo vòng đời transaction |

**Các khác biệt dễ nhầm:**

- `persist()` đưa entity mới vào quản lý; thời điểm SQL chạy còn phụ thuộc chiến lược ID và flush.
- `merge()` chép trạng thái vào một instance managed và trả instance đó; object truyền vào không tự trở thành managed.
- Spring Data JPA `save()` chọn `persist()` hoặc `merge()` dựa trên cách xác định entity mới, không phải đơn giản “có ID là chắc chắn UPDATE”.
- `flush()` đồng bộ các thay đổi đang chờ với database; **flush không phải commit**. Transaction vẫn có thể rollback sau flush.
- `saveAndFlush()` không tự bảo đảm transaction ngoài đã commit.
- Persistence context không phải cache dùng chung tự động cho mọi request. Second-level cache là cơ chế khác, cần cấu hình và đánh giá tính nhất quán.

**Hỏi đào sâu: “Vì sao sửa entity mà không thấy UPDATE?”** Kiểm tra entity còn managed không, có transaction phù hợp không, flush mode/read-only, thay đổi có thật sự khác không và code có đang thao tác với DTO/bản detached không. Quan sát SQL thay vì đoán.

### 5.3. LAZY, EAGER và lỗi N+1

**Trả lời phỏng vấn:**

> “LAZY cho phép trì hoãn tải quan hệ, EAGER yêu cầu quan hệ được tải sẵn theo hợp đồng fetch; EAGER không đảm bảo chỉ có một SQL JOIN. N+1 xảy ra khi query danh sách xong, mỗi phần tử lại phát sinh query phụ. Em kiểm tra SQL rồi chọn fetch join, entity graph, DTO projection hoặc batch fetching theo nhu cầu API.”

Theo mapping JPA 3.1, `OneToMany`/`ManyToMany` mặc định LAZY, `ManyToOne`/`OneToOne` mặc định EAGER. LAZY là hint trong đặc tả; kết quả thực tế còn phụ thuộc provider và mapping. Khai báo fetch rõ ở quan hệ quan trọng và kiểm chứng bằng SQL.

```text
1 query lấy 20 đơn
+ tối đa 20 query riêng lấy user của từng đơn nếu dữ liệu cần tải chưa có
= ví dụ 21 query thay vì một chiến lược tải phù hợp
```

Số query thực tế có thể ít hơn nếu nhiều đơn chung user hoặc có batch/cache. EAGER cũng có thể tạo các secondary select gây N+1.

Ví dụ query chi tiết một đơn và các dòng hàng:

```java
@Query("""
    select distinct o
    from Order o
    left join fetch o.items
    where o.id = :id and o.user.id = :userId
    """)
Optional<Order> findDetail(@Param("id") Long id,
                           @Param("userId") Long userId);
```

Query chỉ fetch `items`; nếu DTO cần `item.product.name`, phải có kế hoạch tải tiếp product hoặc dùng projection phù hợp. `distinct` không loại bỏ chi phí dữ liệu lặp trên đường truyền của một JOIN lớn.

**Không chữa N+1 bằng cách đổi mọi quan hệ sang EAGER.** Điều đó có thể tải thừa, tăng secondary select hoặc tạo tích số dòng khi fetch nhiều collection.

**LazyInitializationException trong Hibernate:** thường xuất hiện khi truy cập một quan hệ chưa khởi tạo sau khi session/context phù hợp đã đóng. Cách giải quyết là tải đúng dữ liệu và map DTO trong ranh giới service/transaction; không mở transaction dài hoặc bật Open Session in View chỉ để che mọi lỗi tải dữ liệu.

### 5.4. Repository là gì?

**Trả lời phỏng vấn:**

> “Repository là abstraction cho truy cập dữ liệu. Trong Spring Data JPA, em khai báo interface extends JpaRepository để có các thao tác CRUD và khả năng paging/sorting liên quan, rồi thêm derived query hoặc @Query. Spring tạo implementation/proxy ở runtime; nghiệp vụ nhiều bước vẫn đặt trong service.”

```java
public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByIdAndUserId(Long id, Long userId);

    Page<Order> findByUserId(Long userId, Pageable pageable);

    boolean existsByIdAndUserId(Long id, Long userId);
}
```

Trong model có thuộc tính `user`, Spring Data có thể phân tích `UserId` thành đường dẫn `user.id`. Có thể viết `findByIdAndUser_Id` để làm rõ nếu tên thuộc tính gây nhập nhằng.

**Hỏi đào sâu:**

- **Derived query:** Spring Data phân tích tên method để tạo query; tên quá dài thì `@Query`, Specification hoặc query builder có thể rõ hơn.
- **`findById()` khác `getReferenceById()`?** `findById()` tìm và trả `Optional`; `getReferenceById()` lấy tham chiếu mà việc tải có thể bị trì hoãn. Lỗi không tồn tại có thể xuất hiện khi truy cập hoặc flush, không nên dùng nó như phép kiểm tra tồn tại.
- **`existsBy...` rồi `save()` có đủ chống trùng?** Không, hai request có thể cùng thấy chưa tồn tại. Unique constraint là lớp bảo vệ cuối; chuyển lỗi constraint thành response phù hợp.
- **Update/delete bằng query?** Dùng `@Modifying` cùng transaction thích hợp. Bulk DML có thể làm entity managed bị cũ và không chạy vòng đời như sửa từng entity; xử lý flush/clear hoặc reload có chủ đích.

### 5.5. JPQL khác SQL thế nào?

**Trả lời phỏng vấn:**

> “JPQL truy vấn theo entity và tên thuộc tính Java, còn SQL theo bảng và cột. Provider chuyển JPQL sang SQL phù hợp database. Với truy vấn đặc thù DB hoặc cần kiểm soát cụ thể, em có thể dùng native SQL nhưng phải cân nhắc tính di động và mapping kết quả.”

```java
@Query("""
    select o from Order o
    where o.user.id = :userId and o.status = :status
    order by o.createdAt desc, o.id desc
    """)
List<Order> findOrders(@Param("userId") Long userId,
                       @Param("status") OrderStatus status);
```

`Order`, `user`, `createdAt` là entity/thuộc tính; bảng có thể là `orders` với cột `user_id`, `created_at`. JPA entity name mặc định theo tên class nhưng có thể đổi bằng annotation.

```java
@Query(value = """
    select * from orders
    where user_id = :userId and status = :status
    """, nativeQuery = true)
List<Order> findOrdersNative(@Param("userId") Long userId,
                             @Param("status") String status);
```

Ví dụ native giả định enum được lưu dạng chuỗi và mapping cột đầy đủ. Query trả danh sách trên chỉ minh họa cú pháp; endpoint có thể trả nhiều dữ liệu nên thêm phân trang/giới hạn thực tế.

**Hỏi đào sâu: “JOIN khác JOIN FETCH trong JPQL?”** JOIN dùng quan hệ để lọc/kết hợp kết quả; FETCH JOIN còn yêu cầu tải quan hệ vào entity kết quả. Không cho rằng một JOIN dùng trong `WHERE` đã khởi tạo collection cho lần truy cập sau.

**SQL injection:** bind tham số thay vì nối trực tiếp input vào JPQL/SQL. Tên cột và hướng sort thường không bind giống giá trị; cần whitelist nếu cho client chọn. Dùng JPA không tự bảo vệ code tự nối chuỗi query.

Ví dụ derived query, `@Query` và entity graph được mô tả tại [Spring Data JPA: Query Methods](https://docs.spring.io/spring-data/jpa/reference/jpa/query-methods.html).

### 5.6. Pagination: Page, Slice và keyset

**Trả lời phỏng vấn:**

> “Phân trang giới hạn lượng dữ liệu trả mỗi lần. Page phù hợp khi cần tổng số phần tử và tổng số trang, thường phát sinh count query. Slice chỉ cho biết còn phần tiếp theo, thường không cần đếm tổng. Với trang rất sâu hoặc feed liên tục, em cân nhắc keyset pagination theo bộ khóa có thứ tự ổn định.”

```java
int safeSize = Math.max(1, Math.min(requestedSize, 100));
int safePage = Math.max(0, requestedPage);
Pageable pageable = PageRequest.of(
    safePage,
    safeSize,
    Sort.by(Sort.Order.desc("createdAt"), Sort.Order.desc("id"))
);
Page<Order> page = orderRepository.findByUserId(currentUserId, pageable);
```

Page index bắt đầu từ `0`. Có thể chọn từ chối input ngoài giới hạn thay vì tự clamp như ví dụ; phải mô tả rõ trong hợp đồng API. DTO response nên biểu diễn `content`, `page`, `size` và metadata cần thiết thay vì phụ thuộc ngẫu nhiên vào JSON nội bộ của framework.

| Cách | Ưu điểm | Đánh đổi |
| --- | --- | --- |
| Offset + Page | Dễ nhảy tới số trang, có tổng | Count có thể đắt; offset lớn cần bỏ qua nhiều dòng |
| Offset + Slice | Tránh đếm tổng trong trường hợp thông thường | Offset sâu vẫn tốn chi phí |
| Keyset/cursor | Đi tiếp từ khóa cuối, hiệu quả khi index phù hợp | Khó nhảy tới trang bất kỳ, cần thiết kế cursor và sort |

Ví dụ keyset cho các trang tiếp theo theo thứ tự giảm dần:

```sql
SELECT id, created_at, status
FROM orders
WHERE user_id = :userId
  AND (
      created_at < :lastCreatedAt
      OR (created_at = :lastCreatedAt AND id < :lastId)
  )
ORDER BY created_at DESC, id DESC
LIMIT :size;
```

`created_at` và `id` nên không null; cursor chứa đủ bộ khóa và phải được kiểm tra. Luôn giữ điều kiện user/quyền ở server. Keyset giảm vấn đề dịch chuyển offset khi có insert mới, nhưng không tự tạo một snapshot bất biến nếu dữ liệu/sort key được sửa giữa các request.

**Bẫy: phân trang cùng fetch join collection.** Một đơn có nhiều dòng nên `LIMIT` theo dòng SQL không tương đương giới hạn số đơn. Hibernate có thể phải phân trang trong bộ nhớ hoặc báo lỗi tùy cấu hình. Hướng giải quyết là page ID của đơn trước, rồi fetch dữ liệu theo tập ID và khôi phục thứ tự, hoặc dùng DTO/query tách phù hợp. Tránh fetch join collection trong query phân trang nếu chưa kiểm chứng SQL và hành vi provider. Xem [Hibernate: Association Fetching](https://docs.hibernate.org/orm/6.6/querylanguage/html_single/#join-fetch).

<a id="s6"></a>
## 6. Security

### 6.1. Authentication: xác minh danh tính

**Trả lời phỏng vấn:**

> “Với đăng nhập bằng mật khẩu, server tra tài khoản và dùng PasswordEncoder để so mật khẩu gửi lên với password hash đã lưu. Nếu thông tin và trạng thái tài khoản hợp lệ, hệ thống thiết lập session hoặc cấp token theo thiết kế. Với các request sau dùng JWT, server xác minh token rồi tạo thông tin principal để những bước tiếp theo kiểm tra quyền.”

Luồng đăng nhập minh họa:

```text
POST /auth/login
→ kiểm tra định dạng và giới hạn số lần thử
→ tra user theo định danh đã chuẩn hóa
→ PasswordEncoder.matches(rawPassword, storedHash)
→ kiểm tra trạng thái tài khoản/chính sách đăng nhập
→ cấp access token và refresh token theo thiết kế
```

Lưu password hash bằng thuật toán chuyên dụng cho mật khẩu như BCrypt/Argon2 theo cấu hình phù hợp; không lưu plaintext hay “mã hóa Base64”. Không so bằng cách tự encode lại rồi so chuỗi vì thuật toán có salt. Thông báo đăng nhập thất bại nên tránh tiết lộ chi tiết không cần thiết về tài khoản.

Trong Spring Security, `Authentication` chứa principal, authorities và trạng thái xác thực. Với luồng phù hợp, `AuthenticationManager` ủy quyền cho `AuthenticationProvider`; `SecurityContext` giữ thông tin xác thực cho quá trình xử lý hiện tại. Không tự đánh dấu request là đã xác thực chỉ vì có chuỗi token.

### 6.2. Authorization: kiểm tra hành động và tài nguyên

**Trả lời phỏng vấn:**

> “Em kiểm tra quyền ở cả mức chức năng và tài nguyên. Role ADMIN có thể được phép quản lý sản phẩm, còn người dùng thông thường chỉ đọc được đơn thuộc về mình. Em đặt kiểm tra ở backend; ẩn nút trên frontend chỉ cải thiện giao diện, không bảo vệ API.”

Ví dụ lỗi **IDOR/BOLA**: user A đổi `/orders/10` thành `/orders/11` và đọc được đơn của B vì backend chỉ kiểm tra đăng nhập.

Một cách giới hạn truy cập đơn của người dùng:

```java
Order order = orderRepository.findByIdAndUserId(orderId, currentUserId)
    .orElseThrow(() -> new OrderNotFoundException(orderId));
```

`currentUserId` được lấy từ danh tính đã xác thực, không phải request body. Nếu admin có quyền xem đơn của người khác, cần nhánh chính sách rõ ràng; không bỏ mọi điều kiện chủ sở hữu cho tất cả tài khoản.

Với method security có thể dùng:

```java
@Configuration
@EnableMethodSecurity
class MethodSecurityConfig {}

// Đặt trên public method của service được gọi qua Spring proxy.
@PreAuthorize("hasAuthority('product:write')")
public void updateProduct(Long productId, UpdateProductRequest request) {
    // Kiểm tra và cập nhật sản phẩm.
}
```

Method security cần được bật và lời gọi cần đi qua cơ chế interception phù hợp; không coi annotation như một kiểm tra Java luôn chạy khi gọi nội bộ cùng object.

### 6.3. JWT là gì? Có được mã hóa không?

**Trả lời phỏng vấn:**

> “JWT là định dạng biểu diễn tập claim. Dạng signed JWT thường gặp gồm header, payload và signature. Chữ ký giúp kiểm tra tính toàn vẹn và nguồn phát hành khi xác minh với khóa tin cậy; header và payload chỉ được Base64URL encode, không được giữ bí mật. JWT không tự bảo đảm phân quyền hoặc khả năng thu hồi.”

```text
base64url(header).base64url(payload).signature
```

Ba phần trên mô tả dạng JWS compact phổ biến. JWT được mã hóa theo JWE có cấu trúc khác; không phát biểu mọi JWT luôn có đúng ba phần.

| Claim | Ý nghĩa điển hình |
| --- | --- |
| `sub` | Subject, danh tính đối tượng mà token nói tới |
| `iss` | Issuer, bên phát hành |
| `aud` | Audience, bên nhận dự kiến |
| `exp` | Thời điểm hết hạn |
| `nbf` | Không được chấp nhận trước thời điểm này |
| `iat` | Thời điểm phát hành; không thay thế kiểm tra `exp` |
| `jti` | ID của token, có thể hỗ trợ theo dõi/thu hồi theo thiết kế |

**Khi nhận token:**

1. Chỉ chấp nhận thuật toán đã cấu hình, xác minh chữ ký với khóa tin cậy.
2. Kiểm tra issuer, audience, thời hạn và các điều kiện claim theo hợp đồng.
3. Ánh xạ principal/quyền từ claim tin cậy.
4. Kiểm tra quyền với hành động và tài nguyên yêu cầu.

Decode payload để đọc JSON **không phải** verify token. Không đưa mật khẩu, secret hoặc dữ liệu nhạy cảm không cần thiết vào payload. Bearer token bị đánh cắp có thể được sử dụng như người sở hữu nó cho đến khi hết hiệu lực theo cơ chế hệ thống.

**Hỏi đào sâu: “JWT giúp stateless hoàn toàn không?”** Server có thể xác minh chữ ký mà không tra session cho mỗi request. Tuy nhiên refresh token, thu hồi, khóa tài khoản, thay role ngay lập tức hoặc chống replay vẫn có thể cần state. JWT là một lựa chọn có đánh đổi, không phải luôn tốt hơn session.

### 6.4. Cấu hình Resource Server trong Spring Security

Ví dụ rút gọn để **xác minh access token** cho API:

```java
@Configuration
class SecurityConfig {
    @Bean
    SecurityFilterChain apiSecurity(HttpSecurity http) throws Exception {
        http
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.GET, "/api/products/**").permitAll()
                .requestMatchers("/api/admin/**").hasAuthority("SCOPE_admin")
                .anyRequest().authenticated())
            .oauth2ResourceServer(oauth -> oauth.jwt(Customizer.withDefaults()));
        return http.build();
    }
}
```

Cần dependency Resource Server/Jose và cấu hình `JwtDecoder` hoặc issuer/JWK tương ứng. Thiết lập kiểm tra audience theo phiên bản và hợp đồng token. Resource Server xác minh token, không tự cung cấp đầy đủ endpoint đăng nhập/phát token/refresh. Mapping scope thông dụng thêm tiền tố `SCOPE_`, nên ví dụ dùng `SCOPE_admin`; claim `roles` tùy chỉnh cần converter riêng nếu muốn thành `ROLE_ADMIN`. Xem [Spring Security: JWT Resource Server](https://docs.spring.io/spring-security/reference/servlet/oauth2/resource-server/jwt.html).

Ví dụ giữ CSRF theo mặc định, nên request thay đổi dữ liệu có thể cần CSRF token. Với API thuần dùng bearer token trong `Authorization` do client chủ động gắn, không có cookie hoặc cơ chế credential tự gửi, có thể cấu hình bỏ qua CSRF cho phạm vi phù hợp sau khi xác định mô hình xác thực. Nếu access/refresh credential nằm trong cookie, vẫn cần thiết kế chống CSRF; `STATELESS` không tự loại bỏ nguy cơ này. Xem [Spring Security: CSRF](https://docs.spring.io/spring-security/reference/servlet/exploits/csrf.html).

**CORS khác CSRF:** CORS là cơ chế trình duyệt kiểm soát việc code từ origin khác truy cập response theo chính sách server. CSRF là việc lợi dụng credential trình duyệt tự gửi để gây hành động ngoài ý muốn. CORS không thay thế xác thực, phân quyền hoặc bảo vệ CSRF.

### 6.5. Access token khác Refresh token thế nào?

**Trả lời phỏng vấn:**

> “Access token được gửi tới API để truy cập tài nguyên, thường có thời hạn ngắn. Refresh token gửi tới endpoint cấp token để xin access token mới và thường sống lâu hơn. Em quản lý refresh token theo phiên, có expiry, thu hồi và rotation; không dùng nó thay access token khi gọi API nghiệp vụ.”

| Tiêu chí | Access token | Refresh token |
| --- | --- | --- |
| Nơi dùng | Resource API | Authorization server hoặc endpoint refresh |
| Mục đích | Chứng minh quyền truy cập được cấp | Xin token mới trong phạm vi được phép |
| Thời hạn | Thường ngắn | Thường dài hơn, tùy chính sách |
| Định dạng | JWT hoặc opaque | Opaque hoặc JWT; không bắt buộc là JWT |
| Rủi ro lộ | Kẻ cầm token có thể gọi API theo quyền token | Có thể tiếp tục xin token mới nếu chưa bị chặn |

Ví dụ thời hạn 10 phút cho access và 7 ngày cho refresh chỉ là giá trị minh họa, không phải chuẩn bắt buộc.

**Luồng refresh rotation minh họa:**

1. Client gửi refresh token tới endpoint refresh qua HTTPS.
2. Server kiểm tra token còn hiệu lực, chưa bị thu hồi và đúng phiên/client theo thiết kế.
3. Trong thao tác nguyên tử, vô hiệu hóa refresh token cũ và ghi token mới.
4. Cấp access token mới và refresh token mới; client thay token cũ.
5. Nếu token cũ xuất hiện lại, xử lý như dấu hiệu replay theo chính sách, có thể thu hồi cả token family và yêu cầu đăng nhập lại.

Cần xử lý cả trường hợp nhiều tab hoặc request đồng thời refresh để tránh nhầm retry hợp lệ với tấn công. Với public client trong OAuth, RFC 9700 yêu cầu cơ chế phát hiện replay bằng refresh token rotation hoặc token ràng buộc với bên gửi. Xem [RFC 9700, mục 4.14](https://www.rfc-editor.org/rfc/rfc9700.html#section-4.14).

**Lưu token ở đâu?**

- Cookie `HttpOnly`, `Secure`, `SameSite` phù hợp giúp hạn chế JavaScript đọc trực tiếp và kiểm soát việc gửi cookie, nhưng cần thiết kế CSRF và luồng cross-site.
- `localStorage` có thể bị JavaScript độc hại cùng origin đọc khi có XSS; không coi nó là mặc định an toàn cho token dài hạn.
- Access token trong bộ nhớ giảm lưu tồn tại lâu dài nhưng mất khi reload và không miễn nhiễm XSS.
- Với refresh token opaque ngẫu nhiên có entropy cao, có thể lưu hash phía server để đối chiếu, kèm metadata phiên; không log token thô.

Lựa chọn phụ thuộc web SPA, mobile, server-rendered app hoặc BFF. Không có một vị trí lưu tối ưu cho mọi loại client.

**Hỏi đào sâu: “Logout rồi access JWT còn dùng được không?”**

Có thể vẫn dùng được đến `exp` nếu API chỉ verify chữ ký/thời hạn và không kiểm tra trạng thái thu hồi. Xóa token ở client không hủy bản sao đã bị lấy. Thu hồi refresh token ngăn cấp mới; muốn chặn access sớm cần cơ chế như denylist theo `jti`, version phiên hoặc introspection, đổi lại phải kiểm tra state. Thay role trong DB cũng không tự sửa claim trong JWT đã phát hành.

### 6.6. Role, authority và scope

**Trả lời phỏng vấn:**

> “Role biểu diễn một nhóm trách nhiệm như ADMIN hay CUSTOMER. Authority biểu diễn quyền mà Spring Security dùng để kiểm tra, có thể chi tiết như product:write. Scope thường là phạm vi quyền được cấp cho access token trong OAuth. Em phân biệt nhóm quyền, quyền cụ thể và quyền trên từng tài nguyên.”

| Khái niệm | Ví dụ | Lưu ý |
| --- | --- | --- |
| Role | `ADMIN`, `CUSTOMER` | Nhóm quyền theo chính sách ứng dụng |
| Authority | `product:write`, `order:read` | Chuỗi quyền để kiểm tra; cách đặt tên do thiết kế |
| Scope | `orders.read` | Quyền được cấp trong ngữ cảnh token/client |
| Ownership | `order.userId == currentUserId` | Kiểm tra tài nguyên cụ thể, role không tự bao hàm |

Theo quy ước mặc định, `hasRole("ADMIN")` kiểm tra authority `ROLE_ADMIN`; `hasAuthority("ROLE_ADMIN")` kiểm tra đúng chuỗi đó. Không dùng `hasRole("ROLE_ADMIN")` như một quy tắc chung. Role hierarchy, claim mapping hoặc prefix tùy chỉnh cần cấu hình rõ; tên `ADMIN` không tự khiến mọi kiểm tra quyền khác thành công.

<a id="s7"></a>
## 7. Test: JUnit, Mockito, Controller và Service

### 7.1. JUnit là gì? Một test tốt kiểm tra gì?

**Trả lời phỏng vấn:**

> “JUnit là nền tảng/framework kiểm thử trong hệ sinh thái Java; với JUnit Jupiter em viết test bằng @Test, dùng assertion để kiểm tra kết quả và lifecycle hook để chuẩn bị dữ liệu. Test tốt mô tả hành vi mong đợi, chạy độc lập và giúp phát hiện sai sót khi thay đổi code.”

| Thành phần Jupiter | Mục đích |
| --- | --- |
| `@Test` | Đánh dấu test method |
| `@BeforeEach`, `@AfterEach` | Chuẩn bị/dọn dẹp cho từng test |
| `@BeforeAll`, `@AfterAll` | Chuẩn bị/dọn dẹp ở mức class theo lifecycle phù hợp |
| `@ParameterizedTest` | Chạy một hành vi với nhiều đầu vào |
| `assertEquals`, `assertTrue` | Kiểm tra kết quả |
| `assertThrows` | Kiểm tra hành vi ném exception |
| `@ExtendWith` | Đăng ký extension, ví dụ Mockito extension |

Cấu trúc **Arrange–Act–Assert**: chuẩn bị trạng thái → gọi hành vi → kiểm tra kết quả. Có thể dùng cách gọi tương đương Given–When–Then. Xem cú pháp tại [JUnit 5 User Guide](https://docs.junit.org/5.11.0/user-guide/).

```java
class OrderTest {
    @Test
    void cancel_newOrder_changesStatusToCancelled() {
        Order order = new Order();

        order.cancel();

        assertEquals(OrderStatus.CANCELLED, order.getStatus());
    }

    @Test
    void cancel_alreadyCancelledOrder_throwsException() {
        Order order = new Order();
        order.cancel();

        assertThrows(IllegalStateException.class, order::cancel);
    }
}
```

Test này dùng đúng class `Order` thuần ở mục 2.1. Với entity bản đầy đủ có thêm constructor/factory nghiệp vụ, tạo fixture qua API tương ứng.

**Không chỉ test happy path.** Cần chọn trường hợp theo quy tắc: dữ liệu thiếu/sai, không tồn tại, không đủ quyền, trạng thái không cho phép, phụ thuộc lỗi và xung đột đồng thời ở tầng thích hợp.

### 7.2. Mockito là gì? Mock khác Spy?

**Trả lời phỏng vấn:**

> “Mockito tạo test double để em kiểm tra một đơn vị code mà không phải chạy database hoặc dịch vụ ngoài. Em stub kết quả của dependency bằng when–thenReturn và dùng verify khi tương tác với dependency là một phần hành vi cần bảo đảm. Em vẫn chạy code thật của class đang test.”

| Khái niệm | Ý nghĩa |
| --- | --- |
| Mock | Đối tượng giả, mặc định trả giá trị mặc định cho phần chưa stub |
| Stub | Cách cung cấp kết quả định trước cho một lời gọi trong test |
| Spy | Bọc/quan sát đối tượng thật, thường gọi implementation thật nếu chưa stub |
| `ArgumentCaptor` | Bắt tham số truyền tới mock để kiểm tra nội dung |
| `verify()` | Kiểm tra một tương tác đã hoặc chưa xảy ra |

Ví dụ:

```java
when(productRepository.findById(99L)).thenReturn(Optional.empty());
verify(productRepository).findById(99L);
```

Với spy, `when(spy.method()).thenReturn(...)` có thể thực sự gọi method khi stub; `doReturn(...).when(spy).method()` tránh điều đó trong tình huống cần thiết. Không lạm dụng spy để che một thiết kế khó kiểm thử.

`@Mock` tạo mock cho test; nó không tự thay bean trong Spring context. `@InjectMocks` hỗ trợ tạo đối tượng và inject mock theo cơ chế Mockito, không khởi động Spring hay áp dụng `@Transactional`. Khi setup ít dependency, constructor trực tiếp thường rõ hơn.

### 7.3. Service test: kiểm tra nghiệp vụ độc lập

Ví dụ service và các kiểu dữ liệu tối giản dành riêng cho phần test:

```java
record Product(Long id, String name) {}
record ProductSummary(Long id, String name) {}

interface ProductRepository {
    Optional<Product> findById(Long id);
}

class ProductQueryService {
    private final ProductRepository repository;

    ProductQueryService(ProductRepository repository) {
        this.repository = repository;
    }

    ProductSummary get(Long id) {
        Product product = repository.findById(id)
            .orElseThrow(() -> new ProductNotFoundException(id));
        return new ProductSummary(product.id(), product.name());
    }
}
```

`Product` record ở đây là fixture đơn giản để giải thích test, **không phải JPA entity** thay thế mapping `Product` trong project thực tế. `ProductNotFoundException` dùng định nghĩa ở mục 2.3. Khi ghép code vào project, dùng model hiện có thay cho fixture cùng tên.

```java
@ExtendWith(MockitoExtension.class)
class ProductQueryServiceTest {
    @Mock
    ProductRepository repository;

    ProductQueryService service;

    @BeforeEach
    void setUp() {
        service = new ProductQueryService(repository);
    }

    @Test
    void get_existingProduct_returnsSummary() {
        when(repository.findById(99L))
            .thenReturn(Optional.of(new Product(99L, "Keyboard")));

        ProductSummary result = service.get(99L);

        assertEquals(new ProductSummary(99L, "Keyboard"), result);
    }

    @Test
    void get_missingProduct_throwsDomainException() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> service.get(99L));
    }
}
```

Test dùng static import từ `org.junit.jupiter.api.Assertions` và `org.mockito.Mockito`, cùng `org.mockito.junit.jupiter.MockitoExtension`. Dependency test cần JUnit Jupiter và Mockito/Jupiter integration theo version được project quản lý.

**Với service tạo đơn, cần kiểm tra thêm:**

- Tính đúng tổng từ giá server và số lượng hợp lệ, không lấy giá client làm nguồn chuẩn.
- Từ chối sản phẩm không tồn tại, số lượng sai hoặc trạng thái không cho mua.
- Không ghi đơn khi bước kiểm tra bắt buộc thất bại.
- Không cho người dùng tác động đơn không thuộc quyền của họ.
- Dùng `Clock` được inject để kiểm tra thời hạn mà không phụ thuộc giờ thực.

**Giới hạn:** mock repository không chứng minh SQL đúng, constraint tồn tại, transaction rollback hoặc optimistic locking hoạt động. Những tính chất đó cần integration test với Spring proxy và database phù hợp.

### 7.4. Controller test: kiểm tra hợp đồng HTTP

**Trả lời phỏng vấn:**

> “Controller test kiểm tra route, HTTP status, JSON, binding, validation và cách chuyển exception sang response. Em mock service để cô lập tầng web. Khi kiểm tra security hoặc cấu hình ứng dụng, em dùng test context có filter chain thực tế thay vì suy từ một test controller đơn lẻ.”

Controller minh họa, dùng service ở mục 7.3:

```java
@RestController
@RequestMapping("/api/products")
class ProductController {
    private final ProductQueryService service;

    ProductController(ProductQueryService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    ProductSummary get(@PathVariable("id") Long id) {
        return service.get(id);
    }
}
```

Ví dụ MockMvc standalone dùng advice ở mục 2.3:

```java
@ExtendWith(MockitoExtension.class)
class ProductControllerTest {
    @Mock
    ProductQueryService service;

    MockMvc mvc;

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders
            .standaloneSetup(new ProductController(service))
            .setControllerAdvice(new ApiExceptionHandler())
            .build();
    }

    @Test
    void get_existingProduct_returns200AndJson() throws Exception {
        when(service.get(99L)).thenReturn(new ProductSummary(99L, "Keyboard"));

        mvc.perform(get("/api/products/99"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(99))
            .andExpect(jsonPath("$.name").value("Keyboard"));
    }

    @Test
    void get_missingProduct_returns404AndErrorCode() throws Exception {
        when(service.get(99L)).thenThrow(new ProductNotFoundException(99L));

        mvc.perform(get("/api/products/99"))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.code").value("PRODUCT_NOT_FOUND"));
    }
}
```

Static import `get` từ `MockMvcRequestBuilders`, `status`/`jsonPath` từ `MockMvcResultMatchers`. Cần Spring MVC test và JSON converter phù hợp, thường có trong bộ dependency test/web của ứng dụng Boot.

MockMvc chạy qua hạ tầng Spring MVC mà không mở HTTP server thật. Bản standalone chỉ có những thành phần đã đăng ký; ví dụ trên **không kiểm tra Spring Security hoặc toàn bộ cấu hình Boot**. Có thể dùng `@WebMvcTest` cho web slice, đăng ký mock service vào context bằng annotation/cấu hình mà phiên bản project hỗ trợ, rồi nạp security config cần kiểm tra. Xem [Spring: MockMvc](https://docs.spring.io/spring-framework/reference/testing/mockmvc.html).

**Các ca web/security có giá trị:**

| Ca | Kỳ vọng |
| --- | --- |
| Request JSON hợp lệ | Status và JSON đúng hợp đồng |
| Thiếu field bắt buộc, `quantity = 0`, phần tử null | Lỗi validation; không gọi nghiệp vụ tạo đơn |
| Không có credential ở endpoint yêu cầu đăng nhập | `401` theo cấu hình API |
| Đã đăng nhập nhưng không đủ authority | `403` theo chính sách |
| User A đọc đơn của B | Bị từ chối hoặc trả `404` theo thiết kế |
| JWT hết hạn/sai chữ ký/sai audience | Bị từ chối ở tầng xác minh token |
| POST có cookie credential thiếu CSRF token | Bị chặn theo cấu hình chống CSRF |

Mock principal bằng tiện ích test JWT chỉ kiểm tra hành vi sau khi có authentication giả lập; nó không chứng minh chữ ký token thật được xác minh. Test decoder/filter với token thật hoặc trường hợp token lỗi khi cần kiểm tra phần đó.

### 7.5. Phân biệt các mức test

| Mức | Ví dụ | Điều không nên suy ra |
| --- | --- | --- |
| Unit test | JUnit + Mockito cho service | Không chứng minh SQL, proxy transaction hay DB constraint |
| Web slice/controller test | MockMvc, service mock | Không chứng minh toàn bộ luồng persistence |
| Repository integration test | `@DataJpaTest` với database phù hợp | Không kiểm tra toàn bộ HTTP/security |
| Application integration test | `@SpringBootTest` với cấu hình test | Mức bao phủ phụ thuộc dependency nào vẫn bị mock |
| End-to-end | Client gọi ứng dụng và các thành phần tích hợp | Chậm và khó chẩn đoán hơn, nên chọn luồng quan trọng |

Với hành vi phụ thuộc dialect, lock, index hoặc native query, ưu tiên test trên cùng loại DB với triển khai thực tế; Testcontainers là một cách cung cấp môi trường đó nếu hạ tầng hỗ trợ.

**“Test có `@Transactional` thì chứng minh rollback nghiệp vụ chưa?”** Chưa chắc. Transaction của test có thể bọc service, che mất việc service thiếu annotation; lỗi constraint cũng có thể chỉ xuất hiện lúc flush. Cần thiết kế test để transaction nghiệp vụ kết thúc đúng ranh giới, rồi đọc lại dữ liệu từ context/transaction mới. Khi kiểm tra concurrency, phải dùng các transaction/connection riêng và điều phối tình huống xung đột có chủ đích.

**“Coverage 100% có nghĩa không còn bug?”** Không. Coverage chỉ cho biết phần code nào đã chạy theo loại metric; assertion có thể sai hoặc thiếu, và còn hành vi dữ liệu/thời gian/đồng thời chưa được kiểm tra. Ưu tiên test quy tắc và rủi ro quan trọng thay vì chỉ tăng phần trăm.

<a id="s8"></a>
## 8. Bảo vệ project trong phỏng vấn

### 8.1. “Em làm module nào?”

**Nhà tuyển dụng muốn biết:** phạm vi đóng góp cá nhân, mức hiểu luồng từ đầu đến cuối và khả năng phân biệt phần mình làm với phần của team.

**Khung trả lời:** bối cảnh → trách nhiệm → luồng xử lý → khó khăn → kiểm chứng/kết quả.

**Mẫu minh họa:**

> “Trong project bán hàng của nhóm, em phụ trách module đơn hàng: tạo đơn, xem danh sách và hủy đơn. Em thiết kế bảng orders/order_items, viết API và service xử lý trạng thái. Khi tạo đơn, service lấy user từ danh tính đã xác thực, kiểm tra sản phẩm, tính giá ở server, cập nhật tồn kho và lưu đơn trong transaction. Em cũng viết test cho việc không đủ tồn và không cho người dùng đọc đơn người khác. Phần tích hợp cổng thanh toán do bạn khác phụ trách; em làm phần nhận kết quả theo hợp đồng giữa hai module.”

Chỉ giữ các chi tiết thực sự đã làm. Nếu project chưa có kiểm soát concurrency hoặc test security, nói rõ hiện trạng rồi trình bày cách dự định bổ sung.

**Phải trả lời tiếp được:**

- Endpoint nào, request/response gồm gì, lỗi nào trả mã gì?
- Class nào chứa nghiệp vụ chính? Dữ liệu đi qua những lớp nào?
- Những bảng/cột/index nào do mình thiết kế, tại sao?
- Transaction bắt đầu/kết thúc ở đâu?
- Ai được gọi API và ai được thao tác tài nguyên cụ thể?
- Test nào chứng minh yêu cầu quan trọng đã được đáp ứng?

Tránh “em làm backend” hoặc “em làm toàn bộ” nếu không giải thích được chi tiết và phạm vi team.

### 8.2. “Tại sao em chọn thiết kế này?”

**Trả lời theo:** yêu cầu/ràng buộc → các phương án → lựa chọn → đánh đổi → điều kiện xem xét lại.

**Mẫu minh họa:**

> “Với phạm vi project và nhóm nhỏ, em chọn một ứng dụng Spring Boot chia module và phân tầng controller–service–repository. Controller xử lý HTTP, service gom nghiệp vụ và transaction, repository truy cập DB. Cách này giúp triển khai và debug tương đối đơn giản. Đánh đổi là phải giữ ranh giới module bằng cấu trúc code và review; em chưa có nhu cầu triển khai độc lập từng module nên chưa tách microservice.”

| Quyết định | Lý do có thể bảo vệ | Đánh đổi cần nắm |
| --- | --- | --- |
| Constructor injection | Dependency rõ, dễ tạo service trong test | Quá nhiều dependency có thể báo hiệu service ôm nhiều trách nhiệm |
| DTO riêng | Kiểm soát dữ liệu API và tránh gắn chặt entity | Thêm mapping, cần giữ hợp đồng nhất quán |
| JPA cho phần CRUD | Giảm code mapping và thao tác persistence lặp | Cần quan sát SQL, fetch và transaction |
| Database quan hệ | Quan hệ và ràng buộc dữ liệu rõ | Schema migration và thay đổi mô hình cần quản lý |
| Modular monolith | Triển khai đơn giản, transaction nội bộ thuận tiện | Các module cần kỷ luật phụ thuộc, cùng chu kỳ deploy |
| JWT hoặc session | Chọn theo loại client và kiến trúc xác thực | JWT có bài toán thu hồi; session có bài toán lưu/chia sẻ phiên |
| Optimistic locking | Phát hiện cập nhật dựa trên version cũ | Phải xử lý xung đột/retry, không phù hợp mọi mức tranh chấp |

**Câu hỏi đào sâu:**

- **Tại sao không microservice?** Nêu nhu cầu triển khai/mở rộng độc lập có thật hay chưa; cân nhắc network failure, distributed tracing, vận hành và nhất quán dữ liệu.
- **Tại sao không cache mọi thứ?** Dữ liệu có độ thay đổi, mức chịu cũ và yêu cầu phân quyền khác nhau; cache tạo thêm bài toán invalidation.
- **Tại sao không dùng native SQL hết?** Cân bằng độ rõ query, khả năng bảo trì, năng lực team và nhu cầu DB-specific; không có quy tắc phải dùng một cách duy nhất.

### 8.3. “Nếu traffic tăng thì sao?”

**Trả lời phỏng vấn:**

> “Em sẽ đo để tìm nút thắt trước: request rate, latency p95/p99, lỗi, CPU, bộ nhớ, connection pool và query chậm. Nếu bottleneck là query, em xử lý N+1, index và dữ liệu trả dư. Nếu app server là giới hạn, em cân nhắc scale ngang khi state của ứng dụng cho phép. Cache và queue chỉ được thêm cho trường hợp có chính sách nhất quán và xử lý lỗi rõ.”

**Quy trình phân tích:**

1. **Xác định tải:** bao nhiêu request/giây, tỷ lệ đọc/ghi, endpoint nóng, lượng dữ liệu và mục tiêu latency. “10.000 user” không cho biết bao nhiêu request đồng thời.
2. **Đo baseline:** p50/p95/p99, throughput, error rate, resource usage và saturation; dùng workload gần thực tế.
3. **Tối ưu bottleneck:** sửa query/fetch, thêm index phù hợp, giảm payload, giới hạn page size, loại bỏ xử lý dư.
4. **Mở rộng có mục tiêu:** thêm instance, cache dữ liệu phù hợp, tách tác vụ nền, hoặc nâng tài nguyên DB khi có bằng chứng.
5. **Đo lại:** cùng workload và dữ liệu, xác nhận hiệu năng cải thiện mà không phá tính đúng.

| Hiện tượng | Điều cần kiểm tra | Hướng xử lý có thể phù hợp |
| --- | --- | --- |
| API danh sách chậm | Query count, execution plan, offset sâu | N+1, index, projection, keyset |
| Chờ connection DB | Pool saturation, transaction dài, query chậm | Rút ngắn transaction và tối ưu query trước khi tăng pool |
| CPU app cao | Profile code, serialize, tính toán lặp | Tối ưu đường nóng hoặc thêm instance |
| Email làm request lâu | External latency, timeout | Queue/outbox và worker với retry/idempotency |
| Đọc sản phẩm lặp nhiều | Tỷ lệ cache hit dự kiến, mức chịu dữ liệu cũ | Cache với TTL và invalidation phù hợp |
| Cập nhật tồn kho tranh chấp | Lock wait/deadlock, retry | Cập nhật có điều kiện, chiến lược khóa và thứ tự thao tác |

**Các đánh đổi phải nói được:**

- Scale từ 2 lên 10 instance cũng có thể tăng tổng connection DB gấp nhiều lần; database có thể trở thành bottleneck.
- Load balancer không sửa được query chậm hoặc race condition.
- Read replica có replication lag; không mặc định đọc được ngay dữ liệu vừa ghi.
- Cache dữ liệu riêng của user cần key/phạm vi quyền đúng, tránh trả dữ liệu người này cho người khác.
- Queue làm xử lý bất đồng bộ và có eventual consistency; cần retry giới hạn, theo dõi lỗi và consumer idempotent.
- Rate limiting bảo vệ tài nguyên nhưng cần chính sách công bằng và response rõ cho client.

Nếu chưa load test, có thể nói: “Em chưa có số đo ở mức tải đó; đây là cách em sẽ kiểm chứng.” Không bịa kết quả throughput/latency.

### 8.4. “Bug khó nhất em từng xử lý?”

**Dùng cấu trúc STAR kết hợp bằng chứng:** Situation — bối cảnh; Task — việc cần giải quyết; Action — điều tra và sửa; Result — kết quả có thể kiểm chứng.

**Tình huống minh họa: danh sách đơn càng nhiều càng chậm.**

> “Khi dữ liệu đơn tăng, API danh sách phản hồi chậm dù mỗi trang chỉ có một số lượng nhỏ đơn. Em cần xác định nó chậm ở truy vấn hay xử lý Java. Em bật log SQL trong môi trường phát triển, thấy query lấy danh sách được theo sau bởi nhiều query tải user khi map DTO. Em xác định đó là N+1. Em điều chỉnh fetch cho quan hệ cần đọc hoặc dùng projection, rồi chạy lại cùng dữ liệu để so query count và latency. Em cũng kiểm tra số phần tử và thứ tự trang để chắc việc sửa không làm sai kết quả.”

Đây là kịch bản luyện trả lời. Khi kể trải nghiệm thật, thay bằng log, commit, test và số đo mình thực sự có.

**Các bước điều tra có thể bảo vệ:**

1. Ghi triệu chứng và cách tái hiện với input cụ thể.
2. Dùng request/trace ID để nối log đúng luồng; tránh log dữ liệu nhạy cảm.
3. Lập giả thuyết và kiểm chứng bằng SQL, debugger, metrics hoặc test.
4. Tìm nguyên nhân gốc, không chỉ thêm `try/catch` để ẩn lỗi.
5. Sửa phạm vi phù hợp, thêm regression test nếu hành vi có nguy cơ tái diễn.
6. Xác minh chức năng và hiệu năng bằng điều kiện so sánh tương đương.

**Một ví dụ khác: hủy đơn nhưng dữ liệu vẫn commit.** Kiểm tra checked exception không khớp rollback rule, exception bị bắt mất, lời gọi không qua proxy hoặc transaction nằm sai tầng. Không kết luận nguyên nhân chỉ bằng việc nhìn thấy `@Transactional`; phải tái hiện đường gọi và đọc dữ liệu sau khi transaction kết thúc.

**Câu hỏi nhà tuyển dụng có thể hỏi tiếp:**

- Vì sao em loại trừ các nguyên nhân khác?
- Trước khi sửa, test có tái hiện được lỗi không?
- Em đo kết quả bằng gì, trên dữ liệu nào?
- Việc sửa có ảnh hưởng pagination, authorization hoặc transaction không?
- Làm sao phát hiện nhanh hơn nếu lỗi tương tự xảy ra?

### 8.5. “Team dùng Git như thế nào?”

**Trả lời phỏng vấn:**

> “Nhóm em làm theo nhánh tính năng và pull request. Mỗi task có phạm vi rõ, em tạo branch từ nhánh nền mà team thống nhất, commit nhỏ theo thay đổi có ý nghĩa, rồi mở PR mô tả hành vi và cách kiểm tra. Sau review và CI, PR được merge theo quy ước của nhóm. Khi có conflict, em đọc cả hai phía và kiểm tra lại nghiệp vụ sau khi giải quyết.”

Đây là quy trình mẫu; nếu team thật chỉ dùng một nhánh hoặc chưa có CI, trình bày đúng hiện trạng và điều muốn cải thiện.

**Ví dụ thao tác phát triển bình thường:**

```bash
git status
git fetch origin
git switch -c feature/order-cancellation origin/main

# Sửa code, kiểm tra diff và chạy kiểm thử phù hợp.
git diff
git add src/main/java/com/example/order/OrderService.java
git add src/test/java/com/example/order/OrderServiceTest.java
git diff --staged
git commit -m "Validate order status before cancellation"
git push -u origin feature/order-cancellation
```

Các lệnh chỉ minh họa, thay nhánh và đường dẫn theo repository thật. Đảm bảo hiểu trạng thái working tree trước khi chuyển nhánh; `git add` không nên đưa secret hoặc file IDE cá nhân vào commit.

**PR nên có:** vấn đề cần giải quyết, hành vi sau thay đổi, cách kiểm tra, migration/cấu hình cần thiết nếu có. Review tập trung vào tính đúng, bảo mật, transaction, SQL và khả năng hiểu code.

| Câu hỏi | Trả lời cần nắm |
| --- | --- |
| `fetch` khác `pull`? | Fetch cập nhật dữ liệu remote-tracking; pull fetch rồi tích hợp vào nhánh hiện tại theo cấu hình merge/rebase |
| Merge khác rebase? | Merge kết hợp lịch sử và có thể tạo merge commit; rebase phát lại commit lên nền mới, thay commit ID |
| Conflict xử lý thế nào? | Hiểu mục đích cả hai phía, sửa nội dung đúng, xóa conflict marker, stage và hoàn tất thao tác đang dở, rồi chạy kiểm tra |
| `revert` khác `reset`? | Revert tạo commit đảo thay đổi; reset di chuyển con trỏ nhánh và có thể thay index/working tree tùy mode |
| Đã push lên nhánh chung, muốn hoàn tác? | Thường dùng revert để giữ lịch sử chia sẻ; phối hợp theo quy trình team |
| Force push có rủi ro gì? | Có thể ghi đè lịch sử người khác; không làm tùy tiện với nhánh chung. Force-with-lease thêm kiểm tra kỳ vọng remote nhưng vẫn cần hiểu tác động |
| `.gitignore` có xóa secret đã commit không? | Không; nó không loại file đã tracked hoặc bí mật trong lịch sử. Secret bị lộ cần thu hồi/rotate và xử lý lịch sử theo quy trình |

Không giải quyết conflict bằng cách chọn toàn bộ “ours” hoặc “theirs” khi chưa hiểu thay đổi. “Không còn conflict marker” chưa chứng minh code đúng.

<a id="s9"></a>
## 9. Tình huống phỏng vấn tổng hợp

### 9.1. “Mô tả toàn bộ luồng tạo đơn hàng.”

**Một câu trả lời có thể bảo vệ:**

1. Security xác thực request và tạo principal từ credential hợp lệ.
2. Controller bind DTO, kiểm tra danh sách hàng không rỗng và số lượng dương.
3. Service lấy user nội bộ từ principal, kiểm tra trạng thái tài khoản và quyền liên quan.
4. Chuẩn hóa/gộp sản phẩm trùng theo hợp đồng; tải sản phẩm và giá từ server.
5. Trong transaction, kiểm tra/cập nhật tồn kho bằng cơ chế chống race đã chọn; tính tổng bằng `BigDecimal`.
6. Lưu `Order`, `OrderItem` và snapshot giá; nếu bước bắt buộc lỗi thì rollback.
7. Nếu cần phát event chắc chắn sau commit, ghi outbox cùng transaction rồi worker chuyển tiếp.
8. Trả DTO cùng `201 Created`; client retry được xử lý bằng idempotency key nếu API hỗ trợ.

**Các câu hỏi nối tiếp:**

- **Client gửi giá 1 đồng?** Bỏ qua giá không có thẩm quyền; tính theo dữ liệu server và chính sách giá.
- **Client gửi cùng product hai lần?** Gộp hoặc từ chối theo hợp đồng, không vô tình kiểm tra tồn từng dòng rồi vượt tổng.
- **Hai request mua sản phẩm cuối?** Atomic conditional update, optimistic/pessimistic locking phù hợp; kiểm tra kết quả và xử lý xung đột.
- **Commit thành công nhưng client mất kết nối?** Retry có thể tạo đơn trùng nếu không có idempotency. Khóa idempotency phải gắn phạm vi user/thao tác, có uniqueness và kiểm tra payload không bị đổi.
- **Email lỗi có hủy đơn không?** Theo nghiệp vụ thông thường có thể giữ đơn và retry email bằng worker; không gộp tùy tiện dịch vụ ngoài vào local DB transaction.

### 9.2. “API xem đơn có JWT rồi, đã an toàn chưa?”

Chưa đủ. Cần xác minh token đúng issuer/audience/thời hạn, ánh xạ quyền đúng, kiểm tra chủ sở hữu hoặc quyền quản trị và chỉ trả field được phép. Bổ sung test user A không đọc được đơn của B; kiểm tra cache không trộn dữ liệu giữa user. JWT hợp lệ chỉ là một đầu vào cho quyết định truy cập.

### 9.3. “Dùng JPA nhưng endpoint vẫn chậm, em làm gì?”

Xác định thời gian ở DB, mapping/serialization hay dịch vụ ngoài; lấy SQL thực tế, kiểm tra N+1, fetch thừa, count query đắt, index và offset sâu. Sửa đúng nguyên nhân bằng query/fetch/projection/index phù hợp, rồi đo lại. Không kết luận phải bỏ JPA chỉ vì endpoint đang chậm.

### 9.4. “Tất cả unit test xanh mà production vẫn lỗi?”

Unit test với mock không tái hiện schema, constraint, transaction proxy, filter security hoặc concurrency. Kiểm tra lớp hành vi bị bỏ sót rồi thêm integration/regression test có mục tiêu. Đồng thời so môi trường: version DB, migration, timezone, cấu hình, dữ liệu và dependency ngoài.

### 9.5. “Em không biết câu này thì trả lời sao?”

> “Phần này em chưa triển khai thực tế nên chưa thể khẳng định chi tiết. Em hiểu nó nhằm giải quyết vấn đề … Nếu được xử lý trong project, em sẽ kiểm tra tài liệu theo phiên bản, tạo ví dụ tái hiện và đo/kiểm thử … trước khi chọn phương án.”

Nêu phần mình biết, phần còn thiếu và cách kiểm chứng. Không dùng câu trả lời này để né câu cơ bản đã ghi trong CV; với công nghệ mình nhận là đã dùng, phải giải thích được luồng sử dụng thực tế.

<a id="s10"></a>
## 10. Checklist tự luyện

### 10.1. Kiến thức cần tự nói được

- [ ] Nêu bốn tính chất OOP và chỉ ra ví dụ cụ thể cho từng tính chất.
- [ ] Phân biệt overloading, overriding và composition.
- [ ] Chọn List/Set/Map/Queue theo yêu cầu; giải thích `equals`/`hashCode`.
- [ ] Phân biệt checked/unchecked exception và không nuốt lỗi.
- [ ] Chọn interface hay abstract class theo quan hệ và trách nhiệm.
- [ ] Viết INNER/LEFT JOIN và giải thích bẫy điều kiện `WHERE`.
- [ ] Giải thích index, composite index và cách đọc execution plan cơ bản.
- [ ] Trình bày ACID, isolation, rollback và race condition tồn kho.
- [ ] Vẽ bảng, PK, FK và giải thích vì sao OrderItem lưu giá lịch sử.
- [ ] Phân biệt Spring/Spring Boot, IoC/DI và object thường/bean.
- [ ] Giải thích constructor injection, nhiều bean cùng kiểu và singleton thread safety.
- [ ] Thiết kế URI, HTTP method, status code, DTO và validation.
- [ ] Phân biệt JDBC, JPA, Hibernate và Spring Data JPA.
- [ ] Giải thích owning side, `mappedBy`, cascade, fetch và N+1.
- [ ] Phân biệt managed/detached, `persist`/`merge`, flush/commit.
- [ ] Viết derived query và JPQL; phân biệt SQL/JPQL.
- [ ] Chọn Page/Slice/keyset và biết bẫy fetch collection với pagination.
- [ ] Phân biệt authentication, authorization và quyền sở hữu tài nguyên.
- [ ] Giải thích signed JWT, decode/verify, issuer/audience và expiry.
- [ ] Trình bày access/refresh token, rotation, logout và thu hồi.
- [ ] Phân biệt role/authority/scope và CORS/CSRF.
- [ ] Viết JUnit assertion, stub Mockito và giải thích giới hạn của mock.
- [ ] Phân biệt service test, controller test và integration test.
- [ ] Mô tả module cá nhân, một quyết định thiết kế và một bug đã xử lý.
- [ ] Đề xuất xử lý tăng traffic dựa trên đo đạc.
- [ ] Giải thích quy trình Git, review, conflict và hoàn tác.

### 10.2. Chuẩn bị bằng chứng từ project thật

| Chuẩn bị | Cách sử dụng khi phỏng vấn |
| --- | --- |
| Một sơ đồ request flow | Giải thích request đi qua filter, controller, service và DB |
| Một sơ đồ dữ liệu | Bảo vệ quan hệ, constraint và snapshot lịch sử |
| Một API mình phụ trách | Nêu input/output, quyền, trạng thái lỗi và transaction |
| Một SQL có EXPLAIN | Giải thích index dựa trên bằng chứng |
| Một test nghiệp vụ và một test tích hợp có ý nghĩa | Nói rõ mỗi test chứng minh điều gì |
| Một bug cùng commit sửa | Kể quá trình điều tra và cách tránh tái diễn |
| Một PR từng review hoặc nhận review | Trình bày cách làm việc nhóm |

### 10.3. Buổi luyện 30 phút

1. **5 phút:** trả lời ba câu mở đầu, mỗi câu không quá một phút rồi nhận xét phần thiếu.
2. **10 phút:** chọn ngẫu nhiên một câu mỗi nhóm Java, DB, Spring, JPA và Security; luôn thêm ví dụ project.
3. **10 phút:** bảo vệ luồng tạo đơn, bị hỏi tiếp về tồn kho, retry, phân quyền và transaction.
4. **5 phút:** kể một bug theo STAR và một quyết định thiết kế có đánh đổi.

Chấm mỗi câu theo thang 0–3: **0** chưa trả lời được; **1** có định nghĩa; **2** có ví dụ đúng; **3** giải thích được cơ chế, giới hạn và cách kiểm chứng. Ưu tiên ôn lại các câu ở mức 0–1.

<a id="s11"></a>
## 11. Tài liệu chính thức để đối chiếu

Các nguồn dưới đây dùng để kiểm tra thuật ngữ, hành vi framework và đào sâu khi cần; ví dụ project và câu trả lời mẫu trong tài liệu được xây dựng cho mục đích luyện phỏng vấn. Trang tài liệu không cố định phiên bản có thể thay đổi, vì vậy chọn đúng phiên bản dependency đang sử dụng.

| Nguồn | Dùng để tra cứu |
| --- | --- |
| [Oracle Java 17 — HashMap](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/HashMap.html) | Hợp đồng map, chi phí kỳ vọng và yêu cầu đồng bộ |
| [Spring Framework — Dependency Injection](https://docs.spring.io/spring-framework/reference/core/beans/dependencies/factory-collaborators.html) | Constructor/setter injection và dependency |
| [Spring Framework — @Transactional](https://docs.spring.io/spring-framework/reference/data-access/transaction/declarative/annotations.html) | Proxy, propagation và rollback rule |
| [Jakarta Persistence 3.1](https://jakarta.ee/specifications/persistence/3.1/jakarta-persistence-spec-3.1) | Đặc tả entity, quan hệ và persistence |
| [Spring Data JPA — Query Methods](https://docs.spring.io/spring-data/jpa/reference/jpa/query-methods.html) | Repository query, @Query và entity graph |
| [Hibernate ORM 6.6 — Query Language](https://docs.hibernate.org/orm/6.6/querylanguage/html_single/) | Fetch join và giới hạn khi phân trang |
| [MySQL 8.4 — Multiple-Column Indexes](https://dev.mysql.com/doc/refman/8.4/en/multiple-column-indexes.html) | Composite index và leftmost prefix |
| [Spring Security — JWT Resource Server](https://docs.spring.io/spring-security/reference/servlet/oauth2/resource-server/jwt.html) | Xác minh JWT và ánh xạ authority |
| [Spring Security — CSRF](https://docs.spring.io/spring-security/reference/servlet/exploits/csrf.html) | Cơ chế CSRF và tích hợp client |
| [RFC 9700 — OAuth 2.0 Security](https://www.rfc-editor.org/rfc/rfc9700.html) | Bảo vệ refresh token và chống replay |
| [RFC 9110 — HTTP Semantics](https://www.rfc-editor.org/rfc/rfc9110.html) | HTTP methods, safe/idempotent và status codes |
| [JUnit 5 User Guide](https://docs.junit.org/5.11.0/user-guide/) | Jupiter, assertions và lifecycle |
| [Mockito API](https://javadoc.io/doc/org.mockito/mockito-core) | Mock, spy, stubbing và verification |
| [Spring Framework — MockMvc](https://docs.spring.io/spring-framework/reference/testing/mockmvc.html) | Kiểm thử Spring MVC không mở server |

Tài liệu cùng workspace để đọc sâu thêm: [Spring Boot](../Springboot/springboot-guide.md), [OOP](../Technical/OOP_Backend_Intern_Interview.md), [SQL/MySQL](../Technical/SQL_MySQL_Backend_Intern_Interview.md) và [HTTP/REST](../Technical/HTTP_RESTful_API_Backend_Intern_Interview.md).
