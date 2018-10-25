package com.example.morte.bennyapp;

import android.content.Intent;
import android.graphics.Typeface;
import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.view.WindowManager;
import android.widget.Toast;

public class BennyMain extends AppCompatActivity {
    public static int whatText; //Bruges til at finde ud af teksten på playButton-knappen
    public static int previousEffect; //Skal måske bruges til animationer


    int RECORD_AUDIO = 0; //Skal bruges til tilladelse om at optagee lyd
    private static final int MY_PERMISSION_REQUEST = 1;  //Skal bruges til at tilgå external storage

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_benny_main);

        TextView playModeText = (TextView) findViewById(R.id.playButton);
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "font/bennyNormal.ttf");
        playModeText.setTypeface(custom_font);


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


    public void viewMessage(View view) {
        ImageButton clickedButton = (ImageButton) view;
        Button playB = (Button) findViewById(R.id.playButton);
        switch (clickedButton.getId()) {
            case R.id.imageButton1:
                playB.setText("@strings/nurse");
                whatText = 1;
                break;

            case R.id.imageButton2:
                playB.setText("@strings/hero");
                whatText = 2;
                break;

            case R.id.imageButton3:
                playB.setText("@strings/cowboy!");
                whatText = 3;
                break;

            case R.id.imageButton4:
                playB.setText("@strings/cactus!");
                whatText = 4;
                break;

            case R.id.imageButton5:
                playB.setText("Let's Play Pirate!");
                whatText = 5;
                break;
        }


    }

    public void onClick(View v) {
        if (whatText == 1) {
            //Insert name of the class to be redirected to.
            Intent i = new Intent(this, BennyEyes.class); startActivity(i);

            //Intent i = new Intent(this, .class);
            //startActivity(i);

        } else if (whatText == 2) {
            Intent i = new Intent(this, NilanTestActivity.class); startActivity(i);


        } else if (whatText == 3) {

        } else if (whatText == 4) {

        } else if (whatText == 5) {

        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,  int[] grantResults) {
        switch (requestCode){
            case MY_PERMISSION_REQUEST: {
                if (grantResults.length> 0 && grantResults[0] ==  PackageManager.PERMISSION_GRANTED){

                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.READ_EXTERNAL_STORAGE) ==  PackageManager.PERMISSION_GRANTED) {


                        // doStuff();
                    }
                } else  {
                    Toast.makeText(this, "You have to give access", Toast.LENGTH_SHORT).show();
                    finish();
                }
                return;
            }
        }

    }

    private void permissionForExcelExport (String permission, Integer requestCode) {
        if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {

                // If user has denied access, then it asks again
                ActivityCompat.requestPermissions(this, new String[]{permission}, requestCode);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{permission}, requestCode);
            }
        }
        else {
            Toast.makeText(this, permission + " is already granted.", Toast.LENGTH_SHORT).show();
        }
    }

}
