package com.example.examen2eventos.app3;


import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class App3MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Redirigir directamente a la actividad del mapa
        Intent intent = new Intent(this, PharmacyMapActivity.class);
        startActivity(intent);
        finish();
    }
}
