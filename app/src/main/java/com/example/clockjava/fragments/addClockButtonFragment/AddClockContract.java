package com.example.clockjava.fragments.addClockButtonFragment;

interface AddClockContract {

    interface View {
        void showNewClockActivity();
    }

    interface Presenter {
        void addClockButtonPressed();
    }
}
