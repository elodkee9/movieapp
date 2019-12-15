package com.example.movieapp.api;

import com.example.movieapp.model.MovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Service {

    @GET("/3/movie/popular")
    Call<MovieResponse>getPopularMovies(@Query("api_key") String apiKey);

    @GET("movie/top_rated")
    Call<MovieResponse>getTopRatedMovies(@Query("api_key") String apiKey);

}
