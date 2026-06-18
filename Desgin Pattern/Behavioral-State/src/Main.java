/**
 * Main - demo mẫu State.
 *
 * Biên dịch & chạy (trong thư mục src/):
 *   javac -encoding UTF-8 *.java
 *   java Main
 *
 * Cùng các thao tác (bỏ tiền / bấm nút) cho kết quả khác nhau tùy trạng thái,
 * và máy tự chuyển trạng thái.
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("=== DEMO MAU STATE (May ban nuoc) ===\n");

        VendingMachine machine = new VendingMachine();

        System.out.println(">> Bam nut khi chua bo tien:");
        machine.pressButton();

        System.out.println("\n>> Bo tien roi bam nut:");
        machine.insertCoin();
        machine.pressButton();

        System.out.println("\n>> Lap lai mot luot mua nua:");
        machine.insertCoin();
        machine.pressButton();
    }
}
