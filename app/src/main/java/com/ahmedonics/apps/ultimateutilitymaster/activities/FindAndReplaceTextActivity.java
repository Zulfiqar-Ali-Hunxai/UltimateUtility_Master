package com.ahmedonics.apps.ultimateutilitymaster.activities;

import androidx.appcompat.app.AppCompatActivity;

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

public class FindAndReplaceTextActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_and_replace_text);

        setTitle("Find and Replace Text");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("appSettings", 0);
        boolean check = pref.getBoolean("keep_screen_on_", false);
        if (check) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }

        final EditText et = (EditText) findViewById(R.id.text);
        final EditText find = (EditText) findViewById(R.id.find_text);
        final EditText replaceWith = (EditText) findViewById(R.id.replace_text);

        Button btn = (Button) findViewById(R.id.convert_text_to_lower__submit);
        btn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CommonFunctions.isEmptyEditText(et) || CommonFunctions.isEmptyEditText(find) || CommonFunctions.isEmptyEditText(replaceWith)) {
                    Toast.makeText(v.getContext(), "You didn't enter required value(s).", Toast.LENGTH_LONG).show();
                } else {
                    String textToConvert = et.getText().toString();
                    String text = "";
                    text += textToConvert.replaceAll(find.getText().toString(), replaceWith.getText().toString());
                    CommonFunctions.resultSharingDialog(FindAndReplaceTextActivity.this, text);
                }

            }
        });


    }
}
