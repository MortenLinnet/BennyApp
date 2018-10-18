package com.example.morte.bennyapp;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

public class BennyMain extends AppCompatActivity {
    public static int whatText;
    public static int previousEffect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_benny_main);

        TextView playModeText = (TextView) findViewById(R.id.playButton);
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "font/bennyNormal.ttf");
        playModeText.setTypeface(custom_font);
    }


    public void viewMessage(View view) {
        ImageButton clickedButton = (ImageButton) view;
        Button playB = (Button) findViewById(R.id.playButton);
        switch (clickedButton.getId()) {
            case R.id.imageButton1:

                playB.setText("Let's Play Nurse!");
                whatText = 1;
                break;

            case R.id.imageButton2:

                playB.setText("Let's Play Hero!");
                whatText = 2;
                break;

            case R.id.imageButton3:
                playB.setText("Let's Play Cowboy!");
                whatText = 3;
                break;

            case R.id.imageButton4:
                playB.setText("Let's Play Cactus!");
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


    }
}