package com.igweze.ebi.journalapp.ui;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.igweze.ebi.journalapp.R;
import com.igweze.ebi.journalapp.ui.fragments.DetailsFragment;
import com.igweze.ebi.journalapp.ui.fragments.EditFragment;
import com.igweze.ebi.journalapp.ui.fragments.JournalListFragment;
import com.igweze.ebi.journalapp.ui.model.Writeup;

import java.util.Date;

public class DetailsActivity extends AppCompatActivity {
    private Writeup writeup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        // get toolbar
        ActionBar actionBar = getSupportActionBar();

        // display back button
        if (actionBar != null) actionBar.setDisplayHomeAsUpEnabled(true);

        writeup = new Writeup(0, null, null);
        Intent intent = getIntent();
        writeup.setId(intent.getIntExtra(Writeup.ITEM_ID, -1));
        writeup.setText(intent.getStringExtra(Writeup.ITEM_TEXT));
        writeup.setTime(new Date(intent.getLongExtra(Writeup.ITEM_TIME, -1)));

        // swap out the details content
        final FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment detailsFragment = DetailsFragment.newInstance(writeup);
        fragmentManager
                .beginTransaction()
                .replace(R.id.detailsContent, detailsFragment)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete:{
                // delete item
                Toast.makeText(this, "Delete was successful", Toast.LENGTH_LONG).show();
                return true;
            }
            case R.id.edit: {
                // edit item in edit activity
                Intent intent = new Intent(this, EditActivity.class);
                // set type to edit action
                intent.putExtra(EditFragment.EDIT_TYPE, EditFragment.TYPE_EDIT);
                // add write up info
                intent.putExtra(Writeup.ITEM_ID, writeup.getId());
                intent.putExtra(Writeup.ITEM_TEXT, writeup.getText());
                intent.putExtra(Writeup.ITEM_TIME, writeup.getTime().getTime());
                startActivity(intent);
                return true;
            }
        }

        return false;
    }
}
