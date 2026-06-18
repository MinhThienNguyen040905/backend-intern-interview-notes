/**
 * PaymentStrategy - vai trò Strategy (interface) trong mẫu Strategy.
 * Khai báo giao diện chung cho mọi cách tính phí thanh toán.
 */
public interface PaymentStrategy {
    /** Tính phí giao dịch dựa trên số tiền. */
    double calculateFee(double amount);

    /** Tên cách thanh toán (để hiển thị). */
    String name();
}
