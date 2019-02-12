package com.example.morte.bennyapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

    public static final String BennyPreferences = "BennyPreferences";
    public Integer Sensitivity;
    Integer SensNumber;
    TextView display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_mic_sensitivity);

        SharedPreferences pref = getSharedPreferences(BennyPreferences, Context.MODE_PRIVATE);
        Sensitivity = pref.getInt("Sensitivity", 7);


        display = findViewById(R.id.ChangeMicLevelNumber);

        display.setText("" + Sensitivity);

    }



    public void Minus(View view) {

        Sensitivity--;

        updatedisplay(Sensitivity);
    }

    public void Plus(View view) {

        Sensitivity++;

        updatedisplay(Sensitivity);
    }

    public void updatedisplay(int currentnumber){
String lol;
        lol = new Integer(currentnumber).toString();
       // lol.valueOf(currentnumber);
        display.setText(lol);


    }

    public void save (View v) {
        saveInShared(Sensitivity);

    }

    public void saveInShared (Integer input) {
        SharedPreferences prefs = getSharedPreferences(BennyPreferences, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("Sensitivity", input);
        editor.apply();
        //Toast.makeText(this, "Language saved: " + input, Toast.LENGTH_SHORT).show();
    }
}
