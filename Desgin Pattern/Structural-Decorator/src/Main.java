/**
 * Main - demo mẫu Decorator.
 *
 * Biên dịch & chạy (trong thư mục src/):
 *   javac -encoding UTF-8 *.java
 *   java Main
 *
 * Thêm chức năng (sữa, kem) cho đồ uống LÚC CHẠY bằng cách bọc lồng decorator,
 * không cần tạo lớp riêng cho mỗi tổ hợp.
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("=== DEMO MAU DECORATOR (Do uong) ===\n");

        // Cà phê gốc
        Beverage drink = new Coffee();
        print(drink);

        // Bọc thêm sữa
        drink = new Milk(drink);
        print(drink);

        // Bọc thêm kem tươi (lồng tiếp)
        drink = new Whip(drink);
        print(drink);

        // Một tổ hợp khác tạo trực tiếp lúc chạy: cà phê + kem + kem
        System.out.println("\nMot ly khac (ca phe + 2 kem):");
        Beverage doubleWhip = new Whip(new Whip(new Coffee()));
        print(doubleWhip);
    }

    private static void print(Beverage b) {
        System.out.printf("  %s = %.0f d%n", b.getDescription(), b.cost());
    }
}
