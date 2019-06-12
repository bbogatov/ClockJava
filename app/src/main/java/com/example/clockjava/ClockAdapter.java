package com.example.clockjava;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * This class used for creating array of clocks on screen using RecyclerView class
 */
public class ClockAdapter extends RecyclerView.Adapter<ClockAdapter.AlarmHolder> {

    private ArrayList<Alarm> alarms;
    private Activity activity;

    ClockAdapter(Activity activity) {
        this.activity = activity;
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
        alarmHolder.bind(alarms.get(i));
    }

    /**
     * Returns quantity of alarms
     *
     * @return alarms quantity
     */
    @Override
    public int getItemCount() {
        return alarms.size();
    }

    /**
     * This class takes array of clocks and shows them on screen
     * @param alarms array of clocks
     */
    void setAlarms(ArrayList<Alarm> alarms) {
        this.alarms = alarms;
        notifyDataSetChanged();
    }

    /**
     * Class that add one clock element to the screen
     */
    class AlarmHolder extends RecyclerView.ViewHolder {

        private Switch aSwitch;
        private TextView textView;
        private long index;

        /**
         * Shows clocks elements on screen
         *
         */
        AlarmHolder(@NonNull View itemView) {
            super(itemView);
            aSwitch = itemView.findViewById(R.id.alarm_switcher);
            textView = itemView.findViewById(R.id.alarm_time_text_view);

            aSwitch.setOnClickListener((View v) -> changeSwitch());
            textView.setOnClickListener((View v) -> changeAlarm());
        }

        /**
         * If user changes switch on the screen this code changes values in database,
         * and adds or removes AlarmManger that response for this clock.
         */
        private void changeSwitch() {
            LocalDataBase localDataBase = LocalDataBase.init();
            localDataBase.changeSwitch(index, aSwitch.isChecked());

            ClockAlarmsManger clockAlarmsManger = new ClockAlarmsManger();
            clockAlarmsManger.changeSwitch(aSwitch.isChecked(), index, textView.getText().toString());
        }

        /**
         * When user want change alarm time or delete clock, user pressed this button
         */
        private void changeAlarm() {
            Intent intent = new Intent(activity, ChangeClockActivity.class);
            intent.putExtra("time", textView.getText().toString());
            intent.putExtra("index", index);
            intent.putExtra("switch", aSwitch.isChecked());
            activity.startActivityForResult(intent, 1);
        }

        /**
         * Method sets clocks values from database to current elements on the screen
         *
         * @param alarm clock alarm signal that need to add on the screen
         */
        void bind(Alarm alarm) {
            aSwitch.setChecked(alarm.getEnable());
            textView.setText(alarm.getTime());
            index = alarm.getIndex();
        }
    }
}
