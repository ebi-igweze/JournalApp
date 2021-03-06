package com.igweze.ebi.journalapp.ui.model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.igweze.ebi.journalapp.datastore.JournalRepository;

public class WriteupDetailViewModel extends ViewModel {

    private JournalRepository mRepository;

    public  WriteupDetailViewModel(JournalRepository repository) {
        this.mRepository = repository;
    }

    public LiveData<Writeup> getWriteup(int id) {
        return mRepository.getWriteupById(id);
    }

    public void addWriteup(Writeup writeup) { mRepository.addWriteup(writeup); }

    public void editWriteup(Writeup writeup) { mRepository.updateWriteup(writeup); }

    public void deleteWriteup(Writeup writeup) { mRepository.deleteWriteup(writeup); }

}
