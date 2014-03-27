package com.tobbetu.MobileECG_Doctor.receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.tobbetu.MobileECG_Doctor.R;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * Created by kanilturgut on 25/03/14.
 */
public class GcmBroadcastReceiver extends BroadcastReceiver{

    GoogleCloudMessaging gcm = null;
    NotificationManager notificationManager = null;
    public static final int NOTIFICATION_ID = 001;

    @Override
    public void onReceive(Context context, Intent intent) {

        try {
            String action = intent.getAction();
            String channel = intent.getExtras().getString("com.parse.Channel");
            JSONObject json = new JSONObject(intent.getExtras().getString("com.parse.Data"));

            Log.d("GcmBroadcastReceiver", "got action " + action + " on channel " + channel + " with:");
            Iterator itr = json.keys();
            while (itr.hasNext()) {
                String key = (String) itr.next();
                Log.d("GcmBroadcastReceiver", "..." + key + " => " + json.getString(key));
            }
        } catch (JSONException e) {
            Log.d("GcmBroadcastReceiver", "JSONException: " + e.getMessage());
        }

        /*
        if (gcm == null)
            gcm = GoogleCloudMessaging.getInstance(context);

        Bundle bundle = intent.getExtras();
        String messageType = gcm.getMessageType(intent);

        if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType) &&
                !bundle.isEmpty()) {
            Log.i("GcmBroadcastReceiver", bundle.getString("msg"));
            sendNotification(context, bundle);
        }

        */
    }

    private void sendNotification(Context context, Bundle bundle) {

        if (notificationManager == null)
            notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Bitmap icon = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.ic_launcher);

        Notification.Builder builder = new Notification.Builder(context);
        builder.setLargeIcon(icon)
                .setContentTitle(bundle.getString("title"))
                .setContentText(bundle.getString("msg"))
                .setAutoCancel(true);

        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }
}
