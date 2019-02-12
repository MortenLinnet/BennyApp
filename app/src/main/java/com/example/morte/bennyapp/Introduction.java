package com.example.morte.bennyapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Introduction extends AppCompatActivity {
    public static final String BennyPreferences = "BennyPreferences";
    public static Integer AppLanguage;
    TextView introduction;
    TextView BodyTextView;
    String HeadlineString;
    String BodyText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduction);

        SharedPreferences pref = getSharedPreferences(BennyPreferences, Context.MODE_PRIVATE);
        AppLanguage = pref.getInt("AppLanguage", 0);
        setStrings(AppLanguage);

        Typeface effra_Heavy = Typeface.createFromAsset(getAssets(), "font/effra_Heavy.ttf");
        Typeface effra_Regular = Typeface.createFromAsset(getAssets(), "font/effra_Regular.ttf");

        introduction = findViewById(R.id.Headline);
        introduction.setTypeface(effra_Heavy);
        introduction.setText(HeadlineString);

        BodyTextView = findViewById(R.id.bodyText);
        BodyTextView.setTypeface(effra_Regular);
        BodyTextView.setText(BodyText);



    }

    public void startBennyMain (View v) {
        Intent i = new Intent(this, BennyMain.class);
        startActivity(i);
    }

    public void setStrings (int Id){

        if (Id == 1){
            HeadlineString = "INTRODUCTION";
            BodyText = "Before you begin to play, you need to\nchoose which personality to play\nwith. Each of the personalities have\ndifferent traits and requests.";
        }

        if (Id == 2){
            HeadlineString = "INTRODUKTION";
            BodyText = "Før du begynder at lege, skal du vælge en personlighed at lege med.\nHver personlighed har forskellige karaktertræk og anmodninger.";
        }

    }
}
