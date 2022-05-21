package com.example.examenandroid.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.examenandroid.Model.MovieModelClass;
import com.google.firebase.firestore.model.Values;

import java.util.ArrayList;
import java.util.List;

public class DbPeliculas extends _DBHelper {

    Context context;

    public DbPeliculas(Context context) {
        super(context);
        this.context = context;
    }

    public long insertarPelicula(String peliculaNum, String peliculaNom, String peliculaImg, String peliculaDesc) {

        long id = 0;
        try {
            _DBHelper dbHelper = new _DBHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("PeliculaNumero", peliculaNum);
            values.put("PeliculaNom", peliculaNom);
            values.put("PeliculaImg", peliculaImg);
            values.put("PeliculaDesc", peliculaDesc);
            id = db.insert(TABLE_PELICULAS, null, values);
        } catch (Exception ex) {
            ex.toString();
        }
        return id;
    }


    public List<MovieModelClass> mostrarPeliculas() {
        _DBHelper dbHelper = new _DBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        List<MovieModelClass> listaPleiculas = new ArrayList<>();

        MovieModelClass peliculas = null;
        Cursor cursorPeliculas = null;

        cursorPeliculas = db.rawQuery("select * from "+TABLE_PELICULAS, null);

        if (cursorPeliculas.moveToFirst()) {
            do {
                peliculas = new MovieModelClass();
                peliculas.setPeliculaId(cursorPeliculas.getString(0));
                peliculas.setPeliculaNumero(cursorPeliculas.getString(1));
                peliculas.setPeliculaNom(cursorPeliculas.getString(2));
                peliculas.setPeliculaImg(cursorPeliculas.getString(3));
                peliculas.setPeliculaDesc(cursorPeliculas.getString(4));
                listaPleiculas.add(peliculas);
            } while (cursorPeliculas.moveToNext());
        }
        cursorPeliculas.close();

        return listaPleiculas;
    }
}
