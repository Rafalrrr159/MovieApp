package com.example.movieapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MovieContainer {
    @SerializedName("results")
    ArrayList<Movie> movies;


    public ArrayList<Movie> getMovies() {
        return movies;
    }
}
