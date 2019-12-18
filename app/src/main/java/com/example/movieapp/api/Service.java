package com.example.movieapp.api;

import com.example.movieapp.model.MovieResponse;
import com.example.movieapp.model.TrailerResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Service {

    @GET("/3/movie/popular")
    Call<MovieResponse>getPopularMovies(@Query("api_key") String apiKey);

    @GET("/3/movie/{movie_id}/videos")
    Call<TrailerResponse> getMovieTrailer(@Path("movie_id") int  id, @Query("api_key")String apiKey);

}
