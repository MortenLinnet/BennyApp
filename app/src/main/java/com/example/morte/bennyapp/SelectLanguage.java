package com.example.morte.bennyapp;

import android.content.Intent;
import android.graphics.Typeface;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class SelectLanguage extends AppCompatActivity {

    int id;
    boolean booleanDanish = false;
    boolean booleanEnglish = false;
    boolean booleanGerman = false;
    boolean startButtonChanged = false;
    ImageButton danishButton;
    ImageButton englishButton;
    ImageButton germanButton;
    ImageButton clickedButton;
    ImageButton startButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_language);

        Typeface bold_font = Typeface.createFromAsset(getAssets(), "font/webfont.ttf");
        Typeface standard_font = Typeface.createFromAsset(getAssets(), "font/Effra Light.ttf");
        TextView header_Text = (TextView) findViewById(R.id.headerText);
        TextView main_Text = (TextView) findViewById(R.id.mainText);
        header_Text.setTypeface(bold_font);
        main_Text.setTypeface(standard_font);
    }

    public void startApp (View v) {
        Intent i = new Intent(this, LoggingData.class);
        startActivity(i);
    }


   /** public void LangSelect (View v) {
        danishButton = (ImageButton) findViewById(R.id.danish);
        englishButton = (ImageButton) findViewById(R.id.english);
        germanButton = (ImageButton) findViewById(R.id.german);
        clickedButton = (ImageButton) v;

        switch (clickedButton.getId()) {

            case R.id.danish:
                danish = true;

                if (german || english) {
                    englishButton.setImageResource(R.drawable.en);
                    germanButton.setImageResource(R.drawable.de);
                }

                clickedButton.setImageResource(R.drawable.dag);
                Toast.makeText(this, "LOL!", Toast.LENGTH_SHORT).show();
                //isStartButtonChanged(true);
                startButton.setImageResource(R.drawable.startbutton);
                break;

            case R.id.english:
                english = true;

                if (danish || german) {
                    danishButton.setImageResource(R.drawable.da);
                    germanButton.setImageResource(R.drawable.de);


                }
                clickedButton.setImageResource(R.drawable.eng);
                //isStartButtonChanged(true);
                startButton.setImageResource(R.drawable.startbutton);
                break;

            case R.id.german:

                //if (danish || english) {
                //    danishButton.setImageResource(R.drawable.da);
                //    englishButton.setImageResource(R.drawable.en);
                //}
                clickedButton.setImageResource(R.drawable.deg);
                //isStartButtonChanged(true);
                startButton.setImageResource(R.drawable.startbutton);
                break;
        }
    }   **/

    public void danish (View v) {
        danishButton = (ImageButton) findViewById(R.id.danish);


        //if (german || english) {
        //    englishButton.setImageResource(R.drawable.en);
        //    germanButton.setImageResource(R.drawable.de);
        //}

        danishButton.setImageResource(R.drawable.dag);
        Toast.makeText(this, "LOL!", Toast.LENGTH_SHORT).show();
        isStartButtonChanged(true);
        //startButton.setImageResource(R.drawable.startbutton);
    }

    public void english (View v) {

    }

    public void isStartButtonChanged (Boolean value) {
        startButton = (ImageButton) findViewById(R.id.startButton);
        startButtonChanged = value;

        if (startButtonChanged) {
            startButton.setImageResource(R.drawable.startbutton);
        }
    }

    public void setEnglishButton () {
        englishButton = (ImageButton) findViewById(R.id.english);

        if (booleanEnglish) {
            englishButton.setImageResource(R.drawable.eng);

        }
        else if (!booleanEnglish) {
            englishButton.setImageResource(R.drawable.en);
        }
    }
}
