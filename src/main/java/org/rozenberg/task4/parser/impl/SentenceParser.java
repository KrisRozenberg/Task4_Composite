package org.rozenberg.task4.parser.impl;

import org.rozenberg.task4.entity.TextComponent;
import org.rozenberg.task4.entity.TextComponentType;
import org.rozenberg.task4.entity.TextComposite;
import org.rozenberg.task4.exception.CustomException;
import org.rozenberg.task4.interpreter.BitExpressionCalculator;
import org.rozenberg.task4.parser.GeneralTextParser;

import java.util.regex.Pattern;

class SentenceParser implements GeneralTextParser {
    private static final String LEXEME_DELIMITER = "\\s+";
    private static final String BIT_OPERATION_PATTERN = "(\\d+|[~&^|()]|(<<)|(>>)){2,}[.?!…]?";
    private static final String END_PUNCTUATION_PATTERN = "[.?!…]";
    private final LexemeParser lexemeParser = new LexemeParser();
    private final BitExpressionCalculator expressionCalculator = new BitExpressionCalculator();

    @Override
    public void parse(TextComponent component, String data) throws CustomException {
        String[] lexemes = data.split(LEXEME_DELIMITER);
        Pattern bitOperationPattern = Pattern.compile(BIT_OPERATION_PATTERN);
        Pattern endPunctuationPattern = Pattern.compile(END_PUNCTUATION_PATTERN);
        for (String lexeme: lexemes) {
            TextComponent lexemeComponent = new TextComposite(TextComponentType.LEXEME);
            component.add(lexemeComponent);
            if (bitOperationPattern.matcher(lexeme).matches()) {
                if (endPunctuationPattern.matcher(lexeme).find()) {
                    int expressionResult = expressionCalculator.calculate(lexeme.substring(0, lexeme.length()-1));
                    lexemeParser.parse(lexemeComponent, String.valueOf(expressionResult).concat(lexeme.substring(lexeme.length()-1)));
                }
                else {
                    int expressionResult = expressionCalculator.calculate(lexeme);
                    lexemeParser.parse(lexemeComponent, String.valueOf(expressionResult));
                }
            }
            else {
                lexemeParser.parse(lexemeComponent, lexeme);
            }
        }
    }
}

