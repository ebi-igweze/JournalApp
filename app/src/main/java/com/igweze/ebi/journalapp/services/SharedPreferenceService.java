package com.igweze.ebi.journalapp.services;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.gson.Gson;
import com.igweze.ebi.journalapp.ui.model.UserInfo;


public class SharedPreferenceService {
    private Context context;
    private String KEY_USER_INFO = "user_info_key";

    public SharedPreferenceService(Context context) {
        this.context = context;
    }

    public void setUser(@NonNull UserInfo user) {
        Gson gson = new Gson();
        SharedPreferences sp = context.getSharedPreferences("user-pref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        String userInfo = gson.toJson(user);
        editor.putString(KEY_USER_INFO, userInfo);
        editor.apply();
    }

    @Nullable
    public UserInfo getUserInfo() {
        Gson gson = new Gson();
        SharedPreferences sp = context.getSharedPreferences("user-pref", Context.MODE_PRIVATE);
        String userInfo =  sp.getString(KEY_USER_INFO, null);
        return userInfo == null ? null : gson.fromJson(userInfo, UserInfo.class);
    }

    public void removeUser() {
        SharedPreferences sp = context.getSharedPreferences("user-pref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(KEY_USER_INFO);
        editor.apply();
    }
}
