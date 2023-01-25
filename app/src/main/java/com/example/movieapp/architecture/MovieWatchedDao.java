package com.example.movieapp.architecture;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MovieWatchedDao {

    @Insert
    public void add(MovieWatchedEntity movieWatchedEntity);

    @Query("DELETE FROM watched_table WHERE movie_id=:movie_id")
    public abstract void remove(int movie_id);

    @Query("SELECT * FROM watched_table ORDER BY id DESC")
    public abstract LiveData<List<MovieWatchedEntity>> getAll();

    @Query("SELECT COUNT(*) FROM watched_table WHERE movie_id=:mov_id")
    public int isValid(int mov_id);
}
