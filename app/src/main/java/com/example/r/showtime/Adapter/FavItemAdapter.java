package com.example.r.showtime.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.r.showtime.Activity.DetailsActivity;
import com.example.r.showtime.DB.AppDatabase;
import com.example.r.showtime.DB.Favorite;
import com.example.r.showtime.Model.Movie;
import com.example.r.showtime.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavItemAdapter extends RecyclerView.Adapter<FavItemAdapter.MyViewHolder> {


    private Context mContext;
    private List<Favorite> favoriteList;

    public FavItemAdapter(Context mContext, List<Favorite> favoriteList) {
        this.mContext = mContext;
        this.favoriteList = favoriteList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view=LayoutInflater.from(mContext).inflate(R.layout.content_item,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Favorite favorite=favoriteList.get(position);
        holder.tv_name.setText(favorite.getTitle());
        holder.tv_rating.setText(favorite.getRating());
        String poster = "https://image.tmdb.org/t/p/w500" + favorite.getImageUrl();
        Glide.with(mContext)
                .load(poster)
                .into(holder.img_thumbnail);

    }

    @Override
    public int getItemCount() {
        return favoriteList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.anime_name)
        TextView tv_name ;
        @BindView(R.id.rating) TextView tv_rating ;
        @BindView(R.id.studio) TextView tv_studio ;
        @BindView(R.id.categorie) TextView tv_category;
        @BindView(R.id.thumbnail)
        ImageView img_thumbnail;
        LinearLayout view_container;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION){
                        Intent intent = new Intent(mContext, DetailsActivity.class);
                        intent.putExtra("fav_title",favoriteList.get(pos).getTitle());
                        intent.putExtra("fav_image","https://image.tmdb.org/t/p/w500"+favoriteList.get(pos).getImageUrl());
                        intent.putExtra("fav_desc",favoriteList.get(pos).getDesc());
                        intent.putExtra("fav_release",favoriteList.get(pos).getReleaseDate());
                        intent.putExtra("fav_rating",favoriteList.get(pos).getRating());
                        intent.putExtra("fav_id",favoriteList.get(pos).getId());
                        intent.putExtra("fav_pos",pos);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(intent);
                    }
                }
            });
        }
    }
}
