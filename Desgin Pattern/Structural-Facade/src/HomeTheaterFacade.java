/**
 * HomeTheaterFacade - vai trò Facade.
 *
 * Cung cấp giao diện đơn giản (watchMovie/endMovie) và ĐIỀU PHỐI các lớp
 * hệ thống con theo đúng trình tự, giấu sự phức tạp khỏi client.
 */
public class HomeTheaterFacade {
    private final Amplifier amp;
    private final DvdPlayer dvd;
    private final Projector projector;
    private final Lights lights;

    public HomeTheaterFacade(Amplifier amp, DvdPlayer dvd, Projector projector, Lights lights) {
        this.amp = amp;
        this.dvd = dvd;
        this.projector = projector;
        this.lights = lights;
    }

    public void watchMovie(String movie) {
        System.out.println(">> Chuan bi xem phim...");
        lights.dim(10);
        projector.on();
        projector.wideScreenMode();
        amp.on();
        amp.setVolume(7);
        dvd.on();
        dvd.play(movie);
    }

    public void endMovie() {
        System.out.println(">> Ket thuc, tat he thong...");
        dvd.stop();
        dvd.off();
        amp.off();
        projector.off();
        lights.on();
    }
}
