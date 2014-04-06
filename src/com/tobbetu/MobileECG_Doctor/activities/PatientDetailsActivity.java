package com.tobbetu.MobileECG_Doctor.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.tobbetu.MobileECG_Doctor.R;
import com.tobbetu.MobileECG_Doctor.model.Patient;
import com.tobbetu.MobileECG_Doctor.service.FollowPatient;
import com.tobbetu.MobileECG_Doctor.util.HttpURL;
import com.tobbetu.MobileECG_Doctor.util.Util;
import org.json.JSONException;

import java.io.IOException;
import java.util.List;

/**
 * Created by kanilturgut on 16/03/14.
 */
public class PatientDetailsActivity extends Activity {

    Context context = null;
    TextView tvHastaAdi, tvHastaDogumGunu, tvHastaTelefonNo, tvHastaAdres, tvHastaCinsiyet, tvHastaBoyKilo,
            tvHastaBMI, tvHastaAktivite, tvHastaSigara, tvHastaAlkol, tvHastaLDL, tvHastaHDL, tvHastaTansiyon, tvHastaSeker,
            tvFollowing;

    Button bGoPatientECGSignalList, bFollow, bDefineRule;
    Patient patient = null;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        context = this;

        patient = (Patient) getIntent().getSerializableExtra("class");
        isFollowingByDoctor();
    }

    private void initialize() {

        try {

            try {
                bFollow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        new AsyncTask<Void, Void, Boolean>() {

                            ProgressDialog progressDialog = null;

                            @Override
                            protected void onPreExecute() {
                                super.onPreExecute();
                                progressDialog = ProgressDialog.show(context, "Lütfen Bekleyiniz", "İşleminiz gerçekleştiriliyor...");
                            }

                            @Override
                            protected Boolean doInBackground(Void... voids) {

                                FollowPatient followPatient = new FollowPatient(patient.getId());
                                try {
                                    return followPatient.makeRequest();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    cancel(true);
                                }
                                return false;
                            }

                            @Override
                            protected void onPostExecute(Boolean aBoolean) {
                                super.onPostExecute(aBoolean);
                                progressDialog.dismiss();

                                if (aBoolean) {
                                    Toast.makeText(context, "Hasta takip işlemi başarı ile sonuçlandı", Toast.LENGTH_LONG).show();
                                    tvFollowing.setVisibility(View.VISIBLE);
                                    bFollow.setVisibility(View.GONE);
                                } else {
                                    Toast.makeText(context, "Hasta takip işlemi sırasında bir sorun yaşandı", Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            protected void onCancelled() {
                                super.onCancelled();
                                Toast.makeText(context, "İşleminiz gerçekleştirilemiyor", Toast.LENGTH_LONG).show();
                            }
                        }.execute();
                    }
                });
            } catch (Exception e) {}



            try {
                tvHastaBMI = (TextView) findViewById(R.id.tvPatientBMI);
                tvHastaBMI.setText("BMI: " + String.valueOf(patient.getBmi()));
            } catch (Exception e) {}

            try {
                tvHastaAdi = (TextView) findViewById(R.id.tvPatientName);
                tvHastaAdi.setText(patient.getName() + " " + patient.getSurname());
            } catch (Exception e) {}

            try {
                tvHastaDogumGunu = (TextView) findViewById(R.id.tvPatientBirthday);
                tvHastaDogumGunu.setText(Util.dateToString(Util.milisecondToDate(patient.getBirthday().getTime())));
            } catch (Exception e) {}

            try {
                tvHastaTelefonNo = (TextView) findViewById(R.id.tvPatientPhoneNumber);
                tvHastaTelefonNo.setText("0" + patient.getPhoneNumber());
            } catch (Exception e) {}

            try {
                tvHastaAdres = (TextView) findViewById(R.id.tvPatientAddress);
                tvHastaAdres.setText(patient.getAddress());
            } catch (Exception e) {}

            try {
                tvHastaCinsiyet = (TextView) findViewById(R.id.tvPatientSex);
                if (patient.getSex() == Patient.PATIENT_SEX_MALE)
                    tvHastaCinsiyet.setText("Erkek");
                else if (patient.getSex() == Patient.PATIENT_SEX_FEMALE)
                    tvHastaCinsiyet.setText("Kadın");
                else
                    tvHastaCinsiyet.setText("Belirtilmedi");
            } catch (Exception e) {}

            try {
                tvHastaBoyKilo = (TextView) findViewById(R.id.tvPatientBoyKilo);
                tvHastaBoyKilo.setText(patient.getHeight() + " cm, " + patient.getWeight() + " kg");
            } catch (Exception e) {}

            try {
                tvHastaAktivite = (TextView) findViewById(R.id.tvPatientActivityFrequency);
                if (Patient.ACTIVITY_FREQUENCY_ADVANCED == patient.getActivityFrequency())
                    tvHastaAktivite.setText("Düzenli");
                else if (Patient.ACTIVITY_FREQUENCY_INTERMEDIATE == patient.getActivityFrequency())
                    tvHastaAktivite.setText("Fırsat buldukça");
                else if (Patient.ACTIVITY_FREQUENCY_NONE == patient.getActivityFrequency())
                    tvHastaAktivite.setText("Yapmıyor");
                else
                    tvHastaAktivite.setText("Belirtilmemiş");
            } catch (Exception e) {}

            try {
                tvHastaSigara = (TextView) findViewById(R.id.tvPatientSmokingFrequency);
                if (Patient.SMOKING_FREQUENCY_FREQUENT == patient.getSmokingFrequency())
                    tvHastaSigara.setText("Çok içiyor");
                else if (Patient.SMOKING_FREQUENCY_SOCIAL == patient.getSmokingFrequency())
                    tvHastaSigara.setText("Arada sırada");
                else if (Patient.SMOKING_FREQUENCY_NONE == patient.getSmokingFrequency())
                    tvHastaSigara.setText("İçmiyor");
                else if (Patient.SMOKING_FREQUENCY_QUIT == patient.getSmokingFrequency())
                    tvHastaSigara.setText("Bırakmış");
                else
                    tvHastaSigara.setText("Belirtilmemiş");
            } catch (Exception e) {}

            try {
                tvHastaAlkol = (TextView) findViewById(R.id.tvPatientAlcoholFrequency);
                if (Patient.ALCOHOL_FREQUENCY_FREQUENT == patient.getAlcoholUsageFrequency())
                    tvHastaAlkol.setText("Çok içiyor");
                else if (Patient.ALCOHOL_FREQUENCY_SOCIAL == patient.getAlcoholUsageFrequency())
                    tvHastaAlkol.setText("Arada sırada");
                else if (Patient.ALCOHOL_FREQUENCY_NONE == patient.getAlcoholUsageFrequency())
                    tvHastaAlkol.setText("İçmiyor");
                else if (Patient.ALCOHOL_FREQUENCY_QUIT == patient.getAlcoholUsageFrequency())
                    tvHastaAlkol.setText("Bırakmış");
                else
                    tvHastaAlkol.setText("Belirtilmemiş");
            } catch (Exception e) {}

            try {
                tvHastaLDL = (TextView) findViewById(R.id.tvPatientKolestrolLDL);
                tvHastaLDL.setText(String.valueOf(patient.getKolesterolLDL()));
            } catch (Exception e) {}

            try {
                tvHastaHDL = (TextView) findViewById(R.id.tvPatientKolestrolHDL);
                tvHastaHDL.setText(String.valueOf(patient.getKolesterolHDL()));
            } catch (Exception e) {}

            try {
                tvHastaTansiyon = (TextView) findViewById(R.id.tvPatientHighTension);
                if (patient.isHasHypertension())
                    tvHastaTansiyon.setText("Var");
                else
                    tvHastaTansiyon.setText("Yok");
            } catch (Exception e) {}

            try {
                tvHastaSeker = (TextView) findViewById(R.id.tvPatientDiabets);
                if (patient.isHasDiabetes())
                    tvHastaSeker.setText("Var");
                else
                    tvHastaSeker.setText("Yok");
            } catch (Exception e) {}
        } catch (Exception e) {
            Log.e("TAG", "Bir sorun cikti", e);
        }

        bGoPatientECGSignalList = (Button) findViewById(R.id.bGoPatientECGSignalList);
        bGoPatientECGSignalList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PatientDetailsActivity.this, LiveECG.class);
                i.putExtra("patient", patient);
                startActivity(i);
            }
        });

        bDefineRule = (Button) findViewById(R.id.bDefineRule);
        bDefineRule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DefineRule.class);
                intent.putExtra("patient", patient);
                startActivity(intent);
            }
        });
    }

    void isFollowingByDoctor() {
        new AsyncTask<Void, Void, List<Patient>>() {


            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                tvFollowing = (TextView) findViewById(R.id.tvFollowing);
                bFollow = (Button) findViewById(R.id.bFollow);
            }

            @Override
            protected List<Patient> doInBackground(Void... voids) {

                try {
                    return Patient.getList(HttpURL.OP_GET_ENROLLED_PATIENTS_LIST);
                } catch (IOException e) {
                    e.printStackTrace();
                    cancel(true);
                } catch (JSONException e) {
                    e.printStackTrace();
                    cancel(true);
                }

                return null;
            }

            @Override
            protected void onPostExecute(final List<Patient> patients) {
                super.onPostExecute(patients);

                initialize();

                if (patients.size() > 0) {
                    for (Patient patient1 : patients) {
                        if (patient1.getId().equals(patient.getId())) {
                            bFollow.setVisibility(View.GONE);
                            tvFollowing.setVisibility(View.VISIBLE);
                            break;
                        } else {
                            bFollow.setVisibility(View.VISIBLE);
                            tvFollowing.setVisibility(View.GONE);
                        }
                    }
                }
            }

            @Override
            protected void onCancelled() {
                super.onCancelled();

                bFollow.setVisibility(View.GONE);
                tvFollowing.setVisibility(View.VISIBLE);

                initialize();
                Toast.makeText(context, "Bir hata oluştu...", Toast.LENGTH_LONG).show();
            }
        }.execute();
    }
}