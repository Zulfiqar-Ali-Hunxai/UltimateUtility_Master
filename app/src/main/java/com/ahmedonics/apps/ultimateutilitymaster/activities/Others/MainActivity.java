package com.ahmedonics.apps.ultimateutilitymaster.activities.Others;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.core.content.pm.ShortcutInfoCompat;
import androidx.core.content.pm.ShortcutManagerCompat;
import androidx.core.graphics.drawable.IconCompat;

import com.ahmedonics.apps.ultimateutilitymaster.R;
import com.ahmedonics.apps.ultimateutilitymaster.activities.Calculator.ScientificCalculator.ScientificCalculator;
import com.ahmedonics.apps.ultimateutilitymaster.activities.Password_Generator.PasswordGeneratorActivity;
import com.ahmedonics.apps.ultimateutilitymaster.activities.Ruler.RulerActivity;
import com.ahmedonics.apps.ultimateutilitymaster.activities.Flesh_Light.FleshLightActivity;
import com.ahmedonics.apps.ultimateutilitymaster.activities.Unit_Converter.UnitAreaActivity;

import java.util.ArrayList;

//Zulfiqar Ali Hunzai
//Android Developer at admedonic tech

public class MainActivity extends BaseActivity {
    private LinearLayout llContainer;
    private EditText etSearch;
    private ListView lvProducts;

    private ArrayList<MainPageButtons> mProductArrayList = new ArrayList<MainPageButtons>();
    private MyAdapter adapter1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("appSettings", 0);
        boolean check = pref.getBoolean("keep_screen_on_", false);
        String userName = pref.getString("user_name_", "");
        if (check) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }

        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

        etSearch = (EditText) findViewById(R.id.etSearch);
        lvProducts = (ListView)findViewById(R.id.lvProducts);

        // Add Text Change Listener to EditText
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Call back the Adapter with current character to Filter
                adapter1.getFilter().filter(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        mProductArrayList.add(new MainPageButtons("Voice Recorder",
                VoiceRecorderActivity.class, R.drawable.ic_button_voice_recorder_foreground));

        mProductArrayList.add(new MainPageButtons("Text to Speech",
                TextToSpeechActivity.class, R.drawable.ic_text_to_speech_foreground));

        mProductArrayList.add(new MainPageButtons("Compass",
                CompassActivity.class, R.drawable.compass_icon_foreground));

        mProductArrayList.add(new MainPageButtons("Stop Watch",
                StopWatchActivity.class, R.drawable.ic_button_stopwatch_foreground));

        mProductArrayList.add(new MainPageButtons("Calendar",
                CalendarActivity.class, R.drawable.ic_button_calendar_foreground));

        mProductArrayList.add(new MainPageButtons("Encode or Decode Text",
                TextEncodersAndDecodersActivity.class, R.drawable.ic_button_text_foreground));

        mProductArrayList.add(new MainPageButtons("Random Numbers Generator",
                RandomNumberGeneratorActivity.class, R.drawable.ic_button_text_foreground));

        mProductArrayList.add(new MainPageButtons("Toggle Text Case",
                ToggleTextCaseActivity.class, R.drawable.ic_button_text_foreground));

        mProductArrayList.add(new MainPageButtons("Find & Replace Text",
                FindAndReplaceTextActivity.class, R.drawable.ic_button_text_foreground));

        mProductArrayList.add(new MainPageButtons("Sort Lines in Text",
                SortLinesInTextActivity.class, R.drawable.ic_button_text_foreground));

        mProductArrayList.add(new MainPageButtons("Remove Duplicate Lines from Text",
                RemoveDuplicateLinesFromTextActivity.class, R.drawable.ic_button_text_foreground));

        mProductArrayList.add(new MainPageButtons("Remove Empty Lines from Text",
                RemoveEmptyLinesFromTextActivity.class, R.drawable.ic_button_text_foreground));

        mProductArrayList.add(new MainPageButtons("Text Reapter",
                TextRepeaterActivity.class, R.drawable.ic_button_text_foreground));

        mProductArrayList.add(new MainPageButtons("Convert Text to Upper Case",
                ConvertTextToUpperActivity.class, R.drawable.ic_button_text_foreground));

        mProductArrayList.add(new MainPageButtons("Convert Text to Lower Case",
                ConvertTextToLowerActivity.class, R.drawable.ic_button_text_foreground));

        mProductArrayList.add(new MainPageButtons("Text Reapter",
                TextRepeaterActivity.class, R.drawable.ic_button_text_foreground));

        mProductArrayList.add(new MainPageButtons("Generate Stylish Text",
                MakeStylishTextActivity.class, R.drawable.ic_button_text_foreground));

        mProductArrayList.add(new MainPageButtons("Remove Emoji from Text",
                RemoveEmojiFromTextActivity.class, R.drawable.ic_button_text_foreground));

        mProductArrayList.add(new MainPageButtons("My Available Sensors List",
                MyAvailableSensorsActivity.class, R.drawable.ic_available_sensors_foreground));

        mProductArrayList.add(new MainPageButtons("What is my IP Address",
                WhatIsMyIpAddressActivity.class, R.drawable.ic_button_network_foreground));

        mProductArrayList.add(new MainPageButtons("System Information",
                SystemInformationActivity.class, R.drawable.ic_button_systerm_info_foreground));

        mProductArrayList.add(new MainPageButtons("Flesh Light",
                FleshLightActivity.class, R.drawable.ic_highlight_black_24dp));

        mProductArrayList.add(new MainPageButtons("Calculator",
                ScientificCalculator.class, R.drawable.xaxa));

        mProductArrayList.add(new MainPageButtons("Password Generator",
                PasswordGeneratorActivity.class, R.drawable.ic_lock_open_black_24dp));

//        mProductArrayList.add(new MainPageButtons("Ruler",
//                RulerActivity.class, R.drawable.rul));

        mProductArrayList.add(new MainPageButtons("Unit Converter",
                UnitAreaActivity.class, R.drawable.conv));

        adapter1 = new MyAdapter(MainActivity.this, mProductArrayList);
        lvProducts.setAdapter(adapter1);
    }

    public class MyAdapter extends BaseAdapter implements Filterable {

        private ArrayList<MainPageButtons> mOriginalValues;
        private ArrayList<MainPageButtons> mDisplayedValues;
        LayoutInflater inflater;

        public MyAdapter(Context context, ArrayList<MainPageButtons> mProductArrayList) {
            this.mOriginalValues = mProductArrayList;
            this.mDisplayedValues = mProductArrayList;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return mDisplayedValues.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        private class ViewHolder {
            LinearLayout llContainer;
            TextView tvName;
            ImageView icon;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;

            if (convertView == null) {
                holder = new ViewHolder();
                convertView = inflater.inflate(R.layout.layout_main_page_row_item, null);
                holder.llContainer = (LinearLayout) convertView.findViewById(R.id.llContainer);
                holder.tvName = (TextView) convertView.findViewById(R.id.tvName);
                holder.icon = (ImageView) convertView.findViewById(R.id.icon);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.tvName.setText(mDisplayedValues.get(position).name);
            Drawable d = getResources().getDrawable(mDisplayedValues.get(position).icon);
            holder.icon.setImageDrawable(d);

            holder.llContainer.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent settings = new Intent(v.getContext(), mDisplayedValues.get(position).v);
                    startActivity(settings);
                }
            });

            holder.llContainer.setOnLongClickListener(new View.OnLongClickListener() {
                public boolean onLongClick(View v) {
                    if (ShortcutManagerCompat.isRequestPinShortcutSupported(v.getContext())) {
                        ShortcutInfoCompat shortcutInfo = new ShortcutInfoCompat.Builder(v.getContext(), "#1")
                                .setIntent(new Intent(v.getContext(), mDisplayedValues.get(position).v).setAction(Intent.ACTION_MAIN))
                                .setShortLabel(mDisplayedValues.get(position).name)
                                .setIcon(IconCompat.createWithResource(v.getContext(), mDisplayedValues.get(position).icon))
                                .build();
                        ShortcutManagerCompat.requestPinShortcut(v.getContext(), shortcutInfo, null);
                    }
                    return false;
                }
            });

            return convertView;
        }

        @Override
        public Filter getFilter() {
            Filter filter = new Filter() {

                @SuppressWarnings("unchecked")
                @Override
                protected void publishResults(CharSequence constraint,FilterResults results) {
                    mDisplayedValues = (ArrayList<MainPageButtons>) results.values;
                    notifyDataSetChanged();
                }

                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    FilterResults results = new FilterResults();
                    ArrayList<MainPageButtons> FilteredArrList = new ArrayList<MainPageButtons>();

                    if (mOriginalValues == null) {
                        mOriginalValues = new ArrayList<MainPageButtons>(mDisplayedValues);
                    }
                    if (constraint == null || constraint.length() == 0) {
                        results.count = mOriginalValues.size();
                        results.values = mOriginalValues;
                    } else {
                        constraint = constraint.toString().toLowerCase();
                        for (int i = 0; i < mOriginalValues.size(); i++) {
                            String data = mOriginalValues.get(i).name;
                            if (data.toLowerCase().contains(constraint.toString())) {
                                FilteredArrList.add(new MainPageButtons(mOriginalValues.get(i).name,
                                                mOriginalValues.get(i).v,mOriginalValues.get(i).icon ));
                            }
                        }
                        results.count = FilteredArrList.size();
                        results.values = FilteredArrList;
                    }
                    return results;
                }
            };
            return filter;
        }
    }
}

class MainPageButtons {
    public String name;
    public Class<?> v;
    public int icon;

    public MainPageButtons(String name, Class<?> v, int icon) {
        super();
        this.name = name;
        this.v = v;
        this.icon = icon;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Class<?> getActivity() {
        return v;
    }
    public void setActivity(Class<?> v) {
        this.v = v;
    }
    public int getIcon() {
        return this.icon;
    }
    public void setIcon(int icon) {
        this.icon = icon;
    }
}