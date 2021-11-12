package org.rozenberg.task4.parser.impl;

import org.rozenberg.task4.entity.TextComponent;
import org.rozenberg.task4.entity.TextComponentType;
import org.rozenberg.task4.entity.TextComposite;
import org.rozenberg.task4.exception.CustomException;
import org.rozenberg.task4.parser.GeneralTextParser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

class ParagraphParser implements GeneralTextParser {
    private static final String SENTENCE_PATTERN = "(\\p{Upper}|[А-Я]|\\p{Digit}).*?[.?!…](\\s|$)";
    private final SentenceParser sentenceParser = new SentenceParser();

    @Override
    public void parse(TextComponent component, String data) throws CustomException {
        Pattern pattern = Pattern.compile(SENTENCE_PATTERN);
        Matcher matcher = pattern.matcher(data);
        while (matcher.find()) {
            TextComponent sentenceComponent = new TextComposite(TextComponentType.SENTENCE);
            component.add(sentenceComponent);
            sentenceParser.parse(sentenceComponent, matcher.group().trim());
        }
    }
}
