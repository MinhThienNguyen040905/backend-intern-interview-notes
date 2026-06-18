/**
 * Tea - ConcreteClass: cài đặt các bước riêng để pha trà.
 * Đồng thời override HOOK wantsCondiments() để hỏi người dùng có thêm chanh không.
 */
public class Tea extends CaffeineBeverage {

    private final boolean wantLemon;

    public Tea(boolean wantLemon) {
        this.wantLemon = wantLemon;
    }

    @Override
    protected void brew() {
        System.out.println("Ngam tui tra trong nuoc nong");
    }

    @Override
    protected void addCondiments() {
        System.out.println("Them lat chanh");
    }

    // Override HOOK: chỉ thêm phụ liệu (chanh) khi người dùng muốn.
    @Override
    protected boolean wantsCondiments() {
        return wantLemon;
    }
}
