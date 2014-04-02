package com.tobbetu.MobileECG_Doctor.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import com.parse.Parse;
import com.parse.PushService;
import com.tobbetu.MobileECG_Doctor.R;
import com.tobbetu.MobileECG_Doctor.android_service.MobileECGDoctorService;

public class SplashActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        startService(new Intent(this, MobileECGDoctorService.class));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                finish();
            }
        }, 1000);
    }
}
