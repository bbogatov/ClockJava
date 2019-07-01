package com.example.clockjava.observerInterfaces;


import com.example.clockjava.database.Alarm;

import java.util.ArrayList;

public interface Observer {
    void handleEvent(ArrayList<Alarm> alarms);
}
