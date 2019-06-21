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
import com.bumptech.glide.request.RequestOptions;
import com.example.r.showtime.Activity.DetailsActivity;
import com.example.r.showtime.Model.Movie;
import com.example.r.showtime.Model.TV;
import com.example.r.showtime.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TvListAdapter extends RecyclerView.Adapter<TvListAdapter.MViewHolder> {

    private Context mContext;
    private List<TV> tvList;

    public TvListAdapter(Context mContext, List<TV> tvList) {
        this.mContext = mContext;
        this.tvList = tvList;
        // Request option for Glide
    }
    @NonNull
    @Override
    public MViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int pos) {
        View view;
        LayoutInflater inflater=LayoutInflater.from(mContext);
        view=inflater.inflate(R.layout.content_item,viewGroup,false);
        return new TvListAdapter.MViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MViewHolder holder, int pos) {

        holder.tv_name.setText(tvList.get(pos).getOriginalName());
        String vote = Double.toString(tvList.get(pos).getVoteAverage());
        holder.tv_rating.setText(vote);
        String poster = "https://image.tmdb.org/t/p/w500" + tvList.get(pos).getPosterPath();
        Glide.with(mContext)
                .load(poster)
                .placeholder(R.drawable.load)
                .into(holder.img_thumbnail);
    }

    @Override
    public int getItemCount() {
        return tvList.size();
    }

    public class MViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.anime_name)
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
                        intent.putExtra("Tv", clickedDataItem );
                        intent.putExtra("tv_image","https://image.tmdb.org/t/p/w500"+clickedDataItem.getPosterPath());
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(intent);
                    }
                }
            });
        }
    }
}
