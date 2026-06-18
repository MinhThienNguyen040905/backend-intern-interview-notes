import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Main - demo mẫu Interpreter.
 *
 * Biên dịch & chạy (trong thư mục src/):
 *   javac -encoding UTF-8 *.java
 *   java Main
 *
 * Biểu thức số học hậu tố (RPN), ví dụ "5 3 + 2 -" nghĩa là (5 + 3) - 2.
 * Phần parse (dựng AST) do client làm; phần DIỄN GIẢI do cây Expression lo.
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("=== DEMO MAU INTERPRETER (Bieu thuc so hoc) ===\n");

        String[] inputs = { "5 3 +", "5 3 + 2 -", "10 4 - 3 +" };
        for (String input : inputs) {
            Expression ast = parse(input);     // dựng cây cú pháp
            int result = ast.interpret();      // diễn giải đệ quy
            System.out.println("\"" + input + "\"  =  " + result);
        }
    }

    // Parser nhỏ cho ký pháp hậu tố -> dựng AST gồm các Expression.
    private static Expression parse(String rpn) {
        Deque<Expression> stack = new ArrayDeque<>();
        for (String token : rpn.trim().split("\\s+")) {
            switch (token) {
                case "+": {
                    Expression r = stack.pop(), l = stack.pop();
                    stack.push(new AddExpression(l, r));
                    break;
                }
                case "-": {
                    Expression r = stack.pop(), l = stack.pop();
                    stack.push(new SubtractExpression(l, r));
                    break;
                }
                default:
                    stack.push(new NumberExpression(Integer.parseInt(token)));
            }
        }
        return stack.pop();
    }
}
