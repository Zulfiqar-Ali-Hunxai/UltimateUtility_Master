package com.ahmedonics.apps.ultimateutilitymaster.activities.Unit_Converter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.ahmedonics.apps.ultimateutilitymaster.R;

public class UnitWeightActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unit_weight);

        setTitle("Unit Weight");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    public void onClick(View view) {
    }
}
