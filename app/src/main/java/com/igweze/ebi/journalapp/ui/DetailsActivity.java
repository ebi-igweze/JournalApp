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
import com.igweze.ebi.journalapp.ui.fragments.JournalListFragment;
import com.igweze.ebi.journalapp.ui.model.Writeup;

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

        writeup = new Writeup();
        Intent intent = getIntent();
        writeup.setId(intent.getIntExtra(Writeup.ITEM_ID, -1));
        writeup.setText(intent.getStringExtra(Writeup.ITEM_TEXT));
        writeup.setTime(intent.getStringExtra(Writeup.ITEM_TIME));

        // swap out the details content
        final FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment listFragment = DetailsFragment.newInstance(writeup);
        fragmentManager
                .beginTransaction()
                .replace(R.id.detailsContent, listFragment)
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
                intent.putExtra(Writeup.ITEM_ID, writeup.getId());
                intent.putExtra(Writeup.ITEM_TEXT, writeup.getText());
                intent.putExtra(Writeup.ITEM_TIME, writeup.getTime());
                startActivity(intent);
                return true;
            }
        }

        return false;
    }
}
