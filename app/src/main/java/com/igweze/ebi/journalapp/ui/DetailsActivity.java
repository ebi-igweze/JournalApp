package com.igweze.ebi.journalapp.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.igweze.ebi.journalapp.R;
import com.igweze.ebi.journalapp.ui.model.Writeup;
import com.igweze.ebi.journalapp.ui.model.WriteupDetailViewModel;
import com.igweze.ebi.journalapp.ui.model.WriteupDetailViewModelFactory;
import com.igweze.ebi.journalapp.utilities.InjectorUtils;

public class DetailsActivity extends AppCompatActivity {
    private Writeup mWriteup;
    private WriteupDetailViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        // get toolbar
        ActionBar actionBar = getSupportActionBar();

        // display back button
        if (actionBar != null) actionBar.setDisplayHomeAsUpEnabled(true);

        mWriteup = new Writeup(0, null, null);
        Intent intent = getIntent();
        int writeupId = intent.getIntExtra(Writeup.ITEM_ID, -1);

        // get view components
        final TextView time = findViewById(R.id.tvWriteupTime);
        final TextView text = findViewById(R.id.tvWriteupText);

        // get details view model
        WriteupDetailViewModelFactory factory = InjectorUtils.provideDetailViewModelFactory(this);
        mViewModel = ViewModelProviders.of(this, factory).get(WriteupDetailViewModel.class);

        // setup observer
        mViewModel.getWriteup(writeupId).observe(this, writeup -> {
            if (writeup != null) {
                // setup view details
                this.mWriteup = writeup;
                time.setText(Writeup.dateFormat.format(writeup.getTime()));
                text.setText(writeup.getText());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mWriteup == null) {
            Toast.makeText(this, "Still loading...", Toast.LENGTH_LONG).show();
            return false;
        }

        switch (item.getItemId()) {
            case R.id.delete:{
                showAlert();
                return true;
            }
            case R.id.edit: {
                gotoEdit();
                return true;
            }
        }

        return false;
    }

    private void gotoEdit() {
        // edit item in edit activity
        Intent intent = new Intent(this, EditActivity.class);
        // set type to edit action
        intent.putExtra(EditActivity.EDIT_TYPE, EditActivity.TYPE_EDIT);
        // add write up info
        intent.putExtra(Writeup.ITEM_ID, mWriteup.getId());
        intent.putExtra(Writeup.ITEM_TEXT, mWriteup.getText());
        intent.putExtra(Writeup.ITEM_TIME, mWriteup.getTime().getTime());
        startActivity(intent);
    }

    private void showAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder
                .setTitle(R.string.title_action_delete)
                .setMessage(R.string.message_warning_delete)
                .setPositiveButton(R.string.action_accept, (v, i) -> deleteWriteup())
                .setNegativeButton(R.string.action_cancel, (v, i) -> {})
                .create().show();
    }

    private void deleteWriteup() {
        mViewModel.deleteWriteup(mWriteup);
        Toast.makeText(this, "Delete was successful", Toast.LENGTH_LONG).show();
        startActivity(new Intent(this, MainActivity.class));
    }
}
