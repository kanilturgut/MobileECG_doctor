package com.tobbetu.MobileECG_Doctor.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.tobbetu.MobileECG_Doctor.R;
import com.tobbetu.MobileECG_Doctor.model.Doctor;
import com.tobbetu.MobileECG_Doctor.task.RegisterTask;

/**
 * Created by kanilturgut on 25/03/14.
 */
public class RegisterActivity extends Activity {

    EditText etDocName, etDocSurname, etDocUsername, etDocPassword;
    Button bDoRegister;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etDocName = (EditText) findViewById(R.id.etRegisterDoctorName);
        etDocSurname = (EditText) findViewById(R.id.etRegisterDoctorSurname);
        etDocUsername = (EditText) findViewById(R.id.etRegisterDoctorUsername);
        etDocPassword = (EditText) findViewById(R.id.etRegisterDoctorPassword);

        bDoRegister = (Button) findViewById(R.id.bDoRegister);
        bDoRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isEmpty(etDocName, etDocSurname, etDocUsername, etDocPassword)) {
                    Doctor doctor = new Doctor();
                    doctor.setName(etDocName.getText().toString().trim());
                    doctor.setSurname(etDocSurname.getText().toString().trim());
                    doctor.setUsername(etDocUsername.getText().toString().trim());
                    doctor.setPassword(etDocPassword.getText().toString().trim());
                    doctor.setDeviceID("sdasdsada");

                    new RegisterTask(RegisterActivity.this).execute(doctor);
                }


            }
        });
    }


    boolean isEmpty(EditText... editTexts) {
        for (EditText editText: editTexts) {
            if (editText.getText().toString().trim().equals(""))
                return true;
        }
        return false;
    }
}