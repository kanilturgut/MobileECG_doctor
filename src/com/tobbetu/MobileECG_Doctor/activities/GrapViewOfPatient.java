package com.tobbetu.MobileECG_Doctor.activities;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import com.shimmerresearch.graph.GraphView;
import com.tobbetu.MobileECG_Doctor.R;
import com.tobbetu.MobileECG_Doctor.model.ECGData;
import com.tobbetu.MobileECG_Doctor.model.User;
import com.tobbetu.MobileECG_Doctor.util.Util;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by kanilturgut on 16/03/14.
 */
public class GrapViewOfPatient extends Activity {

    User user = null;

    GraphView graphView;
    TextView tv;

    Handler handler = null;
    Runnable runnable = null;
    int i = 0;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_view_of_patient);

        user = (User) getIntent().getSerializableExtra("class");

        graphView = (GraphView) findViewById(R.id.mGraphView);
        tv = (TextView) findViewById(R.id.tvDeneme);

        tv.setText(user.getName() + " " + user.getSurname());

        int index = getIntent().getIntExtra("index", -1);
        final List<ECGData> list;

        switch (index) {
            case 0:
                list = Util.readFromText(this, "a.txt");
                break;
            case 1:
                list = Util.readFromText(this, "b.txt");
                break;
            case 2:
                list = Util.readFromText(this, "c.txt");
                break;
            case 3:
                list = Util.readFromText(this, "d.txt");
                break;
            default:
                list = null;
                break;
        }

        if (list != null) {

            handler = new Handler();
            runnable = new Runnable() {
                @Override
                public void run() {
                    int[] datas = new int[2];
                    datas[0] = list.get(i).getRAW_ra_ll();
                    datas[1] = list.get(i).getRAW_la_ll();
                    graphView.setDataWithAdjustment(datas, "Shimmer", "u12");
                    i++;
                    if (i == 199)
                        handler.removeCallbacks(runnable);
                    else
                        handler.postDelayed(runnable, 5);
                }
            };

            handler.postDelayed(runnable, 5);

        }

    }
}