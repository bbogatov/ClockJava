package com.example.clockjava.fragments.addClockButtonFragment;

import android.content.Context;

interface AddClockContract {

    interface View {
        void showNewClockActivity();
    }

    interface Presenter {
        void addClockButtonPressed();
    }
}
