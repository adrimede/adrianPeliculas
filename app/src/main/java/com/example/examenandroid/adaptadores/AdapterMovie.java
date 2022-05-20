package com.example.examenandroid.adaptadores;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.examenandroid.Interfaces.IverDetalles;
import com.example.examenandroid.Model.MovieModelClass;
import com.example.examenandroid.R;

import java.util.List;

public class AdapterMovie extends RecyclerView.Adapter<AdapterMovie.MyViewHolder> {
    private Context mContext;
     static List<MovieModelClass> mListMovie;
    static IverDetalles iverDetalle;
    static MovieModelClass movieModel;

    public AdapterMovie(Context mContext, List<MovieModelClass> mListMovie, IverDetalles iverDetalle) {
        this.mContext = mContext;
        this.mListMovie = mListMovie;
        this.iverDetalle = iverDetalle;
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
        movieModel = mListMovie.get(position);

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

    public static class MyViewHolder extends RecyclerView.ViewHolder  {
        TextView id;
        TextView name;
        ImageView img;
        RelativeLayout rl_itemMovie;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.txt_Id);
            name = itemView.findViewById(R.id.txt_nombre);
            img = itemView.findViewById(R.id.img_movie);
            rl_itemMovie = itemView.findViewById(R.id.rl_itemMovie);
            rl_itemMovie.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    MovieModelClass movieBundle = mListMovie.get(getAdapterPosition());
                    iverDetalle.IrVerDetalles(movieBundle);
                }

        });

        }
    }


}
