/**
 * Main - demo mẫu Strategy.
 *
 * Biên dịch & chạy (trong thư mục src/):
 *   javac -encoding UTF-8 *.java
 *   java Main
 *
 * Điểm mấu chốt: cùng một giỏ hàng (Context), chỉ cần ĐỔI strategy là
 * cách tính phí thay đổi - mà KHÔNG sửa code của ShoppingCart.
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("=== DEMO MAU STRATEGY (Tinh phi thanh toan) ===\n");

        double amount = 1_000_000; // 1 triệu tiền hàng

        // Tạo giỏ hàng với strategy mặc định là thẻ tín dụng.
        ShoppingCart cart = new ShoppingCart(new CreditCardPayment());
        System.out.println("Lan 1 - mac dinh:");
        cart.checkout(amount);

        // Đổi sang ví điện tử lúc chạy - không sửa code ShoppingCart.
        System.out.println("\nLan 2 - doi sang vi dien tu:");
        cart.setStrategy(new EWalletPayment());
        cart.checkout(amount);

        // Đổi sang chuyển khoản.
        System.out.println("\nLan 3 - doi sang chuyen khoan:");
        cart.setStrategy(new BankTransferPayment());
        cart.checkout(amount);

        // Strategy dạng lambda: vì PaymentStrategy có nhiều phương thức,
        // ta dùng lớp ẩn danh ngắn gọn cho một khuyến mãi "phí âm" (giảm giá 1%).
        System.out.println("\nLan 4 - khuyen mai (lop an danh / co the dung lambda):");
        cart.setStrategy(new PaymentStrategy() {
            @Override public double calculateFee(double amt) { return -amt * 0.01; }
            @Override public String name() { return "Khuyen mai (-1%)"; }
        });
        cart.checkout(amount);
    }
}
