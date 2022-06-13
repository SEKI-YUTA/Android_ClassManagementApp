package com.example.classmanagementapp;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class TimePick extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
    EditText edit_result;

    public TimePick(EditText edit_result) {
        this.edit_result = edit_result;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR);
        int min = c.get(Calendar.MINUTE);
        return new TimePickerDialog(getContext(), (TimePickerDialog.OnTimeSetListener) this, hour,min, true);
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {
        Log.d("MyLog", String.valueOf(i));
        Log.d("MyLog", String.valueOf(i1));
        edit_result.setText(String.format("%02d:%02d", i , i1));
    }
}
