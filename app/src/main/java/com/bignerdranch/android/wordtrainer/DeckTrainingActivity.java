package com.bignerdranch.android.wordtrainer;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import java.util.UUID;

public class DeckTrainingActivity extends SingleFragmentActivity {
    public static final String EXTRA_DECK_ID =
            "com.bignerdranch.android.wordtrainer.deck_id";

    @Override
    protected Fragment createFragment() {
        UUID deckID = (UUID) getIntent().getSerializableExtra(EXTRA_DECK_ID);
        return DeckTrainingFragment.newInstance(deckID);
   //     return new DeckTrainingFragment();
    }
    public static Intent newIntent(Context packageContext, UUID deckID) {
        Intent intent = new Intent(packageContext, DeckTrainingActivity.class);
        intent.putExtra(EXTRA_DECK_ID, deckID);
        return intent;
    }
}
