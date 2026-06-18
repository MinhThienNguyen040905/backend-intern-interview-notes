# HTTP/RESTful API cho phỏng vấn Intern Backend

Tài liệu này tổng hợp kiến thức HTTP và RESTful API thường gặp khi phỏng vấn intern backend. Mục tiêu là hiểu cách client-server giao tiếp, thiết kế endpoint hợp lý, dùng đúng HTTP method/status code và tránh các lỗi phổ biến khi xây API.

## 1. HTTP là gì?

HTTP, viết tắt của HyperText Transfer Protocol, là giao thức request-response dùng để client và server giao tiếp với nhau.

Ví dụ:

- Browser gọi API backend.
- Mobile app gọi API backend.
- Frontend React/Vue gọi API qua `fetch` hoặc `axios`.
- Service này gọi service khác qua HTTP.

Luồng cơ bản:

1. Client gửi HTTP request.
2. Server xử lý request.
3. Server trả HTTP response.

Ví dụ request:

```http
GET /api/users/1 HTTP/1.1
Host: example.com
Accept: application/json
Authorization: Bearer <token>
```

Ví dụ response:

```http
HTTP/1.1 200 OK
Content-Type: application/json

{
  "id": 1,
  "email": "alice@example.com",
  "fullName": "Alice"
}
```

## 2. HTTP vs HTTPS

HTTP truyền dữ liệu dạng plain text, có thể bị nghe lén hoặc chỉnh sửa nếu đi qua mạng không an toàn.

HTTPS là HTTP chạy trên TLS/SSL, giúp:

- Mã hóa dữ liệu.
- Xác thực server.
- Giảm nguy cơ bị nghe lén hoặc man-in-the-middle.

Trong production, API gần như luôn phải dùng HTTPS.

Câu trả lời phỏng vấn:

> HTTP là giao thức giao tiếp client-server. HTTPS là HTTP có TLS, giúp mã hóa dữ liệu và an toàn hơn khi truyền token, password hoặc thông tin nhạy cảm.

## 3. Cấu trúc HTTP request

Một HTTP request thường gồm:

- Request line.
- Headers.
- Body, nếu method cần gửi dữ liệu.

Ví dụ:

```http
POST /api/users HTTP/1.1
Host: example.com
Content-Type: application/json
Authorization: Bearer <token>

{
  "email": "alice@example.com",
  "password": "secret"
}
```

Trong đó:

- `POST`: HTTP method.
- `/api/users`: path/endpoint.
- `Content-Type`: kiểu dữ liệu body gửi lên.
- `Authorization`: thông tin xác thực.
- Body là JSON chứa dữ liệu tạo user.

## 4. Cấu trúc HTTP response

Một HTTP response thường gồm:

- Status line.
- Headers.
- Body.

Ví dụ:

```http
HTTP/1.1 201 Created
Content-Type: application/json
Location: /api/users/1

{
  "id": 1,
  "email": "alice@example.com"
}
```

Trong đó:

- `201 Created`: status code báo tạo resource thành công.
- `Content-Type`: kiểu body server trả về.
- `Location`: URL của resource vừa được tạo.

## 5. HTTP method

HTTP method mô tả hành động muốn thực hiện trên resource.

| Method | Ý nghĩa thường dùng |
|---|---|
| `GET` | Lấy dữ liệu |
| `POST` | Tạo mới hoặc thực hiện action |
| `PUT` | Thay thế toàn bộ resource |
| `PATCH` | Cập nhật một phần resource |
| `DELETE` | Xóa resource |
| `HEAD` | Giống GET nhưng chỉ lấy header |
| `OPTIONS` | Hỏi server hỗ trợ method/header nào |

## 5.1 GET

Dùng để đọc dữ liệu.

```http
GET /api/users/1
```

Đặc điểm:

- Không nên làm thay đổi dữ liệu server.
- Có thể cache.
- Dữ liệu lọc thường nằm trong query string.

Ví dụ:

```http
GET /api/products?category=book&page=1&size=20
```

Không nên:

```http
GET /api/delete-user?id=1
```

Vì GET không nên dùng để xóa hoặc thay đổi dữ liệu.

## 5.2 POST

Dùng để tạo resource hoặc thực hiện action không map đẹp sang CRUD.

```http
POST /api/users
Content-Type: application/json

{
  "email": "alice@example.com",
  "password": "secret"
}
```

Response thường là:

```http
201 Created
```

POST cũng có thể dùng cho action:

```http
POST /api/orders/123/cancel
```

## 5.3 PUT

Dùng để thay thế toàn bộ resource.

```http
PUT /api/users/1
Content-Type: application/json

{
  "email": "new@example.com",
  "fullName": "Alice Nguyen"
}
```

Nếu field bị thiếu, có thể được hiểu là xóa/reset field đó, tùy API contract.

## 5.4 PATCH

Dùng để cập nhật một phần resource.

```http
PATCH /api/users/1
Content-Type: application/json

{
  "fullName": "Alice Nguyen"
}
```

Khác PUT:

- `PUT`: replace toàn bộ resource.
- `PATCH`: update một phần.

## 5.5 DELETE

Dùng để xóa resource.

```http
DELETE /api/users/1
```

Response có thể là:

```http
204 No Content
```

Trong backend, DELETE có thể là:

- Hard delete: xóa thật khỏi database.
- Soft delete: set `deleted_at`.

## 6. Safe và idempotent

## 6.1 Safe method

Safe nghĩa là method không làm thay đổi state server theo ý nghĩa nghiệp vụ.

Safe methods:

- `GET`
- `HEAD`
- `OPTIONS`

Ví dụ `GET /api/users/1` chỉ đọc user, không sửa user.

Lưu ý: server vẫn có thể ghi log, metric, access time. Nhưng không được thay đổi resource theo nghiệp vụ.

## 6.2 Idempotent method

Idempotent nghĩa là gọi một lần hay nhiều lần thì kết quả cuối cùng trên server như nhau.

Idempotent methods thường gồm:

- `GET`
- `PUT`
- `DELETE`
- `HEAD`
- `OPTIONS`

Ví dụ:

```http
DELETE /api/users/1
```

Gọi nhiều lần thì user vẫn ở trạng thái đã bị xóa.

`POST` thường không idempotent:

```http
POST /api/orders
```

Gọi nhiều lần có thể tạo nhiều order.

Câu trả lời phỏng vấn:

> Safe nói về việc method có làm thay đổi state nghiệp vụ không. Idempotent nói về việc gọi lặp lại nhiều lần có tạo ra kết quả cuối cùng giống nhau không.

## 7. Status code

HTTP status code cho client biết kết quả request.

## 7.1 Nhóm 2xx - Success

| Code | Ý nghĩa |
|---|---|
| `200 OK` | Request thành công |
| `201 Created` | Tạo resource thành công |
| `202 Accepted` | Đã nhận request, xử lý sau |
| `204 No Content` | Thành công, không có body |

Ví dụ:

- `GET /api/users/1` thành công: `200 OK`.
- `POST /api/users` tạo thành công: `201 Created`.
- `DELETE /api/users/1` thành công không cần body: `204 No Content`.

## 7.2 Nhóm 3xx - Redirection

| Code | Ý nghĩa |
|---|---|
| `301 Moved Permanently` | Resource chuyển vĩnh viễn |
| `302 Found` | Redirect tạm thời |
| `304 Not Modified` | Dùng cache, dữ liệu chưa đổi |

Với API backend, nhóm 3xx ít được hỏi hơn nhưng cần biết `304` liên quan caching.

## 7.3 Nhóm 4xx - Client error

| Code | Ý nghĩa |
|---|---|
| `400 Bad Request` | Request sai format/invalid input |
| `401 Unauthorized` | Chưa xác thực hoặc token sai |
| `403 Forbidden` | Đã xác thực nhưng không có quyền |
| `404 Not Found` | Không tìm thấy resource |
| `405 Method Not Allowed` | Method không được hỗ trợ |
| `409 Conflict` | Xung đột trạng thái dữ liệu |
| `422 Unprocessable Entity` | Dữ liệu hợp lệ về format nhưng sai rule nghiệp vụ/validation |
| `429 Too Many Requests` | Bị rate limit |

Ví dụ:

- Email sai format: `400` hoặc `422`, tùy convention.
- Chưa gửi token: `401`.
- User thường gọi API admin: `403`.
- User id không tồn tại: `404`.
- Đăng ký email đã tồn tại: `409`.

## 7.4 Nhóm 5xx - Server error

| Code | Ý nghĩa |
|---|---|
| `500 Internal Server Error` | Lỗi server chung |
| `502 Bad Gateway` | Gateway/proxy nhận response lỗi từ upstream |
| `503 Service Unavailable` | Server tạm thời không sẵn sàng |
| `504 Gateway Timeout` | Gateway/proxy chờ upstream quá lâu |

Nguyên tắc:

- 4xx: lỗi do request/client.
- 5xx: lỗi phía server/hạ tầng.

Không nên trả `200 OK` cho request thất bại rồi nhét lỗi trong body.

Không tốt:

```http
HTTP/1.1 200 OK

{
  "success": false,
  "message": "User not found"
}
```

Tốt hơn:

```http
HTTP/1.1 404 Not Found

{
  "code": "USER_NOT_FOUND",
  "message": "User not found"
}
```

## 8. Header

Header chứa metadata của request/response.

Header thường gặp:

| Header | Ý nghĩa |
|---|---|
| `Content-Type` | Kiểu dữ liệu body gửi đi |
| `Accept` | Kiểu dữ liệu client muốn nhận |
| `Authorization` | Thông tin xác thực |
| `Cache-Control` | Chính sách cache |
| `User-Agent` | Thông tin client |
| `Location` | URL resource mới hoặc redirect |
| `Set-Cookie` | Server set cookie |
| `Cookie` | Client gửi cookie |
| `X-Request-Id` | ID trace request |

Ví dụ JSON request:

```http
Content-Type: application/json
Accept: application/json
```

Ví dụ bearer token:

```http
Authorization: Bearer eyJhbGciOi...
```

## 9. Body và content type

Body chứa dữ liệu gửi kèm request hoặc response.

Content type thường gặp:

| Content-Type | Dùng khi |
|---|---|
| `application/json` | API JSON phổ biến |
| `application/x-www-form-urlencoded` | HTML form đơn giản |
| `multipart/form-data` | Upload file |
| `text/plain` | Plain text |
| `application/xml` | XML |

Ví dụ JSON:

```json
{
  "email": "alice@example.com",
  "password": "secret"
}
```

Ví dụ upload file:

```http
POST /api/users/1/avatar
Content-Type: multipart/form-data
```

## 10. Query parameter, path variable, body

## 10.1 Path variable

Dùng để định danh resource.

```http
GET /api/users/1
```

`1` là user id.

## 10.2 Query parameter

Dùng để filter, sort, search, pagination.

```http
GET /api/products?category=book&sort=price_desc&page=1&size=20
```

## 10.3 Body

Dùng để gửi dữ liệu tạo/cập nhật.

```http
POST /api/users
Content-Type: application/json

{
  "email": "alice@example.com",
  "password": "secret"
}
```

Nguyên tắc:

- ID resource chính: path.
- Filter/sort/pagination: query.
- Dữ liệu tạo/cập nhật phức tạp: body.

## 11. REST là gì?

REST, viết tắt của Representational State Transfer, là một phong cách kiến trúc để thiết kế API dựa trên resource.

RESTful API thường:

- Xem dữ liệu/chức năng như resource.
- Dùng URL để định danh resource.
- Dùng HTTP method để mô tả action.
- Dùng status code đúng nghĩa.
- Stateless giữa các request.
- Trả representation của resource, thường là JSON.

Ví dụ resource `users`:

```http
GET /api/users
GET /api/users/1
POST /api/users
PUT /api/users/1
PATCH /api/users/1
DELETE /api/users/1
```

## 12. Resource naming

Tên endpoint nên dùng danh từ, không dùng động từ CRUD.

Không tốt:

```http
GET /api/getUsers
POST /api/createUser
POST /api/deleteUser
```

Tốt hơn:

```http
GET /api/users
POST /api/users
DELETE /api/users/1
```

Nguyên tắc:

- Dùng plural noun: `/users`, `/orders`, `/products`.
- Dùng nesting khi thể hiện quan hệ rõ.
- Không nesting quá sâu.

Ví dụ:

```http
GET /api/users/1/orders
GET /api/orders/100/items
```

Nesting quá sâu không tốt:

```http
GET /api/companies/1/departments/2/teams/3/users/4/orders
```

Có thể dùng filter:

```http
GET /api/orders?userId=4
```

## 13. RESTful CRUD mapping

| Use case | Endpoint | Method | Status thường dùng |
|---|---|---|---|
| Lấy danh sách user | `/api/users` | `GET` | `200` |
| Lấy user theo id | `/api/users/{id}` | `GET` | `200`, `404` |
| Tạo user | `/api/users` | `POST` | `201`, `400`, `409` |
| Replace user | `/api/users/{id}` | `PUT` | `200`, `204`, `404` |
| Update một phần user | `/api/users/{id}` | `PATCH` | `200`, `204`, `404` |
| Xóa user | `/api/users/{id}` | `DELETE` | `204`, `404` |

## 14. Action endpoint trong REST

Không phải hành động nào cũng map đẹp sang CRUD.

Ví dụ:

- Cancel order.
- Confirm email.
- Reset password.
- Login/logout.
- Pay order.

Có thể thiết kế:

```http
POST /api/orders/123/cancel
POST /api/orders/123/pay
POST /api/auth/login
POST /api/auth/logout
POST /api/password-reset-requests
```

Lưu ý:

- Dùng action endpoint khi đó thật sự là action nghiệp vụ.
- Không biến mọi thứ thành `/doSomething`.
- Nếu action tạo resource mới, cân nhắc đặt tên resource.

Ví dụ reset password:

```http
POST /api/password-reset-requests
```

Thay vì:

```http
POST /api/sendResetPasswordEmail
```

## 15. Stateless

REST thường yêu cầu stateless: mỗi request phải chứa đủ thông tin để server xử lý, server không phụ thuộc vào trạng thái request trước đó.

Ví dụ:

```http
Authorization: Bearer <token>
```

Mỗi request gửi token để server xác thực.

Ý nghĩa:

- Server dễ scale ngang.
- Không cần lưu session state theo từng client trên memory của một server cụ thể.
- Load balancer có thể chuyển request sang server khác.

Lưu ý:

- Stateless không có nghĩa server không lưu dữ liệu gì. Server vẫn lưu user, order, token blacklist, database state.
- Stateless nói về việc server không cần nhớ conversational state giữa các HTTP request.

## 16. JSON response design

API nên trả response nhất quán.

Ví dụ single resource:

```json
{
  "id": 1,
  "email": "alice@example.com",
  "fullName": "Alice"
}
```

Ví dụ list:

```json
{
  "data": [
    {
      "id": 1,
      "email": "alice@example.com"
    }
  ],
  "pagination": {
    "page": 1,
    "size": 20,
    "totalItems": 100,
    "totalPages": 5
  }
}
```

Không nên trả field nhạy cảm:

```json
{
  "id": 1,
  "email": "alice@example.com",
  "passwordHash": "$2a$..."
}
```

## 17. Error response design

Error response nên nhất quán và đủ thông tin để client xử lý.

Ví dụ:

```json
{
  "code": "USER_NOT_FOUND",
  "message": "User not found",
  "details": null,
  "requestId": "req-123"
}
```

Validation error:

```json
{
  "code": "VALIDATION_ERROR",
  "message": "Invalid request data",
  "errors": [
    {
      "field": "email",
      "message": "Email is invalid"
    },
    {
      "field": "password",
      "message": "Password must be at least 8 characters"
    }
  ],
  "requestId": "req-123"
}
```

Nguyên tắc:

- Không expose stack trace ra client.
- Có error code ổn định cho frontend/mobile xử lý.
- Message nên rõ nhưng không lộ thông tin nhạy cảm.
- Log server nên có request id để trace.

## 18. Authentication và Authorization

## 18.1 Authentication

Authentication là xác thực bạn là ai.

Ví dụ:

- Login bằng email/password.
- Gửi JWT access token.
- Session cookie.

## 18.2 Authorization

Authorization là kiểm tra bạn được phép làm gì.

Ví dụ:

- User thường không được gọi API admin.
- User chỉ được sửa profile của chính họ.
- Seller chỉ được quản lý sản phẩm của shop mình.

So sánh:

| Khái niệm | Câu hỏi |
|---|---|
| Authentication | Bạn là ai? |
| Authorization | Bạn có quyền làm việc này không? |

Ví dụ:

- Không gửi token: `401 Unauthorized`.
- Gửi token hợp lệ nhưng không có quyền admin: `403 Forbidden`.

## 19. Cookie, Session, JWT

## 19.1 Cookie

Cookie là dữ liệu nhỏ được browser lưu và tự gửi kèm request đến domain phù hợp.

```http
Set-Cookie: sessionId=abc123; HttpOnly; Secure; SameSite=Lax
```

Flag quan trọng:

- `HttpOnly`: JavaScript không đọc được cookie, giảm rủi ro XSS lấy cookie.
- `Secure`: chỉ gửi qua HTTPS.
- `SameSite`: giảm rủi ro CSRF.

## 19.2 Session

Session thường là cơ chế server lưu trạng thái đăng nhập, client giữ session id trong cookie.

Luồng:

1. User login.
2. Server tạo session trong database/Redis/memory.
3. Server gửi `sessionId` qua cookie.
4. Client gửi cookie trong các request sau.
5. Server lookup session để biết user là ai.

Ưu điểm:

- Dễ revoke.
- Server kiểm soát session tốt.

Nhược điểm:

- Server phải lưu session state.
- Cần shared session store khi scale nhiều instance.

## 19.3 JWT

JWT, viết tắt của JSON Web Token, là token chứa claims được ký số.

Ví dụ payload:

```json
{
  "sub": "1",
  "email": "alice@example.com",
  "role": "USER",
  "exp": 1790000000
}
```

Client gửi:

```http
Authorization: Bearer <jwt>
```

Ưu điểm:

- Server có thể verify token mà không cần lookup session mỗi request.
- Phù hợp stateless API.

Nhược điểm:

- Khó revoke ngay lập tức nếu không có blacklist/session store.
- Token bị lộ thì dùng được đến khi hết hạn.
- Không nên nhét dữ liệu nhạy cảm vào payload vì payload chỉ base64url encoded, không phải mặc định được mã hóa.

Câu trả lời phỏng vấn:

> JWT nên có thời hạn ngắn cho access token. Nếu cần revoke hoặc refresh, thường dùng refresh token và lưu/kiểm soát ở server.

## 20. CORS

CORS, viết tắt của Cross-Origin Resource Sharing, là cơ chế browser kiểm soát việc frontend ở origin này gọi API ở origin khác.

Origin gồm:

- Scheme: `http` hoặc `https`.
- Host.
- Port.

Ví dụ khác origin:

- Frontend: `http://localhost:3000`
- API: `http://localhost:8080`

Browser sẽ kiểm tra CORS.

Header thường gặp:

```http
Access-Control-Allow-Origin: http://localhost:3000
Access-Control-Allow-Methods: GET, POST, PUT, DELETE
Access-Control-Allow-Headers: Content-Type, Authorization
```

Preflight request:

```http
OPTIONS /api/users
```

Lưu ý:

- CORS là cơ chế của browser, không phải database hay backend security đầy đủ.
- Server-to-server request thường không bị browser CORS chặn.
- Không nên dùng `Access-Control-Allow-Origin: *` cho API cần credential/cookie.

## 21. CSRF và XSS ở mức API

## 21.1 CSRF

CSRF là tấn công khiến browser của user đã đăng nhập gửi request không mong muốn đến server.

Thường liên quan cookie-based auth vì browser tự gửi cookie.

Cách giảm rủi ro:

- CSRF token.
- `SameSite` cookie.
- Kiểm tra origin/referer.
- Dùng method đúng, không thay đổi dữ liệu bằng GET.

## 21.2 XSS

XSS là tấn công inject JavaScript độc hại vào trang.

Với API/backend:

- Validate/sanitize input khi cần.
- Encode output ở frontend.
- Dùng `HttpOnly` cookie để JS không đọc được token trong cookie.
- Không trả HTML/script không kiểm soát.

## 22. Rate limiting

Rate limiting giới hạn số request trong một khoảng thời gian.

Ví dụ:

- 100 request/phút/IP.
- 5 lần login sai/phút/user.

Nếu vượt quá:

```http
429 Too Many Requests
```

Dùng để:

- Giảm brute force login.
- Bảo vệ API khỏi abuse.
- Bảo vệ tài nguyên hệ thống.

Có thể lưu counter trong:

- Redis.
- API gateway.
- Load balancer.
- Application memory, chỉ phù hợp demo/single instance.

## 23. Pagination

Pagination giúp API trả dữ liệu theo trang thay vì trả toàn bộ.

## 23.1 Offset pagination

```http
GET /api/products?page=2&size=20
```

Hoặc:

```http
GET /api/products?limit=20&offset=40
```

Response:

```json
{
  "data": [],
  "pagination": {
    "page": 2,
    "size": 20,
    "totalItems": 100,
    "totalPages": 5
  }
}
```

Ưu điểm:

- Dễ hiểu.
- Dễ nhảy đến page bất kỳ.

Nhược điểm:

- Page sâu có thể chậm.
- Dữ liệu thay đổi có thể gây trùng/thiếu item.

## 23.2 Cursor pagination

```http
GET /api/products?limit=20&cursor=eyJpZCI6MTAwfQ
```

Response:

```json
{
  "data": [],
  "nextCursor": "eyJpZCI6MTIwfQ"
}
```

Ưu điểm:

- Tốt cho dữ liệu lớn.
- Phù hợp infinite scroll/feed.
- Ổn định hơn khi dữ liệu thay đổi.

Nhược điểm:

- Khó nhảy trực tiếp đến page bất kỳ.
- Cần thiết kế sort key ổn định.

## 24. Filtering và sorting

Filtering:

```http
GET /api/orders?status=PAID&userId=1
```

Sorting:

```http
GET /api/products?sort=price_desc
```

Hoặc:

```http
GET /api/products?sort=price,desc
```

Nguyên tắc:

- Validate field sort/filter.
- Không cho client truyền tùy ý tên cột SQL nếu chưa kiểm soát.
- Index các field filter/sort phổ biến.

## 25. API versioning

API versioning giúp thay đổi API mà không phá client cũ.

Cách phổ biến:

## 25.1 Version trong URL

```http
GET /api/v1/users
GET /api/v2/users
```

Dễ hiểu, dễ debug.

## 25.2 Version trong header

```http
Accept: application/vnd.example.v2+json
```

Sạch URL hơn nhưng phức tạp hơn cho client mới.

Với intern/backend phổ thông, dùng URL versioning thường dễ giải thích nhất.

Nguyên tắc:

- Không breaking change tùy tiện.
- Thêm field mới thường ít nguy hiểm hơn đổi/xóa field.
- Nếu đổi behavior lớn, cân nhắc version mới.

## 26. Backward compatibility

Backward compatibility nghĩa là client cũ vẫn chạy được khi server nâng cấp.

Thay đổi thường an toàn:

- Thêm field mới vào response.
- Thêm endpoint mới.
- Thêm optional request field.

Thay đổi dễ phá client:

- Xóa field response.
- Đổi tên field.
- Đổi type field.
- Đổi ý nghĩa status code.
- Biến optional field thành required.

## 27. Caching

HTTP hỗ trợ caching để giảm tải server và tăng tốc response.

Header thường gặp:

```http
Cache-Control: max-age=60
```

```http
ETag: "abc123"
```

Client có thể gửi:

```http
If-None-Match: "abc123"
```

Nếu dữ liệu chưa đổi:

```http
304 Not Modified
```

API nào nên cache:

- Danh mục ít đổi.
- Public product listing.
- Config công khai.

API nào không nên cache bừa:

- Thông tin cá nhân.
- Dữ liệu nhạy cảm.
- Response phụ thuộc quyền user.

## 28. File upload/download

Upload file thường dùng:

```http
POST /api/files
Content-Type: multipart/form-data
```

Backend cần kiểm tra:

- Kích thước file.
- Loại file/MIME type.
- Virus/malware nếu cần.
- Tên file không được tin tuyệt đối.
- Không lưu file upload vào path tùy ý từ user.

Download file:

```http
GET /api/files/123
```

Header thường dùng:

```http
Content-Disposition: attachment; filename="report.pdf"
Content-Type: application/pdf
```

## 29. Logging và tracing

Backend API nên log thông tin đủ để debug nhưng không lộ dữ liệu nhạy cảm.

Nên log:

- Request id.
- Method/path.
- Status code.
- Latency.
- User id nếu đã xác thực.
- Error stack ở server log.

Không nên log:

- Password.
- Access token/refresh token.
- Full credit card number.
- Secret key.

Request id:

```http
X-Request-Id: req-123
```

Khi có lỗi, response có thể trả:

```json
{
  "code": "INTERNAL_ERROR",
  "message": "Unexpected error",
  "requestId": "req-123"
}
```

## 30. Timeout và retry

Client gọi API nên có timeout. Nếu không, request có thể treo lâu.

Retry cần cẩn thận:

- Retry `GET` thường an toàn hơn.
- Retry `POST` tạo order/payment có thể tạo trùng nếu không có idempotency key.

Idempotency key:

```http
Idempotency-Key: 8f2a7c8e-9d0a-4f2b-9c1e-123456
```

Dùng cho:

- Payment.
- Create order.
- Tác vụ quan trọng có thể bị retry.

Server lưu key để biết request đã xử lý chưa và tránh tạo trùng.

## 31. API documentation

API nên có documentation rõ:

- Endpoint.
- Method.
- Auth required hay không.
- Request body.
- Query/path params.
- Response success.
- Error response.
- Status code.

Tool thường gặp:

- OpenAPI/Swagger.
- Postman collection.
- Redoc.

Ví dụ OpenAPI mô tả contract để frontend/backend thống nhất.

## 32. REST vs RPC vs GraphQL

## 32.1 REST

Thiết kế quanh resource và HTTP method.

```http
GET /api/users/1
POST /api/orders
```

## 32.2 RPC

Thiết kế quanh action/procedure.

```http
POST /api/createUser
POST /api/cancelOrder
```

Không phải RPC luôn xấu. Nhưng nếu nói RESTful thì nên ưu tiên resource.

## 32.3 GraphQL

Client query đúng dữ liệu cần lấy qua một endpoint.

```graphql
query {
  user(id: 1) {
    id
    email
    orders {
      id
    }
  }
}
```

Ưu điểm:

- Client chọn field cần lấy.
- Giảm over-fetching/under-fetching.

Nhược điểm:

- Backend phức tạp hơn.
- Cần kiểm soát performance, N+1, authorization.

Với intern REST API, chỉ cần hiểu REST khác RPC/GraphQL ở mức khái niệm.

## 33. Thiết kế API ví dụ: User

```http
GET /api/v1/users?page=1&size=20
GET /api/v1/users/1
POST /api/v1/users
PATCH /api/v1/users/1
DELETE /api/v1/users/1
```

Create request:

```json
{
  "email": "alice@example.com",
  "password": "secret123",
  "fullName": "Alice"
}
```

Create response:

```http
201 Created
Location: /api/v1/users/1
```

```json
{
  "id": 1,
  "email": "alice@example.com",
  "fullName": "Alice",
  "createdAt": "2026-06-12T10:00:00Z"
}
```

Validation error:

```http
400 Bad Request
```

```json
{
  "code": "VALIDATION_ERROR",
  "message": "Invalid request data",
  "errors": [
    {
      "field": "email",
      "message": "Email is invalid"
    }
  ]
}
```

Duplicate email:

```http
409 Conflict
```

```json
{
  "code": "EMAIL_ALREADY_EXISTS",
  "message": "Email already exists"
}
```

## 34. Thiết kế API ví dụ: Order

Endpoints:

```http
GET /api/v1/orders
GET /api/v1/orders/123
POST /api/v1/orders
POST /api/v1/orders/123/cancel
POST /api/v1/orders/123/pay
```

Create order request:

```json
{
  "items": [
    {
      "productId": 10,
      "quantity": 2
    }
  ]
}
```

Create order response:

```http
201 Created
```

```json
{
  "id": 123,
  "status": "CREATED",
  "totalAmount": 200000,
  "items": [
    {
      "productId": 10,
      "quantity": 2,
      "unitPrice": 100000
    }
  ]
}
```

Cancel shipped order:

```http
409 Conflict
```

```json
{
  "code": "ORDER_ALREADY_SHIPPED",
  "message": "Cannot cancel shipped order"
}
```

## 35. Controller, service, repository trong API backend

Luồng thường gặp:

```text
HTTP Request
-> Controller
-> Service
-> Repository/External Client
-> Database/External API
-> Response
```

Controller nên:

- Nhận request.
- Parse path/query/body.
- Gọi validation cơ bản.
- Gọi service.
- Map result sang response DTO.

Service nên:

- Chứa business logic/use case.
- Quản lý transaction.
- Gọi repository/external client.
- Throw exception nghiệp vụ khi cần.

Repository nên:

- Truy cập database.
- Che giấu SQL/ORM detail khỏi service.

Không nên để controller chứa quá nhiều business logic.

## 36. Validation

Validation có nhiều lớp:

## 36.1 Request validation

Kiểm tra format input:

- Email đúng format.
- Password đủ độ dài.
- Quantity > 0.
- Required field không thiếu.

Sai thì trả `400 Bad Request` hoặc `422 Unprocessable Entity` theo convention.

## 36.2 Business validation

Kiểm tra rule nghiệp vụ:

- Email chưa tồn tại.
- Order chưa shipped thì mới được cancel.
- Stock đủ để mua.
- User có quyền sửa resource này.

Status thường gặp:

- Trùng email: `409 Conflict`.
- Không đủ quyền: `403 Forbidden`.
- Resource không tồn tại: `404 Not Found`.
- State conflict: `409 Conflict`.

## 37. DTO trong API

DTO giúp tách API contract khỏi entity/database model.

Ví dụ request DTO:

```java
class CreateUserRequest {
    private String email;
    private String password;
    private String fullName;
}
```

Response DTO:

```java
class UserResponse {
    private Long id;
    private String email;
    private String fullName;
}
```

Không nên trả thẳng entity nếu entity có:

- `passwordHash`
- Internal status.
- Lazy relation.
- Field không muốn expose.
- Cấu trúc phụ thuộc database.

## 38. Những lỗi thường gặp khi thiết kế API

- Dùng `GET` để thay đổi dữ liệu.
- Trả `200 OK` cho mọi lỗi.
- Endpoint dùng động từ CRUD quá nhiều như `/getUsers`, `/createUser`.
- Không phân biệt `401` và `403`.
- Không validate input.
- Trả stack trace ra client.
- Trả password hash/token/secret trong response.
- Không pagination cho list API.
- Không giới hạn page size.
- Không xử lý CORS đúng.
- Lưu token trong nơi dễ bị XSS lấy mà không hiểu rủi ro.
- Không dùng HTTPS ở production.
- Không có format error response nhất quán.
- Không có timeout khi gọi external API.
- Retry POST tạo payment/order nhưng không có idempotency key.
- Controller chứa quá nhiều business logic.

## 39. Câu hỏi phỏng vấn thường gặp

## 39.1 Câu hỏi HTTP cơ bản

1. HTTP là gì?
2. HTTP khác HTTPS như thế nào?
3. HTTP request gồm những phần nào?
4. HTTP response gồm những phần nào?
5. GET, POST, PUT, PATCH, DELETE khác nhau thế nào?
6. PUT khác PATCH như thế nào?
7. Safe method là gì?
8. Idempotent là gì?
9. Status code 2xx, 3xx, 4xx, 5xx nghĩa là gì?
10. `401` khác `403` như thế nào?
11. `400`, `404`, `409`, `422` khác nhau thế nào?
12. Header `Content-Type` và `Accept` khác nhau thế nào?

## 39.2 Câu hỏi RESTful API

1. REST là gì?
2. RESTful API thiết kế quanh resource nghĩa là gì?
3. Vì sao endpoint nên dùng danh từ thay vì động từ?
4. Stateless trong REST nghĩa là gì?
5. Thiết kế CRUD API cho user như thế nào?
6. Khi nào dùng action endpoint như `/orders/{id}/cancel`?
7. Query param khác path variable khác body như thế nào?
8. Pagination nên thiết kế thế nào?
9. API versioning dùng để làm gì?
10. Error response nên có format như thế nào?

## 39.3 Câu hỏi backend thực tế

1. Authentication khác authorization như thế nào?
2. Cookie/session khác JWT thế nào?
3. JWT có nhược điểm gì?
4. CORS là gì?
5. CSRF là gì?
6. Rate limiting dùng để làm gì?
7. Vì sao không nên trả entity trực tiếp ra API?
8. Controller, service, repository nên chia trách nhiệm thế nào?
9. Làm sao tránh duplicate order/payment khi client retry?
10. Những gì không nên log trong API?

## 40. Câu trả lời mẫu ngắn

### REST là gì?

REST là phong cách thiết kế API dựa trên resource. Mỗi resource có URL riêng, hành động được thể hiện bằng HTTP method như GET, POST, PUT, PATCH, DELETE và kết quả được thể hiện bằng status code phù hợp.

### GET khác POST?

GET dùng để lấy dữ liệu và không nên thay đổi state nghiệp vụ trên server. POST thường dùng để tạo resource hoặc thực hiện action, và thường không idempotent.

### PUT khác PATCH?

PUT thường dùng để thay thế toàn bộ resource. PATCH dùng để cập nhật một phần resource.

### 401 khác 403?

401 nghĩa là chưa xác thực hoặc token không hợp lệ. 403 nghĩa là đã xác thực nhưng không có quyền thực hiện hành động đó.

### Stateless là gì?

Stateless nghĩa là mỗi request phải chứa đủ thông tin để server xử lý, ví dụ gửi token trong header. Server không cần nhớ trạng thái hội thoại của request trước đó trong memory của một instance cụ thể.

### CORS là gì?

CORS là cơ chế của browser kiểm soát frontend ở origin này có được gọi API ở origin khác hay không. Server phải trả các header như `Access-Control-Allow-Origin` để browser cho phép request.

### JWT có an toàn không?

JWT an toàn nếu được ký đúng, truyền qua HTTPS, có thời hạn hợp lý và không chứa dữ liệu nhạy cảm trong payload. Payload JWT không nên xem là bí mật vì mặc định chỉ được encode, không phải encrypt.

### Vì sao cần pagination?

Pagination tránh việc API trả quá nhiều dữ liệu một lần, giúp giảm tải database, giảm latency và giúp client xử lý response tốt hơn.

## 41. Bài tập tự luyện

## Bài 1: Thiết kế User API

Yêu cầu:

- Tạo user.
- Lấy danh sách user có pagination.
- Lấy user theo id.
- Cập nhật full name.
- Xóa user.

Gợi ý:

```http
POST /api/v1/users
GET /api/v1/users?page=1&size=20
GET /api/v1/users/{id}
PATCH /api/v1/users/{id}
DELETE /api/v1/users/{id}
```

Nêu status code cho từng case:

- Tạo thành công.
- Email trùng.
- User không tồn tại.
- Input sai.

## Bài 2: Thiết kế Order API

Yêu cầu:

- Tạo order.
- Lấy order theo id.
- Hủy order.
- Thanh toán order.
- Không cho hủy order đã shipped.

Gợi ý:

```http
POST /api/v1/orders
GET /api/v1/orders/{id}
POST /api/v1/orders/{id}/cancel
POST /api/v1/orders/{id}/pay
```

Status code:

- Tạo thành công: `201`.
- Không tìm thấy: `404`.
- Không đủ stock: `409`.
- Không được hủy vì đã shipped: `409`.
- Chưa login: `401`.
- Không phải owner order: `403`.

## Bài 3: Thiết kế error response

Yêu cầu:

- Format lỗi chung.
- Format lỗi validation.
- Có request id.

Gợi ý:

```json
{
  "code": "VALIDATION_ERROR",
  "message": "Invalid request data",
  "errors": [
    {
      "field": "email",
      "message": "Email is invalid"
    }
  ],
  "requestId": "req-123"
}
```

## 42. Checklist trước khi đi phỏng vấn

Bạn nên tự trả lời được:

- HTTP request/response gồm gì.
- HTTP và HTTPS khác nhau thế nào.
- GET, POST, PUT, PATCH, DELETE dùng khi nào.
- Safe và idempotent là gì.
- Status code phổ biến: `200`, `201`, `204`, `400`, `401`, `403`, `404`, `409`, `422`, `429`, `500`.
- Header `Content-Type`, `Accept`, `Authorization`, `Cache-Control` dùng làm gì.
- Path variable, query param, body khác nhau thế nào.
- REST là gì và resource naming nên ra sao.
- Thiết kế CRUD API cho một resource.
- Stateless trong REST nghĩa là gì.
- Authentication khác authorization.
- Cookie/session/JWT khác nhau cơ bản.
- CORS là gì.
- Pagination, filtering, sorting.
- Error response design.
- API versioning và backward compatibility.
- Vì sao cần rate limiting, logging, request id.
- Controller/service/repository chia trách nhiệm thế nào trong API backend.

## 43. Tóm tắt cực ngắn

- HTTP là giao thức request-response giữa client và server.
- HTTPS là HTTP có TLS, nên dùng trong production.
- RESTful API thiết kế quanh resource, dùng URL là danh từ và HTTP method là hành động.
- GET để đọc, POST để tạo/action, PUT replace toàn bộ, PATCH update một phần, DELETE để xóa.
- Status code phải phản ánh đúng kết quả, không trả `200` cho mọi lỗi.
- `401` là chưa xác thực, `403` là không có quyền.
- API nên stateless, response/error format nhất quán, có pagination cho list.
- Auth, CORS, rate limiting, logging, timeout và idempotency là các phần quan trọng của backend API thực tế.
