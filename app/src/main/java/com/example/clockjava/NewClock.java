package com.example.clockjava;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TimePicker;
import android.widget.Toast;

public class NewClock extends AppCompatActivity {

    private TimePicker timePicker;
    private ImageButton applyButton;
    private ImageButton discardButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_clock);

        timePicker = findViewById(R.id.time_picker_new_clock);
        timePicker.setIs24HourView(true);

        applyButton = findViewById(R.id.apply_button);
        applyButton.setOnClickListener((View v) -> addNewClock());

        discardButton = findViewById(R.id.discard_button);
        discardButton.setOnClickListener((View v) -> finish());

    }


    /**
     * Method adds a new clock to database.
     */
    private void addNewClock() {
        String time = getString(R.string.time_format_string, timePicker.getCurrentHour(), timePicker.getCurrentMinute());
        LocalDataBase.addClock(time);
        Toast.makeText(this, "Added new clock " + time, Toast.LENGTH_LONG).show();
        finish();
    }

}


