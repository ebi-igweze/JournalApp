package com.igweze.ebi.journalapp.ui.fragments;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.igweze.ebi.journalapp.R;
import com.igweze.ebi.journalapp.ui.model.Writeup;
import com.igweze.ebi.journalapp.ui.model.WriteupDetailViewModel;
import com.igweze.ebi.journalapp.ui.model.WriteupDetailViewModelFactory;
import com.igweze.ebi.journalapp.utilities.InjectorUtils;

import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditFragment extends Fragment {
    public static int TYPE_ADD = 0;
    public static int TYPE_EDIT = 1;
    public static String EDIT_TYPE = "edit_type";

    private Writeup writeup;
    private EditText editText;
    private WriteupDetailViewModel mViewModel;
    private int editType;

    public EditFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param writeup Parameter
     * @return A new instance of fragment EditFragment.
     */
    public static EditFragment newInstance(Writeup writeup, int type) {
        EditFragment fragment = new EditFragment();
        Bundle args = new Bundle();

        args.putInt(EDIT_TYPE, type);

        if (type != TYPE_ADD) {
            args.putInt(Writeup.ITEM_ID, writeup.getId());
            args.putString(Writeup.ITEM_TEXT, writeup.getText());
            args.putLong(Writeup.ITEM_TIME, writeup.getTime().getTime());
            fragment.setArguments(args);
        }
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();

        if (args != null) {
            this.editType = args.getInt(EDIT_TYPE);
            writeup = new Writeup(0, null, null);

            // set writeup if editing
            if (this.editType == TYPE_EDIT) {
                writeup.setId(args.getInt(Writeup.ITEM_ID, -1));
                writeup.setText(args.getString(Writeup.ITEM_TEXT));
                writeup.setTime(new Date(args.getLong(Writeup.ITEM_TIME)));
            }
        }

        FragmentActivity activity = getActivity();
        WriteupDetailViewModelFactory factory = InjectorUtils.provideDetailViewModelFactory(activity);
        mViewModel = ViewModelProviders.of(activity, factory).get(WriteupDetailViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_edit, container, false);
        editText = root.findViewById(R.id.tvWriteupText);

        // set edit text if editing
        if (this.editType == TYPE_EDIT) editText.setText(writeup.getText());
        return root;
    }

    public void saveChanges() {
        // set text and save changes
        writeup.setText(editText.getText().toString());
        if (this.editType == TYPE_ADD) {
            writeup.setTime(new Date());
            mViewModel.addWriteup(writeup);
        } else {
            mViewModel.editWriteup(writeup);
        }
    }
}
