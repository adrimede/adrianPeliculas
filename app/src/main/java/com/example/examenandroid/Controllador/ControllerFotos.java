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
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.examenandroid.MainActivity;
import com.example.examenandroid.R;
import com.example.examenandroid.Utils.Utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class ControllerFotos extends MainActivity implements View.OnClickListener {

    Button btnCamera, btn_Galeria, btnSubmit;
    ImageView ivImage;
    private Bitmap bitmap, bitmapRotate;
    private String newFileName = "";
    String imagepath = "";
    Uri imageUri;
    public static int CAMERA_CODE = 101;
    public static int GALERIA_CODE = 10;
    Context context;
    int calidadImagen = 50;
    File file;
    ConstraintLayout ly_empty;
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
        if(ivImage.getDrawable() == null){
            ly_empty.setVisibility(View.VISIBLE);
            ivImage.setVisibility(View.GONE);
        }else{
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
                break;
        }
    }

    private void abrirGleria() {
        Intent intent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
    //    startActivityForResult(intent.createChooser(intent, "Seleccione la aplicacion"), GALERIA_CODE);
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
            ivImage.setImageBitmap(imgBitmap);
        }else  if (requestCode == GALERIA_CODE && resultCode == RESULT_OK) {
//            Bundle extras = data.getExtras();
//            Bitmap imgBitmap = (Bitmap) extras.get("data");
//           // ivImage.setImageBitmap(imgBitmap);
//            File loaclPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
//            String root = loaclPath.getAbsolutePath();
//            //  Uri myUri = (Uri.parse(root + "/" + alArchivo.get(position).getArchNomFile()));
//            Uri myUri = (Uri.parse(root + "/" + imgBitmap));
//            ivImage.setImageTintList(null);
            Uri myUri=data.getData();
            ivImage.setImageURI(myUri);
        }
    }


}
