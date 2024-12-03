package com.example.examen2eventos.app1;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.examen2eventos.R;
import com.example.examen2eventos.app1.Subject;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewScheduleActivity extends AppCompatActivity {

    private ListView listViewSubjects;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> subjectsList;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_schedule);

        listViewSubjects = findViewById(R.id.list_view_subjects);
        subjectsList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, subjectsList);
        listViewSubjects.setAdapter(adapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("Subjects");

        configureDayButtons();
    }

    private void configureDayButtons() {
        int[] buttonIds = {
                R.id.btn_monday,
                R.id.btn_tuesday,
                R.id.btn_wednesday,
                R.id.btn_thursday,
                R.id.btn_friday
        };

        String[] days = {"Lunes", "Martes", "Miércoles", "Jueves", "Viernes"};

        for (int i = 0; i < buttonIds.length; i++) {
            int finalI = i;
            findViewById(buttonIds[i]).setOnClickListener(v -> loadSubjectsForDay(days[finalI]));
        }
    }

    private void loadSubjectsForDay(String day) {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                subjectsList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Subject subject = dataSnapshot.getValue(Subject.class);
                    if (subject != null && subject.getDays().contains(day)) {
                        subjectsList.add(subject.getName() + " - " + subject.getHours());
                    }
                }
                adapter.notifyDataSetChanged();
                if (subjectsList.isEmpty()) {
                    subjectsList.add("No hay materias para " + day);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.e("Firebase", "Error al cargar datos", error.toException());
            }
        });
    }
}
