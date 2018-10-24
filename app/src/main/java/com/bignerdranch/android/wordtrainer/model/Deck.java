package com.bignerdranch.android.wordtrainer.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class Deck {
    private UUID mID;
    private String mNameDeck;
    private List<Word> mWordsInDeck;
    private List<Word> mWordsForLearningToday;

    public UUID getID() {
        return mID;
    }

    public void setID(UUID ID) {
        mID = ID;
    }


    public Deck(String nameDeck) {
        mNameDeck = nameDeck;
        mID = UUID.randomUUID();
        mWordsInDeck = new ArrayList<>();
        Word word = new Word();
        word.setEnglishWord("Hello");
        word.setTranslate("Privet");
        mWordsInDeck.add(word);

        mWordsForLearningToday = new ArrayList<>();
        updateWordsForLearningToday();
    }

    public String getNameDeck() {
        return mNameDeck;
    }


    public List<Word> getWordsForLearningToday() {
        return mWordsForLearningToday;
    }

    public List<Word> getWordsInDeck() {
        return mWordsInDeck;
    }

    public void setWordsInDeck(List<Word> wordsInDeck) {
        mWordsInDeck = wordsInDeck;
    }

    public void setWord(Word word) {
        mWordsInDeck.add(word);
        updateWordsForLearningToday();
    }

    public Word getWord(UUID id) {
        for (Word word : mWordsInDeck
                ) {
            if (word.getId().equals(id)) {
                return word;
            }
        }
        return null;
    }

    public Word getWordForLearningToday() {
        if (mWordsForLearningToday.isEmpty()) {
            Word word = new Word();
            word.setEnglishWord("Nothing for learning today");
            mWordsForLearningToday.add(word);
        }
        return getRandomWord(mWordsForLearningToday);
    }

    public Word getWordFromDesk() {
        return getRandomWord(mWordsInDeck);
    }

    private Word getRandomWord(List<Word> wordList) {
        int rnd = new Random().nextInt(wordList.size());
        return wordList.get(rnd);
    }
    public void updateWordsForLearningToday() {
        mWordsForLearningToday.clear();
        Date dateToday = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateToday);
        int dayToday = calendar.get(Calendar.DAY_OF_MONTH);
        for (Word word : mWordsInDeck
                ) {
            Date dateWord = word.getDateForLearn();
            calendar.setTime(dateWord);
            int dayWord = calendar.get(Calendar.DAY_OF_MONTH);
            if (dayToday == dayWord) {
                mWordsForLearningToday.add(word);
            }
        }

    }
}
