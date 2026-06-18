/**
 * TV - ConcreteImplementor: một thiết bị cụ thể.
 */
public class TV implements Device {
    private boolean on = false;
    private int volume = 30;

    @Override public boolean isEnabled() { return on; }
    @Override public void enable() { on = true; System.out.println("TV: BAT"); }
    @Override public void disable() { on = false; System.out.println("TV: TAT"); }
    @Override public void setVolume(int percent) {
        volume = Math.max(0, Math.min(100, percent));
        System.out.println("TV: am luong = " + volume);
    }
    @Override public int getVolume() { return volume; }
}
