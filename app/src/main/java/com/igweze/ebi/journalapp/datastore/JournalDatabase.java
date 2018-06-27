package com.igweze.ebi.journalapp.datastore;

import com.igweze.ebi.journalapp.ui.model.Writeup;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

@Database(entities = {Writeup.class}, version = 1)
@TypeConverters(DateConverter.class)
public abstract class JournalDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "journal";

    // for singleton instance
    private static final Object LOCKER = new Object();
    private static volatile JournalDatabase mDatabase;

    // return single instance
    public static JournalDatabase getInstance(Context ctx) {
        if (mDatabase == null) {
            // prevent access until db is created
            synchronized (LOCKER) {
                mDatabase = Room.databaseBuilder(
                        ctx.getApplicationContext(),
                        JournalDatabase.class,
                        DATABASE_NAME).build();
            }
        }

        // return instance
        return mDatabase;
    }

    public abstract WriteupDao writeupDao();
}
