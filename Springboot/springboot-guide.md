# Học Spring Boot Từ Cơ Bản Đến Phỏng Vấn

## 1. Spring Boot Là Gì?

Spring Boot là framework giúp xây dựng ứng dụng Java dựa trên Spring nhanh hơn, ít cấu hình thủ công hơn.

Nếu Spring Framework giống như một bộ công cụ lớn, thì Spring Boot là cách đóng gói bộ công cụ đó thành một trải nghiệm dễ dùng hơn.

Spring Boot giúp bạn:

- Tạo ứng dụng web nhanh.
- Giảm cấu hình XML.
- Tự động cấu hình bean phổ biến.
- Chạy ứng dụng bằng embedded server như Tomcat.
- Dễ tích hợp database, security, cache, message queue.
- Dễ test và deploy.

Ví dụ chạy ứng dụng Spring Boot:

```java
@SpringBootApplication
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
```

## 2. Vì Sao Spring Boot Ra Đời?

Trước Spring Boot, khi dùng Spring MVC hoặc Spring Framework thuần, lập trình viên thường phải cấu hình nhiều thứ:

- DispatcherServlet.
- ViewResolver.
- DataSource.
- TransactionManager.
- Dependency version.
- Tomcat hoặc server ngoài.
- File XML hoặc Java config dài.

Spring Boot giải quyết bằng:

- Auto Configuration.
- Starter dependencies.
- Embedded server.
- Opinionated defaults.
- Production-ready features qua Actuator.

## 3. Cấu Trúc Dự Án Spring Boot

Một dự án Spring Boot phổ biến:

```text
src
|-- main
|   |-- java
|   |   `-- com.example.demo
|   |       |-- DemoApplication.java
|   |       |-- controller
|   |       |-- service
|   |       |-- repository
|   |       |-- entity
|   |       |-- dto
|   |       `-- config
|   `-- resources
|       |-- application.properties
|       `-- static
`-- test
    `-- java
```

Vai trò thường gặp:

- `controller`: nhận HTTP request, trả HTTP response.
- `service`: xử lý nghiệp vụ.
- `repository`: thao tác database.
- `entity`: ánh xạ bảng database.
- `dto`: dữ liệu request/response.
- `config`: cấu hình bean, security, CORS, Swagger.

## 4. Annotation Quan Trọng

### `@SpringBootApplication`

Annotation chính của ứng dụng Spring Boot.

Nó là tổ hợp của:

- `@Configuration`
- `@EnableAutoConfiguration`
- `@ComponentScan`

```java
@SpringBootApplication
public class App {
}
```

### `@RestController`

Dùng để tạo REST API.

```java
@RestController
@RequestMapping("/api/users")
public class UserController {
}
```

`@RestController` tương đương:

```java
@Controller
@ResponseBody
```

### `@Service`

Đánh dấu class chứa logic nghiệp vụ.

```java
@Service
public class UserService {
}
```

### `@Repository`

Đánh dấu class hoặc interface thao tác dữ liệu.

```java
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
```

### `@Autowired`

Dùng để inject dependency.

Hiện nay nên ưu tiên constructor injection:

```java
@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
```

Constructor injection tốt hơn field injection vì:

- Dễ test.
- Dependency rõ ràng.
- Có thể dùng `final`.
- Tránh object ở trạng thái thiếu dependency.

## 5. REST API Trong Spring Boot

Ví dụ controller cơ bản:

```java
@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<ProductResponse> findAll() {
        return productService.findAll();
    }

    @GetMapping("/{id}")
    public ProductResponse findById(@PathVariable Long id) {
        return productService.findById(id);
    }

    @PostMapping
    public ProductResponse create(@RequestBody ProductRequest request) {
        return productService.create(request);
    }
}
```

Các annotation request thường dùng:

- `@GetMapping`: lấy dữ liệu.
- `@PostMapping`: tạo mới.
- `@PutMapping`: cập nhật toàn bộ.
- `@PatchMapping`: cập nhật một phần.
- `@DeleteMapping`: xóa.
- `@PathVariable`: lấy biến từ URL.
- `@RequestParam`: lấy query parameter.
- `@RequestBody`: đọc JSON body.

Ví dụ:

```java
@GetMapping("/search")
public List<ProductResponse> search(@RequestParam String keyword) {
    return productService.search(keyword);
}
```

Request:

```text
GET /api/products/search?keyword=phone
```

## 6. Layered Architecture

Spring Boot thường dùng kiến trúc phân tầng:

```text
Controller -> Service -> Repository -> Database
```

Không nên viết logic nghiệp vụ trực tiếp trong controller.

Ví dụ tốt:

```java
@RestController
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/orders")
    public OrderResponse create(@RequestBody CreateOrderRequest request) {
        return orderService.createOrder(request);
    }
}
```

Service xử lý nghiệp vụ:

```java
@Service
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public OrderResponse createOrder(CreateOrderRequest request) {
        Order order = new Order();
        order.setCustomerName(request.getCustomerName());
        order.setTotalAmount(request.getTotalAmount());

        Order savedOrder = orderRepository.save(order);

        return new OrderResponse(savedOrder.getId(), savedOrder.getCustomerName());
    }
}
```

## 7. Spring Data JPA

Spring Data JPA giúp thao tác database nhanh hơn thông qua repository interface.

Entity:

```java
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;
}
```

Repository:

```java
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    List<User> findByNameContainingIgnoreCase(String name);
}
```

Các interface phổ biến:

- `CrudRepository`: CRUD cơ bản.
- `PagingAndSortingRepository`: CRUD, phân trang, sắp xếp.
- `JpaRepository`: đầy đủ hơn, thường dùng nhất.

## 8. DTO Và Entity

Không nên trả entity trực tiếp ra API trong các ứng dụng nghiêm túc.

Lý do:

- Tránh lộ cấu trúc database.
- Tránh vòng lặp JSON với quan hệ JPA.
- Kiểm soát dữ liệu response.
- Dễ version API.

Ví dụ DTO:

```java
public class UserResponse {
    private Long id;
    private String name;
    private String email;
}
```

Request DTO:

```java
public class CreateUserRequest {
    private String name;
    private String email;
    private String password;
}
```

## 9. Validation

Thêm validation vào request:

```java
public class CreateUserRequest {
    @NotBlank(message = "Name is required")
    private String name;

    @Email(message = "Email is invalid")
    @NotBlank(message = "Email is required")
    private String email;

    @Size(min = 6, message = "Password must have at least 6 characters")
    private String password;
}
```

Controller:

```java
@PostMapping("/users")
public UserResponse create(@Valid @RequestBody CreateUserRequest request) {
    return userService.create(request);
}
```

Các annotation validation thường gặp:

- `@NotNull`
- `@NotBlank`
- `@NotEmpty`
- `@Email`
- `@Size`
- `@Min`
- `@Max`
- `@Pattern`

## 10. Exception Handling

Dùng `@ControllerAdvice` để xử lý lỗi tập trung.

```java
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(ResourceNotFoundException ex) {
        ErrorResponse error = new ErrorResponse("NOT_FOUND", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult()
                .getFieldErrors()
                .get(0)
                .getDefaultMessage();

        ErrorResponse error = new ErrorResponse("VALIDATION_ERROR", message);
        return ResponseEntity.badRequest().body(error);
    }
}
```

Custom exception:

```java
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
```

## 11. Configuration

File `application.properties`:

```properties
server.port=8080

spring.datasource.url=jdbc:mysql://localhost:3306/demo
spring.datasource.username=root
spring.datasource.password=123456

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

Hoặc dùng `application.yml`:

```yaml
server:
  port: 8080

spring:
  datasource:
    url: jdbc:mysql://localhost:3306/demo
    username: root
    password: 123456
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
```

## 12. Profiles

Profile giúp chạy cấu hình khác nhau theo môi trường.

Ví dụ:

```text
application-dev.yml
application-prod.yml
application-test.yml
```

Chọn profile:

```properties
spring.profiles.active=dev
```

Hoặc chạy bằng command:

```bash
java -jar app.jar --spring.profiles.active=prod
```

## 13. Bean Và Dependency Injection

Bean là object được Spring quản lý.

Tạo bean bằng annotation:

```java
@Service
public class EmailService {
}
```

Hoặc bằng `@Bean`:

```java
@Configuration
public class AppConfig {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
```

Dependency Injection là cơ chế Spring tự đưa dependency cần thiết vào object.

Ví dụ:

```java
@Service
public class NotificationService {
    private final EmailService emailService;

    public NotificationService(EmailService emailService) {
        this.emailService = emailService;
    }
}
```

## 14. Transaction

Dùng `@Transactional` để đảm bảo các thao tác database thành công hoặc rollback cùng nhau.

```java
@Service
public class PaymentService {
    private final AccountRepository accountRepository;

    public PaymentService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Transactional
    public void transfer(Long fromId, Long toId, BigDecimal amount) {
        Account from = accountRepository.findById(fromId)
                .orElseThrow(() -> new ResourceNotFoundException("Sender not found"));

        Account to = accountRepository.findById(toId)
                .orElseThrow(() -> new ResourceNotFoundException("Receiver not found"));

        from.withdraw(amount);
        to.deposit(amount);

        accountRepository.save(from);
        accountRepository.save(to);
    }
}
```

Mặc định, Spring rollback với unchecked exception như `RuntimeException`.

## 15. Spring Security Cơ Bản

Spring Security dùng để xác thực và phân quyền.

Các khái niệm:

- Authentication: bạn là ai?
- Authorization: bạn được làm gì?
- Principal: người dùng đang đăng nhập.
- GrantedAuthority: quyền hoặc vai trò.
- SecurityFilterChain: chuỗi filter bảo vệ request.

Ví dụ cấu hình đơn giản:

```java
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll()
                        .anyRequest().authenticated()
                )
                .build();
    }
}
```

Với REST API hiện đại, thường dùng JWT:

```text
Client login -> Server trả JWT -> Client gửi Authorization: Bearer <token>
```

## 16. Testing

Spring Boot hỗ trợ test qua `spring-boot-starter-test`.

Unit test service:

```java
@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void shouldCreateUser() {
        // Arrange
        CreateUserRequest request = new CreateUserRequest("An", "an@example.com");
        User savedUser = new User(1L, "An", "an@example.com");

        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        // Act
        UserResponse response = userService.create(request);

        // Assert
        assertEquals(1L, response.getId());
        assertEquals("An", response.getName());
    }
}
```

Test controller với `@WebMvcTest`:

```java
@WebMvcTest(UserController.class)
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void shouldReturnUser() throws Exception {
        when(userService.findById(1L))
                .thenReturn(new UserResponse(1L, "An", "an@example.com"));

        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("An"));
    }
}
```

## 17. Actuator

Spring Boot Actuator cung cấp endpoint để theo dõi ứng dụng.

Thêm dependency:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

Cấu hình:

```properties
management.endpoints.web.exposure.include=health,info,metrics
```

Endpoint thường gặp:

- `/actuator/health`
- `/actuator/info`
- `/actuator/metrics`
- `/actuator/env`
- `/actuator/beans`

Không nên public toàn bộ actuator endpoint ở production.

## 18. Quy Trình Xây Dựng REST API Chuẩn

Một workflow thực tế:

1. Xác định tài nguyên API, ví dụ `User`, `Product`, `Order`.
2. Thiết kế endpoint.
3. Tạo entity.
4. Tạo repository.
5. Tạo request DTO và response DTO.
6. Viết service xử lý nghiệp vụ.
7. Viết controller.
8. Thêm validation.
9. Thêm exception handler.
10. Viết test.

Ví dụ endpoint:

```text
GET    /api/users
GET    /api/users/{id}
POST   /api/users
PUT    /api/users/{id}
DELETE /api/users/{id}
```

## 19. Những Lỗi Người Mới Hay Gặp

### Không scan được bean

Nguyên nhân thường gặp:

- Class nằm ngoài package gốc của `@SpringBootApplication`.
- Thiếu annotation như `@Service`, `@Repository`.

### Lỗi circular dependency

Ví dụ:

```text
A depends on B
B depends on A
```

Cách xử lý:

- Tách lại trách nhiệm class.
- Dùng event.
- Tạo service trung gian nếu hợp lý.

### Lazy loading exception

Thường xảy ra khi truy cập quan hệ JPA sau khi session đã đóng.

Cách xử lý:

- Dùng DTO projection.
- Dùng fetch join.
- Thiết kế transaction đúng.
- Tránh trả entity trực tiếp.

### N+1 query

Ví dụ lấy danh sách order, mỗi order lại query customer riêng.

Cách xử lý:

- `JOIN FETCH`
- `@EntityGraph`
- DTO projection.

## 20. Câu Hỏi Phỏng Vấn Spring Boot

### 1. Spring Boot khác gì Spring Framework?

Spring Framework là nền tảng lớn để xây dựng ứng dụng Java. Spring Boot xây trên Spring Framework và giúp giảm cấu hình, cung cấp auto configuration, starter dependency, embedded server và actuator.

### 2. `@SpringBootApplication` gồm những gì?

Gồm:

- `@Configuration`
- `@EnableAutoConfiguration`
- `@ComponentScan`

### 3. Auto Configuration hoạt động như thế nào?

Spring Boot kiểm tra classpath, bean hiện có và các property cấu hình để tự động tạo bean phù hợp. Ví dụ nếu có dependency Spring MVC, Spring Boot tự cấu hình DispatcherServlet, Jackson, embedded Tomcat.

### 4. Starter dependency là gì?

Starter là dependency gom sẵn các thư viện cần thiết cho một chức năng.

Ví dụ:

- `spring-boot-starter-web`
- `spring-boot-starter-data-jpa`
- `spring-boot-starter-security`
- `spring-boot-starter-test`

### 5. Bean là gì?

Bean là object do Spring IoC Container quản lý.

### 6. IoC và DI là gì?

IoC là nguyên lý đảo ngược quyền kiểm soát việc tạo và quản lý object cho container. DI là cách hiện thực IoC bằng việc inject dependency vào class.

### 7. Nên dùng field injection hay constructor injection?

Nên dùng constructor injection vì rõ dependency, dễ test, hỗ trợ `final` field và tránh object thiếu dependency.

### 8. `@Controller` khác gì `@RestController`?

`@Controller` thường dùng cho MVC trả view. `@RestController` dùng cho REST API và tự động serialize object thành JSON/XML.

### 9. `@Component`, `@Service`, `@Repository` khác nhau thế nào?

Tất cả đều là stereotype annotation để Spring phát hiện bean.

- `@Component`: chung chung.
- `@Service`: tầng nghiệp vụ.
- `@Repository`: tầng truy cập dữ liệu, có hỗ trợ dịch exception persistence.

### 10. `@Transactional` đặt ở đâu?

Thường đặt ở service layer, nơi chứa một use case nghiệp vụ cần tính toàn vẹn transaction.

### 11. `JpaRepository` khác `CrudRepository` thế nào?

`CrudRepository` cung cấp CRUD cơ bản. `JpaRepository` mở rộng thêm nhiều chức năng JPA như flush, batch delete, paging và sorting.

### 12. Làm sao xử lý exception tập trung?

Dùng `@ControllerAdvice` kết hợp `@ExceptionHandler`.

### 13. Làm sao validate request body?

Dùng Bean Validation annotation trong DTO và thêm `@Valid` ở controller.

### 14. Profile dùng để làm gì?

Profile dùng để tách cấu hình theo môi trường như dev, test, staging, production.

### 15. Actuator dùng để làm gì?

Actuator cung cấp endpoint theo dõi sức khỏe, metrics và thông tin runtime của ứng dụng.

## 21. Lộ Trình Học Gợi Ý

Nếu bạn mới học, đi theo thứ tự này:

1. Java core: OOP, collection, exception, stream, lambda.
2. Maven hoặc Gradle.
3. HTTP, REST, JSON.
4. Spring Core: bean, IoC, DI.
5. Spring Boot Web.
6. Spring Data JPA.
7. Validation và exception handling.
8. Spring Security và JWT.
9. Testing.
10. Docker, deployment, logging, monitoring.

## 22. Bài Tập Thực Hành

### Bài 1: Todo API

Tạo REST API quản lý công việc:

```text
GET    /api/todos
GET    /api/todos/{id}
POST   /api/todos
PUT    /api/todos/{id}
DELETE /api/todos/{id}
```

Todo gồm:

- `id`
- `title`
- `description`
- `completed`
- `createdAt`

Yêu cầu:

- Dùng Spring Data JPA.
- Validate title không được rỗng.
- Dùng DTO.
- Xử lý lỗi không tìm thấy todo.

### Bài 2: User API

Tạo API quản lý user:

- Đăng ký user.
- Lấy danh sách user.
- Tìm user theo email.
- Không cho trùng email.
- Mã hóa password bằng BCrypt.

### Bài 3: Product API Có Phân Trang

Endpoint:

```text
GET /api/products?page=0&size=10&sort=name,asc
```

Yêu cầu:

- Dùng `Pageable`.
- Trả response có `content`, `page`, `size`, `totalElements`, `totalPages`.

## 23. Checklist Ôn Phỏng Vấn Nhanh

Trước khi đi phỏng vấn Spring Boot, bạn nên chắc các ý sau:

- Giải thích được Spring Boot là gì.
- Biết `@SpringBootApplication`.
- Biết auto configuration và starter.
- Biết tạo REST API.
- Biết controller, service, repository.
- Biết Spring Data JPA cơ bản.
- Biết DTO và validation.
- Biết exception handling bằng `@ControllerAdvice`.
- Biết transaction.
- Biết profile.
- Biết Spring Security cơ bản.
- Biết viết unit test và controller test.
- Biết các lỗi phổ biến như N+1 query, lazy loading, circular dependency.

## 24. Tóm Tắt

Spring Boot giúp xây dựng ứng dụng Java nhanh, gọn và dễ triển khai hơn Spring truyền thống. Để dùng tốt Spring Boot, bạn cần hiểu rõ IoC, DI, bean, REST API, JPA, validation, exception handling, transaction và security.

Khi học, đừng chỉ đọc lý thuyết. Hãy tự xây ít nhất một REST API hoàn chỉnh có database, validation, exception handling, test và security. Đó là cách nhanh nhất để biến kiến thức Spring Boot thành kỹ năng thật.
