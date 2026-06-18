/**
 * Shape - vai trò Prototype (interface) trong mẫu Prototype.
 * Mỗi hình phải biết tự sao chép (clone) chính nó.
 */
public interface Shape {
    Shape cloneShape();
    void draw();
}
