package com.example.clockjava.activities.changeClock;


import android.os.Build;
import android.widget.TimePicker;

import com.example.clockjava.database.LocalDataBase;
import com.example.clockjava.alarmManger.ClockAlarmsManger;

class ChangeClockPresenter implements ChangeClockContract.Presenter {
    private ChangeClockContract.View view;

    /**
     * Main constructor
     *
     * @param view view for this presenter
     */
    public ChangeClockPresenter(ChangeClockContract.View view) {
        this.view = view;
    }

    /**
     * Method that sets current alarm time on time picker.
     *
     * @param time time when clock should starts.
     */
    @Override
    public void setAlarmTimeTimePicker(String time, TimePicker timePicker) {
        int hours = Integer.valueOf(time.substring(0, 2));
        int minutes = Integer.valueOf(time.substring(3, 5));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            timePicker.setHour(hours);
            timePicker.setMinute(minutes);
        } else {
            timePicker.setCurrentHour(hours);
            timePicker.setCurrentMinute(minutes);
        }
    }

    /**
     * When user clicks apply button this method checks does any changes have been made.
     * If user changes nothing, method will not work.
     *
     * @param timeChanged boolean that respounce
     * @param newTime     time user chose
     * @param oldTime     old value of current clock
     * @param id          index of element in database
     */
    @Override
    public void applyButtonClicked(boolean timeChanged, String newTime, String oldTime, long id) {
        if (timeChanged) {
            changeTime(oldTime, newTime, id);
        }
        view.closeActivity();
    }

    /**
     * If user clicks close button this code checks does it made any changes.
     * If user made changes it code shows alarm window and asks does user wants to save data.
     * If user dont wanna save any changes code returns main activity.
     *
     * @param timeChanged if user made changes true, in other cases false.
     */
    @Override
    public void discardButtonClicked(boolean timeChanged) {
        if (timeChanged) {
            view.showAlertWindowSaveData();
        } else {
            view.closeActivity();
        }
    }

    /**
     * If user wants delete a clock this method shows alert window, and ask does he sure.
     */
    @Override
    public void deleteButtonClicked() {
        view.showDeleteAlertWindow();
    }

    /**
     * Method deletes clock from database and removes its alarm manager.
     * <p>
     * When user click delete clock method shows alarm window and aks does it sure.
     * If user sures it clicks positive button and this method runs.
     *
     * @param id   index of element in database that need remove
     * @param time time when alarm clock must started
     */
    @Override
    public void deleteClockPositiveButtonClicked(long id, String time) {
        deleteClockInDataBase(id);
        deleteAlarmManager(id, time);

        view.closeActivity();
    }

    /**
     * If user clicks delete clock AlertWindow appears and ask him does he sure.
     * If user clicks negative button on alert window, this code runs and destroys {@link ChangeClockActivity} activity
     */
    @Override
    public void deleteClockNegativeButtonClicked() {
        view.closeActivity();
    }

    /**
     * If user changes time on {@link TimePicker}
     *
     * @return true when user changes time on timePicker
     */
    @Override
    public boolean userChangesTime() {
        return true;
    }

    /**
     * User made changes and click close button, after it appears alert window that asks does he want make any changes
     * If user clicks no this code will work, and destroys {@link ChangeClockActivity} activity
     */
    @Override
    public void negativeAlertButtonClicked() {
        view.closeActivity();
    }

    /**
     * When user has made changes and click discard button this
     *
     * @param timeChanged boolean that respounce
     * @param newTime     time user chose
     * @param oldTime     old value of current clock
     * @param id          index of element in database
     */
    @Override
    public void positiveAlertButtonClicked(boolean timeChanged, String newTime, String oldTime, long id) {
        applyButtonClicked(timeChanged, newTime, oldTime, id);
        view.closeActivity();
    }

    /**
     * Method that changes alarm time in database and changes alarmManger
     */
    private void changeTime(String oldTime, String newTime, long id) {
        changeAlarmManger(oldTime, newTime, id);
        changeValuesDataBase(newTime, id);
    }

    /**
     * Method that changes alarm time.
     *
     * @param oldTime old time that need change
     * @param newTime future clock time
     * @param id      index of element in DB
     */
    private void changeAlarmManger(String oldTime, String newTime, long id) {
        ClockAlarmsManger clockAlarmsManger = new ClockAlarmsManger();
        clockAlarmsManger.changeAlarmTime(oldTime, newTime, id);
    }

    /**
     * Method that changes values in database.
     *
     * @param newTime new time value
     * @param id      index of element in DB that need change
     */
    private void changeValuesDataBase(String newTime, long id) {
        LocalDataBase localDataBase = LocalDataBase.getInstance();
        localDataBase.changeTime(id, newTime);
        localDataBase.changeSwitch(id, true);
    }

    /**
     * Method that removes clock from database.
     *
     * @param id index of element that need remove
     */
    private void deleteClockInDataBase(long id) {
        LocalDataBase localDataBase = LocalDataBase.getInstance();
        localDataBase.removeClock(id);
    }

    /**
     * Method that removes alarm manager.
     *
     * @param id   index if element in database
     * @param time time when alarm manger must started
     */
    private void deleteAlarmManager(long id, String time) {
        ClockAlarmsManger clockAlarmsManger = new ClockAlarmsManger();
        clockAlarmsManger.removeAlarm(time, id);

    }
}

