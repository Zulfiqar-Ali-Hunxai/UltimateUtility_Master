package com.ahmedonics.apps.ultimateutilitymaster.activities.Others;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

import com.ahmedonics.apps.ultimateutilitymaster.R;
import com.ahmedonics.apps.ultimateutilitymaster.utils.CommonFunctions;

public class SystemInformationActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_information);

        setTitle("System Information");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("appSettings", 0);
        boolean check = pref.getBoolean("keep_screen_on_", false);
        if (check) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }

        TextView et4 = (TextView) findViewById(R.id.hardware_info);
        String text = "Hardware Details: \n\n";
        String myVersion = android.os.Build.VERSION.RELEASE;
        //String codeName = Build.VERSION.CODENAME;
        //text += "Operating System Version: " + myVersion + "\n";
        String Manufacturer_value = Build.MANUFACTURER;
        text += "Manufacturer: " + Manufacturer_value + "\n";
        String Brand_value = Build.BRAND;
        text += "Brand: " + Brand_value + "\n";
        String Model_value = Build.MODEL;
        text += "Model: " + Model_value + "\n";
        String Board_value = Build.BOARD;
        text += "Board: " + Board_value + "\n";
        String Hardware_value = Build.HARDWARE;
        text += "Hardware: " + Hardware_value + "\n";
        String User_value = Build.USER;
        text += "User: " + User_value + "\n";
        String Host_value = Build.HOST;
        text += "Host: " + Host_value + "\n";
        String Version = Build.VERSION.RELEASE;
        text += "Release Version: " + Version + "\n";
        text += "Available Memory: " + CommonFunctions.getMemorySizeHumanized() + "\n";

        et4.setText(text);

    }

}

