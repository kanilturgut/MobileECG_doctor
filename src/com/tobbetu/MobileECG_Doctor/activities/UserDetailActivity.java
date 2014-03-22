package com.tobbetu.MobileECG_Doctor.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.tobbetu.MobileECG_Doctor.R;
import com.tobbetu.MobileECG_Doctor.model.User;

/**
 * Created by kanilturgut on 16/03/14.
 */
public class UserDetailActivity extends Activity {

    ProgressDialog progressDialog = null;
    TextView tvHastaAdi, tvHastaDogumGunu, tvHastaTelefonNo, tvHastaAdres, tvHastaEPosta;
    Button bGoPatientECGSignalList;
    User user = null;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);

        progressDialog = ProgressDialog.show(this, "Lütfen Bekleyiniz", "Hastanın bilgileri yükleniyor");

        user = (User) getIntent().getSerializableExtra("class");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressDialog.dismiss();
                initialize();
            }
        }, 3000);

    }

    private void initialize() {

        tvHastaAdi = (TextView) findViewById(R.id.tvPatientName);
        tvHastaAdi.setText(user.getName() + " " + user.getSurname());

        tvHastaDogumGunu = (TextView) findViewById(R.id.tvPatientBirthday);
        tvHastaDogumGunu.setText(user.getBirthday());

        tvHastaTelefonNo = (TextView) findViewById(R.id.tvPatientPhoneNumber);
        tvHastaTelefonNo.setText(user.getPhoneNumber());

        tvHastaAdres = (TextView) findViewById(R.id.tvPatientAddress);
        tvHastaAdres.setText(user.getAddress());

        tvHastaEPosta = (TextView) findViewById(R.id.tvPatientEmail);
        tvHastaEPosta.setText(user.getEmail());

        bGoPatientECGSignalList = (Button) findViewById(R.id.bGoPatientECGSignalList);
        bGoPatientECGSignalList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(UserDetailActivity.this, PatientECGSignalList.class);
                i.putExtra("class", user);
                startActivity(i);
            }
        });

    }
}