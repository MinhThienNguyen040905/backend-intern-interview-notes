# OOP cho phỏng vấn Intern Backend

Tài liệu này tổng hợp các kiến thức OOP thường gặp khi phỏng vấn intern backend. Mục tiêu không phải học thuộc định nghĩa, mà là hiểu để giải thích được bằng ví dụ, biết khi nào dùng và biết tránh lạm dụng.

## 1. OOP là gì?

OOP, viết tắt của Object-Oriented Programming, là lập trình hướng đối tượng. Thay vì chỉ tổ chức chương trình quanh các hàm, OOP tổ chức chương trình quanh các đối tượng.

Một đối tượng thường có:

- **State**: dữ liệu/trạng thái, ví dụ `name`, `email`, `balance`.
- **Behavior**: hành vi/phương thức, ví dụ `deposit()`, `withdraw()`, `changePassword()`.

Ví dụ:

```java
class BankAccount {
    private String owner;
    private long balance;

    public BankAccount(String owner, long initialBalance) {
        this.owner = owner;
        this.balance = initialBalance;
    }

    public void deposit(long amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        balance += amount;
    }

    public long getBalance() {
        return balance;
    }
}
```

Trong backend, OOP thường xuất hiện ở:

- Entity/domain model: `User`, `Order`, `Product`.
- Service: `UserService`, `PaymentService`.
- Repository/DAO: `UserRepository`.
- DTO/request/response model.
- Controller/resource handler.
- Strategy, factory, adapter, validator, mapper.

## 2. Class và Object

### Class

Class là bản thiết kế, mô tả dữ liệu và hành vi của một loại đối tượng.

```java
class User {
    private String id;
    private String email;
}
```

### Object

Object là thực thể cụ thể được tạo từ class.

```java
User user = new User();
```

### Câu hỏi phỏng vấn hay gặp

- Class khác object như thế nào?
- Object có phải lúc nào cũng đại diện cho dữ liệu trong database không?
- Một service class có được xem là object không?

Trả lời ngắn:

- Class là khuôn mẫu, object là instance cụ thể.
- Object không nhất thiết phải map với database. Có object domain, object DTO, object service, object config.
- Service cũng là object nếu được tạo từ class, nhưng thường không đại diện cho một thực thể nghiệp vụ có state riêng.

## 3. Bốn tính chất chính của OOP

## 3.1 Encapsulation - Đóng gói

Đóng gói là che giấu dữ liệu bên trong object và chỉ cho phép truy cập/thay đổi qua các phương thức được kiểm soát.

Không nên:

```java
class User {
    public String email;
}
```

Tốt hơn:

```java
class User {
    private String email;

    public void changeEmail(String newEmail) {
        if (newEmail == null || !newEmail.contains("@")) {
            throw new IllegalArgumentException("Invalid email");
        }
        this.email = newEmail;
    }

    public String getEmail() {
        return email;
    }
}
```

Ý nghĩa trong backend:

- Bảo vệ tính hợp lệ của dữ liệu.
- Tránh code bên ngoài sửa state tùy tiện.
- Gom logic nghiệp vụ vào đúng nơi.

Ví dụ thực tế:

- Không cho order chuyển từ `CANCELLED` về `PAID`.
- Không cho tài khoản rút tiền vượt quá số dư.
- Không cho user đổi email sang định dạng sai.

Câu trả lời phỏng vấn:

> Encapsulation giúp object tự bảo vệ state của nó. Thay vì để code bên ngoài sửa field trực tiếp, ta cung cấp method có kiểm tra business rule.

## 3.2 Inheritance - Kế thừa

Kế thừa cho phép một class con tái sử dụng hoặc mở rộng hành vi của class cha.

```java
class Animal {
    public void eat() {
        System.out.println("Eating");
    }
}

class Dog extends Animal {
    public void bark() {
        System.out.println("Barking");
    }
}
```

Class `Dog` kế thừa method `eat()` từ `Animal`.

### Khi nào nên dùng kế thừa?

Dùng khi có quan hệ **is-a** thật sự.

- `Dog` is an `Animal`.
- `AdminUser` is a `User`, nếu admin thật sự là một dạng user.

Không nên dùng kế thừa chỉ để tái sử dụng vài dòng code.

Ví dụ không tốt:

```java
class EmailSender extends Logger {
}
```

`EmailSender` không phải là một loại `Logger`. Trường hợp này nên dùng composition:

```java
class EmailSender {
    private Logger logger;
}
```

### Vấn đề của kế thừa

- Làm class con phụ thuộc chặt vào class cha.
- Dễ tạo hierarchy phức tạp.
- Thay đổi class cha có thể làm hỏng class con.
- Có thể vi phạm Liskov Substitution Principle nếu class con không thay thế được class cha.

Câu trả lời phỏng vấn:

> Inheritance dùng để mô hình hóa quan hệ is-a. Nhưng trong backend thực tế thường ưu tiên composition hơn inheritance để giảm coupling và dễ test.

## 3.3 Polymorphism - Đa hình

Đa hình cho phép cùng một interface hoặc class cha có nhiều cách triển khai khác nhau.

Ví dụ:

```java
interface PaymentMethod {
    void pay(long amount);
}

class CreditCardPayment implements PaymentMethod {
    public void pay(long amount) {
        System.out.println("Pay by credit card: " + amount);
    }
}

class MomoPayment implements PaymentMethod {
    public void pay(long amount) {
        System.out.println("Pay by Momo: " + amount);
    }
}

class PaymentService {
    public void checkout(PaymentMethod paymentMethod, long amount) {
        paymentMethod.pay(amount);
    }
}
```

`PaymentService` không cần biết cụ thể đang dùng credit card hay Momo. Nó chỉ cần biết object đó có thể `pay()`.

Ý nghĩa trong backend:

- Dễ mở rộng thêm implementation mới.
- Giảm `if else` theo type.
- Hỗ trợ dependency injection.
- Hỗ trợ testing bằng mock/fake implementation.

Không tốt:

```java
if (type.equals("MOMO")) {
    payByMomo();
} else if (type.equals("CARD")) {
    payByCard();
} else if (type.equals("BANK")) {
    payByBank();
}
```

Tốt hơn:

```java
PaymentMethod method = paymentFactory.get(type);
method.pay(amount);
```

Câu trả lời phỏng vấn:

> Polymorphism giúp code phụ thuộc vào abstraction thay vì implementation cụ thể. Khi thêm cách thanh toán mới, ta thêm class mới thay vì sửa nhiều logic cũ.

## 3.4 Abstraction - Trừu tượng hóa

Trừu tượng hóa là che giấu chi tiết triển khai và chỉ expose những gì cần thiết.

Ví dụ:

```java
interface EmailClient {
    void send(String to, String subject, String body);
}
```

Code nghiệp vụ chỉ cần biết có thể gửi email, không cần biết đang dùng SMTP, SendGrid hay AWS SES.

```java
class NotificationService {
    private final EmailClient emailClient;

    public NotificationService(EmailClient emailClient) {
        this.emailClient = emailClient;
    }

    public void sendWelcomeEmail(String email) {
        emailClient.send(email, "Welcome", "Hello");
    }
}
```

Ý nghĩa:

- Giảm phụ thuộc vào vendor/framework.
- Dễ thay thế implementation.
- Dễ mock khi test.
- Giữ code nghiệp vụ sạch hơn.

Câu trả lời phỏng vấn:

> Abstraction giúp giấu chi tiết không cần thiết. Ví dụ service chỉ phụ thuộc vào `EmailClient`, còn gửi bằng SMTP hay API bên thứ ba là chuyện của implementation.

## 4. Encapsulation vs Abstraction

Hai khái niệm này dễ bị nhầm.

| Tiêu chí | Encapsulation | Abstraction |
|---|---|---|
| Tập trung vào | Che giấu dữ liệu/state | Che giấu chi tiết triển khai |
| Câu hỏi chính | Ai được sửa dữ liệu này? | Người dùng cần biết gì? |
| Công cụ thường dùng | `private`, getter/setter, method validate | interface, abstract class, public API |
| Ví dụ | Không cho sửa `balance` trực tiếp | Dùng `PaymentMethod` thay vì biết từng loại payment |

Nói ngắn:

- Encapsulation bảo vệ bên trong object.
- Abstraction thiết kế giao diện đơn giản cho bên ngoài dùng.

## 5. Access modifier

Access modifier quy định phạm vi truy cập class, field, method.

Trong Java:

| Modifier | Phạm vi |
|---|---|
| `public` | Truy cập từ mọi nơi |
| `protected` | Trong cùng package và class con |
| default/package-private | Trong cùng package |
| `private` | Chỉ trong class hiện tại |

Nguyên tắc:

- Field thường để `private`.
- Method chỉ `public` khi thật sự là API cần expose.
- Constructor có thể `private` trong singleton/factory/static utility.
- Không expose internal state nếu không cần.

Ví dụ tránh lộ mutable list:

```java
class Order {
    private final List<OrderItem> items = new ArrayList<>();

    public List<OrderItem> getItems() {
        return Collections.unmodifiableList(items);
    }
}
```

## 6. Constructor

Constructor dùng để khởi tạo object.

```java
class Product {
    private final String name;
    private final long price;

    public Product(String name, long price) {
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        this.name = name;
        this.price = price;
    }
}
```

Điểm cần biết:

- Constructor không có return type.
- Có thể overload constructor.
- Constructor nên đảm bảo object được tạo ra ở trạng thái hợp lệ.
- Với dependency injection, constructor injection thường tốt hơn field injection.

Ví dụ constructor injection:

```java
class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
```

Lợi ích:

- Dependency rõ ràng.
- Dễ test.
- Có thể dùng `final`.
- Object không bị thiếu dependency sau khi khởi tạo.

## 7. Method overloading và overriding

## 7.1 Overloading

Overloading là nhiều method cùng tên nhưng khác tham số trong cùng class.

```java
class Calculator {
    int add(int a, int b) {
        return a + b;
    }

    int add(int a, int b, int c) {
        return a + b + c;
    }
}
```

Đặc điểm:

- Xảy ra ở compile-time.
- Khác số lượng hoặc kiểu tham số.
- Chỉ khác return type thì không đủ để overload.

## 7.2 Overriding

Overriding là class con định nghĩa lại method đã có ở class cha/interface.

```java
class BaseDiscountPolicy {
    int discount(int amount) {
        return 0;
    }
}

class VipDiscountPolicy extends BaseDiscountPolicy {
    @Override
    int discount(int amount) {
        return amount * 10 / 100;
    }
}
```

Đặc điểm:

- Xảy ra ở runtime.
- Method signature phải tương thích.
- Dùng cho polymorphism.
- Nên dùng annotation `@Override` để compiler bắt lỗi.

So sánh:

| Tiêu chí | Overloading | Overriding |
|---|---|---|
| Xảy ra | Compile-time | Runtime |
| Quan hệ kế thừa | Không bắt buộc | Bắt buộc |
| Signature | Phải khác tham số | Phải giống/tương thích |
| Mục đích | Tiện gọi method cùng ý nghĩa | Thay đổi hành vi của class cha |

## 8. Interface và Abstract class

## 8.1 Interface

Interface định nghĩa contract/hợp đồng hành vi.

```java
interface UserRepository {
    Optional<User> findById(String id);
    void save(User user);
}
```

Class implement interface:

```java
class JdbcUserRepository implements UserRepository {
    public Optional<User> findById(String id) {
        // Query database
        return Optional.empty();
    }

    public void save(User user) {
        // Insert/update database
    }
}
```

Dùng interface khi:

- Muốn nhiều implementation.
- Muốn tách code nghiệp vụ khỏi database/API/framework cụ thể.
- Muốn dễ mock khi unit test.
- Muốn thể hiện capability: `Comparable`, `Serializable`, `PaymentMethod`.

## 8.2 Abstract class

Abstract class là class không nhất thiết tạo object trực tiếp, có thể chứa cả method abstract và method đã implement.

```java
abstract class BaseController {
    protected String getCurrentUserId() {
        return "current-user-id";
    }

    abstract String getResourceName();
}
```

Dùng abstract class khi:

- Các class con có logic chung thật sự.
- Cần chia sẻ state hoặc protected helper.
- Muốn ép class con implement một phần behavior.

## 8.3 Interface vs Abstract class

| Tiêu chí | Interface | Abstract class |
|---|---|---|
| Mục đích chính | Định nghĩa contract | Chia sẻ base behavior/state |
| Đa kế thừa | Một class có thể implement nhiều interface | Thường chỉ extend một class |
| State | Hạn chế, thường không giữ state nghiệp vụ | Có thể giữ state |
| Coupling | Thấp hơn | Cao hơn |
| Dùng khi | Cần abstraction linh hoạt | Cần base class chung |

Câu trả lời phỏng vấn:

> Nếu chỉ cần định nghĩa hành vi, em dùng interface. Nếu các class có logic chung cần chia sẻ và quan hệ kế thừa hợp lý, em cân nhắc abstract class.

## 9. Composition over Inheritance

Composition là xây object lớn từ các object nhỏ hơn.

```java
class OrderService {
    private final PaymentService paymentService;
    private final InventoryService inventoryService;
    private final EmailService emailService;
}
```

Nguyên tắc "composition over inheritance" nghĩa là ưu tiên dùng object chứa object khác thay vì tạo cây kế thừa phức tạp.

Lý do:

- Ít coupling hơn.
- Dễ thay thế dependency.
- Dễ test.
- Dễ hiểu hơn hierarchy kế thừa sâu.

Ví dụ:

Không tốt:

```java
class ReportService extends EmailService {
}
```

Tốt hơn:

```java
class ReportService {
    private final EmailService emailService;
}
```

Câu trả lời phỏng vấn:

> Em dùng inheritance khi có quan hệ is-a rõ ràng. Nếu chỉ cần dùng chức năng của class khác, em dùng composition.

## 10. Coupling và Cohesion

## 10.1 Coupling

Coupling là mức độ phụ thuộc giữa các module/class.

High coupling:

```java
class UserService {
    private MySqlUserRepository repository = new MySqlUserRepository();
}
```

Low coupling:

```java
class UserService {
    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }
}
```

Vì sao low coupling tốt:

- Dễ thay database implementation.
- Dễ mock khi test.
- Code ít bị ảnh hưởng dây chuyền.

## 10.2 Cohesion

Cohesion là mức độ các trách nhiệm trong một class có liên quan với nhau.

Low cohesion:

```java
class UserService {
    void createUser() {}
    void sendEmail() {}
    void generatePdfReport() {}
    void calculateShippingFee() {}
}
```

High cohesion:

```java
class UserService {
    void createUser() {}
    void updateProfile() {}
    void deactivateUser() {}
}
```

Mục tiêu:

- Low coupling.
- High cohesion.

Câu trả lời phỏng vấn:

> Một class tốt nên có trách nhiệm rõ ràng, các method liên quan đến cùng một mục đích, và không phụ thuộc quá chặt vào implementation cụ thể của class khác.

## 11. SOLID

SOLID là 5 nguyên tắc thiết kế OOP giúp code dễ bảo trì, mở rộng và test.

## 11.1 S - Single Responsibility Principle

Một class nên có một lý do chính để thay đổi.

Không tốt:

```java
class InvoiceService {
    void calculateTotal() {}
    void saveToDatabase() {}
    void sendEmail() {}
    void exportPdf() {}
}
```

Tốt hơn:

```java
class InvoiceCalculator {}
class InvoiceRepository {}
class InvoiceEmailService {}
class InvoicePdfExporter {}
```

Lưu ý:

- SRP không có nghĩa mỗi class chỉ có một method.
- SRP nói về một nhóm trách nhiệm gắn với một lý do thay đổi.

Câu trả lời phỏng vấn:

> SRP giúp class tập trung vào một trách nhiệm chính. Ví dụ tính tiền invoice, lưu invoice và gửi email nên tách ra vì chúng thay đổi vì các lý do khác nhau.

## 11.2 O - Open/Closed Principle

Software entities nên mở để mở rộng nhưng đóng với sửa đổi.

Không tốt:

```java
class DiscountService {
    int discount(String type, int amount) {
        if (type.equals("VIP")) return amount * 10 / 100;
        if (type.equals("NEW_USER")) return amount * 5 / 100;
        return 0;
    }
}
```

Tốt hơn:

```java
interface DiscountPolicy {
    int discount(int amount);
}

class VipDiscountPolicy implements DiscountPolicy {
    public int discount(int amount) {
        return amount * 10 / 100;
    }
}

class NewUserDiscountPolicy implements DiscountPolicy {
    public int discount(int amount) {
        return amount * 5 / 100;
    }
}
```

Khi thêm loại discount mới, thêm class mới thay vì sửa logic cũ quá nhiều.

Cần thực tế:

- Không phải mọi `if else` đều xấu.
- Nếu logic chỉ có 2 case và ít thay đổi, tách class quá sớm có thể làm code rối hơn.

## 11.3 L - Liskov Substitution Principle

Class con phải thay thế được class cha mà không làm sai hành vi mong đợi.

Ví dụ kinh điển:

```java
class Bird {
    void fly() {}
}

class Penguin extends Bird {
    @Override
    void fly() {
        throw new UnsupportedOperationException();
    }
}
```

`Penguin` là chim nhưng không bay được. Nếu code kỳ vọng mọi `Bird` đều `fly()`, thì `Penguin` phá vỡ kỳ vọng đó.

Thiết kế tốt hơn:

```java
interface Bird {}

interface Flyable {
    void fly();
}

class Sparrow implements Bird, Flyable {
    public void fly() {}
}

class Penguin implements Bird {
}
```

Trong backend:

- `ReadOnlyRepository` không nên kế thừa `Repository` có method `save()` rồi throw exception.
- Payment implementation không nên silently ignore `pay()`.

Câu trả lời phỏng vấn:

> Nếu một class con override method nhưng làm yếu đi contract, throw exception bất ngờ, hoặc không giữ invariant của class cha, có thể đã vi phạm LSP.

## 11.4 I - Interface Segregation Principle

Client không nên bị ép phụ thuộc vào method mà nó không dùng.

Không tốt:

```java
interface Machine {
    void print();
    void scan();
    void fax();
}

class SimplePrinter implements Machine {
    public void print() {}

    public void scan() {
        throw new UnsupportedOperationException();
    }

    public void fax() {
        throw new UnsupportedOperationException();
    }
}
```

Tốt hơn:

```java
interface Printer {
    void print();
}

interface Scanner {
    void scan();
}

interface Fax {
    void fax();
}
```

Trong backend:

- Tách `ReadableRepository`, `WritableRepository` nếu có use case chỉ đọc.
- Tách interface lớn thành các contract nhỏ hơn nếu client chỉ dùng một phần nhỏ.

## 11.5 D - Dependency Inversion Principle

Module cấp cao không nên phụ thuộc trực tiếp vào module cấp thấp. Cả hai nên phụ thuộc vào abstraction.

Không tốt:

```java
class OrderService {
    private final MySqlOrderRepository repository = new MySqlOrderRepository();
}
```

Tốt hơn:

```java
interface OrderRepository {
    void save(Order order);
}

class OrderService {
    private final OrderRepository repository;

    public OrderService(OrderRepository repository) {
        this.repository = repository;
    }
}
```

Trong Spring Boot:

```java
@Service
class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }
}
```

Câu trả lời phỏng vấn:

> Service nghiệp vụ nên phụ thuộc vào abstraction như repository interface, không phụ thuộc trực tiếp vào implementation cụ thể. Dependency Injection giúp inject implementation vào runtime.

## 12. Association, Aggregation, Composition

Các khái niệm này mô tả quan hệ giữa object.

## 12.1 Association

Association là quan hệ sử dụng/biết nhau nói chung.

```java
class Teacher {
    private List<Student> students;
}
```

Teacher có liên hệ với Student.

## 12.2 Aggregation

Aggregation là quan hệ has-a yếu. Object con có thể tồn tại độc lập với object cha.

Ví dụ:

- `Department` có nhiều `Employee`.
- Employee vẫn có thể tồn tại nếu department bị xóa hoặc chuyển department.

```java
class Department {
    private List<Employee> employees;
}
```

## 12.3 Composition

Composition là quan hệ has-a mạnh. Object con là một phần của object cha, vòng đời phụ thuộc vào cha.

Ví dụ:

- `Order` có nhiều `OrderItem`.
- `OrderItem` thường không có ý nghĩa nếu tách khỏi `Order`.

```java
class Order {
    private final List<OrderItem> items = new ArrayList<>();
}
```

So sánh:

| Quan hệ | Ý nghĩa | Vòng đời |
|---|---|---|
| Association | Có liên hệ/sử dụng | Không ràng buộc |
| Aggregation | Has-a yếu | Con sống độc lập |
| Composition | Has-a mạnh | Con phụ thuộc cha |

## 13. Static

`static` thuộc về class, không thuộc về instance cụ thể.

```java
class MathUtil {
    public static int max(int a, int b) {
        return a > b ? a : b;
    }
}
```

Dùng `static` cho:

- Constant.
- Utility function không cần state.
- Factory method.

Ví dụ:

```java
class UserFactory {
    public static User createWithEmail(String email) {
        return new User(email);
    }
}
```

Cẩn thận:

- Static state có thể gây bug trong concurrent application.
- Static method khó mock hơn trong unit test.
- Lạm dụng static làm code procedural hơn là OOP.

## 14. Final và Immutability

`final` trong Java:

- Final variable: không gán lại được.
- Final method: không override được.
- Final class: không extend được.

Immutable object là object không thay đổi state sau khi tạo.

```java
final class Money {
    private final String currency;
    private final long amount;

    public Money(String currency, long amount) {
        this.currency = currency;
        this.amount = amount;
    }

    public Money add(Money other) {
        if (!this.currency.equals(other.currency)) {
            throw new IllegalArgumentException("Currency mismatch");
        }
        return new Money(currency, amount + other.amount);
    }
}
```

Lợi ích immutable:

- Dễ hiểu.
- An toàn hơn trong multi-thread.
- Tránh side effect.
- Phù hợp với value object như `Money`, `Email`, `Address`, `DateRange`.

## 15. Object equality

Trong Java, cần phân biệt:

- `==`: so sánh reference, tức hai biến có trỏ đến cùng object không.
- `equals()`: so sánh logic equality, nếu class override đúng.

Ví dụ:

```java
String a = new String("hello");
String b = new String("hello");

System.out.println(a == b);      // false
System.out.println(a.equals(b)); // true
```

Nếu override `equals()`, phải override `hashCode()`.

Lý do:

- Collection như `HashSet`, `HashMap` dùng `hashCode()` để tìm bucket.
- Nếu `equals()` đúng nhưng `hashCode()` sai, collection có thể hoạt động sai.

Ví dụ:

```java
class UserId {
    private final String value;

    public UserId(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserId)) return false;
        UserId userId = (UserId) o;
        return Objects.equals(value, userId.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
```

Câu hỏi hay gặp:

- `==` và `equals()` khác gì nhau?
- Vì sao override `equals()` phải override `hashCode()`?
- Object dùng làm key trong `HashMap` cần lưu ý gì?

## 16. Object lifecycle

Object lifecycle là vòng đời của object:

1. Được tạo.
2. Được sử dụng.
3. Không còn reference.
4. Có thể được garbage collector thu hồi.

Trong backend framework như Spring:

- Bean được tạo bởi container.
- Dependency được inject.
- Bean được dùng để xử lý request.
- Bean bị destroy khi app shutdown.

Quan trọng:

- Singleton service không nên giữ request-specific mutable state.
- State theo từng request nên nằm trong local variable, request object, session hoặc context phù hợp.

Không tốt:

```java
@Service
class OrderService {
    private String currentUserId;

    public void createOrder(String userId) {
        this.currentUserId = userId;
    }
}
```

Vì service singleton có thể dùng bởi nhiều request đồng thời.

Tốt hơn:

```java
@Service
class OrderService {
    public void createOrder(String userId) {
        // userId là local/request data
    }
}
```

## 17. Dependency Injection

Dependency Injection là kỹ thuật đưa dependency từ bên ngoài vào object thay vì object tự tạo dependency.

Không DI:

```java
class UserService {
    private final UserRepository repository = new MySqlUserRepository();
}
```

DI:

```java
class UserService {
    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }
}
```

Lợi ích:

- Low coupling.
- Dễ test.
- Dễ thay implementation.
- Dependency rõ ràng.

Các kiểu DI:

- Constructor injection.
- Setter injection.
- Field injection.

Ưu tiên constructor injection cho dependency bắt buộc.

Trong Spring:

```java
@Service
class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
```

## 18. DTO, Entity, Domain Model

Trong backend, nhiều ứng viên intern hay nhầm các loại object này.

## 18.1 Entity

Entity thường đại diện cho dữ liệu có identity, có thể map với database.

```java
class User {
    private Long id;
    private String email;
}
```

Đặc điểm:

- Có identity, ví dụ `id`.
- Có vòng đời.
- Thường được persistence layer quản lý.

## 18.2 DTO

DTO, viết tắt của Data Transfer Object, dùng để chuyển dữ liệu giữa các layer hoặc qua API.

```java
class CreateUserRequest {
    private String email;
    private String password;
}

class UserResponse {
    private Long id;
    private String email;
}
```

Lý do không nên trả thẳng entity ra API:

- Có thể lộ field nhạy cảm.
- API contract bị phụ thuộc vào database model.
- Dễ gặp lỗi lazy loading/serialization.
- Khó versioning API.

## 18.3 Domain Model

Domain model chứa logic nghiệp vụ cốt lõi.

```java
class Order {
    private OrderStatus status;

    public void cancel() {
        if (status == OrderStatus.SHIPPED) {
            throw new IllegalStateException("Cannot cancel shipped order");
        }
        status = OrderStatus.CANCELLED;
    }
}
```

Với intern, chỉ cần hiểu:

- DTO dùng để giao tiếp dữ liệu.
- Entity thường liên quan persistence.
- Domain model nên chứa business rule nếu hệ thống theo hướng domain-driven hơn.

## 19. Anemic Domain Model

Anemic Domain Model là model chỉ có field/getter/setter, còn logic nghiệp vụ nằm hết trong service.

Ví dụ:

```java
class Order {
    private OrderStatus status;

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}

class OrderService {
    public void cancel(Order order) {
        if (order.getStatus() == OrderStatus.SHIPPED) {
            throw new IllegalStateException("Cannot cancel shipped order");
        }
        order.setStatus(OrderStatus.CANCELLED);
    }
}
```

Không phải lúc nào cũng sai, đặc biệt với CRUD đơn giản. Nhưng với domain phức tạp, có thể cân nhắc đưa logic vào entity/domain object:

```java
class Order {
    private OrderStatus status;

    public void cancel() {
        if (status == OrderStatus.SHIPPED) {
            throw new IllegalStateException("Cannot cancel shipped order");
        }
        status = OrderStatus.CANCELLED;
    }
}
```

Câu trả lời thực tế:

> Với CRUD đơn giản, anemic model có thể chấp nhận được. Nhưng nếu business rule phức tạp, đưa logic vào domain object giúp rule nằm gần data và tránh bị duplicate ở nhiều service.

## 20. Service, Repository, Controller dưới góc nhìn OOP

## 20.1 Controller

Controller nhận request, validate input cơ bản, gọi service và trả response.

Không nên để business logic phức tạp trong controller.

```java
class UserController {
    private final UserService userService;

    public UserResponse create(CreateUserRequest request) {
        return userService.create(request);
    }
}
```

## 20.2 Service

Service xử lý use case/business workflow.

```java
class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User create(CreateUserRequest request) {
        // validate business rule, encode password, save user
        return null;
    }
}
```

## 20.3 Repository

Repository che giấu chi tiết truy cập dữ liệu.

```java
interface UserRepository {
    Optional<User> findByEmail(String email);
    User save(User user);
}
```

Ý nghĩa OOP:

- Controller phụ thuộc vào service abstraction/behavior.
- Service phụ thuộc vào repository abstraction.
- Repository implementation xử lý database cụ thể.

## 21. Common design patterns cho intern backend

## 21.1 Singleton

Singleton đảm bảo một class chỉ có một instance.

Trong Spring, bean mặc định thường là singleton trong ApplicationContext.

Cẩn thận:

- Singleton service không nên giữ mutable state theo request.
- Singleton tự viết có thể khó test nếu lạm dụng global state.

## 21.2 Factory

Factory dùng để tạo object, đặc biệt khi logic tạo object phức tạp hoặc phụ thuộc vào type.

```java
class PaymentFactory {
    private final Map<String, PaymentMethod> methods;

    public PaymentFactory(List<PaymentMethod> paymentMethods) {
        this.methods = paymentMethods.stream()
            .collect(Collectors.toMap(
                method -> method.getClass().getSimpleName(),
                method -> method
            ));
    }

    public PaymentMethod get(String type) {
        PaymentMethod method = methods.get(type);
        if (method == null) {
            throw new IllegalArgumentException("Unsupported payment type");
        }
        return method;
    }
}
```

Ý nghĩa:

- Tách logic chọn/tạo object khỏi business service.
- Giảm `if else` lặp lại.

## 21.3 Strategy

Strategy cho phép thay đổi thuật toán/hành vi ở runtime thông qua interface.

```java
interface ShippingFeeStrategy {
    long calculate(Order order);
}

class StandardShippingFee implements ShippingFeeStrategy {
    public long calculate(Order order) {
        return 30000;
    }
}

class ExpressShippingFee implements ShippingFeeStrategy {
    public long calculate(Order order) {
        return 60000;
    }
}
```

Dùng khi:

- Có nhiều thuật toán cùng mục đích.
- Muốn thêm thuật toán mới mà ít sửa code cũ.
- Ví dụ: discount, shipping fee, payment, notification channel.

## 21.4 Builder

Builder giúp tạo object có nhiều field tùy chọn dễ đọc hơn.

```java
User user = User.builder()
    .email("a@example.com")
    .name("Alice")
    .active(true)
    .build();
```

Dùng khi:

- Constructor quá dài.
- Có nhiều optional field.
- Muốn object immutable nhưng vẫn dễ khởi tạo.

## 21.5 Adapter

Adapter chuyển interface của một class/service thành interface mà code của mình mong muốn.

Ví dụ:

```java
interface EmailClient {
    void send(String to, String subject, String body);
}

class SendGridEmailClient implements EmailClient {
    private final SendGridSdk sdk;

    public void send(String to, String subject, String body) {
        sdk.sendEmail(to, subject, body);
    }
}
```

Dùng khi tích hợp:

- Payment gateway.
- Email provider.
- SMS provider.
- Cloud storage.
- External API.

## 21.6 Template Method

Template Method định nghĩa khung xử lý chung ở class cha, class con override một số bước.

```java
abstract class ImportJob {
    public final void run() {
        readFile();
        validate();
        save();
    }

    protected abstract void readFile();
    protected abstract void validate();
    protected abstract void save();
}
```

Dùng khi nhiều flow có cấu trúc giống nhau nhưng khác chi tiết từng bước.

## 22. Error handling dưới góc nhìn OOP

Exception cũng là object.

Nên:

- Tạo exception có ý nghĩa nghiệp vụ.
- Không bắt exception rồi nuốt mất lỗi.
- Không dùng exception cho flow thông thường nếu có cách rõ hơn.

Ví dụ:

```java
class InsufficientBalanceException extends RuntimeException {
    public InsufficientBalanceException() {
        super("Insufficient balance");
    }
}
```

Trong domain:

```java
class BankAccount {
    private long balance;

    public void withdraw(long amount) {
        if (amount > balance) {
            throw new InsufficientBalanceException();
        }
        balance -= amount;
    }
}
```

Trong backend:

- Domain/service throw exception.
- Controller advice/exception handler map exception sang HTTP response.

Ví dụ:

- `UserNotFoundException` -> `404 Not Found`.
- `DuplicateEmailException` -> `409 Conflict`.
- `ValidationException` -> `400 Bad Request`.

## 23. OOP và testing

OOP tốt giúp test dễ hơn.

Ví dụ service phụ thuộc interface:

```java
class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
```

Khi test có thể dùng fake/mock repository.

```java
class FakeUserRepository implements UserRepository {
    private final Map<String, User> users = new HashMap<>();
}
```

Điểm phỏng vấn:

- Constructor injection giúp unit test dễ.
- Interface giúp mock dependency.
- Class có quá nhiều dependency có thể đang làm quá nhiều việc.
- Static/global state làm test khó hơn.

## 24. Dấu hiệu thiết kế OOP chưa tốt

- Class quá lớn, nhiều trách nhiệm.
- Method quá dài, nhiều nhánh.
- Quá nhiều `if else` theo type.
- Field public hoặc setter tràn lan.
- Class con override method rồi throw `UnsupportedOperationException`.
- Service singleton giữ request state.
- Entity bị trả thẳng ra API và lộ field nhạy cảm.
- Code phụ thuộc trực tiếp vào implementation cụ thể.
- Interface quá to, client chỉ dùng một phần nhỏ.
- Kế thừa nhiều tầng chỉ để reuse code.

## 25. Cách trả lời câu hỏi thiết kế trong phỏng vấn

Khi được hỏi "thiết kế class cho chức năng X", có thể trả lời theo khung:

1. Xác định object chính trong domain.
2. Xác định state và behavior của từng object.
3. Xác định relation: has-a, is-a, uses.
4. Tách interface nếu có nhiều implementation hoặc cần test/mở rộng.
5. Đặt business rule ở nơi phù hợp.
6. Cân nhắc SOLID nhưng không over-engineer.

Ví dụ câu hỏi:

> Thiết kế hệ thống thanh toán có nhiều phương thức: card, ví điện tử, chuyển khoản.

Cách trả lời:

- Tạo interface `PaymentMethod` với method `pay(amount)`.
- Mỗi phương thức là một implementation: `CardPayment`, `WalletPayment`, `BankTransferPayment`.
- `PaymentService` phụ thuộc vào `PaymentMethod` hoặc `PaymentFactory`.
- Nếu thêm phương thức mới, thêm class mới và đăng ký vào factory/DI container.
- Service không nên chứa quá nhiều `if else` theo type.

## 26. Câu hỏi phỏng vấn OOP thường gặp

## 26.1 Câu hỏi lý thuyết

1. OOP là gì?
2. Bốn tính chất của OOP là gì?
3. Encapsulation khác abstraction như thế nào?
4. Interface khác abstract class như thế nào?
5. Overloading khác overriding như thế nào?
6. `==` khác `equals()` như thế nào?
7. Vì sao override `equals()` phải override `hashCode()`?
8. Inheritance có nhược điểm gì?
9. Composition over inheritance nghĩa là gì?
10. SOLID là gì?
11. Dependency Injection là gì?
12. Coupling và cohesion là gì?
13. Association, aggregation, composition khác nhau thế nào?
14. Static method có nhược điểm gì?
15. Immutable object là gì?

## 26.2 Câu hỏi backend thực tế

1. Vì sao không nên trả entity trực tiếp ra API?
2. Controller, service, repository nên chịu trách nhiệm gì?
3. Service singleton trong Spring có nên lưu `currentUserId` vào field không?
4. Làm sao thiết kế nhiều payment method mà không dùng nhiều `if else`?
5. Khi nào cần tạo interface cho repository/service?
6. Vì sao constructor injection thường được khuyến nghị?
7. Nếu class có quá nhiều dependency thì có vấn đề gì?
8. DTO khác entity như thế nào?
9. Business validation nên đặt ở controller, service hay domain object?
10. Làm sao thiết kế code dễ unit test?

## 27. Câu trả lời mẫu ngắn

### OOP là gì?

OOP là cách tổ chức chương trình quanh object. Object kết hợp state và behavior. Trong backend, ví dụ `Order` có state như status, items và behavior như `cancel()`, `pay()`.

### Encapsulation là gì?

Encapsulation là đóng gói state bên trong object và chỉ cho phép thay đổi qua method có kiểm soát. Ví dụ không cho sửa trực tiếp `balance`, mà dùng `withdraw()` để kiểm tra số dư trước.

### Polymorphism là gì?

Polymorphism là cùng một abstraction có nhiều implementation. Ví dụ `PaymentMethod` có `CardPayment`, `MomoPayment`, `BankTransferPayment`. Service chỉ gọi `pay()` mà không cần biết implementation cụ thể.

### Interface khác abstract class?

Interface chủ yếu định nghĩa contract, phù hợp khi cần nhiều implementation và low coupling. Abstract class phù hợp khi các class con có logic hoặc state chung cần chia sẻ.

### Vì sao dùng Dependency Injection?

DI giúp class không tự tạo dependency cụ thể mà nhận từ bên ngoài. Điều này giảm coupling, dễ thay implementation và dễ unit test.

### SOLID dùng để làm gì?

SOLID là nhóm nguyên tắc giúp thiết kế code OOP dễ bảo trì, mở rộng và test hơn. Tuy nhiên cần áp dụng thực tế, không nên tách abstraction quá sớm khi bài toán còn đơn giản.

## 28. Mini exercise tự luyện

## Bài 1: Payment

Yêu cầu:

- Có nhiều phương thức thanh toán.
- Mỗi phương thức có logic xử lý riêng.
- Dễ thêm phương thức mới.

Gợi ý:

- `PaymentMethod` interface.
- `CardPayment`, `WalletPayment`, `BankTransferPayment`.
- `PaymentService`.
- `PaymentFactory` nếu cần chọn theo type.

## Bài 2: Notification

Yêu cầu:

- Gửi thông báo qua email, SMS, push notification.
- User có thể chọn channel.

Gợi ý:

- `NotificationSender` interface.
- `EmailNotificationSender`, `SmsNotificationSender`, `PushNotificationSender`.
- `NotificationService`.

## Bài 3: Order

Yêu cầu:

- Order có trạng thái `CREATED`, `PAID`, `SHIPPED`, `CANCELLED`.
- Không cho cancel order đã shipped.
- Không cho ship order chưa paid.

Gợi ý:

- Đặt rule trong `Order.cancel()` và `Order.ship()`.
- Không expose setter status tùy tiện.

Ví dụ:

```java
class Order {
    private OrderStatus status = OrderStatus.CREATED;

    public void pay() {
        if (status != OrderStatus.CREATED) {
            throw new IllegalStateException("Only created order can be paid");
        }
        status = OrderStatus.PAID;
    }

    public void ship() {
        if (status != OrderStatus.PAID) {
            throw new IllegalStateException("Only paid order can be shipped");
        }
        status = OrderStatus.SHIPPED;
    }

    public void cancel() {
        if (status == OrderStatus.SHIPPED) {
            throw new IllegalStateException("Cannot cancel shipped order");
        }
        status = OrderStatus.CANCELLED;
    }
}
```

## 29. Checklist trước khi đi phỏng vấn

Bạn nên tự trả lời được:

- Giải thích 4 tính chất OOP bằng ví dụ backend.
- Phân biệt interface và abstract class.
- Phân biệt overloading và overriding.
- Phân biệt `==`, `equals()`, `hashCode()`.
- Nói được khi nào dùng inheritance, khi nào dùng composition.
- Giải thích SOLID ở mức thực tế.
- Thiết kế được payment/notification/discount bằng polymorphism.
- Biết controller/service/repository làm gì.
- Biết vì sao cần DTO.
- Biết vì sao DI giúp test dễ hơn.
- Biết tránh public field, setter bừa bãi, service ôm quá nhiều trách nhiệm.

## 30. Tóm tắt cực ngắn

- OOP tổ chức code quanh object có state và behavior.
- Encapsulation bảo vệ state.
- Abstraction giấu chi tiết implementation.
- Inheritance mô hình hóa quan hệ is-a nhưng không nên lạm dụng.
- Polymorphism giúp dùng chung abstraction cho nhiều implementation.
- Composition thường linh hoạt hơn inheritance.
- SOLID giúp code dễ bảo trì nhưng phải áp dụng vừa đủ.
- Backend OOP tốt thường đi cùng DI, interface hợp lý, DTO rõ ràng, service/repository/controller tách trách nhiệm.
- Một câu trả lời tốt trong phỏng vấn nên có ví dụ cụ thể, trade-off và liên hệ thực tế backend.
