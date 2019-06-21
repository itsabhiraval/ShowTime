package com.example.r.showtime.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.r.showtime.Adapter.MovieListAdapter;
import com.example.r.showtime.Adapter.TvListAdapter;
import com.example.r.showtime.BuildConfig;
import com.example.r.showtime.Model.Movie;
import com.example.r.showtime.Model.MoviesResponse;
import com.example.r.showtime.Model.TV;
import com.example.r.showtime.Model.TVResponse;
import com.example.r.showtime.R;
import com.example.r.showtime.RetrofitClient;
import com.example.r.showtime.Services.MovieService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TVFragment extends Fragment {
    View v;
    RecyclerView recyclerView;
    TvListAdapter tvListAdapter;
    List<TV> tvList;
    ProgressDialog progressDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.fragment_two, container, false);
        recyclerView=v.findViewById(R.id.recyclerviewId);
        tvList=new ArrayList<>();
        tvListAdapter =new TvListAdapter(getContext(),tvList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(tvListAdapter);
        tvListAdapter.notifyDataSetChanged();
        fetchData();
        return v;
    }
    private void fetchData() {
        try{
            RetrofitClient client=new RetrofitClient();
            MovieService movieService=RetrofitClient.getClient().create(MovieService.class);
            Call<TVResponse> call=movieService.getPopularTV(BuildConfig.THE_MOVIE_DB_API_TOKEN);
            call.enqueue(new Callback<TVResponse>() {
                @Override
                public void onResponse(Call<TVResponse> call, Response<TVResponse> response) {
                   List<TV> tvs=response.body().getResults();
                    recyclerView.setAdapter(new TvListAdapter(getActivity(), tvs));
                    recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
                    recyclerView.smoothScrollToPosition(0);
                }

                @Override
                public void onFailure(Call<TVResponse> call, Throwable t) {
                    Log.d("Error in retrofit", t.getMessage());
                    Toast.makeText(getActivity(), "Error Fetching Data", Toast.LENGTH_SHORT).show();
                }
            });
        }
        catch (Exception e){
            Log.d("Error", e.getMessage());
            Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}