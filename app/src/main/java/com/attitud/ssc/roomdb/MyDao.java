package com.attitud.ssc.roomdb;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MyDao {

    @Insert
    public void addFavorite(FavoriteData data);

    @Delete
    public void deleteFavorite(FavoriteData data);

    @Query("DELETE FROM favoritedata WHERE id == :id")
    public void deleteDataById(String id);

    @Query("SELECT * FROM favoritedata order by `index` desc")
    public LiveData<List<FavoriteData>> getAllFavoriteData();

    @Query("SELECT * FROM FavoriteData where id == :quoteId")
    public LiveData<FavoriteData> getFavoriteData(String quoteId);
}
