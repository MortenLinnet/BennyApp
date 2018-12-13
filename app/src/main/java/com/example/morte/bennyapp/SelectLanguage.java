package com.example.morte.bennyapp;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
    public static String BennyPreferences;
    boolean booleanDanishActive = false;
    boolean booleanEnglishActive = false;
    boolean booleanGermanActive = false;
    boolean startButtonChanged = false;
    public static String DanishActive;
    public static String EnglishActive;
    public static String GermanActive;
    public static String AppLanguage;

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
        setContentView(R.layout.activity_select_language);
        Bennyclothes = findViewById(R.id.BennyClothes);
        startButton = findViewById(R.id.startButton);
        startButton.setEnabled(false);

        danishButton = (ImageButton) findViewById(R.id.danish);
        englishButton = (ImageButton) findViewById(R.id.english);
        germanButton = (ImageButton) findViewById(R.id.german);
        startButton = (ImageButton) findViewById(R.id.startButton);
        header_Text = (TextView) findViewById(R.id.headerText);
        main_Text = (TextView) findViewById(R.id.mainText);

        Typeface bold_font = Typeface.createFromAsset(getAssets(), "font/webfont.ttf");
        Typeface standard_font = Typeface.createFromAsset(getAssets(), "font/Effra Light.ttf");
        header_Text.setTypeface(bold_font);
        main_Text.setTypeface(standard_font);
        animatorSet = new AnimatorSet();

    }
        startButton.setEnabled(false);

    public void startApp (View v) {
        Intent i = new Intent(this, Introduction.class);
        startActivity(i);
    }
        SharedPreferences pref = getSharedPreferences(BennyPreferences, Context.MODE_PRIVATE);
        AppLanguage = pref.getString("AppLanguage", null);

        if (AppLanguage != null) {
            if (AppLanguage.equals("DanishActive")) {
                SwitchBetweenLang(1);
            }
            else if (AppLanguage.equals("EnglishActive") ) {
                SwitchBetweenLang(2);
            }
            else if (AppLanguage.equals("GermanActive")) {
                SwitchBetweenLang(3);
            }
        }

        Toast.makeText(this, "Applang = " + AppLanguage, Toast.LENGTH_SHORT).show();
    }

    public void SwitchBetweenLang (Integer LangInt) {
        switch (LangInt) {
            case 1:
                booleanDanishActive = true;

                if (booleanEnglishActive || booleanGermanActive) {
                    englishButton.setImageResource(R.drawable.en);
                    germanButton.setImageResource(R.drawable.de);
                }
                danishButton.setImageResource(R.drawable.dag);
                SetStrings(1);

                isStartButtonChanged(true);
                save("DanishActive");
                break;

            case 2:
                booleanEnglishActive = true;

                if (booleanDanishActive || booleanGermanActive) {
                    danishButton.setImageResource(R.drawable.da);
                    germanButton.setImageResource(R.drawable.de);
                }
                englishButton.setImageResource(R.drawable.eng);
                SetStrings(2);

                isStartButtonChanged(true);
                save("EnglishActive");
                break;

            case 3:
                booleanGermanActive = true;

                if (booleanDanishActive || booleanEnglishActive) {
                    danishButton.setImageResource(R.drawable.da);
                    englishButton.setImageResource(R.drawable.en);
                }
                germanButton.setImageResource(R.drawable.deg);
                SetStrings(3);

                isStartButtonChanged(true);
                save("GermanActive");
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

    public void danish (View v) {
        danishButton = (ImageButton) findViewById(R.id.danish);
animations(R.drawable.danish);
        SwitchBetweenLang(1);
    }

    public void english (View v) {
        SwitchBetweenLang(2);
    }

    public void german (View v) {
        SwitchBetweenLang(3);
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

    public void startApp (View v) {
        Intent i = new Intent(this, LoggingData.class);
        startActivity(i);
    }

    public void SetStrings(Integer lang) {
        if (lang == 1) {
            header_Text.setText("HEJ VENNER!");
            main_Text.setText("Du har valgt dansk!");
        }

        if (lang == 2) {
            header_Text.setText("HI FRIENDS!");
            main_Text.setText("You have chosen English!");
        }
        if (lang == 3) {
            header_Text.setText("HALLO FREUNDE!");
            main_Text.setText("Sie haben Deutsch gewählt!");
        }
    }

    public void save (String input) {
        SharedPreferences prefs = getSharedPreferences(BennyPreferences, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("AppLanguage", input);
        editor.apply();
        Toast.makeText(this, "Language saved: " + input, Toast.LENGTH_SHORT).show();
    }
}
