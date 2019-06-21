package com.example.r.showtime.Activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.r.showtime.Adapter.TabAdapter;
import com.example.r.showtime.Fragments.Movies;
import com.example.r.showtime.Fragments.TVFragment;
import com.example.r.showtime.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity {

    @BindView(R.id.viewPager)
     ViewPager viewPager;
    @BindView(R.id.tabLayout)
     TabLayout tabLayout;
    private TabAdapter adapter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        adapter = new TabAdapter(getSupportFragmentManager());
        adapter.addFragment(new Movies(), "Movies");
        adapter.addFragment(new TVFragment(), "TVResponse Shows");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

    }

}
