package com.example.r.showtime.DB;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface FavoriteDAO  {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void addFav(Favorite favorite);

    @Query("SELECT * FROM favoriteItems ORDER BY id DESC")
    public List<Favorite> getFavorites();

    @Query("SELECT EXISTS (SELECT 1 FROM favoriteItems WHERE id=:itemId)")
    public int isFav(int itemId);

    @Delete
    void delete(Favorite favorite);
}
