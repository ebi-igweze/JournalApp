package com.igweze.ebi.journalapp.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.igweze.ebi.journalapp.R;
import com.igweze.ebi.journalapp.ui.fragments.JournalListFragment;

public class MainActivity extends AppCompatActivity {

    private boolean isLoggedIn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // swap out the main content
        final FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment listFragment = JournalListFragment.newInstance();
        fragmentManager
                .beginTransaction()
                .replace(R.id.mainContent, listFragment)
                .commit();
    }
}
