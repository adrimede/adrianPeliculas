package com.example.examenandroid.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.examenandroid.Database._DBHelper;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MovieModelClassDS {
    public static final String tabla = "Pelicula";
    private SQLiteDatabase database;
    private _DBHelper dbHelper;
    Context cont;

    private String[] allMovieColumns = {"PeliculaId", "PeliculaNom", "PeliculaImg", "PeliculaDesc"
    };

    public MovieModelClassDS() {
        dbHelper = new _DBHelper(cont);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    private void openW() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    private void openR() throws SQLException {
        database = dbHelper.getReadableDatabase();
    }

    public void close() {
        dbHelper.close();
    }


    public boolean createPelicula(MovieModelClass o) {

        return createPelicula(o.getPeliculaId(), o.getPeliculaNom(), o.getPeliculaImg(), o.getPeliculaDesc());
    }

    public boolean createPelicula(String PeliculaId, String PeliculaNom, String PeliculaImg, String PeliculaDesc) {

        ContentValues values = new ContentValues();
        values.put("PeliculaId", PeliculaId);
        if (PeliculaNom != null) values.put("PeliculaNom", PeliculaNom.trim());
        if (PeliculaImg != null) values.put("PeliculaImg", PeliculaImg.trim());
        if (PeliculaDesc != null) values.put("PeliculaDesc", PeliculaDesc.trim());

        try {
            this.openW();
            if (database.replace(tabla, null, values) >= 0) {
                this.close();
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            this.close();
            e.printStackTrace();

            return false;
        }
    }
    public boolean deleteAllRepartoPedidoLinea() {
        List<MovieModelClass> mMovieModelClass = new ArrayList<MovieModelClass>();
        this.openW();
        database.execSQL("select * from Pelicula");
        this.close();
        return true;
    }
    private MovieModelClass cursorToMovie(Cursor cursor) {
        if (cursor.getCount() == 0) {
            return null;
        }
        MovieModelClass mMovieModelClass = new MovieModelClass();
        //EmpId, DepId, RepId, TpoDocId, DocUsuId, DocId, DocLineaId, RepLinCntVta, RepLinCntEnt, RepLinCntNoEnt, RepLinMotDevId,
        // RepLinFchEnt, RepPedLinModDt, syncFlag, syncErrorSet, SyncIntentos, ProdId, ProdDsc, DocLinUnimedId, DocLinImpCDCI, MonedaId, EstadoEntrega
        mMovieModelClass.setPeliculaId(cursor.getString(0));
        mMovieModelClass.setPeliculaNom(cursor.getString(1));
        mMovieModelClass.setPeliculaImg(cursor.getString(2));
        mMovieModelClass.setPeliculaDesc(cursor.getString(3));

        return mMovieModelClass;
    }

    public List<MovieModelClass> getAllMoviesBase() {
        List<MovieModelClass> mMovieModelClass = new ArrayList<MovieModelClass>();
        String parFilter;
        this.openR();
        Cursor cursor;
        cursor = database.query(tabla, allMovieColumns, null, null, null, null, "PeliculaNomb ASC");
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            MovieModelClass mMovieModelmov = cursorToMovie(cursor);
            mMovieModelClass.add(mMovieModelmov);
            cursor.moveToNext();
        }
        cursor.close();
        this.close();
        return mMovieModelClass;
    }
}
