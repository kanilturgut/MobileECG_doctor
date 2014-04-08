package com.tobbetu.MobileECG_Doctor.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.shimmerresearch.graph.GraphView;
import com.tobbetu.MobileECG_Doctor.R;
import com.tobbetu.MobileECG_Doctor.model.ECGData;
import com.tobbetu.MobileECG_Doctor.model.Patient;
import com.tobbetu.MobileECG_Doctor.util.Util;
import org.json.JSONException;

import java.io.IOException;
import java.util.List;

/**
 * Created by kanilturgut on 04/04/14, 21:52.
 */
public class PastECGDatas extends Activity implements View.OnClickListener {

    Context context = null;

    TextView tvPatientName, tvBeginningDate, tvEndDate;
    GraphView graphView;

    Patient patient = null;

    Handler handler = null;
    Runnable runnable = null;

    Button playButton = null;
    Button pauseButton = null;

    List<ECGData> ecgDataList = null;
    int index = 0;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_ecgdatas);
        context = this;

        patient = (Patient) getIntent().getSerializableExtra("patient");

        final String start = getIntent().getStringExtra("start");
        final String end = getIntent().getStringExtra("end");

        new AsyncTask<String, Void, List<ECGData>>() {

            ProgressDialog progressDialog = null;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = ProgressDialog.show(context, "Lütfen Bekleyiniz", "Hastaya ait anomali tespiti yapılan ecg sinyalleri yükleniyor");
            }

            @Override
            protected List<ECGData> doInBackground(String... strings) {

                try {
                    return ECGData.getQueriedDatas(Util.stringToDate(start), Util.stringToDate(end), patient.getId());
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
            protected void onPostExecute(List<ECGData> ecgDatas) {
                super.onPostExecute(ecgDatas);

                if (progressDialog != null)
                    progressDialog.dismiss();

                if (ecgDatas != null && ecgDatas.size() > 0) {
                    ecgDataList = ecgDatas;
                    initialize(start, end);
                } else {
                    Toast.makeText(context, "Bu tarihler arasında kayıtlı veri bulunmamaktadır.", Toast.LENGTH_LONG).show();
                    finish();
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

    void initialize(String s, String e) {

        tvPatientName = (TextView) findViewById(R.id.tvGraphViewPatientName);
        tvBeginningDate = (TextView) findViewById(R.id.tvGraphBeginningTime);
        tvEndDate = (TextView) findViewById(R.id.tvGraphEndTime);

        graphView = (GraphView) findViewById(R.id.mGraphView);

        tvPatientName.setText(patient.getName() + " " + patient.getSurname());
        tvBeginningDate.setText(s);
        tvEndDate.setText(e);

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

    private void startPainting() {

        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                int[] datas = new int[2];
                datas[0] = ecgDataList.get(index).getRAW_ra_ll();
                datas[1] = ecgDataList.get(index).getRAW_la_ll();
                graphView.setDataWithAdjustment(datas, "Shimmer", "u12");

                index++;

                if (index == ecgDataList.size())
                    handler.removeCallbacks(runnable);
                else
                    handler.postDelayed(runnable, 5);
            }
        };

        handler.postDelayed(runnable, 0);
    }
}