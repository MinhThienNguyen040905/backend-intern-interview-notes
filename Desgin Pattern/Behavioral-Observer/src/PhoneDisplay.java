/** PhoneDisplay - ConcreteObserver: hiển thị trên điện thoại. */
public class PhoneDisplay implements Observer {
    private final String owner;

    public PhoneDisplay(String owner) {
        this.owner = owner;
    }

    @Override
    public void update(float temperature) {
        System.out.println("   [Dien thoai cua " + owner + "] Nhiet do: " + temperature + "C");
    }
}
