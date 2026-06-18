/**
 * Device - vai trò Implementor trong mẫu Bridge.
 * Chứa các thao tác cơ bản của thiết bị; các loại thiết bị cụ thể sẽ cài đặt.
 */
public interface Device {
    boolean isEnabled();
    void enable();
    void disable();
    void setVolume(int percent);
    int getVolume();
}
