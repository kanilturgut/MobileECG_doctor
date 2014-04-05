package com.tobbetu.MobileECG_Doctor.model;

import android.util.Log;
import com.tobbetu.MobileECG_Doctor.backend.Requests;
import com.tobbetu.MobileECG_Doctor.util.HttpURL;
import com.tobbetu.MobileECG_Doctor.util.Util;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by kanilturgut on 18/03/14.
 */
public class ECGData {

    String id;
    Date date;
    double latitude;
    double longitude;
    double ra_ll;
    double la_ll;
    int RAW_ra_ll;
    int RAW_la_ll;
    int userState;

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

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getRa_ll() {
        return ra_ll;
    }

    public void setRa_ll(double ra_ll) {
        this.ra_ll = ra_ll;
    }

    public double getLa_ll() {
        return la_ll;
    }

    public void setLa_ll(double la_ll) {
        this.la_ll = la_ll;
    }

    public int getRAW_ra_ll() {
        return RAW_ra_ll;
    }

    public void setRAW_ra_ll(int RAW_ra_ll) {
        this.RAW_ra_ll = RAW_ra_ll;
    }

    public int getRAW_la_ll() {
        return RAW_la_ll;
    }

    public void setRAW_la_ll(int RAW_la_ll) {
        this.RAW_la_ll = RAW_la_ll;
    }

    public int getUserState() {
        return userState;
    }

    public void setUserState(int userState) {
        this.userState = userState;
    }

    public static ECGData fromJSON(JSONObject object) {

        ECGData ecgData = new ECGData();

        ecgData.setId(object.optString("id"));
        ecgData.setUserState(object.optInt("userState"));
        ecgData.setRa_ll(object.optDouble("ra_ll"));
        ecgData.setLa_ll(object.optDouble("la_ll"));
        ecgData.setRAW_ra_ll(object.optInt("raw_ra_ll"));
        ecgData.setRAW_la_ll(object.optInt("raw_la_ll"));
        try {
            ecgData.setDate(Util.milisecondToDate(object.optLong("date")));
        } catch (Exception e) {
            Log.e("ECGData", "Birthday FAILED", e);
            ecgData.setDate(new Date());
        }

        ecgData.setLatitude(object.optDouble("latitude"));
        ecgData.setLongitude(object.optDouble("longitude"));

        return ecgData;
    }

    public static List<ECGData> getQueriedDatas(Date begin, Date end, String patientId) throws JSONException, IOException {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("beginningDate", begin.getTime());
        jsonObject.put("endDate", end.getTime());
        jsonObject.put("patientID", patientId);

        HttpResponse response = Requests.post(HttpURL.OP_GET_ECG_DATAS_BETWEEN_DATES, jsonObject.toString());

        if (Requests.checkStatusCode(response, HttpStatus.SC_OK))
            return ECGData.parseList(Requests.readResponse(response));
        else
            return null;

    }

    private static List<ECGData> parseList(String response)  {
        List<ECGData> ecgDataList = new LinkedList<ECGData>();

        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(response);
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = null;
            try {
                jsonObject = jsonArray.getJSONObject(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            ecgDataList.add(ECGData.fromJSON(jsonObject));
        }

        return ecgDataList;
    }

}
