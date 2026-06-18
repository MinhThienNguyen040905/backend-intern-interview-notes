/**
 * Truck - ConcreteProduct: giao hàng bằng đường bộ.
 */
public class Truck implements Transport {
    @Override
    public void deliver() {
        System.out.println("Giao hang bang XE TAI tren duong bo.");
    }
}
