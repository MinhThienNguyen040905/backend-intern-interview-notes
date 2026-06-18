/**
 * SoldState - ConcreteState: máy đang nhả hàng, sau đó quay về chờ tiền.
 */
public class SoldState implements State {
    private final VendingMachine machine;

    public SoldState(VendingMachine machine) {
        this.machine = machine;
    }

    @Override
    public void insertCoin() {
        System.out.println("Dang nha hang, vui long doi");
    }

    @Override
    public void pressButton() {
        System.out.println("Dang nha hang, khong bam them duoc");
    }

    // Gọi sau khi nhả hàng xong để quay về trạng thái ban đầu.
    public void dispense() {
        System.out.println("Nha 1 san pham -> quay ve trang thai CHO TIEN");
        machine.setState(machine.getNoCoinState());
    }
}
