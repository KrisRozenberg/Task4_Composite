package org.rozenberg.task4.interpreter;

@FunctionalInterface
public interface BitExpression {
    void interpret(NumberContext numberContext);
}
