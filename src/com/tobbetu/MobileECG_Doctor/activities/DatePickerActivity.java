package com.tobbetu.MobileECG_Doctor.activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;
import com.tobbetu.MobileECG_Doctor.R;
import com.tobbetu.MobileECG_Doctor.model.ECGData;
import com.tobbetu.MobileECG_Doctor.model.Patient;
import com.tobbetu.MobileECG_Doctor.util.Util;
import org.json.JSONException;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

/**
 * Created by kanilturgut on 03/04/14, 15:15.
 */
public class DatePickerActivity extends Activity implements View.OnClickListener {

    Context context = null;
    Button startDate, endDate, send;

    Calendar calendar = null;
    int day, month, year;

    Patient patient = null;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_picker);
        context = this;

        patient = (Patient) getIntent().getSerializableExtra("class");

        calendar = Calendar.getInstance();
        day = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH) + 1;
        year = calendar.get(Calendar.YEAR);

        startDate = (Button) findViewById(R.id.bStartDate);
        startDate.setText(addZero(day-1) + "." + addZero(month) + "." + addZero(year));
        startDate.setOnClickListener(this);

        endDate = (Button) findViewById(R.id.bEndDate);
        endDate.setText(addZero(day) + "." + addZero(month) + "." + addZero(year));
        endDate.setOnClickListener(this);

        send = (Button) findViewById(R.id.bDoQuery);
        send.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.bStartDate:
                DatePickerDialog dpd = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        startDate.setText(addZero(day) + "." + addZero(month+1) + "." + addZero(year));
                    }
                }, year, (month-1), day);
                dpd.show();
                break;
            case R.id.bEndDate:
                DatePickerDialog dpd2 = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        endDate.setText(addZero(day) + "." + addZero(month + 1) + "." + addZero(year));
                    }
                }, year, (month-1), day);
                dpd2.show();
                break;
            case R.id.bDoQuery:

                Intent intent = new Intent(context, PastECGDatas.class);
                intent.putExtra("start", startDate.getText().toString().trim());
                intent.putExtra("end", endDate.getText().toString().trim());
                intent.putExtra("patient", patient);
                startActivity(intent);

                break;
        }
    }

    String addZero(int number) {
        if (number < 10)
            return 0 + String.valueOf(number);
        else
            return String.valueOf(number);
    }
}