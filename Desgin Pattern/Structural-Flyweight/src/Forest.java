import java.util.ArrayList;
import java.util.List;

/**
 * Forest - vai trò Client.
 *
 * Mỗi cây trong rừng chỉ lưu EXTRINSIC (x, y) + tham chiếu tới TreeType
 * dùng chung (flyweight). Nhờ vậy hàng nghìn cây chỉ tốn vài TreeType.
 */
public class Forest {

    // Lớp con biểu diễn một cây cụ thể: chỉ giữ extrinsic + con trỏ flyweight.
    private static class Tree {
        final int x, y;
        final TreeType type;
        Tree(int x, int y, TreeType type) { this.x = x; this.y = y; this.type = type; }
        void draw() { type.draw(x, y); }
    }

    private final List<Tree> trees = new ArrayList<>();

    public void plant(int x, int y, String name, String color, String texture) {
        TreeType type = TreeTypeFactory.get(name, color, texture); // lấy flyweight chia sẻ
        trees.add(new Tree(x, y, type));
    }

    public void draw() {
        for (Tree t : trees) {
            t.draw();
        }
    }

    public int treeCount() {
        return trees.size();
    }
}
