package com.bignerdranch.android.wordtrainer.model;

import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class WordTest {
    private Word mSubject;

    @Before
    public void setUp() throws Exception {
        mSubject = new Word();
        mSubject.addCorrectAnswer();
    }

    @Test
    public void exposesDateForLearnAsTomorrow() {
        Date date = mSubject.getDateForLearn();
        Calendar calendarSubject = Calendar.getInstance();
        calendarSubject.setTime(date);

        int minInSubject = calendarSubject.get(Calendar.MINUTE);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int minExpectAfter1Correct = (calendar.get(Calendar.MINUTE) + 20)%60;
        assertThat(minInSubject, is(minExpectAfter1Correct));

        mSubject.addCorrectAnswer();
        calendarSubject.setTime(mSubject.getDateForLearn());
        int hoursInSubject = calendarSubject.get(Calendar.HOUR_OF_DAY);
        calendar.add(Calendar.MINUTE, minExpectAfter1Correct);
        int hoursExpectAfter2Correct = calendar.get(Calendar.HOUR_OF_DAY) + 8;
        assertThat(hoursInSubject, is(hoursExpectAfter2Correct));


    }

}