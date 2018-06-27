package com.igweze.ebi.journalapp.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.igweze.ebi.journalapp.R;
import com.igweze.ebi.journalapp.ui.model.Writeup;
import com.igweze.ebi.journalapp.ui.model.WriteupDetailViewModel;
import com.igweze.ebi.journalapp.ui.model.WriteupDetailViewModelFactory;
import com.igweze.ebi.journalapp.utilities.InjectorUtils;

import java.util.Date;

public class EditActivity extends AppCompatActivity {
    public static int TYPE_ADD = 0;
    public static int TYPE_EDIT = 1;
    public static String EDIT_TYPE = "edit_type";

    private Writeup writeup;
    private EditText editText;
    private WriteupDetailViewModel mViewModel;
    private int editType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Intent intent = getIntent();
        editType = intent.getIntExtra(EDIT_TYPE, -1);
        writeup = new Writeup(0, null, null);
        editText = findViewById(R.id.tvWriteupText);

        if (editType != -1 && editType == TYPE_EDIT) {
            writeup.setId(intent.getIntExtra(Writeup.ITEM_ID, -1));
            writeup.setText(intent.getStringExtra(Writeup.ITEM_TEXT));
            writeup.setTime(new Date(intent.getLongExtra(Writeup.ITEM_TIME, -1)));
            // set the content of editText
            editText.setText(writeup.getText());
        }

        WriteupDetailViewModelFactory factory = InjectorUtils.provideDetailViewModelFactory(this);
        mViewModel = ViewModelProviders.of(this, factory).get(WriteupDetailViewModel.class);
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
                this.saveChanges();
                return true;
            }
        }
        return false;
    }


    private void saveChanges() {
        String message;
        // set text and save changes
        writeup.setText(editText.getText().toString());
        if (this.editType == TYPE_ADD) {
            writeup.setTime(new Date());
            mViewModel.addWriteup(writeup);
            message = "New writeup saved successfully";
            gotoList();
        } else if(this.editType == TYPE_EDIT) {
            mViewModel.editWriteup(writeup);
            message = "Edit saved successfully";
            gotoDetails();
        } else {
            message = "Unable to perform action";
            gotoDetails();
        }
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    private void gotoList() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void gotoDetails() {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra(Writeup.ITEM_ID, writeup.getId());
        startActivity(intent);
    }
}
