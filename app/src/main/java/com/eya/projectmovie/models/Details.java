package com.eya.projectmovie.models;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Details {
    @GET("tv/{tv_id}")
    Call<DetailPage> getdetails(@Path("tv_id") int id, @Query("api_key") String key);

}
