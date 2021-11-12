package org.rozenberg.task4.reader;

import org.rozenberg.task4.exception.CustomException;

import java.util.List;

public interface CustomReader {
    String readAllText(String resourcePath) throws CustomException;
}
