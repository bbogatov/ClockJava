package com.example.clockjava;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;


public class ClockAdapter extends RecyclerView.Adapter<ClockAdapter.AlarmHolder> {

    private ArrayList<Alarm> alarms;


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


    public void setAlarms(ArrayList<Alarm> alarms) {
        this.alarms = alarms;
        notifyDataSetChanged();
    }

    public void clearAlarms() {
        alarms.clear();
        notifyDataSetChanged();
    }

    class AlarmHolder extends RecyclerView.ViewHolder {

        private Switch aSwitch;
        private TextView textView;

        public AlarmHolder(@NonNull View itemView) {
            super(itemView);
            aSwitch = itemView.findViewById(R.id.alarm_switcher);
            textView = itemView.findViewById(R.id.alarm_time_text_view);
        }

        public void bind(Alarm alarm) {
            aSwitch.setChecked(alarm.getEnable());
            textView.setText(alarm.getTime());
        }
    }
}
