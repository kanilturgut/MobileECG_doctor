package com.tobbetu.MobileECG_Doctor.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * Created by kanilturgut on 27/03/14, 17:24.
 */
public class GCMReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            String action = intent.getAction();
            String channel = intent.getExtras().getString("com.parse.Channel");
            JSONObject json = new JSONObject(intent.getExtras().getString("com.parse.Data"));

            Log.d("GCMReceiver", "got action " + action + " on channel " + channel + " with:");
            Iterator itr = json.keys();
            while (itr.hasNext()) {
                String key = (String) itr.next();
                Log.d("GCMReceiver", "..." + key + " => " + json.getString(key));
            }
        } catch (JSONException e) {
            Log.d("GCMReceiver", "JSONException: " + e.getMessage());
        }
    }
}
