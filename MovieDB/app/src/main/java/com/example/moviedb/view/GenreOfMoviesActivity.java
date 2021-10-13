package com.example.moviedb.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.moviedb.R;
import com.example.moviedb.adapter.GenreOfMoviesRVAdapter;
import com.example.moviedb.model.NowPlaying;
import com.example.moviedb.viewmodel.MoviesViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.Result;

public class GenreOfMoviesActivity extends AppCompatActivity {
    private int genreId;
    private String toolbarTitle;
    private RecyclerView rvGenreOfMovies;
    private Toolbar toolbarGenreOfMovies;
    private MoviesViewModel moviesViewModel;
    private ArrayList<NowPlaying.Results> listGenreResults;
    private GenreOfMoviesRVAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genre_of_movies);

        getIntentData();
        initVar();
        setComponent();
        initViewModel();
        setListener();
    }

    private void setListener() {
        toolbarGenreOfMovies.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GenreOfMoviesActivity.this, NowPlayingActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
    }

    private void initViewModel() {
        moviesViewModel.getNowPlaying();
        moviesViewModel.getNowPlayingResult().observe(GenreOfMoviesActivity.this, showGetNowPlayingResult);
    }

    private Observer<NowPlaying> showGetNowPlayingResult = new Observer<NowPlaying>() {
        @Override
        public void onChanged(NowPlaying nowPlaying) {
            for(NowPlaying.Results i : nowPlaying.getResults()){
                if(i.getGenre_ids().contains(genreId)){
                    listGenreResults.add(i);
                }
            }

            setAdapter();
        }
    };

    private void setAdapter(){
        adapter = new GenreOfMoviesRVAdapter(listGenreResults, GenreOfMoviesActivity.this);
        rvGenreOfMovies.setAdapter(adapter);
    }

    private void initVar() {
        rvGenreOfMovies = findViewById(R.id.rvGenreOfMovies);
        toolbarGenreOfMovies = findViewById(R.id.toolbarGenreOfMovies);
        moviesViewModel = new ViewModelProvider(GenreOfMoviesActivity.this).get(MoviesViewModel.class);
        listGenreResults = new ArrayList<>();
    }

    private void setComponent(){
        toolbarGenreOfMovies.setTitle(toolbarTitle);
    }

    private void getIntentData() {
        Intent intent = getIntent();
        if(intent.getIntExtra("genreId", -1) != -1 && intent.getStringExtra("toolbarTitle") != null){
            genreId = intent.getIntExtra("genreId", -1);
            toolbarTitle = intent.getStringExtra("toolbarTitle");
        }
    }
}