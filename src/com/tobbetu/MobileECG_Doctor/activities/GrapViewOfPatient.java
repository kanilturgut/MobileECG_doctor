package com.tobbetu.MobileECG_Doctor.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.*;
import com.shimmerresearch.graph.GraphView;
import com.tobbetu.MobileECG_Doctor.R;
import com.tobbetu.MobileECG_Doctor.model.Anomaly;
import com.tobbetu.MobileECG_Doctor.model.ECGData;
import com.tobbetu.MobileECG_Doctor.model.Patient;
import org.json.JSONException;

import java.io.IOException;
import java.util.List;

/**
 * Created by kanilturgut on 16/03/14
 */
public class GrapViewOfPatient extends Activity implements View.OnClickListener {

    Context context = null;
    Anomaly anomaly = null;
    Patient patient = null;

    GraphView graphView;
    TextView tv;
    LinearLayout llDetectedAnomalies;
    Button playButton = null;
    Button pauseButton = null;

    Handler handler = null;
    Runnable runnable = null;
    int i = 0;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_view_of_patient);
        context = this;

        patient = (Patient) getIntent().getSerializableExtra("patient");
        anomaly = (Anomaly) getIntent().getSerializableExtra("anomaly");

        new AsyncTask<String, Void, Anomaly>() {

            ProgressDialog progressDialog = null;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = ProgressDialog.show(context, "Lütfen Bekleyiniz", "Anomaliye ait veriler yükleniyor", false, false);
            }

            @Override
            protected Anomaly doInBackground(String... strings) {
                try {

                    return Anomaly.getPatientAnomalyWithId(anomaly.getId());
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
            protected void onPostExecute(Anomaly _anomaly) {
                super.onPostExecute(anomaly);

                if (progressDialog != null)
                    progressDialog.dismiss();

                if (_anomaly != null) {
                    anomaly = _anomaly;

                    initialize();
                } else {
                    Toast.makeText(context, "Bir sorun oluştu", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            protected void onCancelled() {
                super.onCancelled();

                if (progressDialog != null) {
                    progressDialog.dismiss();
                }

                Toast.makeText(context, "Bir sorun oluştu", Toast.LENGTH_LONG).show();
            }
        }.execute();
    }

    private void initialize() {
        graphView = (GraphView) findViewById(R.id.mGraphView);
        tv = (TextView) findViewById(R.id.tvDeneme);
        tv.setText(patient.getName() + " " + patient.getSurname());

        llDetectedAnomalies = (LinearLayout) findViewById(R.id.llDetectedAnomalies);

        playButton = (Button) findViewById(R.id.bPlay);
        playButton.setOnClickListener(this);

        pauseButton = (Button) findViewById(R.id.bPause);
        pauseButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.bPlay) {
            startPainting();

            playButton.setVisibility(Button.INVISIBLE);
            pauseButton.setVisibility(Button.VISIBLE);

        } else {
            handler.removeCallbacks(runnable);

            playButton.setVisibility(Button.VISIBLE);
            pauseButton.setVisibility(Button.INVISIBLE);
        }

    }

    void startPainting() {
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
    }
}