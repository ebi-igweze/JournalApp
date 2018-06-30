package com.igweze.ebi.journalapp.ui.model;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.igweze.ebi.journalapp.datastore.JournalRepository;

public class WriteupListViewModelFactory extends  ViewModelProvider.NewInstanceFactory {

    private JournalRepository mRepository;

    public WriteupListViewModelFactory(JournalRepository repository) {
        mRepository = repository;
    }

    @NonNull
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        // noinspection unchecked
        return (T) new WriteupListViewModel(mRepository);
    }
}
