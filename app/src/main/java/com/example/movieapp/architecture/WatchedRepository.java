package com.example.movieapp.architecture;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.movieapp.Movie_info;

import java.util.List;

public class WatchedRepository {

    private Context context;
    private LiveData<List<MovieWatchedEntity>> listLiveData;
    private MovieWatchedDao movieWatchedDao;

    public WatchedRepository(Context context) {

        this.context = context;
        movieWatchedDao = MovieRoomDb.getMovieRoomDb(context).getDao();
        listLiveData = movieWatchedDao.getAll();
    }

    public LiveData<List<MovieWatchedEntity>> getListLiveData() {

        return listLiveData;
    }


    public void add(MovieWatchedEntity movieWatchedEntity) {

        new Add(movieWatchedDao).execute(movieWatchedEntity);

    }

    public void remove(int mov_id) {

        new Remove(movieWatchedDao).execute(mov_id);

    }

    public void isValid(int mov_id){

        new Get(movieWatchedDao).execute(mov_id);

    }

    private class Get extends AsyncTask<Integer,Void,Void> {

        private MovieWatchedDao movieWatchedDao;

        public Get(MovieWatchedDao movieWatchedDao) {
            this.movieWatchedDao = movieWatchedDao;
        }

        @Override
        protected Void doInBackground(Integer... integers) {

            int count = movieWatchedDao.isValid(integers[0]);
            if(count!=0) Movie_info.isWatched = false;
            return null;
        }
    }

    private class Add extends AsyncTask<MovieWatchedEntity, Void, Void> {

        private MovieWatchedDao movieWatchedDao;

        public Add(MovieWatchedDao movieWatchedDao) {
            this.movieWatchedDao = movieWatchedDao;
        }

        @Override
        protected Void doInBackground(MovieWatchedEntity... movieWatchedEntities) {
            this.movieWatchedDao.add(movieWatchedEntities[0]);
            return null;
        }
    }

    private class Remove extends AsyncTask<Integer, Void, Void> {

        private MovieWatchedDao movieWatchedDao;

        public Remove(MovieWatchedDao movieWatchedDao) {
            this.movieWatchedDao = movieWatchedDao;
        }

        @Override
        protected Void doInBackground(Integer... integers) {
            this.movieWatchedDao.remove(integers[0]);
            return null;
        }
    }
}
