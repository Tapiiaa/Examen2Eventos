package com.example.examen2eventos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.examen2eventos.app1.App1MainActivity;
import com.example.examen2eventos.app2.App2MainActivity;
import com.example.examen2eventos.app3.App3MainActivity;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnApp1 = findViewById(R.id.btn_app1);
        Button btnApp2 = findViewById(R.id.btn_app2);
        Button btnApp3 = findViewById(R.id.btn_app3);

        btnApp1.setOnClickListener(v -> startActivity(new Intent(this, App1MainActivity.class)));
        btnApp2.setOnClickListener(v -> startActivity(new Intent(this, App2MainActivity.class)));
        btnApp3.setOnClickListener(v -> startActivity(new Intent(this, App3MainActivity.class)));
    }
}
