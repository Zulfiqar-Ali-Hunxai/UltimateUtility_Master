package com.ahmedonics.apps.ultimateutilitymaster.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.ahmedonics.apps.ultimateutilitymaster.R;
import com.ahmedonics.apps.ultimateutilitymaster.utils.CommonFunctions;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import static java.nio.charset.StandardCharsets.UTF_8;

public class TextEncodersAndDecodersActivity extends BaseActivity {

    private int type_id, feature_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_encoders_and_decoders);

        setTitle("Encode or Decode Text");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("appSettings", 0);
        boolean check = pref.getBoolean("keep_screen_on_", false);
        if (check) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }

        final EditText et = (EditText) findViewById(R.id.text);
        final Spinner type = (Spinner) findViewById(R.id.en_type);
        type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                type_id = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        final Spinner feature = (Spinner) findViewById(R.id.en_feature);
        feature.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                feature_id = position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        Button btn = (Button) findViewById(R.id.submit);
        btn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CommonFunctions.isEmptyEditText(et)) {
                    Toast.makeText(v.getContext(), "You didn't enter required value(s).", Toast.LENGTH_LONG).show();
                } else {
                    String textToConvert = et.getText().toString();
                    String text = "";
                    if (type_id == 0) {
                        // encode
                        switch(feature_id) {
                            // web url
                            case 0:
                                try {
                                    text = URLEncoder.encode(textToConvert,"UTF-8");
                                } catch (UnsupportedEncodingException e) {
                                    e.printStackTrace();
                                }
                            break;
                            // base 64
                            case 1:
                                text = Base64.encodeToString(textToConvert.getBytes(), Base64.DEFAULT);
                            break;

                            default:
                            break;
                        }
                    } else {
                        // decode
                        switch(feature_id) {
                            // web url
                            case 0:
                                try {
                                    text = URLDecoder.decode(textToConvert,"UTF-8");
                                } catch (UnsupportedEncodingException e) {
                                    e.printStackTrace();
                                }
                            break;
                            // base 64
                            case 1:
                                byte[] data = Base64.decode(textToConvert, Base64.DEFAULT);
                                text = new String(data);
                            break;

                            default:
                                break;
                        }
                    }

                    CommonFunctions.resultSharingDialog(TextEncodersAndDecodersActivity.this, text);
                }
            }
        });

    }
}
