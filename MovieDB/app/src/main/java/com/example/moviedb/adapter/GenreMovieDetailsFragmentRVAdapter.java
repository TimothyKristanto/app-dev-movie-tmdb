package com.example.moviedb.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviedb.R;
import com.example.moviedb.model.Movies;

import java.util.List;

public class GenreMovieDetailsFragmentRVAdapter extends RecyclerView.Adapter<GenreMovieDetailsFragmentRVAdapter.GenreMovieDetailsViewholder> {
    private List<Movies.Genres> listGenre;

    public GenreMovieDetailsFragmentRVAdapter(List<Movies.Genres> listGenre) {
        this.listGenre = listGenre;
    }

    @NonNull
    @Override
    public GenreMovieDetailsViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_detail_genre_viewholder, parent, false);
        return new GenreMovieDetailsViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GenreMovieDetailsViewholder holder, int position) {
        holder.txtGenreMovieDetailViewholder.setText(listGenre.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return listGenre.size();
    }

    class GenreMovieDetailsViewholder extends RecyclerView.ViewHolder {
        TextView txtGenreMovieDetailViewholder;

        public GenreMovieDetailsViewholder(@NonNull View itemView) {
            super(itemView);

            txtGenreMovieDetailViewholder = itemView.findViewById(R.id.txtGenreMovieDetailViewholder);
        }
    }
}
