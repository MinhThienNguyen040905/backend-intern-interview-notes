/**
 * Main - demo mẫu Chain of Responsibility.
 *
 * Biên dịch & chạy (trong thư mục src/):
 *   javac -encoding UTF-8 *.java
 *   java Main
 *
 * Client chỉ gửi yêu cầu vào ĐẦU chuỗi; tùy mức tiền, handler phù hợp xử lý.
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("=== DEMO MAU CHAIN OF RESPONSIBILITY (Duyet chi tieu) ===\n");

        // Dựng chuỗi: TruongNhom -> TruongPhong -> GiamDoc
        Approver teamLead = new TeamLead("An");
        Approver manager = new Manager("Binh");
        Approver director = new Director("Cuong");
        teamLead.setNext(manager).setNext(director);

        // Gửi các yêu cầu với mức tiền khác nhau vào đầu chuỗi.
        int[] requests = { 500_000, 5_000_000, 80_000_000, 500_000_000 };
        for (int amount : requests) {
            System.out.print("Yeu cau duyet " + amount + "d -> ");
            teamLead.handle(amount);
        }
    }
}
