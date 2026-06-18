/**
 * CaffeineBeverage - vai trò AbstractClass trong mẫu Template Method.
 *
 * Chứa TEMPLATE METHOD prepare() định nghĩa khung (skeleton) của thuật toán
 * pha đồ uống. Khung này CỐ ĐỊNH (để 'final' để lớp con không phá vỡ),
 * còn các bước riêng (brew, addCondiments) là trừu tượng cho lớp con cài.
 */
public abstract class CaffeineBeverage {

    // ===== TEMPLATE METHOD =====
    // 'final' để lớp con KHÔNG override, giữ nguyên trình tự thuật toán.
    public final void prepare() {
        boilWater();                 // bước chung
        brew();                      // bước riêng (abstract)
        pourInCup();                 // bước chung
        if (wantsCondiments()) {     // hook: quyết định có thêm phụ liệu không
            addCondiments();         // bước riêng (abstract)
        }
        System.out.println("-> Hoan tat!\n");
    }

    // ----- Các bước nguyên thủy (primitive operations) lớp con PHẢI cài -----
    protected abstract void brew();
    protected abstract void addCondiments();

    // ----- HOOK: có cài đặt mặc định, lớp con TÙY CHỌN override -----
    protected boolean wantsCondiments() {
        return true; // mặc định: luôn thêm phụ liệu
    }

    // ----- Các bước chung dùng lại cho mọi lớp con -----
    private void boilWater() {
        System.out.println("Dun soi nuoc");
    }

    private void pourInCup() {
        System.out.println("Rot ra coc");
    }
}
