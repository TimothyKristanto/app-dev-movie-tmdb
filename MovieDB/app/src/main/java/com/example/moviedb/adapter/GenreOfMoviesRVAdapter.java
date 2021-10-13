package com.example.moviedb.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.moviedb.R;
import com.example.moviedb.helper.Const;
import com.example.moviedb.model.NowPlaying;
import com.example.moviedb.view.MovieDetailActivity;

import java.util.ArrayList;

public class GenreOfMoviesRVAdapter extends RecyclerView.Adapter<GenreOfMoviesRVAdapter.GenreOfMoviesViewHolder> {
    private ArrayList<NowPlaying.Results> listGenreResults;
    private Context context;

    public GenreOfMoviesRVAdapter(ArrayList<NowPlaying.Results> listGenreResults, Context context) {
        this.listGenreResults = listGenreResults;
        this.context = context;
    }

    @NonNull
    @Override
    public GenreOfMoviesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.now_playing_viewholder, parent, false);
        return new GenreOfMoviesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GenreOfMoviesViewHolder holder, int position) {
        final NowPlaying.Results genreResult = listGenreResults.get(position);

        holder.txtMovieTitleNowPlayingViewholder.setText(genreResult.getTitle());
        holder.txtOverviewNowPlayingViewholder.setText(genreResult.getOverview());
        holder.txtReleaseDateNowPlayingViewholder.setText(genreResult.getRelease_date());
        Glide.with(context).load(Const.IMG_URL + genreResult.getPoster_path()).into(holder.imgMovieNowPlayingViewholder);

        holder.cvNowPlayingViewholder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MovieDetailActivity.class);
                intent.putExtra("movie", genreResult);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listGenreResults.size();
    }

    public class GenreOfMoviesViewHolder extends RecyclerView.ViewHolder {
        TextView txtMovieTitleNowPlayingViewholder, txtOverviewNowPlayingViewholder, txtReleaseDateNowPlayingViewholder;
        ImageView imgMovieNowPlayingViewholder;
        CardView cvNowPlayingViewholder;

        public GenreOfMoviesViewHolder(@NonNull View itemView) {
            super(itemView);

            txtMovieTitleNowPlayingViewholder = itemView.findViewById(R.id.txtMovieTitleNowPlayingViewholder);
            txtOverviewNowPlayingViewholder = itemView.findViewById(R.id.txtOverviewNowPlayingViewholder);
            txtReleaseDateNowPlayingViewholder = itemView.findViewById(R.id.txtReleaseDateNowPlayingViewholder);
            imgMovieNowPlayingViewholder = itemView.findViewById(R.id.imgMovieNowPlayingViewholder);
            cvNowPlayingViewholder = itemView.findViewById(R.id.cvNowPlayingViewholder);
        }
    }
}
