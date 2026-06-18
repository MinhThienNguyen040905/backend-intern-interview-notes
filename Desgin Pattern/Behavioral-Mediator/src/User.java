/**
 * User - vai trò Colleague.
 *
 * Chỉ biết tới mediator (ChatMediator), KHÔNG biết các User khác.
 * Khi muốn nhắn tin thì báo cho mediator để nó phân phối.
 */
public class User {
    private final String name;
    private final ChatMediator mediator;

    public User(String name, ChatMediator mediator) {
        this.name = name;
        this.mediator = mediator;
    }

    public String getName() {
        return name;
    }

    public void send(String message) {
        System.out.println(name + " gui: " + message);
        mediator.send(message, this);   // gửi qua mediator
    }

    public void receive(String message, User sender) {
        System.out.println("   " + name + " nhan tu " + sender.getName() + ": " + message);
    }
}
