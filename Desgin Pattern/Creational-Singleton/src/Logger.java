/**
 * Logger - Ví dụ mẫu Singleton.
 *
 * Cài đặt theo kiểu "Initialization-on-demand holder":
 *   - Lazy: thể hiện chỉ được tạo khi getInstance() được gọi lần đầu.
 *   - Thread-safe: cơ chế nạp lớp (class loading) của JVM đảm bảo
 *     Holder.INSTANCE chỉ được khởi tạo một lần, an toàn với đa luồng.
 *
 * Lớp là 'final' để không thể bị kế thừa và phá vỡ tính duy nhất.
 */
public final class Logger {

    // (1) Constructor private: chặn mọi nơi bên ngoài gọi 'new Logger()'.
    private Logger() {
        System.out.println(">> Khoi tao Logger (chi xay ra 1 lan)");
    }

    // (2) Lớp tĩnh lồng bên trong giữ thể hiện duy nhất.
    //     JVM chỉ nạp lớp Holder khi nó thực sự được tham chiếu lần đầu
    //     (tức khi getInstance() được gọi) -> lazy + thread-safe.
    private static class Holder {
        private static final Logger INSTANCE = new Logger();
    }

    // (3) Điểm truy cập toàn cục trả về thể hiện duy nhất.
    public static Logger getInstance() {
        return Holder.INSTANCE;
    }

    // ---- Các phương thức nghiệp vụ của Singleton ----

    private int count = 0; // trạng thái dùng chung để minh họa

    public void log(String message) {
        count++;
        System.out.println("[LOG #" + count + "] " + message);
    }

    public int getCount() {
        return count;
    }
}
