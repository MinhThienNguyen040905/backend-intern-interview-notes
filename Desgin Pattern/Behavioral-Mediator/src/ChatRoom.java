import java.util.ArrayList;
import java.util.List;

/**
 * ChatRoom - vai trò ConcreteMediator.
 *
 * Các User KHÔNG tham chiếu trực tiếp lẫn nhau. Mọi tin nhắn đi qua ChatRoom,
 * và ChatRoom chịu trách nhiệm ĐỊNH TUYẾN tới những người nhận phù hợp.
 */
public class ChatRoom implements ChatMediator {
    private final List<User> users = new ArrayList<>();

    @Override
    public void register(User user) {
        users.add(user);
    }

    @Override
    public void send(String message, User sender) {
        for (User user : users) {
            if (user != sender) {           // không gửi lại cho người gửi
                user.receive(message, sender);
            }
        }
    }
}
