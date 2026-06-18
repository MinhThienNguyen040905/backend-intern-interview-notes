/**
 * TreeType - vai trò ConcreteFlyweight.
 *
 * Lưu trạng thái INTRINSIC (dùng chung, bất biến): tên loài, màu, texture.
 * Nhận trạng thái EXTRINSIC (x, y) từ bên ngoài khi vẽ.
 */
public class TreeType {
    // ----- Trạng thái intrinsic (chia sẻ, bất biến) -----
    private final String name;
    private final String color;
    private final String texture;

    public TreeType(String name, String color, String texture) {
        this.name = name;
        this.color = color;
        this.texture = texture;
        System.out.println("   (Tao moi TreeType: " + name + "/" + color + ")");
    }

    // Nhận extrinsic (x, y) truyền từ ngoài vào.
    public void draw(int x, int y) {
        System.out.println("Ve cay " + name + " mau " + color
                + " tai (" + x + "," + y + ")");
    }
}
