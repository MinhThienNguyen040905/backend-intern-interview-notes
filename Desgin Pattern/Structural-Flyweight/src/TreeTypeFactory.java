import java.util.HashMap;
import java.util.Map;

/**
 * TreeTypeFactory - vai trò FlyweightFactory.
 * Quản lý kho (pool) TreeType và ĐẢM BẢO CHIA SẺ: cùng tham số thì trả về
 * cùng một đối tượng thay vì tạo mới.
 */
public class TreeTypeFactory {
    private static final Map<String, TreeType> pool = new HashMap<>();

    public static TreeType get(String name, String color, String texture) {
        String key = name + "-" + color + "-" + texture;
        return pool.computeIfAbsent(key, k -> new TreeType(name, color, texture));
    }

    public static int poolSize() {
        return pool.size();
    }
}
