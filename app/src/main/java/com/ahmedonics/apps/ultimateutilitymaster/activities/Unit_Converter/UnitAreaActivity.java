package com.ahmedonics.apps.ultimateutilitymaster.activities.Unit_Converter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ahmedonics.apps.ultimateutilitymaster.R;
import com.ahmedonics.apps.ultimateutilitymaster.utils.CommonFunctions;

import java.util.ArrayList;

public class UnitAreaActivity extends AppCompatActivity implements Spinner.OnItemSelectedListener{

    private EditText e1,e2;
    private Spinner s0,s1,s2;
    private TextView spinnertxt0,spinnertxt,spinnertxt1;
    private ConvertingUnits.Temperature ca;
    private ConvertingUnits.Area ca1;
    private ConvertingUnits.Weight ca3;
    private ConvertingUnits.Length ca2;
    private Button equal;

    ArrayAdapter<String> arrayAdapter_parent;

    ArrayAdapter<String> arrayAdapter_child;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unit_area);

        setTitle("Unit Converter");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        e1 = findViewById(R.id.item1);
        e2 = findViewById(R.id.item2);

        s1 = findViewById(R.id.spinner1);
        s2 = findViewById(R.id.spinner2);

        ca = new ConvertingUnits.Temperature();
        ca1 = new ConvertingUnits.Area();
        ca2 = new ConvertingUnits.Length();
        ca3 = new ConvertingUnits.Weight();

        s0 = findViewById(R.id.spinner0);

        spinnertxt0 = findViewById(R.id.spinnertxt0);
        spinnertxt = findViewById(R.id.spinnertxt);
        spinnertxt1 = findViewById(R.id.spinnertxt1);

        String[] stringArray = getResources().getStringArray(R.array.units);

        arrayAdapter_parent = new ArrayAdapter<>(getApplication(),android.R.layout.simple_spinner_item,stringArray);
        s0.setAdapter(arrayAdapter_parent);

        s0.setOnItemSelectedListener(this);
        s1.setOnItemSelectedListener(this);
        s2.setOnItemSelectedListener(this);

        String[] stringArrayarea = getResources().getStringArray(R.array.area);
        String[] stringArrayTemp = getResources().getStringArray(R.array.temperature);
        String[] stringArrayLenght = getResources().getStringArray(R.array.length);
        String[] stringArrayWeight = getResources().getStringArray(R.array.weight);

        e1.setOnClickListener(view -> {
        });

        equal = findViewById(R.id.equal);

        equal.setOnClickListener(v -> {

            if (CommonFunctions.isEmptyEditText(e1)) {
                Toast.makeText(UnitAreaActivity.this, "Enter Value", Toast.LENGTH_SHORT).show();

            }else{
                switch (v.getId()) {
                    case R.id.equal:
                        int item1 = s1.getSelectedItemPosition();
                        int item2 = s2.getSelectedItemPosition();
                        double value1 = Double.parseDouble(e1.getText().toString());
                        double result = evaluate(item1, item2, value1);
                        e2.setText(result + "");
                        break;
                }
            }
        });



        s0.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i == 0){
                    arrayAdapter_child = new ArrayAdapter<>(getApplication(),android.R.layout.simple_spinner_item,stringArrayTemp);
                }

                if (i == 1){
                    arrayAdapter_child = new ArrayAdapter<>(getApplication(),android.R.layout.simple_spinner_item,stringArrayLenght);
                   // spinnertxt0.setText(s1.getSelectedItem().toString());
                }

                if (i == 2){
                    arrayAdapter_child = new ArrayAdapter<>(getApplication(),android.R.layout.simple_spinner_item,stringArrayarea);
                  //  spinnertxt0.setText(s1.getSelectedItem().toString());
                }

                 if (i == 3){
                    arrayAdapter_child = new ArrayAdapter<>(getApplication(),android.R.layout.simple_spinner_item,stringArrayWeight);
                    // spinnertxt0.setText(s1.getSelectedItem().toString());

                }

                s1.setAdapter(arrayAdapter_child);
                s2.setAdapter(arrayAdapter_child);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public double evaluate(int item1,int item2,double value){

        System.out.println("CHECK: "+s0.getSelectedItem()+" :  "+e1.getText().toString());
        double temp=0.0;

        if(item1==item2)
          return value;

        else if (s0.getSelectedItem().equals("Temperature")) {

            switch (item1) {
                case 0:
                    temp=ca.CelsiTokelvin(value);
                    break;
                case 1:
                    temp=ca.FerToKelvin(value);
                    break;
                case 2:
                    temp=value;
                    break;
            }

            switch (item2) {
                case 0:
                    temp=ca.KelvinToCelsi(temp);
                    break;
                case 1:
                    temp=ca.KelvinToFer(temp);
                    break;
            }


        } else if (s0.getSelectedItem().equals("Area")) {

            switch (item1) {
                case 0:
                    temp=ca1.sqMilliToMeter(value);
                    break;
                case 1:
                    temp=ca1.sqCentiToMeter(value);
                    break;
                case 2:
                    temp=value;
                    break;
                case 3:
                    temp=ca1.sqKiloToMeter(value);
                    break;
                case 4:
                    temp=ca1.AcreToMeter(value);
                    break;
                case 5:
                    temp=ca1.HectareToMeter(value);
                    break;
            }

            switch (item2)
            {
                case 0:
                    temp= ca1.sqMeterToMilli(temp);
                    break;
                case 1:
                    temp= ca1.sqMeterToCenti(temp);
                    break;
                case 3:
                    temp= ca1.sqMeterToKilo(temp);
                    break;
                case 4:
                    temp= ca1.sqMeterToAcre(temp);
                    break;
                case 5:
                    temp= ca1.sqMeterToHectare(temp);
                    break;
            }

        }else if (s0.getSelectedItem().equals("Length")) {
            switch (item1){
                case 0:
                    temp=ca2.NanoToMeter(value);
                    break;
                case 1:
                    temp=ca2.MilliToMeter(value);
                    break;
                case 2:
                    temp=ca2.CentiToMeter(value);
                    break;
                case 3:
                    temp=value;
                    break;
                case 4:
                    temp=ca2.KiloToMeter(value);
                    break;
                case 5:
                    temp=ca2.InchToMeter(value);
                    break;
                case 6:
                    temp=ca2.FootToMeter(value);
                    break;
                case 7:
                    temp=ca2.YardToMeter(value);
                    break;
                case 8:
                    temp=ca2.MileToMeter(value);
                    break;
            }

            switch (item2) {
                case 0:
                    temp=ca2.MeterToNano(temp);
                    break;
                case 1:
                    temp=ca2.MeterToMilli(temp);
                    break;
                case 2:
                    temp=ca2.MeterToCenti(temp);
                    break;
                case 4:
                    temp=ca2.MeterToKilo(temp);
                    break;
                case 5:
                    temp=ca2.MeterToInch(temp);
                    break;
                case 6:
                    temp=ca2.MeterToFoot(temp);
                    break;
                case 7:
                    temp=ca2.MeterToYard(temp);
                    break;
                case 8:
                    temp=ca2.MeterToMile(temp);
                    break;
            }

        }else if (s0.getSelectedItem().equals("Weight")) {


            switch (item1) {
                case 0:
                    temp=ca3.MilliToKilo(value);
                    break;
                case 1:
                    temp=ca3.CentiToKilo(value);
                    break;
                case 2:
                    temp=ca3.DeciToKilo(value);
                    break;
                case 3:
                    temp=ca3.GramToKilo(value);
                    break;
                case 4:
                    temp=value;
                    break;
                case 5:
                    temp=ca3.MetricTonnesToKilo(value);
                    break;
                case 6:
                    temp=ca3.PoundsToKilo(value);
                    break;
                case 7:
                    temp=ca3.OuncesToKilo(value);
                    break;
            }

            switch (item2) {
                case 0:
                    temp=ca3.KiloToMilli(temp);
                    break;
                case 1:
                    temp=ca3.KiloToCenti(temp);
                    break;
                case 2:
                    temp=ca3.KiloToDeci(temp);
                    break;
                case 3:
                    temp=ca3.KiloToGram(temp);
                    break;
                case 5:
                    temp=ca3.KiloToMetricTonnes(temp);
                    break;
                case 6:
                    temp=ca3.KiloToPounds(temp);
                    break;
                case 7:
                    temp=ca3.KiloToOunces(temp);
                    break;
            }
        }


        return temp;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        spinnertxt.setText(s0.getSelectedItem().toString());
        spinnertxt0.setText(s1.getSelectedItem().toString());
        spinnertxt1.setText(s2.getSelectedItem().toString());
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
