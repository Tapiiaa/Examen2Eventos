package com.example.examen2eventos.app2;


import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.examen2eventos.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddEventActivity extends AppCompatActivity {

    private EditText edtName, edtDescription, edtPrice;
    private Button btnSave;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        // Vincular vistas
        edtName = findViewById(R.id.edt_name);
        edtDescription = findViewById(R.id.edt_description);
        edtPrice = findViewById(R.id.edt_price);
        btnSave = findViewById(R.id.btn_save);

        // Inicializar referencia a Firebase
        databaseReference = FirebaseDatabase.getInstance("https://examen2eventos-default-rtdb.europe-west1.firebasedatabase.app")
                .getReference("Events");

        // Configurar acción del botón Guardar
        btnSave.setOnClickListener(v -> saveEvent());
    }

    private void saveEvent() {
        String name = edtName.getText().toString().trim();
        String description = edtDescription.getText().toString().trim();
        String priceText = edtPrice.getText().toString().trim();

        if (name.isEmpty() || description.isEmpty() || priceText.isEmpty()) {
            Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        double price;
        try {
            price = Double.parseDouble(priceText);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Introduce un precio válido", Toast.LENGTH_SHORT).show();
            return;
        }

        String id = databaseReference.push().getKey();
        if (id == null) {
            Toast.makeText(this, "Error al generar ID del evento", Toast.LENGTH_SHORT).show();
            return;
        }

        Event event = new Event(id, name, description, price);

        databaseReference.child(id).setValue(event)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "Evento registrado con éxito", Toast.LENGTH_SHORT).show();
                        Log.d("FirebaseDebug", "Evento registrado: " + event);
                        finish();
                    } else {
                        Toast.makeText(this, "Error al guardar el evento.", Toast.LENGTH_SHORT).show();
                        Log.e("FirebaseError", "Error al guardar evento: ", task.getException());
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Fallo al guardar el evento: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("FirebaseError", "Fallo al guardar evento: " + e.getMessage());
                });
    }


}
