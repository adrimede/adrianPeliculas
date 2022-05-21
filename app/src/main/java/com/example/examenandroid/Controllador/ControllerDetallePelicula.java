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

import com.bumptech.glide.Glide;
import com.example.examenandroid.Globales.GlobalController;
import com.example.examenandroid.MainActivity;
import com.example.examenandroid.Model.MovieModelClass;
import com.example.examenandroid.R;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

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
//        Glide.with(context)
//                .load(moviePel.getPeliculaImg())
//                .error(R.mipmap.not_found_144)
//                .into(imgCabezal);
     //   String URl="https://www.google.com/imgres?imgurl=https%3A%2F%2Fcdn.pixabay.com%2Fphoto%2F2019%2F10%2F19%2F11%2F32%2Ffungus-4561194__340.jpg&imgrefurl=https%3A%2F%2Fpixabay.com%2Fes%2Fphotos%2Fsearch%2Fcinem%25C3%25A1ticas%2F&tbnid=dB_kdYk2OlwiAM&vet=12ahUKEwi8qee1ze73AhU_s5UCHcl9B00QMygGegUIARDHAQ..i&docid=d0iZ9q9yxm-GoM&w=226&h=340&itg=1&q=fotos%20cinematicas&ved=2ahUKEwi8qee1ze73AhU_s5UCHcl9B00QMygGegUIARDHAQ";
        Uri uri= Uri.parse("https://api.themoviedb.org"+moviePel.getPeliculaImg());
        Picasso.get()
                .load(R.drawable.cabezal_movie)
                .fit().into(imgCabezal);
        peliculaNomb.setText(moviePel.getPeliculaNom());
        peliculaDesc.setText(moviePel.getPeliculaDesc());

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_principal,menu);
        return true;
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

            case R.id.Firestore:
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

}
