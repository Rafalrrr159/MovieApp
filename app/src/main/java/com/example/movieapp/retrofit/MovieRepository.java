package com.example.movieapp.retrofit;

import android.content.Context;
import android.widget.Toast;

import com.example.movieapp.MainActivity;
import com.example.movieapp.Movie_info;
import com.example.movieapp.model.Movie;
import com.example.movieapp.model.MovieContainer;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieRepository {
    private Context context;

    private final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(MainActivity.BASE_MOVIEDB_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private final MovieDB movieDB = retrofit.create(MovieDB.class);


    public MovieRepository(Context context) {

        this.context = context;
    }


    public void getMovies(String filter_type, String api_key, String language, int page) {

        Call<MovieContainer> call = movieDB.getMovies(filter_type, api_key, language, page);


        call.enqueue(new Callback<MovieContainer>() {
            @Override
            public void onResponse(Call<MovieContainer> call, Response<MovieContainer> response) {

                if (response.isSuccessful() && response.code() == 200) {

                    MainActivity mainActivity = (MainActivity) context;
                    ArrayList<Movie> movies = response.body().getMovies();
                    if (movies != null) {
                        mainActivity.movieAdapter.update(response.body().getMovies());
                        mainActivity.movies = movies;
                    } else
                        Toast.makeText(mainActivity, "Something went wrong!", Toast.LENGTH_LONG).show();

                }

            }

            @Override
            public void onFailure(Call<MovieContainer> call, Throwable t) {

            }
        });


    }

    public void getSimilar(int id, String api_key, String language) {

        Call<MovieContainer> call = movieDB.getSimilar(id, api_key, language);

        call.enqueue(new Callback<MovieContainer>() {
            @Override
            public void onResponse(Call<MovieContainer> call, Response<MovieContainer> response) {

                if(response.isSuccessful() && response.code()==200){

                    Movie_info movie_info = (Movie_info) context;
                    ArrayList<Movie> movies = response.body().getMovies();
                    if(movies!=null){

                        movie_info.movies = movies;
                        movie_info.movieAdapter.update(movies);

                    }


                }

            }

            @Override
            public void onFailure(Call<MovieContainer> call, Throwable t) {

            }
        });


    }
}
