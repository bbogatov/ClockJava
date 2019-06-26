package com.example.clockjava.activities.newClockActivity;

public interface NewClockContract {
    interface View {
        void finishActivity();
        void showMessage(String time);
    }

    interface Presenter {
        void addNewClockBackMainActivity(String  time);
        void finishActivity();
    }

}
