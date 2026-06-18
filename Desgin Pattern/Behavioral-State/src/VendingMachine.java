/**
 * VendingMachine - vai trò Context.
 *
 * Giữ tham chiếu tới State hiện tại và ỦY THÁC các thao tác cho nó.
 * Không chứa if/else theo trạng thái - mỗi trạng thái tự lo hành vi của mình.
 */
public class VendingMachine {
    // Tạo sẵn các đối tượng trạng thái.
    private final State noCoinState = new NoCoinState(this);
    private final State hasCoinState = new HasCoinState(this);
    private final State soldState = new SoldState(this);

    private State currentState = noCoinState;

    public void setState(State state) {
        this.currentState = state;
    }

    // Ủy thác cho trạng thái hiện tại.
    public void insertCoin() {
        currentState.insertCoin();
    }

    public void pressButton() {
        currentState.pressButton();
        // Nếu đang ở trạng thái nhả hàng thì nhả rồi quay về chờ tiền.
        if (currentState == soldState) {
            ((SoldState) soldState).dispense();
        }
    }

    public State getNoCoinState() { return noCoinState; }
    public State getHasCoinState() { return hasCoinState; }
    public State getSoldState() { return soldState; }
}
