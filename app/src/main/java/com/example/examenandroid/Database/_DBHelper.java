package com.example.examenandroid.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.examenandroid.MainActivity;


public class _DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Examen.db";
    private static final int DATABASE_VERSION = 2;
    public static final String TABLE_PELICULAS = "t_peliculas";

    public _DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    protected void finalize() throws Throwable {
        this.close();
        super.finalize();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tabla Pelicula
        db.execSQL("CREATE TABLE " + TABLE_PELICULAS + "(" +
                "PeliculaId INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "PeliculaNumero TEXT NOT NULL, " +
                "PeliculaNom TEXT NOT NULL, " +
                "PeliculaImg TEXT NOT NULL, " +
                "PeliculaDesc TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PELICULAS);
        onCreate(db);
    }

}