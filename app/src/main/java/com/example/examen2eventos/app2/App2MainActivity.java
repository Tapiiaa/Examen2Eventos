package com.example.examen2eventos.app2;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.examen2eventos.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class App2MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EventAdapter eventAdapter;
    private List<Event> eventList;
    private DatabaseReference databaseReference;
    private Button btnRegisterEvent;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app2_main);

        recyclerView = findViewById(R.id.recycler_view_events);
        btnRegisterEvent = findViewById(R.id.btn_register_event);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        eventList = new ArrayList<>();
        eventAdapter = new EventAdapter(this, eventList);
        recyclerView.setAdapter(eventAdapter);

        // Inicializar Firebase
        databaseReference = FirebaseDatabase.getInstance("https://examen2eventos-default-rtdb.firebaseio.com/").getReference("Events");

        loadEvents();

        // Redirigir a la actividad de registro
        btnRegisterEvent.setOnClickListener(v -> {
            Intent intent = new Intent(App2MainActivity.this, AddEventActivity.class);
            startActivity(intent);
        });
    }

    private void loadEvents() {
        databaseReference = FirebaseDatabase.getInstance("https://examen2eventos-default-rtdb.europe-west1.firebasedatabase.app")
                .getReference("Events");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                eventList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Event event = dataSnapshot.getValue(Event.class);
                    if (event != null) {
                        eventList.add(event);
                    }
                }

                if (eventList.isEmpty()) {
                    Toast.makeText(App2MainActivity.this, "No hay eventos registrados.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(App2MainActivity.this, "Eventos cargados correctamente.", Toast.LENGTH_SHORT).show();
                }

                eventAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(App2MainActivity.this, "Error al cargar eventos.", Toast.LENGTH_SHORT).show();
                Log.e("FirebaseError", "Error al cargar eventos: ", error.toException());
            }
        });
    }


    private void openEventDetail(Event event) {
        Intent intent = new Intent(this, EventDetailActivity.class);
        intent.putExtra("eventId", event.getId());
        startActivity(intent);
    }
}

