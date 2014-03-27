package com.tobbetu.MobileECG_Doctor.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.tobbetu.MobileECG_Doctor.R;
import com.tobbetu.MobileECG_Doctor.model.Patient;

/**
 * Created by kanilturgut on 16/03/14.
 */
public class PatientECGSignalList extends Activity {

    ProgressDialog progressDialog = null;
    ListView lvECGSignals;
    Patient patient = null;

    String ECG_SIGNALS[] = {"16.03.2014", "13.03.2014", "09.03.2014", "25.02.2014"};

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_ecgsignals_list);

        patient = (Patient) getIntent().getSerializableExtra("class");

        progressDialog = ProgressDialog.show(this, "Lütfen Bekleyiniz", "Hastaya ait anomali tespiti yapılan ecg sinyalleri yükleniyor");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressDialog.dismiss();
                initialize();
            }
        }, 500);
    }

    private void initialize() {

        lvECGSignals = (ListView) findViewById(R.id.lvECGListOfPatient);
        lvECGSignals.setAdapter(new ArrayAdapter<String>(PatientECGSignalList.this, android.R.layout.simple_list_item_1, ECG_SIGNALS));
        lvECGSignals.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(PatientECGSignalList.this, GrapViewOfPatient.class);
                intent.putExtra("index", i);
                intent.putExtra("class", patient);
                startActivity(intent);
            }
        });

    }
}