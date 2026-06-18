/**
 * FileLeaf - vai trò Leaf: đối tượng lá, không có con.
 */
public class FileLeaf implements FileSystemNode {
    private final String name;
    private final int size;

    public FileLeaf(String name, int size) {
        this.name = name;
        this.size = size;
    }

    @Override public String getName() { return name; }

    @Override public int getSize() { return size; }

    @Override public void print(String indent) {
        System.out.println(indent + "- " + name + " (" + size + " KB)");
    }
}
