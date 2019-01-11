package com.example.morte.bennyapp;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.List;

public class MicNotPluggedIn extends AppCompatActivity {
int Language;
boolean HasMicBeenPluggedIn;
CountDownTimer SaKanViStoppeSkidtet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mic_not_plugged_in);
        //Gud hvor har je kneppet meget rundt i det her, det giver ingen mening men nu virker det
        // tror det crashede pga. threading den hoppede af.
        HasMicBeenPluggedIn=false;

        try {
            Language= 0;
            Intent intent = getIntent();
            Bundle bundle = intent.getExtras();
            Integer lol = bundle.getInt("Language");
            Language = lol;
            Toast.makeText(this, "Tal er" + Language, Toast.LENGTH_SHORT).show();
        }
        catch (NullPointerException e){
            Toast.makeText(this, "Nullpointer catched", Toast.LENGTH_SHORT).show();
        }

        CheckForMicConnected();
    }

    public void CheckForMicConnected(){

        SaKanViStoppeSkidtet = new  CountDownTimer(1000, 500) {

            public void onTick(long millisUntilFinished) {
                IsMicPluggedIn(MicNotPluggedIn.this);
               // Toast.makeText(MicNotPluggedIn.this, "tjekker", Toast.LENGTH_SHORT).show();

            }

            public void onFinish() {

                if (HasMicBeenPluggedIn){

                    StupidWayToBeginBennyEyes();
                }
                else
                    CheckForMicConnected();
            }
        }.start();
    }

    public void IsMicPluggedIn(Context context){


        AudioManager am = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);

        if(am.isWiredHeadsetOn()) {
            // handle headphones plugged in

            HasMicBeenPluggedIn=true;

        } else{


        }
    }

    public void StupidWayToBeginBennyEyes(){

        Bundle bundle = new Bundle();
        Integer language = Language;
        bundle.putInt("Language", language);
        Intent i = new Intent(this, BennyEyes.class);
        i.putExtras(bundle);
        startActivity(i);

    }

    @Override
    protected void onStop() {
        super.onStop();
        SaKanViStoppeSkidtet.cancel();
        finish();                        //Vi går tilbage til main når vi klikker retur
    }

    public void GoOnWithOutMic(View view) {
        StupidWayToBeginBennyEyes();

    }

    public void Return(View view) {
        onStop();
    }
}
