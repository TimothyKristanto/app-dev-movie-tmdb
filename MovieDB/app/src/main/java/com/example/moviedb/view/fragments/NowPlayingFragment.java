package com.example.moviedb.view.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.moviedb.R;
import com.example.moviedb.adapter.NowPlayingRVAdapter;
import com.example.moviedb.helper.ItemClickSupport;
import com.example.moviedb.helper.LoadingDialog;
import com.example.moviedb.model.NowPlaying;
import com.example.moviedb.viewmodel.MoviesViewModel;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NowPlayingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NowPlayingFragment extends Fragment {
    private RecyclerView rvNowPlayingFragment;
    private NowPlayingRVAdapter adapter;
    private MoviesViewModel moviesViewModel;
    private Boolean isLoading;
    private List<NowPlaying.Results> listNowPlaying;
    private int page, maxPage;
    private LoadingDialog loadingDialog;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NowPlayingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NowPlayingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NowPlayingFragment newInstance(String param1, String param2) {
        NowPlayingFragment fragment = new NowPlayingFragment();
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
        View view = inflater.inflate(R.layout.fragment_now_playing, container, false);

        initVar(view);
        initViewModel();
        setUpAdapter();

        // Inflate the layout for this fragment
        return view;
    }

    private void initVar(View view){
        isLoading = false;
        page = 1;
        listNowPlaying = new ArrayList<>();
        rvNowPlayingFragment = view.findViewById(R.id.rvNowPlayingFragment);
        moviesViewModel = new ViewModelProvider(getActivity()).get(MoviesViewModel.class);
    }

    private void addData(){
        listNowPlaying.add(null);
        adapter.notifyDataSetChanged();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                listNowPlaying.remove(null);

                page += 1;

                initViewModel();

                isLoading = false;
            }
        }, 1000);
    }

    private void setUpAdapter(){
        adapter = new NowPlayingRVAdapter(listNowPlaying, getActivity());
        rvNowPlayingFragment.setAdapter(adapter);

        ItemClickSupport.addTo(rvNowPlayingFragment).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                Bundle bundle = new Bundle();
                bundle.putString("movieId", "" + listNowPlaying.get(position).getId());
                Navigation.findNavController(v).navigate(R.id.action_nowPlayingFragment_to_movieDetailsFragment, bundle);
            }
        });

        rvNowPlayingFragment.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) rvNowPlayingFragment.getLayoutManager();
                if(!isLoading){
                    if(layoutManager != null && layoutManager.findLastCompletelyVisibleItemPosition() == listNowPlaying.size() - 1) {
                        isLoading = true;
                        if(page < maxPage) {
                            addData();
                        }
                    }
                }
            }
        });

    }

    private void initViewModel(){
        moviesViewModel.getNowPlaying(page);
        moviesViewModel.getNowPlayingResult().observe(getActivity(), showGetNowPlayingResult);
    }

    private Observer<NowPlaying> showGetNowPlayingResult = new Observer<NowPlaying>() {
        @Override
        public void onChanged(NowPlaying nowPlaying) {
            maxPage = nowPlaying.getTotal_pages();

            for (NowPlaying.Results results : nowPlaying.getResults()){
                listNowPlaying.add(results);
            }

            adapter.notifyDataSetChanged();

            loadingDialog.stopLoading();
        }
    };
}