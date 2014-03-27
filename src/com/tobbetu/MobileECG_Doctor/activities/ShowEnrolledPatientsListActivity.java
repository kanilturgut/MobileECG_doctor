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
import com.tobbetu.MobileECG_Doctor.model.Patient;
import com.tobbetu.MobileECG_Doctor.util.HttpURL;
import org.json.JSONException;

import java.io.IOException;
import java.util.List;

/**
 * Created by kanilturgut on 27/03/14, 19:12.
 */
public class ShowEnrolledPatientsListActivity extends Activity{

    ListView listView = null;
    Context context = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_enrolled_patients);
        context = this;

        listView = (ListView) findViewById(R.id.lvListOfEnrolledPatients);


        new AsyncTask<Void, Void, List<Patient>>() {

            ProgressDialog progressDialog = null;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = ProgressDialog.show(context, "Lütfen Bekleyiniz", "Takip ettiğiniz hastaların listesi yükleniyor...");
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

                if (progressDialog != null)
                    progressDialog.dismiss();

                if (patients.size() > 0) {
                    String[] hastalar = new String[patients.size()];
                    for(int i = 0; i < patients.size(); i++) {
                        hastalar[i] = patients.get(i).getName() + " " + patients.get(i).getSurname();
                    }

                    listView.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, hastalar));
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Intent intent = new Intent(context, HastaActivity.class);
                            intent.putExtra("class", patients.get(i));
                            startActivity(intent);
                        }
                    });
                } else {
                    Toast.makeText(context, "Hiç bir hastayı takip etmiyorsunuz", Toast.LENGTH_LONG).show();
                }


            }

            @Override
            protected void onCancelled() {
                super.onCancelled();

                if (progressDialog != null)
                    progressDialog.dismiss();

                Toast.makeText(context, "Bir hata oluştu...", Toast.LENGTH_LONG).show();
            }
        }.execute();

    }
}
