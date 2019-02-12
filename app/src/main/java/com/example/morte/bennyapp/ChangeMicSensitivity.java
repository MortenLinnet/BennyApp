package com.example.morte.bennyapp;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.PowerManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class ChangeMicSensitivity extends AppCompatActivity {



    int SensNumber;
TextView display;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_mic_sensitivity);

         display = findViewById(R.id.ChangeMicLevelNumber);


        SensNumber = 7;
display.setText("7");

    }



    public void Minus(View view) {

        SensNumber--;

        updatedisplay(SensNumber);


    }

    public void Plus(View view) {

        SensNumber++;

                updatedisplay(SensNumber);

    }

    public void updatedisplay(int currentnumber){
String lol;
        lol = new Integer(currentnumber).toString();
       // lol.valueOf(currentnumber);
        display.setText(lol);


    }
}
