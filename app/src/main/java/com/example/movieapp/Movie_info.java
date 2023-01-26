package com.example.movieapp;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.movieapp.adapter.TrailerAdapter;
//import com.example.movieapp.databinding.ActivityMovieInfoBinding;
import com.example.movieapp.databinding.ActivityMovieInfoBinding;

import com.example.movieapp.adapter.RecommendationAdapter;
import com.example.movieapp.architecture.MovieWatchedEntity;
import com.example.movieapp.architecture.WatchedViewModel;
import com.example.movieapp.model.Movie;
import com.example.movieapp.model.Trailer;
import com.example.movieapp.retrofit.MovieViewModel;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Movie_info extends AppCompatActivity implements View.OnClickListener, OnClickMovie, OnClickTrailer {
    private Movie movie;
    private ActivityMovieInfoBinding movieInfoBinding;
    private String shortString;
    private boolean shortStringEnabled = true;
    public static volatile boolean isWatched = true;
    public static ArrayList<Trailer> trailers;
    public static ArrayList<Movie> movies;
    public static TrailerAdapter trailerAdapter;
    public static RecommendationAdapter movieAdapter;
    private MovieViewModel movieViewModel;
    private WatchedViewModel watchedViewModel;
    public static volatile List<MovieWatchedEntity> movieWatchedEntities;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        movieInfoBinding = DataBindingUtil.setContentView(this, R.layout.activity_movie_info);
        setContentView(movieInfoBinding.getRoot());
        movieInfoBinding.readMore.setOnClickListener(this);
        movieInfoBinding.myWatched.setOnClickListener(this);

        movieWatchedEntities = new ArrayList<>();

        trailerAdapter = new TrailerAdapter(this);
        movieInfoBinding.movieTrailers.setAdapter(trailerAdapter);
        movieInfoBinding.movieTrailers.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        int span;
        int width = (int) Resources.getSystem().getDisplayMetrics().widthPixels;

        if (width <= 1080) span = 2;
        else span = 3;


        movieAdapter = new RecommendationAdapter(this, (int) width / span + 70);
        movieInfoBinding.movieRecommendations.setAdapter(movieAdapter);
        movieInfoBinding.movieRecommendations.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));


        movieViewModel = new MovieViewModel(this);


        watchedViewModel = new ViewModelProvider(this).get(WatchedViewModel.class);


    }

    @Override
    protected void onResume() {
        super.onResume();

        Gson gson = new Gson();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {


            movie = gson.fromJson(bundle.getString("movie"), Movie.class);

            movieViewModel.getTrailers(movie.getId(), getResources().getString(R.string.api_key), getResources().getString(R.string.language2));

            movieViewModel.getSimilar(movie.getId(), getResources().getString(R.string.api_key), getResources().getString(R.string.language));


            movieInfoBinding.movieLikes.setText(movie.getVote_count() + "");
            movieInfoBinding.movieRatings.setText(movie.getVote_average() + "");
            shortString = getShortDescription(movie.getOverview());
            if (shortString.equals(movie.getOverview()))
                movieInfoBinding.readMore.setVisibility(View.GONE);
            movieInfoBinding.movieDes.setText(shortString);
            movieInfoBinding.movieTitle.setText(movie.getTitle());
            Picasso.get().load(MainActivity.BASE_IMAGE_URL + movie.getPoster_path())
                    .into(movieInfoBinding.movieImg);


            if (!isWatched) movieInfoBinding.myWatched.setImageResource(R.drawable.like);
            else movieInfoBinding.myWatched.setImageResource(R.drawable.star);

        } else {

            Toast.makeText(this, "Something went wrong..", Toast.LENGTH_SHORT).show();

        }

    }

    @Override
    public void onClick(View v) {

        int id = v.getId();
        if (id == R.id.read_more) {
            if (shortStringEnabled) {
                movieInfoBinding.movieDes.setText(movie.getOverview());
                movieInfoBinding.readMore.setText("Show less");
            } else {
                movieInfoBinding.movieDes.setText(shortString);
                movieInfoBinding.readMore.setText("Read more");
            }

            shortStringEnabled = !shortStringEnabled;
        } else if (id == R.id.my_watched) {

            if (isWatched) {
                watchedViewModel.add(new MovieWatchedEntity(movie.getVote_count(), movie.getId(), movie.getVote_average(), movie.getTitle(), movie.getPoster_path(), movie.getOriginal_language(), movie.getOriginal_title(), movie.getOverview(), movie.getRelease_date()));
                movieInfoBinding.myWatched.setImageResource(R.drawable.like);
                Toast.makeText(this, "Marked as Watched", Toast.LENGTH_SHORT).show();


            } else {
                watchedViewModel.remove(movie.getId());
                movieInfoBinding.myWatched.setImageResource(R.drawable.star);
                Toast.makeText(this, "Removed from Watched", Toast.LENGTH_SHORT).show();
            }
            isWatched = !isWatched;
        }
    }

    private String getShortDescription(String des) {

        Matcher matcher = Pattern.compile(".*?\\.").matcher(des);
        if (matcher.find()) {
            return matcher.group(0);
        } else
            return "";
    }

    @Override
    public void click(int position) {


        Gson gson = new Gson();
        Intent intent = new Intent(Movie_info.this, Movie_info.class);
        String movie = gson.toJson(movies.get(position));
        intent.putExtra("movie", movie);
        startActivity(intent);


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        isWatched = true;
        finish();
    }

    @Override
    public void clickTrailer(int position) {

        Intent youtube = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + trailers.get(position).getKey()));
        Intent web = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=" + trailers.get(position).getKey()));

        try {
            try {
                startActivity(youtube);
            } catch (ActivityNotFoundException e) {
                startActivity(web);
            }
        } catch (Exception e) {
            Toast.makeText(this, "You don't have proper application installed in your phone to play this video", Toast.LENGTH_LONG).show();
        }
    }
}
