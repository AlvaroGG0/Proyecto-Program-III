package com.example.sporttogether.ui.dialogs;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    private EditText editText;

    public static TimePickerFragment newInstance(EditText editText) {
        TimePickerFragment fragment = new TimePickerFragment();
        fragment.setEditText(editText);
        return fragment;
    }

    public void setEditText(EditText editText){
        this.editText = editText;
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));

    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        @SuppressLint("DefaultLocale") String selectedTime = String.format("%02d", hourOfDay) + ":" + String.format("%02d", minute);
        this.editText.setText(selectedTime);
    }


}
