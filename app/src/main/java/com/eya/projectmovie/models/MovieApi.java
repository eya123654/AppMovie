package com.eya.projectmovie.models;

import android.graphics.Movie;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieApi {
    @GET("tv/popular?api_key=e66f4ebe720469069d93e9b821877a3b")
    Call <Moviepage> getMovies(@Query("api_key") String key);


}
