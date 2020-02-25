package com.ahmedonics.apps.ultimateutilitymaster.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.ahmedonics.apps.ultimateutilitymaster.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StopWatchActivity extends BaseActivity {

    TextView textView ;
    Button start, stop, reset, lap ;
    long MillisecondTime, StartTime, TimeBuff, UpdateTime = 0L ;
    Handler handler;
    int Seconds, Minutes, MilliSeconds ;
    ListView listView ;
    String[] ListElements = new String[] {};
    List<String> ListElementsArrayList ;
    ArrayAdapter<String> adapter ;

    boolean paused;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stop_watch);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("appSettings", 0);
        boolean check = pref.getBoolean("keep_screen_on_", false);
        String userName = pref.getString("user_name_", "");
        if (check) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }

        setTitle("Stopwatch");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

        textView = (TextView)findViewById(R.id.textView);
        start = (Button)findViewById(R.id.button);
        stop = (Button)findViewById(R.id.button2);
        reset = (Button)findViewById(R.id.button3);
        lap = (Button)findViewById(R.id.button4) ;
        listView = (ListView)findViewById(R.id.listview1);
        handler = new Handler();

        ListElementsArrayList = new ArrayList<String>(Arrays.asList(ListElements));
        adapter = new ArrayAdapter<String>(StopWatchActivity.this,
                android.R.layout.simple_list_item_1,
                ListElementsArrayList
        );
        listView.setAdapter(adapter);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StartTime = SystemClock.uptimeMillis();
                handler.postDelayed(runnable, 0);
                start.setVisibility(View.GONE);
                stop.setVisibility(View.VISIBLE);
                lap.setVisibility(View.VISIBLE);
                reset.setVisibility(View.GONE);
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               handler.removeCallbacks(runnable);
               start.setVisibility(View.VISIBLE);
               stop.setVisibility(View.GONE);
               lap.setVisibility(View.GONE);
               reset.setVisibility(View.VISIBLE);
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MillisecondTime = 0L ;
                StartTime = 0L ;
                TimeBuff = 0L ;
                UpdateTime = 0L ;
                Seconds = 0 ;
                Minutes = 0 ;
                MilliSeconds = 0 ;
                textView.setText("00:00:00");
                ListElementsArrayList.clear();
                adapter.notifyDataSetChanged();

                start.setVisibility(View.VISIBLE);
                stop.setVisibility(View.GONE);
                lap.setVisibility(View.GONE);
                reset.setVisibility(View.GONE);
            }
        });

        lap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (textView.getText().toString() != "00:00:00") {
                    ListElementsArrayList.add(textView.getText().toString());
                    adapter.notifyDataSetChanged();
                }
            }
        });

    }

    public Runnable runnable = new Runnable() {
        public void run() {
            MillisecondTime = SystemClock.uptimeMillis() - StartTime;
            UpdateTime = MillisecondTime;

            UpdateTime = MillisecondTime;

            Seconds = (int) (UpdateTime / 1000);
            Minutes = Seconds / 60;
            Seconds = Seconds % 60;
            MilliSeconds = (int) (UpdateTime % 100);

            String padded = String.format("%02d", Minutes);
            textView.setText(padded + ":"
                    + String.format("%02d", Seconds) + ":"
                    + String.format("%02d", MilliSeconds));

            handler.postDelayed(this, 0);
        }

    };

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
