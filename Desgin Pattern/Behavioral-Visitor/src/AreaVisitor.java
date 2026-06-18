/**
 * AreaVisitor - ConcreteVisitor: tính diện tích cho từng loại hình.
 * Có thể TÍCH LŨY tổng diện tích khi duyệt nhiều hình.
 */
public class AreaVisitor implements ShapeVisitor {
    private double total = 0;

    @Override
    public void visitCircle(Circle circle) {
        double area = Math.PI * circle.getRadius() * circle.getRadius();
        total += area;
        System.out.printf("Dien tich hinh tron: %.2f%n", area);
    }

    @Override
    public void visitRectangle(Rectangle rectangle) {
        double area = rectangle.getWidth() * rectangle.getHeight();
        total += area;
        System.out.printf("Dien tich hinh chu nhat: %.2f%n", area);
    }

    public double getTotal() {
        return total;
    }
}
