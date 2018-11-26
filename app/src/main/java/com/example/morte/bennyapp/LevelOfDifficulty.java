package com.example.morte.bennyapp;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class LevelOfDifficulty extends AppCompatActivity  {


//    implements CompoundButton.OnCheckedChangeListener
    private static SeekBar SbBuilding;
    private static TextView TextviewBuilding;
    private static Integer SbBuildingValue;
    //private int lol;
 //Switch Switch1;
 //Switch Switch2;
    private static SeekBar SbCollecting;
    private static TextView TextviewCollecting;
    private static Integer SbCollectingValue;
    //private int lol;


    Button SaveButton;
    RadioGroup radioGroup;
    RadioButton radioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_of_difficulty);


        SaveButton = findViewById(R.id.SavePlayValue);
//SeekbarCollecting();
//SeekBarBuilding();

radioGroup = findViewById(R.id.radiogroup);
      //  SbBuilding.setProgress(50);
        //SbCollecting.setProgress(50);


  /*  Switch1 = findViewById(R.id.SwitchBut1);
    Switch2 = findViewById(R.id.SwitvhBut2);
    Switch1.setOnCheckedChangeListener(this);
    Switch2.setOnCheckedChangeListener(this);
*/



    }

    public void CheckButton(View view) {
    int radioID = radioGroup.getCheckedRadioButtonId();
    radioButton = findViewById(radioID);
        int position = radioGroup.indexOfChild(radioButton);   // giver mig mit ID
        Toast.makeText(this, "You selected " + position, Toast.LENGTH_SHORT).show();


        if (!SaveButton.isEnabled()) {
            SaveButton.setEnabled(true);

            ObjectAnimator GoplayButtonFadeOut = ObjectAnimator.ofFloat(SaveButton, "alpha", 0f, .1f);
            GoplayButtonFadeOut.setDuration(100);
            ObjectAnimator GoplaybuttonFadeIn = ObjectAnimator.ofFloat(SaveButton, "alpha", .1f, 1f);
            GoplaybuttonFadeIn.setDuration(450);

            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.play(GoplayButtonFadeOut).before(GoplaybuttonFadeIn);
            animatorSet.start();
        }

    }

  /*  @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

    if (Switch1.isChecked()){
Switch2.setChecked(false);

    }


    }
*/
/*
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
*/

/*
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
*/

    public void Save(View view) {
        Intent returnIntent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putInt("buildingvalue",SbBuildingValue);
        bundle.putInt("collectingvalue",SbCollectingValue);

        returnIntent.putExtra("results", bundle);
        setResult(Activity.RESULT_OK,returnIntent);
        finish();

    }


    public void SaveClick(View view) {


    }

    public void Return(View view) {
        finish();
    }
}








//Gammel XML kode
/* <SeekBar
        android:id="@+id/SbBuild"
        android:layout_width="307dp"
        android:layout_height="130dp"
        android:layout_marginBottom="8dp"
        android:layout_marginEnd="8dp"
        android:layout_marginStart="8dp"
        android:layout_marginTop="8dp"
        app:layout_constraintBottom_toTopOf="@+id/SbCollect"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintHorizontal_bias="0.508"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintVertical_bias="0.678" />

    <SeekBar
        android:id="@+id/SbCollect"
        android:layout_width="307dp"
        android:layout_height="130dp"
        android:layout_marginEnd="8dp"
        android:layout_marginStart="8dp"
        android:thumb="@drawable/legobrick"
        android:layout_marginTop="408dp"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintHorizontal_bias="0.508"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

    <TextView
        android:id="@+id/TwBuild"
        android:layout_width="175dp"
        android:layout_height="43dp"
        android:layout_marginBottom="8dp"
        android:layout_marginEnd="8dp"
        android:layout_marginStart="8dp"
        android:layout_marginTop="192dp"
        android:text="textview"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintHorizontal_bias="0.041"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintVertical_bias="0.027" />

    <TextView
        android:id="@+id/TwCollect"
        android:layout_width="173dp"
        android:layout_height="50dp"
        android:layout_marginBottom="8dp"
        android:layout_marginEnd="192dp"
        android:layout_marginStart="8dp"
        android:layout_marginTop="360dp"
        android:text="TextView"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintHorizontal_bias="0.727"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintVertical_bias="0.0" />

    <Button
        android:id="@+id/SaveButton"
        android:layout_width="match_parent"
        android:layout_height="68dp"
        android:layout_marginBottom="8dp"
        android:layout_marginTop="8dp"
        android:text="Save"
        android:onClick="Save"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintHorizontal_bias="0.0"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintVertical_bias="1.0" />

        */

