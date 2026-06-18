/**
 * SeaLogistics - ConcreteCreator: override factory method để tạo Ship.
 */
public class SeaLogistics extends Logistics {
    @Override
    public Transport createTransport() {
        return new Ship();
    }
}
