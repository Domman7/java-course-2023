package edu.hw2;

import edu.hw2.task1.Expr;
import edu.hw2.task2.Rectangle;
import edu.hw2.task2.Square;
import edu.hw2.Main.CallingInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static edu.hw2.Main.callingInfo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SampleTest {
    @DisplayName("1. Калькулятор выражений")
    @Test
    public void exprTest() {
        var two = new Expr.Constant(2);
        var four = new Expr.Constant(4);
        var negOne = new Expr.Negate(new Expr.Constant(1));
        var sumTwoFour = new Expr.Addition(two, four);
        var mult = new Expr.Multiplication(sumTwoFour, negOne);
        var exp = new Expr.Exponent(mult, new Expr.Constant(2));
        var res = new Expr.Addition(exp, new Expr.Constant(1));
        var expected = 37.0;

        assertEquals(expected, res.evaluate());
    }

    static Arguments[] rectangles() {
        return new Arguments[] {
            Arguments.of(new Rectangle()),
            Arguments.of(new Square())
        };
    }

    @DisplayName("2. Квадрат и прямоугольник")
    @ParameterizedTest
    @MethodSource("rectangles")
    void rectangleAreaTest(Rectangle rect) {
        rect = rect.setWidth(10);
        rect = rect.setHeight(20);

        assertEquals(200.0, rect.area());
    }

    @DisplayName("4. Кто вызвал функцию?")
    @Test
    void callingInfoTest() {
        CallingInfo info = callingInfo();
        var callingClass = info.className();
        var callingMethod = info.methodName();

        assertEquals("edu.hw2.SampleTest", callingClass);
        assertEquals("callingInfoTest", callingMethod);
    }
}
