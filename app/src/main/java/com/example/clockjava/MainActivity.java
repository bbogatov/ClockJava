package com.example.clockjava.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.clockjava.fragments.AddClockFragment;
import com.example.clockjava.fragments.ClockListFragment;
import com.example.clockjava.logger.Logger;
import com.example.clockjava.R;

/**
 * Main activity, from this activity starts working application
 */
public class MainActivity extends AppCompatActivity {


    private ClockListFragment alarmListFragment;
    private AddClockFragment addClockFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Logger.log("Create main activity");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        alarmListFragment = new ClockListFragment();
        addClockFragment = new AddClockFragment();


        FragmentTransaction upperFragmentTrans = getSupportFragmentManager().beginTransaction();
        upperFragmentTrans.add(R.id.fragFirst, alarmListFragment);
        upperFragmentTrans.commit();


        FragmentTransaction lowerFragmentTrans = getSupportFragmentManager().beginTransaction();
        lowerFragmentTrans.add(R.id.fragSecond, addClockFragment);
        lowerFragmentTrans.commit();
    }
}
