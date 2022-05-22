package com.example.examenandroid.Controllador;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.ViewTreeObserver;

import com.example.examenandroid.MainActivity;
import com.example.examenandroid.Model.Ubicacion;
import com.example.examenandroid.R;
import com.example.examenandroid.databinding.ActivityControllerMapsBinding;
import com.google.android.gms.common.util.ArrayUtils;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.osmdroid.util.BoundingBox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ControllerMapsActivity extends MainActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityControllerMapsBinding binding;
    private FusedLocationProviderClient mFusedLocationClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityControllerMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        ActionBar actionBar = this.getSupportActionBar();
        actionBar.setTitle(R.string.tituloMapa);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // obtenerDatosFirestore();
        mMap = googleMap;
        marcadores(googleMap);
    }

    //Generar marcadores
    private void marcadores(GoogleMap googleMap) {
        if (!ubicacion.isEmpty()) {
            for (Ubicacion ubi : ubicacion) {
                LatLng ActualPosition = new LatLng(ubi.getLatitud(), ubi.getLongitud());
                mMap.addMarker(new MarkerOptions().position(ActualPosition).title("Marker in Actual"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(ActualPosition));
            }
        } else {
            LatLng ActualPosition = new LatLng(latitude, longitud);
            mMap.addMarker(new MarkerOptions().position(ActualPosition).title("Marker in Actual"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(ActualPosition));
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
            case R.id.menu_Mapa:
                goToMapa();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

}