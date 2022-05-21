package com.example.examenandroid.Controllador;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;

import com.example.examenandroid.Database._DBHelper;
import com.example.examenandroid.MainActivity;
import com.example.examenandroid.R;

public class ControllerPrincipal extends MainActivity implements View.OnClickListener {

    Button btn_Peliculas;
    Button btn_Firebase;
    Button btn_BaseDatos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        iniComponents();
        iniEvenet();
    }

    private void iniComponents() {
        //Botones
        btn_Peliculas=findViewById(R.id.btn_Peliculas);
        btn_Firebase=findViewById(R.id.btn_Firebase);
        btn_BaseDatos=findViewById(R.id.btn_BaseDatos);
    }

    private void iniEvenet() {
        btn_Peliculas.setOnClickListener(this);
        btn_Firebase.setOnClickListener(this);
        btn_BaseDatos.setOnClickListener(this);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_principal,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_Home:
                goToPrincipal();
                return true;

            case R.id.menu_Peliculas:
                goToPeliculasPopulares();
                return true;

            case R.id.Firestore:
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_Peliculas:
                goToPeliculasPopulares();
                break;
            case R.id.btn_BaseDatos:
                generarBaseDeDatos();
                break;
            case R.id.btn_Firebase:
                GuardarUbicacion();
                break;

        }
    }

//Ejecuciòn cada 30 segundos
    public void GuardarUbicacion(){

        final Handler handler= new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                metodoEjecutar();//llamamos nuestro metodo
                handler.postDelayed(this,30000);//se ejecutara cada 10 segundos
            }

        },5000);//empezara a ejecutarse después de 5 milisegundos

    }


    private void metodoEjecutar() {

    }
    //Genera la abse de Datos
    public void generarBaseDeDatos(){
        _DBHelper dbHelper=new _DBHelper(ControllerPrincipal.this);
        SQLiteDatabase db= dbHelper.getWritableDatabase();
        if(db!=null){
            Toast.makeText(this,"Base de datos creada",Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(this,"Error al crear la Base de datos",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        ActionBar actionBar = this.getSupportActionBar();
        actionBar.setTitle(R.string.tituloHome);

    }
}
