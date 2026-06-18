/**
 * Observer - giao diện người quan sát: nhận thông báo khi subject đổi trạng thái.
 * (Dùng kiểu "push": subject đẩy kèm dữ liệu nhiệt độ.)
 */
public interface Observer {
    void update(float temperature);
}
