package com.example.morte.bennyapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class LevelOfDifficulty extends AppCompatActivity {

    private static SeekBar SbBuilding;
    private static TextView TextviewBuilding;
    private static Integer SbBuildingValue;
    //private int lol;

    private static SeekBar SbCollecting;
    private static TextView TextviewCollecting;
    private static Integer SbCollectingValue;
    //private int lol;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_of_difficulty);

SeekbarCollecting();
SeekBarBuilding();
        SbBuilding.setProgress(50);
        SbCollecting.setProgress(50);
    }

    public void SeekBarBuilding(){

        SbBuilding = findViewById(R.id.SbBuild);
        TextviewBuilding = findViewById(R.id.TwBuild);
        TextviewBuilding.setText("How much does my child like to build : " + SbBuilding.getProgress() + " / "+ SbBuilding.getMax());

        SbBuilding.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        SbBuildingValue = progress;

                        TextviewBuilding.setText("How much does my child like to build : " + SbBuildingValue + " / "+ SbBuilding.getMax());
                        SbCollecting.setProgress(SbBuilding.getMax()-SbBuildingValue);
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {



                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        TextviewBuilding.setText("How much does my child like to build : " + SbBuildingValue + " / "+ SbBuilding.getMax());

                    }

                });




    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);

    }


    public void SeekbarCollecting(){
        SbCollecting = findViewById(R.id.SbCollect);
        TextviewCollecting = findViewById(R.id.TwCollect);


        TextviewCollecting.setText("How much does my child like to collect : " + SbCollecting.getProgress() + " / "+ SbCollecting.getMax());

        SbCollecting.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                        SbCollectingValue = progress;
                        TextviewCollecting.setText("How much does my child like to collect : "  + SbCollectingValue + " / "+ SbCollecting.getMax());
                        SbBuilding.setProgress(SbCollecting.getMax()-SbCollectingValue);
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {



                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        TextviewCollecting.setText("How much does my child like to collect : "  + SbCollectingValue + " / "+ SbCollecting.getMax());

                    }

                });




    }


    public void Save(View view) {
        Intent returnIntent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putInt("buildingvalue",SbBuildingValue);
        bundle.putInt("collectingvalue",SbCollectingValue);

        returnIntent.putExtra("results", bundle);
        setResult(Activity.RESULT_OK,returnIntent);
        finish();

    }
}



