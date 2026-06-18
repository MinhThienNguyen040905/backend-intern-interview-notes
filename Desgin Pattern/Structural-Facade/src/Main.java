/**
 * Main - demo mẫu Facade.
 *
 * Biên dịch & chạy (trong thư mục src/):
 *   javac -encoding UTF-8 *.java
 *   java Main
 *
 * Client chỉ gọi watchMovie()/endMovie() thay vì điều khiển thủ công
 * từng lớp con theo đúng trình tự.
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("=== DEMO MAU FACADE (Rap phim tai gia) ===\n");

        HomeTheaterFacade theater = new HomeTheaterFacade(
                new Amplifier(), new DvdPlayer(), new Projector(), new Lights());

        theater.watchMovie("Interstellar");
        System.out.println();
        theater.endMovie();
    }
}
