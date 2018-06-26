package com.igweze.ebi.journalapp.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.igweze.ebi.journalapp.R;
import com.igweze.ebi.journalapp.ui.model.Writeup;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditFragment extends Fragment {
    private Writeup writeup;

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
    public static EditFragment newInstance(Writeup writeup) {
        EditFragment fragment = new EditFragment();
        Bundle args = new Bundle();
        args.putInt(Writeup.ITEM_ID, writeup.getId());
        args.putString(Writeup.ITEM_TEXT, writeup.getText());
        args.putString(Writeup.ITEM_TIME, writeup.getTime());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        writeup = new Writeup();

        if (args != null) {
            writeup.setId(args.getInt(Writeup.ITEM_ID, -1));
            writeup.setText(args.getString(Writeup.ITEM_TEXT));
            writeup.setTime(args.getString(Writeup.ITEM_TIME));
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_edit, container, false);
        EditText editText = root.findViewById(R.id.tvWriteupText);
        editText.setText(writeup.getText());
        return root;
    }
}
