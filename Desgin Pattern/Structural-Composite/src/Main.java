/**
 * Main - demo mẫu Composite.
 *
 * Biên dịch & chạy (trong thư mục src/):
 *   javac -encoding UTF-8 *.java
 *   java Main
 *
 * Client xử lý File (lá) và Folder (nhóm) ĐỒNG NHẤT qua FileSystemNode:
 * cùng gọi getSize()/print() mà không cần biết đó là lá hay nhóm.
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("=== DEMO MAU COMPOSITE (He thong tap tin) ===\n");

        // Xây dựng cây thư mục.
        Folder root = new Folder("root");
        root.add(new FileLeaf("readme.txt", 2));

        Folder src = new Folder("src");
        src.add(new FileLeaf("Main.java", 5));
        src.add(new FileLeaf("Utils.java", 8));

        Folder images = new Folder("images");
        images.add(new FileLeaf("logo.png", 120));
        images.add(new FileLeaf("banner.jpg", 340));

        src.add(images);   // folder lồng folder
        root.add(src);

        // In cả cây và tính tổng kích thước (đệ quy, đồng nhất).
        root.print("");

        System.out.println("\nTong kich thuoc cua 'root': " + root.getSize() + " KB");
        System.out.println("Tong kich thuoc cua 'src':  " + src.getSize() + " KB");
    }
}
