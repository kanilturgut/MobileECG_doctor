package com.tobbetu.MobileECG_Doctor.service;

import com.tobbetu.MobileECG_Doctor.backend.Requests;
import com.tobbetu.MobileECG_Doctor.util.HttpURL;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by kanilturgut on 28/03/14, 10:50.
 */
public class FollowPatient {

    final String TAG = "FollowPatient";
    String followInfo;

    public FollowPatient(String id) {

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("id", id);

            this.followInfo = jsonObject.toString();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public boolean makeRequest() throws IOException {
        HttpResponse response = Requests.post(HttpURL.OP_FOLLOW_PATIENT, followInfo);

        if (Requests.checkStatusCode(response, HttpStatus.SC_OK))
            return true;
        else
            return false;
    }
}
