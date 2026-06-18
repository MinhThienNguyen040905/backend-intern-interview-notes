/**
 * CondimentDecorator - vai trò Decorator (trừu tượng).
 * Cài đặt Beverage và giữ tham chiếu tới đối tượng bị bọc (wrappee).
 */
public abstract class CondimentDecorator implements Beverage {
    protected final Beverage wrappee;   // đối tượng được trang trí

    protected CondimentDecorator(Beverage wrappee) {
        this.wrappee = wrappee;
    }
}
