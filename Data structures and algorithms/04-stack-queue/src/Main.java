import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Queue;

public class Main {
    static boolean isValidParentheses(String s) {
        Deque<Character> stack = new ArrayDeque<>();

        for (char c : s.toCharArray()) {
            if (c == '(' || c == '[' || c == '{') {
                stack.push(c);
            } else {
                if (stack.isEmpty()) return false;
                char open = stack.pop();
                if (c == ')' && open != '(') return false;
                if (c == ']' && open != '[') return false;
                if (c == '}' && open != '{') return false;
            }
        }

        return stack.isEmpty();
    }

    static void bfsDemo() {
        Queue<Integer> queue = new ArrayDeque<>();
        queue.offer(1);
        queue.offer(2);
        queue.offer(3);

        while (!queue.isEmpty()) {
            System.out.print(queue.poll() + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        System.out.println(isValidParentheses("([]{})"));
        bfsDemo();
    }
}

