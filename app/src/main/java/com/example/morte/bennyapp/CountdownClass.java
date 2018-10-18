package com.example.morte.bennyapp;

import android.os.CountDownTimer;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

/**
 * Created by morte on 15-10-2018.
 */




public class CountdownClass {

    private static long TotalTImeForTask;
    private TextView TaskTextView;
    private CountDownTimer TaskCountdownTimer;
    private boolean TaskIsRunning;
    private long TimeLeftInMillisTask = TotalTImeForTask;


    public CountdownClass(long TotalTimeForTaskConstructor, TextView TimeTextViewConstructor){

TotalTImeForTask = TotalTimeForTaskConstructor;
TaskTextView = TimeTextViewConstructor;
TimeLeftInMillisTask = TotalTImeForTask;


    }

    public void StartTimer (){

        TaskCountdownTimer = new CountDownTimer(TimeLeftInMillisTask, TotalTImeForTask) {


            @Override
            public void onTick(long MillisUntillFinish) {
                TimeLeftInMillisTask = MillisUntillFinish;
                UpDownCountDownTextBox();
            }

            @Override
            public void onFinish() {
TaskIsRunning = false;

            }
        }.start();
        TaskIsRunning = true;

    }

    public void UpDownCountDownTextBox() {
        int min = (int)(TimeLeftInMillisTask/1000) / 60;
        int secs = (int)(TimeLeftInMillisTask/1000) % 60;

        String TimeleftFormatted = String.format(Locale.getDefault(),"%02d:%02d", min, secs);
        TaskTextView.setText(TimeleftFormatted);

    }

}
