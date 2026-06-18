/**
 * Milk - ConcreteDecorator: thêm sữa (cộng mô tả và giá).
 */
public class Milk extends CondimentDecorator {
    public Milk(Beverage wrappee) {
        super(wrappee);
    }

    @Override public String getDescription() {
        return wrappee.getDescription() + ", sua";
    }

    @Override public double cost() {
        return wrappee.cost() + 5_000;
    }
}
