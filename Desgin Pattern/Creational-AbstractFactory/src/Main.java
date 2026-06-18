/**
 * Main - demo mẫu Abstract Factory.
 *
 * Biên dịch & chạy (trong thư mục src/):
 *   javac -encoding UTF-8 *.java
 *   java Main
 *
 * Điểm mấu chốt: client (renderUI) chỉ làm việc với GUIFactory + interface
 * sản phẩm. Đổi cả họ widget chỉ bằng cách truyền vào factory khác.
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("=== DEMO MAU ABSTRACT FACTORY (UI da nen tang) ===\n");

        // Giả sử đọc cấu hình hệ điều hành lúc chạy.
        String os = "windows";
        GUIFactory factory = (os.equals("mac")) ? new MacFactory() : new WinFactory();

        System.out.println(">> He dieu hanh: " + os);
        renderUI(factory);

        System.out.println("\n>> Doi sang macOS (chi thay factory):");
        renderUI(new MacFactory());
    }

    // Client: không hề biết lớp widget cụ thể, chỉ dùng interface.
    private static void renderUI(GUIFactory factory) {
        Button button = factory.createButton();
        Checkbox checkbox = factory.createCheckbox();
        button.paint();
        checkbox.paint();
    }
}
