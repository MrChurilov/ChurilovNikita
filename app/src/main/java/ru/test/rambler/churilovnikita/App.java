package ru.test.rambler.churilovnikita;

import android.app.Application;

import ru.test.rambler.churilovnikita.Managers.HelpManager;
import ru.test.rambler.churilovnikita.modules.AppModule;

public class App extends Application {
    private static AppComponent component;

    public static AppComponent getComponent() {
        return component;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        component = buildComponent();
    }

    private AppComponent buildComponent() {
        return DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }
}