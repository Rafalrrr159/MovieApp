package com.example.movieapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.MainActivity;
import com.example.movieapp.R;
import com.example.movieapp.Watched;
import com.example.movieapp.architecture.MovieWatchedEntity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class WatchedAdapter extends RecyclerView.Adapter<WatchedAdapter.FavViewHolder>{

    private List<MovieWatchedEntity> movieWatchedEntities;
    private Context context;

    public WatchedAdapter(Context context) {
        this.context = context;
        movieWatchedEntities = new ArrayList<>();
    }

    public void update(List<MovieWatchedEntity> movieFavEntities){

        this.movieWatchedEntities = movieFavEntities;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FavViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.watched_movie_list,viewGroup,false);
        return new FavViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull FavViewHolder favViewHolder, int i) {

        Picasso.get().load(MainActivity.BASE_IMAGE_URL+movieWatchedEntities.get(i).getPoster_path())
                .into(favViewHolder.imageView);
        favViewHolder.likes.setText(movieWatchedEntities.get(i).getVote_count()+"");
        favViewHolder.ratings.setText(movieWatchedEntities.get(i).getVote_average()+"");
    }

    @Override
    public int getItemCount() {
        return movieWatchedEntities.size();
    }

    public class FavViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private CardView cardView;
        private ImageView imageView;
        private TextView ratings,likes;


        public FavViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.movie_view);
            imageView = itemView.findViewById(R.id.movie_img);
            ratings = itemView.findViewById(R.id.movie_ratings);
            likes = itemView.findViewById(R.id.movie_likes);
            cardView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            Watched watched = (Watched) context;
            watched.click(getAdapterPosition());

        }

    }


}