package com.ahmedonics.apps.ultimateutilitymaster.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.ahmedonics.apps.ultimateutilitymaster.R;

import java.io.IOException;
import java.io.InputStream;

public class AboutPagePrivacyFragment extends Fragment {

    public AboutPagePrivacyFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View returnView = inflater.inflate(R.layout.fragment_about_page_privacy, container, false);
        String tContents = "";
        try {
            InputStream stream = getActivity().getAssets().open("privacy.txt");

            int size = stream.available();
            byte[] buffer = new byte[size];
            stream.read(buffer);
            stream.close();
            tContents = new String(buffer);
        } catch (IOException e) {
            // Handle exceptions here
        }
        TextView et4 = (TextView) returnView.findViewById(R.id.textView);
        et4.setText(tContents);
        return returnView;
    }

}