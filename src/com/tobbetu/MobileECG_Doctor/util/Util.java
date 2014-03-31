package com.tobbetu.MobileECG_Doctor.util;

import android.content.Context;
import android.util.Log;
import com.tobbetu.MobileECG_Doctor.model.ECGData;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by kanilturgut on 18/03/14.
 */
public class Util {

    public static Date stringToDate(String strDate) {
        try {
            return new SimpleDateFormat("dd.MM.yyyy").parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return new Date();
    }

    public static String dateToString(Date date) {
        return new SimpleDateFormat("dd.MM.yyyy").format(date);
    }

    public static List<ECGData> readFromText(Context context, String filename) {

        List<ECGData> list = new LinkedList<ECGData>();

        InputStream fIn = null;
        InputStreamReader isr = null;
        BufferedReader input = null;
        try {
            fIn = context.getAssets().open(filename);
            isr = new InputStreamReader(fIn, "windows-1254");
            input = new BufferedReader(isr);
            String line = "";
            while ((line = input.readLine()) != null) {
                ECGData newECGData = new ECGData();
                newECGData.setRAW_ra_ll(Integer.parseInt(line.split(" ")[0]));
                newECGData.setRAW_la_ll(Integer.parseInt(line.split(" ")[1]));
                list.add(newECGData);
            }

            Log.i("TAG", "list " + list.size());

            return list;
        } catch (Exception e) {
            e.getMessage();
        } finally {
            try {
                if (isr != null)
                    isr.close();
                if (fIn != null)
                    fIn.close();
                if (input != null)
                    input.close();
            } catch (Exception e2) {
                e2.getMessage();
            }
        }
        return list;
    }
}
