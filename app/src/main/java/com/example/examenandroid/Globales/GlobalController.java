package com.example.examenandroid.Globales;

import android.app.Application;

import com.example.examenandroid.Model.MovieModelClass;


public class GlobalController extends Application {
    private static GlobalController INSTANCE = null;

    // other instance variables can be here

    private GlobalController() {};

    public static GlobalController getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new GlobalController();
        }
        return(INSTANCE);
    }
    private static MovieModelClass MovieElegida;

    public static MovieModelClass getMovieElegida() {
        return MovieElegida;
    }

    public static void setMovieElegida(MovieModelClass movieElegida) {
        MovieElegida = movieElegida;
    }
}
