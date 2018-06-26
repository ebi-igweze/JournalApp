package com.igweze.ebi.journalapp.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.igweze.ebi.journalapp.R;
import com.igweze.ebi.journalapp.ui.EditActivity;
import com.igweze.ebi.journalapp.ui.model.Writeup;

import org.w3c.dom.Text;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailsFragment extends Fragment {

    // writeup's details
    private Writeup writeup;

    public DetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param writeup Parameter 1.
     * @return A new instance of fragment DetailsFragment.
     */
    public static DetailsFragment newInstance(Writeup writeup) {
        DetailsFragment fragment = new DetailsFragment();
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

        if (getArguments() != null) {
            writeup.setId(args.getInt(Writeup.ITEM_ID, -1));
            writeup.setText(args.getString(Writeup.ITEM_TEXT));
            writeup.setTime(args.getString(Writeup.ITEM_TIME));
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_details, container, false);

        // get view components
        TextView time = root.findViewById(R.id.tvWriteupTime);
        TextView text = root.findViewById(R.id.tvWriteupText);

        // setup view details
        time.setText(writeup.getTime());
        text.setText(writeup.getText());
        return root;
    }
}
