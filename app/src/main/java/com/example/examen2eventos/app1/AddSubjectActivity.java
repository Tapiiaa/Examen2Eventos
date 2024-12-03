package com.example.examen2eventos.app1;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.examen2eventos.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddSubjectActivity extends AppCompatActivity {

    private EditText edtName, edtDays, edtHours;
    private Button btnSave;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subject);

        // Inicializar campos
        edtName = findViewById(R.id.edt_name);
        edtDays = findViewById(R.id.edt_days);
        edtHours = findViewById(R.id.edt_hours);
        btnSave = findViewById(R.id.btn_save);

        // Inicializar Firebase
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        databaseReference = FirebaseDatabase.getInstance("https://examen2eventos-default-rtdb.europe-west1.firebasedatabase.app")
                .getReference("Subjects");

        // Guardar datos al hacer clic en el botón
        btnSave.setOnClickListener(v -> saveSubject());
    }

    private void saveSubject() {
        String name = edtName.getText().toString().trim();
        String days = edtDays.getText().toString().trim();
        String hours = edtHours.getText().toString().trim();

        if (name.isEmpty() || days.isEmpty() || hours.isEmpty()) {
            Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Generar un ID único para la asignatura
        String id = databaseReference.push().getKey();

        if (id == null) {
            Toast.makeText(this, "Error generando el ID de la asignatura", Toast.LENGTH_SHORT).show();
            return;
        }

        // Crear objeto Subject
        Subject subject = new Subject(id, name, days, hours);

        // Guardar en Firebase
        databaseReference.child(id).setValue(subject)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "Asignatura guardada en Firebase", Toast.LENGTH_SHORT).show();
                        finish(); // Regresa a la actividad anterior
                    } else {
                        Log.e("Firebase", "Error al guardar en Firebase", task.getException());
                        Toast.makeText(this, "Error al guardar en Firebase", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("Firebase", "Fallo en la escritura: " + e.getMessage());
                    Toast.makeText(this, "Error en la conexión con Firebase", Toast.LENGTH_SHORT).show();
                });
    }
}
