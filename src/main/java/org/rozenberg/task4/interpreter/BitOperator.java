package org.rozenberg.task4.interpreter;

import org.rozenberg.task4.exception.CustomException;

public class BitOperator {
    public static final String LEFT_SHIFT = "<<";
    public static final String RIGHT_SHIFT = ">>";
    public static final String AND = "&";
    public static final String OR = "|";
    public static final String XOR = "^";
    public static final String NEGATION = "~";

    public static int getPriority(String operator) throws CustomException {
        return switch (operator) {
            case NEGATION -> 5;
            case LEFT_SHIFT, RIGHT_SHIFT -> 4;
            case AND -> 3;
            case XOR -> 2;
            case OR -> 1;
            default -> throw new CustomException("Provided parameter isn't a bit operator");
        };
    }
}
