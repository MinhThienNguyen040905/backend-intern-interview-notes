/**
 * Mp4Player - một Adaptee cụ thể (giả lập thư viện ngoài).
 */
public class Mp4Player implements AdvancedPlayer {
    @Override
    public void playMp4(String fileName) {
        System.out.println("Dang phat MP4: " + fileName);
    }
}
