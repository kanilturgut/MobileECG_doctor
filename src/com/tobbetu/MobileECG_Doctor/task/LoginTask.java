package com.tobbetu.MobileECG_Doctor.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;
import com.tobbetu.MobileECG_Doctor.activities.MainActivity;
import com.tobbetu.MobileECG_Doctor.service.Login;
import org.json.JSONException;

import java.io.IOException;

/**
 * Created by kanilturgut on 25/03/14.
 */
public class LoginTask extends AsyncTask<String, Void, Boolean>{

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
    protected Boolean doInBackground(String... strings) {

        Login login = new Login(strings);

        try {
            return login.makeRequest();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);

        if (progressDialog != null)
            progressDialog.dismiss();

        if (aBoolean)
            context.startActivity(new Intent(context, MainActivity.class));
        else
            Toast.makeText(context, "Login FAILED", Toast.LENGTH_LONG).show();

    }
}
