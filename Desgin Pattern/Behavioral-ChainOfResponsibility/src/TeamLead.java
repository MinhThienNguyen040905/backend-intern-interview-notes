/** TeamLead - ConcreteHandler: duyệt khoản <= 1.000.000d. */
public class TeamLead extends Approver {
    public TeamLead(String name) { super(name); }

    @Override protected boolean canApprove(int amount) {
        return amount <= 1_000_000;
    }

    @Override protected void approve(int amount) {
        System.out.println("Truong nhom " + name + " duyet " + amount + "d");
    }
}
