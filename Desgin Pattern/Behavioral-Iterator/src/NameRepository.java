/**
 * NameRepository - vai trò ConcreteAggregate.
 *
 * Giấu cấu trúc bên trong (một mảng) và cung cấp iterator để duyệt.
 * - getIterator(): trả về Iterator TỰ ĐỊNH NGHĨA (minh họa mẫu).
 * - cài java.lang.Iterable để dùng được for-each của Java.
 */
public class NameRepository implements Iterable<String> {
    private final String[] names;

    public NameRepository(String... names) {
        this.names = names;
    }

    // Tạo ConcreteIterator (lớp lồng ẩn danh) cho giao diện tự định nghĩa.
    public Iterator<String> getIterator() {
        return new Iterator<String>() {
            private int index = 0;

            @Override public boolean hasNext() {
                return index < names.length;
            }

            @Override public String next() {
                return names[index++];
            }
        };
    }

    // Cài Iterable<String> để hỗ trợ for-each (dùng java.util.Iterator).
    @Override
    public java.util.Iterator<String> iterator() {
        return new java.util.Iterator<String>() {
            private int index = 0;
            @Override public boolean hasNext() { return index < names.length; }
            @Override public String next() { return names[index++]; }
        };
    }
}
