package com.igweze.ebi.journalapp.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.igweze.ebi.journalapp.R;
import com.igweze.ebi.journalapp.ui.adapters.WriteupAdapter;
import com.igweze.ebi.journalapp.ui.model.WriteupListViewModel;
import com.igweze.ebi.journalapp.ui.model.WriteupListViewModelFactory;
import com.igweze.ebi.journalapp.utilities.InjectorUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final RecyclerView rv = findViewById(R.id.rvWriteupList);

        WriteupListViewModelFactory factory = InjectorUtils.provideListViewModelFactory(this);
        WriteupListViewModel mViewModel = ViewModelProviders.of(this, factory).get(WriteupListViewModel.class);

        mViewModel.getWriteups().observe(this, writeups -> {
            rv.setAdapter(new WriteupAdapter(writeups));
            rv.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL, false));
        });
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
                intent.putExtra(EditActivity.EDIT_TYPE, EditActivity.TYPE_ADD);
                startActivity(intent);
                return true;
            }
        }

        return false;
    }
}
