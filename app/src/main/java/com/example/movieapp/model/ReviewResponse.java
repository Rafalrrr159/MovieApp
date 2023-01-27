package com.example.movieapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ReviewResponse {

    @SerializedName("results")
    ArrayList<Review> reviews;

    public ArrayList<Review> getReviews() {
        return reviews;
    }
}