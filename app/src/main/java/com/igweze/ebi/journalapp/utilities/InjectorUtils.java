package com.igweze.ebi.journalapp.utilities;

import android.content.Context;

import com.igweze.ebi.journalapp.datastore.JournalDatabase;
import com.igweze.ebi.journalapp.datastore.JournalRepository;
import com.igweze.ebi.journalapp.ui.model.WriteupDetailViewModelFactory;

public class InjectorUtils {

    public static JournalRepository provideRepository(Context context) {
        JournalDatabase database = JournalDatabase.getInstance(context.getApplicationContext());
//        AppExecutors executors = AppExecutors.getInstance();
//        WeatherNetworkDataSource networkDataSource = WeatherNetworkDataSource.getInstance(context.getApplicationContext(), executors);

        return JournalRepository.getInstance(database.writeupDao()); // , networkDataSource, executors);
    }
    public static WriteupDetailViewModelFactory provideDetailViewModelFactory(Context context) {
        JournalRepository repository = provideRepository(context.getApplicationContext());
        return new WriteupDetailViewModelFactory(repository);
    }
}
