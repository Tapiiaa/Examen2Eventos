package com.example.examen2eventos.app1;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.examen2eventos.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class NowSubjectActivity extends AppCompatActivity {

    private TextView txtNowSubject;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_now_subject);

        txtNowSubject = findViewById(R.id.txt_now_subject);

        // Inicializar Firebase con la URL completa
        databaseReference = FirebaseDatabase.getInstance("https://examen2eventos-default-rtdb.europe-west1.firebasedatabase.app")
                .getReference("Subjects");

        // Cargar y mostrar la última asignatura
        loadLastSubject();
    }

    private void loadLastSubject() {
        databaseReference.orderByKey().limitToLast(1).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Subject subject = dataSnapshot.getValue(Subject.class);
                        if (subject != null) {
                            txtNowSubject.setText("Última asignatura añadida:\n" +
                                    "Nombre: " + subject.getName() + "\n" +
                                    "Días: " + subject.getDays() + "\n" +
                                    "Horas: " + subject.getHours());
                            return;
                        }
                    }
                } else {
                    txtNowSubject.setText("No hay asignaturas registradas en Firebase.");
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.e("Firebase", "Error al cargar datos", error.toException());
                txtNowSubject.setText("Error al cargar los datos.");
            }
        });
    }
}


