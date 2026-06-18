/**
 * ShoppingCart - vai trò Context trong mẫu Strategy.
 *
 * Giỏ hàng KHÔNG tự tính phí mà giữ một tham chiếu tới PaymentStrategy
 * và ỦY THÁC (delegate) việc tính phí cho strategy hiện tại.
 * Có thể đổi strategy bất kỳ lúc nào trong lúc chạy.
 */
public class ShoppingCart {

    // Tham chiếu tới thuật toán hiện tại (có thể đổi lúc chạy).
    private PaymentStrategy strategy;

    public ShoppingCart(PaymentStrategy defaultStrategy) {
        this.strategy = defaultStrategy; // strategy mặc định
    }

    /** Cho phép client "cắm" một strategy khác vào. */
    public void setStrategy(PaymentStrategy strategy) {
        this.strategy = strategy;
    }

    /** Tính tổng tiền phải trả = số tiền hàng + phí (do strategy quyết định). */
    public double checkout(double amount) {
        double fee = strategy.calculateFee(amount); // <-- ủy thác cho strategy
        double total = amount + fee;
        System.out.printf("  [%s] Tien hang: %.0f | Phi: %.0f | Tong: %.0f%n",
                strategy.name(), amount, fee, total);
        return total;
    }
}
