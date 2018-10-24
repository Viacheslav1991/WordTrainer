package com.bignerdranch.android.wordtrainer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.bignerdranch.android.wordtrainer.model.Deck;
import com.bignerdranch.android.wordtrainer.model.DeckLab;
import com.bignerdranch.android.wordtrainer.model.Word;

import java.util.UUID;



/**
 * Created by Viacheslav on 06.07.2018.
 */

public class WordFragment extends Fragment {
    private static final String ARG_DECK_ID = "deck_id";
    private static final String ARG_WORD_ID = "word_id";

    private Word mWord;
    private EditText mEnglishWordEditText;
    private EditText mTranslateEditText;
    private Button mButtonOk;
    private Button mButtonCancel;
    private Deck mDeck;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID deckId = (UUID) getArguments().getSerializable(ARG_DECK_ID);
        UUID wordId = (UUID) getArguments().getSerializable(ARG_WORD_ID);
        mDeck = DeckLab.getInstance(getActivity()).getDeck(deckId);
        mWord = mDeck.getWord(wordId);
    }

    public static WordFragment newInstance(UUID deckId, UUID wordId) {
            Bundle args = new Bundle();
            args.putSerializable(ARG_DECK_ID, deckId);
            args.putSerializable(ARG_WORD_ID, wordId);

            WordFragment fragment = new WordFragment();
            fragment.setArguments(args);
            return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_word, container, false);
        Toast.makeText(getActivity(), "Deck: " + mDeck.getNameDeck() + " Word Id: " + mWord.getId(),
                Toast.LENGTH_LONG).show();
        final String oldEnglishWord = mWord.getEnglishWord();
        final String oldTranslate = mWord.getTranslate();
        mEnglishWordEditText = v.findViewById(R.id.word_in_english_field);
        mEnglishWordEditText.setText(mWord.getEnglishWord());
        mEnglishWordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mWord.setEnglishWord(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mTranslateEditText = v.findViewById(R.id.translate_field);
        mTranslateEditText.setText(mWord.getTranslate());
        mTranslateEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mWord.setTranslate(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mButtonOk = v.findViewById(R.id.button_ok);
        mButtonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        mButtonCancel = v.findViewById(R.id.button_cancel);
        mButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWord.setEnglishWord(oldEnglishWord);
                mWord.setTranslate(oldTranslate);
                getActivity().finish();
            }
        });
        return v;
    }
//    @Override
//    public void onPause() {
//        super.onPause();
//        WordLab.getWordLab(getActivity())
//                .updateWord(mWord);
//    }
}
