package com.tobbetu.MobileECG_Doctor.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;
import com.tobbetu.MobileECG_Doctor.activities.DoctorOperationsActivity;
import com.tobbetu.MobileECG_Doctor.model.Doctor;
import com.tobbetu.MobileECG_Doctor.service.Login;
import org.apache.http.HttpResponse;
import org.json.JSONException;

import java.io.IOException;

/**
 * Created by kanilturgut on 25/03/14.
 */
public class LoginTask extends AsyncTask<String, Void, HttpResponse>{

    Context context = null;
    ProgressDialog progressDialog = null;

    public LoginTask(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = ProgressDialog.show(context, "Please Wait", "Login Started", true, false);
    }

    @Override
    protected HttpResponse doInBackground(String... strings) {

        Login login = new Login(strings);

        try {
            return login.makeRequest();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(HttpResponse response) {
        super.onPostExecute(response);

        if (progressDialog != null)
            progressDialog.dismiss();

        try {
            Doctor doctor = Doctor.getDoctor(response);

            Intent intent = new Intent(context, DoctorOperationsActivity.class);
            intent.putExtra("class", doctor);
            context.startActivity(intent);

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context, "Login FAILED", Toast.LENGTH_LONG).show();
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(context, "Login FAILED", Toast.LENGTH_LONG).show();
        }
    }
}
