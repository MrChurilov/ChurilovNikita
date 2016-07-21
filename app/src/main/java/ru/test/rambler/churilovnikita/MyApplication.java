package ru.test.rambler.churilovnikita;

import android.app.Application;

import ru.test.rambler.churilovnikita.Managers.HelpManager;

/**
 * Created by MrKosherno on 21.07.2016.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        HelpManager.init(this);
    }
}