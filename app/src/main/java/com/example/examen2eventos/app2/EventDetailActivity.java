package com.example.examen2eventos.app2;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.examen2eventos.R;

public class EventDetailActivity extends AppCompatActivity {

    private TextView txtName, txtDescription, txtPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);

        txtName = findViewById(R.id.txt_name);
        txtDescription = findViewById(R.id.txt_description);
        txtPrice = findViewById(R.id.txt_price);

        // Obtener los datos del Intent
        String name = getIntent().getStringExtra("name");
        String description = getIntent().getStringExtra("description");
        double price = getIntent().getDoubleExtra("price", 0);

        // Mostrar los datos
        txtName.setText(name);
        txtDescription.setText(description);
        txtPrice.setText(String.format("$%.2f", price));
    }
}
