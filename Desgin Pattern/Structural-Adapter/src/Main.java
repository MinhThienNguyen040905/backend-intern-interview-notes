/**
 * Main - demo mẫu Adapter.
 *
 * Biên dịch & chạy (trong thư mục src/):
 *   javac -encoding UTF-8 *.java
 *   java Main
 *
 * Client chỉ làm việc với Target (MediaPlayer); Adapter giúp tận dụng
 * AdvancedPlayer (giao diện khác) mà không sửa client hay thư viện ngoài.
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("=== DEMO MAU ADAPTER (MediaPlayer) ===\n");

        // Client dùng giao diện Target, nhưng đối tượng thực là Adapter.
        MediaPlayer player = new MediaAdapter();
        player.play("bai-hat.mp4");

        System.out.println("\n-> Client khong he biet ben trong la AdvancedPlayer/Mp4Player.");
    }
}
