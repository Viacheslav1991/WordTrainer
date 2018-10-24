package com.bignerdranch.android.wordtrainer;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.widget.EditText;

public class NewDeckDialogFragment extends DialogFragment {
    public static final String EXTRA_DECK_NAME = "com.bignerdranch.android.wordtrainer.newdeckname";
    EditText mEditText;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        mEditText = new EditText(getActivity());
        mEditText.setText(getString(R.string.unnamed));
        return new AlertDialog.Builder(getActivity())
                .setTitle("Enter deck name")
                .setView(mEditText)
                .setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                sendResult(WordTrainerActivity.RESULT_OK, String.valueOf(mEditText.getText()));
                            }
                        })
                .create();
    }
    private void sendResult(int resultCode, String deckName) {
        if (getTargetFragment() == null) {
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(EXTRA_DECK_NAME, deckName);
        getTargetFragment()
                .onActivityResult(getTargetRequestCode(), resultCode, intent);
    }

}
