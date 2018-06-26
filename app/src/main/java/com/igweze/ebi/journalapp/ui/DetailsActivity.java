package com.igweze.ebi.journalapp.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.igweze.ebi.journalapp.R;
import com.igweze.ebi.journalapp.ui.fragments.DetailsFragment;
import com.igweze.ebi.journalapp.ui.fragments.JournalListFragment;

public class DetailsActivity extends AppCompatActivity {
    public static final String ITEM_ID = "ITEM_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        String itemId = getIntent().getStringExtra(ITEM_ID);

        // swap out the details content
        final FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment listFragment = DetailsFragment.newInstance(itemId);
        fragmentManager
                .beginTransaction()
                .replace(R.id.detailsContent, listFragment)
                .commit();
    }
}
