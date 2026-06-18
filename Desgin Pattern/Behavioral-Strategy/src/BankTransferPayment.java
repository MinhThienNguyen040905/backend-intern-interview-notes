/**
 * BankTransferPayment - ConcreteStrategy: chuyển khoản, phí cố định 5.000đ.
 */
public class BankTransferPayment implements PaymentStrategy {
    private static final double FLAT_FEE = 5000.0;

    @Override
    public double calculateFee(double amount) {
        return FLAT_FEE;
    }

    @Override
    public String name() {
        return "Chuyen khoan (phi co dinh 5.000d)";
    }
}
