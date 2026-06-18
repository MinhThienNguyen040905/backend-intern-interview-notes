/**
 * XmlExportVisitor - ConcreteVisitor: xuất mỗi hình ra XML.
 *
 * Đây là THAO TÁC MỚI được thêm mà KHÔNG cần sửa các lớp Circle/Rectangle.
 */
public class XmlExportVisitor implements ShapeVisitor {

    @Override
    public void visitCircle(Circle circle) {
        System.out.println("<circle radius=\"" + circle.getRadius() + "\"/>");
    }

    @Override
    public void visitRectangle(Rectangle rectangle) {
        System.out.println("<rectangle width=\"" + rectangle.getWidth()
                + "\" height=\"" + rectangle.getHeight() + "\"/>");
    }
}
