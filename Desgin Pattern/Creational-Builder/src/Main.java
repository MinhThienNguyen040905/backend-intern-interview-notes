/**
 * Main - demo mẫu Builder.
 *
 * Biên dịch & chạy (trong thư mục src/):
 *   javac -encoding UTF-8 *.java
 *   java Main
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("=== DEMO MAU BUILDER (Pizza) ===\n");

        // Pizza đầy đủ tùy chọn - đọc rõ như văn xuôi.
        Pizza deluxe = new Pizza.Builder("L")
                .crust("mong")
                .sauce("ca chua")
                .addTopping("Nam")
                .addTopping("Pho mai")
                .addTopping("Xuc xich")
                .build();
        System.out.println("Pizza dac biet: " + deluxe);

        // Pizza tối giản - chỉ truyền trường bắt buộc, còn lại dùng mặc định.
        Pizza simple = new Pizza.Builder("S").build();
        System.out.println("Pizza don gian: " + simple);

        // Minh họa kiểm tra hợp lệ trong build().
        try {
            new Pizza.Builder("").build();
        } catch (IllegalStateException e) {
            System.out.println("\nLoi mong doi: " + e.getMessage());
        }
    }
}
