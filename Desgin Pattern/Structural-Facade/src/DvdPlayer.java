/** DvdPlayer - một lớp trong hệ thống con. */
public class DvdPlayer {
    public void on() { System.out.println("Dau DVD: bat"); }
    public void off() { System.out.println("Dau DVD: tat"); }
    public void play(String movie) { System.out.println("Dau DVD: phat phim '" + movie + "'"); }
    public void stop() { System.out.println("Dau DVD: dung"); }
}
