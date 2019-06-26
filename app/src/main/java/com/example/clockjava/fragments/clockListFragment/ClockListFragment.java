package com.example.clockjava.fragments.clockListFragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.clockjava.R;
import com.example.clockjava.adapter.ClockAdapter;
import com.example.clockjava.database.Alarm;
import com.example.clockjava.logger.Logger;

import java.util.ArrayList;

public class ClockListFragment extends Fragment implements ClockListContract.View {

    private RecyclerView alarmsRecyclerView;
    private ClockAdapter adapter;
    private ArrayList<Alarm> alarmArrayList;
    private View view;
    private ClockListContract.Presenter presenter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_clock_list, null);

        presenter = new ClockListPresenter(this);

        //gets all clocks from presenter
        alarmArrayList = presenter.getClockData();

        addRecyclerView();

        return view;
    }


    /**
     * This method adds recycler view of all clocks
     */
    private void addRecyclerView() {
        Logger.log("Добавляем рекуклер вью");
        adapter = new ClockAdapter(getActivity());

        alarmsRecyclerView = view.findViewById(R.id.alarms_list);
        alarmsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter.setAlarms(alarmArrayList);
        alarmsRecyclerView.setAdapter(adapter);
        Logger.log("Рекуклер вью добавлен");
    }

}
