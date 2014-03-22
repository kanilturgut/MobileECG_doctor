package com.tobbetu.MobileECG_Doctor.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.tobbetu.MobileECG_Doctor.R;


/**
 * Created by kanilturgut on 16/03/14.
 */
public class MainActivity extends Activity {

    ProgressDialog progressDialog = null;

    String HASTALAR[] = {"Kadir Anıl Turğut", "Umut Ozan Yıldırım", "Tansel Özyer", "Onur Can Sert"};

    ListView lvListOfPatients;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressDialog = ProgressDialog.show(this, "Lütfen Bekliyiniz", "Takip ettiğiniz hastalarınız listesi getiriliyor");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressDialog.dismiss();
                initialize();
            }
        }, 3000);

    }

    private void initialize() {
        lvListOfPatients = (ListView) findViewById(R.id.lvListOfPatients);
        lvListOfPatients.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, HASTALAR));
        lvListOfPatients.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, HastaActivity.class);
                intent.putExtra("index", i);
                startActivity(intent);
            }
        });
    }
}