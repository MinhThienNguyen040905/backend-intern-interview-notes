/**
 * Shape - vai trò Element trong mẫu Visitor.
 * Mỗi hình phải biết "tiếp đón" một visitor.
 */
public interface Shape {
    void accept(ShapeVisitor visitor);
}
