package com.example.movieapp.retrofit;

import com.example.movieapp.model.MovieContainer;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieDB {
    @GET("3/movie/{filter_type}")
    Call<MovieContainer> getMovies(@Path("filter_type") String filter_type, @Query("api_key") String api_key, @Query("language") String language, @Query("page") int page);
}
