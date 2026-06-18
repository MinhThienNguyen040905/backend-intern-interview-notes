import java.util.Arrays;
import java.util.List;

/**
 * Main - demo mẫu Visitor.
 *
 * Biên dịch & chạy (trong thư mục src/):
 *   javac -encoding UTF-8 *.java
 *   java Main
 *
 * Cùng một tập hình, áp các thao tác khác nhau (tính diện tích, xuất XML)
 * bằng cách đổi visitor - KHÔNG sửa các lớp hình.
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("=== DEMO MAU VISITOR (Cac hinh hoc) ===\n");

        List<Shape> shapes = Arrays.asList(
                new Circle(2),
                new Rectangle(3, 4),
                new Circle(1)
        );

        System.out.println(">> Visitor 1: tinh dien tich");
        AreaVisitor areaVisitor = new AreaVisitor();
        for (Shape s : shapes) {
            s.accept(areaVisitor);
        }
        System.out.printf("Tong dien tich: %.2f%n", areaVisitor.getTotal());

        System.out.println("\n>> Visitor 2: xuat XML (them thao tac moi, khong sua lop hinh)");
        XmlExportVisitor xmlVisitor = new XmlExportVisitor();
        for (Shape s : shapes) {
            s.accept(xmlVisitor);
        }
    }
}
