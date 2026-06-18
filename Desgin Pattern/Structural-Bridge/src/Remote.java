/**
 * Remote - vai trò Abstraction trong mẫu Bridge.
 *
 * Giữ một tham chiếu tới Device (Implementor) - đây chính là "cây cầu".
 * Remote ỦY THÁC các thao tác cho device, không quan tâm device cụ thể là gì.
 */
public class Remote {

    protected Device device;   // <-- cây cầu tới phần cài đặt

    public Remote(Device device) {
        this.device = device;
    }

    public void togglePower() {
        if (device.isEnabled()) {
            device.disable();
        } else {
            device.enable();
        }
    }

    public void volumeUp() {
        device.setVolume(device.getVolume() + 10);
    }

    public void volumeDown() {
        device.setVolume(device.getVolume() - 10);
    }
}
