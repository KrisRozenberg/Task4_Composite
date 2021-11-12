package org.rozenberg.task4.interpreter;

import java.util.ArrayDeque;

public class NumberContext {
    private final ArrayDeque<Integer> numbers = new ArrayDeque<>();

    public void push(int number) {
        numbers.push(number);
    }

    public Integer pop() {
        return numbers.pop();
    }
}
