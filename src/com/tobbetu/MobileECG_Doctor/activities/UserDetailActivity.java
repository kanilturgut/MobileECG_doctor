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
import com.tobbetu.MobileECG_Doctor.model.Patient;

/**
 * Created by kanilturgut on 16/03/14.
 */
public class UserDetailActivity extends Activity {

    ProgressDialog progressDialog = null;
    TextView tvHastaAdi, tvHastaDogumGunu, tvHastaTelefonNo, tvHastaAdres, tvHastaCinsiyet, tvHastaBoyKilo,
            tvHastaBMI, tvHastaAktivite, tvHastaSigara, tvHastaAlkol, tvHastaLDL, tvHastaHDL, tvHastaTansiyon, tvHastaSeker;

    Button bGoPatientECGSignalList;
    Patient patient = null;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);

        progressDialog = ProgressDialog.show(this, "Lütfen Bekleyiniz", "Hastanın bilgileri yükleniyor");

        patient = (Patient) getIntent().getSerializableExtra("class");

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
            tvHastaAdi.setText(patient.getName() + " " + patient.getSurname());

            tvHastaDogumGunu = (TextView) findViewById(R.id.tvPatientBirthday);
            tvHastaDogumGunu.setText("" + patient.getBirthday().getTime());

            tvHastaTelefonNo = (TextView) findViewById(R.id.tvPatientPhoneNumber);
            tvHastaTelefonNo.setText(patient.getPhoneNumber());

            tvHastaAdres = (TextView) findViewById(R.id.tvPatientAddress);
            tvHastaAdres.setText(patient.getAddress());

            tvHastaCinsiyet = (TextView) findViewById(R.id.tvPatientSex);
            tvHastaCinsiyet.setText(patient.getSex());

            tvHastaBoyKilo = (TextView) findViewById(R.id.tvPatientBoyKilo);
            tvHastaBoyKilo.setText(patient.getHeight() + " cm, " + patient.getWeight() + " kg");

            tvHastaBMI = (TextView) findViewById(R.id.tvPatientBMI);
            tvHastaBMI.setText("BMI: " + patient.getBmi());

            tvHastaAktivite = (TextView) findViewById(R.id.tvPatientActivityFrequency);
            tvHastaAktivite.setText("" + patient.getActivityFrequency());

            tvHastaSigara = (TextView) findViewById(R.id.tvPatientSmokingFrequency);
            tvHastaSigara.setText("" + patient.getSmokingFrequency());

            tvHastaAlkol = (TextView) findViewById(R.id.tvPatientAlcoholFrequency);
            tvHastaAlkol.setText("" + patient.getAlcoholUsageFrequency());

            tvHastaLDL = (TextView) findViewById(R.id.tvPatientKolestrolLDL);
            tvHastaLDL.setText("" + patient.getKolesterolLDL());

            tvHastaHDL = (TextView) findViewById(R.id.tvPatientKolestrolHDL);
            tvHastaHDL.setText("" + patient.getKolesterolHDL());

            tvHastaTansiyon = (TextView) findViewById(R.id.tvPatientHighTension);
            tvHastaTansiyon.setText("" + patient.isHasHypertension());

            tvHastaSeker = (TextView) findViewById(R.id.tvPatientDiabets);
            tvHastaSeker.setText("" + patient.isHasDiabetes());
        } catch (Exception e) {
            Log.e("TAG", "Bir sorun cikti", e);
        }

        bGoPatientECGSignalList = (Button) findViewById(R.id.bGoPatientECGSignalList);
        bGoPatientECGSignalList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(UserDetailActivity.this, PatientECGSignalList.class);
                i.putExtra("class", patient);
                startActivity(i);
            }
        });

    }
}