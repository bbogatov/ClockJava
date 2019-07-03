package com.example.clockjava.database;

import java.util.Objects;

/**
 * This class used for keeping alarms from database in array that used in recyclerView
 */
public class ClockAlarm {
    private final boolean enable;
    private final String time;
    private final Long id;

    /**
     * Method that creates new clock signal.
     *
     * @param id id of clock from database
     * @param time time whent clock should start
     * @param enable
     */
    public ClockAlarm(Long id, String time, boolean enable) {
        this.enable = enable;
        this.time = time;
        this.id = id;
    }

    /**
     * Method that returns value does current clock enabled or not
     *
     * @return
     */
    public boolean getEnable() {
        return enable;
    }

    /**
     * @return returns alarm time for clock
     */
    public String getTime() {
        return time;
    }

    /**
     * Returns id of clock from database
     *
     * @return
     */
    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClockAlarm clockAlarm = (ClockAlarm) o;
        return enable == clockAlarm.enable &&
                time.equals(clockAlarm.time) &&
                id.equals(clockAlarm.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(enable, time, id);
    }

    @Override
    public String toString() {
        return "ClockAlarm{" +
                "enable=" + enable +
                ", time='" + time + '\'' +
                ", id=" + id +
                '}';
    }
}
