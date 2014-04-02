package com.tobbetu.MobileECG_Doctor.android_service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import com.parse.Parse;

/**
 * Created by kanilturgut on 02/04/14, 23:03.
 */
public class MobileECGDoctorService extends Service {

    public static String anomalyID;
    public static String patientID;
    public static boolean fromService = false;

    private IBinder mBinder = new LocalBinder();

    public class LocalBinder extends Binder {
        public MobileECGDoctorService getServerInstance() {
            return MobileECGDoctorService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(getApplicationContext(), "Otb0jFkKfqV54fkZn5OVilaNoZ6yrnTYADs9cCc6", "RjMoc9RDfqsdZIIbr4T9VAhCrbRjO6Y6abeImYI9");
    }

}