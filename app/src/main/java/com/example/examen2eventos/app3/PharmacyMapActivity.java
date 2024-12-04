package com.example.examen2eventos.app3;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PharmacyMapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Button addPharmacyButton;
    private Marker selectedMarker;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    // Firebase Database Reference
    private DatabaseReference firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharmacy_map);

        // Inicializar Firebase Database
        firebaseDatabase = FirebaseDatabase.getInstance("https://examen2eventos-default-rtdb.europe-west1.firebasedatabase.app").getReference("Pharmacies");

        // Inicializar Places API
        Places.initialize(getApplicationContext(), "AIzaSyDA_2udnmh6p9-FNWRJo1gnsif1Q7aC8Iw");

        // Configurar el fragmento del mapa
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        // Configurar el botón para añadir farmacias
        addPharmacyButton = findViewById(R.id.addPharmacyButton);
        addPharmacyButton.setVisibility(View.GONE); // Ocultar al inicio
        addPharmacyButton.setOnClickListener(v -> addPharmacyToFirebase());
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        // Solicitar permisos de ubicación
        if (checkAndRequestPermissions()) {
            enableMyLocation();
        }

        // Centrar el mapa en Zaragoza
        LatLng zaragoza = new LatLng(41.6488226, -0.8890853);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(zaragoza, 13));

        // Mostrar farmacias reales en Zaragoza
        fetchRealPharmacies(zaragoza);

        // Configurar el clic en los marcadores
        mMap.setOnMarkerClickListener(marker -> {
            selectedMarker = marker;
            addPharmacyButton.setVisibility(View.VISIBLE);
            return false;
        });
    }

    private boolean checkAndRequestPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
            return false;
        }
        return true;
    }

    private void enableMyLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        }
    }

    private void fetchRealPharmacies(LatLng location) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            try {
                // URL del endpoint Nearby Search
                String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" +
                        location.latitude + "," + location.longitude +
                        "&radius=2000&type=pharmacy&key=AIzaSyDA_2udnmh6p9-FNWRJo1gnsif1Q7aC8Iw";

                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url(url).build();
                Response response = client.newCall(request).execute();

                if (response.isSuccessful() && response.body() != null) {
                    String responseBody = response.body().string();
                    JSONObject jsonObject = new JSONObject(responseBody);
                    JSONArray results = jsonObject.getJSONArray("results");

                    runOnUiThread(() -> {
                        for (int i = 0; i < results.length(); i++) {
                            try {
                                JSONObject place = results.getJSONObject(i);
                                String name = place.getString("name");
                                String address = place.getString("vicinity");
                                JSONObject locationObj = place.getJSONObject("geometry").getJSONObject("location");
                                LatLng pharmacyLocation = new LatLng(
                                        locationObj.getDouble("lat"),
                                        locationObj.getDouble("lng"));

                                // Agregar marcador al mapa
                                mMap.addMarker(new MarkerOptions()
                                        .position(pharmacyLocation)
                                        .title(name)
                                        .snippet(address));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } else {
                    runOnUiThread(() -> Toast.makeText(this, "Error al obtener farmacias", Toast.LENGTH_SHORT).show());
                }
            } catch (Exception e) {
                runOnUiThread(() -> Toast.makeText(this, "Error al realizar la solicitud", Toast.LENGTH_SHORT).show());
                e.printStackTrace();
            }
        });
    }

    private void addPharmacyToFirebase() {
        if (selectedMarker != null) {
            String name = selectedMarker.getTitle();
            String address = selectedMarker.getSnippet();
            LatLng location = selectedMarker.getPosition();

            // Crear un objeto de farmacia
            Pharmacy pharmacy = new Pharmacy(name, address, location.latitude, location.longitude);

            // Guardar en Firebase
            firebaseDatabase.push().setValue(pharmacy)
                    .addOnSuccessListener(aVoid -> Toast.makeText(this, "Farmacia añadida a Firebase", Toast.LENGTH_SHORT).show())
                    .addOnFailureListener(e -> Toast.makeText(this, "Error al añadir farmacia", Toast.LENGTH_SHORT).show());

            // Ocultar el botón
            addPharmacyButton.setVisibility(View.GONE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                enableMyLocation();
            } else {
                Toast.makeText(this, "Permiso de ubicación denegado", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
