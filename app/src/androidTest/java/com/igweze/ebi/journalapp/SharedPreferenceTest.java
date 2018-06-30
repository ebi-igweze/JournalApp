package com.igweze.ebi.journalapp;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.igweze.ebi.journalapp.services.SharedPreferenceService;
import com.igweze.ebi.journalapp.ui.model.UserInfo;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.test.MoreAsserts.assertNotEqual;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

@RunWith(AndroidJUnit4.class)
public class SharedPreferenceTest {

    private SharedPreferenceService mSharedPreferenceService;

    @Before
    public void createService() {
        Context testContext = InstrumentationRegistry.getTargetContext();
        mSharedPreferenceService = new SharedPreferenceService(testContext);
    }

    @Test
    public void addUserInfo() {
        UserInfo actualUserInfo = new UserInfo("firstName", "lastName", "email");

        mSharedPreferenceService.setUser(actualUserInfo);
        UserInfo testUserInfo = mSharedPreferenceService.getUserInfo();

        // Assert
        assertNotEqual(null, testUserInfo);
        assertThat(actualUserInfo.email, equalTo(testUserInfo.email));
        assertThat(actualUserInfo.firstName, equalTo(testUserInfo.firstName));
        assertThat(actualUserInfo.lastName, equalTo(testUserInfo.lastName));

    }

    @Test
    public void removeUserInfo() {
        UserInfo actualUserInfo = new UserInfo("firstName", "lastName", "email");

        mSharedPreferenceService.setUser(actualUserInfo);
        mSharedPreferenceService.removeUser();
        UserInfo testUserInfo = mSharedPreferenceService.getUserInfo();

        // Assert
        assertThat(null, equalTo(testUserInfo));

    }
}
