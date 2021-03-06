package com.ahmedonics.apps.ultimateutilitymaster.activities.Others;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.ahmedonics.apps.ultimateutilitymaster.R;
import com.ahmedonics.apps.ultimateutilitymaster.utils.CommonFunctions;

import java.util.Arrays;
import java.util.Collections;

public class SortLinesInTextActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort_lines_in_text);

        setTitle("Sort Lines in Text");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("appSettings", 0);
        boolean check = pref.getBoolean("keep_screen_on_", false);
        if (check) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }

        final EditText et = (EditText) findViewById(R.id.text);
        final Switch switchBtn = (Switch) findViewById(R.id.sort_asc_desc);

        Button btn = (Button) findViewById(R.id.submit);
        btn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if (CommonFunctions.isEmptyEditText(et)) {
                Toast.makeText(v.getContext(), "You didn't enter required value(s).", Toast.LENGTH_LONG).show();
            } else {
                String textToConvert = et.getText().toString();
                String[] tokens = textToConvert.split("\n");
                if (!switchBtn.isChecked()) Arrays.sort(tokens);
                else Arrays.sort(tokens, Collections.reverseOrder());

                StringBuilder resultBuilder = new StringBuilder();
                boolean first = true;
                for (String token : tokens) {
                    if (first) first = false;
                    else resultBuilder.append("\n");
                    resultBuilder.append(token);
                }
                String text = resultBuilder.toString();
                CommonFunctions.resultSharingDialog(SortLinesInTextActivity.this, text);
            }
            }
        });

    }
}
