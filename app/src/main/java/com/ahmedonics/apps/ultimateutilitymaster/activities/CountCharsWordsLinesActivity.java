package com.ahmedonics.apps.ultimateutilitymaster.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ahmedonics.apps.ultimateutilitymaster.R;
import com.ahmedonics.apps.ultimateutilitymaster.utils.CommonFunctions;

public class CountCharsWordsLinesActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count_chars_words_lines);

        setTitle("Count Chars, Words and Lines in Text");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("appSettings", 0);
        boolean check = pref.getBoolean("keep_screen_on_", false);
        if (check) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }

        final EditText et = (EditText) findViewById(R.id.count_chars_words_lines__text);

        final TextView txtResult = (TextView) findViewById(R.id.utility_result);
        txtResult.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = CommonFunctions.getSharingIntent(txtResult.getText().toString(), "plain/text");
                startActivity(Intent.createChooser(intent, "Share using"));
            }
        });

        Button btnRandomNumberGeneratorSubmit = (Button) findViewById(R.id.count_chars_words_lines__submit);
        btnRandomNumberGeneratorSubmit.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CommonFunctions.isEmptyEditText(et)) {
                    Toast.makeText(v.getContext(), "You didn't enter required value(s).", Toast.LENGTH_LONG).show();
                } else {
                    String textToConvert = et.getText().toString();
                    String text = "Generated Result:\n\n";

                    text += "Total Characters: " + CommonFunctions.getTotalCharsInText(textToConvert) + "\n";
                    text += "Total Words: " + CommonFunctions.getTotalWordsInText(textToConvert) + "\n";
                    text += "Total Lines: " + CommonFunctions.getTotalLinesInText(textToConvert) + "\n";

                    CommonFunctions.resultSharingDialog(CountCharsWordsLinesActivity.this, text);
                }

            }
        });
    }

}
