package com.example.clockjava.activities.changeClockActivity;


import com.example.clockjava.database.LocalDataBase;
import com.example.clockjava.alarmManger.ClockAlarmsManger;

//TODO тут нужно будет еще вызывать RecyclerView что бы сообщить об изменениях
class ChangeClockPresenter implements ChangeClockContract.Presenter {
    private ChangeClockContract.View view;

    public ChangeClockPresenter(ChangeClockContract.View view) {
        this.view = view;
    }

    /**
     * Method that sets alarm time on time picker.
     *
     * @param time time when clock should starts.
     */
    @Override
    public void setAlarmTimeTimePicker(String time) {
        view.setAlertTime(time);
    }

    /**
     * When user clicks apply button this method checks does any changes have been made.
     * If user changes nothing, method will not work.
     *
     * @param timeChanged boolean that respounce
     * @param newTime     time user chose
     * @param oldTime     old value of current clock
     * @param index       index of element in database
     */
    @Override
    public void applyButtonClicked(boolean timeChanged, String newTime, String oldTime, long index) {
        if (timeChanged) {
            changeTime(oldTime, newTime, index);
        }
        view.closeActivity();
    }

    @Override
    public void discardButtonClicked(boolean timeChanged) {
        if (timeChanged) {
            view.showDeleteAlertWindow();
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
     * @param index index of element in database that need remove
     * @param time  time when alarm clock must started
     */
    @Override
    public void deleteClockPositiveButtonClicked(long index, String time) {
        deleteClockInDataBase(index);
        deleteAlarmManger(index, time);

        view.closeActivity();
    }

    @Override
    public void deleteClockNegativeButtonClicked() {
        view.closeActivity();
    }

    @Override
    public boolean userChangesTime() {
        return true;
    }

    /**
     * If alert window appears and user click negative button this code closes current activity/
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
     * @param index       index of element in database
     */
    @Override
    public void positiveAlertButtonClicked(boolean timeChanged, String newTime, String oldTime, long index) {
        applyButtonClicked(timeChanged, newTime, oldTime, index);
        view.closeActivity();
    }

    /**
     * Method that changes alarm time in database and changes alarmManger
     */
    private void changeTime(String oldTime, String newTime, long index) {
        changeAlarmManger(oldTime, newTime, index);
        changeValuesDataBase(oldTime, newTime, index);
    }

    /**
     * Method that changes alarm time.
     *
     * @param oldTime old time that need change
     * @param newTime future clock time
     * @param index   index of element in DB
     */
    private void changeAlarmManger(String oldTime, String newTime, long index) {
        ClockAlarmsManger clockAlarmsManger = new ClockAlarmsManger();
        clockAlarmsManger.changeAlarm(oldTime, newTime, index);
    }

    /**
     * Method that changes values in database.
     *
     * @param oldTime old time value
     * @param newTime new time value
     * @param index   index of element in DB that need change
     */
    private void changeValuesDataBase(String oldTime, String newTime, long index) {
        LocalDataBase localDataBase = LocalDataBase.init();
        localDataBase.changeTime(index, newTime);
        localDataBase.changeSwitch(index, true);
    }

    /**
     * Method that removes clock from database.
     *
     * @param index index of element that need remove
     */
    private void deleteClockInDataBase(long index) {
        LocalDataBase localDataBase = LocalDataBase.init();
        localDataBase.removeClock(index);
    }

    /**
     * Method that removes alarm manager.
     *
     * @param index index if element in database
     * @param time  time when alarm manger must started
     */
    private void deleteAlarmManger(long index, String time) {
        ClockAlarmsManger clockAlarmsManger = new ClockAlarmsManger();
        clockAlarmsManger.removeAlarm(time, index);

    }
}

