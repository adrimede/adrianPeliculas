package com.example.examenandroid.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.examenandroid.Model.MovieModelClass;
import com.example.examenandroid.Model.MovieModelClassDS;
import com.example.examenandroid.R;

import java.util.List;

public class AdapterMovie extends RecyclerView.Adapter<AdapterMovie.MyViewHolder> {
    private Context mContext;
    private List<MovieModelClass> mListMovie;

    public AdapterMovie(Context mContext, List<MovieModelClass> mListMovie) {
        this.mContext = mContext;
        this.mListMovie = mListMovie;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.movie_item_laoyut, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        MovieModelClass movieModel=mListMovie.get(position);

       holder.id.setText(movieModel.getPeliculaId());
       holder.name.setText(movieModel.getPeliculaNom());

        //Libreria para mostrar la imagen
        Glide.with(mContext)
                .load(movieModel.getPeliculaImg())
                .error(R.mipmap.not_found_144)
                .into(holder.img);

    }


    @Override
    public int getItemCount() {
        return mListMovie.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView id;
        TextView name;
        ImageView img;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.txt_Id);
            name = itemView.findViewById(R.id.txt_nombre);
            img = itemView.findViewById(R.id.img_movie);
        }
    }
}
