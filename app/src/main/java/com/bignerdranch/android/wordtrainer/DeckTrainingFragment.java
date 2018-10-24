package com.bignerdranch.android.wordtrainer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bignerdranch.android.wordtrainer.model.Deck;
import com.bignerdranch.android.wordtrainer.model.DeckLab;
import com.bignerdranch.android.wordtrainer.model.Word;

import java.util.UUID;

public class DeckTrainingFragment extends Fragment implements View.OnClickListener {
    private static final String ARG_DECK_ID = "deck_id";
    Deck mDeck;
    Word mWord;
    Button mButtonShowTranslate;
    Button mButtonKnown;
    Button mButtonUnknown;
    TextView mWordTextView;
    TextView mTranslateTextView;

    public static Fragment newInstance(UUID deckID) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_DECK_ID, deckID);

        DeckTrainingFragment fragment = new DeckTrainingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID deckId = (UUID) getArguments().getSerializable(ARG_DECK_ID);
        mDeck = DeckLab.getInstance(getActivity()).getDeck(deckId);
        setHasOptionsMenu(true);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_deck_training, container, false);
//        mWord = new Word();
//        mWord.setEnglishWord("Hello");
//        mWord.setTranslate("Privet");
        mWord = mDeck.getWordForLearningToday();


        mWordTextView = view.findViewById(R.id.word_text_view);
        mTranslateTextView = view.findViewById(R.id.translate_text_view);
        mWordTextView.setText(mWord.getEnglishWord());
        mTranslateTextView.setText(mWord.getTranslate());

        mButtonShowTranslate = view.findViewById(R.id.show_translate_button);
        mButtonShowTranslate.setOnClickListener(this);

        mButtonKnown = view.findViewById(R.id.known_button);
        mButtonKnown.setOnClickListener(this);
        mButtonUnknown = view.findViewById(R.id.unknown_button);
        mButtonUnknown.setOnClickListener(this);

        FloatingActionButton fab = view.findViewById(R.id.new_word_active_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "New word create",
                        Toast.LENGTH_LONG).show();
                mWord = new Word();
                mDeck.setWord(mWord);
                Intent intent = WordActivity.newIntent(getActivity(), mDeck.getID(), mWord.getId());
                startActivity(intent);
            }
        });


        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_deck_training, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.change_word:
                Intent intent = WordActivity.newIntent(getActivity(), mDeck.getID(), mWord.getId());
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.show_translate_button:
                mTranslateTextView.setVisibility(View.VISIBLE);
                mButtonShowTranslate.setVisibility(View.INVISIBLE);
                mButtonUnknown.setVisibility(View.VISIBLE);
                mButtonKnown.setVisibility(View.VISIBLE);
                break;
            case R.id.known_button:
                mWord.addCorrectAnswer();
                mDeck.updateWordsForLearningToday();
                updateUI();
                break;
            case R.id.unknown_button:
                mWord.addIncorrectAnswer();
                updateUI();
                break;
        }
    }

    private void updateUI() {
        mWord = mDeck.getWordForLearningToday();
        mWordTextView.setText(mWord.getEnglishWord());
        mTranslateTextView.setText(mWord.getTranslate());
        mTranslateTextView.setVisibility(View.INVISIBLE);
        mButtonShowTranslate.setVisibility(View.VISIBLE);
        mButtonUnknown.setVisibility(View.INVISIBLE);
        mButtonKnown.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }
}
