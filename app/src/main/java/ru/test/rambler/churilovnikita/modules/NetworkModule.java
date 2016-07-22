package ru.test.rambler.churilovnikita.modules;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import ru.test.rambler.churilovnikita.network.ServiceFactory;

@Module
public class NetworkModule {
    @Provides
    @Singleton
    ServiceFactory providesServiceFactory(OkHttpClient client) {
        return new ServiceFactory(client);
    }

    @Provides
    @Singleton
    OkHttpClient providesOkHttpClient() {
        OkHttpClient client = new OkHttpClient();
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        client = builder.build();
        return client;
    }
}
