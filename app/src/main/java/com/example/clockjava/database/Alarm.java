package com.example.clockjava.database;

import java.util.Objects;

/**
 * This class used for keeping alarms from database in array that used in recyclerView
 */
public class Alarm {
    private final boolean enable;
    private final String time;
    private final Long index;

    public Alarm(Long index, String time, boolean enable) {
        this.enable = enable;
        this.time = time;
        this.index = index;
    }

    public boolean getEnable() {
        return enable;
    }

    public String getTime() {
        return time;
    }

    public Long getIndex() {
        return index;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Alarm alarm = (Alarm) o;
        return enable == alarm.enable &&
                time.equals(alarm.time) &&
                index.equals(alarm.index);
    }

    @Override
    public int hashCode() {
        return Objects.hash(enable, time, index);
    }
}
