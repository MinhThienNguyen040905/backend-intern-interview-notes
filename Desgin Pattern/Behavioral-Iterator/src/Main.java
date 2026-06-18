/**
 * Main - demo mẫu Iterator.
 *
 * Biên dịch & chạy (trong thư mục src/):
 *   javac -encoding UTF-8 *.java
 *   java Main
 *
 * Duyệt tập hợp mà KHÔNG biết bên trong là mảng hay gì khác.
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("=== DEMO MAU ITERATOR (NameRepository) ===\n");

        NameRepository repo = new NameRepository("An", "Binh", "Cuong", "Dung");

        System.out.println(">> Duyet bang iterator tu dinh nghia (hasNext/next):");
        Iterator<String> it = repo.getIterator();
        while (it.hasNext()) {
            System.out.println("  - " + it.next());
        }

        System.out.println("\n>> Duyet bang for-each (nho cai Iterable):");
        for (String name : repo) {
            System.out.println("  - " + name);
        }

        System.out.println("\n>> Hai iterator doc lap chay dong thoi:");
        Iterator<String> a = repo.getIterator();
        Iterator<String> b = repo.getIterator();
        System.out.println("  a.next()=" + a.next() + ", a.next()=" + a.next());
        System.out.println("  b.next()=" + b.next() + " (b van bat dau tu dau)");
    }
}
