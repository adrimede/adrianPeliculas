package com.example.examenandroid.Controllador;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.examenandroid.Database.DbPeliculas;
import com.example.examenandroid.Interfaces.IverDetalles;
import com.example.examenandroid.MainActivity;
import com.example.examenandroid.Model.MovieModelClass;
import com.example.examenandroid.R;
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

public class ControllerPeliculas extends MainActivity implements IverDetalles {

    RecyclerView peliculasPopulares;
    List<MovieModelClass> movieList = new ArrayList<>();
    ImageView imgCabezal;
    IverDetalles iverDetalle;
    String estado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peliculas_layout);

        iniComponent();
        EventComponent();
    }


    private void iniComponent() {
        peliculasPopulares = findViewById(R.id.ls_peliculasPopulares);
        imgCabezal = findViewById(R.id.img_cabezal);
        iverDetalle = this;
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
                    url = new URL(MainActivity.JSON_URL);
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

        //Obtengo lo que me devuelve la API
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

                    for (MovieModelClass movi : movieList) {
                        DbPeliculas dbPeliculas = new DbPeliculas(ControllerPeliculas.this);
                        long id = dbPeliculas.insertarPelicula(movi.getPeliculaId(), movi.getPeliculaNom(), movi.getPeliculaImg(),
                                movi.getPeliculaDesc());

                        if (id > 0) {
                            estado = "S";
                        } else {
                            estado = "N";
                        }

                    }
                    //Valido si se guardaron los datos en SQLLite
                    if (estado.equals("S")) {
                        toast("Datos Guardados");
                    } else {
                        toast("No se Guardaron Guardados");
                    }

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            PutDataIntoRecyclerView(movieList);
        }
    }

    //Carga del Adaptador
    private void PutDataIntoRecyclerView(List<MovieModelClass> movieList) {
        List<MovieModelClass> listMovieInt = new ArrayList<>();
        //Verifica si hay internet
        if (Utils.isOnlineNet()) {
            listMovieInt = movieList;
        } else {
            DbPeliculas dbPeliculas = new DbPeliculas(this);
            listMovieInt = dbPeliculas.mostrarPeliculas();
        }
        AdapterMovie adapterMovie = new AdapterMovie(this, listMovieInt, iverDetalle);
        peliculasPopulares.setLayoutManager(new LinearLayoutManager(this));
        peliculasPopulares.setAdapter(adapterMovie);
    }



    //Items del menu lateral
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_Home:
                goToPrincipal();
                return true;

            case R.id.menu_Peliculas:
                goToPeliculasPopulares();
                return true;

            case R.id.menu_Firestore:
                return true;
            case R.id.menu_Fotos:
                goToCamara();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
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
