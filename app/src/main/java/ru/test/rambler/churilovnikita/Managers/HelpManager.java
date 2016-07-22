package ru.test.rambler.churilovnikita.Managers;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.test.rambler.churilovnikita.Constants;
import ru.test.rambler.churilovnikita.R;


public class HelpManager {

    public static boolean isOnline(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public static void showOfflineDialog(Context context) {
        Toast.makeText(context, R.string.offline, Toast.LENGTH_SHORT).show();
    }
}
