package com.tobbetu.MobileECG_Doctor.service;

import com.tobbetu.MobileECG_Doctor.backend.Requests;
import com.tobbetu.MobileECG_Doctor.util.HttpURL;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by kanilturgut on 25/03/14.
 */
public class Login {

    final String TAG = "Login";
    String loginInfo;

    public Login(String[] params) {

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("username", params[0]);
            jsonObject.put("password", params[1]);
            jsonObject.put("deviceID", params[2]);

            this.loginInfo = jsonObject.toString();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public HttpResponse makeRequest() throws IOException, JSONException {
        HttpResponse response = Requests.post(HttpURL.OP_LOGIN, loginInfo);

        if (Requests.checkStatusCode(response, HttpStatus.SC_OK))
            return response;
        else
            return null;
    }
}
