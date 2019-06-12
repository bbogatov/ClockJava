package com.example.clockjava;

/**
 * This class used for keeping alarms from database in array that used in recyclerView
 */
public class Alarm {
    private boolean enable;
    private String time;
    private Long index;

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

}
