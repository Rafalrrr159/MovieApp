package com.example.movieapp.architecture;

import android.content.Context;
import android.support.annotation.NonNull;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {MovieWatchedEntity.class},version = 1)
public abstract class MovieRoomDb extends RoomDatabase {

    public abstract MovieWatchedDao getDao();

    private static MovieRoomDb movieRoomDb;

    public static synchronized MovieRoomDb getMovieRoomDb(Context context) {

        if(movieRoomDb==null){

            movieRoomDb = Room.databaseBuilder(context.getApplicationContext(),
                            MovieRoomDb.class,"movie_app")
                    .fallbackToDestructiveMigration()
                    .addCallback(callback)
                    .build();

        }

        return movieRoomDb;
    }

    private static RoomDatabase.Callback callback =
            new RoomDatabase.Callback(){

                @Override
                public void onOpen(@NonNull SupportSQLiteDatabase db) {
                    super.onOpen(db);
                }
            };


}