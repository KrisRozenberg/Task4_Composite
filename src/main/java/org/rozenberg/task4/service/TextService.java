package org.rozenberg.task4.service;

import org.rozenberg.task4.entity.TextComponent;
import org.rozenberg.task4.entity.TextComposite;
import org.rozenberg.task4.exception.CustomException;

import java.util.List;
import java.util.Map;

public interface TextService {
    TextComponent sortParagraphsBySentencesNumber(TextComponent text) throws CustomException;
    List<String> findSentencesWithLongestWord(TextComponent text);
    TextComponent deleteSentencesWithLessWordsNumber(TextComponent text, int wordNumber);
    Map<String, Integer> findAllRepeatedWordsNumber(TextComponent text);
    int findVowelsNumberInSentence(TextComponent sentence);
    int findConsonantsNumberInSentence(TextComponent sentence);
}
