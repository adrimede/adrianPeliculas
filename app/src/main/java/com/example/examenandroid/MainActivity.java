package com.example.examenandroid;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.examenandroid.Controllador.ControllerDetallePelicula;
import com.example.examenandroid.Controllador.ControllerFotos;
import com.example.examenandroid.Controllador.ControllerPeliculas;
import com.example.examenandroid.Controllador.ControllerPrincipal;
import com.example.examenandroid.Globales.GlobalController;
import com.example.examenandroid.Interfaces.IverDetalles;
import com.example.examenandroid.Model.MovieModelClass;
import com.google.firebase.firestore.FirebaseFirestore;


public class MainActivity extends AppCompatActivity implements IverDetalles {

    //Link de API peliculas populares
    public static String JSON_URL = "https://api.themoviedb.org/3/movie/popular?api_key=7abda88ea13e3fb5e0151f00800b753d";
    private static String ultima_sincronizacion;
    public static FirebaseFirestore mfirestore = FirebaseFirestore.getInstance();


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
        accesoAUbicacion();
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

    //LLeva al controlador correspondiente
    protected void goToCamara() {
        Intent intent = new Intent(this,
                ControllerFotos.class);
        startActivity(intent);

    }

//    //LLeva al controlador correspondiente
//    protected void goToGeolocalizacion() {
//        Intent intent = new Intent(this,
//                ControllerMapsActivity.class);
//        startActivity(intent);
//
//    }


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

    public void toast(String msj) {
        Toast.makeText(this, msj, Toast.LENGTH_LONG).show();

    }

    private void accesoAUbicacion() {
        int permiso = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
        if (permiso == PackageManager.PERMISSION_DENIED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {

            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        }
    }
}