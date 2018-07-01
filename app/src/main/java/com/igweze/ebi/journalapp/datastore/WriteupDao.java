package com.igweze.ebi.journalapp.datastore;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.igweze.ebi.journalapp.ui.model.Writeup;

import java.util.List;

@Dao
public interface WriteupDao {

    @Query("SELECT * FROM writeup WHERE active = 0")
    LiveData<List<Writeup>> getWriteups();

    @Query("SELECT * FROM writeup WHERE id = :id AND active = 0")
    LiveData<Writeup> getWriteupWithId(int id);

    @Query("SELECT * FROM writeup WHERE loggedOut = :loggedOut  AND active = 0")
    List<Writeup> getLocalWriteups(int loggedOut);

    @Query("SELECT * FROM writeup WHERE active = -1")
    List<Writeup> getPendingRemovals();

    @Insert
    long insertWriteup(Writeup writeup);

    @Update
    void updateWriteup(Writeup writeup);

    @Update
    void updateAll(List<Writeup> writeups);

    @Delete
    void deleteWriteup(Writeup writeup);

    @Delete
    void deleteAll(List<Writeup> writeups);
}
