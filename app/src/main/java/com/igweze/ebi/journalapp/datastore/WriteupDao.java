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

    @Query("SELECT * FROM writeup")
    LiveData<List<Writeup>> getWriteups();

    @Query("SELECT * FROM writeup WHERE id = :id")
    LiveData<Writeup> getWriteupWithId(int id);

    @Insert
    void insertWriteup(Writeup writeup);

    @Update
    void updateWriteup(Writeup writeup);

    @Delete
    void deleteWriteup(Writeup writeup);
}
