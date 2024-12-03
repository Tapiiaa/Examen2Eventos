package com.example.examen2eventos.app1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.examen2eventos.R;

public class App1MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app1_main);

        Button btnAddSubject = findViewById(R.id.btn_add_subject);
        Button btnViewSchedule = findViewById(R.id.btn_view_schedule);
        Button btnNowSubject = findViewById(R.id.btn_now_subject);

        btnAddSubject.setOnClickListener(v -> startActivity(new Intent(this, AddSubjectActivity.class)));
        btnViewSchedule.setOnClickListener(v -> startActivity(new Intent(this, ViewScheduleActivity.class)));
        btnNowSubject.setOnClickListener(v -> startActivity(new Intent(this, NowSubjectActivity.class)));
    }
}
