/**
 * Radio - ConcreteImplementor: thêm một thiết bị mới mà KHÔNG cần
 * sửa hay thêm lớp Remote nào (nhờ Bridge tách hai phân cấp).
 */
public class Radio implements Device {
    private boolean on = false;
    private int volume = 20;

    @Override public boolean isEnabled() { return on; }
    @Override public void enable() { on = true; System.out.println("Radio: BAT"); }
    @Override public void disable() { on = false; System.out.println("Radio: TAT"); }
    @Override public void setVolume(int percent) {
        volume = Math.max(0, Math.min(100, percent));
        System.out.println("Radio: am luong = " + volume);
    }
    @Override public int getVolume() { return volume; }
}
