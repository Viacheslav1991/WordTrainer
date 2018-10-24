package com.bignerdranch.android.wordtrainer;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import java.util.UUID;

public class WordActivity extends SingleFragmentActivity {

    public static final String EXTRA_DECK_ID =
            "com.bignerdranch.android.wordtrainer.deck_id_for_word";
    public static final String EXTRA_WORD_ID =
            "com.bignerdranch.android.wordtrainer.word_id";

    @Override
    protected Fragment createFragment() {
        UUID deckID = (UUID) getIntent().getSerializableExtra(EXTRA_DECK_ID);
        UUID wordID = (UUID) getIntent().getSerializableExtra(EXTRA_WORD_ID);
        return WordFragment.newInstance(deckID, wordID);

    }
    public static Intent newIntent(Context packageContext, UUID deckID, UUID wordID) {
        Intent intent = new Intent(packageContext, WordActivity.class);
        intent.putExtra(EXTRA_DECK_ID, deckID);
        intent.putExtra(EXTRA_WORD_ID, wordID);
        return intent;
    }

}
