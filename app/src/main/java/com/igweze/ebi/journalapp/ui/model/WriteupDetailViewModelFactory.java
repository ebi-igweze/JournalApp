package com.igweze.ebi.journalapp.ui.model;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.igweze.ebi.journalapp.datastore.JournalRepository;

public class WriteupDetailViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final JournalRepository mRepository;

    public WriteupDetailViewModelFactory(JournalRepository journalRepository) {
        this.mRepository = journalRepository;
    }

    @Override
    @NonNull
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        // noinspection unchecked
        return (T) new WriteupDetailViewModel(mRepository);
    }

}
