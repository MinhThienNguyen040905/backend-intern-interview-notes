import java.util.ArrayList;
import java.util.List;

/**
 * Folder - vai trò Composite: node có con (file và folder con khác).
 * Các thao tác được thực hiện bằng cách CHUYỂN TIẾP đệ quy xuống các con.
 */
public class Folder implements FileSystemNode {
    private final String name;
    private final List<FileSystemNode> children = new ArrayList<>();

    public Folder(String name) {
        this.name = name;
    }

    public Folder add(FileSystemNode node) {
        children.add(node);
        return this;
    }

    public void remove(FileSystemNode node) {
        children.remove(node);
    }

    @Override public String getName() { return name; }

    // Tổng hợp kích thước bằng cách cộng dồn kích thước các con (đệ quy).
    @Override public int getSize() {
        int total = 0;
        for (FileSystemNode child : children) {
            total += child.getSize();
        }
        return total;
    }

    @Override public void print(String indent) {
        System.out.println(indent + "[" + name + "] (" + getSize() + " KB)");
        for (FileSystemNode child : children) {
            child.print(indent + "   ");
        }
    }
}
