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
import com.igweze.ebi.journalapp.ui.DetailsActivity;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link JournalListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class JournalListFragment extends Fragment {

    public JournalListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of this fragment.
     * @return A new instance of fragment JournalListFragment.
     */
    public static JournalListFragment newInstance() {
        JournalListFragment fragment = new JournalListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_journal_list, container, false);
        Button button = root.findViewById(R.id.showDetails);
        button.setOnClickListener(v -> showDetails());
        return root;
    }

    private void showDetails() {
        Intent intent = new Intent(getActivity(), DetailsActivity.class);
        intent.putExtra(DetailsActivity.ITEM_ID, "1");
        startActivity(intent);
    }
}
