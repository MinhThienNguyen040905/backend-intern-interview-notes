/**
 * AdvancedRemote - RefinedAbstraction: mở rộng Remote với chức năng mới (mute),
 * độc lập với việc có bao nhiêu loại Device.
 */
public class AdvancedRemote extends Remote {

    public AdvancedRemote(Device device) {
        super(device);
    }

    public void mute() {
        device.setVolume(0);
        System.out.println("(Da tat tieng)");
    }
}
