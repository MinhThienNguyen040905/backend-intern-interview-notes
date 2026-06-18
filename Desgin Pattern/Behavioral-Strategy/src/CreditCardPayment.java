/**
 * CreditCardPayment - ConcreteStrategy: thẻ tín dụng, phí 2% số tiền.
 */
public class CreditCardPayment implements PaymentStrategy {
    @Override
    public double calculateFee(double amount) {
        return amount * 0.02;
    }

    @Override
    public String name() {
        return "The tin dung (2%)";
    }
}
