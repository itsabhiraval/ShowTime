package com.example.r.showtime.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.r.showtime.Adapter.MovieListAdapter;
import com.example.r.showtime.Adapter.TvListAdapter;
import com.example.r.showtime.BuildConfig;
import com.example.r.showtime.Model.Movie;
import com.example.r.showtime.Model.MoviesResponse;
import com.example.r.showtime.Model.TV;
import com.example.r.showtime.Model.TVResponse;
import com.example.r.showtime.OnScrollChanged;
import com.example.r.showtime.R;
import com.example.r.showtime.RetrofitClient;
import com.example.r.showtime.Services.MovieService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.support.constraint.Constraints.TAG;

public class TVFragment extends Fragment {
    View v;
    RecyclerView recyclerView;
    TvListAdapter tvListAdapter;
    List<TV> tvList;
    private static final int PAGE_START = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_PAGES = 5;
    private int currentPage = PAGE_START;
    private MovieService movieService;;
    ProgressBar progressBar;
    GridLayoutManager gridLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.fragment_one, container, false);
        recyclerView=v.findViewById(R.id.recyclerviewId);
        progressBar=v.findViewById(R.id.main_progress);
        tvListAdapter=new TvListAdapter(getContext());
        gridLayoutManager=new GridLayoutManager(getContext(),2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(tvListAdapter);
        recyclerView.addOnScrollListener(new OnScrollChanged(gridLayoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage += 1;

                // mocking network delay for API call
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadNextPage();
                    }
                }, 1000);
            }

            @Override
            public int getTotalPageCount() {
                return TOTAL_PAGES;
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });
        // movieListAdapter.notifyDataSetChanged();
        movieService=RetrofitClient.getClient().create(MovieService.class);
        loadFirstPage();
        //fetchData();
        return v;
        }
    private void loadNextPage() {
        Log.d(TAG, "loadNextPage: " + currentPage);
        callTopRatedMoviesApi().enqueue(new Callback<TVResponse>() {
            @Override
            public void onResponse(Call<TVResponse> call, Response<TVResponse> response) {
                tvListAdapter.removeLoadingFooter();
                isLoading = false;
                List<TV> results = fetchResults(response);
                tvListAdapter.addAll(results);
                if (currentPage != TOTAL_PAGES) tvListAdapter.addLoadingFooter();
                else isLastPage = true;
            }

            @Override
            public void onFailure(Call<TVResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void loadFirstPage() {
        Log.d(TAG, "loadFirstPage: ");

        callTopRatedMoviesApi().enqueue(new Callback<TVResponse>() {
            @Override
            public void onResponse(Call<TVResponse> call, Response<TVResponse> response) {
                // Got data. Send it to adapter
                List<TV> results = fetchResults(response);
                progressBar.setVisibility(View.GONE);
                tvListAdapter.addAll(results);
                if (currentPage <= TOTAL_PAGES) tvListAdapter.addLoadingFooter();
                else isLastPage = true;
            }

            @Override
            public void onFailure(Call<TVResponse> call, Throwable t) {
                t.printStackTrace();

            }
        });

    }
    private List<TV> fetchResults(Response<TVResponse> response) {
        TVResponse topRatedMovies = response.body();
        return topRatedMovies.getResults();
    }


    private Call<TVResponse> callTopRatedMoviesApi() {
        return movieService.getPopularTV(
                BuildConfig.THE_MOVIE_DB_API_TOKEN,
                currentPage
        );
    }

}