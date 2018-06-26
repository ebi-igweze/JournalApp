package com.igweze.ebi.journalapp.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.igweze.ebi.journalapp.R;
import com.igweze.ebi.journalapp.ui.fragments.EditFragment;

public class EditActivity extends AppCompatActivity {
    public static String ITEM_ID = "item_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        String itemId = getIntent().getStringExtra(ITEM_ID);

        // swap out the details content
        final FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment listFragment = EditFragment.newInstance(itemId);
        fragmentManager
                .beginTransaction()
                .replace(R.id.editContent, listFragment)
                .commit();
    }
}
