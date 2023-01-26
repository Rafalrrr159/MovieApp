package com.example.movieapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.Movie_info;
import com.example.movieapp.R;
import com.example.movieapp.model.Trailer;

import java.util.ArrayList;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.VideoViewHolder>{

    private Context context;
    private ArrayList<Trailer> trailers;

    public TrailerAdapter(Context context) {
        this.context = context;
        this.trailers = new ArrayList<>();
    }

    public void update(ArrayList<Trailer> trailers){

        this.trailers = trailers;
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.trailer_list,viewGroup,false);

        return new VideoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder videoViewHolder, int i) {
        videoViewHolder.textView.setText(trailers.get(i).getName());

    }

    @Override
    public int getItemCount() {
        return trailers.size();
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ConstraintLayout constraintLayout;
        private TextView textView;


        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            constraintLayout = itemView.findViewById(R.id.mov_trailer);
            textView = itemView.findViewById(R.id.trailer_name);
            constraintLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            int position = getAdapterPosition();
            Movie_info movie_info = (Movie_info) context;
            movie_info.clickTrailer(position);
        }

    }

}
