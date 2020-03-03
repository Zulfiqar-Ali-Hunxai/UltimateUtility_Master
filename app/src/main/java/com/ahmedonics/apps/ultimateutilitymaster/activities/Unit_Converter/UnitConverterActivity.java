package com.ahmedonics.apps.ultimateutilitymaster.activities.Unit_Converter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ahmedonics.apps.ultimateutilitymaster.R;

public class UnitConverterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unit_converter);

        setTitle("Unit Converter");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

    }

    public void onClick(View v) {

        Intent i;
        switch(v.getId())
        {
            case R.id.area:
                i=new Intent(this,UnitAreaActivity.class);
                startActivity(i);
                break;
            case R.id.length:
                i=new Intent(this,UnitLengthActivity.class);
                startActivity(i);
                break;
            case R.id.weight:
                i=new Intent(this,UnitWeightActivity.class);
                startActivity(i);
                break;
            case R.id.tempearture:
                i=new Intent(this,UnitTemperatureActivity.class);
                startActivity(i);
                break;
        }
    }
}
