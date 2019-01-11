package com.example.morte.bennyapp;

import android.app.Activity;
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == 1){
        if (resultCode == Activity.RESULT_OK){
            Intent intent = getIntent();
            Bundle extras = intent.getExtras();
            int BuildingValue = extras.getInt("buildingvalue");
            int Collectingvalue = extras.getInt("collectingvalue");
            Toast.makeText(this, "It all went well" + BuildingValue +" " + Collectingvalue, Toast.LENGTH_SHORT).show();


        }
        if (resultCode == Activity.RESULT_CANCELED){
            Toast.makeText(this, "Something went horribly horribly wrong", Toast.LENGTH_SHORT).show();
        }
    }

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

    public void ToDiffLevel(View view) {
        Intent i = new Intent(this, LevelOfDifficulty.class); startActivityForResult(i, 1);


    }

    public void ActivityNilan (View v) {
        Intent i = new Intent(this, Introduction.class);
        startActivity(i);
    }

    public void openMortenactiivty(View view) {

        Intent i = new Intent(this, Introduction.class);
        startActivity(i);
    }
}


