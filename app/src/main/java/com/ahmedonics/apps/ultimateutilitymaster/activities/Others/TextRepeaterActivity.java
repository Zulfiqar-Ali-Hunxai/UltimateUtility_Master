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
import com.ahmedonics.apps.ultimateutilitymaster.config.Constants;
import com.ahmedonics.apps.ultimateutilitymaster.utils.CommonFunctions;

public class TextRepeaterActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_repeater);

        setTitle("Text Repeater");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("appSettings", 0);
        boolean check = pref.getBoolean("keep_screen_on_", false);
        if (check) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }

        final EditText et = (EditText) findViewById(R.id.text_repeater__text);
        final EditText et2 = (EditText) findViewById(R.id.text_repeater__how_many);
        final EditText et3 = (EditText) findViewById(R.id.text_repeater__separator);

        et.setText(Constants.text_repeater_default_text);
        et2.setText(Constants.text_repeater_default_how_many);
        et3.setText(Constants.text_repeater_default_separator);

        final TextView txtResult = (TextView) findViewById(R.id.utility_result);
        txtResult.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = CommonFunctions.getSharingIntent(txtResult.getText().toString(), "plain/text");
                startActivity(Intent.createChooser(intent, "Share using"));
            }
        });

        Button btnRandomNumberGeneratorSubmit = (Button) findViewById(R.id.btn_text_repeater__submit);
        btnRandomNumberGeneratorSubmit.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CommonFunctions.isEmptyEditText(et) || CommonFunctions.isEmptyEditText(et2)) {
                    Toast.makeText(v.getContext(), "You didn't enter required value(s).", Toast.LENGTH_LONG).show();
                } else {
                    String textToRepeat = et.getText().toString();
                    int repeatThisMuchTimes = CommonFunctions.getEditTextAsInt(et2);
                    String separator = et3.getText().toString();

                    if (repeatThisMuchTimes > Constants.text_repeater_max_time_limit && repeatThisMuchTimes < Constants.text_repeater_min_time_limit) {
                        Toast.makeText(v.getContext(), "Can only repeat text for 1 to 1000 times.", Toast.LENGTH_LONG).show();
                    } else {
                        String text = "Generated Repeated Text:\n\n";
                        for (int i = 1; i <= repeatThisMuchTimes; i++) {
                            if (i > 1) text += separator;
                            text += textToRepeat;
                        }
                        CommonFunctions.resultSharingDialog(TextRepeaterActivity.this, text);
                    }
                }

            }
        });
    }
}
