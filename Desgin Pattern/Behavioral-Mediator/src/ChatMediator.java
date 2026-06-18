/**
 * ChatMediator - vai trò Mediator: giao diện điều phối giao tiếp giữa các User.
 */
public interface ChatMediator {
    void register(User user);
    void send(String message, User sender);
}
