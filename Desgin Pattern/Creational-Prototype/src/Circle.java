/**
 * Circle - ConcretePrototype: dùng copy constructor để sao chép an toàn.
 */
public class Circle implements Shape {
    private int x, y, radius;
    private String color;

    public Circle(int x, int y, int radius, String color) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.color = color;
    }

    // Copy constructor: sao chép trạng thái từ một Circle khác.
    public Circle(Circle src) {
        this.x = src.x;
        this.y = src.y;
        this.radius = src.radius;
        this.color = src.color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public Shape cloneShape() {
        return new Circle(this);
    }

    @Override
    public void draw() {
        System.out.println("Hinh tron tai (" + x + "," + y + ") ban kinh "
                + radius + " mau " + color);
    }
}
