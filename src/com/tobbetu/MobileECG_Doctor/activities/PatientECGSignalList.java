package com.tobbetu.MobileECG_Doctor.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import com.tobbetu.MobileECG_Doctor.R;
import com.tobbetu.MobileECG_Doctor.model.Anomaly;
import com.tobbetu.MobileECG_Doctor.model.Patient;
import com.tobbetu.MobileECG_Doctor.util.Util;
import org.json.JSONException;

import java.io.IOException;
import java.util.List;

/**
 * Created by kanilturgut on 16/03/14.
 */
public class PatientECGSignalList extends Activity {

    Context context = null;
    ListView lvECGSignals;
    Patient patient = null;

    List<Anomaly> anomalyList = null;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_ecgsignals_list);
        context = this;

        patient = (Patient) getIntent().getSerializableExtra("class");

        new AsyncTask<String, Void, List<Anomaly>>() {

            ProgressDialog progressDialog = null;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = ProgressDialog.show(context, "Lütfen Bekleyiniz", "Hastaya ait anomali tespiti yapılan ecg sinyalleri yükleniyor");
            }

            @Override
            protected List<Anomaly> doInBackground(String... strings) {

                try {
                    return Anomaly.getPatientAnomalies(patient.getId());
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
            protected void onPostExecute(List<Anomaly> anomalies) {
                super.onPostExecute(anomalies);

                if (progressDialog != null)
                    progressDialog.dismiss();

                if (anomalies != null) {
                    anomalyList = anomalies;
                    initialize();
                }

            }

            @Override
            protected void onCancelled() {
                super.onCancelled();

                if (progressDialog != null)
                    progressDialog.dismiss();

                Toast.makeText(context, "Üzgünüz ama sinyalleri listeleyemiyoruz. Lütfen daha sonra tekrar deneyiniz.", Toast.LENGTH_LONG).show();
            }
        }.execute();




    }

    private void initialize() {

        String[] ECG_SIGNALS = new String[anomalyList.size()];

        if (anomalyList != null) {
            for (int i = 0; i < anomalyList.size(); i++)
                ECG_SIGNALS[i] = Util.dateToString(anomalyList.get(i).getDate());

            lvECGSignals = (ListView) findViewById(R.id.lvECGListOfPatient);
            lvECGSignals.setAdapter(new ArrayAdapter<String>(PatientECGSignalList.this,
                    android.R.layout.simple_list_item_1, ECG_SIGNALS));
            lvECGSignals.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(PatientECGSignalList.this, GrapViewOfPatient.class);
                    intent.putExtra("patient", patient);
                    intent.putExtra("anomaly", anomalyList.get(i));
                    startActivity(intent);
                }
            });
        } else {
            Toast.makeText(context, "Üzgünüz ama sinyalleri listeleyemiyoruz. Lütfen daha sonra tekrar deneyiniz.", Toast.LENGTH_LONG).show();
        }




    }
}