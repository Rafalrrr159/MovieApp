package com.example.movieapp.architecture;

import android.app.Application;
import android.support.annotation.NonNull;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class WatchedViewModel extends AndroidViewModel {

    private WatchedRepository watchedRepository;
    private LiveData<List<MovieWatchedEntity>> listLiveData;


    public WatchedViewModel(@NonNull Application application) {
        super(application);
        watchedRepository = new WatchedRepository(application);
        listLiveData = watchedRepository.getListLiveData();
    }

    public LiveData<List<MovieWatchedEntity>> getListLiveData(){

        return listLiveData;
    }


    public void add(MovieWatchedEntity movieWatchedEntity){

        watchedRepository.add(movieWatchedEntity);

    }

    public void remove(int mov_id){

        watchedRepository.remove(mov_id);

    }


    public void isValid(int mov_id){

        watchedRepository.isValid(mov_id);

    }
}
