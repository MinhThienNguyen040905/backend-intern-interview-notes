# Hướng dẫn trình bày các Mẫu thiết kế GoF (Gang of Four)

Tài liệu này quy định **cấu trúc thư mục** và **dàn ý chuẩn** để trình bày từng mẫu thiết kế (design pattern) trong môn học SE401 - Mẫu thiết kế.

---

## 1. Quy ước tổ chức thư mục

- Ở thư mục gốc (root) có file hướng dẫn này (`HUONG_DAN.md`).
- **Mỗi mẫu thiết kế được trình bày trong MỘT folder riêng**, đặt tại root.
- Tên folder theo định dạng: `NhomPhanLoai-TenMau` (không dấu, dùng PascalCase hoặc gạch nối), ví dụ:
  - `Creational-Singleton`
  - `Creational-FactoryMethod`
  - `Structural-Adapter`
  - `Behavioral-Observer`
- Bên trong mỗi folder bắt buộc có file `README.md` trình bày theo **dàn ý chuẩn** ở mục 3.
- **Mã nguồn minh họa viết bằng Java** (`.java`), đặt trong thư mục con `src/` của folder mẫu.
- Sơ đồ cấu trúc (UML/OMT): **ưu tiên vẽ bằng Mermaid** ngay trong `README.md` (GitHub render trực tiếp). Chỉ tạo thư mục `images/` khi thực sự dùng ảnh nhị phân (`.png`).

### Cây thư mục mẫu

```
DesignPattern/
├── HUONG_DAN.md                ← file này
├── Creational-Singleton/
│   ├── README.md               ← trình bày theo dàn ý (sơ đồ Mermaid nằm trong đây)
│   └── src/
│       ├── Singleton.java
│       └── Main.java
├── Creational-FactoryMethod/
│   ├── README.md
│   └── src/
├── Structural-Adapter/
│   ├── README.md
│   └── src/
└── Behavioral-Observer/
    ├── README.md
    └── src/
```

---

## 2. Phân loại 23 mẫu GoF (gợi ý đặt tên folder)

### Creational (Khởi tạo) — 5 mẫu
| Mẫu | Folder gợi ý |
|-----|--------------|
| Abstract Factory | `Creational-AbstractFactory` |
| Builder | `Creational-Builder` |
| Factory Method | `Creational-FactoryMethod` |
| Prototype | `Creational-Prototype` |
| Singleton | `Creational-Singleton` |

### Structural (Cấu trúc) — 7 mẫu
| Mẫu | Folder gợi ý |
|-----|--------------|
| Adapter | `Structural-Adapter` |
| Bridge | `Structural-Bridge` |
| Composite | `Structural-Composite` |
| Decorator | `Structural-Decorator` |
| Facade | `Structural-Facade` |
| Flyweight | `Structural-Flyweight` |
| Proxy | `Structural-Proxy` |

### Behavioral (Hành vi) — 11 mẫu
| Mẫu | Folder gợi ý |
|-----|--------------|
| Chain of Responsibility | `Behavioral-ChainOfResponsibility` |
| Command | `Behavioral-Command` |
| Interpreter | `Behavioral-Interpreter` |
| Iterator | `Behavioral-Iterator` |
| Mediator | `Behavioral-Mediator` |
| Memento | `Behavioral-Memento` |
| Observer | `Behavioral-Observer` |
| State | `Behavioral-State` |
| Strategy | `Behavioral-Strategy` |
| Template Method | `Behavioral-TemplateMethod` |
| Visitor | `Behavioral-Visitor` |

---

## 3. Dàn ý chuẩn cho mỗi mẫu (`README.md` trong từng folder)

Mỗi file `README.md` của một mẫu trình bày lần lượt **13 mục** sau:

1. **Tên và phân loại** — Tên mẫu và nhóm phân loại của nó (Creational / Structural / Behavioral).
2. **Mục đích, ý định** — Mô tả ngắn gọn mẫu này dùng để làm gì.
3. **Bí danh** — Các tên gọi khác của mẫu (nếu có).
4. **Motivation (Động cơ)** — Trình bày một tình huống thiết kế phần mềm cụ thể dẫn đến việc cần dùng mẫu này để giải quyết vấn đề.
5. **Khả năng ứng dụng** — Gợi ý những tình huống thiết kế có thể áp dụng mẫu. **Bắt buộc nêu rõ: Khi nào NÊN dùng và khi nào KHÔNG nên dùng** mẫu (kèm dấu hiệu nhận biết / lựa chọn thay thế).
6. **Cấu trúc** — Mô tả mẫu bằng ký hiệu đồ hình (OMT, UML…); chèn ảnh từ `images/`.
7. **Các thành viên** — Ý nghĩa và trách nhiệm của các lớp/đối tượng tham gia vào mẫu.
8. **Sự cộng tác** — Cách các thành viên phối hợp với nhau để thực hiện trách nhiệm.
9. **Các hệ quả mang lại** — Những ưu/nhược điểm, kết quả khi dùng mẫu.
10. **Chú ý khi cài đặt** — Các lưu ý liên quan đến việc hiện thực mẫu.
11. **Mã nguồn minh họa** — Ví dụ code cụ thể (tham chiếu tới `src/`).
12. **Ví dụ thực tế** — Các hệ thống thực tế đã/đang chạy có dùng mẫu này.
13. **Các mẫu liên quan** — Những mẫu có liên hệ, điểm cần phân biệt, và cách phối hợp với các mẫu khác.

---

## 4. Mẫu khung (template) cho `README.md`

Sao chép khối dưới đây vào `README.md` của mỗi folder rồi điền nội dung:

```markdown
# <Tên mẫu>

## 1. Tên và phân loại
- **Tên:** <Tên mẫu>
- **Phân loại:** <Creational / Structural / Behavioral>

## 2. Mục đích, ý định
<Mô tả ngắn gọn mẫu dùng để làm gì.>

## 3. Bí danh
<Các tên gọi khác, hoặc "Không có".>

## 4. Motivation (Động cơ)
<Tình huống cụ thể dẫn đến nhu cầu dùng mẫu.>

## 5. Khả năng ứng dụng
<Các tình huống thiết kế có thể áp dụng mẫu.>

### ✅ Khi nào NÊN dùng
- <Dấu hiệu / tình huống nên áp dụng mẫu.>

### ❌ Khi nào KHÔNG nên dùng
- <Dấu hiệu lạm dụng / tình huống nên tránh, kèm lựa chọn thay thế.>

## 6. Cấu trúc
<Sơ đồ UML/OMT. Ưu tiên dùng khối ```mermaid (GitHub render trực tiếp, không cần file ảnh).
Nếu muốn dùng ảnh thì đặt file thật trong images/ rồi: ![Cấu trúc](images/structure.png)>

## 7. Các thành viên
<Vai trò và trách nhiệm của từng lớp/đối tượng.>

## 8. Sự cộng tác
<Cách các thành viên phối hợp.>

## 9. Các hệ quả mang lại
**Ưu điểm:**
-

**Nhược điểm:**
-

## 10. Chú ý khi cài đặt
<Các lưu ý hiện thực.>

## 11. Mã nguồn minh họa
<Giải thích ví dụ bằng Java và liên kết tới mã nguồn trong src/. Chèn đoạn code trong khối ```java ... ```.>

## 12. Ví dụ thực tế
<Hệ thống/thư viện thực tế dùng mẫu này.>

## 13. Các mẫu liên quan
<Mẫu liên hệ, điểm phân biệt, cách phối hợp.>
```

---

## 5. Quy trình thực hiện một mẫu mới

1. Tạo folder mới ở root theo quy ước đặt tên ở mục 2.
2. Tạo file `README.md` trong folder, dán template ở mục 4 và điền đủ 13 mục.
3. Tạo thư mục `images/` và thêm sơ đồ cấu trúc (UML/OMT).
4. Tạo thư mục `src/` và viết **mã nguồn Java** minh họa chạy được (kèm lớp `Main.java` để demo).
5. Kiểm tra lại: đủ 13 mục, ảnh hiển thị đúng, mã nguồn biên dịch và chạy được.

> **Biên dịch & chạy Java:** trong thư mục `src/` của mẫu, dùng:
> ```bash
> javac -encoding UTF-8 *.java
> java Main
> ```
> Lưu ý: comment trong mã nguồn dùng **tiếng Việt có dấu** (UTF-8), nên **bắt buộc** thêm `-encoding UTF-8` khi biên dịch để `javac` đọc đúng ký tự.
