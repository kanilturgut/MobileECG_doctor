package com.tobbetu.MobileECG_Doctor.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.tobbetu.MobileECG_Doctor.R;
import com.tobbetu.MobileECG_Doctor.model.Patient;

import java.util.Date;

/**
 * Created by kanilturgut on 16/03/14.
 */
public class HastaActivity extends Activity {

    Patient patient = null;
    Button bUserInfo, bUserECG;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hasta);

        int index = getIntent().getIntExtra("index", -1);
        patient = new Patient();

        if (index == 0) {
            patient.setName("Kadir Anıl");
            patient.setSurname("Turğut");
            patient.setBirthday(new Date());
            patient.setPhoneNumber("05055851345");
            patient.setAddress("Kardelen Mahallesi 2030.Sokak Berkay Sitesi No:8");
        } else if (index == 1) {
            patient.setName("Umut Ozan");
            patient.setSurname("Yıldırım");
            patient.setBirthday(new Date());
            patient.setPhoneNumber("05424122972");
            patient.setAddress("TOBB ETÜ Öğrenci Konukevi B2 Blok");
        } else if (index == 2) {
            patient.setName("Tansel");
            patient.setSurname("Özyer");
            patient.setBirthday(new Date());
            patient.setPhoneNumber("05055851345");
            patient.setAddress("Kardelen Mahallesi 2030.Sokak Berkay Sitesi No:8");
        } else if (index == 3) {
            patient.setName("Onur Can");
            patient.setSurname("Sert");
            patient.setBirthday(new Date());
            patient.setPhoneNumber("05055851345");
            patient.setAddress("Ayrancı Ankara Dikmen");
        } else {
            patient = null;
        }


        if (patient != null) {
            initialize();
        }

    }

    private void initialize() {

        bUserECG = (Button) findViewById(R.id.bUserECG);
        bUserInfo = (Button) findViewById(R.id.bUserInfo);

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
                Intent i = new Intent(HastaActivity.this, UserDetailActivity.class);
                i.putExtra("class", patient);
                startActivity(i);
            }
        });

    }
}