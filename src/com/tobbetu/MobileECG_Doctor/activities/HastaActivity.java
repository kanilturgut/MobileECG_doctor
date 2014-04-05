package com.tobbetu.MobileECG_Doctor.activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.tobbetu.MobileECG_Doctor.R;
import com.tobbetu.MobileECG_Doctor.model.Patient;

/**
 * Created by kanilturgut on 16/03/14.
 */
public class HastaActivity extends Activity {

    Patient patient = null;
    Button bUserInfo, bUserECG, bUserECGWithDates;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hasta);

        patient = (Patient) getIntent().getSerializableExtra("class");

        if (patient != null)
            initialize();


    }

    private void initialize() {

        bUserECG = (Button) findViewById(R.id.bUserECG);
        bUserInfo = (Button) findViewById(R.id.bUserInfo);
        bUserECGWithDates = (Button) findViewById(R.id.bUserECGWithDates);

        bUserECG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HastaActivity.this, PatientECGSignalList.class);
                i.putExtra("class", patient);
                startActivity(i);
            }
        });

        bUserInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HastaActivity.this, PatientDetailsActivity.class);
                i.putExtra("class", patient);
                startActivity(i);
            }
        });

        bUserECGWithDates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HastaActivity.this, DatePickerActivity.class);
                i.putExtra("class", patient);
                startActivity(i);
            }
        });

    }
}