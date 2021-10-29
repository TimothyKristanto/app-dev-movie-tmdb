package com.example.moviedb.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviedb.R;
import com.example.moviedb.model.NowPlaying;
import com.example.moviedb.view.activities.GenreOfMoviesActivity;
//import com.example.moviedb.view.activities.GenreOfMoviesActivity;

import java.util.ArrayList;
import java.util.List;

public class MovieDetailGenreRVAdapter extends RecyclerView.Adapter<MovieDetailGenreRVAdapter.MovieDetailGenreViewHolder> {
    private ArrayList<String> listMovieGenre;
    private Context context;
    private List<Integer> genreIds;
    private List<NowPlaying.Results> listNowPlaying;

    public MovieDetailGenreRVAdapter(ArrayList<String> listGenre, Context context, List<Integer> genreIds) {
        this.listMovieGenre = listGenre;
        this.context = context;
        this.genreIds = genreIds;
    }

    @NonNull
    @Override
    public MovieDetailGenreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.movie_detail_genre_viewholder, parent, false);
        return new MovieDetailGenreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieDetailGenreViewHolder holder, int position) {
        final String resultMovieGenre = listMovieGenre.get(position);
        final int resultGenreId = genreIds.get(position);
        final NowPlaying.Results results = listNowPlaying.get(position);

        holder.txtGenreMovieDetailViewholder.setText(resultMovieGenre);

        holder.cvGenreMovieDetailViewholder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, GenreOfMoviesActivity.class);
                intent.putExtra("toolbarTitle", resultMovieGenre);
                intent.putExtra("genreId", resultGenreId);
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return listMovieGenre.size();
    }

    public class MovieDetailGenreViewHolder extends RecyclerView.ViewHolder {
        TextView txtGenreMovieDetailViewholder;
        CardView cvGenreMovieDetailViewholder;

        public MovieDetailGenreViewHolder(@NonNull View itemView) {
            super(itemView);

            txtGenreMovieDetailViewholder = itemView.findViewById(R.id.txtGenreMovieDetailViewholder);
            cvGenreMovieDetailViewholder = itemView.findViewById(R.id.cvGenreMovieDetailViewholder);
        }
    }
}
