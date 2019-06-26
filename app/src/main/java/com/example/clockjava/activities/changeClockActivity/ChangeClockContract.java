package com.example.clockjava.activities.changeClockActivity;

interface ChangeClockContract {

    interface View {
        void closeActivity();
        void showDeleteAlertWindow();
        void setAlertTime(String time);
        void showAlertWindowSaveData();
    }

    interface Presenter {
        void setAlarmTimeTimePicker(String time);
        void applyButtonClicked(boolean timeChanged, String newTime, String oldTime, long index);
        void discardButtonClicked(boolean timeChanged);
        void deleteButtonClicked();
        void deleteClockPositiveButtonClicked(long index, String time);
        void deleteClockNegativeButtonClicked();
        boolean userChangesTime();
        void negativeAlertButtonClicked();
        void positiveAlertButtonClicked(boolean timeChanged, String newTime, String oldTime, long index);
    }

}
