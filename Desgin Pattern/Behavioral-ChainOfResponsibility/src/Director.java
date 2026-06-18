/** Director - ConcreteHandler: duyệt khoản <= 100.000.000d. */
public class Director extends Approver {
    public Director(String name) { super(name); }

    @Override protected boolean canApprove(int amount) {
        return amount <= 100_000_000;
    }

    @Override protected void approve(int amount) {
        System.out.println("Giam doc " + name + " duyet " + amount + "d");
    }
}
