package com.example.examen2eventos.app1;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.examen2eventos.R;

public class NowSubjectActivity extends AppCompatActivity {

    private TextView txtNowSubject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_now_subject);

        txtNowSubject = findViewById(R.id.txt_now_subject);

        // Aquí puedes agregar lógica para mostrar la asignatura actual según la hora
        txtNowSubject.setText("No hay asignaturas en curso ahora mismo.");
    }
}

