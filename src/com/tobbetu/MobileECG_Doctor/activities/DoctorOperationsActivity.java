package com.tobbetu.MobileECG_Doctor.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.parse.Parse;
import com.parse.PushService;
import com.tobbetu.MobileECG_Doctor.R;
import com.tobbetu.MobileECG_Doctor.android_service.MobileECGDoctorService;
import com.tobbetu.MobileECG_Doctor.model.Doctor;

/**
 * Created by kanilturgut on 27/03/14, 19:00.
 */
public class DoctorOperationsActivity extends Activity implements View.OnClickListener {

    Context context = null;
    Button bShowAllPatients, bShowEnrolledPatients;
    public static Doctor doctor = null;
    boolean fromService;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_operations);
        context = this;

        doctor = (Doctor) getIntent().getSerializableExtra("class");

        PushService.subscribe(getApplicationContext(), "A" + doctor.getId(), LoginActivity.class);

        if (MobileECGDoctorService.fromService) {
            MobileECGDoctorService.fromService = false;

            Toast.makeText(context, "Push Notification ile geldin", Toast.LENGTH_LONG).show();
        }

        bShowAllPatients = (Button) findViewById(R.id.bShowAllPatients);
        bShowAllPatients.setOnClickListener(this);

        bShowEnrolledPatients = (Button) findViewById(R.id.bShowAllEnrolledPatients);
        bShowEnrolledPatients.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.bShowAllPatients) {
            startActivity(new Intent(context, AllPatientsListActivity.class));
        } else {
            startActivity(new Intent(context, ShowEnrolledPatientsListActivity.class));
            //startActivity(new Intent(context, MapActivity.class));
        }
    }
}