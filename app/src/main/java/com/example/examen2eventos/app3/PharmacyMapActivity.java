package com.example.examen2eventos.app3;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.example.examen2eventos.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PharmacyMapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private List<Pharmacy> pharmacyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharmacy_map);

        pharmacyList = new ArrayList<>();

        // Configurar el mapa
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        // Cargar farmacias desde Firebase
        loadPharmaciesFromFirebase();
    }

    private void loadPharmaciesFromFirebase() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Pharmacies");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                pharmacyList.clear();
                for (DataSnapshot data : snapshot.getChildren()) {
                    Pharmacy pharmacy = data.getValue(Pharmacy.class);
                    if (pharmacy != null) {
                        pharmacyList.add(pharmacy);
                    }
                }

                if (mMap != null) {
                    addMarkersToMap();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Manejar errores de Firebase
            }
        });
    }

    private void addMarkersToMap() {
        mMap.clear();
        for (Pharmacy pharmacy : pharmacyList) {
            LatLng location = new LatLng(pharmacy.getLatitude(), pharmacy.getLongitude());
            mMap.addMarker(new MarkerOptions()
                    .position(location)
                    .title(pharmacy.getName()));
        }

        // Centrar el mapa en Zaragoza
        LatLng zaragoza = new LatLng(41.6488226, -0.8890853);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(zaragoza, 13));
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        // Si ya se cargaron las farmacias, añadir los marcadores
        if (!pharmacyList.isEmpty()) {
            addMarkersToMap();
        }
    }
}

