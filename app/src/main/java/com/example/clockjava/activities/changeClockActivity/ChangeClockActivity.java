package com.example.clockjava.activities.changeClockActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TimePicker;

import com.example.clockjava.R;

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
        //method that sets current alarm time on timepicker
        presenter.setAlarmTimeTimePicker(time, timePicker);
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                timeChanged = presenter.userChangesTime();
            }
        });


        applyButton = findViewById(R.id.active_clock_settings_image_button);
        applyButton.setOnClickListener((View v) ->
                presenter.applyButtonClicked(timeChanged,
                        getString(R.string.time_format_string, timePicker.getCurrentHour(), timePicker.getCurrentMinute()),
                        time, index));


        discardButton = findViewById(R.id.close_clock_settings_image_button);
        discardButton.setOnClickListener((View v) -> presenter.discardButtonClicked(timeChanged));


        deleteButton = findViewById(R.id.delete_button);
        deleteButton.setOnClickListener((View v) -> presenter.deleteButtonClicked());
    }

    /**
     * Method finish current activity.
     */
    @Override
    public void closeActivity() {
        finish();
    }

    /**
     * Method shows alert window that asks user does it really wants delete a clock.
     */
    @Override
    public void showDeleteAlertWindow() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this, R.style.Theme_AppCompat_Dialog_Alert);
        alertDialog.setCancelable(false);
        alertDialog.setMessage(R.string.delete_clock);

        //User clicks delete clock
        alertDialog.setPositiveButton(R.string.yes_chose, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                presenter.deleteClockPositiveButtonClicked(index, time);
            }
        });

        //User don't wanna delete button
        alertDialog.setNegativeButton(R.string.no_chose, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                presenter.deleteClockNegativeButtonClicked();
            }
        });

        alertDialog.show();
    }


    /**
     * When user made changes and click close button this code will run, and ask it does it wants make any changes.
     */
    @Override
    public void showAlertWindowSaveData() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this, R.style.Theme_AppCompat_Dialog_Alert);
        alertDialog.setCancelable(false);
        alertDialog.setMessage(R.string.change_data);

        alertDialog.setPositiveButton(R.string.yes_chose, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                presenter.positiveAlertButtonClicked(timeChanged,
                        getString(R.string.time_format_string, timePicker.getCurrentHour(), timePicker.getCurrentMinute()),
                        time, index);
            }
        });

        alertDialog.setNegativeButton(R.string.no_chose, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                presenter.negativeAlertButtonClicked();
            }
        });

        alertDialog.show();

    }
}
