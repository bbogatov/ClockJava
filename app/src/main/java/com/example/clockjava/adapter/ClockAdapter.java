package com.example.clockjava.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import com.example.clockjava.R;
import com.example.clockjava.activities.changeClockActivity.ChangeClockActivity;
import com.example.clockjava.database.ClockAlarm;
import com.example.clockjava.database.LocalDataBase;
import com.example.clockjava.logger.Logger;
import com.example.clockjava.observerInterfaces.Observer;

import java.util.ArrayList;


/**
 * This class used for creating array of clocks on screen using RecyclerView class.
 * Class created as singleton pattern.
 */
public class ClockAdapter extends RecyclerView.Adapter<ClockAdapter.AlarmHolder> implements Observer {

    /**
     * This is static object used for creating singleton pattern.
     */
    private static ClockAdapter clockAdapter;

    /**
     * List of alarm clocks from database
     */
    private ArrayList<ClockAlarm> clockAlarms;

    /**
     * Activity where need show recycler view
     */
    private Activity activity;

    private ClockAdapter(Activity activity) {
        LocalDataBase localDataBase = LocalDataBase.getInstance();
        localDataBase.addObserver(this);
        this.activity = activity;
    }

    /**
     * Main method that returns initialized recyclerView.
     *
     * @return recycler view that response for showing list of clocks on the main activity.
     */
    public static ClockAdapter getInstance(Activity activity) {
        if (clockAdapter == null) {
            clockAdapter = new ClockAdapter(activity);
        }
        return clockAdapter;
    }

    @NonNull
    @Override
    public AlarmHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.recycler_view_item, viewGroup, false);

        return new AlarmHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlarmHolder alarmHolder, int i) {
        alarmHolder.bind(clockAlarms.get(i));
    }

    /**
     * Returns quantity of clockAlarms
     *
     * @return clockAlarms quantity
     */
    @Override
    public int getItemCount() {
        return clockAlarms.size();
    }

    /**
     * This class takes array of clocks and shows them on screen
     *
     * @param clockAlarms array of clocks
     */
    public void setClockAlarms(ArrayList<ClockAlarm> clockAlarms) {
        this.clockAlarms = clockAlarms;
        notifyDataSetChanged();
    }

    @Override
    public void handleEvent(ArrayList<ClockAlarm> clockAlarms) {
        this.clockAlarms = clockAlarms;
        notifyDataSetChanged();
    }

    /**
     * Class that add one clock element to the screen
     */
    class AlarmHolder extends RecyclerView.ViewHolder implements ClockAdapterContract.View {

        private Switch aSwitch;
        private TextView textView;
        private long index;
        private ClockListPresenter presenter;

        /**
         * Shows clocks elements on screen
         */
        AlarmHolder(@NonNull View itemView) {
            super(itemView);

            presenter = new ClockListPresenter(this);

            aSwitch = itemView.findViewById(R.id.alarm_switcher);
            textView = itemView.findViewById(R.id.alarm_time_text_view);

            aSwitch.setOnClickListener((View v) -> presenter.switchPressed(index, aSwitch.isChecked(), textView.getText().toString()));

            textView.setOnClickListener((View v) -> presenter.textViewPressed());
        }


        /**
         * When user want change alarm time or delete clock, user pressed this button
         */
        @Override
        public void changeAlarmTime() {
            Intent intent = new Intent(activity, ChangeClockActivity.class);
            intent.putExtra("time", textView.getText().toString());
            intent.putExtra("index", index);
            intent.putExtra("switch", aSwitch.isChecked());
            activity.startActivity(intent);
        }

        /**
         * Method sets clocks values from database to current elements on the screen
         *
         * @param clockAlarm clock clockAlarm signal that need to add on the screen
         */
        void bind(ClockAlarm clockAlarm) {
            aSwitch.setChecked(clockAlarm.getEnable());
            textView.setText(clockAlarm.getTime());
            index = clockAlarm.getId();
        }
    }
}

