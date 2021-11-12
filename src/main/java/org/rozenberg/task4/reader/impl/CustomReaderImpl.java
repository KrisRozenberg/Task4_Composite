package org.rozenberg.task4.reader.impl;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.rozenberg.task4.exception.CustomException;
import org.rozenberg.task4.reader.CustomReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CustomReaderImpl implements CustomReader{
    private static final Logger logger = LogManager.getLogger();

    @Override
    public String readAllText(String resourcePath) throws CustomException {
        if (resourcePath == null) {
            logger.log(Level.ERROR, "Provided filepath is null");
            throw new CustomException("Provided filepath is null");
        }

        String text;
        try (InputStream stream = getClass().getClassLoader().getResourceAsStream(resourcePath)) {
            if (stream == null) {
                logger.log(Level.ERROR, "Provided filepath is corrupt");
                throw new CustomException("Provided filepath is corrupt");
            }
            try (InputStreamReader in = new InputStreamReader(stream);
                 BufferedReader bufferedReader = new BufferedReader(in);
                 Stream<String> linesStream = bufferedReader.lines()) {
                text = linesStream.collect(Collectors.joining("\n"));
            }
        } catch (IOException e) {
            logger.log(Level.ERROR, "Error while reading file");
            throw new CustomException("Error while reading file", e);
        }

        logger.log(Level.INFO, resourcePath + "file reading is complete");
        return text;
    }
}
