package com.ahmedonics.apps.ultimateutilitymaster.activities.Others;

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

public class ConvertTextToLowerActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convert_text_to_lower);

        setTitle("Convert Text to Lower Case");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("appSettings", 0);
        boolean check = pref.getBoolean("keep_screen_on_", false);
        if (check) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }

        final EditText et = (EditText) findViewById(R.id.convert_text_to_lower__text);

        final TextView txtResult = (TextView) findViewById(R.id.utility_result);

        Button btnRandomNumberGeneratorSubmit = (Button) findViewById(R.id.convert_text_to_lower__submit);
        btnRandomNumberGeneratorSubmit.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CommonFunctions.isEmptyEditText(et)) {
                    Toast.makeText(v.getContext(), "You didn't enter required value(s).", Toast.LENGTH_LONG).show();
                } else {
                    String textToConvert = et.getText().toString();

                    String text = "Generated Result:\n\n";
                    text += textToConvert.toLowerCase();
                    CommonFunctions.resultSharingDialog(ConvertTextToLowerActivity.this, text);
                }

            }
        });
    }

}
