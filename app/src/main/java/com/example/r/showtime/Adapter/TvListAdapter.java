package com.example.r.showtime.Adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.r.showtime.Activity.DetailsActivity;
import com.example.r.showtime.Model.Movie;
import com.example.r.showtime.Model.TV;
import com.example.r.showtime.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TvListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<TV> tvList;
    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private boolean isLoadingAdded = false;
    public TvListAdapter(Context mContext) {
        this.mContext = mContext;
         tvList=new ArrayList<>();
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int pos) {
        RecyclerView.ViewHolder myViewHolder=null;
        View view;
        LayoutInflater inflater=LayoutInflater.from(mContext);
        switch (pos){
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
        myViewHolder = new MViewHolder(v1);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)){
            case ITEM:
                final TvListAdapter.MViewHolder myViewHolder= (TvListAdapter.MViewHolder) holder;
                ((TvListAdapter.MViewHolder) holder).tv_name.setText(tvList.get(position).getOriginalName());
                String vote=Double.toString(tvList.get(position).getVoteAverage());
                ((TvListAdapter.MViewHolder) holder).tv_rating.setText(vote);
                String poster = "https://image.tmdb.org/t/p/w500" + tvList.get(position).getPosterPath();
                ((TvListAdapter.MViewHolder) holder).img_thumbnail.setAnimation(AnimationUtils.loadAnimation(mContext,R.anim.fade_trans));
                Glide.with(mContext)
                        .load(poster)
                        .placeholder(R.drawable.load)
                        .into(((TvListAdapter.MViewHolder) holder).img_thumbnail);
                break;
            case LOADING:
                break;
        }
    }

    @Override
    public int getItemCount() {
        return tvList.size();
    }
    @Override
    public int getItemViewType(int position) {
        return (position == tvList.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }


    public void add(TV r) {
        tvList.add(r);
        notifyItemInserted(tvList.size() - 1);
    }
    public void addAll(List<TV> moveResults) {
        for (TV result : moveResults) {
            add(result);
        }
    }

    public void remove(TV r) {
        int position = tvList.indexOf(r);
        if (position > -1) {
            tvList.remove(position);
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
        add(new TV());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = tvList.size() - 1;
        TV result = getItem(position);

        if (result != null) {
            tvList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public TV getItem(int position) {
        return tvList.get(position);
    }


    public class MViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.movie_name)
        TextView tv_name ;
        @BindView(R.id.rating) TextView tv_rating ;
        @BindView(R.id.studio) TextView tv_studio ;
        @BindView(R.id.categorie) TextView tv_category;
        @BindView(R.id.thumbnail)
        ImageView img_thumbnail;
        LinearLayout view_container;
        public MViewHolder(@NonNull View view) {
            super(view);
            ButterKnife.bind(this,view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION){
                        TV clickedDataItem = tvList.get(pos);
                        Intent intent = new Intent(mContext, DetailsActivity.class);
                        ActivityOptions options=ActivityOptions.makeSceneTransitionAnimation((Activity) mContext,img_thumbnail,"transition");
                        intent.putExtra("Tv", clickedDataItem );
                        intent.putExtra("tv_image","https://image.tmdb.org/t/p/w500"+clickedDataItem.getPosterPath());
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
