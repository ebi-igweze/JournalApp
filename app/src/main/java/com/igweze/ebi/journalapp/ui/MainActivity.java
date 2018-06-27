package com.igweze.ebi.journalapp.ui;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.igweze.ebi.journalapp.R;
import com.igweze.ebi.journalapp.ui.fragments.EditFragment;
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

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.newWriteup: {
                Intent intent = new Intent(this, EditActivity.class);
                intent.putExtra(EditFragment.EDIT_TYPE, EditFragment.TYPE_ADD);
                startActivity(intent);
                return true;
            }
        }

        return false;
    }
}
