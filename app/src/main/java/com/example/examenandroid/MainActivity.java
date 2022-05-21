package com.example.examenandroid;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.examenandroid.Controllador.ControllerDetallePelicula;
import com.example.examenandroid.Controllador.ControllerPeliculas;
import com.example.examenandroid.Controllador.ControllerPrincipal;
import com.example.examenandroid.Database._DBHelper;
import com.example.examenandroid.Globales.GlobalController;
import com.example.examenandroid.Interfaces.IverDetalles;
import com.example.examenandroid.Model.MovieModelClass;
import com.example.examenandroid.Model.MovieModelClassDS;
import com.example.examenandroid.Utils.Utils;
import com.example.examenandroid.adaptadores.AdapterMovie;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
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
        _DBHelper dbHelper=new _DBHelper(MainActivity.this);
        SQLiteDatabase db= dbHelper.getWritableDatabase();
        if(db!=null){

            Toast.makeText(this,"Base de datos creada",Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(this,"Error al crear la Base de datos",Toast.LENGTH_LONG).show();
        }
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
}