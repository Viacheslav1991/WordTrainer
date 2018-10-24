package com.bignerdranch.android.wordtrainer.model;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DeckLab {
    private static  DeckLab sDeckLab;
    private  List<Deck> mDecks;
    private Context mContext;

    public static DeckLab getInstance(Context context) {
        if (sDeckLab == null) {
            sDeckLab = new DeckLab(context);
        }
        return sDeckLab;
    }

    private DeckLab(Context context) {
        mContext = context.getApplicationContext();
        mDecks = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Deck deck = new Deck("Deck #" + i);
            mDecks.add(deck);
        }
    }

    public  List<Deck> getDecks() {
        return mDecks;
    }

    public  void setDecks(List<Deck> decks) {
        mDecks = decks;
    }

    public  void addDeck(Deck deck) {
        mDecks.add(deck);
    }

    public Deck getDeck(UUID deckId) {
        for (Deck deck : mDecks
                ) {
            if (deck.getID().equals(deckId)) {
                return deck;
            }
        }
        return null;
    }
}
