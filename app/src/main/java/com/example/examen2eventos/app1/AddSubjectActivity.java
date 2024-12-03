package com.example.examen2eventos.app1;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.examen2eventos.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddSubjectActivity extends AppCompatActivity {

    private EditText edtSubjectName, edtDays, edtHours;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subject);

        edtSubjectName = findViewById(R.id.edt_subject_name);
        edtDays = findViewById(R.id.edt_days);
        edtHours = findViewById(R.id.edt_hours);
        btnSave = findViewById(R.id.btn_save);

        btnSave.setOnClickListener(v -> {
            String name = edtSubjectName.getText().toString().trim();
            String days = edtDays.getText().toString().trim();
            String hours = edtHours.getText().toString().trim();

            if (name.isEmpty() || days.isEmpty() || hours.isEmpty()) {
                Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            saveSubjectToFirebase(name, days, hours);
        });
    }

    private void saveSubjectToFirebase(String name, String days, String hours) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Subjects");
        String id = databaseReference.push().getKey();

        Subject subject = new Subject(id, name, days, hours);
        databaseReference.child(id).setValue(subject)
                .addOnSuccessListener(aVoid -> Toast.makeText(this, "Materia guardada", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(this, "Error al guardar", Toast.LENGTH_SHORT).show());
    }
}
