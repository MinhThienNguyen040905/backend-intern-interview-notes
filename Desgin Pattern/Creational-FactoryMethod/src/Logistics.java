/**
 * Logistics - vai trò Creator (trừu tượng) trong mẫu Factory Method.
 *
 * - Khai báo FACTORY METHOD createTransport() trả về kiểu trừu tượng Transport.
 * - Chứa nghiệp vụ planDelivery() sử dụng Transport mà KHÔNG biết lớp cụ thể.
 *   (planDelivery() đồng thời minh họa mối quan hệ với Template Method.)
 */
public abstract class Logistics {

    // ===== FACTORY METHOD =====
    // Lớp con quyết định tạo loại Transport nào.
    public abstract Transport createTransport();

    // Nghiệp vụ của Creator: chỉ làm việc với Transport ở mức interface.
    public void planDelivery() {
        Transport t = createTransport();   // <-- gọi factory method
        System.out.print("Lap ke hoach giao hang -> ");
        t.deliver();
    }
}
