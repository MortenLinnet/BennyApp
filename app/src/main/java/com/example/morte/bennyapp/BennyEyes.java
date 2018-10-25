package com.example.morte.bennyapp;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
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
import android.widget.VideoView;

import java.util.Locale;
import java.util.Random;
import java.util.logging.Level;

public class BennyEyes extends AppCompatActivity {

   public boolean BrickDetected;                             //ARGHGHGHGHGHGGHGHGHGHGH
   public boolean ReadyForFeedback;
   public boolean IsMediaPlayerOccupied;

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

                FeedbackWhenMicrohoneIsTriggered();




                //Button Klodsindtaget = findViewById(R.id.forsjov);
                //Klodsindtaget.setAlpha(1);
             //   ImageView PlayBennyOjne = findViewById(R.id.ojneview);
               // PlayBennyOjne.setImageResource(R.drawable.venstre1);
            }
            else {


               // ImageView PlayBennyOjne = findViewById(R.id.ojneview);
                //PlayBennyOjne.setImageResource(R.drawable.ligeud);
                // Button Klodsindtaget = findViewById(R.id.forsjov);
                //Klodsindtaget.setAlpha(0);
            }
            // Runnable(mPollTask) will again execute after POLL_INTERVAL
            mHandler.postDelayed(mPollTask, POLL_INTERVAL);
        }
    };



    // Super request (Det omkring liggende)
    private static long SuperRequestTid = 10000;
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
    private static long FeedbackCooldown = 7000;
    private TextView FeedbackCDTextView;
    private CountDownTimer FeedbackCoolDownCountdowntimer;
    private boolean FeedBackCoolDownIsRunning;
    private long TimeLeftInMillisFeedbackCooldDown = FeedbackCooldown;


    private VideoView OjneView;
    String[] BennyHappyOjneArray;
    String[] BennyNoFeelingOjneArray;
    String[] BennyNotPleasedOjneArray;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_benny_eyes);


SuperRequestTimeTextView = findViewById(R.id.SuperRequestTView);         //Timer
RequestNotActiveTextView = findViewById(R.id.RequestNotActiveTView);     //Timer
FeedbackCDTextView = findViewById(R.id.FeedbackTView);                   //Timer
DecibelTextView =(TextView)findViewById(R.id.NoiseTextView);             //Lyd
OjneView = findViewById(R.id.BennyOjne);                                 //OjneView

SuperRequestTimerStart();                                                //Timer
RequestNotActiveTimerStart();                                            //Timer
FeedbackTimerTimerStart();                                               //Timer

StartRequestRound();

 // Used to record Sound
mSensor = new DetectNoise();
PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
mWakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "NoiseAlert");


ReadyForFeedback = false;

        InitBennyOjneArray();


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
        FeedbackCoolDownCountdowntimer.cancel();
        RequestNotActiveCountdownTimer.cancel();
        SuperRequestTidCountdownTimer.cancel();
     //   Toast.makeText(this, "", Toast.LENGTH_SHORT).cancel();
        // Log.i("Noise", "==== onStop ===");
        //Stop noise monitoring
        stop();

    }


    public void FeedbackWhenMicrohoneIsTriggered() {
        findViewById(R.id.BennyOjne).setAlpha(1);  //Fjern baggrund
        if (ReadyForFeedback == true){
            PlayBennyHappyOjne();
            ReadyForFeedback = false;
            FeedbackCoolDownCountdowntimer.start();
            RequestNotActiveCountdownTimer.cancel();
            RequestNotActiveCountdownTimer.start();
            PlayFeedBack(1);
        }
      //  if (ReadyForFeedback == false)
  //          Toast.makeText(this, "I else", Toast.LENGTH_SHORT).show();
       // FeedbackCoolDownCountdowntimer.start();
    }


    public void LevelOfRequestDifficulty() {
        Random r = new Random();
        int n = r.nextInt(100);

        if (n <= 40) //HardTask;
        {
            mediaPlayer = MediaPlayer.create(this, R.raw.sultenalleklodser);
            mediaPlayer.start();
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    mediaPlayer.reset();
                }
            });

        }

        if (n > 40) //EzTask
        {
           //Play Ez request.
            mediaPlayer = MediaPlayer.create(this, R.raw.sultenblaaklodser);
            mediaPlayer.start();
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    mediaPlayer.reset();
                }
            });

        }


    }

    public void PlayFeedBack (int i){
        // modtag sværhedsgraden af request iform af int
        Random r = new Random();
        int n = r.nextInt(100);

        if (i == 1){                        //Nem Opgave

        if (n <= 33) //HappyFeedBack
            {
                mediaPlayer = MediaPlayer.create(this, R.raw.jadenergod);
                mediaPlayer.start();
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        mediaPlayer.reset();
                    }
                });
            }

        if (n >=34 && n<=63) //NotSoHapppyFeedback
            {
                mediaPlayer = MediaPlayer.create(this, R.raw.lakkerrt);
                mediaPlayer.start();
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        mediaPlayer.reset();


                    }
                });
            }

         if (n >=64) //AngryFeedback
            {
                mediaPlayer = MediaPlayer.create(this, R.raw.smagtesjovt);
                mediaPlayer.start();

                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        mediaPlayer.reset();
                    }
                });
            }


    }
    if (i==2){                          //Svær opgave

        if (n <= 50) //HappyFeedBack
        {
            //Play Happy Feedback
        }

        if (n<=51) //NotSoHapppyFeedback
        {
            //Play Not so happy feedback
        }

    }
    }

    public void InitBennyOjneArray(){

        BennyHappyOjneArray = new String[]{


                //Insert Happy Eyes
                "android.resource://com.example.morte.bennyapp/" + R.raw.ojnekort,
                "android.resource://com.example.morte.bennyapp/" + R.raw.ojnelang,
                "android.resource://com.example.morte.bennyapp/" + R.raw.bennyleaf,
                "android.resource://com.example.morte.bennyapp/" + R.raw.bennyleaf1,
                "android.resource://com.example.morte.bennyapp/" + R.raw.bennyleaf2,
                "android.resource://com.example.morte.bennyapp/" + R.raw.bennyleaf3,
                "android.resource://com.example.morte.bennyapp/" + R.raw.bennyleaf4,
                "android.resource://com.example.morte.bennyapp/" + R.raw.bennyleaf5,
                "android.resource://com.example.morte.bennyapp/" + R.raw.ojnekort1,
                "android.resource://com.example.morte.bennyapp/" + R.raw.ojnekort2,
                "android.resource://com.example.morte.bennyapp/" + R.raw.ojnekort3,
                "android.resource://com.example.morte.bennyapp/" + R.raw.ojnekort4,
                "android.resource://com.example.morte.bennyapp/" + R.raw.ojnekort5,
                "android.resource://com.example.morte.bennyapp/" + R.raw.ojnelang1,
                "android.resource://com.example.morte.bennyapp/" + R.raw.ojnelang2,
                "android.resource://com.example.morte.bennyapp/" + R.raw.ojnelang3,
                "android.resource://com.example.morte.bennyapp/" + R.raw.ojnelang4,
                "android.resource://com.example.morte.bennyapp/" + R.raw.ojnelang5,


        };

        BennyNoFeelingOjneArray = new String[]{

                //Insert No feeling eyes

        };

        BennyNotPleasedOjneArray = new String[]{

                //Insert Not pleased eyes


        };
    }      //Arrays til alle Benny's øjne

    public void PlayBennyHappyOjne(){


        Random r = new Random();
        int ArrayLength = BennyHappyOjneArray.length;
        int nyrandom = r.nextInt(ArrayLength - 0)+0;    //Der er noget galt her. Den går udover det tilltdte   har slettet et +1 og nogle parenteser
       // Toast.makeText(this, "Nummmeret er " + nyrandom, Toast.LENGTH_SHORT).show();
         String PathToBennyEyes = BennyHappyOjneArray[nyrandom];
         Uri uriLang =Uri.parse(PathToBennyEyes);
         OjneView.setVideoURI(uriLang);
         OjneView.start();

    }

    public void PlayBennyNoFeelingOjne(){

         String PathToBennyEyes = BennyNoFeelingOjneArray[0];
         Uri uriLang =Uri.parse(PathToBennyEyes);
         OjneView.setVideoURI(uriLang);
         OjneView.start();

    }

    public void PlayBennyNotPleasedOjne(){

         String PathToBennyEyes = BennyNotPleasedOjneArray[0];
         Uri uriLang =Uri.parse(PathToBennyEyes);
         OjneView.setVideoURI(uriLang);
         OjneView.start();

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
     //   Toast.makeText(getApplicationContext(), "Noise Thersold Crossed, do here your stuff.",
       //         Toast.LENGTH_LONG).show();
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
               if (!mediaPlayer.isPlaying()){

                   StartRequestRound();
               }
               else {
                   Toast.makeText(BennyEyes.this, "Issuses", Toast.LENGTH_SHORT).show();


               }
                SuperRequestTidIsRunning = false;
            }
        }.start();
        SuperRequestTidIsRunning = true;
    }

    private void RequestNotActiveTimerStart(){
        RequestNotActiveCountdownTimer  = new CountDownTimer(TimeLeftInMillisRequestNotActive, 1000) {

            @Override
            public void onTick(long millisuntillfinish2) {
                TimeLeftInMillisRequestNotActive = millisuntillfinish2;
                UpdateRequestNotActiveTextView();
            }

            @Override
            public void onFinish() {
                RequestNotActiveeIsRunning = false;
                StartRequestRound();
            }
        }.start();
        RequestNotActiveeIsRunning = true;
    }

    private void FeedbackTimerTimerStart(){
        FeedbackCoolDownCountdowntimer = new CountDownTimer(TimeLeftInMillisFeedbackCooldDown, 1000) {

            @Override
            public void onTick(long millisuntillfinish3) {
                TimeLeftInMillisFeedbackCooldDown = millisuntillfinish3;
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


            //resettimer();

        }
    }

    private void UpdateFeedbackCooldownTextView() {
        int min = (int) (TimeLeftInMillisFeedbackCooldDown / 1000) / 60;
        int secs = (int) (TimeLeftInMillisFeedbackCooldDown / 1000) % 60;

        String TimeleftFormatted = String.format(Locale.getDefault(), "%02d:%02d", min, secs);
        FeedbackCDTextView.setText(TimeleftFormatted);

        if (TimeLeftInMillisFeedbackCooldDown < 2000) {

            ReadyForFeedback = true;
         //   Toast.makeText(this, "cd", Toast.LENGTH_SHORT).show();
            FeedbackCoolDownCountdowntimer.cancel();
           // FeedbackCoolDownCountdowntimer.start();
        }
    }

    private void UpdateRequestNotActiveTextView() {
        int min = (int)(TimeLeftInMillisRequestNotActive /1000) / 60;
        int secs = (int)(TimeLeftInMillisRequestNotActive /1000) % 60;

        String TimeleftFormatted = String.format(Locale.getDefault(),"%02d:%02d", min, secs);
        RequestNotActiveTextView.setText(TimeleftFormatted);

    }
/*
    private void resettimer(){
        TimeLeftInMillisFeedbackCooldDown = FeedbackCooldown;
        UpdateFeedbackCooldownTextView();
        FeedbackCoolDownCountdowntimer.cancel();


    }
*/
   public void Klodsindtaget(View view) {
       FeedbackTimerTimerStart();
        BrickDetected = true;

    }


   public void StartRequestRound(){
       Toast.makeText(this, "New Round", Toast.LENGTH_SHORT).show();
       FeedbackCoolDownCountdowntimer.cancel();
       RequestNotActiveCountdownTimer.cancel();
       SuperRequestTidCountdownTimer.cancel();
       LevelOfRequestDifficulty();
       FeedbackCoolDownCountdowntimer.start();
       RequestNotActiveCountdownTimer.start();
       SuperRequestTidCountdownTimer.start();


   }
}


