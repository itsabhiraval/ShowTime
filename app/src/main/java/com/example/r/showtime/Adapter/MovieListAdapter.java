package com.example.r.showtime.Adapter;


import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.r.showtime.Activity.DetailsActivity;
import com.example.r.showtime.Model.Movie;
import com.example.r.showtime.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<Movie> movieList;
    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private boolean isLoadingAdded = false;
    public MovieListAdapter(Context mContext) {
        this.mContext = mContext;
        movieList=new ArrayList<>();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
       RecyclerView.ViewHolder myViewHolder=null;
        View view;
        LayoutInflater inflater=LayoutInflater.from(mContext);
        switch (i){
            case ITEM:
                myViewHolder = getViewHolder(viewGroup, inflater);
                break;
            case LOADING:
                View v2 = inflater.inflate(R.layout.item_progress, viewGroup, false);
                myViewHolder = new LoadingVH(v2);
                break;
        }
        //view=inflater.inflate(R.layout.content_item,viewGroup,false);
        return myViewHolder;
    }
    @NonNull
    private RecyclerView.ViewHolder getViewHolder(ViewGroup parent, LayoutInflater inflater) {
        RecyclerView.ViewHolder myViewHolder=null;
        View v1 = inflater.inflate(R.layout.content_item, parent, false);
        myViewHolder = new MyViewHolder(v1);
        return myViewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)){
            case ITEM:
                final MyViewHolder myViewHolder= (MyViewHolder) holder;
                ((MyViewHolder) holder).tv_name.setText(movieList.get(position).getOriginalTitle());
                String vote=Double.toString(movieList.get(position).getVoteAverage());
                ((MyViewHolder) holder).tv_rating.setText(vote);
                String poster = "https://image.tmdb.org/t/p/w500" + movieList.get(position).getPosterPath();
                ((MyViewHolder) holder).img_thumbnail.setAnimation(AnimationUtils.loadAnimation(mContext,R.anim.fade_trans));
                Glide.with(mContext)
                        .load(poster)
                        .placeholder(R.drawable.load)
                        .into(((MyViewHolder) holder).img_thumbnail);
                break;
            case LOADING:
                break;
        }
    }

    @Override
    public int getItemCount() {
        return movieList == null ? 0 : movieList.size();
    }
    @Override
    public int getItemViewType(int position) {
        return (position == movieList.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }


    public void add(Movie r) {
        movieList.add(r);
        notifyItemInserted(movieList.size() - 1);
    }
    public void addAll(List<Movie> moveResults) {
        for (Movie result : moveResults) {
            add(result);
        }
    }

    public void remove(Movie r) {
        int position = movieList.indexOf(r);
        if (position > -1) {
            movieList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        isLoadingAdded = false;
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }


    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new Movie());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = movieList.size() - 1;
        Movie result = getItem(position);

        if (result != null) {
            movieList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public Movie getItem(int position) {
        return movieList.get(position);
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{
       @BindView(R.id.movie_name) TextView tv_name ;
        @BindView(R.id.rating) TextView tv_rating ;
        @BindView(R.id.studio) TextView tv_studio ;
        @BindView(R.id.categorie) TextView tv_category;
        @BindView(R.id.thumbnail) ImageView img_thumbnail;
        LinearLayout view_container;

        public MyViewHolder(@NonNull final View view) {
            super(view);
            ButterKnife.bind(this,view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION){
                        Movie clickedDataItem = movieList.get(pos);
                        Intent intent = new Intent(mContext, DetailsActivity.class);
                        ActivityOptions options=ActivityOptions.makeSceneTransitionAnimation((Activity) mContext,img_thumbnail,"transition");
                        intent.putExtra("movies", clickedDataItem );
                        intent.putExtra("movie_image","https://image.tmdb.org/t/p/w500"+clickedDataItem.getPosterPath());
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(intent,options.toBundle());
                }
            }
        });
    }
    }
    protected class LoadingVH extends RecyclerView.ViewHolder {

        public LoadingVH(View itemView) {
            super(itemView);
        }
    }

}

