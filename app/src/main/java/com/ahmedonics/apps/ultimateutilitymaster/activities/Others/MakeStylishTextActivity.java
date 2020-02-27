package com.ahmedonics.apps.ultimateutilitymaster.activities.Others;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ahmedonics.apps.ultimateutilitymaster.R;
import com.ahmedonics.apps.ultimateutilitymaster.utils.CommonFunctions;

public class MakeStylishTextActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_stylish_text);

        setTitle("Generate Stylish Text");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("appSettings", 0);
        boolean check = pref.getBoolean("keep_screen_on_", false);
        if (check) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }

        final EditText et = (EditText) findViewById(R.id.text);

        final TextView txtResult = (TextView) findViewById(R.id.utility_result);
        txtResult.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = CommonFunctions.getSharingIntent(txtResult.getText().toString(), "plain/text");
                startActivity(Intent.createChooser(intent, "Share using"));
            }
        });

        Button btnRandomNumberGeneratorSubmit = (Button) findViewById(R.id.convert_text_to_upper__submit);
        btnRandomNumberGeneratorSubmit.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CommonFunctions.isEmptyEditText(et)) {
                    Toast.makeText(v.getContext(), "You didn't enter required value(s).", Toast.LENGTH_LONG).show();
                } else {
                    String textToConvert = et.getText().toString();

                    String text = "Generated Result:\n\n";

                    for (int i = 0; i < textToConvert.length(); i++){
                        char c = textToConvert.charAt(i);
                        if (i % 2 == 0) {
                            text += String.valueOf(c).toUpperCase();
                        } else {
                            text += String.valueOf(c).toLowerCase();
                        }
                    }
                    CommonFunctions.resultSharingDialog(MakeStylishTextActivity.this, text);
                }

            }
        });


    }
}
