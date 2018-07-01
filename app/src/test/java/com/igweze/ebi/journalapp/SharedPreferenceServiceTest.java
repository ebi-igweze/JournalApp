package com.igweze.ebi.journalapp;

import android.content.Context;
import android.content.SharedPreferences;

import com.igweze.ebi.journalapp.services.SharedPreferenceService;
import com.igweze.ebi.journalapp.ui.model.UserInfo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static android.test.MoreAsserts.assertNotEqual;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SharedPreferenceServiceTest {
    @Mock
    private Context context;

    @Mock
    private SharedPreferences sharedPreferences;

    @Mock
    private SharedPreferences.Editor sharedPreferenceEditor;

    private SharedPreferenceService mSharedPreferenceService;

    private void setupSharedPreferencesWithValue() {
        when(sharedPreferences.edit()).thenReturn(sharedPreferenceEditor);
        String SAMPLE_USER = "{\"firstName\":\"Ebi\", \"lastName\":\"Igweze\", \"email\": \"ebiigweze@email.com\"}";
        when(sharedPreferences.getString(anyString(), any())).thenReturn(SAMPLE_USER);
        when(context.getSharedPreferences(anyString(), anyInt())).thenReturn(sharedPreferences);
        mSharedPreferenceService = new SharedPreferenceService(context);
    }

    private void setupSharedpreferencesWihtoutValue() {
        when(sharedPreferences.edit()).thenReturn(sharedPreferenceEditor);
        when(sharedPreferences.getString(anyString(), any())).thenReturn(null);
        when(context.getSharedPreferences(anyString(), anyInt())).thenReturn(sharedPreferences);
        mSharedPreferenceService = new SharedPreferenceService(context);
    }

    @Test
    public void addUserInfo() {
        setupSharedPreferencesWithValue();
        UserInfo actualUserInfo = new UserInfo("Ebi", "Igweze", "ebiigweze@email.com");

        mSharedPreferenceService.setUser(actualUserInfo);
        UserInfo testUserInfo = mSharedPreferenceService.getUserInfo();

        // Assert
        assert testUserInfo != null;
        assertThat(actualUserInfo.email, equalTo(testUserInfo.email));
        assertThat(actualUserInfo.firstName, equalTo(testUserInfo.firstName));
        assertThat(actualUserInfo.lastName, equalTo(testUserInfo.lastName));

    }

    @Test
    public void removeUserInfo() {
        setupSharedpreferencesWihtoutValue();

        mSharedPreferenceService.removeUser();
        UserInfo testUserInfo = mSharedPreferenceService.getUserInfo();

        // Assert
        assertThat(null, equalTo(testUserInfo));

    }
}
