package com.example.clockjava.fragments.addClockButtonFragment;

import android.content.Context;

public class AddClockPresenter implements AddClockContract.Presenter {
    private AddClockContract.View view;

    AddClockPresenter(AddClockContract.View view) {
        this.view = view;
    }

    @Override
    public void addClockButtonPressed() {
        view.showNewClockActivity();
    }
}
