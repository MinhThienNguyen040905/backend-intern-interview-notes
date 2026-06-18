import java.util.ArrayList;
import java.util.List;

/**
 * Pizza - Product trong mẫu Builder, được thiết kế BẤT BIẾN (immutable).
 *
 * Sử dụng biến thể "static nested Builder" của Java:
 *   - Constructor của Pizza là private, chỉ Builder gọi được.
 *   - Builder cấu hình từng phần qua method chaining rồi build().
 */
public final class Pizza {
    // Tất cả thuộc tính là final -> đối tượng bất biến.
    private final String size;        // bắt buộc
    private final String crust;       // tùy chọn
    private final String sauce;       // tùy chọn
    private final List<String> toppings;

    // Constructor private: chỉ tạo từ Builder.
    private Pizza(Builder b) {
        this.size = b.size;
        this.crust = b.crust;
        this.sauce = b.sauce;
        this.toppings = b.toppings;
    }

    @Override
    public String toString() {
        return "Pizza{co=" + size + ", de=" + crust
                + ", sot=" + sauce + ", topping=" + toppings + "}";
    }

    // ===== BUILDER lồng tĩnh =====
    public static class Builder {
        private final String size;            // trường bắt buộc -> qua constructor
        private String crust = "thuong";      // giá trị mặc định
        private String sauce = "ca chua";
        private final List<String> toppings = new ArrayList<>();

        public Builder(String size) {
            this.size = size;
        }

        public Builder crust(String crust) {
            this.crust = crust;
            return this;                       // method chaining
        }

        public Builder sauce(String sauce) {
            this.sauce = sauce;
            return this;
        }

        public Builder addTopping(String topping) {
            this.toppings.add(topping);
            return this;
        }

        public Pizza build() {
            // Nơi tập trung kiểm tra hợp lệ trước khi tạo Product.
            if (size == null || size.isEmpty()) {
                throw new IllegalStateException("Kich co la bat buoc");
            }
            return new Pizza(this);
        }
    }
}
