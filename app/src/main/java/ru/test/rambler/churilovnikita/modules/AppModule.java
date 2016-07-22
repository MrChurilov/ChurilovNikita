package ru.test.rambler.churilovnikita.modules;

import android.content.Context;
import android.support.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.test.rambler.churilovnikita.PrefHelper;

@Module
public class AppModule {
    private Context appContext;

    public AppModule(@NonNull Context context) {
        appContext = context;
    }

    @Provides
    @Singleton
    PrefHelper providesPrefHelper(Context context) {
        return new PrefHelper(context);
    }

    @Provides
    @Singleton
    Context provideContext() {
        return appContext;
    }
}
