package com.example.movieapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.movieapp.adapter.WatchedAdapter;
import com.example.movieapp.architecture.MovieWatchedEntity;
import com.example.movieapp.architecture.WatchedViewModel;
import com.example.movieapp.databinding.ActivityWatchedBinding;
import com.example.movieapp.model.Movie;
import com.google.gson.Gson;

import java.util.List;

public class Watched extends AppCompatActivity implements OnClickMovie{

    private ActivityWatchedBinding activityWatchedBinding;
    private WatchedAdapter watchedAdapter;
    private WatchedViewModel watchedViewModel;
    public static List<MovieWatchedEntity> movieWatchedEntities;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityWatchedBinding = DataBindingUtil.setContentView(this, R.layout.activity_watched);
        setContentView(activityWatchedBinding.getRoot());

        watchedAdapter = new WatchedAdapter(this);
        activityWatchedBinding.recyclerWatched.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        activityWatchedBinding.recyclerWatched.setAdapter(watchedAdapter);


        //set the toolbar
        setSupportActionBar(activityWatchedBinding.toolBar);
        activityWatchedBinding.toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(Watched.this, MainActivity.class));
                finish();

            }
        });

        //set the view model
        watchedViewModel = new ViewModelProvider(this).get(WatchedViewModel.class);
        watchedViewModel.getListLiveData().observe(this, new Observer<List<MovieWatchedEntity>>() {
            @Override
            public void onChanged(@Nullable List<MovieWatchedEntity> movieWatchedEntities) {

                if (movieWatchedEntities != null) {


                    Watched.movieWatchedEntities = movieWatchedEntities;
                    watchedAdapter.update(movieWatchedEntities);

                }

            }
        });


    }

    @Override
    public void click(int position) {


        Gson gson = new Gson();
        Intent intent = new Intent(this,Movie_info.class);
        MovieWatchedEntity mo = movieWatchedEntities.get(position);
        Movie movie = new Movie(mo.getVote_count(),mo.getMovie_id(),mo.getVote_average(),mo.getTitle(),mo.getPoster_path(),mo.getOriginal_language(),mo.getOriginal_title(),new int[3],mo.getOverview(),mo.getRelease_date());
        String m = gson.toJson(movie);
        intent.putExtra("movie",m);
        new ViewModelProvider(this).get(WatchedViewModel.class)
                .isValid(movie.getId());
        startActivity(intent);
    }
}