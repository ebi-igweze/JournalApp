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

import java.util.Date;

public class EditActivity extends AppCompatActivity {
    private Writeup writeup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Intent intent = getIntent();
        int type = intent.getIntExtra(EditFragment.EDIT_TYPE, -1);
        writeup = new Writeup(0, null, null);

        if (type != -1 || type != EditFragment.TYPE_ADD) {
            writeup.setId(intent.getIntExtra(Writeup.ITEM_ID, -1));
            writeup.setText(intent.getStringExtra(Writeup.ITEM_TEXT));
            writeup.setTime(new Date(intent.getLongExtra(Writeup.ITEM_TIME, -1)));
        }

        // swap out the edit content
        final FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment editFragment = EditFragment.newInstance(writeup, type);
        fragmentManager
                .beginTransaction()
                .replace(R.id.editContent, editFragment)
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
