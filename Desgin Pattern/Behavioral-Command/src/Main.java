/**
 * Main - demo mẫu Command.
 *
 * Biên dịch & chạy (trong thư mục src/):
 *   javac -encoding UTF-8 *.java
 *   java Main
 *
 * Đóng gói hành động bật/tắt đèn thành Command; Invoker thực thi và undo
 * mà không cần biết chi tiết. Cuối demo minh họa lambda thay cho lớp command.
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("=== DEMO MAU COMMAND (Dieu khien den) ===\n");

        Light livingRoom = new Light("phong khach");
        Light kitchen = new Light("nha bep");

        RemoteControl remote = new RemoteControl();

        remote.submit(new LightOnCommand(livingRoom));
        remote.submit(new LightOnCommand(kitchen));
        remote.submit(new LightOffCommand(livingRoom));

        System.out.println("\nHoan tac 2 thao tac gan nhat:");
        remote.undoLast();   // bật lại đèn phòng khách
        remote.undoLast();   // tắt đèn nhà bếp

        System.out.println("\nCommand dang lambda (Java) - khong can lop rieng:");
        Command custom = new Command() {
            @Override public void execute() { System.out.println("Lam viec gi do..."); }
            @Override public void undo()    { System.out.println("Hoan tac viec do."); }
        };
        remote.submit(custom);
        remote.undoLast();
    }
}
