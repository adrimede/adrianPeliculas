package com.example.examenandroid.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.google.firebase.firestore.model.Values;

public class DbPeliculas extends _DBHelper {

    Context context;

    public DbPeliculas(Context context) {
        super(context);
        this.context=context;
    }

    public long insertarPelicula(String peliculaId,String peliculaNom,String peliculaImg,String peliculaDesc){

        long id=0;
      try {
       _DBHelper dbHelper=new _DBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values=new ContentValues();
        values.put("PeliculaId",peliculaId);
        values.put("PeliculaNom",peliculaNom);
        values.put("PeliculaImg",peliculaImg);
        values.put("PeliculaDesc",peliculaDesc);
         id = db.insert(CREATE_Peliculas,null, values);
      }catch (Exception ex){
          ex.toString();
      }
        return id;
    }
}
