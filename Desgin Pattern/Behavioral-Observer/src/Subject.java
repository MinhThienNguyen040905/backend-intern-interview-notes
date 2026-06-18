/**
 * Subject - giao diện chủ thể: quản lý đăng ký và thông báo cho observer.
 */
public interface Subject {
    void attach(Observer observer);
    void detach(Observer observer);
    void notifyObservers();
}
