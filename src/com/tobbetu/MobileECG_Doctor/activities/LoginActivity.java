package com.tobbetu.MobileECG_Doctor.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.tobbetu.MobileECG_Doctor.R;
import com.tobbetu.MobileECG_Doctor.task.LoginTask;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by kanilturgut on 25/03/14.
 */
public class LoginActivity extends Activity implements View.OnClickListener {

    EditText etUsername, etPassword;
    TextView tvRegister;
    Button bDoLogin;

    Context context;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initialize();
    }

    private void initialize() {

        //bindings
        etUsername = (EditText) findViewById(R.id.etLoginUsername);
        etPassword = (EditText) findViewById(R.id.etLoginPassword);
        tvRegister = (TextView) findViewById(R.id.tvRegister);
        bDoLogin = (Button) findViewById(R.id.bDoLogin);

        //set listeners
        tvRegister.setOnClickListener(this);
        bDoLogin.setOnClickListener(this);

        //etUsername.setText("kanilturgut");
        //etPassword.setText("123");

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bDoLogin:
                String userName = etUsername.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                String regID = "sdasdsada";

                new LoginTask(LoginActivity.this).execute(userName, password, regID);
                break;
            case R.id.tvRegister:
                Intent i = new Intent(this, RegisterActivity.class);
                i.putExtra("regid", "sdasdsada");
                startActivity(i);
                break;
        }
    }
}