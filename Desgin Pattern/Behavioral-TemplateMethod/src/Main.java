/**
 * Main - demo mẫu Template Method.
 *
 * Biên dịch & chạy (trong thư mục src/):
 *   javac -encoding UTF-8 *.java
 *   java Main
 *
 * Điểm mấu chốt: cùng một khung prepare() ở lớp cha điều khiển trình tự;
 * mỗi lớp con chỉ "điền vào chỗ trống" (brew, addCondiments) và có thể
 * dùng HOOK (wantsCondiments) để bật/tắt một bước tùy chọn.
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("=== DEMO MAU TEMPLATE METHOD (Pha do uong) ===\n");

        System.out.println(">> Pha CA PHE:");
        CaffeineBeverage coffee = new Coffee();
        coffee.prepare();

        System.out.println(">> Pha TRA (co chanh):");
        CaffeineBeverage teaWithLemon = new Tea(true);
        teaWithLemon.prepare();

        System.out.println(">> Pha TRA (khong chanh - hook bo qua buoc them phu lieu):");
        CaffeineBeverage plainTea = new Tea(false);
        plainTea.prepare();
    }
}
