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
import com.google.android.libraries.places.api.net.PlacesClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PharmacyMapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharmacy_map);

        // Inicializar Places API
        Places.initialize(getApplicationContext(), "AIzaSyDA_2udnmh6p9-FNWRJo1gnsif1Q7aC8Iw");

        // Configurar el fragmento del mapa
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
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
