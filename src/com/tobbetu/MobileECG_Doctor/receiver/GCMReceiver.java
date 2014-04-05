package com.tobbetu.MobileECG_Doctor.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.tobbetu.MobileECG_Doctor.android_service.MobileECGDoctorService;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kanilturgut on 27/03/14, 17:24.
 */
public class GCMReceiver extends BroadcastReceiver{


    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            JSONObject json = new JSONObject(intent.getExtras().getString("com.parse.Data"));


            JSONObject data = json.getJSONObject("data");

            String anomalyID = data.getString("anomalyID");
            String patientID = data.getString("patientID");

            MobileECGDoctorService.anomalyID = anomalyID;
            MobileECGDoctorService.patientID = patientID;
            MobileECGDoctorService.fromService = true;

        } catch (JSONException e) {
            Log.d("GCMReceiver", "JSONException: " + e.getMessage());
        }
    }

}
