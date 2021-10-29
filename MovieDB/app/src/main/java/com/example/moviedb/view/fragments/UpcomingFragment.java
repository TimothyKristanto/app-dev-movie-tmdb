package com.example.moviedb.view.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.moviedb.R;
import com.example.moviedb.adapter.UpcomingFragmentRVAdapter;
import com.example.moviedb.helper.ItemClickSupport;
import com.example.moviedb.helper.LoadingDialog;
import com.example.moviedb.model.Upcoming;
import com.example.moviedb.viewmodel.MoviesViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UpcomingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UpcomingFragment extends Fragment {
    private RecyclerView rvUpcomingFragment;
    private MoviesViewModel moviesViewModel;
    private UpcomingFragmentRVAdapter adapter;
    private int page, maxPage;
    private List<Upcoming.Results> listUpcomingResults;
    private Boolean isLoading;
    private LoadingDialog loadingDialog;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UpcomingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UpcomingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UpcomingFragment newInstance(String param1, String param2) {
        UpcomingFragment fragment = new UpcomingFragment();
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
        View view = inflater.inflate(R.layout.fragment_upcoming, container, false);

        initView(view);
        initViewModel();
        setAdapter();

        // Inflate the layout for this fragment
        return view;
    }

    private void addDatas() {
        listUpcomingResults.add(null);
        adapter.notifyDataSetChanged();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                listUpcomingResults.remove(null);

                page += 1;

                initViewModel();

                isLoading = false;
            }
        }, 500);

    }

    private void initView(View view) {
        isLoading = false;
        listUpcomingResults = new ArrayList<>();
        page = 1;
        rvUpcomingFragment = view.findViewById(R.id.rvUpcomingFragment);

        moviesViewModel = new ViewModelProvider(getActivity()).get(MoviesViewModel.class);
    }

    private void initViewModel(){
        moviesViewModel.getUpcoming(page);
        moviesViewModel.getUpcomingResult().observe(getActivity(), showGetUpcomingResult);
    }

    private Observer<Upcoming> showGetUpcomingResult = new Observer<Upcoming>() {
        @Override
        public void onChanged(Upcoming upcoming) {
            maxPage = upcoming.getTotal_pages();

            for(Upcoming.Results results : upcoming.getResults()){
                listUpcomingResults.add(results);
            }
            adapter.notifyDataSetChanged();

            loadingDialog.stopLoading();
        }
    };

    private void setAdapter(){
        adapter = new UpcomingFragmentRVAdapter(listUpcomingResults, getActivity());
        rvUpcomingFragment.setAdapter(adapter);

        ItemClickSupport.addTo(rvUpcomingFragment).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                Bundle bundle = new Bundle();
                bundle.putString("movieId", "" + listUpcomingResults.get(position).getId());
                Navigation.findNavController(v).navigate(R.id.action_upcomingFragment_to_movieDetailsFragment, bundle);
            }
        });

        rvUpcomingFragment.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) rvUpcomingFragment.getLayoutManager();
                if(!isLoading){
                    if(layoutManager != null && layoutManager.findLastCompletelyVisibleItemPosition() == listUpcomingResults.size() - 1){
                        isLoading = true;
                        if(page < maxPage) {
                            addDatas();
                        }
                    }
                }
            }
        });
    }
}