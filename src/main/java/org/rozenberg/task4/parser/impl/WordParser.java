package org.rozenberg.task4.parser.impl;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.rozenberg.task4.entity.Symbol;
import org.rozenberg.task4.entity.TextComponent;
import org.rozenberg.task4.entity.TextComponentType;
import org.rozenberg.task4.exception.CustomException;
import org.rozenberg.task4.parser.GeneralTextParser;

import java.util.regex.Pattern;

class WordParser implements GeneralTextParser {
    private static final Logger logger = LogManager.getLogger();
    private static final String NOT_WORD_PATTERN = "[\\W&&[^А-Я]&&[^а-я]]+";

    @Override
    public void parse(TextComponent component, String data) throws CustomException {
        Pattern notDordPattern = Pattern.compile(NOT_WORD_PATTERN);
        if (notDordPattern.matcher(data).find()) {
            logger.log(Level.ERROR, "Inappropriate text component type for provided data");
            throw new CustomException("Inappropriate text component type for provided data");
        }
        char[] symbols = data.toCharArray();
        for (char symbol: symbols) {
            TextComponent letterOrDigitLeafComponent;
            if (Character.isDigit(symbol)) {
                letterOrDigitLeafComponent = new Symbol(symbol, TextComponentType.DIGIT);
            }
            else {
                letterOrDigitLeafComponent = new Symbol(symbol, TextComponentType.LETTER);
            }
            component.add(letterOrDigitLeafComponent);
        }
    }
}
