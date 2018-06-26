package com.igweze.ebi.journalapp.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.igweze.ebi.journalapp.R;
import com.igweze.ebi.journalapp.ui.DetailsActivity;
import com.igweze.ebi.journalapp.ui.adapters.WriteupAdapter;
import com.igweze.ebi.journalapp.ui.model.Writeup;

import java.util.ArrayList;
import java.util.List;


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

        RecyclerView rv = root.findViewById(R.id.rvWriteupList);
        List<Writeup> writeups = getWriteups();
        rv.setAdapter(new WriteupAdapter(writeups));
        rv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayout.VERTICAL, false));

        return root;
    }

    private void showDetails() {
        Intent intent = new Intent(getActivity(), DetailsActivity.class);
        intent.putExtra(DetailsActivity.ITEM_ID, "1");
        startActivity(intent);
    }

    private List<Writeup> getWriteups() {
        List<Writeup> writeups = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Writeup writeup = new Writeup();
            writeup.id = i;
            writeup.time = i + "-12-2018";
            writeup.text = getString(R.string.lorem_ipsum);
            writeups.add(writeup);
        }
        return writeups;
    }
}
