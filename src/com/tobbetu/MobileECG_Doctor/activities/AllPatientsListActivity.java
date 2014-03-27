package com.tobbetu.MobileECG_Doctor.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.tobbetu.MobileECG_Doctor.R;
import com.tobbetu.MobileECG_Doctor.model.Patient;
import com.tobbetu.MobileECG_Doctor.util.HttpURL;
import org.json.JSONException;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;


/**
 * Created by kanilturgut on 16/03/14.
 */
public class AllPatientsListActivity extends Activity {

    ProgressDialog progressDialog = null;
    ListView lvListOfPatients;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();
    }

    private void initialize() {

        new AsyncTask<Void, Void, List<Patient>>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = ProgressDialog.show(AllPatientsListActivity.this, "Lütfen Bekleyiniz", "Takip ettiğiniz hastalarınız listesi getiriliyor");
            }

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
            protected void onPostExecute(final List<Patient> patients) {
                super.onPostExecute(patients);

                progressDialog.dismiss();

                String[] hastalar = new String[patients.size()];
                for(int i = 0; i < patients.size(); i++) {
                    hastalar[i] = patients.get(i).getName() + " " + patients.get(i).getSurname();
                }

                lvListOfPatients = (ListView) findViewById(R.id.lvListOfPatients);
                lvListOfPatients.setAdapter(new ArrayAdapter<String>(AllPatientsListActivity.this, android.R.layout.simple_list_item_1, hastalar));
                lvListOfPatients.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent intent = new Intent(AllPatientsListActivity.this, HastaActivity.class);
                        intent.putExtra("class", patients.get(i));
                        startActivity(intent);
                    }
                });

            }
        }.execute();



    }
}