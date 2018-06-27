package com.igweze.ebi.journalapp.ui.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
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
import com.igweze.ebi.journalapp.ui.model.WriteupListViewModel;
import com.igweze.ebi.journalapp.ui.model.WriteupListViewModelFactory;
import com.igweze.ebi.journalapp.utilities.InjectorUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link JournalListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class JournalListFragment extends Fragment {
    private WriteupListViewModel mViewModel;

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

        final RecyclerView rv = root.findViewById(R.id.rvWriteupList);

        FragmentActivity activity = getActivity();
        WriteupListViewModelFactory factory = InjectorUtils.provideListViewModelFactory(activity);
        mViewModel = ViewModelProviders.of(activity, factory).get(WriteupListViewModel.class);

        mViewModel.getWriteups().observe(this, writeups -> {
            rv.setAdapter(new WriteupAdapter(writeups));
            rv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayout.VERTICAL, false));
        });

        return root;
    }
}
