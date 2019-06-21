package com.example.r.showtime.DB;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "favoriteItems")
public class Favorite {

    @PrimaryKey
    private int id;

    @ColumnInfo(name = "movie_title")
    private String title;

    @ColumnInfo(name = "movie_desc")
    private String desc;

    @ColumnInfo(name = "movie_release")
    private String releaseDate;

    @ColumnInfo(name = "movie_rating")
    private String rating;

    @ColumnInfo(name = "movie_img_url")
    private String imageUrl;

    @ColumnInfo(name = "status")
    private boolean isAdded;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean getAdded() {
        return isAdded;
    }
    public void setAdded(boolean added) {
        isAdded = added;
    }
}
