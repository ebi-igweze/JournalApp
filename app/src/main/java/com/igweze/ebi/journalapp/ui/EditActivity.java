package com.igweze.ebi.journalapp.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.igweze.ebi.journalapp.R;
import com.igweze.ebi.journalapp.ui.fragments.EditFragment;
import com.igweze.ebi.journalapp.ui.model.Writeup;

public class EditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        String itemId = getIntent().getStringExtra(Writeup.ITEM_ID);

        // swap out the details content
        final FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment listFragment = EditFragment.newInstance(itemId);
        fragmentManager
                .beginTransaction()
                .replace(R.id.editContent, listFragment)
                .commit();
    }
}
