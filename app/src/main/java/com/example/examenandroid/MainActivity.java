package com.example.examenandroid;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.examenandroid.Controllador.ControllerDetallePelicula;
import com.example.examenandroid.Globales.GlobalController;
import com.example.examenandroid.Interfaces.IverDetalles;
import com.example.examenandroid.Model.MovieModelClass;
import com.example.examenandroid.Model.MovieModelClassDS;
import com.example.examenandroid.Utils.Utils;
import com.example.examenandroid.adaptadores.AdapterMovie;

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
    private static String JSON_URL = "https://api.themoviedb.org/3/movie/popular?api_key=7abda88ea13e3fb5e0151f00800b753d";
    RecyclerView peliculasPopulares;
    List<MovieModelClass> movieList = new ArrayList<>();
    ImageView imgCabezal;
    private static String ultima_sincronizacion;
    IverDetalles iverDetalle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iniComponent();
        EventComponent();
    }


    private void iniComponent() {
        peliculasPopulares = findViewById(R.id.ls_peliculasPopulares);
        imgCabezal = findViewById(R.id.img_cabezal);
        iverDetalle=this;
    }

    private void EventComponent() {
        GetData getData = new GetData();
        getData.execute();


    }



    public class GetData extends AsyncTask<String, String, String> {


        @Override
        protected String doInBackground(String... strings) {
            String current = "";
            try {
                URL url;
                HttpURLConnection urlConnection = null;
                try {
                    url = new URL(JSON_URL);
                    urlConnection = (HttpURLConnection) url.openConnection();

                    InputStream is = urlConnection.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is);
                    int data = isr.read();
                    while (data != -1) {
                        current += (char) data;
                        data = isr.read();
                    }
                    return current;


                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return current;
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("results");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    MovieModelClass model = new MovieModelClass();
                    model.setPeliculaId(jsonObject1.getString("id"));
                    model.setPeliculaNom(jsonObject1.getString("original_title"));
                    model.setPeliculaImg(jsonObject1.getString("poster_path"));
                    model.setPeliculaDesc(jsonObject1.getString("overview"));
                    movieList.add(model);
                    MovieModelClassDS movi = new MovieModelClassDS();
                    movi.createPelicula(model);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            PutDataIntoRecyclerView(movieList);
        }
    }

    private void PutDataIntoRecyclerView(List<MovieModelClass> movieList) {
        List<MovieModelClass> listMovieInt = new ArrayList<>();
        //Verifica si hay internet
        if (Utils.isOnlineNet()) {
            listMovieInt = movieList;
        } else {
            MovieModelClassDS movi = new MovieModelClassDS();
            listMovieInt = movi.getAllMoviesBase();
        }
        AdapterMovie adapterMovie = new AdapterMovie(this, listMovieInt,iverDetalle);
        peliculasPopulares.setLayoutManager(new LinearLayoutManager(this));
        peliculasPopulares.setAdapter(adapterMovie);
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


    protected void goToDetalles(MovieModelClass moviesSeleccionada) {
        GlobalController.setMovieElegida(moviesSeleccionada);
        Intent intent = new Intent(this,
                ControllerDetallePelicula.class);
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
        actionBar.
    }
}