/**
 * Ship - ConcreteProduct: giao hàng bằng đường biển.
 */
public class Ship implements Transport {
    @Override
    public void deliver() {
        System.out.println("Giao hang bang TAU THUY tren duong bien.");
    }
}
