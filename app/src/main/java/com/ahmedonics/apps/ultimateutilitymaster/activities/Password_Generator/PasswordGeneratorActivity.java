package com.ahmedonics.apps.ultimateutilitymaster.activities.Password_Generator;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.ahmedonics.apps.ultimateutilitymaster.R;

public class PasswordGeneratorActivity extends AppCompatActivity {

    private TextView passwordTv;
    private TextView passwordLegntTv;
    private SeekBar seekBar;
    private Button generateBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_generator);

        setTitle("Password Generator");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        passwordTv = findViewById(R.id.passwordTv);
        passwordLegntTv = findViewById(R.id.passwordLegntTv);
        seekBar = findViewById(R.id.seekBar);
        generateBtn = findViewById(R.id.generateBtn);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                passwordLegntTv.setText("Lenght : " + i);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void generate(View view) {
        String password = PasswordGenerator.process(seekBar.getProgress());

        passwordTv.setText(password);

    }

}
