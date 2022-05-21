package com.example.examenandroid.Controllador;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
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
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.examenandroid.Database._DBHelper;
import com.example.examenandroid.MainActivity;
import com.example.examenandroid.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ControllerPrincipal extends MainActivity implements View.OnClickListener {

    Button btn_Peliculas;
    Button btn_Firebase;
    Button btn_BaseDatos;
    private FusedLocationProviderClient mFusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        iniComponents();
        iniEvent();
    }

    private void iniComponents() {
        //Botones
        btn_Peliculas = findViewById(R.id.btn_Peliculas);
        btn_Firebase = findViewById(R.id.btn_Firebase);
        btn_BaseDatos = findViewById(R.id.btn_BaseDatos);
    }

    private void iniEvent() {
        btn_Peliculas.setOnClickListener(this);
        btn_Firebase.setOnClickListener(this);
        btn_BaseDatos.setOnClickListener(this);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
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
        switch (v.getId()) {
            case R.id.btn_Peliculas:
                goToPeliculasPopulares();
                break;
            case R.id.btn_BaseDatos:
                generarBaseDeDatos();
                break;
            case R.id.btn_Firebase:
             //   goToGeolocalizacion();
                 GuardarUbicacion();
                break;

        }
    }

    //Ejecuciòn cada 30 segundos
    public void GuardarUbicacion() {

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                metodoEjecutar();//llamamos nuestro metodo
                handler.postDelayed(this, 30000);//se ejecutara cada 10 segundos
            }

        }, 5000);//empezara a ejecutarse después de 5 milisegundos

    }


    private void metodoEjecutar() {

        int MY_PERMISSIONS_REQUEST_READ_CONTACTS=0 ;
//Permisos para FINE LOCATION
            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(ControllerPrincipal.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);

                return;
            }
            //Obteniendo la ubicacion
            mFusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            //Sabiendo que obtuvimos location, lo usamos
                            if (location != null) {
                                double latitude = location.getLatitude();
                                double longitud = location.getLongitude();
                                Map<String,Object> map=new HashMap<>();
                                map.put("latitud",latitude);
                                map.put("longitud",longitud);
                             //   mfirestore.collection("Ubicacion").document().set(map);
                                mfirestore.collection("Ubicacion")
                                        .add(map)
                                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                            @Override
                                            public void onSuccess(DocumentReference documentReference) {
                                                toast("Ubicaciòn guardada");  }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                              toast("No se pudo guardar la ubicacion");
                                            }
                                        });
                            }
                        }
                    });

    }

    //Genera la abse de Datos
    public void generarBaseDeDatos() {
        _DBHelper dbHelper = new _DBHelper(ControllerPrincipal.this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (db != null) {
            Toast.makeText(this, "Base de datos creada", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Error al crear la Base de datos", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        ActionBar actionBar = this.getSupportActionBar();
        actionBar.setTitle(R.string.tituloHome);

    }
}
