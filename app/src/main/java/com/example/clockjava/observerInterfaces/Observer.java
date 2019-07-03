package com.example.clockjava.observerInterfaces;


import com.example.clockjava.database.ClockAlarm;

import java.util.ArrayList;

public interface Observer {
    void handleEvent(ArrayList<ClockAlarm> clockAlarms);
}
