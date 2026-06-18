import java.util.ArrayList;
import java.util.List;

/**
 * WeatherStation - vai trò ConcreteSubject.
 *
 * Giữ danh sách observer (chỉ biết qua interface). Khi nhiệt độ đổi thì
 * thông báo cho TẤT CẢ observer đã đăng ký.
 */
public class WeatherStation implements Subject {
    private final List<Observer> observers = new ArrayList<>();
    private float temperature;

    @Override public void attach(Observer observer) {
        observers.add(observer);
    }

    @Override public void detach(Observer observer) {
        observers.remove(observer);
    }

    @Override public void notifyObservers() {
        // Lặp trên bản sao để an toàn nếu observer tự hủy đăng ký khi nhận tin.
        for (Observer o : new ArrayList<>(observers)) {
            o.update(temperature);
        }
    }

    // Khi trạng thái đổi -> tự động thông báo.
    public void setTemperature(float temperature) {
        this.temperature = temperature;
        System.out.println("WeatherStation: nhiet do moi = " + temperature + "C");
        notifyObservers();
    }
}
