package com.tobbetu.MobileECG_Doctor.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.SyncStateContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.tobbetu.MobileECG_Doctor.R;
import com.tobbetu.MobileECG_Doctor.model.Patient;
import com.tobbetu.MobileECG_Doctor.util.HttpURL;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;


/**
 * Created by kanilturgut on 16/03/14.
 */
public class MainActivity extends Activity {

    ProgressDialog progressDialog = null;

    List<Patient> patientList;

    String HASTALAR[] = {"Kadir Anıl Turğut", "Umut Ozan Yıldırım", "Tansel Özyer", "Onur Can Sert"};

    ListView lvListOfPatients;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressDialog = ProgressDialog.show(this, "Lütfen Bekliyiniz", "Takip ettiğiniz hastalarınız listesi getiriliyor");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressDialog.dismiss();
                initialize();
            }
        }, 3000);

    }

    private void initialize() {

        patientList = new LinkedList<Patient>();

        new AsyncTask<Void, Void, List<Patient>>() {
            @Override
            protected List<Patient> doInBackground(Void... voids) {
                try {
                    return Patient.getList(HttpURL.OP_PATIENT_LIST);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            protected void onPostExecute(List<Patient> patients) {
                super.onPostExecute(patients);

                String[] hastalar = new String[patients.size()];
                for(int i = 0; i < patients.size(); i++) {
                    hastalar[i] = patients.get(i).getName() + " " + patients.get(i).getSurname();
                }

                lvListOfPatients = (ListView) findViewById(R.id.lvListOfPatients);
                lvListOfPatients.setAdapter(new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, hastalar));
                lvListOfPatients.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent intent = new Intent(MainActivity.this, HastaActivity.class);
                        intent.putExtra("index", i);
                        startActivity(intent);
                    }
                });

            }
        }.execute();



    }
}