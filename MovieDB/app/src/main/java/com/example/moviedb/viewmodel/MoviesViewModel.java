package com.example.moviedb.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.moviedb.model.Genre;
import com.example.moviedb.model.Movies;
import com.example.moviedb.model.NowPlaying;
import com.example.moviedb.model.Upcoming;
import com.example.moviedb.model.Videos;
import com.example.moviedb.repositories.MoviesRepository;

public class MoviesViewModel extends AndroidViewModel {

    private MoviesRepository moviesRepository;
    private MutableLiveData<Movies> resultGetMoviesData = new MutableLiveData<>();
    private MutableLiveData<NowPlaying> resultGetNowPlaying = new MutableLiveData<>();
    private MutableLiveData<Genre> resultGetGenresData = new MutableLiveData<>();
    private MutableLiveData<Upcoming> resultGetUpcoming = new MutableLiveData<>();
    private MutableLiveData<Videos> resultGetVideoByMovieId = new MutableLiveData<>();

    public MoviesViewModel(@NonNull Application application) {
        super(application);

        moviesRepository = MoviesRepository.getInstance();
    }

    public void getMovieById(String movieId){
        resultGetMoviesData = moviesRepository.getMoviesData(movieId);
    }

    public LiveData<Movies> getMovieByIdResult(){
        return resultGetMoviesData;
    }

    public void getNowPlaying(int page){
        resultGetNowPlaying = moviesRepository.getNowPlaying(page);
    }

    public LiveData<NowPlaying> getNowPlayingResult(){
        return resultGetNowPlaying;
    }

    public void getGenreById(String genreId){
        resultGetGenresData = moviesRepository.getGenresData(genreId);
    }

    public LiveData<Genre> getGenreByIdResult(){
        return resultGetGenresData;
    }

    public void getUpcoming(int page){
        resultGetUpcoming = moviesRepository.getUpcoming(page);
    }

    public LiveData<Upcoming> getUpcomingResult(){
        return resultGetUpcoming;
    }

    public void getVideoByMovieId(String movie_id){
        resultGetVideoByMovieId = moviesRepository.getVideoByMovieId(movie_id);
    }

    public LiveData<Videos> getVideoByMovieIdResult(){
        return resultGetVideoByMovieId;
    }
}
