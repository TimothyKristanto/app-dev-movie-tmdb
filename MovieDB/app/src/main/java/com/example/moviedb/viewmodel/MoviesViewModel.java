package com.example.moviedb.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.moviedb.model.Genre;
import com.example.moviedb.model.Movies;
import com.example.moviedb.model.NowPlaying;
import com.example.moviedb.repositories.MoviesRepository;

public class MoviesViewModel extends AndroidViewModel {

    private MoviesRepository moviesRepository;
    private MutableLiveData<Movies> resultGetMoviesData = new MutableLiveData<>();
    private MutableLiveData<NowPlaying> resultGetNowPlaying = new MutableLiveData<>();
    private MutableLiveData<Genre> resultGetGenresData = new MutableLiveData<>();

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

    public void getNowPlaying(){
        resultGetNowPlaying = moviesRepository.getNowPlaying();
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
}
