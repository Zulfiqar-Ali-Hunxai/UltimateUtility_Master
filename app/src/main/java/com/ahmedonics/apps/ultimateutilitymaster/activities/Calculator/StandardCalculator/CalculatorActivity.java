package com.ahmedonics.apps.ultimateutilitymaster.activities.Calculator.StandardCalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ahmedonics.apps.ultimateutilitymaster.R;
import com.ahmedonics.apps.ultimateutilitymaster.activities.Calculator.ScientificCalculator.ScientificCalculator;

public class CalculatorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        setTitle("Select Calculator");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

    }

    public void onClick(View view) {

        Intent i;

        if (view.getId() == R.id.button1) {

            i=new Intent(this,StandardCal.class);
            startActivity(i);

        } else if (view.getId() == R.id.button) {

            i=new Intent(this, ScientificCalculator.class);
            startActivity(i);
        }



    }
}
