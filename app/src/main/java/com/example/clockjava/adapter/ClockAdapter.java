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
import com.example.clockjava.database.Alarm;

import java.util.ArrayList;

/**
 * This class used for creating array of clocks on screen using RecyclerView class
 */
public class ClockAdapter extends RecyclerView.Adapter<ClockAdapter.AlarmHolder> {

    private ArrayList<Alarm> alarms;
    private Activity activity;


    public ClockAdapter(Activity activity) {
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
     *
     * @param alarms array of clocks
     */
    public void setAlarms(ArrayList<Alarm> alarms) {
        this.alarms = alarms;
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


            aSwitch.setOnClickListener((View v) -> presenter.switchChanged(index, aSwitch.isChecked(), textView.getText().toString()));

            textView.setOnClickListener((View v) -> presenter.runChangeTimeActivity());
        }

        //TODO убрать старт активити фор ризалт
        /**
         * When user want change alarm time or delete clock, user pressed this button
         */
        @Override
        public void changeAlarmTime() {
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
