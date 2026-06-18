/**
 * RealImage - vai trò RealSubject: đối tượng thật, KHỞI TẠO TỐN KÉM
 * (giả lập tải ảnh từ đĩa).
 */
public class RealImage implements Image {
    private final String fileName;

    public RealImage(String fileName) {
        this.fileName = fileName;
        loadFromDisk();   // việc nặng xảy ra ngay khi tạo
    }

    private void loadFromDisk() {
        System.out.println("   [Tai anh tu dia: " + fileName + " ... (ton kem)]");
    }

    @Override
    public void display() {
        System.out.println("Hien thi anh: " + fileName);
    }
}
