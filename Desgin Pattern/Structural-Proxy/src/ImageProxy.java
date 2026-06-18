/**
 * ImageProxy - vai trò Proxy (virtual proxy / lazy loading).
 *
 * Cùng giao diện Image với RealImage. Chỉ tạo RealImage (tốn kém) khi
 * display() được gọi LẦN ĐẦU; các lần sau dùng lại đối tượng đã tạo.
 */
public class ImageProxy implements Image {
    private final String fileName;
    private RealImage real;   // chưa tạo vội (lazy)

    public ImageProxy(String fileName) {
        this.fileName = fileName;
        // Không tải gì ở đây -> tạo proxy rất nhẹ.
    }

    @Override
    public void display() {
        if (real == null) {              // chỉ tải khi thực sự cần
            real = new RealImage(fileName);
        }
        real.display();                  // chuyển tiếp cho đối tượng thật
    }
}
