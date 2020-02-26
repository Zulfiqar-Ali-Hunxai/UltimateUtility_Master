package com.ahmedonics.apps.ultimateutilitymaster.activities.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ahmedonics.apps.ultimateutilitymaster.R;

public class CalculatorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
    }

    public void onClick(View view) {

        Intent i;

        if (view.getId() == R.id.button1) {

            i=new Intent(this,StandardCal.class);
            startActivity(i);

        } else if (view.getId() == R.id.button1) {
        }



    }
}
