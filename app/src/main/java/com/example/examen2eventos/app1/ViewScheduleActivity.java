package com.example.examen2eventos.app1;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.examen2eventos.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewScheduleActivity extends AppCompatActivity {

    private LinearLayout daysLayout;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_schedule);

        daysLayout = findViewById(R.id.days_layout);

        // Inicializar Firebase
        databaseReference = FirebaseDatabase.getInstance("https://examen2eventos-default-rtdb.europe-west1.firebasedatabase.app").getReference("Subjects");

        setupDayButtons();
    }

    private void setupDayButtons() {
        String[] days = {"Lunes", "Martes", "Miércoles", "Jueves", "Viernes"};

        for (String day : days) {
            Button dayButton = new Button(this);
            dayButton.setText(day);
            dayButton.setOnClickListener(v -> loadSubjectsForDay(day));
            daysLayout.addView(dayButton);
        }
    }

    private void loadSubjectsForDay(String day) {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                List<String> subjectsForDay = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Subject subject = dataSnapshot.getValue(Subject.class);
                    if (subject != null && subject.getDays().contains(day)) {
                        subjectsForDay.add(subject.getName() + " - " + subject.getHours());
                    }
                }

                showSubjectsDialog(day, subjectsForDay);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.e("Firebase", "Error al cargar datos", error.toException());
            }
        });
    }

    private void showSubjectsDialog(String day, List<String> subjects) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Asignaturas para " + day);

        if (subjects.isEmpty()) {
            builder.setMessage("No hay asignaturas registradas para este día.");
        } else {
            StringBuilder message = new StringBuilder();
            for (String subject : subjects) {
                message.append(subject).append("\n");
            }
            builder.setMessage(message.toString());
        }

        builder.setPositiveButton("Cerrar", (dialog, which) -> dialog.dismiss());
        builder.create().show();
    }
}
