package com.tobbetu.MobileECG_Doctor.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.tobbetu.MobileECG_Doctor.R;
import com.tobbetu.MobileECG_Doctor.model.User;

/**
 * Created by kanilturgut on 16/03/14.
 */
public class HastaActivity extends Activity {

    User user = null;
    Button bUserInfo, bUserECG;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hasta);

        int index = getIntent().getIntExtra("index", -1);
        user = new User();

        if (index == 0) {
            user.setName("Kadir Anıl");
            user.setSurname("Turğut");
            user.setBirthday("30.10.1988");
            user.setPhoneNumber("05055851345");
            user.setAddress("Kardelen Mahallesi 2030.Sokak Berkay Sitesi No:8");
            user.setEmail("anil@enforceapp.com");
        } else if (index == 1) {
            user.setName("Umut Ozan");
            user.setSurname("Yıldırım");
            user.setBirthday("20.09.1991");
            user.setPhoneNumber("05424122972");
            user.setAddress("TOBB ETÜ Öğrenci Konukevi B2 Blok");
            user.setEmail("ozan@enforceapp.com");
        } else if (index == 2) {
            user.setName("Tansel");
            user.setSurname("Özyer");
            user.setBirthday("30.10.1988");
            user.setPhoneNumber("05055851345");
            user.setAddress("Kardelen Mahallesi 2030.Sokak Berkay Sitesi No:8");
            user.setEmail("ozyer@etu.edu.tr");
        } else if (index == 3) {
            user.setName("Onur Can");
            user.setSurname("Sert");
            user.setBirthday("30.10.1988");
            user.setPhoneNumber("05055851345");
            user.setAddress("Ayrancı Ankara Dikmen");
            user.setEmail("onur@enforceapp.com");
        } else {
            user = null;
        }


        if (user != null) {
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
                i.putExtra("class", user);
                startActivity(i);
            }
        });

        bUserInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HastaActivity.this, UserDetailActivity.class);
                i.putExtra("class", user);
                startActivity(i);
            }
        });

    }
}