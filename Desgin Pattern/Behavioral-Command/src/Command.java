/**
 * Command - giao diện mệnh lệnh: đóng gói một yêu cầu.
 * Hỗ trợ cả undo() để hoàn tác.
 */
public interface Command {
    void execute();
    void undo();
}
