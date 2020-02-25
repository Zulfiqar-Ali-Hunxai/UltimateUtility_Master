package com.ahmedonics.apps.ultimateutilitymaster.activities;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.transition.TransitionManager;

import com.ahmedonics.apps.ultimateutilitymaster.R;

import java.io.File;
import java.io.IOException;

public class VoiceRecorderActivity extends BaseActivity implements View.OnClickListener {

    private int RECORD_AUDIO_REQUEST_CODE = 123;

    private Chronometer chronometer;
    private ImageView imageViewRecord, imageViewPlay, imageViewStop;
    private SeekBar seekBar;
    private LinearLayout linearLayoutRecorder, linearLayoutPlay;

    private MediaRecorder mRecorder;
    private MediaPlayer mPlayer;
    private String fileName = null;
    private int lastProgress = 0;
    private long lastChromeTimer = 0;
    private Handler mHandler = new Handler();
    private boolean isPlaying = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_recorder);

        setTitle("Voice Recorder");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("appSettings", 0);
        boolean check = pref.getBoolean("keep_screen_on_", false);
        String userName = pref.getString("user_name_", "");
        if (check) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getPermissionToRecordAudio();
        }

        Button myBtn = (Button) findViewById(R.id.btn_view_list);
        myBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mRecorder != null) {
                    prepareforStop();
                    stopRecording();
                }
                Intent settings = new Intent(v.getContext(), VoiceRecorderListActivity.class);
                startActivity(settings);
            }
        });

        linearLayoutRecorder = (LinearLayout) findViewById(R.id.linearLayoutRecorder);
        chronometer = (Chronometer) findViewById(R.id.chronometerTimer);
        chronometer.setBase(SystemClock.elapsedRealtime());
        imageViewRecord = (ImageView) findViewById(R.id.imageViewRecord);
        imageViewStop = (ImageView) findViewById(R.id.imageViewStop);
        imageViewPlay = (ImageView) findViewById(R.id.imageViewPlay);
        linearLayoutPlay = (LinearLayout) findViewById(R.id.linearLayoutPlay);
        seekBar = (SeekBar) findViewById(R.id.seekBar);

        imageViewRecord.setOnClickListener(this);
        imageViewStop.setOnClickListener(this);
        imageViewPlay.setOnClickListener(this);

    }

    private void prepareforRecording() {
        TransitionManager.beginDelayedTransition(linearLayoutRecorder);
        imageViewRecord.setVisibility(View.GONE);
        imageViewStop.setVisibility(View.VISIBLE);
        linearLayoutPlay.setVisibility(View.GONE);
    }

    private void startRecording() {
        //we use the MediaRecorder class to record
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        /**In the lines below, we create a directory VoiceRecorderSimplifiedCoding/Audios in the phone storage
         * and the audios are being stored in the Audios folder **/
        File root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File file = new File(root.getAbsolutePath() + "/UltimateUtilityMaster/Audios");
        if (!file.exists()) {
            if (file.mkdirs()) {
                Log.d("Directory created  ", "dir");
            } else {
                Log.d("Directory failed ", "dir");
            }
        }

        fileName = root.getAbsolutePath() + "/UltimateUtilityMaster/Audios/" +
                String.valueOf(System.currentTimeMillis() + ".mp3");
        Log.d("filename",fileName);
        mRecorder.setOutputFile(fileName);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mRecorder.prepare();
            mRecorder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        lastProgress = 0;
        seekBar.setProgress(0);
        stopPlaying();
        //starting the chronometer
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();
    }


    private void stopPlaying() {
        try{
            if (mPlayer != null) {
                mPlayer.release();
                mPlayer = null;
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        //showing the play button
        imageViewPlay.setImageResource(R.drawable.ic_play_foreground);
        chronometer.stop();
    }

    private void prepareforStop() {
        TransitionManager.beginDelayedTransition(linearLayoutRecorder);
        imageViewRecord.setVisibility(View.VISIBLE);
        imageViewStop.setVisibility(View.GONE);
        linearLayoutPlay.setVisibility(View.VISIBLE);
    }

    private void startPlaying() {
        mPlayer = new MediaPlayer();
        try {
            mPlayer.setDataSource(fileName);
            mPlayer.prepare();
            mPlayer.start();
        } catch (IOException e) {
            Log.e("LOG_TAG", "prepare() failed");
        }
        //making the imageview pause button
        imageViewPlay.setImageResource(R.drawable.ic_pause_foreground);

        seekBar.setProgress(lastProgress);
        mPlayer.seekTo(lastProgress);
        seekBar.setMax(mPlayer.getDuration());
        seekUpdation();
        if (lastChromeTimer != 0) {
            chronometer.setBase(lastChromeTimer);
        } else {
            chronometer.setBase(SystemClock.elapsedRealtime());
        }
        chronometer.start();

        /** once the audio is complete, timer is stopped here**/
        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                imageViewPlay.setImageResource(R.drawable.ic_play_foreground);
                isPlaying = false;
                mPlayer.seekTo(0);
                chronometer.stop();
                lastChromeTimer = 0;
                chronometer.setBase(SystemClock.elapsedRealtime());
            }
        });

        /** moving the track as per the seekBar's position**/
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if( mPlayer!=null && fromUser ){
                    //here the track's progress is being changed as per the progress bar
                    mPlayer.seekTo(progress);
                    //timer is being updated as per the progress of the seekbar
                    long time = SystemClock.elapsedRealtime() - mPlayer.getCurrentPosition();
                    chronometer.setBase(time);
                    lastProgress = progress;
                    lastChromeTimer = time;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            seekUpdation();
        }
    };

    private void seekUpdation() {
        if(mPlayer != null){
            int mCurrentPosition = mPlayer.getCurrentPosition() ;
            seekBar.setProgress(mCurrentPosition);
            lastProgress = mCurrentPosition;
        }
        mHandler.postDelayed(runnable, 100);
    }


    private void stopRecording() {
        try {
            if (mRecorder != null) {
                mRecorder.stop();
                mRecorder.release();
                mRecorder = null;
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        //starting the chronometer
        chronometer.stop();
        //showing the play button
        Toast.makeText(this, "Recording saved successfully.", Toast.LENGTH_SHORT).show();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void getPermissionToRecordAudio() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    RECORD_AUDIO_REQUEST_CODE);

        }
    }

    // Callback with the request from calling requestPermissions(...)
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        // Make sure it's our original READ_CONTACTS request
        if (requestCode == RECORD_AUDIO_REQUEST_CODE) {
            if (grantResults.length == 3 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED
                    && grantResults[2] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Thank you for granting permission.", Toast.LENGTH_SHORT).show();
            } else {
                Intent main = new Intent(this, MainActivity.class);
                startActivity(main);
                finish();
            }
        }
    }

    @Override
    public void onClick(View view) {
        if (view == imageViewRecord ) {
            prepareforRecording();
            startRecording();
        } else if( view == imageViewStop ){
            prepareforStop();
            stopRecording();
        } else if( view == imageViewPlay ){
            if( !isPlaying && fileName != null ){
                isPlaying = true;
                startPlaying();
            }else{
                isPlaying = false;
                stopPlaying();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mRecorder != null) {
            prepareforStop();
            stopRecording();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
