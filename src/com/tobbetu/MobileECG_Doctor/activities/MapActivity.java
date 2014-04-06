package com.tobbetu.MobileECG_Doctor.activities;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.tobbetu.MobileECG_Doctor.R;

/**
 * Created by kanilturgut on 02/04/14, 20:41.
 */
public class MapActivity extends Activity {

    Context context = null;
    GoogleMap myMap = null;

    Double latitude, longitude;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        context = this;

        latitude = getIntent().getDoubleExtra("latitude", 0);
        longitude = getIntent().getDoubleExtra("longitude", 0);

        LatLng latLng = new LatLng(latitude, longitude);

        myMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.mapFragment)).getMap();
        myMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        myMap.addMarker(new MarkerOptions().position(latLng));
        myMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
    }
}