package com.example.examenandroid.Controllador;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.examenandroid.Globales.PopUpGenericoSimple;
import com.example.examenandroid.Globales.TipoTransaccionEnum;
import com.example.examenandroid.MainActivity;
import com.example.examenandroid.R;
import com.example.examenandroid.Utils.Utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ControllerFotos extends MainActivity implements View.OnClickListener {

    Button btnCamera, btn_Galeria, btnSubmit;
    ImageView ivImage;
    public static int CAMERA_CODE = 101;
    public static int GALERIA_CODE = 10;
    Context context;
    int calidadImagen = 50;
    ConstraintLayout ly_empty;
    String imagenElegida = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fotos_layout);

        iniComponents();
        iniEvenets();
    }

    private void iniComponents() {
        //Botones
        btnCamera = findViewById(R.id.btnCamera);
        btn_Galeria = findViewById(R.id.btn_Galeria);
        btnSubmit = findViewById(R.id.btnSubmit);
        //Imagenes
        ivImage = findViewById(R.id.ivImage);
        //LinearLayout
        ly_empty = findViewById(R.id.ly_empty);
    }

    private void iniEvenets() {
        btnCamera.setOnClickListener(this);
        btn_Galeria.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        ActionBar actionBar = this.getSupportActionBar();
        actionBar.setTitle(R.string.ly_fotoTitulo);
        if (ivImage.getDrawable() == null) {
            ly_empty.setVisibility(View.VISIBLE);
            ivImage.setVisibility(View.GONE);
        } else {
            ly_empty.setVisibility(View.GONE);
            ivImage.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCamera:
                abrircamara();
                break;
            case R.id.btn_Galeria:
                abrirGleria();
                break;
            case R.id.btnSubmit:
                guardarFotoFirestore();
                break;
        }
    }

//Envio la foto a Firestore
    private void guardarFotoFirestore() {

        if (!imagenElegida.equals("")) {

            Map<String, Object> map = new HashMap<>();
            map.put("UrlFoto", imagenElegida);

            mfirestore.collection("Fotos").document().set(map);
            PopUpGenericoSimple pop = new PopUpGenericoSimple(getString(R.string.txt_fotoGurdada), getString(R.string.txt_fotoGurdadaSub)+" "+imagenElegida+" "+getString(R.string.txt_fotoGurdadaSub2), getString(R.string.btn_Aceptar), this, TipoTransaccionEnum.DEFAULT);
            pop.show();
        }else {
            PopUpGenericoSimple pop = new PopUpGenericoSimple(getString(R.string.txt_fotoVacia), getString(R.string.txt_fotoVaciaSub), getString(R.string.btn_Aceptar), this, TipoTransaccionEnum.DEFAULT);
            pop.show();

        }
    }

//Abrir galeria
    private void abrirGleria() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        startActivityForResult(intent, GALERIA_CODE);
    }

//Abrir camara
    public void abrircamara() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA_CODE);
    }

//Mostrar camara
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_CODE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imgBitmap = (Bitmap) extras.get("data");
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            imgBitmap.compress(Bitmap.CompressFormat.JPEG, calidadImagen, bos);
            try {
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            File loaclPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            String root = loaclPath.getPath();
            imagenElegida = root+"/"+(System.currentTimeMillis() / 100) + ".jpg";
            //Seteo la foto al ImageView
            ivImage.setImageBitmap(imgBitmap);

        } else if (requestCode == GALERIA_CODE && resultCode == RESULT_OK) {
            File loaclPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            String root = loaclPath.getPath();
            imagenElegida = root+"/" +(System.currentTimeMillis() / 100) + ".jpg";
            Uri myUri = data.getData();
            //Seteo la foto al ImageView
            ivImage.setImageURI(myUri);
        }
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
            case R.id.menu_Fotos:
                goToCamara();
                return true;
            case R.id.menu_Firestore:
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }
}
