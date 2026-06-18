/**
 * Main - demo mẫu Observer.
 *
 * Biên dịch & chạy (trong thư mục src/):
 *   javac -encoding UTF-8 *.java
 *   java Main
 *
 * Khi WeatherStation đổi nhiệt độ, mọi observer đăng ký được cập nhật tự động.
 * Có thể đăng ký / hủy đăng ký lúc chạy.
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("=== DEMO MAU OBSERVER (Tram thoi tiet) ===\n");

        WeatherStation station = new WeatherStation();

        Observer phoneAn = new PhoneDisplay("An");
        Observer window = new WindowDisplay();

        station.attach(phoneAn);
        station.attach(window);

        station.setTemperature(28);
        System.out.println();
        station.setTemperature(33);

        System.out.println("\n-> An huy dang ky, them dien thoai cua Binh:");
        station.detach(phoneAn);
        station.attach(new PhoneDisplay("Binh"));

        System.out.println();
        station.setTemperature(25);
    }
}
