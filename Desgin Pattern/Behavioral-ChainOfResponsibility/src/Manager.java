/** Manager - ConcreteHandler: duyệt khoản <= 10.000.000d. */
public class Manager extends Approver {
    public Manager(String name) { super(name); }

    @Override protected boolean canApprove(int amount) {
        return amount <= 10_000_000;
    }

    @Override protected void approve(int amount) {
        System.out.println("Truong phong " + name + " duyet " + amount + "d");
    }
}
