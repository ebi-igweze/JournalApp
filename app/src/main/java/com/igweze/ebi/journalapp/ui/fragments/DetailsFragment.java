package com.igweze.ebi.journalapp.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.igweze.ebi.journalapp.R;
import com.igweze.ebi.journalapp.ui.EditActivity;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailsFragment extends Fragment {
    private static final String ITEM_ID = "ITEM_ID";

    // journal item id
    private String itemId;

    public DetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param itemId Parameter 1.
     * @return A new instance of fragment DetailsFragment.
     */
    public static DetailsFragment newInstance(String itemId) {
        DetailsFragment fragment = new DetailsFragment();
        Bundle args = new Bundle();
        args.putString(ITEM_ID, itemId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            itemId = getArguments().getString(ITEM_ID);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_details, container, false);
        Button button = root.findViewById(R.id.showEdit);
        button.setOnClickListener(v -> showEdit());
        return root;
    }

    public void showEdit() {
        Intent intent = new Intent(getActivity(), EditActivity.class);
        intent.putExtra(EditActivity.ITEM_ID, itemId);
        startActivity(intent);
    }
}
