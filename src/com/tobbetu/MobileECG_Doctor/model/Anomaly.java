package com.tobbetu.MobileECG_Doctor.model;

import android.annotation.TargetApi;
import android.os.Build;
import com.tobbetu.MobileECG_Doctor.backend.Requests;
import com.tobbetu.MobileECG_Doctor.util.HttpURL;
import com.tobbetu.MobileECG_Doctor.util.Util;
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
 * Created by kanilturgut on 03/04/14, 02:14.
 */
public class Anomaly implements Serializable{

    String id;
    Date date;
    boolean[] detectedAnomalies;
    Patient patient;
    List<ECGData> ecgDataList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean[] getDetectedAnomalies() {
        return detectedAnomalies;
    }

    public void setDetectedAnomalies(boolean[] detectedAnomalies) {
        this.detectedAnomalies = detectedAnomalies;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public List<ECGData> getEcgDataList() {
        return ecgDataList;
    }

    public void setEcgDataList(List<ECGData> ecgDataList) {
        this.ecgDataList = ecgDataList;
    }

    public static Anomaly getPatientAnomalyWithId(String anomalyId) throws IOException, JSONException {
        HttpResponse response = Requests.post(HttpURL.OP_GET_PATIENT_ANOMALY_WITHID, anomalyId);

        if (Requests.checkStatusCode(response, HttpStatus.SC_OK))
            return Anomaly.fromJSONWithECGData(Anomaly.anomalyToJsonObject(Requests.readResponse(response)));
        else
            return null;
    }

    public static List<Anomaly> getPatientAnomaliesBetweenGivenDates(Date begin, Date end, String patientId) throws JSONException, IOException {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("beginningDate", begin.getTime());
        jsonObject.put("endDate", end.getTime());
        jsonObject.put("patientID", patientId);

        HttpResponse response = Requests.post(HttpURL.OP_GET_ECG_DATAS_BETWEEN_DATES, jsonObject.toString());

        if (Requests.checkStatusCode(response, HttpStatus.SC_OK))
            return Anomaly.parseList(Requests.readResponse(response));
        else
            return null;
    }

    public static List<Anomaly> getPatientAnomalies(String patientId) throws IOException, JSONException {
        HttpResponse response = Requests.post(HttpURL.OP_GET_PATIENT_ANOMALIES, patientId);

        if (Requests.checkStatusCode(response, HttpStatus.SC_OK))
            return Anomaly.parseList(Requests.readResponse(response));
        else
            return null;
    }

    private static List<Anomaly> parseList(String response) throws JSONException {
        List<Anomaly> anomalyList = new LinkedList<Anomaly>();

        JSONArray results = new JSONArray(response);

        for (int i = 0; i < results.length(); i++) {
            JSONObject item = results.getJSONObject(i);
            anomalyList.add(Anomaly.fromJSON(item));
        }

        return anomalyList;
    }

    private static JSONObject anomalyToJsonObject(String response) throws JSONException {
        return new JSONObject(response);
    }

    private static Anomaly fromJSON(JSONObject obj) throws JSONException {

        Anomaly anomaly = new Anomaly();
        anomaly.setId(obj.getString("id"));
        anomaly.setDate(Util.milisecondToDate(obj.getLong("annotationDate")));

        JSONArray jsonArray = obj.getJSONArray("detectedAnomalies");
        boolean[] detectedAnomalies = new boolean[3];
        for (int i = 0; i < 3; i++) {
            detectedAnomalies[i] = jsonArray.getBoolean(i);
        }

        anomaly.setDetectedAnomalies(detectedAnomalies);

        return anomaly;
    }

    private static Anomaly fromJSONWithECGData(JSONObject obj) throws JSONException {

        Anomaly anomaly = new Anomaly();
        anomaly.setId(obj.getString("id"));
        anomaly.setDate(Util.milisecondToDate(obj.getLong("annotationDate")));

        JSONArray jsonArray = obj.getJSONArray("detectedAnomalies");
        boolean[] detectedAnomalies = new boolean[3];
        for (int i = 0; i < 3; i++) {
            detectedAnomalies[i] = jsonArray.getBoolean(i);
        }
        anomaly.setDetectedAnomalies(detectedAnomalies);

        List<ECGData> list = new LinkedList<ECGData>();
        jsonArray = obj.getJSONArray("anormalWaves");

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            list.add(ECGData.fromJSON(jsonObject));
        }
        anomaly.setEcgDataList(list);

        return anomaly;
    }

}
