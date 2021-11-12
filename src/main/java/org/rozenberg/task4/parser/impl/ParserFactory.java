package org.rozenberg.task4.parser.impl;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.rozenberg.task4.entity.TextComponent;
import org.rozenberg.task4.exception.CustomException;
import org.rozenberg.task4.parser.GeneralTextParser;

public class ParserFactory implements GeneralTextParser {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public void parse(TextComponent component, String data) throws CustomException {
        switch (component.getComponentType().name()){
            case "TEXT" -> {
                TextParser textParser = new TextParser();
                textParser.parse(component, data);
            }
            case "PARAGRAPH" -> {
                ParagraphParser paragraphParser = new ParagraphParser();
                paragraphParser.parse(component, data);
            }
            case "SENTENCE" -> {
                SentenceParser sentenceParser = new SentenceParser();
                sentenceParser.parse(component, data);
            }
            case "LEXEME" -> {
                LexemeParser lexemeParser = new LexemeParser();
                lexemeParser.parse(component, data);
            }
            case "WORD" -> {
                WordParser wordParser = new WordParser();
                wordParser.parse(component, data);
            }
            case "LETTER", "DIGIT", "SERVICE_SYMBOL" -> {
                logger.log(Level.ERROR, "Inappropriate text component type for provided data");
                throw new CustomException("Inappropriate text component type for provided data");
            }
            default -> {
                logger.log(Level.ERROR, "Unexpected exception");
                throw new CustomException("Unexpected exception");
            }
        }
    }
}
