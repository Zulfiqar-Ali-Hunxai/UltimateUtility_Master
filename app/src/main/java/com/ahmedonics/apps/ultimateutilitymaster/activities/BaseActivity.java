package com.ahmedonics.apps.ultimateutilitymaster.activities;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import com.ahmedonics.apps.ultimateutilitymaster.R;

public class BaseActivity extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent settings;
        switch(item.getItemId()) {
            case R.id.activity_settings_link:
                if (getClass().getName() != "com.ahmedonics.apps.ultimateutilitymaster.activities.SettingsActivity") {
                    settings = new Intent(this, SettingsActivity.class);
                    startActivity(settings);
                }
                break;
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
