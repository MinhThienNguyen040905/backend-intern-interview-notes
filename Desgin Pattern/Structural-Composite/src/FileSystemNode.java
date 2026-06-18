/**
 * FileSystemNode - vai trò Component trong mẫu Composite.
 * Giao diện chung cho cả File (lá) lẫn Folder (nhóm).
 */
public interface FileSystemNode {
    String getName();
    int getSize();                 // kích thước (KB)
    void print(String indent);     // in cây có thụt lề
}
