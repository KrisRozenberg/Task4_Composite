package org.rozenberg.task4.parser.impl;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.rozenberg.task4.entity.TextComponent;
import org.rozenberg.task4.entity.TextComponentType;
import org.rozenberg.task4.entity.TextComposite;
import org.rozenberg.task4.exception.CustomException;
import org.rozenberg.task4.parser.GeneralTextParser;

class TextParser implements GeneralTextParser {
    private static final Logger logger = LogManager.getLogger();
    private static final String PARAGRAPH_DELIMITER = "\\n\\t|\\n\\s{4}";
    private final ParagraphParser paragraphParser = new ParagraphParser();

    @Override
    public void parse(TextComponent component, String data) throws CustomException {
        String[] paragraphs = data.trim().split(PARAGRAPH_DELIMITER);
        for (String paragraph: paragraphs) {
            TextComponent paragraphComponent = new TextComposite(TextComponentType.PARAGRAPH);
            component.add(paragraphComponent);
            paragraphParser.parse(paragraphComponent, paragraph);
        }
        logger.log(Level.INFO, "Text parsing complete");
    }
}

