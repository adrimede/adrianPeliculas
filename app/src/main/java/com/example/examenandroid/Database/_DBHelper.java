package com.example.examenandroid.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.examenandroid.MainActivity;


public class _DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "com.example.examenandroid.db";
    private static final int DATABASE_VERSION = 1;

    public _DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    protected void finalize() throws Throwable {
        this.close();
        super.finalize();
    }

    // Tabla Pelicula
    private static final String CREATE_Peliculas = "CREATE TABLE Pelicula (" +
            "PeliculaId INTEGER NOT NULL, " +
            "PeliculaNom TEXT NOT NULL, " +
            "PeliculaImg TEXT NOT NULL, " +
            "PeliculaDesc TEXT, " +

            "PRIMARY KEY (PeliculaId)" +
            ");";



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_Peliculas);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
       db.execSQL("DROP TABLE IF EXISTS Pelicula;");

        onCreate(db);
    }

    public void truncate(SQLiteDatabase db) {

        db.execSQL("DELETE FROM Pelicula;");


        // borro login status
        MainActivity.setUltima_Sincronizacion("");
        MainActivity.truncate();

    }
}