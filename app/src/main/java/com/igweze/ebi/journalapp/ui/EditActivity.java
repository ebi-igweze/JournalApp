package com.igweze.ebi.journalapp.ui;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.igweze.ebi.journalapp.R;
import com.igweze.ebi.journalapp.ui.fragments.EditFragment;
import com.igweze.ebi.journalapp.ui.model.Writeup;

public class EditActivity extends AppCompatActivity {
    private Writeup writeup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        writeup = new Writeup();
        Intent intent = getIntent();
        writeup.setId(intent.getIntExtra(Writeup.ITEM_ID, -1));
        writeup.setText(intent.getStringExtra(Writeup.ITEM_TEXT));
        writeup.setTime(intent.getStringExtra(Writeup.ITEM_TIME));

        // swap out the details content
        final FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment listFragment = EditFragment.newInstance(writeup);
        fragmentManager
                .beginTransaction()
                .replace(R.id.editContent, listFragment)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.save: {
                // save to storage and go back to edit
                Toast.makeText(this, "Edit saved successfully", Toast.LENGTH_LONG).show();
                return true;
            }
        }
        return false;
    }
}
