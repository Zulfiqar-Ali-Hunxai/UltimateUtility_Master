package com.ahmedonics.apps.ultimateutilitymaster.activities.Others;

import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.ahmedonics.apps.ultimateutilitymaster.R;

import java.util.ArrayList;
import java.util.List;

public class MyAvailableSensorsActivity extends BaseActivity {

    ListView listView ;
    SensorManager sensorManager ;
    List<Sensor> listsensor;
    List<String> liststring ;
    ArrayAdapter<String> adapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_available_sensors);

        setTitle("My Available Sensors List");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("appSettings", 0);
        boolean check = pref.getBoolean("keep_screen_on_", false);
        if (check) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }

        listView = (ListView)findViewById(R.id.listview1);

        liststring = new ArrayList<String>();

        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);

        listsensor = sensorManager.getSensorList(Sensor.TYPE_ALL);

        for(int i=0; i<listsensor.size(); i++){

            liststring.add(listsensor.get(i).getName());
        }

        adapter = new ArrayAdapter<String>(MyAvailableSensorsActivity.this,
                android.R.layout.simple_list_item_2,
                android.R.id.text1, liststring
        );

        listView.setAdapter(adapter);

    }

}
