package com.example.movieapp;

import android.content.Intent;

import android.content.res.Resources;
import android.os.Bundle;

import com.example.movieapp.adapter.MovieAdapter;
import com.example.movieapp.architecture.WatchedViewModel;
import com.example.movieapp.model.Movie;
import com.example.movieapp.retrofit.MovieViewModel;

import androidx.appcompat.app.AppCompatActivity;


import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.databinding.ActivityMainBinding;
import com.google.gson.Gson;

import android.util.Log;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnClickMovie{

    public static final String BASE_MOVIEDB_URL = "https://api.themoviedb.org/";
    public static final String BASE_IMAGE_URL = "https://image.tmdb.org/t/p/original";
    private ActivityMainBinding binding;
    private RecyclerView recyclerView;
    public static MovieAdapter movieAdapter;
    private MovieViewModel movieViewModel;
    public static volatile ArrayList<Movie> movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        setContentView(binding.getRoot());

        movieViewModel = new MovieViewModel(this);
        recyclerView = binding.recyclerViewHomepage;

        renderUI("popular");
    }

    private void renderUI(String url){

        int span;
        int width = (int) Resources.getSystem().getDisplayMetrics().widthPixels;

        if(width<=1080) span=2;
        else span=3;


        movieAdapter = new MovieAdapter(this,(int)width/span-20);
        recyclerView.setAdapter(movieAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this,span));

        String API_KEY = getResources().getString(R.string.api_key);
        String language = getResources().getString(R.string.language);
        int page = 1 ; //default
        movieViewModel.getMovies(url,API_KEY,language,page);
    }

    @Override
    public void click(int position) {
        Gson gson = new Gson();
        Intent intent = new Intent(this,Movie_info.class);
        String movie = gson.toJson(movies.get(position));
        Log.d("MainActivity", String.valueOf(position));
        intent.putExtra("movie",movie);
        new ViewModelProvider(this).get(WatchedViewModel.class)
                .isValid(movies.get(position).getId());
        startActivity(intent);
    }
}