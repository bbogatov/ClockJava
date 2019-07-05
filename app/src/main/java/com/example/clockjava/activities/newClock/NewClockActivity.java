package com.example.clockjava.activities.newClock;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.clockjava.R;

/**
 * Class used for creating new alarm clock.
 */
public class NewClockActivity extends AppCompatActivity implements NewClockContract.View {

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

    private NewClockContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_clock);

        presenter = new NewClockPresenter(this);

        timePicker = findViewById(R.id.time_picker_new_clock);
        timePicker.setIs24HourView(true);

        applyButton = findViewById(R.id.apply_button);
        applyButton.setOnClickListener((View v) -> presenter.applyButtonPressed(getString(R.string.time_format_string, timePicker.getCurrentHour(), timePicker.getCurrentMinute())));

        discardButton = findViewById(R.id.discard_button);
        discardButton.setOnClickListener((View v) -> presenter.closeButtonPressed());
    }


    /**
     * Method that closed current activity
     */
    @Override
    public void finishActivity() {
        finish();
    }

    /**
     * This method shows message when clock successfully added
     * @param time
     */
    @Override
    public void showMessage(String time) {
        Toast.makeText(this, "Added new clock " + time, Toast.LENGTH_SHORT).show();
    }
}


