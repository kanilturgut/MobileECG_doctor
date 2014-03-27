package com.tobbetu.MobileECG_Doctor.model;

import android.util.Log;
import com.tobbetu.MobileECG_Doctor.backend.Requests;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by kanilturgut on 27/03/14, 13:01.
 */
public class Patient implements Serializable {

    private Date birthday;

    private String name;
    private String surname;
    private String username;
    private String password;
    private String phoneNumber;
    private String address;
    private String deviceID;

    private int sex;
    private int weight;                 // In kilograms
    private int height;                 // In centimeters
    private int activityFrequency;
    private int smokingFrequency;
    private int alcoholUsageFrequency;
    private int kolesterolLDL;
    private int kolesterolHDL;

    private boolean hasHypertension;
    private boolean hasDiabetes;

    private double bmi;

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getActivityFrequency() {
        return activityFrequency;
    }

    public void setActivityFrequency(int activityFrequency) {
        this.activityFrequency = activityFrequency;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getSmokingFrequency() {
        return smokingFrequency;
    }

    public void setSmokingFrequency(int smokingFrequency) {
        this.smokingFrequency = smokingFrequency;
    }

    public int getAlcoholUsageFrequency() {
        return alcoholUsageFrequency;
    }

    public void setAlcoholUsageFrequency(int alcoholUsageFrequency) {
        this.alcoholUsageFrequency = alcoholUsageFrequency;
    }

    public int getKolesterolLDL() {
        return kolesterolLDL;
    }

    public void setKolesterolLDL(int kolesterolLDL) {
        this.kolesterolLDL = kolesterolLDL;
    }

    public int getKolesterolHDL() {
        return kolesterolHDL;
    }

    public void setKolesterolHDL(int kolesterolHDL) {
        this.kolesterolHDL = kolesterolHDL;
    }

    public boolean isHasHypertension() {
        return hasHypertension;
    }

    public void setHasHypertension(boolean hasHypertension) {
        this.hasHypertension = hasHypertension;
    }

    public boolean isHasDiabetes() {
        return hasDiabetes;
    }

    public void setHasDiabetes(boolean hasDiabetes) {
        this.hasDiabetes = hasDiabetes;
    }

    public double getBmi() {
        return bmi;
    }

    public void setBmi(double bmi) {
        this.bmi = bmi;
    }

    public static List<Patient> getList(String url) throws IOException, JSONException {
        HttpResponse get = Requests.get(url);

        if (!Requests.checkStatusCode(get, HttpStatus.SC_OK))
            Log.e("Announcement.getList", "[ERROR] Status Code: "
                    + get.getStatusLine().getStatusCode());
        
        String response = Requests.readResponse(get);
        return Patient.parseList(response);
    }

    public static List<Patient> parseList(String response) throws JSONException {

        List<Patient> patientList = new LinkedList<Patient>();

        JSONArray results = new JSONArray(response);

        for (int i = 0; i < results.length(); i++) {
            JSONObject item = results.getJSONObject(i);
            patientList.add(Patient.fromJSON(item));
        }

        return patientList;
    }

    public static Patient fromJSON(JSONObject obj) throws JSONException {

        Patient patient = new Patient();
        patient.setName(obj.getString("name"));
        patient.setSurname(obj.getString("surname"));
        patient.setUsername(obj.getString("username"));
        patient.setPassword(obj.getString("password"));
        patient.setPhoneNumber(obj.getString("phoneNumber"));
        patient.setAddress(obj.getString("address"));
        patient.setDeviceID(obj.getString("deviceID"));
        patient.setBirthday(new Date());

        patient.setSex(obj.getInt("sex"));
        patient.setWeight(obj.getInt("weight"));
        patient.setHeight(obj.getInt("height"));
        patient.setActivityFrequency(obj.getInt("activityFrequency"));
        patient.setSmokingFrequency(obj.getInt("smokingFrequency"));
        patient.setAlcoholUsageFrequency(obj.getInt("alcoholUsageFrequency"));
        patient.setKolesterolLDL(obj.getInt("kolesterolLDL"));
        patient.setKolesterolHDL(obj.getInt("kolesterolHDL"));

        patient.setHasHypertension(obj.getBoolean("hasHypertension"));
        patient.setHasDiabetes(obj.getBoolean("hasDiabetes"));
        patient.setBmi(obj.getDouble("bmi"));

        return patient;
    }
}

