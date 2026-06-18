/**
 * Main - demo mẫu Mediator.
 *
 * Biên dịch & chạy (trong thư mục src/):
 *   javac -encoding UTF-8 *.java
 *   java Main
 *
 * Các User giao tiếp qua ChatRoom (mediator) thay vì tham chiếu trực tiếp nhau.
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("=== DEMO MAU MEDIATOR (Phong chat) ===\n");

        ChatMediator room = new ChatRoom();

        User an = new User("An", room);
        User binh = new User("Binh", room);
        User cuong = new User("Cuong", room);

        room.register(an);
        room.register(binh);
        room.register(cuong);

        an.send("Chao ca nha!");
        System.out.println();
        binh.send("Chao An nhe.");
    }
}
