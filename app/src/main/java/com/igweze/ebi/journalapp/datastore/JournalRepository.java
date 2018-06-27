package com.igweze.ebi.journalapp.datastore;

import android.arch.lifecycle.LiveData;
import android.util.Log;

import com.igweze.ebi.journalapp.ui.model.Writeup;
import com.igweze.ebi.journalapp.utilities.AppExecutors;

import java.util.List;

public class JournalRepository {
    private static String TAG = JournalRepository.class.getSimpleName();


    // for singleton instance
    private static final Object LOCKER = new Object();
    private static volatile JournalRepository mRepository;
    private final WriteupDao mWriteupDao;
    private final AppExecutors mExecutors;

    private JournalRepository(WriteupDao writeupDao, AppExecutors executors) {
        this.mWriteupDao = writeupDao;
        this.mExecutors = executors;
    }

    // return single instance
    public synchronized static JournalRepository getInstance(WriteupDao writeupDao, AppExecutors executors) {
        if (mRepository == null) {
            // prevent access until instance is created
            synchronized (LOCKER) {
                mRepository = new JournalRepository(writeupDao, executors);
                Log.d(TAG, "Created new repository");
            }
        }

        // return instance
        return mRepository;
    }


    public LiveData<Writeup> getWriteupById(int id) {
        return mWriteupDao.getWriteupWithId(id);
    }

    public LiveData<List<Writeup>> getWriteups() {
        return mWriteupDao.getWriteups();
    }

    public void addWriteup(Writeup writeup) {
        mExecutors.diskIO().execute(() -> mWriteupDao.insertWriteup(writeup));
    }

    public void updateWriteup(Writeup writeup) {
        mExecutors.diskIO().execute(() -> mWriteupDao.updateWriteup(writeup));
    }

    public void deleteWriteup(Writeup writeup) {
        mExecutors.diskIO().execute(() ->  mWriteupDao.deleteWriteup(writeup));
    }
}
