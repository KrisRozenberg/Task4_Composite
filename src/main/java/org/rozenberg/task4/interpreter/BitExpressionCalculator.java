package org.rozenberg.task4.interpreter;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.rozenberg.task4.exception.CustomException;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BitExpressionCalculator {
    private static final Logger logger = LogManager.getLogger();
    private static final String ANY_OPERATION_ELEMENT_PATTERN = "\\d+|[~&^|()]|(<<)|(>>)";
    private static final String NOT_BIT_OPERATION_PATTERN = "[^(\\d+|[~&^|()]|(<<)|(>>))]";

    private List<String> parseToPolishNotation(String expression) throws CustomException {
        logger.log(Level.INFO, "Parsing expression to polish notation...");
        List<String> expressionElements = new ArrayList<>();
        Pattern anyOperationElementPattern = Pattern.compile(ANY_OPERATION_ELEMENT_PATTERN);
        Matcher anyOperationElementMatcher = anyOperationElementPattern.matcher(expression);
        while (anyOperationElementMatcher.find()) {
            expressionElements.add(anyOperationElementMatcher.group());
        }
        Stack<String> helper = new Stack<>();
        List<String> result = new ArrayList<>();
        for (String element: expressionElements) {
            switch (element) {
                case "(" -> helper.push("(");
                case ")" -> {
                    while (!helper.peek().equals("(")) {
                        result.add(helper.pop());
                    }
                    helper.pop();
                }
                case "~", "<<", ">>", "&", "^", "|" -> {
                    if (helper.empty() || helper.peek().equals("(") ||
                            BitOperator.getPriority(element) > BitOperator.getPriority(helper.peek())) {
                        helper.push(element);
                    }
                    else if (BitOperator.getPriority(element) <= BitOperator.getPriority(helper.peek())) {
                        do {
                            result.add(helper.pop());
                        }
                        while (!helper.empty() && !helper.peek().equals("(") &&
                                BitOperator.getPriority(element) <= BitOperator.getPriority(helper.peek()));
                        helper.push(element);
                    }
                }
                default -> result.add(element);
            }
        }
        while (!helper.empty()) {
            result.add(helper.pop());
        }
        return result;
    }

    public int calculate(String expression) throws CustomException {
        Pattern notBitOperationPattern = Pattern.compile(NOT_BIT_OPERATION_PATTERN);
        Matcher notBitOperationMatcher = notBitOperationPattern.matcher(expression);
        if (notBitOperationMatcher.find()) {
            logger.log(Level.ERROR, "Provided data is not a bit operation");
            throw new CustomException("Provided data is not a bit operation");
        }
        List<String> polishList = parseToPolishNotation(expression);
        NumberContext numberContext = new NumberContext();
        polishList.forEach(token -> {
            switch (token) {
                case BitOperator.LEFT_SHIFT -> {
                    BitExpression leftShiftExpression = context -> {
                        int num = context.pop();
                        context.push(context.pop() << num);
                    };
                    leftShiftExpression.interpret(numberContext);
                }
                case BitOperator.RIGHT_SHIFT -> {
                    BitExpression rightShiftExpression = context -> {
                        int num = context.pop();
                        context.push(context.pop() >> num);
                    };
                    rightShiftExpression.interpret(numberContext);
                }
                case BitOperator.AND -> {
                    BitExpression andExpression = context -> context.push(context.pop() & context.pop());
                    andExpression.interpret(numberContext);
                }
                case BitOperator.OR -> {
                    BitExpression orExpression = context -> context.push(context.pop() | context.pop());
                    orExpression.interpret(numberContext);
                }
                case BitOperator.XOR -> {
                    BitExpression xorExpression = context -> context.push(context.pop() ^ context.pop());
                    xorExpression.interpret(numberContext);
                }
                case BitOperator.NEGATION -> {
                    BitExpression negationExpression = context -> context.push(~context.pop());
                    negationExpression.interpret(numberContext);
                }
                default -> numberContext.push(Integer.parseInt(token));
            }
        });
        int result = numberContext.pop();
        logger.log(Level.INFO, "The result of provided bit expression is: " + result);
        return result;
    }
}
