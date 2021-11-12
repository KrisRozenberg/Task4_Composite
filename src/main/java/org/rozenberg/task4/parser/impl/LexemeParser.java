package org.rozenberg.task4.parser.impl;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.rozenberg.task4.entity.Symbol;
import org.rozenberg.task4.entity.TextComponent;
import org.rozenberg.task4.entity.TextComponentType;
import org.rozenberg.task4.entity.TextComposite;
import org.rozenberg.task4.exception.CustomException;
import org.rozenberg.task4.parser.GeneralTextParser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

class LexemeParser implements GeneralTextParser {
    private static final Logger logger = LogManager.getLogger();
    private static final String LEXEME_DELIMITER = "\\p{Blank}|\\n";
    private static final String WORD_PATTERN = "[\\w[А-Я][а-я]]+";
    private static final String WORD_OR_SERVICE_SYMBOL_OR_NUMBER_PATTERN = "[\\w[А-Я][а-я]]+|[\\p{Punct}…]|\\p{Digit}";
    private final WordParser wordParser = new WordParser();

    @Override
    public void parse(TextComponent component, String data) throws CustomException {
        Pattern lexemeDelimiterPattern = Pattern.compile(LEXEME_DELIMITER);
        if (lexemeDelimiterPattern.matcher(data).find()) {
            logger.log(Level.ERROR, "Inappropriate text component type for provided data");
            throw new CustomException("Inappropriate text component type for provided data");
        }
        Pattern wordOrServiceOrNumberPattern = Pattern.compile(WORD_OR_SERVICE_SYMBOL_OR_NUMBER_PATTERN);
        Matcher wordOrServiceOrNumberMatcher = wordOrServiceOrNumberPattern.matcher(data);
        while (wordOrServiceOrNumberMatcher.find()) {
            String foundPart = wordOrServiceOrNumberMatcher.group();
            Pattern wordPattern = Pattern.compile(WORD_PATTERN);
            Matcher wordMatcher = wordPattern.matcher(foundPart);
            if (wordMatcher.matches()) {
                TextComponent wordComponent = new TextComposite(TextComponentType.WORD);
                component.add(wordComponent);
                wordParser.parse(wordComponent, foundPart);
            }
            else {
                TextComponent symbolLeafComponent = new Symbol(foundPart.charAt(0), TextComponentType.SERVICE_SYMBOL);
                component.add(symbolLeafComponent);
            }
        }
    }
}
