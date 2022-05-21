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
import android.widget.Switch;

import androidx.annotation.Nullable;

import com.example.examenandroid.MainActivity;
import com.example.examenandroid.R;
import com.example.examenandroid.Utils.Utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class ControllerFotos extends MainActivity implements View.OnClickListener {

    Button btnCamera, btn_btnGaleria, btnSubmit;
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
        btn_btnGaleria = findViewById(R.id.btn_btnGaleria);
        btnSubmit = findViewById(R.id.btnSubmit);
        //Imagenes
        ivImage = findViewById(R.id.ivImage);
    }

    private void iniEvenets() {
        btnCamera.setOnClickListener(this);
        btn_btnGaleria.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCamera:
                abrircamara();
                break;
            case R.id.btn_btnGaleria:
                abrirGleria();
                break;
            case R.id.btnSubmit:
                break;
        }
    }

    private void abrirGleria() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
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
            ivImage.setImageBitmap(imgBitmap);
        }else  if (requestCode == GALERIA_CODE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imgBitmap = (Bitmap) extras.get("data");
            ivImage.setImageBitmap(imgBitmap);
        }
    }


}
