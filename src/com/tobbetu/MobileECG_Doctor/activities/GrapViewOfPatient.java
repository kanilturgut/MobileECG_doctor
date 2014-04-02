package com.tobbetu.MobileECG_Doctor.activities;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.widget.TextView;
import com.pubnub.api.Callback;
import com.pubnub.api.Pubnub;
import com.pubnub.api.PubnubError;
import com.pubnub.api.PubnubException;
import com.shimmerresearch.graph.GraphView;
import com.tobbetu.MobileECG_Doctor.R;
import com.tobbetu.MobileECG_Doctor.model.ECGData;
import com.tobbetu.MobileECG_Doctor.model.Patient;
import com.tobbetu.MobileECG_Doctor.util.Util;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by kanilturgut on 16/03/14.
 */
public class GrapViewOfPatient extends Activity {

    Patient patient = null;

    GraphView graphView;
    TextView tv;

    Handler handler = null;
    Runnable runnable = null;
    int i = 0;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_view_of_patient);

        patient = (Patient) getIntent().getSerializableExtra("class");

        graphView = (GraphView) findViewById(R.id.mGraphView);
        tv = (TextView) findViewById(R.id.tvDeneme);

        tv.setText(patient.getName() + " " + patient.getSurname());

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

           // handler.postDelayed(runnable, 5);

        }

        Pubnub pubnub = new Pubnub("pub-c-13b31cee-ef79-440f-b46d-e3804f3d5435", "sub-c-3a5a7350-b28d-11e3-b8c3-02ee2ddab7fe");

        try {
            pubnub.subscribe("hello_world", new Callback() {

                @Override
                public void connectCallback(String channel, Object message) {
                    System.out.println("SUBSCRIBE : CONNECT on channel:" + channel
                            + " : " + message.getClass() + " : "
                            + message.toString());
                }

                @Override
                public void disconnectCallback(String channel, Object message) {
                    System.out.println("SUBSCRIBE : DISCONNECT on channel:" + channel
                            + " : " + message.getClass() + " : "
                            + message.toString());
                }

                public void reconnectCallback(String channel, Object message) {
                    System.out.println("SUBSCRIBE : RECONNECT on channel:" + channel
                            + " : " + message.getClass() + " : "
                            + message.toString());
                }

                @Override
                public void successCallback(String channel, Object message) {

                    final String msg = message.toString();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {

                                JSONArray array = new JSONArray(msg);

                                for (int i = 0; i < array.length() ; i++) {
                                    JSONObject obj = array.getJSONObject(i);
                                    int[] datas = new int[2];
                                    datas[0] = obj.getInt("r");
                                    datas[1] = obj.getInt("l");
                                    graphView.setDataWithAdjustment(datas, "Shimmer", "u12");
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });


                }

                @Override
                public void errorCallback(String channel, PubnubError error) {
                    System.out.println("SUBSCRIBE : ERROR on channel " + channel
                            + " : " + error.toString());
                }
            });
        } catch (PubnubException e) {
            System.out.println(e.toString());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }
}