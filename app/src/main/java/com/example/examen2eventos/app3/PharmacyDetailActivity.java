package com.example.examen2eventos.app3;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.examen2eventos.R;

public class PharmacyDetailActivity extends AppCompatActivity {

    private TextView txtName, txtAddress, txtPhone, txtLatitude, txtLongitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharmacy_detail);

        txtName = findViewById(R.id.txt_name);
        txtAddress = findViewById(R.id.txt_address);
        txtPhone = findViewById(R.id.txt_phone);
        txtLatitude = findViewById(R.id.txt_latitude);
        txtLongitude = findViewById(R.id.txt_longitude);

        txtName.setText(getIntent().getStringExtra("name"));
        txtAddress.setText(getIntent().getStringExtra("address"));
        txtPhone.setText(getIntent().getStringExtra("phone"));
        txtLatitude.setText(String.valueOf(getIntent().getDoubleExtra("latitude", 0)));
        txtLongitude.setText(String.valueOf(getIntent().getDoubleExtra("longitude", 0)));
    }
}

