package org.rozenberg.task4.parser;

import org.rozenberg.task4.entity.TextComponent;
import org.rozenberg.task4.exception.CustomException;

public interface GeneralTextParser {
    void parse(TextComponent component, String data) throws CustomException;
}
