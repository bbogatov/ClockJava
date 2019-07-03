package com.example.clockjava;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.clockjava.fragments.addClockButtonFragment.AddClockFragment;
import com.example.clockjava.fragments.clockListFragment.ClockListFragment;
import com.example.clockjava.logger.Logger;

/**
 * Main activity, from this activity starts working application
 */
public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Logger.log("Create main activity");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ClockListFragment alarmListFragment = new ClockListFragment();
        AddClockFragment clockButtonFragment = new AddClockFragment();

        FragmentTransaction upperFragmentTrans = getSupportFragmentManager().beginTransaction();
        upperFragmentTrans.add(R.id.list_fragment, alarmListFragment);
        upperFragmentTrans.commit();


        FragmentTransaction lowerFragmentTrans = getSupportFragmentManager().beginTransaction();
        lowerFragmentTrans.add(R.id.button_fragment, clockButtonFragment);
        lowerFragmentTrans.commit();
    }
}
