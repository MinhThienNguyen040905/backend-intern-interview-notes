/**
 * AdvancedPlayer - vai trò Adaptee: "thư viện bên thứ ba" có giao diện
 * KHÔNG khớp với MediaPlayer (dùng playMp4 thay vì play).
 */
public interface AdvancedPlayer {
    void playMp4(String fileName);
}
