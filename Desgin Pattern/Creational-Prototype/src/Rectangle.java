/**
 * Rectangle - ConcretePrototype: tự sao chép qua copy constructor.
 */
public class Rectangle implements Shape {
    private int width, height;
    private String color;

    public Rectangle(int width, int height, String color) {
        this.width = width;
        this.height = height;
        this.color = color;
    }

    public Rectangle(Rectangle src) {
        this.width = src.width;
        this.height = src.height;
        this.color = src.color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public Shape cloneShape() {
        return new Rectangle(this);
    }

    @Override
    public void draw() {
        System.out.println("Hinh chu nhat " + width + "x" + height + " mau " + color);
    }
}
