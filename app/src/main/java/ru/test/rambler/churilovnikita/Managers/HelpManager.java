package ru.test.rambler.churilovnikita.Managers;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import ru.test.rambler.churilovnikita.Constants;
import ru.test.rambler.churilovnikita.R;

/**
 * Created by MrKosherno on 21.07.2016.
 */
public class HelpManager {
    private static Context mContext;

    public static String getAccessToken() {
        final SharedPreferences sharedPreferences = mContext
                .getSharedPreferences(Constants.SHARED_PREFS_NAME, 0);
        return sharedPreferences.getString(Constants.ACCESS_TOKEN, "");
    }

    public static void setAccessToken(String token) {
        final SharedPreferences sharedPreferences = mContext
                .getSharedPreferences(Constants.SHARED_PREFS_NAME, 0);
        final SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(Constants.ACCESS_TOKEN, token);
        edit.commit();
    }

    public static void init(Context context) {
        mContext = context;
    }

    public static boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public static void showOfflineDialog() {
        Toast.makeText(mContext, R.string.Offline, Toast.LENGTH_SHORT).show();
    }
}
