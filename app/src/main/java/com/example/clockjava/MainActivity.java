package com.example.clockjava;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;

import java.util.ArrayList;

/**
 * Main activity, from this activity starts working application
 */
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

    /**
     * If user wants to add new clock it clicks on {@link #addClockButton} and this code starts next activity.
     */
    private void addClock() {
        Intent newClockIntent = new Intent(this, NewClock.class);
        startActivityForResult(newClockIntent, 1);
    }

    /**
     * Method recreates recycler view if added or removed clock.
     *
     * @param resultCode 1 meas need redraw recyclerView
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //redraws recycler view
        if (resultCode == 1) {
            addRecyclerView();
        }
    }

    /**
     * This method adds recycler view of all clocks
     */
    private void addRecyclerView() {
        alarmArrayList = getAlarms();
        adapter = new ClockAdapter(this);

        alarmsRecyclerView = findViewById(R.id.alarms_list);
        alarmsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setAlarms(alarmArrayList);
        alarmsRecyclerView.setAdapter(adapter);
    }

    /**
     * Method takes from database all clocks.
     *
     * @return array of clocks (active and inactive)
     */
    private ArrayList<Alarm> getAlarms() {
        LocalDataBase localDataBase = LocalDataBase.init();
        return localDataBase.getAlarms();
    }
}
