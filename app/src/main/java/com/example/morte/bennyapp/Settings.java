package com.example.morte.bennyapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);





    }

    public void GobackDK(View view) {
        Bundle bundle = new Bundle();
        Integer languageIdentifyer = 2;
        bundle.putInt("Language", languageIdentifyer);
        Intent i = new Intent(this, BennyMain.class);
        i.putExtras(bundle);
        startActivity(i);

    }


    public void GobackUSA(View view) {
        Bundle bundle = new Bundle();
        Integer language = 1;
        bundle.putInt("Language", language);
        Intent i = new Intent(this, BennyMain.class);
        i.putExtras(bundle);
        startActivity(i);

    }

    public void GoToSizeOFEyes(View view) {
        Intent i = new Intent(this, SizeOfEyes.class); startActivity(i);

    }
}


