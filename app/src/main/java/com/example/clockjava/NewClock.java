package com.example.clockjava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TimePicker;
import android.widget.Toast;

/**
 * Class used for creating new alarm clock.
 */
public class NewClock extends AppCompatActivity {

    /**
     * Time picker user chose at which time clock should work
     */
    private TimePicker timePicker;

    /**
     * When user chose clock time he click this button to create new clock
     */
    private ImageButton applyButton;

    /**
     * If used don't wanna create new clock, he clicks this button
     */
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
        long newClockIndex = addNewClockDataBase(time);
        addNewAlarmManger(time, newClockIndex);

        Toast.makeText(this, "Added new clock " + time, Toast.LENGTH_SHORT).show();
        setResult(1);
        finish();
    }

    /**
     * Method creates new {@link android.app.AlarmManager} for {@code time}.
     *
     * @param time  time when starts clock alarm, should lock like this HH:mm for example 15:05 or 02:34
     *              using 24-hours format with leading zeroes
     * @param index index of new added clock in database
     */
    private void addNewAlarmManger(String time, long index) {
        ClockAlarmsManger clockAlarmsManger = new ClockAlarmsManger();
        clockAlarmsManger.addAlarmSignal(time, index);
    }

    /**
     * Method adds new clock to database.
     *
     * @param time time when starts clock alarm, should lock like this HH:mm for example 15:05 or 02:34
     *             using 24-hours format with leading zeroes
     * @return index of new added clock from database, this index auto increasing
     */
    private long addNewClockDataBase(String time) {
        LocalDataBase localDataBase = LocalDataBase.init();
        return localDataBase.addClock(time);
    }

}


