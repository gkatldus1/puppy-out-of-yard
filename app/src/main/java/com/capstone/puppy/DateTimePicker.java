package com.capstone.puppy;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.sql.Date;
import java.sql.Time;
import java.util.Calendar;

public class DateTimePicker extends Dialog implements View.OnClickListener {
    private MainActivity activity;
    private EditText et_start_date;
    private EditText et_start_time;
    private EditText et_end_date;
    private EditText et_end_time;

    private Button btn_search;

    boolean isStart = true;

    Date start_date;
    Time start_time;
    Date end_date;
    Time end_time;

    public interface ReturnEventListener {
        public void customDialogEvent(Date start_date, Time start_time, Date end_date, Time end_time);

    }

    private ReturnEventListener returnEventListener;



    // In the constructor, you set the callback

    public DateTimePicker(Context context, ReturnEventListener onCustomDialogEventListener) {
        super(context);
        activity = (MainActivity)context;
        this.returnEventListener = onCustomDialogEventListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_datetimepicker);

        et_start_date = findViewById(R.id.et_start_date);
        et_start_time = findViewById(R.id.et_start_time);
        et_end_date = findViewById(R.id.et_end_date);
        et_end_time = findViewById(R.id.et_end_time);

        et_start_date.setOnClickListener(this);
        et_start_time.setOnClickListener(this);
        et_end_date.setOnClickListener(this);
        et_end_time.setOnClickListener(this);

        btn_search = findViewById(R.id.btn_search);
        btn_search.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Calendar c = Calendar.getInstance();
        int day = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);
        int hour = c.get(Calendar.HOUR);
        int min = c.get(Calendar.MINUTE);

        switch (v.getId()){
            case R.id.et_start_date:
                isStart = true;
                new DatePickerDialog(activity, date_listener, year, month, day).show();
                break;
            case R.id.et_start_time:
                isStart = true;
                new TimePickerDialog(activity, time_listener, hour, min, true).show();
                break;
            case R.id.et_end_date:
                isStart = false;
                new DatePickerDialog(activity, date_listener, year, month, day).show();
                break;
            case R.id.et_end_time:
                isStart = false;
                new TimePickerDialog(activity, time_listener, hour, min, true).show();
                break;
            case R.id.btn_search:
                returnEventListener.customDialogEvent(start_date, start_time, end_date, end_time);
                dismiss();
                break;
        }
    }

    private DatePickerDialog.OnDateSetListener date_listener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            if(isStart) {
                et_start_date.setText("" + year + (month+1) + dayOfMonth);
                start_date = new Date(year,month,dayOfMonth);
            } else {
                et_end_date.setText("" + year + (month+1) + dayOfMonth);
                end_date = new Date(year,month,dayOfMonth);
            }
            new Date(year,month,dayOfMonth);
        }
    };

    private TimePickerDialog.OnTimeSetListener time_listener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            if(isStart) {
                et_start_time.setText("" + hourOfDay + ":" + minute);
                start_time = new Time(hourOfDay,minute,0);
            } else {
                et_end_time.setText("" + hourOfDay + ":" + minute);
                end_time = new Time(hourOfDay,minute,0);
            }
        }
    };
}
