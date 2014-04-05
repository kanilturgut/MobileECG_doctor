package com.tobbetu.MobileECG_Doctor.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import com.tobbetu.MobileECG_Doctor.activities.DoctorOperationsActivity;
import com.tobbetu.MobileECG_Doctor.android_service.MobileECGDoctorService;
import com.tobbetu.MobileECG_Doctor.model.Doctor;
import com.tobbetu.MobileECG_Doctor.service.Login;
import org.apache.http.HttpResponse;
import org.json.JSONException;

import java.io.IOException;

/**
 * Created by kanilturgut on 25/03/14.
 */
public class LoginTask extends AsyncTask<String, Void, Doctor>{

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
    protected Doctor doInBackground(String... strings) {

        Login login = new Login(strings);

        try {
            HttpResponse response = login.makeRequest();
            return Doctor.getDoctor(response);

        } catch (IOException e) {
            e.printStackTrace();
            cancel(true);
        } catch (JSONException e) {
            e.printStackTrace();
            cancel(true);
        }

        return null;
    }

    @Override
    protected void onPostExecute(Doctor doctor) {
        super.onPostExecute(doctor);

        if (progressDialog != null)
            progressDialog.dismiss();

        if (doctor != null) {
            if (!MobileECGDoctorService.fromService) {
                Intent intent = new Intent(context, DoctorOperationsActivity.class);
                intent.putExtra("class", doctor);
                context.startActivity(intent);
            } else {
                Log.w("LoginTask", "Doktor login oldu");
                Toast.makeText(context, "Başarı ile giriş yaptınız", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(context, "Login FAILED", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();

        if (progressDialog != null)
            progressDialog.dismiss();

        Toast.makeText(context, "Login FAILED", Toast.LENGTH_LONG).show();
    }
}
