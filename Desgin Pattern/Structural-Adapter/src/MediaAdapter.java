/**
 * MediaAdapter - vai trò Adapter (object adapter).
 *
 * Cài đặt Target (MediaPlayer) nhưng bên trong ỦY THÁC cho Adaptee
 * (AdvancedPlayer) - "dịch" lời gọi play() thành playMp4().
 */
public class MediaAdapter implements MediaPlayer {

    private final AdvancedPlayer advanced;

    public MediaAdapter() {
        this.advanced = new Mp4Player();
    }

    @Override
    public void play(String fileName) {
        // Chuyển đổi giao diện: play(...) -> playMp4(...)
        advanced.playMp4(fileName);
    }
}
