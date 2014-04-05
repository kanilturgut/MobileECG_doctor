package com.tobbetu.MobileECG_Doctor.service;

import com.tobbetu.MobileECG_Doctor.backend.Requests;
import com.tobbetu.MobileECG_Doctor.model.Doctor;
import com.tobbetu.MobileECG_Doctor.util.HttpURL;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by kanilturgut on 25/03/14.
 */
public class Register {

    final String TAG = "Register";
    String registerInfo;

    public Register(Doctor doctor) {

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("name", doctor.getName());
            jsonObject.put("surname", doctor.getSurname());
            jsonObject.put("username", doctor.getUsername());
            jsonObject.put("password", doctor.getPassword());
            jsonObject.put("deviceID", "sdasdsada");

            registerInfo = jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public boolean makeRequest() throws IOException, JSONException {

        HttpResponse response = Requests.post(HttpURL.OP_REGISTER, registerInfo);

        if (Requests.checkStatusCode(response, HttpStatus.SC_OK))
            return true;
        else
            return false;
    }
}
