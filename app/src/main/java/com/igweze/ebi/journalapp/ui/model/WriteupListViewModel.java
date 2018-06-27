package com.igweze.ebi.journalapp.ui.model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.igweze.ebi.journalapp.datastore.JournalRepository;

import java.util.List;

public class WriteupListViewModel extends ViewModel {

    private JournalRepository mRepository;

    public WriteupListViewModel(JournalRepository repository) {
        mRepository = repository;
    }

    public LiveData<List<Writeup>> getWriteups() {
        return mRepository.getWriteups();
    }
}
