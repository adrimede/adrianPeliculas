package com.example.examenandroid;

import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.examenandroid.Globales.GlobalController;
import com.example.examenandroid.Model.MovieModelClass;

public class ControllerDetallePelicula extends MainActivity {

    ImageView imgCabezal;
    TextView peliculaNomb;
    TextView peliculaDesc;
    MovieModelClass moviePel;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_pelicula);
//        if (getIntent().getSerializableExtra("movieSlector") != null) {
//            moviePel = (MovieModelClass) getIntent().getExtras().get("movieSlector");
//        }
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
        Glide.with(context)
                .load(moviePel.getPeliculaImg())
                .error(R.mipmap.not_found_144)
                .into(imgCabezal);

        peliculaNomb.setText(moviePel.getPeliculaNom());
        peliculaDesc.setText(moviePel.getPeliculaDesc());

    }


}
