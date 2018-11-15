package com.example.morte.bennyapp;

import android.content.Intent;
import android.graphics.ImageFormat;
import android.graphics.Typeface;
import android.Manifest;
import android.content.Intent;
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
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

public class OldDesign extends AppCompatActivity {
    public static int whatText;
    public static int previousEffect;
    public Integer Language;
    String Nurse;
    String Hero;
    String Cowboy;
    String Cactus;
    String Pirate;
    String Settings;
    String LetsPlay;
    String SelectMode;


    int RECORD_AUDIO = 0; //Skal bruges til tilladelse om at optagee lyd
    private static final int MY_PERMISSION_REQUEST = 1;  //Skal bruges til at tilgå external storage

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_benny_main);

        TextView playModeText = (TextView) findViewById(R.id.playButton);
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "font/bennyNormal.ttf");
        playModeText.setTypeface(custom_font);




        try {
            Language= 1;
            Intent intent = getIntent();
            Bundle bundle = intent.getExtras();
            Integer languageIdentifyer = bundle.getInt("Language");
            Language = languageIdentifyer;


            Toast.makeText(this, "Tal er" + Language, Toast.LENGTH_SHORT).show();

        }
        catch (NullPointerException e){
            Toast.makeText(this, "Nullpointer catched", Toast.LENGTH_SHORT).show();
        }


        SetStrings(Language);

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

    public void SetStrings(Integer lang){
        if (lang== 1){
            Nurse = "Nurse";
            Hero = "Hero";
            Cowboy = "Cowboy";
            Cactus = "Cactus";
            Pirate = "Pirate";
            Settings = "Settings";
            LetsPlay = "Let us play ";
            SelectMode = "Select Mode";

        }
        if ( lang==2){
            Nurse = "Sygeplejerske";
            Hero = "Helt";
            Cowboy = "Kodreng";
            Cactus = "Kaktus";
            Pirate = "Pirat";
            Settings = "Indstillinger";
            LetsPlay = "Lad os lege";
            SelectMode = "Vælg mode";

        }
    }
    public void viewMessage(View view) {
        ImageButton clickedButton = (ImageButton) view;
        Button playB = (Button) findViewById(R.id.playButton);
        switch (clickedButton.getId()) {
            case R.id.imageButton1:

                playB.setText(LetsPlay + " " + Nurse);
                whatText = 1;
                break;

            case R.id.imageButton2:

                playB.setText(LetsPlay + " " + Hero);
                whatText = 2;
                break;

            case R.id.imageButton3:
                playB.setText(LetsPlay + " " + Cowboy);
                whatText = 3;
                break;

            case R.id.imageButton4:
                playB.setText(LetsPlay + " " + Cactus);
                whatText = 4;
                break;

            case R.id.imageButton5:
                playB.setText(LetsPlay + " " + Pirate);
                whatText = 5;
                break;
        }


    }

    public void onClick(View v) {
        if (whatText == 1) {
            //Insert name of the class to be redirected to.

            Bundle bundle = new Bundle();
            Integer language = Language;
            bundle.putInt("Language", language);
            Intent i = new Intent(this, BennyEyes.class);
            i.putExtras(bundle);
            startActivity(i);

            //Intent i = new Intent(this, .class);
            //startActivity(i);

        } else if (whatText == 2) {

        } else if (whatText == 3) {

        } else if (whatText == 4) {

        } else if (whatText == 5) {

        }

    }






    public void Settings(View view) {

        // New activity with options to calibrate, about us and so on.
        Intent i = new Intent(this, Settings.class); startActivity(i);

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
}