package com.example.examen2eventos.app1;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.examen2eventos.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ViewScheduleActivity extends AppCompatActivity {

    private RecyclerView rvSchedule;
    private ScheduleAdapter adapter;
    private List<Subject> subjectList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_schedule);

        rvSchedule = findViewById(R.id.rv_schedule);
        rvSchedule.setLayoutManager(new LinearLayoutManager(this));

        subjectList = new ArrayList<>();
        adapter = new ScheduleAdapter(subjectList);
        rvSchedule.setAdapter(adapter);

        fetchSchedule();
    }

    private void fetchSchedule() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Subjects");

        databaseReference.get().addOnSuccessListener(snapshot -> {
            subjectList.clear();
            for (DataSnapshot data : snapshot.getChildren()) {
                Subject subject = data.getValue(Subject.class);
                if (subject != null) {
                    subjectList.add(subject);
                }
            }
            adapter.notifyDataSetChanged();
        }).addOnFailureListener(e -> {
            // Manejar errores aquí si es necesario
        });
    }
}
