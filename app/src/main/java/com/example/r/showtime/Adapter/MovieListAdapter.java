package com.example.r.showtime.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.r.showtime.Activity.DetailsActivity;
import com.example.r.showtime.Model.Movie;
import com.example.r.showtime.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MyViewHolder> {
    private Context mContext;
    private List<Movie> movieList;
    public MovieListAdapter(Context mContext, List<Movie> movieList) {
        this.mContext = mContext;
        this.movieList = movieList;
        // Request option for Glide

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        LayoutInflater inflater=LayoutInflater.from(mContext);
        view=inflater.inflate(R.layout.content_item,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tv_name.setText(movieList.get(position).getOriginalTitle());
        String vote = Double.toString(movieList.get(position).getVoteAverage());
        holder.tv_rating.setText(vote);
        String poster = "https://image.tmdb.org/t/p/w500" + movieList.get(position).getPosterPath();
        Glide.with(mContext)
                .load(poster)
                .placeholder(R.drawable.load)
                .into(holder.img_thumbnail);
//        holder.tv_rating.setText(mData.get(position).getRating());
//        holder.tv_studio.setText(mData.get(position).getStudio());
//        holder.tv_category.setText(mData.get(position).getCategorie());
//
//        // Load Image from the internet and set it into Imageview using Glide
//
//        Glide.with(mContext).load(mData.get(position).getImage_url()).apply(option).into(holder.img_thumbnail);
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
       @BindView(R.id.anime_name) TextView tv_name ;
        @BindView(R.id.rating) TextView tv_rating ;
        @BindView(R.id.studio) TextView tv_studio ;
        @BindView(R.id.categorie) TextView tv_category;
        @BindView(R.id.thumbnail) ImageView img_thumbnail;
        LinearLayout view_container;

        public MyViewHolder(@NonNull View view) {
            super(view);
            ButterKnife.bind(this,view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION){
                        Movie clickedDataItem = movieList.get(pos);
                        Intent intent = new Intent(mContext, DetailsActivity.class);
                        intent.putExtra("movies", clickedDataItem );
                        intent.putExtra("movie_image","https://image.tmdb.org/t/p/w500"+clickedDataItem.getPosterPath());
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(intent);
                }
            }
        });
    }
    }
}

