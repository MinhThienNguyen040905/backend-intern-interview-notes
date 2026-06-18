/**
 * Whip - ConcreteDecorator: thêm kem tươi (cộng mô tả và giá).
 */
public class Whip extends CondimentDecorator {
    public Whip(Beverage wrappee) {
        super(wrappee);
    }

    @Override public String getDescription() {
        return wrappee.getDescription() + ", kem tuoi";
    }

    @Override public double cost() {
        return wrappee.cost() + 8_000;
    }
}
