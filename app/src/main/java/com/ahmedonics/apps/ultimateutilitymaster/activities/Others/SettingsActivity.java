package com.ahmedonics.apps.ultimateutilitymaster.activities.Others;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ahmedonics.apps.ultimateutilitymaster.R;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        setTitle("Configure Application");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

        final SharedPreferences pref = getApplicationContext().getSharedPreferences("appSettings", 0);
        final SharedPreferences.Editor editor = pref.edit();

        final Switch et = (Switch) findViewById(R.id.settings_keep_screen_on);
        final EditText userName = (EditText) findViewById(R.id.user_name);

        Button btn = (Button) findViewById(R.id.btn_settings_submit);
        btn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userNameStr = userName.getText().toString();
                if (userNameStr.length() <= 20 && userNameStr != "") {
                    editor.putBoolean("keep_screen_on_", et.isChecked());
                    editor.putString("user_name_", userName.getText().toString());
                    editor.commit();
                    Toast.makeText(v.getContext(), "Settings updated. These will take affect on application restart.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(v.getContext(), "There was an error updating settings", Toast.LENGTH_LONG).show();
                }

            }
        });


        Boolean check = pref.getBoolean("keep_screen_on_", false);
        et.setChecked(check);
        String userNameVal = pref.getString("user_name_", "");
        userName.setText(userNameVal);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent settings;
        switch(item.getItemId()) {
            case R.id.activity_about_link:
                settings = new Intent(this, AboutActivity.class);
                startActivity(settings);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
