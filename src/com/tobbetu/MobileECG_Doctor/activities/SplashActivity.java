package com.tobbetu.MobileECG_Doctor.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import com.parse.Parse;
import com.parse.PushService;
import com.tobbetu.MobileECG_Doctor.R;

public class SplashActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Parse.initialize(this, "Otb0jFkKfqV54fkZn5OVilaNoZ6yrnTYADs9cCc6", "RjMoc9RDfqsdZIIbr4T9VAhCrbRjO6Y6abeImYI9");
        //Parse.initialize(this, "r9ukJq7pTEV0i6cL78CVP9InuV1wDPkSAaWDCDg2", "E9rDS9WhJaNau98Q90FwAn8JyBaYfEDWz2zmSgiT");


        PushService.subscribe(this, "deneme", LoginActivity.class);
        //PushService.setDefaultPushCallback(this, LoginActivity.class);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                finish();
            }
        }, 1000);
    }
}
