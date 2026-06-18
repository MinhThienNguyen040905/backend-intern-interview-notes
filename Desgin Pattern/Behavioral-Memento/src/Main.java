/**
 * Main - demo mẫu Memento.
 *
 * Biên dịch & chạy (trong thư mục src/):
 *   javac -encoding UTF-8 *.java
 *   java Main
 *
 * Gõ văn bản, lưu các bản chụp, rồi UNDO lần lượt về trạng thái trước.
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("=== DEMO MAU MEMENTO (Undo trinh soan thao) ===\n");

        Editor editor = new Editor();
        History history = new History();

        editor.type("Xin chao");
        history.push(editor.save());                 // chụp trạng thái 1
        System.out.println("Sau buoc 1: \"" + editor.getContent() + "\"");

        editor.type(", the gioi");
        history.push(editor.save());                 // chụp trạng thái 2
        System.out.println("Sau buoc 2: \"" + editor.getContent() + "\"");

        editor.type("!!!");
        System.out.println("Sau buoc 3: \"" + editor.getContent() + "\" (chua luu)");

        System.out.println("\n--- UNDO ---");
        editor.restore(history.pop());               // về trạng thái 2
        System.out.println("Undo 1: \"" + editor.getContent() + "\"");

        editor.restore(history.pop());               // về trạng thái 1
        System.out.println("Undo 2: \"" + editor.getContent() + "\"");
    }
}
