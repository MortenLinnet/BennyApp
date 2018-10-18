package com.example.morte.bennyapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

public class BennyMain extends AppCompatActivity {


    int RECORD_AUDIO = 0; //Skal bruges til tilladelse om at optagee lyd
    private static final int MY_PERMISSION_REQUEST = 1;  //Skal bruges til at tilgå external storage

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_benny_main);


        WindowManager.LayoutParams layout = getWindow().getAttributes();
        layout.screenBrightness = 1F; //https://developer.android.com/reference/android/view/WindowManager.LayoutParams#screenBrightness 1 er max value
        getWindow().setAttributes(layout);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);   // disable sleep også i manifest.

        // Ting vi skal have tilladelse til:
        //Optage Lyd
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO},
                    RECORD_AUDIO);
        }
        // Tilgå External Storage
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)){
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSION_REQUEST);

            }
            else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSION_REQUEST);

            }

        }
        else{
          // Tænker bare det er her vi initialiserer vores arrays af lyd.  doStuff();

        }

    }








    public void GoToBennyEyes(View view) {
        Intent i = new Intent(this, BennyEyes.class); startActivity(i);
    }
}
