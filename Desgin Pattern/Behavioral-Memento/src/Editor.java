/**
 * Editor - vai trò Originator.
 *
 * Có trạng thái nội tại (content). Tự tạo Memento để chụp trạng thái và
 * tự khôi phục từ Memento. Memento là LỚP LỒNG bất biến, chỉ Editor đọc
 * được nội dung -> bảo toàn đóng gói.
 */
public class Editor {
    private String content = "";

    public void type(String text) {
        content += text;
    }

    public String getContent() {
        return content;
    }

    // Tạo bản chụp trạng thái hiện tại.
    public Memento save() {
        return new Memento(content);
    }

    // Khôi phục trạng thái từ một memento.
    public void restore(Memento memento) {
        this.content = memento.getState();
    }

    // ===== Memento: lớp lồng, bất biến =====
    public static class Memento {
        private final String state;   // private -> Caretaker không xem được

        private Memento(String state) {
            this.state = state;
        }

        // private: chỉ lớp bao ngoài (Editor) truy cập được.
        private String getState() {
            return state;
        }
    }
}
