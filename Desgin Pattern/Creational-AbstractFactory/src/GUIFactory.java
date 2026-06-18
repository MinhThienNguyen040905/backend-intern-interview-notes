/**
 * GUIFactory - AbstractFactory: khai báo cách tạo từng sản phẩm trong họ.
 * Mỗi factory cụ thể sẽ tạo ra MỘT HỌ widget tương thích với nhau.
 */
public interface GUIFactory {
    Button createButton();
    Checkbox createCheckbox();
}
