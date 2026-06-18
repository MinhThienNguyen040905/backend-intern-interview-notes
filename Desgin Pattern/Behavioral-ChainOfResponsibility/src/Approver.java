/**
 * Approver - vai trò Handler (trừu tượng) trong Chain of Responsibility.
 *
 * Giữ tham chiếu tới handler kế tiếp (next) và cài sẵn logic:
 * tự xử lý nếu đủ thẩm quyền, ngược lại chuyển tiếp cho next.
 */
public abstract class Approver {
    protected Approver next;
    protected final String name;

    protected Approver(String name) {
        this.name = name;
    }

    // Trả về 'next' để nối chuỗi gọn: a.setNext(b).setNext(c)
    public Approver setNext(Approver next) {
        this.next = next;
        return next;
    }

    public void handle(int amount) {
        if (canApprove(amount)) {
            approve(amount);
        } else if (next != null) {
            next.handle(amount);   // chuyển tiếp dọc chuỗi
        } else {
            System.out.println("Khong ai du tham quyen duyet khoan " + amount + "d");
        }
    }

    protected abstract boolean canApprove(int amount);
    protected abstract void approve(int amount);
}
