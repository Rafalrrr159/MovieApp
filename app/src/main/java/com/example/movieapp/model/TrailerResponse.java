package com.example.movieapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class TrailerResponse {
    @SerializedName("results")
    ArrayList<Trailer> trailers;

    public ArrayList<Trailer> getTrailers() {
        return trailers;
    }
}
