/**
 * Light - vai trò Receiver: biết cách thực hiện công việc thực sự.
 */
public class Light {
    private final String location;

    public Light(String location) {
        this.location = location;
    }

    public void on() {
        System.out.println("Den " + location + ": BAT");
    }

    public void off() {
        System.out.println("Den " + location + ": TAT");
    }
}
