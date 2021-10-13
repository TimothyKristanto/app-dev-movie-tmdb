package com.example.moviedb.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.moviedb.R;
import com.example.moviedb.adapter.MovieDetailGenreRVAdapter;
import com.example.moviedb.helper.Const;
import com.example.moviedb.model.Genre;
import com.example.moviedb.model.NowPlaying;
import com.example.moviedb.viewmodel.MoviesViewModel;

import java.util.ArrayList;
import java.util.List;

public class MovieDetailActivity extends AppCompatActivity {
    private NowPlaying.Results movie;
    private TextView txtTitleMovieDetail, txtSynopsisMovieDetail, txtRatingMovieDetail, txtReleaseDateMovieDetail;
    private TextView txtPopularityMovieDetail, txtOriginalTitleMovieDetail, txtOriginalLanguageMovieDetail;
    private ImageView imgPosterMovieDetail;
    private RecyclerView rvGenreMovieDetail;
    private Toolbar toolbarMovieDetail;
    private MoviesViewModel moviesViewModel;
    private ArrayList<String> listMovieGenre;
    private Genre genre;
    private MovieDetailGenreRVAdapter adapter;
    private List<NowPlaying.Results> movieResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        getIntentData();
        initVar();
        initViewModel();
        setComponent();
        setListener();
    }

    private void initViewModel() {
        for(int genreId : movie.getGenre_ids()){
            moviesViewModel.getGenreById(String.valueOf(genreId));
            moviesViewModel.getGenreByIdResult().observe(MovieDetailActivity.this, showGetGenreByIdResult);
        }
    }

    private Observer<Genre> showGetGenreByIdResult = new Observer<Genre>() {
        @Override
        public void onChanged(Genre genre) {
            // cek bilaman id dalam genres sama dengan id dalam genre_ids
            listMovieGenre.clear();
            for(int i : movie.getGenre_ids()){
                listMovieGenre.add(genre.getGenresHashMap().get(i));
            }
            setAdapter();
        }
    };

    private void setListener() {
        toolbarMovieDetail.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MovieDetailActivity.this, NowPlayingActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
    }

    private void setAdapter() {
        adapter = new MovieDetailGenreRVAdapter(listMovieGenre, MovieDetailActivity.this, movie.getGenre_ids());
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 1, RecyclerView.HORIZONTAL, false);
        rvGenreMovieDetail.setLayoutManager(layoutManager);
        rvGenreMovieDetail.setAdapter(adapter);
    }

    public void initVar(){
        txtTitleMovieDetail = findViewById(R.id.txtTitleMovieDetail);
        txtSynopsisMovieDetail = findViewById(R.id.txtSynopsisMovieDetail);
        txtRatingMovieDetail = findViewById(R.id.txtRatingMovieDetail);
        txtReleaseDateMovieDetail = findViewById(R.id.txtReleaseDateMovieDetail);
        txtPopularityMovieDetail = findViewById(R.id.txtPopularityMovieDetail);
        txtOriginalTitleMovieDetail = findViewById(R.id.txtOriginalTitleMovieDetail);
        txtOriginalLanguageMovieDetail = findViewById(R.id.txtOriginalLanguageMovieDetail);
        imgPosterMovieDetail = findViewById(R.id.imgPosterMovieDetail);
        rvGenreMovieDetail = findViewById(R.id.rvGenreMovieDetail);
        toolbarMovieDetail = findViewById(R.id.toolbarMovieDetail);
        moviesViewModel = new ViewModelProvider(MovieDetailActivity.this).get(MoviesViewModel.class);
        listMovieGenre = new ArrayList<>();
        genre = new Genre();
    }

    public void getIntentData(){
        Intent intent = getIntent();
        if(intent.getParcelableExtra("movie") != null){
            movie = intent.getParcelableExtra("movie");
        }
    }

    public void setComponent(){
        txtTitleMovieDetail.setText(movie.getTitle());
        txtPopularityMovieDetail.setText("Popularity: " + String.valueOf(movie.getPopularity()));
        txtOriginalTitleMovieDetail.setText("Original Title: " + movie.getOriginal_title());
        txtOriginalLanguageMovieDetail.setText("Original Language: " + movie.getOriginal_language());
        txtSynopsisMovieDetail.setText(movie.getOverview());
        txtRatingMovieDetail.setText("Rating: " + String.valueOf(movie.getVote_average()));
        txtReleaseDateMovieDetail.setText("Release Date: " + movie.getRelease_date());
        Glide.with(MovieDetailActivity.this).load(Const.IMG_URL + movie.getPoster_path()).into(imgPosterMovieDetail);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}