/**
 * Main - demo mẫu Proxy (virtual proxy).
 *
 * Biên dịch & chạy (trong thư mục src/):
 *   javac -encoding UTF-8 *.java
 *   java Main
 *
 * Tạo nhiều proxy ảnh rất nhanh (chưa tải gì). Ảnh thật chỉ được tải khi
 * display() lần đầu; lần thứ hai không tải lại.
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("=== DEMO MAU PROXY (Virtual proxy cho anh) ===\n");

        System.out.println("Tao 3 proxy anh (KHONG tai gi ca):");
        Image img1 = new ImageProxy("anh1.png");
        Image img2 = new ImageProxy("anh2.png");
        Image img3 = new ImageProxy("anh3.png");
        System.out.println("-> Da tao xong, rat nhanh.\n");

        System.out.println("Hien thi anh1 lan dau (luc nay moi tai):");
        img1.display();

        System.out.println("\nHien thi anh1 lan hai (KHONG tai lai):");
        img1.display();

        System.out.println("\nHien thi anh2 (tai khi can):");
        img2.display();

        System.out.println("\n(anh3 chua bao gio hien thi -> chua bao gio bi tai)");
    }
}
