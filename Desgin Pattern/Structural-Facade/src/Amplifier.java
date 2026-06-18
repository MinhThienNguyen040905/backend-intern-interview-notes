/** Amplifier - một lớp trong hệ thống con (subsystem). */
public class Amplifier {
    public void on() { System.out.println("Ampli: bat"); }
    public void off() { System.out.println("Ampli: tat"); }
    public void setVolume(int level) { System.out.println("Ampli: am luong = " + level); }
}
