package com.ahmedonics.apps.ultimateutilitymaster.activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ahmedonics.apps.ultimateutilitymaster.R;
import com.ahmedonics.apps.ultimateutilitymaster.config.Constants;
import com.ahmedonics.apps.ultimateutilitymaster.utils.CommonFunctions;

public class RandomNumberGeneratorActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_number_generator);

        setTitle("Random Number Generator");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("appSettings", 0);
        boolean check = pref.getBoolean("keep_screen_on_", false);
        if (check) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }

        final EditText et = (EditText) findViewById(R.id.generate_random_number__start_val);
        final EditText et2 = (EditText) findViewById(R.id.generate_random_number__end_val);
        final EditText et3 = (EditText) findViewById(R.id.generate_random_number__how_many);

        et.setText(Constants.random_number_generator_start_min + "");
        et2.setText(Constants.random_number_generator_start_max + "");
        et3.setText(Constants.random_number_generator_generated_numbers_min + "");

        Button btnRandomNumberGeneratorSubmit = (Button) findViewById(R.id.btn_generate_random_number_submit);
        btnRandomNumberGeneratorSubmit.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CommonFunctions.isEmptyEditText(et) || CommonFunctions.isEmptyEditText(et2)
                        || CommonFunctions.isEmptyEditText(et3) ) {
                    Toast.makeText(v.getContext(), "You didn't enter required value(s).", Toast.LENGTH_LONG).show();
                } else {
                    int startValue = CommonFunctions.getEditTextAsInt(et);
                    int endValue = CommonFunctions.getEditTextAsInt(et2);
                    int howMany = CommonFunctions.getEditTextAsInt(et3);

                    if (howMany < Constants.random_number_generator_generated_numbers_min || howMany > Constants.random_number_generator_generated_numbers_max) {
                        Toast.makeText(v.getContext(), "Can't generate this much random numbers.", Toast.LENGTH_LONG).show();
                    } else if (startValue >= endValue || startValue < Constants.random_number_generator_start_min || startValue > Constants.random_number_generator_start_max) {
                        Toast.makeText(v.getContext(), "Invalid start value.", Toast.LENGTH_LONG).show();
                    } else if (endValue <= startValue || endValue < Constants.random_number_generator_start_min || endValue > Constants.random_number_generator_start_max) {
                        Toast.makeText(v.getContext(), "Invalid end value.", Toast.LENGTH_LONG).show();
                    } else if (endValue - startValue <= Constants.random_number_generator_start_stop_margin) {
                        Toast.makeText(v.getContext(), "Start and End value should have min difference of 100.", Toast.LENGTH_LONG).show();
                    } else {
                        String text = "Generated Random Numbers:\n\n";
                        for (int i = 1; i <= howMany; i++) {
                            if (i > 1) text += ", ";
                            text += CommonFunctions.generateRandomNumber(startValue, endValue);
                        }
                        CommonFunctions.resultSharingDialog(RandomNumberGeneratorActivity.this, text);
                    }
                }

            }
        });
    }
}
