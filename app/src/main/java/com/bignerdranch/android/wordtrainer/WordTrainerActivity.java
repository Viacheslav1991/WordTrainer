package com.bignerdranch.android.wordtrainer;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class WordTrainerActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new WordTrainerFragmentList();
    }


}
