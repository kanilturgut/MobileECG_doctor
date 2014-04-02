package com.tobbetu.MobileECG_Doctor.model;

import com.tobbetu.MobileECG_Doctor.backend.Requests;
import org.apache.http.HttpResponse;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;

/**
 * Created by kanilturgut on 25/03/14.
 */
public class Doctor implements Serializable {

    String id;
    String name;
    String surname;
    String username;
    String password;
    String deviceID;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    public static Doctor getDoctor(HttpResponse response) throws IOException, JSONException {
        return fromJSON(parseDoctor(Requests.readResponse(response)));
    }

    public static JSONObject parseDoctor(String doctor) throws JSONException {
        return new JSONObject(doctor);
    }

    public static Doctor fromJSON(JSONObject jsonObject) throws JSONException {
        Doctor doctor = new Doctor();

        doctor.setId(jsonObject.getString("id"));
        doctor.setName(jsonObject.getString("name"));
        doctor.setSurname(jsonObject.getString("surname"));
        doctor.setUsername(jsonObject.getString("username"));
        doctor.setPassword(jsonObject.getString("password"));
        doctor.setDeviceID(jsonObject.optString("deviceID"));

        return doctor;
    }
}
