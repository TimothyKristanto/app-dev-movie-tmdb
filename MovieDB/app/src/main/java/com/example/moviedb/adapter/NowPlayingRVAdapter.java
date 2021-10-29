package com.example.moviedb.adapter;

import android.content.Context;
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

import java.util.List;

public class NowPlayingRVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<NowPlaying.Results> listNowPlaying;
    private Context context;

    public NowPlayingRVAdapter(List<NowPlaying.Results> listNowPlaying, Context context) {
        this.listNowPlaying = listNowPlaying;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == 1) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View view = layoutInflater.inflate(R.layout.now_playing_viewholder, parent, false);
            return new NowPlayingViewholder(view);
        } else{
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.loading_viewholder, parent, false);
            return new LoadingHolder(view);
        }
    }

    private void setUpViewHolder(NowPlayingViewholder holder, int position){
        final NowPlaying.Results positionResult = listNowPlaying.get(position);
        holder.txtMovieTitleNowPlayingViewholder.setText(listNowPlaying.get(position).getTitle());
        holder.txtOverviewNowPlayingViewholder.setText(listNowPlaying.get(position).getOverview());
        holder.txtReleaseDateNowPlayingViewholder.setText(listNowPlaying.get(position).getRelease_date());
        Glide.with(context).load(Const.IMG_URL + listNowPlaying.get(position).getPoster_path()).into(holder.imgMovieNowPlayingViewholder);

//        holder.cvNowPlayingViewholder.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                Intent intent = new Intent(context, MovieDetailActivity.class);
////                intent.putExtra("movie", positionResult);
////                context.startActivity(intent);
//
//                Bundle bundle = new Bundle();
//                bundle.putString("movieId", "" + positionResult.getId());
//                Navigation.findNavController(view).navigate(R.id.action_nowPlayingFragment_to_movieDetailsFragment, bundle);
//            }
//        });
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof NowPlayingViewholder){
            setUpViewHolder((NowPlayingViewholder) holder, position);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return listNowPlaying.get(position) == null? 0 : 1;
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

    public class LoadingHolder extends RecyclerView.ViewHolder{

        public LoadingHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
