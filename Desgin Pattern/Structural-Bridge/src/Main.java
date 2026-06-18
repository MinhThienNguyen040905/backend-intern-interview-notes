/**
 * Main - demo mẫu Bridge.
 *
 * Biên dịch & chạy (trong thư mục src/):
 *   javac -encoding UTF-8 *.java
 *   java Main
 *
 * Cùng một loại remote dùng được cho nhiều thiết bị; và nâng cấp remote
 * (AdvancedRemote) độc lập với việc thêm thiết bị (Radio) -> tránh bùng nổ lớp.
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("=== DEMO MAU BRIDGE (Remote - Device) ===\n");

        System.out.println(">> Remote co ban + TV:");
        Remote tvRemote = new Remote(new TV());
        tvRemote.togglePower();
        tvRemote.volumeUp();

        System.out.println("\n>> Remote nang cao + Radio (to hop moi, khong them lop rieng):");
        AdvancedRemote radioRemote = new AdvancedRemote(new Radio());
        radioRemote.togglePower();
        radioRemote.volumeUp();
        radioRemote.mute();
    }
}
