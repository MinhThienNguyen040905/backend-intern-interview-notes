/**
 * NoCoinState - ConcreteState: máy đang chờ bỏ tiền.
 */
public class NoCoinState implements State {
    private final VendingMachine machine;

    public NoCoinState(VendingMachine machine) {
        this.machine = machine;
    }

    @Override
    public void insertCoin() {
        System.out.println("Da nhan tien -> chuyen sang trang thai CO TIEN");
        machine.setState(machine.getHasCoinState());
    }

    @Override
    public void pressButton() {
        System.out.println("Vui long bo tien truoc khi bam nut");
    }
}
