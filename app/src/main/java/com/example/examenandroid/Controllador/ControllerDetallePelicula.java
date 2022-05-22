package com.example.examenandroid.Controllador;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.examenandroid.Globales.GlobalController;
import com.example.examenandroid.MainActivity;
import com.example.examenandroid.Model.MovieModelClass;
import com.example.examenandroid.R;
import com.squareup.picasso.Picasso;

public class ControllerDetallePelicula extends MainActivity {

    ImageView imgCabezal;
    TextView peliculaNomb;
    TextView peliculaDesc;
    MovieModelClass moviePel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_pelicula_layout);

        iniComponents();
        eventComponent();
    }

    private void iniComponents() {
        imgCabezal = findViewById(R.id.img_cabezal);
        peliculaNomb = findViewById(R.id.txt_nomPelicula);
        peliculaDesc = findViewById(R.id.txt_descPelicula);
    }

    private void eventComponent() {
        moviePel= GlobalController.getMovieElegida();
        //Libreria para mostrar la imagen
//        Glide.with(context)
//                .load(moviePel.getPeliculaImg())
//                .error(R.mipmap.not_found_144)
//                .into(imgCabezal);
       Uri uri= Uri.parse("https://api.themoviedb.org"+moviePel.getPeliculaImg());
        Picasso.get()
                .load(R.drawable.cabezal_movie)
                .fit().into(imgCabezal);
        peliculaNomb.setText(moviePel.getPeliculaNom());
        peliculaDesc.setText(moviePel.getPeliculaDesc());

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_Home:
                goToPrincipal();
                return true;
            case R.id.menu_Peliculas:
                goToPeliculasPopulares();
                return true;
            case R.id.menu_Fotos:
                goToCamara();
                return true;
            case R.id.menu_Mapa:
                goToMapa();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

}
