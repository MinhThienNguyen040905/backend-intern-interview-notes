/**
 * RoadLogistics - ConcreteCreator: override factory method để tạo Truck.
 */
public class RoadLogistics extends Logistics {
    @Override
    public Transport createTransport() {
        return new Truck();
    }
}
