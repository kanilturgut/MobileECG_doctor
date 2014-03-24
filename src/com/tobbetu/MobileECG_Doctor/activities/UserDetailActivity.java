package com.tobbetu.MobileECG_Doctor.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
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
    TextView tvHastaAdi, tvHastaDogumGunu, tvHastaTelefonNo, tvHastaAdres, tvHastaCinsiyet, tvHastaBoyKilo,
            tvHastaBMI, tvHastaAktivite, tvHastaSigara, tvHastaAlkol, tvHastaLDL, tvHastaHDL, tvHastaTansiyon, tvHastaSeker;

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

        try {

            tvHastaAdi = (TextView) findViewById(R.id.tvPatientName);
            tvHastaAdi.setText(user.getName() + " " + user.getSurname());

            tvHastaDogumGunu = (TextView) findViewById(R.id.tvPatientBirthday);
            tvHastaDogumGunu.setText("" + user.getBirthday().getTime());

            tvHastaTelefonNo = (TextView) findViewById(R.id.tvPatientPhoneNumber);
            tvHastaTelefonNo.setText(user.getPhoneNumber());

            tvHastaAdres = (TextView) findViewById(R.id.tvPatientAddress);
            tvHastaAdres.setText(user.getAddress());

            tvHastaCinsiyet = (TextView) findViewById(R.id.tvPatientSex);
            tvHastaCinsiyet.setText(user.getSex());

            tvHastaBoyKilo = (TextView) findViewById(R.id.tvPatientBoyKilo);
            tvHastaBoyKilo.setText(user.getHeight() + " cm, " + user.getWeight() + " kg");

            tvHastaBMI = (TextView) findViewById(R.id.tvPatientBMI);
            tvHastaBMI.setText("BMI: " + user.getBmi());

            tvHastaAktivite = (TextView) findViewById(R.id.tvPatientActivityFrequency);
            tvHastaAktivite.setText("" + user.getActivityFrequency());

            tvHastaSigara = (TextView) findViewById(R.id.tvPatientSmokingFrequency);
            tvHastaSigara.setText("" + user.getSmokingFrequency());

            tvHastaAlkol = (TextView) findViewById(R.id.tvPatientAlcoholFrequency);
            tvHastaAlkol.setText("" + user.getAlcoholUsageFrequency());

            tvHastaLDL = (TextView) findViewById(R.id.tvPatientKolestrolLDL);
            tvHastaLDL.setText("" + user.getKolesterolLDL());

            tvHastaHDL = (TextView) findViewById(R.id.tvPatientKolestrolHDL);
            tvHastaHDL.setText("" + user.getKolesterolHDL());

            tvHastaTansiyon = (TextView) findViewById(R.id.tvPatientHighTension);
            tvHastaTansiyon.setText("" + user.isHasHypertension());

            tvHastaSeker = (TextView) findViewById(R.id.tvPatientDiabets);
            tvHastaSeker.setText("" + user.isHasDiabetes());
        } catch (Exception e) {
            Log.e("TAG", "Bir sorun cikti", e);
        }

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