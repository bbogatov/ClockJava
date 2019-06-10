package com.example.clockjava;

import android.widget.Switch;

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

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Long getIndex() {
        return index;
    }

    public void setIndex(Long index) {
        this.index = index;
    }
}
