package com.example.r.showtime.Services;

import com.example.r.showtime.Activity.DetailsActivity;
import com.example.r.showtime.Model.MoviesResponse;
import com.example.r.showtime.Model.TVResponse;
import com.example.r.showtime.Model.TrailerResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieService {

    @GET("movie/popular")
    Call<MoviesResponse> getPopularMovies(@Query("api_key") String apiKey);

    @GET("movie/top_rated")
    Call<MoviesResponse> getTopRatedMovies(@Query("api_key") String apiKey);

    @GET("tv/popular")
    Call<TVResponse> getPopularTV(@Query("api_key") String apiKey);

    @GET("movie/{movie_id}/videos")
    Call<TrailerResponse> getMovieVideos(@Path("movie_id") int id, @Query("api_key") String apiKey);

    @GET("tv/{tv_id}/videos")
    Call<TrailerResponse> getTvVideos(@Path("tv_id") int id, @Query("api_key") String apiKey);

}