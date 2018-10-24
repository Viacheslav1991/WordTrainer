package com.bignerdranch.android.wordtrainer.model;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Viacheslav on 03.07.2018.
 */

public class Word {
    private Deck mDeck;
    private UUID mId;
    private String mEnglishWord;
    private String mTranslate;
    private boolean mLearned;
    private int mCountCorrectAnswers;


    public Date getDateForLearn() {
        return mDateForLearn;
    }

    private Date mDateForLearn;

    public Word() {
        this(UUID.randomUUID());
    }

    public Word(UUID uuid) {
        mId = uuid;
        mDateForLearn = new Date();
    }

    public String getEnglishWord() {
        return mEnglishWord;
    }

    public void setEnglishWord(String englishWord) {
        mEnglishWord = englishWord;
    }

    public String getTranslate() {
        return mTranslate;
    }

    public void setTranslate(String translate) {
        mTranslate = translate;
    }

    public boolean isLearned() {
        return mLearned;
    }

    public void setLearned(boolean learned) {
        mLearned = learned;
    }

    public UUID getId() {
        return mId;
    }

    public void addCorrectAnswer() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mDateForLearn);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        mCountCorrectAnswers++;
        if (mCountCorrectAnswers < 6) {
            switch (mCountCorrectAnswers) {
                case 1:
//                day++;
//                calendar.set(Calendar.DAY_OF_MONTH, day);
                    calendar.add(Calendar.MINUTE, 20);
                    mDateForLearn = calendar.getTime();
                    break;
                case 2:
//                day = day + 4;
//                calendar.set(Calendar.DAY_OF_MONTH, day);
                    calendar.add(Calendar.HOUR_OF_DAY, 8);
                    mDateForLearn = calendar.getTime();
                    break;
                case 3:
//                day = day + 7;
//                calendar.set(Calendar.DAY_OF_MONTH, day);
                    calendar.add(Calendar.DAY_OF_MONTH, 1);
                    mDateForLearn = calendar.getTime();
                    break;
                case 4:
//                day = day + 14;
//                calendar.set(Calendar.DAY_OF_MONTH, day);
                    calendar.add(Calendar.DAY_OF_MONTH, 4);
                    mDateForLearn = calendar.getTime();
                    break;
                case 5:
                    calendar.add(Calendar.DAY_OF_MONTH, 7);
                    mDateForLearn = calendar.getTime();
                    break;

            }

        } else {
            for (int i = 1; i < mCountCorrectAnswers - 5; i++) {
                calendar.add(Calendar.DAY_OF_MONTH, i * 20);
                mDateForLearn = calendar.getTime();
            }
        }
        setLearned(true);
    }

    public void addIncorrectAnswer() {
        mCountCorrectAnswers--;
    }
}
