package com.example.examen2eventos.app1;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.examen2eventos.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class NowSubjectActivity extends AppCompatActivity {

    private TextView tvCurrentSubject, tvSubjectDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_now_subject);

        tvCurrentSubject = findViewById(R.id.tv_current_subject);
        tvSubjectDetails = findViewById(R.id.tv_subject_details);

        fetchCurrentSubject();
    }

    private void fetchCurrentSubject() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Subjects");

        String currentDay = new SimpleDateFormat("EEEE", Locale.getDefault()).format(Calendar.getInstance().getTime());
        String currentTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(Calendar.getInstance().getTime());

        databaseReference.get().addOnSuccessListener(snapshot -> {
            boolean found = false;
            for (DataSnapshot data : snapshot.getChildren()) {
                Subject subject = data.getValue(Subject.class);
                if (subject != null && subject.getDays().contains(currentDay) && subject.getHours().contains(currentTime)) {
                    tvCurrentSubject.setText(subject.getName());
                    tvSubjectDetails.setText("Días: " + subject.getDays() + "\nHorario: " + subject.getHours());
                    found = true;
                    break;
                }
            }
            if (!found) {
                tvCurrentSubject.setText("No hay materias en este momento.");
                tvSubjectDetails.setText("");
            }
        }).addOnFailureListener(e -> {
            tvCurrentSubject.setText("Error al obtener datos.");
            tvSubjectDetails.setText("");
        });
    }
}
