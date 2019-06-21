package com.example.r.showtime.Fragments;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.r.showtime.Adapter.TabAdapter;
import com.example.r.showtime.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Home extends Fragment {
    View view;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    private TabAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this,view);

        adapter = new TabAdapter(getFragmentManager());
        adapter.addFragment(new Movies(), "Movies");
        adapter.addFragment(new TVFragment(), "TV");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }

}
