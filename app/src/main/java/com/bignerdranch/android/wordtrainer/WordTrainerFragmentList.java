package com.bignerdranch.android.wordtrainer;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bignerdranch.android.wordtrainer.model.Deck;
import com.bignerdranch.android.wordtrainer.model.DeckLab;

import java.util.List;

public class WordTrainerFragmentList extends Fragment {

    private static final int REQUEST_DECK_NAME = 0;
    private static final String DIALOG_DECK_NAME = "Dialog deck name";

    private RecyclerView mDeckRecyclerView;
    private DeckAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.fragment_word_trainer_list);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_word_trainer_list, container, false);
        FloatingActionButton fab = view.findViewById(R.id.new_word_active_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = getFragmentManager();
                NewDeckDialogFragment dialog = new NewDeckDialogFragment();
                dialog.setTargetFragment(WordTrainerFragmentList.this, REQUEST_DECK_NAME);
                dialog.show(manager, DIALOG_DECK_NAME);
            }
        });

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        mDeckRecyclerView = view.findViewById(R.id.decks_recycler_view);
        mDeckRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_word_trainer_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_DECK_NAME) {

            Toast.makeText(getActivity(), "New deck name is: "
                            + data.getSerializableExtra(NewDeckDialogFragment.EXTRA_DECK_NAME),
                    Toast.LENGTH_LONG).show();
            Deck deck = new Deck((String) data.getSerializableExtra(NewDeckDialogFragment.EXTRA_DECK_NAME));
            DeckLab.getInstance(getActivity()).addDeck(deck);
            updateUI();
        }
    }

    public class DeckHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Deck mDeck;

        private TextView mDeckName;
        private TextView mNumberWordsInDeck;
        private TextView mNumberWordsForLearningToday;

        public DeckHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_deck, parent, false));
            itemView.setOnClickListener(this);

            mDeckName = itemView.findViewById(R.id.deck_name_text_view);
            mNumberWordsInDeck = itemView.findViewById(R.id.number_words_text_view);
            mNumberWordsForLearningToday = itemView.findViewById(R.id.number_words_for_learning_today_text_view);

        }

        @Override
        public void onClick(View v) {
            Intent intent = DeckTrainingActivity.newIntent(getActivity(), mDeck.getID());
            startActivity(intent);
        }

        @SuppressLint("SetTextI18n")
        public void bind(Deck deck) {
            mDeck = deck;
            mDeckName.setText(deck.getNameDeck());
            mNumberWordsInDeck.setText(Integer.toString(deck.getWordsInDeck().size()));
            mNumberWordsForLearningToday.setText(Integer.toString(deck.getWordsForLearningToday().size()));
        }
    }

    public class DeckAdapter extends RecyclerView.Adapter<DeckHolder> {
        private List<Deck> mDecks;

        public DeckAdapter(List<Deck> decks) {
            mDecks = decks;
        }

        @NonNull
        @Override
        public DeckHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new DeckHolder(layoutInflater, viewGroup);
        }

        @Override
        public void onBindViewHolder(@NonNull DeckHolder deckHolder, int i) {
            Deck deck = mDecks.get(i);
            deckHolder.bind(deck);
        }

        @Override
        public int getItemCount() {
            return mDecks.size();
        }

        public void setDecks(List<Deck> decks) {
            mDecks = decks;
        }
    }

    public void updateUI() {
        DeckLab deckLab = DeckLab.getInstance(getActivity());
        List<Deck> decks = deckLab.getDecks();

        if (mAdapter == null) {
            mAdapter = new DeckAdapter(decks);
            mDeckRecyclerView.setAdapter(mAdapter);
        } else {

            mAdapter.setDecks(decks);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }
}
