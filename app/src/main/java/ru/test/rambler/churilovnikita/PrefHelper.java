package ru.test.rambler.churilovnikita;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by MrKosherno on 22.07.2016.
 */
public class PrefHelper {
    private final SharedPreferences sPrefs;

    public PrefHelper(Context context) {
        sPrefs = context.getSharedPreferences(Constants.SHARED_PREFS_NAME, 0);
    }

    public String getAccessToken() {
        return sPrefs.getString(Constants.ACCESS_TOKEN, "");
    }

    public void setAccessToken(String token) {
        final SharedPreferences.Editor edit = sPrefs.edit();
        edit.putString(Constants.ACCESS_TOKEN, token);
        edit.commit();
    }
    public boolean isTokenExist(){
        return sPrefs.getString(Constants.ACCESS_TOKEN, "").compareTo("")!=0;
    }
}
