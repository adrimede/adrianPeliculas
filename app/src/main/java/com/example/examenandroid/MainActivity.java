package com.example.examenandroid;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.examenandroid.Controllador.ControllerDetallePelicula;
import com.example.examenandroid.Controllador.ControllerPeliculas;
import com.example.examenandroid.Controllador.ControllerPrincipal;
import com.example.examenandroid.Globales.GlobalController;
import com.example.examenandroid.Interfaces.IverDetalles;
import com.example.examenandroid.Model.MovieModelClass;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements IverDetalles {

    //Link de API peliculas populares
    public static String JSON_URL = "https://api.themoviedb.org/3/movie/popular?api_key=7abda88ea13e3fb5e0151f00800b753d";
    RecyclerView peliculasPopulares;
    List<MovieModelClass> movieList = new ArrayList<>();
    ImageView imgCabezal;
    private static String ultima_sincronizacion;
    IverDetalles iverDetalle;
   // private FirebaseFirestore db=new FirebaseFirestore();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iniComponent();
        EventComponent();
    }


    private void iniComponent() {
    }

    private void EventComponent() {

    }

    public static String getUltima_Sincroizacion() {
        return ultima_sincronizacion;
    }

    public static void setUltima_Sincronizacion(String ultima_sincronizacion) {
        MainActivity.ultima_sincronizacion = ultima_sincronizacion;
    }

    public static void truncate() {
        ultima_sincronizacion = null;
    }

    //LLeva al controlador correspondiente
    protected void goToDetalles(MovieModelClass moviesSeleccionada) {
        GlobalController.setMovieElegida(moviesSeleccionada);
        Intent intent = new Intent(this,
                ControllerDetallePelicula.class);
        startActivity(intent);

    }

    //LLeva al controlador correspondiente
    protected void goToPeliculasPopulares() {
        Intent intent = new Intent(this,
                ControllerPeliculas.class);
        startActivity(intent);

    }

    //LLeva al controlador correspondiente
    protected void goToPrincipal() {
        Intent intent = new Intent(this,
                ControllerPrincipal.class);
        startActivity(intent);

    }
    @Override
    public void IrVerDetalles(MovieModelClass mov) {
        goToDetalles(mov);
    }
    //Pongo titulo a la vista
    @Override
    public void onResume() {
        super.onResume();

        ActionBar actionBar = this.getSupportActionBar();
        actionBar.setTitle(R.string.tituloPeliuclas);

    }

    public void toast(String msj){
        Toast.makeText(this,msj,Toast.LENGTH_LONG).show();

    }
}