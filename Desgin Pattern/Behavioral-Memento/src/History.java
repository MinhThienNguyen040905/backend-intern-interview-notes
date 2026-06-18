import java.util.ArrayDeque;
import java.util.Deque;

/**
 * History - vai trò Caretaker.
 *
 * Giữ các Memento (ngăn xếp lịch sử để undo) nhưng KHÔNG xem/sửa nội dung
 * bên trong memento - chỉ cất giữ và trả lại khi cần.
 */
public class History {
    private final Deque<Editor.Memento> stack = new ArrayDeque<>();

    public void push(Editor.Memento memento) {
        stack.push(memento);
    }

    public Editor.Memento pop() {
        return stack.isEmpty() ? null : stack.pop();
    }

    public boolean isEmpty() {
        return stack.isEmpty();
    }
}
