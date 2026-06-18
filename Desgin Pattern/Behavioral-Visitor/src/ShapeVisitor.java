/**
 * ShapeVisitor - vai trò Visitor: khai báo một phương thức visit cho MỖI
 * loại hình cụ thể.
 */
public interface ShapeVisitor {
    void visitCircle(Circle circle);
    void visitRectangle(Rectangle rectangle);
}
