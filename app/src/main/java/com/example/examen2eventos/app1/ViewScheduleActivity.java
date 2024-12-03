package com.example.examen2eventos.app1;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
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

        // Referencia a Firebase
        databaseReference = FirebaseDatabase.getInstance().getReference("Subjects");

        // Cargar datos desde Firebase
        loadSubjects();
    }

    private void loadSubjects() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                subjectsList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Subject subject = dataSnapshot.getValue(Subject.class);
                    if (subject != null) {
                        String details = subject.getName() + " - " + subject.getDays() + " - " + subject.getHours();
                        subjectsList.add(details);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.e("Firebase", "Error al cargar datos", error.toException());
            }
        });
    }
}
