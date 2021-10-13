package com.example.moviedb.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
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
import java.util.List;

public class NowPlayingRVAdapter extends RecyclerView.Adapter<NowPlayingRVAdapter.NowPlayingViewholder> {
    private List<NowPlaying.Results> listNowPlaying;
    private Context context;

    public NowPlayingRVAdapter(List<NowPlaying.Results> listNowPlaying, Context context) {
        this.listNowPlaying = listNowPlaying;
        this.context = context;
    }

    @NonNull
    @Override
    public NowPlayingViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.now_playing_viewholder, parent, false);
        return new NowPlayingViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NowPlayingViewholder holder, int position) {
        final NowPlaying.Results positionResult = listNowPlaying.get(position);
        holder.txtMovieTitleNowPlayingViewholder.setText(listNowPlaying.get(position).getTitle());
        holder.txtOverviewNowPlayingViewholder.setText(listNowPlaying.get(position).getOverview());
        holder.txtReleaseDateNowPlayingViewholder.setText(listNowPlaying.get(position).getRelease_date());
        Glide.with(context).load(Const.IMG_URL + listNowPlaying.get(position).getPoster_path()).into(holder.imgMovieNowPlayingViewholder);

        holder.cvNowPlayingViewholder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MovieDetailActivity.class);
                intent.putExtra("movie", positionResult);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listNowPlaying.size();
    }

    public class NowPlayingViewholder extends RecyclerView.ViewHolder {
        TextView txtMovieTitleNowPlayingViewholder, txtOverviewNowPlayingViewholder, txtReleaseDateNowPlayingViewholder;
        ImageView imgMovieNowPlayingViewholder;
        CardView cvNowPlayingViewholder;

        public NowPlayingViewholder(@NonNull View itemView) {
            super(itemView);

            txtMovieTitleNowPlayingViewholder = itemView.findViewById(R.id.txtMovieTitleNowPlayingViewholder);
            txtOverviewNowPlayingViewholder = itemView.findViewById(R.id.txtOverviewNowPlayingViewholder);
            txtReleaseDateNowPlayingViewholder = itemView.findViewById(R.id.txtReleaseDateNowPlayingViewholder);
            imgMovieNowPlayingViewholder = itemView.findViewById(R.id.imgMovieNowPlayingViewholder);
            cvNowPlayingViewholder = itemView.findViewById(R.id.cvNowPlayingViewholder);
        }
    }
}
