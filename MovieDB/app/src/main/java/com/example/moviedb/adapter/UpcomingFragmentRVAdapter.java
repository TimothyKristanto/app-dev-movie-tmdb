package com.example.moviedb.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.moviedb.R;
import com.example.moviedb.helper.Const;
import com.example.moviedb.model.Upcoming;

import java.util.List;

public class UpcomingFragmentRVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Upcoming.Results> listUpcoming;
    private Context context;

    public UpcomingFragmentRVAdapter(List<Upcoming.Results> listUpcoming, Context context) {
        this.listUpcoming = listUpcoming;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == 1) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.now_playing_viewholder, parent, false);
            return new UpcomingViewholder(view);
        }else{
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.loading_viewholder, parent, false);
            return new LoadingViewholder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof UpcomingViewholder){
            setUpViewholders((UpcomingViewholder) holder, position);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return listUpcoming.get(position) == null ? 0 : 1;
    }

    private void setUpViewholders(UpcomingViewholder holder, int position) {
        Upcoming.Results upcomingResults = listUpcoming.get(position);

        holder.txtMovieTitleNowPlayingViewholder.setText(upcomingResults.getTitle());
        holder.txtOverviewNowPlayingViewholder.setText(upcomingResults.getOverview());
        holder.txtReleaseDateNowPlayingViewholder.setText(upcomingResults.getRelease_date());
        Glide.with(context).load(Const.IMG_URL + upcomingResults.getPoster_path()).into(holder.imgMovieNowPlayingViewholder);
    }

    @Override
    public int getItemCount() {
        return listUpcoming.size();
    }

    public class UpcomingViewholder extends RecyclerView.ViewHolder {
        TextView txtMovieTitleNowPlayingViewholder,txtOverviewNowPlayingViewholder, txtReleaseDateNowPlayingViewholder;
        ImageView imgMovieNowPlayingViewholder;

        public UpcomingViewholder(@NonNull View itemView) {
            super(itemView);

            txtMovieTitleNowPlayingViewholder = itemView.findViewById(R.id.txtMovieTitleNowPlayingViewholder);
            txtOverviewNowPlayingViewholder = itemView.findViewById(R.id.txtOverviewNowPlayingViewholder);
            txtReleaseDateNowPlayingViewholder = itemView.findViewById(R.id.txtReleaseDateNowPlayingViewholder);
            imgMovieNowPlayingViewholder = itemView.findViewById(R.id.imgMovieNowPlayingViewholder);
        }
    }

    public class LoadingViewholder extends RecyclerView.ViewHolder{

        public LoadingViewholder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
