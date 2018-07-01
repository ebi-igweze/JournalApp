package com.igweze.ebi.journalapp;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.igweze.ebi.journalapp.datastore.JournalDatabase;
import com.igweze.ebi.journalapp.datastore.WriteupDao;
import com.igweze.ebi.journalapp.ui.model.Writeup;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;


@RunWith(AndroidJUnit4.class)
public class DatabaseInstrumentedTest {

    @Rule
    public InstantTaskExecutorRule executorRule = new InstantTaskExecutorRule();

    private JournalDatabase mJournalDatabase;
    private WriteupDao mWriteupDao;


    @Before
    public void createDao() {
        Context testContext = InstrumentationRegistry.getTargetContext();
        mJournalDatabase = Room
                .inMemoryDatabaseBuilder(testContext, JournalDatabase.class)
                .allowMainThreadQueries()
                .build();
        mWriteupDao = mJournalDatabase.writeupDao();
    }

    @After
    public void removeDao() {
        mJournalDatabase.close();
    }

    @Test
    public void createWriteUp() throws InterruptedException {
        // Arrange
        Writeup actualWriteup = getSampleWriteup();

        // Act
        mWriteupDao.insertWriteup(actualWriteup);
        LiveData<Writeup> liveData = mWriteupDao.getWriteupWithId(actualWriteup.getId());
        Writeup testWriteup = LiveDataUtils.getValue(liveData);

        // Assert
        assertThat(testWriteup.getId(), equalTo(actualWriteup.getId()));
        assertThat(testWriteup.getText(), equalTo(actualWriteup.getText()));
        assertThat(testWriteup.getTime().getTime(), equalTo(actualWriteup.getTime().getTime()));
    }

    @Test
    public void deleteWriteup() throws InterruptedException {
        // Arrange
        Writeup actualWriteup = getSampleWriteup();

        // Act
        mWriteupDao.insertWriteup(actualWriteup);
        mWriteupDao.deleteWriteup(actualWriteup);
        LiveData<Writeup> liveData = mWriteupDao.getWriteupWithId(actualWriteup.getId());
        Writeup testWriteup = LiveDataUtils.getValue(liveData);

        // Assert
        assertThat(null, equalTo(testWriteup));
    }

    @Test
    public void editWriteup() throws InterruptedException {
        // Arrange
        Writeup actualWriteup = getSampleWriteup();

        // Act
        mWriteupDao.insertWriteup(actualWriteup); // add something in db
        String newText = actualWriteup.getText().concat(" an appendage to current.");
        actualWriteup.setText(newText);
        mWriteupDao.updateWriteup(actualWriteup); // modify added writeup
        LiveData<Writeup> liveData = mWriteupDao.getWriteupWithId(actualWriteup.getId());
        Writeup testWriteup = LiveDataUtils.getValue(liveData);

        // Assert
        assertThat(newText, equalTo(testWriteup.getText()));
    }

    public Writeup getSampleWriteup() {
        final int sampleId = 7;
        final Date currentDate = new Date();
        final String sampleText = "Some sample test text";
        return new Writeup(sampleId, currentDate, sampleText, Writeup.SINGED_IN, Writeup.ACTIVE);
    }
}
