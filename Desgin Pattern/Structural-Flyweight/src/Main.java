/**
 * Main - demo mẫu Flyweight.
 *
 * Biên dịch & chạy (trong thư mục src/):
 *   javac -encoding UTF-8 *.java
 *   java Main
 *
 * Trồng nhiều cây nhưng chỉ có vài LOẠI cây (TreeType) được tạo và chia sẻ.
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("=== DEMO MAU FLYWEIGHT (Rung cay) ===\n");

        Forest forest = new Forest();

        System.out.println("Trong cay (chu y so lan 'Tao moi TreeType'):");
        // Trồng nhiều cây nhưng chỉ dùng 2 loại -> chỉ tạo 2 TreeType.
        for (int i = 0; i < 5; i++) {
            forest.plant(i, i * 2, "Thong", "xanh dam", "thong.png");
        }
        for (int i = 0; i < 5; i++) {
            forest.plant(i * 3, i, "Anh dao", "hong", "anhdao.png");
        }

        System.out.println("\nVe rung:");
        forest.draw();

        System.out.println("\n--- Thong ke ---");
        System.out.println("So cay da trong: " + forest.treeCount());
        System.out.println("So TreeType thuc su tao (chia se): " + TreeTypeFactory.poolSize());
        System.out.println("-> 10 cay nhung chi 2 doi tuong TreeType.");
    }
}
