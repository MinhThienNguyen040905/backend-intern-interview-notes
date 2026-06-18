/**
 * Main - Chương trình demo chứng minh Singleton chỉ có MỘT thể hiện.
 *
 * Biên dịch & chạy (trong thư mục src/):
 *   javac -encoding UTF-8 *.java
 *   java Main
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("=== DEMO MAU SINGLETON (Logger) ===\n");

        // Lấy thể hiện lần 1
        Logger a = Logger.getInstance();
        a.log("Ung dung khoi dong");

        // Lấy thể hiện lần 2 (ở một 'chỗ khác' trong chương trình)
        Logger b = Logger.getInstance();
        b.log("Nguoi dung dang nhap");

        // Lấy thể hiện lần 3
        Logger c = Logger.getInstance();
        c.log("Tai du lieu xong");

        System.out.println("\n--- Kiem chung ---");
        System.out.println("a == b ? " + (a == b)); // true
        System.out.println("b == c ? " + (b == c)); // true
        System.out.println("Tat ca cung 1 doi tuong (hashCode):");
        System.out.println("  a: " + System.identityHashCode(a));
        System.out.println("  b: " + System.identityHashCode(b));
        System.out.println("  c: " + System.identityHashCode(c));

        // Vì cùng 1 thể hiện nên 'count' được chia sẻ -> phải bằng 3
        System.out.println("\nTong so log da ghi (trang thai dung chung): "
                + Logger.getInstance().getCount());

        // Không thể gọi 'new Logger()' ở đây vì constructor là private:
        // Logger x = new Logger(); // -> LỖI BIÊN DỊCH
    }
}
