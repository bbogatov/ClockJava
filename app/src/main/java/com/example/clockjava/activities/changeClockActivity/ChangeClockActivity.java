package com.example.clockjava.activities.changeClockActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TimePicker;

import com.example.clockjava.R;
import com.example.clockjava.managers.ClockAlarmsManger;
import com.example.clockjava.database.LocalDataBase;


//TODO провернить что бы не возвращало результат setResult() или оно должно обрабатывать результат


/**
 * This class used for changing clock time or deleting clock.
 */
public class ChangeClockActivity extends AppCompatActivity implements ChangeClockContract.View {

    /**
     * Button that applies changes
     */
    private ImageButton applyButton;

    /**
     * Button that discards changes
     */
    private ImageButton discardButton;

    /**
     * TimePicker where user chose new clock time
     */
    private TimePicker timePicker;

    /**
     * Button that response for deleting clock
     */
    private Button deleteButton;

    /**
     * Current clock time which user wants to change
     */
    private String time;

    /**
     * Index of element in database
     */
    private long index;

    /**
     * If user changes time this boolean becomes true and main activity will redraw if user applies changes
     */
    private boolean timeChanged;


    private ChangeClockContract.Presenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_clock);

        presenter = new ChangeClockPresenter(this);

        Intent intent = getIntent();
        time = intent.getStringExtra("time");
        index = intent.getLongExtra("index", 0);

        timePicker = findViewById(R.id.change_clock_time_picker);
        timePicker.setIs24HourView(true);
        setAlertTime(timePicker, time);

        applyButton = findViewById(R.id.active_clock_settings_image_button);
        applyButton.setOnClickListener((View v) -> confirmTime());

        discardButton = findViewById(R.id.close_clock_settings_image_button);
        discardButton.setOnClickListener((View v) -> closeButtonAction());


        deleteButton = findViewById(R.id.delete_button);
        deleteButton.setOnClickListener((View v) -> deleteClock());
    }

    /**
     * If user wants to make a change, clicks apply button.
     * If timePicker has changes at time will run method {@link #changeTime()}
     * If there was no changes {@link #finish()} method runs.
     */
    private void confirmTime() {
        if (timeChanged) {

            changeTime();
            setResult(1);
            finish();
        } else {
            finish();
        }
    }

    /**
     * Method that changes alarm time in database
     */
    private void changeTime() {
        String newTime = getString(R.string.time_format_string, timePicker.getCurrentHour(), timePicker.getCurrentMinute());

        LocalDataBase localDataBase = LocalDataBase.init();
        localDataBase.changeTime(index, newTime);
        localDataBase.changeSwitch(index, true);

        ClockAlarmsManger clockAlarmsManger = new ClockAlarmsManger();
        clockAlarmsManger.changeAlarm(time, newTime, index);
    }

    /**
     * If user wants delete a clock, this method shows alert window to ask user does user sure.
     * If user sure it deletes clock and {@link ClockAlarmsManger}.
     */
    private void deleteClock() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this, R.style.Theme_AppCompat_Dialog_Alert);
        alertDialog.setCancelable(false);
        alertDialog.setMessage(R.string.delete_clock);

        alertDialog.setPositiveButton(R.string.yes_chose, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                LocalDataBase localDataBase = LocalDataBase.init();
                localDataBase.removeClock(index);

                ClockAlarmsManger clockAlarmsManger = new ClockAlarmsManger();
                clockAlarmsManger.removeAlarm(time, index);

                setResult(1);
                finish();
            }
        });

        alertDialog.setNegativeButton(R.string.no_chose, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        alertDialog.show();
    }

    /**
     * Method set alert time in timePicker. Because timePicker starts working with current system time.
     *
     * @param timePicker current timePicker on screen
     * @param time       alert time of clock
     */
    private void setAlertTime(TimePicker timePicker, String time) {
        int hours = Integer.valueOf(time.substring(0, 2));
        int minutes = Integer.valueOf(time.substring(3, 5));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            timePicker.setHour(hours);
        } else {
            timePicker.setCurrentHour(hours);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            timePicker.setMinute(minutes);
        } else {
            timePicker.setCurrentMinute(minutes);
        }

        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                timeChanged = true;
            }
        });
    }

    /**
     * If user click close button this method runs.
     * There two cases if user change time and not.
     * In first case alert window asks user does he want to make a change, if not main activity starts.
     * If user wants to make a change in data base changes value to new one, and returns main activity.
     */
    private void closeButtonAction() {

        if (timeChanged) {

            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this, R.style.Theme_AppCompat_Dialog_Alert);
            alertDialog.setCancelable(false);
            alertDialog.setMessage(R.string.change_data);

            alertDialog.setPositiveButton(R.string.yes_chose, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    changeTime();
                }
            });

            alertDialog.setNegativeButton(R.string.no_chose, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });

            alertDialog.show();
        } else {
            finish();
        }
    }
}
