package com.ahmedonics.apps.ultimateutilitymaster.activities.Ruler;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.ahmedonics.apps.ultimateutilitymaster.R;
import com.kevalpatel2106.rulerpicker.RulerValuePicker;
import com.kevalpatel2106.rulerpicker.RulerValuePickerListener;

public class RulerActivity extends AppCompatActivity {
    private RulerValuePicker rulerValuePicker;
    private TextView tv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ruler);
//        title
        setTitle("Ruler");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        tv = findViewById(R.id.tv);
        rulerValuePicker = findViewById(R.id.ruler_picker);
        rulerValuePicker.selectValue(50);

        rulerValuePicker.setValuePickerListener(new RulerValuePickerListener() {
            @Override
            public void onValueChange(int selectedValue) {

                tv.setText("Value: " + selectedValue);
            }

            @Override
            public void onIntermediateValueChange(int selectedValue) {

            }
        });
    }
}
