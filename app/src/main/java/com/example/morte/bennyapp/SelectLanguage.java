package com.example.morte.bennyapp;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Fade;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
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
    ImageView Bennyclothes;
AnimatorSet animatorSet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_language);
        Bennyclothes = findViewById(R.id.BennyClothes);
        startButton = findViewById(R.id.startButton);
        startButton.setEnabled(false);

        Typeface bold_font = Typeface.createFromAsset(getAssets(), "font/webfont.ttf");
        Typeface standard_font = Typeface.createFromAsset(getAssets(), "font/Effra Light.ttf");
        TextView header_Text = (TextView) findViewById(R.id.headerText);
        TextView main_Text = (TextView) findViewById(R.id.mainText);
        header_Text.setTypeface(bold_font);
        main_Text.setTypeface(standard_font);
        animatorSet = new AnimatorSet();

    }

    public void startApp (View v) {
        Intent i = new Intent(this, Introduction.class);
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



   public void animations (int src){


       Bennyclothes.setImageResource(src);
       //ObjectAnimator Fadeout = ObjectAnimator.ofFloat(Bennyclothes, "alpha",  0f, .8f);
       //Fadeout.setDuration(100);
       ObjectAnimator FadeIn = ObjectAnimator.ofFloat(Bennyclothes, "alpha", .9f, 1f);
       FadeIn.setDuration(1350);
      // animatorSet.play(Fadeout).before(FadeIn);
       animatorSet.play(FadeIn);


       animatorSet.start();


   }

    public void danish (View v) {
        danishButton = (ImageButton) findViewById(R.id.danish);

        //if (german || english) {
        //    englishButton.setImageResource(R.drawable.en);
        //    germanButton.setImageResource(R.drawable.de);
        //}
animations(R.drawable.danish);

        danishButton.setImageResource(R.drawable.dag);
        isStartButtonChanged(true);
        //startButton.setImageResource(R.drawable.startbutton);
    }

    public void english (View v) {

    }

    public void isStartButtonChanged (Boolean value) {

        startButton.setEnabled(true);

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

    public void LangSelect(View view) {
     //Bennyclothes.setImageResource(R.drawable.bennyengelsktoj);

        animations(R.drawable.british);

        //Bennyclothes.setImageResource(R.drawable.british);
      //  Toast.makeText(this, "Nilan Mangler at lave det her", Toast.LENGTH_SHORT).show();
    }
}
