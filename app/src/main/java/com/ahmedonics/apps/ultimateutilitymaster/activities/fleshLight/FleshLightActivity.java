package com.ahmedonics.apps.ultimateutilitymaster.activities.fleshLight;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ahmedonics.apps.ultimateutilitymaster.R;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class FleshLightActivity extends AppCompatActivity {

    Global global;
    boolean isTorchOn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flesh_light);

        setTitle("Flesh Light");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        global = new Global();

    }
    public void toggle(View view) {
        Button button = (Button) view;
        if (button.getText().equals("On")) {
            button.setText(R.string.off);
            torchToggle("on");
        } else {
            button.setText(R.string.on);
            torchToggle("off");
        }
    }
    private void torchToggle(String command) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            CameraManager camManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
            String cameraId = null;
            try {
                if (camManager != null) {
                    cameraId = camManager.getCameraIdList()[0];
                }
                if (camManager != null) {
                    if (command.equals("on")) {
                        camManager.setTorchMode(cameraId, true);
                        isTorchOn = true;
                    } else {
                        camManager.setTorchMode(cameraId, false);
                        isTorchOn = false;
                    }
                }
            } catch (CameraAccessException e) {
                e.getMessage();
            }
        }
    }
}
