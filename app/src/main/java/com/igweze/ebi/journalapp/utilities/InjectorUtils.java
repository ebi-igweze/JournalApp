package com.igweze.ebi.journalapp.utilities;

import android.content.Context;

import com.igweze.ebi.journalapp.datastore.JournalDatabase;
import com.igweze.ebi.journalapp.datastore.JournalRepository;
import com.igweze.ebi.journalapp.ui.model.WriteupDetailViewModelFactory;
import com.igweze.ebi.journalapp.ui.model.WriteupListViewModelFactory;

public class InjectorUtils {

    private static JournalRepository provideRepository(Context context) {
        JournalDatabase database = JournalDatabase.getInstance(context.getApplicationContext());
        AppExecutors executors = AppExecutors.getInstance();

        return JournalRepository.getInstance(database.writeupDao(), executors);
    }

    public static WriteupDetailViewModelFactory provideDetailViewModelFactory(Context context) {
        JournalRepository repository = provideRepository(context.getApplicationContext());
        return new WriteupDetailViewModelFactory(repository);
    }

    public static WriteupListViewModelFactory provideListViewModelFactory(Context context) {
        JournalRepository repository = provideRepository(context.getApplicationContext());
        return new WriteupListViewModelFactory(repository);
    }
}
