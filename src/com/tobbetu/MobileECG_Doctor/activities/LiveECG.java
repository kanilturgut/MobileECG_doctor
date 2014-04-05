package com.tobbetu.MobileECG_Doctor.activities;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;
import com.pubnub.api.Callback;
import com.pubnub.api.Pubnub;
import com.pubnub.api.PubnubError;
import com.pubnub.api.PubnubException;
import com.shimmerresearch.graph.GraphView;
import com.tobbetu.MobileECG_Doctor.R;
import com.tobbetu.MobileECG_Doctor.model.Patient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

/**
 * Created by kanilturgut on 05/04/14, 13:55.
 */
public class LiveECG extends Activity {

    Context context = null;
    Patient patient = null;

    TextView tvPatientName;
    GraphView graphView;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ecg_live);
        context = this;

        patient = (Patient) getIntent().getSerializableExtra("patient");

        initialize();

    }

    private void initialize() {
        tvPatientName = (TextView) findViewById(R.id.tvECGLivePatientName);
        tvPatientName.setText(patient.getName() + " " + patient.getSurname());

        graphView = (GraphView) findViewById(R.id.mGraphView);

        startPubnup();
    }

    void startPubnup() {

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
}