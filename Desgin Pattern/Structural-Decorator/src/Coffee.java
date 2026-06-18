/**
 * Coffee - ConcreteComponent: đồ uống gốc (chưa trang trí).
 */
public class Coffee implements Beverage {
    @Override public String getDescription() { return "Ca phe"; }
    @Override public double cost() { return 20_000; }
}
