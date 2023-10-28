package edu.hw2.task1;

public sealed interface Expr {
    double evaluate();

    public record Constant(double value) implements Expr {
        @Override
        public double evaluate() {
            return value;
        }
    }

    public record Negate(Expr operand) implements Expr {
        @Override
        public double evaluate() {
            return -operand.evaluate();
        }
    }

    public record Exponent(Expr operand, Expr degree) implements Expr {
        @Override
        public double evaluate() {
            return Math.pow(operand.evaluate(), degree.evaluate());
        }
    }

    public record Addition(Expr first, Expr second) implements Expr {
        @Override
        public double evaluate() {
            return first.evaluate() + second.evaluate();
        }
    }

    public record Multiplication(Expr first, Expr second) implements Expr {
        @Override
        public double evaluate() {
            return first.evaluate() * second.evaluate();
        }
    }
}
