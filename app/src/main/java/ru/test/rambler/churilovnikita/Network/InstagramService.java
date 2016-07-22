package ru.test.rambler.churilovnikita.network;

import retrofit2.http.GET;
import retrofit2.http.Path;

import retrofit2.http.Query;
import rx.Observable;


public interface InstagramService {
   // String SERVICE_ENDPOINT = "https://api.instagram.com";

    @GET("/v1/users/self/media/recent")
    Observable<RecentResponce> getRecentPhotos(@Query("access_token") String token);

    @GET("/v1/tags/{tag-name}/media/recent")
    Observable<RecentResponce> getRecentPhotosByTag(@Path("tag-name") String tag_name, @Query("access_token") String token);
}
