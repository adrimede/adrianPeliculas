package com.example.examenandroid.Globales;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.examenandroid.R;


public class PopUpGenericoSimple extends Dialog implements View.OnClickListener{

    public Context c;
    public Button ok;
    public TipoTransaccionEnum tipoTransaccion;
    public String titulo;
    public String body;
    public String button;
    public TextView mensaje;
    public TextView titulos;


    public PopUpGenericoSimple(String titulo, String body, String boton, @NonNull Context context, TipoTransaccionEnum tipoTransaccion) {
        super(context);
        this.titulo=titulo;
        this.body=body;
        this.button=boton;
        this.c = context;
        this.tipoTransaccion=tipoTransaccion;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.mensaje_genericos_pop);

        iniComponentes();
        iniEventsComponentes();
        setearTextos();

    }

    private void setearTextos() {
        titulos.setText(titulo);
        mensaje.setText(body);
        ok.setText(button);
    }

    private void iniComponentes() {
        ok = findViewById(R.id.button_ok);
        titulos=findViewById(R.id.titulo_alert_dialog_bio);
        mensaje = findViewById(R.id.titulo_alert_dialog_bio2);

    }

    private void iniEventsComponentes() {
        ok.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_ok:
                switch (tipoTransaccion){
                    case DEFAULT:
                        dismiss();
                        break;
                }

        }
    }
}
