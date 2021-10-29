package com.example.moviedb.repositories;

import androidx.lifecycle.MutableLiveData;

import com.example.moviedb.helper.Const;
import com.example.moviedb.model.Genre;
import com.example.moviedb.model.Movies;
import com.example.moviedb.model.NowPlaying;
import com.example.moviedb.model.Upcoming;
import com.example.moviedb.model.Videos;
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

    public MutableLiveData<NowPlaying> getNowPlaying(int page){
        final MutableLiveData<NowPlaying> resultNowPlaying = new MutableLiveData<>();

        ApiService.endPoint().getNowPlaying(Const.API_KEY, String.valueOf(page)).enqueue(new Callback<NowPlaying>() {
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

    public MutableLiveData<Upcoming> getUpcoming(int page){
        final MutableLiveData<Upcoming> resultUpcoming = new MutableLiveData<>();

        ApiService.endPoint().getUpcoming(Const.API_KEY, String.valueOf(page)).enqueue(new Callback<Upcoming>() {
            @Override
            public void onResponse(Call<Upcoming> call, Response<Upcoming> response) {
                resultUpcoming.setValue(response.body());
            }

            @Override
            public void onFailure(Call<Upcoming> call, Throwable t) {

            }
        });


        return resultUpcoming;
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

    public MutableLiveData<Videos> getVideoByMovieId(String movie_id){
        final MutableLiveData<Videos> resultGetVideoByMovieId = new MutableLiveData<>();

        ApiService.endPoint().getVideoByMovieId(movie_id, Const.API_KEY).enqueue(new Callback<Videos>() {
            @Override
            public void onResponse(Call<Videos> call, Response<Videos> response) {
                resultGetVideoByMovieId.setValue(response.body());
            }

            @Override
            public void onFailure(Call<Videos> call, Throwable t) {

            }
        });


        return resultGetVideoByMovieId;
    }

//    public MutableLiveData<Movies> getMoviesData(String movieId){
//        final MutableLiveData<Movies> resultMovie = new MutableLiveData<>();
//
//        ApiService.endPoint().getMovieById(movieId, Const.API_KEY).enqueue(new Callback<Movies>() {
//            @Override
//            public void onResponse(Call<Movies> call, Response<Movies> response) {
//                resultMovie.setValue(response.body());
//            }
//
//            @Override
//            public void onFailure(Call<Movies> call, Throwable t) {
//
//            }
//        });
//
//        return resultMovie;
//    }


}
