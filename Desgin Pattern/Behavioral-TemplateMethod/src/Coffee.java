/**
 * Coffee - ConcreteClass: cài đặt các bước riêng để pha cà phê.
 */
public class Coffee extends CaffeineBeverage {
    @Override
    protected void brew() {
        System.out.println("Pha ca phe qua phin");
    }

    @Override
    protected void addCondiments() {
        System.out.println("Them duong va sua");
    }
}
