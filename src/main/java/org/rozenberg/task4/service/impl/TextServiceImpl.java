package org.rozenberg.task4.service.impl;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.rozenberg.task4.entity.TextComponent;
import org.rozenberg.task4.entity.TextComponentType;
import org.rozenberg.task4.entity.TextComposite;
import org.rozenberg.task4.exception.CustomException;
import org.rozenberg.task4.service.TextService;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class TextServiceImpl implements TextService {
    private static final Logger logger = LogManager.getLogger();
    private static final String VOWEL_PATTERN = "[aeiouyауоеияюёэыAEIOYАУОЕИЯЮЁЭЫ]";
    private static final String ANY_LETTER_PATTERN = "[A-Za-zА-Яа-я]";

    @Override
    public TextComponent sortParagraphsBySentencesNumber(TextComponent text) throws CustomException {
        if (text.getComponentType() != TextComponentType.TEXT) {
            logger.log(Level.ERROR, "Sorting paragraphs operation cannot be applied: provided text-component is not text");
            throw new CustomException("Sorting paragraphs operation cannot be applied: provided text-component is not text");
        }
        logger.log(Level.INFO, "Sorting paragraphs by sentences number...");
        TextComponent result = new TextComposite(TextComponentType.TEXT);
        List<TextComponent> paragraphsList = text.getChildren().stream()
                .sorted(Comparator.comparingInt(paragraph -> paragraph.getChildren().size()))
                .collect(Collectors.toList());
        paragraphsList.forEach(result::add);
        return result;
    }

    @Override
    public List<String> findSentencesWithLongestWord(TextComponent text) {
        return text.getChildren().stream().
                flatMap(paragraph -> paragraph.getChildren().stream()
                        .filter(sentence -> isSentenceContainWord(sentence, findLongestWordInText(text))))
                .map(TextComponent::toString)
                .collect(Collectors.toList());
    }

    @Override
    public TextComponent deleteSentencesWithLessWordsNumber(TextComponent text, int wordNumber) { //TODO ?сделать конструктор копирования?
        TextComponent textCopy = text.getTextComponentCopy();
        textCopy.getChildren()
                .forEach(paragraph -> paragraph.getChildren()
                        .forEach(sentence -> {
                            if (getWordsNumberInSentence(sentence) < wordNumber){
                                paragraph.remove(sentence);
                            }
                        }));
        return textCopy;
    }

    @Override
    public Map<String, Integer> findAllRepeatedWordsNumber(TextComponent text) {
        List<String> allWords = findAllWordsInText(text);
        Map<String, Integer> result = new HashMap<>();
        allWords.forEach(word -> {
            String normWord = word.toLowerCase();
            if (result.containsKey(normWord)) {
                int frequency = result.get(normWord);
                result.put(normWord, ++frequency);
            }
            else {
                result.put(normWord, 1);
            }
        });
        result.entrySet().removeIf(item -> item.getValue() == 1);
        return result;
    }

    @Override
    public int findVowelsNumberInSentence(TextComponent sentence) {
        Pattern vowelPattern = Pattern.compile(VOWEL_PATTERN);
        return (int) sentence.getChildren().stream()
                .flatMap(lexeme -> lexeme.getChildren().stream()
                        .filter(part -> part.getComponentType() == TextComponentType.WORD)
                        .flatMap(word -> word.getChildren().stream()
                                .filter(symbol -> vowelPattern.matcher(symbol.toString()).matches()))).count();
    }

    @Override
    public int findConsonantsNumberInSentence(TextComponent sentence) {
        Pattern vowelPattern = Pattern.compile(VOWEL_PATTERN);
        Pattern anyLetterPattern = Pattern.compile(ANY_LETTER_PATTERN);
        return (int) sentence.getChildren().stream()
                .flatMap(lexeme -> lexeme.getChildren().stream()
                        .filter(part -> part.getComponentType() == TextComponentType.WORD)
                        .flatMap(word -> word.getChildren().stream()
                                .filter(symbol -> (anyLetterPattern.matcher(symbol.toString()).matches() &&
                                        !vowelPattern.matcher(symbol.toString()).matches())))).count();
    }


    private TextComponent findLongestWordInSentence(TextComponent sentence) {
        return sentence.getChildren().stream()
                .flatMap(lexeme -> lexeme.getChildren().stream()
                        .filter(part -> part.getComponentType() == TextComponentType.WORD))
                .max(Comparator.comparingInt(word -> word.getChildren().size()))
                .orElse(new TextComposite(TextComponentType.WORD));
    }

    private TextComponent findLongestWordInParagraph(TextComponent paragraph) {
        return paragraph.getChildren().stream()
                .map(this::findLongestWordInSentence)
                .max(Comparator.comparingInt(word -> word.getChildren().size()))
                .orElse(new TextComposite(TextComponentType.WORD));
    }

    private TextComponent findLongestWordInText(TextComponent text) {
        return text.getChildren().stream()
                .map(this::findLongestWordInParagraph)
                .max(Comparator.comparingInt(word -> word.getChildren().size()))
                .orElse(new TextComposite(TextComponentType.WORD));
    }

    private boolean isSentenceContainWord(TextComponent sentence, TextComponent word) { //TODO ?composite equals?
        return sentence.getChildren().stream()
                .flatMap(lexeme -> lexeme.getChildren().stream()
                        .filter(part -> part.getComponentType() == TextComponentType.WORD))
                .anyMatch(sentenceWord -> sentenceWord.toString().equals(word.toString()));
    }

    private int getWordsNumberInSentence(TextComponent sentence) {
        return (int) sentence.getChildren().stream()
                .flatMap(lexeme -> lexeme.getChildren().stream())
                .filter(part -> part.getComponentType() == TextComponentType.WORD).count();
    }

    private List<String> findAllWordsInText(TextComponent text) {
        return text.getChildren().stream()
                .flatMap(paragraph -> paragraph.getChildren().stream()
                        .flatMap(sentence -> sentence.getChildren().stream()
                                .flatMap(lexeme -> lexeme.getChildren().stream()
                                        .filter(part -> part.getComponentType() == TextComponentType.WORD))))
                .map(TextComponent::toString)
                .collect(Collectors.toList());
    }
}
