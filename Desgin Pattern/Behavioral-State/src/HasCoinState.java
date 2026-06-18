/**
 * HasCoinState - ConcreteState: máy đã có tiền, chờ bấm nút.
 */
public class HasCoinState implements State {
    private final VendingMachine machine;

    public HasCoinState(VendingMachine machine) {
        this.machine = machine;
    }

    @Override
    public void insertCoin() {
        System.out.println("Da co tien roi, khong nhan them");
    }

    @Override
    public void pressButton() {
        System.out.println("Bam nut -> chuyen sang trang thai NHA HANG");
        machine.setState(machine.getSoldState());
    }
}
