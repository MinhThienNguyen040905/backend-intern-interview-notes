/** WindowDisplay - ConcreteObserver: bảng hiển thị treo tường. */
public class WindowDisplay implements Observer {
    @Override
    public void update(float temperature) {
        String status = temperature >= 30 ? "Nong" : "Mat";
        System.out.println("   [Bang tuong] " + temperature + "C (" + status + ")");
    }
}
