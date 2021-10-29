package com.example.moviedb.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.moviedb.R;
import com.example.moviedb.adapter.NowPlayingRVAdapter;
import com.example.moviedb.model.NowPlaying;
import com.example.moviedb.viewmodel.MoviesViewModel;

import java.util.ArrayList;

public class NowPlayingActivity extends AppCompatActivity {
    private RecyclerView rvNowPlaying;
    private MoviesViewModel moviesViewModel;
    private NowPlayingRVAdapter adapter;
    private ArrayList<NowPlaying> listNowPlaying;
    private int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_now_playing);

        initVar();
        initViewModel();
    }

    private void initVar() {
        rvNowPlaying = findViewById(R.id.rvNowPlaying);
        moviesViewModel = new ViewModelProvider(NowPlayingActivity.this).get(MoviesViewModel.class);
    }

    private void initViewModel(){
        moviesViewModel.getNowPlaying(page);
        moviesViewModel.getNowPlayingResult().observe(NowPlayingActivity.this, showNowPlayingResult);
    }

    private Observer<NowPlaying> showNowPlayingResult = new Observer<NowPlaying>() {
        @Override
        public void onChanged(NowPlaying nowPlaying) {
            adapter = new NowPlayingRVAdapter(nowPlaying.getResults(), NowPlayingActivity.this);
            rvNowPlaying.setAdapter(adapter);
        }
    };


}