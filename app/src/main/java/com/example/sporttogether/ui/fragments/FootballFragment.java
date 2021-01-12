package com.example.sporttogether.ui.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;

import com.example.sporttogether.R;
import com.example.sporttogether.database.Database;
import com.example.sporttogether.partido.Partido;
import com.example.sporttogether.ui.activities.LogInActivity;
import com.example.sporttogether.ui.dialogs.DatePickerFragment;
import com.example.sporttogether.ui.dialogs.TimePickerFragment;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class FootballFragment extends BaseCreateMatchFragment implements View.OnClickListener {

    private EditText editTextNumber;
    private EditText etPlannedDate;
    private EditText etPlannedTime;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_football, container, false);

        editTextNumber = view.findViewById(R.id.editTextNumber);
        etPlannedDate = view.findViewById(R.id.etPlannedDate);
        etPlannedDate.setOnClickListener(this);
        etPlannedTime = view.findViewById(R.id.etPlannedTime);
        etPlannedTime.setOnClickListener(this);

        return view;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.etPlannedDate:
                showDatePickerDialog(etPlannedDate);
                break;
            case R.id.etPlannedTime:
                showTimePickerDialog(etPlannedTime);
                break;
        }
    }

    public void showDatePickerDialog(EditText v) {
        DialogFragment newFragment = DatePickerFragment.newInstance(v);
        newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
    }

    public void showTimePickerDialog(EditText v) {
        DialogFragment newFragment = TimePickerFragment.newInstance(v);
        newFragment.show(getActivity().getSupportFragmentManager(), "timePicker");
    }

    @Override
    public void createMatch() throws Exception {
        if (etPlannedDate.getText().toString().isEmpty() || etPlannedTime.getText().toString().isEmpty() || editTextNumber.getText().toString().isEmpty()) {
            throw new Exception();
        } else {
            int numJugadores = Integer.parseInt(editTextNumber.getText().toString());
            String dateTime = etPlannedDate.getText().toString() + " " + etPlannedTime.getText().toString();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            LocalDateTime LDateTime = LocalDateTime.parse(dateTime, formatter);

            Database.createMatch(LogInActivity.usuario, new Partido(LDateTime, 2, new String[numJugadores], new String[numJugadores], new String[1]));
        }
    }
}