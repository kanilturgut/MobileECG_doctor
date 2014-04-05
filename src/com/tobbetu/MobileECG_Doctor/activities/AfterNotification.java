package com.tobbetu.MobileECG_Doctor.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.shimmerresearch.graph.GraphView;
import com.tobbetu.MobileECG_Doctor.R;
import com.tobbetu.MobileECG_Doctor.android_service.MobileECGDoctorService;
import com.tobbetu.MobileECG_Doctor.model.Anomaly;
import com.tobbetu.MobileECG_Doctor.model.Doctor;
import com.tobbetu.MobileECG_Doctor.model.Patient;
import com.tobbetu.MobileECG_Doctor.service.Login;
import com.tobbetu.MobileECG_Doctor.task.LoginTask;
import org.apache.http.HttpResponse;
import org.json.JSONException;

import java.io.IOException;

/**
 * Created by kanilturgut on 05/04/14, 14:22.
 */
public class AfterNotification extends Activity {

    Context context = null;
    Patient patient = null;
    Anomaly anomaly = null;

    GraphView graphView;
    TextView tv;
    LinearLayout llDetectedAnomalies;
    Button bMap;

    Handler handler = null;
    Runnable runnable = null;
    int i = 0;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_notification);
        context = this;

        new AutoLogin().execute("kanilturgut", "123", "asdsad13");

    }

    class AutoLogin extends AsyncTask<String, Void, Doctor> {

        @Override
        protected Doctor doInBackground(String... strings) {
            Login login = new Login(strings);

            try {
                HttpResponse response = login.makeRequest();
                return Doctor.getDoctor(response);

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
        protected void onPostExecute(Doctor doctor) {
            super.onPostExecute(doctor);

            if (doctor != null)
                new GetPatientWithId().execute();
            else
                Toast.makeText(context, "Sisteme girişte sorun yaşadınız", Toast.LENGTH_LONG).show();
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            Toast.makeText(context, "Sisteme girişte sorun yaşadınız", Toast.LENGTH_LONG).show();
        }
    }

    class GetPatientWithId extends AsyncTask<String, Void, Patient> {

        @Override
        protected Patient doInBackground(String... strings) {
            try {
                return Patient.getPatientWithId(MobileECGDoctorService.patientID);
            } catch (IOException e) {
                cancel(true);
                e.printStackTrace();
            } catch (JSONException e) {
                cancel(true);
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Patient result) {
            super.onPostExecute(result);

            if (result != null) {
                patient = result;
                new GetAnomalyWithId().execute();
            } else
                Toast.makeText(context, "Hasta bilgisi yanlış", Toast.LENGTH_LONG).show();
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();

            Toast.makeText(context, "Hasta bilgisi yanlış", Toast.LENGTH_LONG).show();
        }
    }

    class GetAnomalyWithId extends AsyncTask<String, Void, Anomaly> {

        ProgressDialog progressDialog = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(context, "Lütfen Bekleyiniz", patient.getName() + " " + patient.getSurname() + " adlı hastaya ait anomali yükleniyor");
        }

        @Override
        protected Anomaly doInBackground(String... strings) {
            try {
                return Anomaly.getPatientAnomalyWithId(MobileECGDoctorService.anomalyID);
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
        protected void onPostExecute(Anomaly result) {
            super.onPostExecute(result);

            if (progressDialog != null)
                progressDialog.dismiss();

            if (result != null) {
                anomaly = result;

                initialize();
            } else {
                Toast.makeText(context, "Anomali yüklenemiyor", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();

            if (progressDialog != null)
                progressDialog.dismiss();

            Toast.makeText(context, "Anomali yüklenemiyor", Toast.LENGTH_LONG).show();
        }
    }

    private void initialize() {
        graphView = (GraphView) findViewById(R.id.mGraphView);
        tv = (TextView) findViewById(R.id.tvAfterNotificationPatientName);
        tv.setText(patient.getName() + " " + patient.getSurname());

        llDetectedAnomalies = (LinearLayout) findViewById(R.id.llDetectedAnomalies);

        if (anomaly.getEcgDataList() != null && anomaly.getEcgDataList().size() != 0) {

            handler = new Handler();
            runnable = new Runnable() {
                @Override
                public void run() {
                    int[] datas = new int[2];
                    datas[0] = anomaly.getEcgDataList().get(i).getRAW_ra_ll();
                    datas[1] = anomaly.getEcgDataList().get(i).getRAW_la_ll();
                    graphView.setDataWithAdjustment(datas, "Shimmer", "u12");

                    i++;
                    if (i == anomaly.getEcgDataList().size())
                        handler.removeCallbacks(runnable);
                    else
                        handler.postDelayed(runnable, 5);
                }
            };

            handler.postDelayed(runnable, 0);

            boolean[] detectedAnomalies = anomaly.getDetectedAnomalies();

            if (detectedAnomalies[0]) {
                TextView textView = new TextView(context);
                textView.setGravity(Gravity.CENTER_HORIZONTAL);
                textView.setTextSize(16);
                textView.setText("PQ arası anomali");
                llDetectedAnomalies.addView(textView);
            }
            if (detectedAnomalies[1]) {
                TextView textView = new TextView(context);
                textView.setGravity(Gravity.CENTER_HORIZONTAL);
                textView.setTextSize(16);
                textView.setText("QRS arası anomali");
                llDetectedAnomalies.addView(textView);
            }
            if (detectedAnomalies[2]) {
                TextView textView = new TextView(context);
                textView.setGravity(Gravity.CENTER_HORIZONTAL);
                textView.setTextSize(16);
                textView.setText("QT arası anomali");
                llDetectedAnomalies.addView(textView);
            }

        } else {
            Toast.makeText(context, "Bir sorun oluştu", Toast.LENGTH_LONG).show();
        }

        bMap = (Button) findViewById(R.id.showOnMap);
        bMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int size = (anomaly.getEcgDataList().size() / 2);

                Intent intent = new Intent(context, MapActivity.class);
                intent.putExtra("latitude", anomaly.getEcgDataList().get(size).getLatitude());
                intent.putExtra("longitude", anomaly.getEcgDataList().get(size).getLongitude());
                startActivity(intent);
            }
        });
    }
}