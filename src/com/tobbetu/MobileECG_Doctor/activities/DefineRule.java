package com.tobbetu.MobileECG_Doctor.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.tobbetu.MobileECG_Doctor.R;
import com.tobbetu.MobileECG_Doctor.model.Doctor;
import com.tobbetu.MobileECG_Doctor.model.Patient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by kanilturgut on 05/04/14, 22:50.
 */
public class DefineRule extends Activity implements CompoundButton.OnCheckedChangeListener {

    Context context = null;

    Patient patient = null;

    CheckBox cbPQ, cbQRS, cbQT;
    EditText etPQMin, etPQMax, etQRSMin, etQRSMax, etQTMin, etQTMax;
    Button bSave;

    ProgressDialog progressDialog = null;

    static int success = 0;
    int expected = 0;

    final int INTERVAL_TYPE_PQ = 0;
    final int INTERVAL_TYPE_QRS = 1;
    final int INTERVAL_TYPE_QT = 2;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_define_rule);
        context = this;

        patient = (Patient) getIntent().getSerializableExtra("patient");

        cbPQ = (CheckBox) findViewById(R.id.cbPQ);
        cbPQ.setOnCheckedChangeListener(this);

        cbQRS = (CheckBox) findViewById(R.id.cbQRS);
        cbQRS.setOnCheckedChangeListener(this);

        cbQT = (CheckBox) findViewById(R.id.cbQT);
        cbQT.setOnCheckedChangeListener(this);

        etPQMin = (EditText) findViewById(R.id.etPQMin);
        etPQMax = (EditText) findViewById(R.id.etPQMax);
        etQRSMin = (EditText) findViewById(R.id.etQRSMin);
        etQRSMax = (EditText) findViewById(R.id.etQRSMax);
        etQTMin = (EditText) findViewById(R.id.etQTMin);
        etQTMax = (EditText) findViewById(R.id.etQTMax);

        bSave = (Button) findViewById(R.id.bSaveRules);
        bSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressDialog = ProgressDialog.show(context, "Lütfen Bekleyiniz", "Tanımladığınız yeni kurallar sisteme kaydediliyor", false, false);

                if (cbPQ.isChecked()) {
                    if (checkForMinMax(etPQMin, etPQMax)) {
                        expected++;
                        new PQ().execute();
                    }
                }
                if (cbQRS.isChecked()) {
                    if (checkForMinMax(etQRSMin, etQRSMax)) {
                        expected++;
                        new QRS().execute();
                    }
                }
                if (cbQT.isChecked()) {
                    if (checkForMinMax(etQTMin, etQTMax)) {
                        expected++;
                        new QT().execute();
                    }
                }
            }
        });
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

        if (compoundButton.getId() == R.id.cbPQ) {
            etPQMin.setEnabled(b);
            etPQMax.setEnabled(b);
        } else if (compoundButton.getId() == R.id.cbQRS) {
            etQRSMin.setEnabled(b);
            etQRSMax.setEnabled(b);
        } else {
            etQTMin.setEnabled(b);
            etQTMax.setEnabled(b);
        }
    }


    class PQ extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... voids) {

            JSONObject jsonObject = new JSONObject();

            try {
                jsonObject.put("patient", patient.getId());
                jsonObject.put("intervalType", INTERVAL_TYPE_PQ);
                if (!etPQMin.getText().toString().equals(""))
                    jsonObject.put("intervalMin", Integer.parseInt(etPQMin.getText().toString()));
                if (!etPQMax.getText().toString().equals(""))
                    jsonObject.put("intervalMax", Integer.parseInt(etPQMax.getText().toString()));

                return Doctor.changeRule(jsonObject.toString());

            } catch (JSONException e) {
                e.printStackTrace();
                cancel(true);
            } catch (IOException e) {
                e.printStackTrace();
                cancel(true);
            }

            return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);


            if (aBoolean)
                success++;

            checkAllTasksFinisihed();
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();

            if (progressDialog != null)
                progressDialog.dismiss();
        }
    }

    class QRS extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... voids) {

            JSONObject jsonObject = new JSONObject();

            try {
                jsonObject.put("patient", patient.getId());
                jsonObject.put("intervalType", INTERVAL_TYPE_QRS);
                if (!etQRSMin.getText().toString().equals(""))
                    jsonObject.put("intervalMin", Integer.parseInt(etQRSMin.getText().toString()));
                if (!etQRSMax.getText().toString().equals(""))
                    jsonObject.put("intervalMax", Integer.parseInt(etQRSMax.getText().toString()));

                return Doctor.changeRule(jsonObject.toString());

            } catch (JSONException e) {
                e.printStackTrace();
                cancel(true);
            } catch (IOException e) {
                e.printStackTrace();
                cancel(true);
            }

            return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

            if (aBoolean)
                success++;

            checkAllTasksFinisihed();
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();

            if (progressDialog != null)
                progressDialog.dismiss();
        }
    }

    class QT extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... voids) {

            JSONObject jsonObject = new JSONObject();

            try {
                jsonObject.put("patient", patient.getId());
                jsonObject.put("intervalType", INTERVAL_TYPE_QT);
                if (!etQTMin.getText().toString().equals(""))
                    jsonObject.put("intervalMin", Integer.parseInt(etQTMin.getText().toString()));
                if (!etQTMax.getText().toString().equals(""))
                    jsonObject.put("intervalMax", Integer.parseInt(etQTMax.getText().toString()));

                return Doctor.changeRule(jsonObject.toString());

            } catch (JSONException e) {
                e.printStackTrace();
                cancel(true);
            } catch (IOException e) {
                e.printStackTrace();
                cancel(true);
            }

            return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

            if (aBoolean)
                success++;

            checkAllTasksFinisihed();
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();

            if (progressDialog != null)
                progressDialog.dismiss();
        }
    }


    void checkAllTasksFinisihed() {

        if (expected == success) {
            progressDialog.dismiss();

            Toast.makeText(context, "Tanımladığınız kurallar sisteme yüklenmiştir", Toast.LENGTH_LONG).show();
        }

    }

    boolean checkForMinMax(EditText min, EditText max) {

        if (min.getText().toString().equals("") && max.getText().toString().equals(""))
            return false;
        else
            return true;
    }

}