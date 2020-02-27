package com.ahmedonics.apps.ultimateutilitymaster.activities.Others;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.Formatter;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.ahmedonics.apps.ultimateutilitymaster.R;
import com.ahmedonics.apps.ultimateutilitymaster.utils.CommonFunctions;

public class WhatIsMyIpAddressActivity extends BaseActivity {

    private Handler handler;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_what_is_my_ip_address);

        setTitle("What is my IP Address");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("appSettings", 0);
        boolean check = pref.getBoolean("keep_screen_on_", false);
        if (check) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }

        final TextView txtResult = (TextView) findViewById(R.id.utility_result);
        txtResult.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = CommonFunctions.getSharingIntent(txtResult.getText().toString(), "plain/text");
                startActivity(Intent.createChooser(intent, "Share using"));
            }
        });

        dialog = new ProgressDialog(this);
        dialog.setMessage("Processing your request ...");
        dialog.setCancelable(false);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.show();

        new Thread() {
            public void run() {
                runOnUiThread(new Runnable() {
                    public void run() {
                        String text = "";
                        TextView et4 = (TextView) findViewById(R.id.utility_result);

                        String publicIp = CommonFunctions.getPublicIPAddress();

                        if (haveNetworkConnection() && publicIp != null) {
                            text += "Public IPv4 Address is\n";
                            text += CommonFunctions.getPublicIPAddress() + "\n";
                        } else {
                            text += "No internet connection found to determine public IP address or there was an issue. Please try again\n";
                        }

                        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
                        String ipAddress = Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress());

                        text += "Local IP Address is\n" + ipAddress + "\n";

                        et4.setText(text);
                        et4.setVisibility(View.VISIBLE);
                    }
                });
                handler.sendEmptyMessage(0);
            }
        }.start();

        handler = new Handler() {
            public void handleMessage(android.os.Message msg) {
                dialog.dismiss();
            };
        };
    }

    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }

}
