package com.ahmedonics.apps.ultimateutilitymaster.activities.Password_Generator;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ShareCompat;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ahmedonics.apps.ultimateutilitymaster.R;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

public class PasswordGeneratorActivity extends AppCompatActivity {

    private String GENERATED_PASSWORD_TAG = "GENERATED_PASSWORD";

    @BindView(R.id.password_result)
    AppCompatTextView passwordResult;

    @BindView(R.id.password_length_hint)
    AppCompatTextView lengthTitle;

    @BindView(R.id.password_length_seek_bar)
    DiscreteSeekBar passwordLengthSeekBar;

    @BindView(R.id.lower_letters_switch)
    SwitchCompat lowerLettersSwitch;

    @BindView(R.id.upper_letters_switch)
    SwitchCompat upperLettersSwitch;

    @BindView(R.id.digits_switch)
    SwitchCompat digitsSwitch;

    @BindView(R.id.symbols_switch)
    SwitchCompat symbolsSwitch;

    @OnClick(R.id.lower_letters_container)
    void toggleLowerLetters() {
        lowerLettersSwitch.setChecked(!lowerLettersSwitch.isChecked());
    }

    @OnClick(R.id.upper_letters_container)
    void toggleUpperLetters() {
        upperLettersSwitch.setChecked(!upperLettersSwitch.isChecked());
    }

    @OnClick(R.id.digits_container)
    void toggleDigits() {
        digitsSwitch.setChecked(!digitsSwitch.isChecked());
    }

    @OnClick(R.id.symbols_container)
    void toggleSymbols() {
        symbolsSwitch.setChecked(!symbolsSwitch.isChecked());
    }

    @OnClick(R.id.generate_password_button)
    void generatePassword() {
        passwordResult.setText(getPasswordGenerator().generate(passwordLengthSeekBar.getProgress()));
    }

    @OnClick(R.id.generated_password_container)
    void copyPassword() {
        String password = passwordResult.getText().toString();
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText(password, password);

        if (clipboard != null) {
            clipboard.setPrimaryClip(clip);
            Toast.makeText(this, getResources().getString(R.string.password_copied), Toast.LENGTH_SHORT).show();
        }
    }

    @OnLongClick(R.id.generated_password_container)
    boolean sharePassword() {
        ShareCompat.IntentBuilder
                .from(this)
                .setType("text/plain")
                .setText(passwordResult.getText().toString())
                .startChooser();

        return true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_generator);

        setTitle("Password Generator");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        ButterKnife.bind(this);
        
        lengthTitle.setText(String.format(getResources().getString(R.string.lengtha),
                String.valueOf(passwordLengthSeekBar.getProgress())));

        passwordLengthSeekBar.setOnProgressChangeListener(new DiscreteSeekBar.OnProgressChangeListener() {
            @Override
            public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {
                lengthTitle.setText(String.format(getResources().getString(R.string.lengtha), String.valueOf(value)));
            }

            @Override
            public void onStartTrackingTouch(DiscreteSeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(DiscreteSeekBar seekBar) {
            }
        });

        // Generate password automatically when app starts
        if (TextUtils.isEmpty(passwordResult.getText()))
            generatePassword();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(GENERATED_PASSWORD_TAG, passwordResult.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        passwordResult.setText(savedInstanceState.getString(GENERATED_PASSWORD_TAG));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.action_info:
//                new AboutDialog.Builder(this)
//                        .setAuthor("@MarcosCGdev")
//                        .setWebsite("www.marcoscg.com")
//                        .show();
//                return true;
//            default:
                return super.onOptionsItemSelected(item);
//        }
    }

    private PasswordGenerator getPasswordGenerator() {
        return new PasswordGenerator.Builder()
                .useLower(lowerLettersSwitch.isChecked())
                .useUpper(upperLettersSwitch.isChecked())
                .useDigits(digitsSwitch.isChecked())
                .usePunctuation(symbolsSwitch.isChecked())
                .build();
    }
}
