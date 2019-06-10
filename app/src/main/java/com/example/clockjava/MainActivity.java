package com.example.clockjava;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ImageButton addClockButton;
    private RecyclerView alarmsRecyclerView;
    private ClockAdapter adapter;
    private ArrayList<Alarm> alarmArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Logger.log("Create main activity");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        addClockButton = findViewById(R.id.clock_button);

        addClockButton.setOnClickListener((View v) -> addClock());
        addRecyclerView();

    }

    private void addClock() {
        Intent newClockIntent = new Intent(this, NewClock.class);
        startActivity(newClockIntent);
    }



    private void addRecyclerView() {

        alarmArrayList = getAlarms();
        adapter = new ClockAdapter();

        alarmsRecyclerView = findViewById(R.id.alarms_list);
        alarmsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setAlarms(alarmArrayList);
        alarmsRecyclerView.setAdapter(adapter);
    }

    private ArrayList<Alarm> getAlarms() {

        ArrayList<Alarm> alarmArrayList = new ArrayList<>();
        alarmArrayList.add(new Alarm(1L, "12:05", true));
        alarmArrayList.add(new Alarm(2L, "13:05", true));
        alarmArrayList.add(new Alarm(3L, "14:05", true));
        alarmArrayList.add(new Alarm(4L, "15:05", true));
        alarmArrayList.add(new Alarm(4L, "15:05", true));
        alarmArrayList.add(new Alarm(4L, "15:05", true));
        alarmArrayList.add(new Alarm(4L, "15:05", true));
        alarmArrayList.add(new Alarm(4L, "15:05", true));
        alarmArrayList.add(new Alarm(4L, "15:05", true));
        alarmArrayList.add(new Alarm(4L, "15:05", true));
        alarmArrayList.add(new Alarm(4L, "15:05", true));
        alarmArrayList.add(new Alarm(4L, "15:05", true));
        alarmArrayList.add(new Alarm(4L, "15:05", true));
        alarmArrayList.add(new Alarm(4L, "15:05", true));
        alarmArrayList.add(new Alarm(4L, "15:05", true));
        alarmArrayList.add(new Alarm(4L, "15:05", true));
        alarmArrayList.add(new Alarm(4L, "15:05", true));
        alarmArrayList.add(new Alarm(4L, "15:05", true));
        alarmArrayList.add(new Alarm(4L, "15:05", true));
        alarmArrayList.add(new Alarm(4L, "15:05", true));
        alarmArrayList.add(new Alarm(4L, "15:05", true));
        alarmArrayList.add(new Alarm(4L, "15:05", true));
        alarmArrayList.add(new Alarm(4L, "15:05", true));
        alarmArrayList.add(new Alarm(4L, "15:05", true));
        alarmArrayList.add(new Alarm(4L, "15:05", true));
        alarmArrayList.add(new Alarm(4L, "15:05", true));
        alarmArrayList.add(new Alarm(4L, "15:05", true));
        alarmArrayList.add(new Alarm(4L, "15:05", true));
        alarmArrayList.add(new Alarm(4L, "15:05", true));
        alarmArrayList.add(new Alarm(4L, "15:05", true));

        return alarmArrayList;
    }


}
