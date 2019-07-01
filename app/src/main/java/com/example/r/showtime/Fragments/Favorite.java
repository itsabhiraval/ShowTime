package com.example.r.showtime.Fragments;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.r.showtime.Adapter.FavItemAdapter;
import com.example.r.showtime.DB.AppDatabase;
import com.example.r.showtime.DB.FavoriteDAO;
import com.example.r.showtime.R;

import java.util.List;

public class Favorite extends Fragment {

    View v;
    RecyclerView recyclerView;
    List<com.example.r.showtime.DB.Favorite> favorites;
    ProgressDialog progressDialog;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.fragment_two,container,false);
        recyclerView=v.findViewById(R.id.recyclerviewId);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        getFav();
        return v;
        }
        public void getFav() {
        List<com.example.r.showtime.DB.Favorite> favoriteList = AppDatabase.getAppDatabase(getContext()).favoriteDAO().getFavorites();
        FavItemAdapter favItemAdapter = new FavItemAdapter(getContext(), favoriteList);
        recyclerView.setAdapter(favItemAdapter);
        favItemAdapter.notifyDataSetChanged();
    }
}
