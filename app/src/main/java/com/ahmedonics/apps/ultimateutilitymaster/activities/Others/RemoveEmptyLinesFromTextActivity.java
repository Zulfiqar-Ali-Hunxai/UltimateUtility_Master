package com.ahmedonics.apps.ultimateutilitymaster.activities.Others;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ahmedonics.apps.ultimateutilitymaster.R;
import com.ahmedonics.apps.ultimateutilitymaster.utils.CommonFunctions;

public class RemoveEmptyLinesFromTextActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_empty_lines_from_text);

        setTitle("Remove Empty Lines from Text");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("appSettings", 0);
        boolean check = pref.getBoolean("keep_screen_on_", false);
        if (check) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }

        final EditText et = (EditText) findViewById(R.id.text);

        Button btn = (Button) findViewById(R.id.submit);
        btn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CommonFunctions.isEmptyEditText(et)) {
                    Toast.makeText(v.getContext(), "You didn't enter required value(s).", Toast.LENGTH_LONG).show();
                } else {
                    String textToConvert = et.getText().toString();
                    String text = textToConvert.replaceAll("[\\\r\\\n]+","\n");;
                    CommonFunctions.resultSharingDialog(RemoveEmptyLinesFromTextActivity.this, text);
                }

            }
        });

    }
}
