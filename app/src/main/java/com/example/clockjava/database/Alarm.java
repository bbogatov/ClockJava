package com.example.clockjava.database;

import java.util.Objects;

/**
 * This class used for keeping alarms from database in array that used in recyclerView
 */
public class Alarm {
    private final boolean enable;
    private final String time;
    private final Long id;

    public Alarm(Long id, String time, boolean enable) {
        this.enable = enable;
        this.time = time;
        this.id = id;
    }

    public boolean getEnable() {
        return enable;
    }

    public String getTime() {
        return time;
    }

    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Alarm alarm = (Alarm) o;
        return enable == alarm.enable &&
                time.equals(alarm.time) &&
                id.equals(alarm.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(enable, time, id);
    }

    @Override
    public String toString() {
        return "Alarm{" +
                "enable=" + enable +
                ", time='" + time + '\'' +
                ", id=" + id +
                '}';
    }
}
