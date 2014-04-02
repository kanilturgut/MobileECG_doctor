package com.tobbetu.MobileECG_Doctor.activities;

import android.app.Activity;
import android.os.Bundle;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.tobbetu.MobileECG_Doctor.R;

/**
 * Created by kanilturgut on 02/04/14, 20:41.
 */
public class MapActivity extends Activity {

    GoogleMap myMap = null;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        myMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.mapFragment)).getMap();
        myMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        myMap.addMarker(new MarkerOptions().position(new LatLng(51.5033630,-0.1276250)));
    }
}