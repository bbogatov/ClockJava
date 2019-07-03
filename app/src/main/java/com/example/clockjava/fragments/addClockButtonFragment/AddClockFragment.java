package com.example.clockjava.fragments.addClockButtonFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.clockjava.R;
import com.example.clockjava.activities.newClockActivity.NewClockActivity;


/**
 * Class that response for add new clock button
 */
public class AddClockFragment extends Fragment implements AddClockContract.View {

    private ImageButton addClockButton;
    private AddClockContract.Presenter presenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_new_clock_button, null);

        presenter = new AddClockPresenter(this);

        addClockButton = view.findViewById(R.id.clock_button);

        addClockButton.setOnClickListener((View v) -> presenter.addClockButtonPressed());

        return view;
    }

    /**
     * If user wants to add new clock it clicks on {@link #addClockButton} and this code starts next activity.
     */
    @Override
    public void showNewClockActivity() {
        Intent newClockIntent = new Intent(getContext(), NewClockActivity.class);
        startActivity(newClockIntent);
    }
}
