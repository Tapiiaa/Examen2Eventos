package com.example.examen2eventos.app3;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.examen2eventos.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest;
import com.google.android.libraries.places.api.net.FindCurrentPlaceResponse;
import com.google.android.libraries.places.api.net.PlacesClient;

import java.util.Arrays;

public class PharmacyMapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private PlacesClient placesClient;

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharmacy_map);

        // Inicializar Places API con la clave de API
        Places.initialize(getApplicationContext(), "AIzaSyDA_2udnmh6p9-FNWRJo1gnsif1Q7aC8Iw");
        placesClient = Places.createClient(this);

        // Configurar el mapa
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        // Verificar y solicitar permisos de ubicación
        if (checkAndRequestPermissions()) {
            enableMyLocation();
        }

        // Centrar el mapa en Zaragoza
        LatLng zaragoza = new LatLng(41.6488226, -0.8890853);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(zaragoza, 13));

        // Buscar farmacias en Zaragoza
        searchPharmacies();
    }

    private boolean checkAndRequestPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
            return false;
        }
        return true;
    }

    private void enableMyLocation() {
        try {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mMap.setMyLocationEnabled(true);
            }
        } catch (SecurityException e) {
            Toast.makeText(this, "Error al habilitar la ubicación: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void searchPharmacies() {
        // Crear una solicitud para encontrar lugares
        FindCurrentPlaceRequest request = FindCurrentPlaceRequest.newInstance(
                Arrays.asList(Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.TYPES));

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        placesClient.findCurrentPlace(request)
                .addOnSuccessListener((FindCurrentPlaceResponse response) -> {
                    for (com.google.android.libraries.places.api.model.PlaceLikelihood placeLikelihood : response.getPlaceLikelihoods()) {
                        Place place = placeLikelihood.getPlace();
                        if (place.getTypes() != null && place.getTypes().contains(Place.Type.PHARMACY)) {
                            LatLng location = place.getLatLng();
                            if (location != null) {
                                mMap.addMarker(new MarkerOptions()
                                        .position(location)
                                        .title(place.getName()));
                            }
                        }
                    }
                })
                .addOnFailureListener((exception) -> {
                    Toast.makeText(this, "Error al cargar farmacias: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults); // Asegurar super llamada
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                enableMyLocation();
            } else {
                Toast.makeText(this, "Permiso de ubicación denegado", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
