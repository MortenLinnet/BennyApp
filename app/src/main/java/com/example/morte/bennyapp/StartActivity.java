package com.example.morte.bennyapp;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class StartActivity extends AppCompatActivity {

    int id;
    public static final String BennyPreferences = "BennyPreferences";
    boolean booleanDanishActive = false;
    boolean booleanEnglishActive = false;
    boolean booleanGermanActive = false;
    boolean startButtonChanged = false;
    public static Integer AppLanguage;

    ImageButton danishButton;
    ImageButton englishButton;
    ImageButton germanButton;
    ImageButton startButton;
    ImageView Bennyclothes;
    AnimatorSet animatorSet;
    TextView header_Text;
    TextView main_Text;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_activity);
        Bennyclothes = findViewById(R.id.BennyClothes);
        startButton = findViewById(R.id.startButton);
        startButton.setEnabled(false);

        danishButton = (ImageButton) findViewById(R.id.danish);
        englishButton = (ImageButton) findViewById(R.id.english);
        startButton = (ImageButton) findViewById(R.id.startButton);
        header_Text = (TextView) findViewById(R.id.headerText);
        main_Text = (TextView) findViewById(R.id.mainText);

        Typeface headline = Typeface.createFromAsset(getAssets(), "font/effra_Heavy.ttf");
        Typeface bodyText = Typeface.createFromAsset(getAssets(), "font/effra_Regular.ttf");
        header_Text.setTypeface(headline);
        main_Text.setTypeface(bodyText);
        animatorSet = new AnimatorSet();


        SharedPreferences pref = getSharedPreferences(BennyPreferences, Context.MODE_PRIVATE);
        AppLanguage = pref.getInt("AppLanguage", 0);

        if (AppLanguage != 0) {
            if (AppLanguage.equals(1)) {
                SwitchBetweenLang(1);
                animations(R.drawable.robertenglish);
            }
            else if (AppLanguage.equals(2) ) {
                SwitchBetweenLang(2);
                animations(R.drawable.robertdanish);
            }
        }

        //Toast.makeText(this, "Applang = " + AppLanguage, Toast.LENGTH_SHORT).show();
    }


     //   startButton.setEnabled(false);

    public void startApp (View v) {
        Intent i = new Intent(this, Introduction.class);
        startActivity(i);
    }


    public void SwitchBetweenLang (Integer LangInt) {
        switch (LangInt) {
            case 1:
                booleanEnglishActive = true;

                if (booleanDanishActive) {
                    danishButton.setImageResource(R.drawable.danish);
                }
                englishButton.setImageResource(R.drawable.englishg);
                SetStrings(1);

                isStartButtonChanged(true);
                save(1);
                break;


            case 2:
                booleanDanishActive = true;

                if (booleanEnglishActive) {
                    englishButton.setImageResource(R.drawable.english);
                }
                danishButton.setImageResource(R.drawable.danishg);
                SetStrings(2);

                isStartButtonChanged(true);
                save(2);
                break;
        }
    }



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

    public void english (View v) {
        SwitchBetweenLang(1);
        animations(R.drawable.robertenglish);

    }

    public void danish (View v) {
     //   danishButton = (ImageButton) findViewById(R.id.danish);  // Det er mig der lige har taget det med fra merge 13/12
        animations(R.drawable.robertdanish);                                   // samme som oven
        SwitchBetweenLang(2);
    }



    public void isStartButtonChanged (Boolean value) {

        startButton.setEnabled(true);

        startButtonChanged = value;

        if (startButtonChanged) {
            startButton.setImageResource(R.drawable.startbutton);
        }

        if (!startButton.isEnabled()) {
            startButton.setEnabled(true);
            startButton.setImageResource(R.drawable.startbutton);
        }
    }



    public void SetStrings(Integer lang) {
        if (lang == 1) {
            header_Text.setText("HI FRIENDS!");
            main_Text.setText("Get ready to play! \n First, you need to choose \n which language Robert \n should speak in:");
        }

        if (lang == 2) {
            header_Text.setText("HEJ VENNER!");
            main_Text.setText("Gør jer klar til at lege! \n Først skal i vælge \n hvilket sprog Robert \n skal snakke:");
        }
    }

    public void save (Integer input) {
        SharedPreferences prefs = getSharedPreferences(BennyPreferences, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("AppLanguage", input);
        editor.apply();
        //Toast.makeText(this, "Language saved: " + input, Toast.LENGTH_SHORT).show();
    }
}
