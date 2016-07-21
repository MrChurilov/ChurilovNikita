package ru.test.rambler.churilovnikita.Network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class ServiceFactory {
    public static final String API_BASE_URL = "https://api.instagram.com";
    public static <T> T createRetrofitService(final Class<T> clazz) {
        OkHttpClient client = new OkHttpClient();
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        client = builder.build();

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(RecentResponce.class, new PhotoDeserializer())
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        T service = retrofit.create(clazz);

        return service;
    }
}
