package com.example.clockjava.activities.changeClockActivity;

class ChangeClockPresenter implements ChangeClockContract.Presenter {
    private ChangeClockContract.View view;

    public ChangeClockPresenter(ChangeClockContract.View view) {
        this.view = view;
    }


}

