/**
 * Main - demo mẫu Factory Method.
 *
 * Biên dịch & chạy (trong thư mục src/):
 *   javac -encoding UTF-8 *.java
 *   java Main
 *
 * Điểm mấu chốt: code client chỉ làm việc với kiểu trừu tượng 'Logistics'
 * và 'Transport'. Việc tạo loại phương tiện cụ thể (Truck/Ship) do
 * lớp con (ConcreteCreator) quyết định qua factory method.
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("=== DEMO MAU FACTORY METHOD (Logistics) ===\n");

        // Tùy cấu hình, ta chọn một ConcreteCreator. Client không hề 'new Truck()'.
        Logistics[] companies = {
                new RoadLogistics(),   // -> tạo Truck
                new SeaLogistics()     // -> tạo Ship
        };

        for (Logistics company : companies) {
            // Gọi cùng một nghiệp vụ; kết quả khác nhau tùy lớp con.
            company.planDelivery();
        }

        System.out.println("\n--- Chon dong loai luc chay (vi du doc tu cau hinh) ---");
        String mode = "sea"; // giả sử đọc từ file cấu hình / tham số dòng lệnh
        Logistics chosen = createLogistics(mode);
        chosen.planDelivery();
    }

    // Một 'simple factory' nhỏ để chọn ConcreteCreator theo cấu hình đầu vào.
    private static Logistics createLogistics(String mode) {
        switch (mode.toLowerCase()) {
            case "road": return new RoadLogistics();
            case "sea":  return new SeaLogistics();
            default: throw new IllegalArgumentException("Khong ho tro: " + mode);
        }
    }
}
