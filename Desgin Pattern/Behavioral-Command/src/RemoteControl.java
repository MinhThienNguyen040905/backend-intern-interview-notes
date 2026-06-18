import java.util.ArrayDeque;
import java.util.Deque;

/**
 * RemoteControl - vai trò Invoker.
 *
 * Giữ và thực thi command mà KHÔNG biết chi tiết bên trong. Lưu lịch sử
 * các command đã thực hiện vào một ngăn xếp để hỗ trợ UNDO.
 */
public class RemoteControl {
    private final Deque<Command> history = new ArrayDeque<>();

    public void submit(Command command) {
        command.execute();
        history.push(command);   // lưu để có thể undo
    }

    public void undoLast() {
        if (history.isEmpty()) {
            System.out.println("(Khong con gi de hoan tac)");
            return;
        }
        Command last = history.pop();
        System.out.print("UNDO -> ");
        last.undo();
    }
}
