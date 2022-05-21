package com.example.examenandroid.Controllador;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;

import com.example.examenandroid.MainActivity;
import com.example.examenandroid.R;

public class ControllerPrincipal extends MainActivity implements View.OnClickListener {

    Button btn_Peliculas;
    Button btn_Firebase;

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
    }

    private void iniEvenet() {
        btn_Peliculas.setOnClickListener(this);
        btn_Firebase.setOnClickListener(this);
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
            case R.id.btn_Firebase:

                break;

        }
    }

    @Override
    public void onResume() {
        super.onResume();

        ActionBar actionBar = this.getSupportActionBar();
        actionBar.setTitle(R.string.tituloHome);

    }
}
