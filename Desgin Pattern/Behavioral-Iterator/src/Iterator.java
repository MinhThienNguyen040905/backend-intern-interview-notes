/**
 * Iterator - giao diện duyệt tự định nghĩa (minh họa mẫu Iterator).
 * Trong thực tế Java nên dùng java.util.Iterator; ở đây tự định nghĩa
 * để thể hiện rõ vai trò của mẫu.
 */
public interface Iterator<T> {
    boolean hasNext();
    T next();
}
