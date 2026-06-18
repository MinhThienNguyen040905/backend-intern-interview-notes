/**
 * EWalletPayment - ConcreteStrategy: ví điện tử, miễn phí.
 */
public class EWalletPayment implements PaymentStrategy {
    @Override
    public double calculateFee(double amount) {
        return 0.0;
    }

    @Override
    public String name() {
        return "Vi dien tu (mien phi)";
    }
}
