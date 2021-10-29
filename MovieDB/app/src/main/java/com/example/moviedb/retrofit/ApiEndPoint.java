package com.example.moviedb.retrofit;

import com.example.moviedb.model.Genre;
import com.example.moviedb.model.Movies;
import com.example.moviedb.model.NowPlaying;
import com.example.moviedb.model.Upcoming;
import com.example.moviedb.model.Videos;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiEndPoint {

    @GET("movie/{movie_id}")
    Call<Movies> getMovieById(
            @Path("movie_id") String movie_id,
            @Query("api_key") String apiKey
    );

    @GET("movie/now_playing")
    Call<NowPlaying> getNowPlaying(
            @Query("api_key") String apiKey,
            @Query("page") String page
    );

    @GET("genre/movie/list")
    Call<Genre> getGenreById(
            @Query("genre_id") String genre_id,
            @Query("api_key") String apiKey
    );

    @GET("movie/upcoming")
    Call<Upcoming> getUpcoming(
            @Query("api_key") String apiKey,
            @Query("page") String page
    );

    @GET("movie/{movie_id}/videos")
    Call<Videos> getVideoByMovieId(
            @Path("movie_id") String movie_id,
            @Query("api_key") String apiKey
    );
}
