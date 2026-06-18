import java.util.HashMap;
import java.util.Map;

/**
 * Main - demo mẫu Prototype.
 *
 * Biên dịch & chạy (trong thư mục src/):
 *   javac -encoding UTF-8 *.java
 *   java Main
 *
 * Minh họa: tạo đối tượng mới bằng cách CLONE prototype, và một REGISTRY
 * nhỏ giữ sẵn các nguyên mẫu để client lấy ra clone mà không cần biết lớp.
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("=== DEMO MAU PROTOTYPE (Shape) ===\n");

        // Một "kho" prototype (registry).
        Map<String, Shape> registry = new HashMap<>();
        registry.put("tron-do", new Circle(0, 0, 10, "do"));
        registry.put("chunhat-xanh", new Rectangle(20, 10, "xanh"));

        // Client lấy prototype và clone, KHÔNG cần biết lớp cụ thể.
        Shape s1 = registry.get("tron-do").cloneShape();
        Shape s2 = registry.get("tron-do").cloneShape();
        Shape s3 = registry.get("chunhat-xanh").cloneShape();

        // Đổi màu bản sao s2 -> không ảnh hưởng prototype hay s1.
        ((Circle) s2).setColor("vang");

        System.out.println("s1 (clone tu 'tron-do'):");
        s1.draw();
        System.out.println("s2 (clone tu 'tron-do', doi mau):");
        s2.draw();
        System.out.println("s3 (clone tu 'chunhat-xanh'):");
        s3.draw();

        System.out.println("\nPrototype goc khong bi anh huong:");
        registry.get("tron-do").draw();

        System.out.println("\ns1 va s2 la hai doi tuong khac nhau? "
                + (s1 != s2));
    }
}
