package com.example.moviedb.repositories;

import androidx.lifecycle.MutableLiveData;

import com.example.moviedb.helper.Const;
import com.example.moviedb.model.Genre;
import com.example.moviedb.model.Movies;
import com.example.moviedb.model.NowPlaying;
import com.example.moviedb.retrofit.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviesRepository {

    private static MoviesRepository moviesRepository;

    public MoviesRepository(){

    }

    public static MoviesRepository getInstance(){
        if(moviesRepository == null){
            moviesRepository = new MoviesRepository();
        }

        return moviesRepository;
    }

    public MutableLiveData<Movies> getMoviesData(String movieId){
        final MutableLiveData<Movies> resultMovie = new MutableLiveData<>();

        ApiService.endPoint().getMovieById(movieId, Const.API_KEY).enqueue(new Callback<Movies>() {
            @Override
            public void onResponse(Call<Movies> call, Response<Movies> response) {
                resultMovie.setValue(response.body());
            }

            @Override
            public void onFailure(Call<Movies> call, Throwable t) {

            }
        });

        return resultMovie;
    }

    public MutableLiveData<NowPlaying> getNowPlaying(){
        final MutableLiveData<NowPlaying> resultNowPlaying = new MutableLiveData<>();

        ApiService.endPoint().getNowPlaying(Const.API_KEY).enqueue(new Callback<NowPlaying>() {
            @Override
            public void onResponse(Call<NowPlaying> call, Response<NowPlaying> response) {
                resultNowPlaying.setValue(response.body());
            }

            @Override
            public void onFailure(Call<NowPlaying> call, Throwable t) {

            }
        });


        return resultNowPlaying;
    }

    public MutableLiveData<Genre> getGenresData(String genreId){
        final MutableLiveData<Genre> resultGenre = new MutableLiveData<>();

        ApiService.endPoint().getGenreById(genreId, Const.API_KEY).enqueue(new Callback<Genre>() {
            @Override
            public void onResponse(Call<Genre> call, Response<Genre> response) {
                resultGenre.setValue(response.body());
            }

            @Override
            public void onFailure(Call<Genre> call, Throwable t) {

            }
        });

        return resultGenre;
    }


}
