package com.example.morte.bennyapp;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.PowerManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class BennyEyes extends AppCompatActivity {

    /* constants */
    private static final int POLL_INTERVAL = 300;

    /* running state*/
    private boolean mRunning = false;

    /*config state*/
    private int mThreshold;

    int RECORD_AUDIO = 0;
    private PowerManager.WakeLock mWakeLock;
    private Handler mHandler = new Handler();
    /* References to view elements */
    private TextView mStatusView, DecibelTextView;
    /* sound data source */
    private DetectNoise mSensor;
    ProgressBar bar;
    /*Define runnable thread again and again detect noise*/

    private Runnable mSleepTask = new Runnable() {
        public void run() {
            //Log.i("Noise", "runnable mSleepTask");
            mSensor.start();
        }
    };

    // Create runnable thread to Monitor Voice
    private Runnable mPollTask = new Runnable() {

        public void run() {
            double amp = mSensor.getAmplitude();
            //Log.i("Noise", "runnable mPollTask");

            updateDisplay("Listening for bricks....", amp);

            if ((amp > mThreshold)) {
                //callForHelp(amp);
                //Log.i("Noise", "==== onCreate ===");
            }

            if (amp > 7){
                //Button Klodsindtaget = findViewById(R.id.forsjov);
                //Klodsindtaget.setAlpha(1);
             //   ImageView lol = findViewById(R.id.ojneview);
               // lol.setImageResource(R.drawable.venstre1);
            }
            else {


               // ImageView lol = findViewById(R.id.ojneview);
                //lol.setImageResource(R.drawable.ligeud);
                // Button Klodsindtaget = findViewById(R.id.forsjov);
                //Klodsindtaget.setAlpha(0);
            }
            // Runnable(mPollTask) will again execute after POLL_INTERVAL
            mHandler.postDelayed(mPollTask, POLL_INTERVAL);
        }
    };

// Super request (Det omkring liggende)
    private static long SuperRequestTid = 40000;
    private TextView SuperRequestTimeTextView;
    private CountDownTimer SuperRequestTidCountdownTimer;
    private boolean SuperRequestTidIsRunning;
    private long TimeLeftInMillisSuperRequestTime = SuperRequestTid;

    // Request
    private static long RequestNotActiveTime = 25000;
    private TextView RequestNotActiveTextView;
    private CountDownTimer RequestNotActiveCountdownTimer;
    private boolean RequestNotActiveeIsRunning;
    private long TimeLeftInMillisRequestNotActive = RequestNotActiveTime;

// Feedback
    private static long FeedbackCooldown = 5000;
    private TextView FeedbackCDTextView;
    private CountDownTimer FeedbackCoolDownCountdowntimer;
    private boolean FeedBackCoolDownIsRunning;
    private long TimeLeftInMillisFeedbackCooldDown = FeedbackCooldown;

    private boolean BrickDetected;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_benny_eyes);


SuperRequestTimeTextView = findViewById(R.id.SuperRequestTView);         //Timer
RequestNotActiveTextView = findViewById(R.id.RequestNotActiveTView);     //Timer
FeedbackCDTextView = findViewById(R.id.FeedbackTView);                   //Timer
DecibelTextView =(TextView)findViewById(R.id.NoiseTextView);             //Lyd


SuperRequestTimerStart();                                                //Timer
RequestNotActiveTimerStart();                                            //Timer
FeedbackTimerTimerStart();                                               //Timer



 // Used to record Sound
mSensor = new DetectNoise();
PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
mWakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "NoiseAlert");


    }
    @Override
    public void onResume() {
        super.onResume();
        //Log.i("Noise", "==== onResume ===");

        initializeApplicationConstants();
        if (!mRunning) {
            mRunning = true;
            start();
        }
    }
    @Override
    public void onStop() {
        super.onStop();
        // Log.i("Noise", "==== onStop ===");
        //Stop noise monitoring
        stop();
    }




    private void start() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO},
                    RECORD_AUDIO);
        }

        //Log.i("Noise", "==== start ===");
        mSensor.start();
        if (!mWakeLock.isHeld()) {
            mWakeLock.acquire();
        }
        //Noise monitoring start
        // Runnable(mPollTask) will execute after POLL_INTERVAL
        mHandler.postDelayed(mPollTask, POLL_INTERVAL);
    }

    private void stop() {
        Log.d("Noise", "==== Stop Noise Monitoring===");
        if (mWakeLock.isHeld()) {
            mWakeLock.release();
        }
        mHandler.removeCallbacks(mSleepTask);
        mHandler.removeCallbacks(mPollTask);
        mSensor.stop();
        updateDisplay("stopped...", 0.0);
        mRunning = false;
    }

    private void initializeApplicationConstants() {
        // Set Noise Threshold
        mThreshold = 8;

    }

    private void updateDisplay(String status, double signalEMA) {
       
        Log.d("SONUND", String.valueOf(signalEMA));
        DecibelTextView.setText(signalEMA+"dB");
    }

    private void callForHelp(double signalEMA) {

        //stop();
        // Show alert when noise thersold crossed
        Toast.makeText(getApplicationContext(), "Noise Thersold Crossed, do here your stuff.",
                Toast.LENGTH_LONG).show();
        Log.d("SONUND", String.valueOf(signalEMA));
        DecibelTextView.setText(signalEMA+"dB");
    }

    private void SuperRequestTimerStart(){
        SuperRequestTidCountdownTimer = new CountDownTimer(TimeLeftInMillisSuperRequestTime, 1000) {


            @Override
            public void onTick(long millisuntillfinish) {
                TimeLeftInMillisSuperRequestTime = millisuntillfinish;
                UpdateSuperRequestTextview();
            }

            @Override
            public void onFinish() {
                SuperRequestTidIsRunning = false;
            }
        }.start();
        SuperRequestTidIsRunning = true;
    }

    private void RequestNotActiveTimerStart(){
        RequestNotActiveCountdownTimer  = new CountDownTimer(RequestNotActiveTime, 1000) {

            @Override
            public void onTick(long millisuntillfinish2) {
                TimeLeftInMillisRequestNotActive = millisuntillfinish2;
                UpdateRequestNotActiveTextView();
            }

            @Override
            public void onFinish() {
                RequestNotActiveeIsRunning = false;
            }
        }.start();
        RequestNotActiveeIsRunning = true;
    }

    private void FeedbackTimerTimerStart(){
        FeedbackCoolDownCountdowntimer = new CountDownTimer(TimeLeftInMillisFeedbackCooldDown, 1000) {

            @Override
            public void onTick(long millisuntillfinish2) {
                TimeLeftInMillisFeedbackCooldDown = millisuntillfinish2;
                UpdateFeedbackCooldownTextView();
            }

            @Override
            public void onFinish() {
                FeedBackCoolDownIsRunning = false;


            }
        }.start();
        FeedBackCoolDownIsRunning = true;
    }

    private void UpdateSuperRequestTextview() {
        int min = (int)(TimeLeftInMillisSuperRequestTime/1000) / 60;
        int secs = (int)(TimeLeftInMillisSuperRequestTime/1000) % 60;

        String TimeleftFormatted = String.format(Locale.getDefault(),"%02d:%02d", min, secs);
        SuperRequestTimeTextView.setText(TimeleftFormatted);

        if (TimeLeftInMillisSuperRequestTime < 2000){


            resettimer();

        }
    }

    private void UpdateFeedbackCooldownTextView() {
        int min = (int) (TimeLeftInMillisFeedbackCooldDown / 1000) / 60;
        int secs = (int) (TimeLeftInMillisFeedbackCooldDown / 1000) % 60;

        String TimeleftFormatted = String.format(Locale.getDefault(), "%02d:%02d", min, secs);
        FeedbackCDTextView.setText(TimeleftFormatted);

        if (TimeLeftInMillisFeedbackCooldDown < 2000) {
           // resettimer();

        }
    }

    private void UpdateRequestNotActiveTextView() {
        int min = (int)(TimeLeftInMillisRequestNotActive /1000) / 60;
        int secs = (int)(TimeLeftInMillisRequestNotActive /1000) % 60;

        String TimeleftFormatted = String.format(Locale.getDefault(),"%02d:%02d", min, secs);
        RequestNotActiveTextView.setText(TimeleftFormatted);
        if (BrickDetected == true){
            RequestNotActiveCountdownTimer.cancel();    // Super gay måde at løse problemet med at nulstille tiden men det virker, beklager fremtidige Morten.
            RequestNotActiveTimerStart();
            BrickDetected = false;

        }
    }

    private void resettimer(){
        TimeLeftInMillisFeedbackCooldDown = FeedbackCooldown;
        UpdateFeedbackCooldownTextView();
        FeedbackCoolDownCountdowntimer.cancel();




    }

    public void Klodsindtaget(View view) {
       FeedbackTimerTimerStart();
        BrickDetected = true;

    }

}


