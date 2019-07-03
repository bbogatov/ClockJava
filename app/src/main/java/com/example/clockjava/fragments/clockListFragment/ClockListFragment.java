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
import com.example.clockjava.database.ClockAlarm;

import java.util.ArrayList;

public class ClockListFragment extends Fragment implements ClockListContract.View {

    private ArrayList<ClockAlarm> clockAlarmArrayList;
    private View view;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_clock_list, null);

        ClockListContract.Presenter presenter = new ClockListPresenter(this);

        //gets all clocks from presenter
        clockAlarmArrayList = presenter.getClockData();

        addRecyclerView();

        return view;
    }


    /**
     * This method adds recycler view of all clocks
     */
    private void addRecyclerView() {
        ClockAdapter adapter = ClockAdapter.getInstance(getActivity());
        adapter.setClockAlarms(clockAlarmArrayList);

        RecyclerView alarmsRecyclerView = view.findViewById(R.id.alarms_list);
        alarmsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        alarmsRecyclerView.setAdapter(adapter);
    }

}
