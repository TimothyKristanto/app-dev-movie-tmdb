package com.example.moviedb.view.fragments;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.example.moviedb.R;
import com.example.moviedb.adapter.GenreMovieDetailsFragmentRVAdapter;
import com.example.moviedb.adapter.ProductionCompaniesMovieDetailsFragmentRvAdapter;
import com.example.moviedb.helper.Const;
import com.example.moviedb.helper.ItemClickSupport;
import com.example.moviedb.helper.LoadingDialog;
import com.example.moviedb.model.Movies;
import com.example.moviedb.model.Videos;
import com.example.moviedb.viewmodel.MoviesViewModel;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.YouTubePlayerTracker;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MovieDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MovieDetailsFragment extends Fragment {
    private TextView txtTitleMovieDetailsFragment, txtTaglineMovieDetailsFragment, txtRateMovieDetailsFragment, txtOverviewMovieDetailsFragment,
            txtPopularityMovieDetailsFragment, txtTotalVoteMovieDetailsFragment, txtReleaseDateMovieDetailsFragment, txtNoTrailerMovieDetailsFragment;
    private ImageView imgBackdropMovieDetailsFragment, imgPosterMovieDetailsFragment;
    private RecyclerView rvGenresMovieDetailsFragment, rvProductionCompaniesMovieDetailsFragment, rvCastsMovieDetailsFragment;
    private String movieId;
    private MoviesViewModel moviesViewModel;
    private GenreMovieDetailsFragmentRVAdapter genreAdapter;
    private ProductionCompaniesMovieDetailsFragmentRvAdapter productionCompaniesAdapter;
    private LoadingDialog loadingDialog;
    private YouTubePlayerView ytTrailerMovieDetailsFragment;
    private YouTubePlayerTracker ytTracker;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MovieDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MovieDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MovieDetailsFragment newInstance(String param1, String param2) {
        MovieDetailsFragment fragment = new MovieDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        loadingDialog = new LoadingDialog(getActivity());
        loadingDialog.startLoading();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_details, container, false);

        initVar(view);
        getNavigationArgument();

        // Inflate the layout for this fragment
        return view;
    }

    private void getNavigationArgument(){
        movieId = getArguments().getString("movieId");
        moviesViewModel.getMovieById(movieId);
        moviesViewModel.getMovieByIdResult().observe(getActivity(), showGetMovieByIdResult);
        moviesViewModel.getVideoByMovieId(movieId);
        moviesViewModel.getVideoByMovieIdResult().observe(getActivity(), showGetVideoByMovieIdResult);

    }

    private Observer<Videos> showGetVideoByMovieIdResult = new Observer<Videos>() {
        @Override
        public void onChanged(Videos videos) {
            MovieDetailsFragment.this.getLifecycle().addObserver(ytTrailerMovieDetailsFragment);

            final String video = videos.getResults().get(0).getKey();

            ytTrailerMovieDetailsFragment.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
               @Override
               public void onReady(YouTubePlayer youTubePlayer) {
                   youTubePlayer.cueVideo(video, 0);
               }
            });
        }
    };

    private void initVar(View view){
        txtNoTrailerMovieDetailsFragment = view.findViewById(R.id.txtNoTrailerMovieDetailsFragment);
        txtTitleMovieDetailsFragment = view.findViewById(R.id.txtTitleMovieDetailsFragment);
        txtTaglineMovieDetailsFragment = view.findViewById(R.id.txtTaglineMovieDetailsFragment);
        txtRateMovieDetailsFragment = view.findViewById(R.id.txtRateMovieDetailsFragment);
        txtOverviewMovieDetailsFragment = view.findViewById(R.id.txtOverviewMovieDetailsFragment);
        txtPopularityMovieDetailsFragment = view.findViewById(R.id.txtPopularityMovieDetailsFragment);
        txtTotalVoteMovieDetailsFragment = view.findViewById(R.id.txtTotalVoteMovieDetailsFragment);
        txtReleaseDateMovieDetailsFragment = view.findViewById(R.id.txtReleaseDateMovieDetailsFragment);
        imgBackdropMovieDetailsFragment = view.findViewById(R.id.imgBackdropMovieDetailsFragment);
        imgPosterMovieDetailsFragment = view.findViewById(R.id.imgPosterMovieDetailsFragment);
        rvGenresMovieDetailsFragment = view.findViewById(R.id.rvGenresMovieDetailsFragment);
        rvProductionCompaniesMovieDetailsFragment = view.findViewById(R.id.rvProductionCompaniesMovieDetailsFragment);
        moviesViewModel = new ViewModelProvider(getActivity()).get(MoviesViewModel.class);
        ytTrailerMovieDetailsFragment = view.findViewById(R.id.ytTrailerMovieDetailsFragment);
    }

    private Observer<Movies> showGetMovieByIdResult = new Observer<Movies>() {
        @Override
        public void onChanged(Movies movies) {
            txtTitleMovieDetailsFragment.setText(movies.getTitle());
            txtOverviewMovieDetailsFragment.setText(movies.getOverview());
            txtRateMovieDetailsFragment.setText(String.valueOf(movies.getVote_average()));
            txtPopularityMovieDetailsFragment.setText("Popularity: " + movies.getPopularity());
            if(movies.getTagline() != null && movies.getTagline() != "") {
                txtTaglineMovieDetailsFragment.setText("\"" + movies.getTagline() + "\"");
            }else{
                txtTaglineMovieDetailsFragment.setText("No Tagline :(");
            }
            txtReleaseDateMovieDetailsFragment.setText("Release date: " + movies.getRelease_date());
            txtTotalVoteMovieDetailsFragment.setText("Total vote: " + movies.getVote_count());
            Glide.with(getActivity()).load(Const.IMG_URL + movies.getPoster_path()).into(imgPosterMovieDetailsFragment);
            Glide.with(getActivity()).load(Const.IMG_URL + movies.getBackdrop_path()).into(imgBackdropMovieDetailsFragment);

            setMoviesAdapter(movies.getGenres(), movies.getProduction_companies());

            loadingDialog.stopLoading();
        }
    };

    private void setMoviesAdapter(List<Movies.Genres> listGenre, List<Movies.ProductionCompanies> listProductionCompanies){

        RecyclerView.LayoutManager genreLayoutManager = new GridLayoutManager(getActivity(), 1, RecyclerView.HORIZONTAL, false);
        RecyclerView.LayoutManager productionCompaniesLayoutManager = new GridLayoutManager(getActivity(), 1, RecyclerView.HORIZONTAL, false);

        genreAdapter = new GenreMovieDetailsFragmentRVAdapter(listGenre);
        rvGenresMovieDetailsFragment.setLayoutManager(genreLayoutManager);
        rvGenresMovieDetailsFragment.setAdapter(genreAdapter);

        productionCompaniesAdapter = new ProductionCompaniesMovieDetailsFragmentRvAdapter(listProductionCompanies, getActivity());
        rvProductionCompaniesMovieDetailsFragment.setAdapter(productionCompaniesAdapter);
        rvProductionCompaniesMovieDetailsFragment.setLayoutManager(productionCompaniesLayoutManager);
        ItemClickSupport.addTo(rvProductionCompaniesMovieDetailsFragment).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                Toast.makeText(getActivity(), listProductionCompanies.get(position).getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}