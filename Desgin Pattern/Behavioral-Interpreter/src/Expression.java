/**
 * Expression - AbstractExpression: mọi node trong cây cú pháp đều biết
 * tự diễn giải (interpret) thành một giá trị số.
 */
public interface Expression {
    int interpret();
}
