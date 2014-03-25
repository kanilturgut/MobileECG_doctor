package com.tobbetu.MobileECG_Doctor.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;
import com.tobbetu.MobileECG_Doctor.activities.MainActivity;
import com.tobbetu.MobileECG_Doctor.model.Doctor;
import com.tobbetu.MobileECG_Doctor.service.Register;
import org.json.JSONException;

import java.io.IOException;

/**
 * Created by kanilturgut on 25/03/14.
 */
public class RegisterTask extends AsyncTask<Doctor, Void, Boolean> {

    Context context = null;
    ProgressDialog progressDialog = null;

    public RegisterTask(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = ProgressDialog.show(context, "Please Wait", "Register started", true, false);
    }

    @Override
    protected Boolean doInBackground(Doctor... doctors) {

        Register newRegister = new Register(doctors[0]);

        try {
            return newRegister.makeRequest();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        if (progressDialog != null)
            progressDialog.dismiss();

        if (result)
            context.startActivity(new Intent(context, MainActivity.class));
        else
            Toast.makeText(context, "Register FAILED", Toast.LENGTH_LONG).show();
    }
}