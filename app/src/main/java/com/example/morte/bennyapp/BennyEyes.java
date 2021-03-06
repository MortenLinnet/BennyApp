package com.example.morte.bennyapp;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.JetPlayer;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.os.PowerManager;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Size;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.logging.Level;

public class BennyEyes extends AppCompatActivity {

    public boolean BrickDetected;                             //ARGHGHGHGHGHGGHGHGHGHGH
    public boolean ReadyForFeedback;
    public boolean IsMediaPlayerOccupied;
    public boolean ItsAPretendRound;
    public int HaveBeenInPresentMode;
    public boolean Notgonnastack;
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

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        public void run() {
            double amp = mSensor.getAmplitude();
            //Log.i("Noise", "runnable mPollTask");

            updateDisplay("Listening for bricks....", amp);

            if ((amp > mThreshold)) {
                //callForHelp(amp);
                //Log.i("Noise", "==== onCreate ===");
            }

            if (amp > 7) {
                save("Bricks Detected");
            }

            if (amp > 7  && mediaPlayer == null && !IdleModeIsActive && TimeLeftInMillisSuperRequestTime > 4000){  //Tjekker om mediaplayer kører for at forhindre den i at give en falsk positvi pga lyd efter Benny selv har sagt noget
                FeedbackWhenMicrohoneIsTriggered();
            }

            if (amp > 7 && mediaPlayer == null && IdleModeIsActive){  //Tjekker om mediaplayer kører for at forhindre den i at give en falsk positvi pga lyd efter Benny selv har sagt noget
                IdleModeIsActive = false;
                //    Toast.makeText(BennyEyes.this, "Vi har været i idlemode og nu begynder vi forfra", Toast.LENGTH_SHORT).show();
                IdleModeCountdowntimer.cancel();
                AmounfOfIdleRounds=0;
                StartRequestRound();
            }


            else {

            }
            // Runnable(mPollTask) will again execute after POLL_INTERVAL
            mHandler.postDelayed(mPollTask, POLL_INTERVAL);
        }
    };

    // Super request (Det omkring liggende)
    private static long SuperRequestTid = 30000;
    private TextView SuperRequestTimeTextView;
    private CountDownTimer SuperRequestTidCountdownTimer;
    private boolean SuperRequestTidIsRunning;
    private long TimeLeftInMillisSuperRequestTime = SuperRequestTid;

    // Request
    private static long RequestNotActiveTime = 22000;
    private TextView RequestNotActiveTextView;
    private CountDownTimer RequestNotActiveCountdownTimer;
    private boolean RequestNotActiveeIsRunning;
    private long TimeLeftInMillisRequestNotActive = RequestNotActiveTime;

    // Feedback
    private static long FeedbackCooldown = 6000;
    private TextView FeedbackCDTextView;
    private CountDownTimer FeedbackCoolDownCountdowntimer;
    private boolean FeedBackCoolDownIsRunning;
    private long TimeLeftInMillisFeedbackCooldDown = FeedbackCooldown;

    // IdleMode
    private static long IdleModeTime = 25000;
    private CountDownTimer IdleModeCountdowntimer;







    private VideoView OjneView;
    String[] BennyHappyOjneArray;
    String[] BennyNoFeelingOjneArray;
    String[] BennyNotPleasedOjneArray;
    //  MediaPlayer mediaPlayer;        Tog den med fra import music fra gammel kode
    Integer Language;

    int HardTaskProperbility = 40; //Under/lig 40 = 40%
    int EasyTaskProperbility = 40; //Over 40-100 == 60%


    int IdleChanceNumber;

    int TypeofFeedback;

    int HowManyTimesHaveIBeenCalledThatManyImSickOfBeingCalledAllTheTime;
    double temp;


    String tempo;
    Boolean IdleModeIsActive;
    int AmounfOfIdleRounds;
    Boolean Longpressed;
    int CohreneceBetweenEyesAndVoice;

    String tempforstringcompare;

    //Alt der har med loading af musik filerne at gøre
    ArrayList<String> RobertIdle;
    ArrayList<String> RobertCollectRequest;
    ArrayList<String> RobertKiggeNed;
    ArrayList<String> RobertBuildRequest;
    ArrayList<String> RobertPretendRequest;
    ArrayList<String> RobertHappyFeedback;
    ArrayList<String> RobertNeutralFeedback;
    ArrayList<String> RobertBaffledFeedback;
    ArrayList<String> RobertIntroduction;



    ArrayAdapter<String> adapter;
    ArrayAdapter<String> adapter2;
    MediaPlayer mediaPlayer;
//    MediaPlayer NyMp;

    //Hvilket nummer i replik arrays der senest er brugt
    int TempBuildRequest;
    int TempCollectRequest;
    int TempPretendRequest;
    int TempKiggeNed;
    int TempBaffeldFeedback;
    int TempHappyFeedback;
    int TempIdle;

    ImageButton HoldtoReleasButton;
    Long down, up;
    int[] TempBuildRequestArray = new int[2];
    int[] TempCollectRequestArray = new int[2];
    int[] TempPretendRequestArray = new int[2];
    int[] TempKiggeNedArray = new int[2];
    int[] TempBaffeldFeedbackArray = new int[2];
    int[] TempHappyFeedbackArray = new int[2];
    int[] TempIdleArray = new int[2];



    int TempKiggeNedOjne; // Som så ikke bliver brugt pt
    int TempHappyOjne;
    int TempBaffeledOjne;
    //Hvilket nummer af ojne i arrays der senest er blevet brugt

    ImageButton nextPersonalityButton;
    ImageButton PrevioisPersonalityButton;

    ImageButton HoldtoChangeCharecter;
    Long downcharecter, upcharecter;


    int IsThisFirstTime;

    boolean IsStopped;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        readFile(LogHashmap, LogInstance);

        //     mediaPlayer = new MediaPlayer();
        //   NyMp = new MediaPlayer();
        InitializeAllMusicArrays();
        TypeofFeedback = 0;
        CohreneceBetweenEyesAndVoice = 0;
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);  // fjerner notifikationsbar
        setContentView(R.layout.activity_benny_eyes);
        IsStopped = false;


        WindowManager.LayoutParams layout = getWindow().getAttributes();
        layout.screenBrightness = 1F; //https://developer.android.com/reference/android/view/WindowManager.LayoutParams#screenBrightness 1 er max value
        getWindow().setAttributes(layout);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        IdleModeIsActive = false;
        Notgonnastack =true;
        try {
            Language= 0;
            Intent intent = getIntent();
            Bundle bundle = intent.getExtras();
            Integer lol = bundle.getInt("Language");
            Language = lol;
            //  Toast.makeText(this, "Tal er" + Language, Toast.LENGTH_SHORT).show();
        }
        catch (NullPointerException e){
            //      Toast.makeText(this, "Nullpointer catched", Toast.LENGTH_SHORT).show();
        }

        SetLanguage(Language);
        tempforstringcompare = null;


        SuperRequestTimeTextView = findViewById(R.id.SuperRequestTView);         //Timer
        RequestNotActiveTextView = findViewById(R.id.RequestNotActiveTView);     //Timer
        FeedbackCDTextView = findViewById(R.id.FeedbackTView);                   //Timer
        DecibelTextView =(TextView)findViewById(R.id.NoiseTextView);             //Lyd
        OjneView = findViewById(R.id.BennyOjne);                                 //OjneView


        //PresentBenny();
        /*
        SuperRequestTimerStart();                                                //Timer
        RequestNotActiveTimerStart();                                            //Timer
        FeedbackTimerTimerStart();                                               //Timer

        StartRequestRound();
        */
        // Used to record Sound
        mSensor = new DetectNoise();
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        mWakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "NoiseAlert");

        HowManyTimesHaveIBeenCalledThatManyImSickOfBeingCalledAllTheTime = 0;
        temp =0;
        ReadyForFeedback = false;
        InitBennyOjneArray();
        IsThisFirstTime = 0;
        IdleChanceNumber= 0;
        ItsAPretendRound = false;
        Longpressed = false;
        HaveBeenInPresentMode = 0;
        findViewById(R.id.Blinkable).setOnTouchListener(new OnSwipeListener(this){

            @Override
            public void onLooongPress() {

                if (!Longpressed) {
                    //       Toast.makeText(BennyEyes.this, "LongPress", Toast.LENGTH_SHORT).show();
                    Longpressed = true;
                    super.onLooongPress();
                    PresentBenny();
                }
            }


            public void onClick() {


                if ( OjneView.isPlaying()){
                    //   Toast.makeText(BennyEyes.this, "avavav", Toast.LENGTH_SHORT).show();

                }
                else {
                    String PathToBennyEyes =   "android.resource://com.example.morte.bennyapp/" + R.raw.blinkerbeggeojne;
                    Uri uriLang =Uri.parse(PathToBennyEyes);
                    OjneView.setVideoURI(uriLang);
                    OjneView.start();


                }
            }





        });

        HoldtoReleasButton = findViewById(R.id.HoldToRelease);
        HoldtoReleasButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN :
                        //   Toast.makeText(ClownEyes.this, "Down", Toast.LENGTH_SHORT).show();
                        down=System.currentTimeMillis();
                        break;
                    case MotionEvent.ACTION_UP :
                        //     Toast.makeText(ClownEyes.this, "Up", Toast.LENGTH_SHORT).show();
                        up=System.currentTimeMillis();
                        if(up-down>5000){
                            //      Toast.makeText(ClownEyes.this, "More than 3", Toast.LENGTH_SHORT).show();
                            //   FeedbackCoolDownCountdowntimer.cancel();
                            if (RequestNotActiveCountdownTimer != null) {
                                RequestNotActiveCountdownTimer.cancel();

                            }
                            if (SuperRequestTidCountdownTimer != null) {
                                SuperRequestTidCountdownTimer.cancel();
                            }


                            StopPlayer();

                            finish();
                        }
                        return true;
                }
                return false;
            }


        });

        HoldtoChangeCharecter = findViewById(R.id.HoldTchangeCharecter);
        HoldtoChangeCharecter.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN :
                        //      Toast.makeText(ClownEyes.this, "Down", Toast.LENGTH_SHORT).show();
                        downcharecter=System.currentTimeMillis();
                        break;
                    case MotionEvent.ACTION_UP :
                        //   Toast.makeText(ClownEyes.this, "Up", Toast.LENGTH_SHORT).show();
                        upcharecter=System.currentTimeMillis();
                        if(upcharecter-downcharecter>5000){
                            if (!IsStopped) {

                                StopBenny();
                                //    Toast.makeText(ClownEyes.this, "Stopped", Toast.LENGTH_SHORT).show();
                                IsStopped= true;
                            }
                            else if (IsStopped){
                                TimeLeftInMillisSuperRequestTime = SuperRequestTid;
                                TimeLeftInMillisRequestNotActive= RequestNotActiveTime;
                                TimeLeftInMillisFeedbackCooldDown = FeedbackCooldown;



                                PresentBenny();
                                //      Toast.makeText(ClownEyes.this, "Restarted", Toast.LENGTH_SHORT).show();

                                IsThisFirstTime = 0;
                                IsStopped=false;
                            }

                        }
                        return true;
                }
                return false;
            }


        });

        PrevioisPersonalityButton = findViewById(R.id.PreviousPersonality);
        PrevioisPersonalityButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN :
                        //      Toast.makeText(ClownEyes.this, "Down", Toast.LENGTH_SHORT).show();
                        downcharecter=System.currentTimeMillis();
                        break;
                    case MotionEvent.ACTION_UP :
                        //   Toast.makeText(ClownEyes.this, "Up", Toast.LENGTH_SHORT).show();
                        upcharecter=System.currentTimeMillis();
                        if(upcharecter-downcharecter>5000){


                            Intent i = new Intent(BennyEyes.this, PirateEyes.class);
                            startActivityForResult(i,1);




                            //      StopBenny();
                            //    Toast.makeText(ClownEyes.this, "Stopped", Toast.LENGTH_SHORT).show();
                            //  IsStopped= true;


                        }
                        return true;
                }
                return false;
            }


        });

        nextPersonalityButton = findViewById(R.id.NextPersonality);
        nextPersonalityButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN :
                        //      Toast.makeText(ClownEyes.this, "Down", Toast.LENGTH_SHORT).show();
                        downcharecter=System.currentTimeMillis();
                        break;
                    case MotionEvent.ACTION_UP :
                        //   Toast.makeText(ClownEyes.this, "Up", Toast.LENGTH_SHORT).show();
                        upcharecter=System.currentTimeMillis();
                        if(upcharecter-downcharecter>5000){


                            Intent i = new Intent(BennyEyes.this, ClownEyes.class);
                            startActivityForResult(i,1);

                            //      StopBenny();
                            //    Toast.makeText(ClownEyes.this, "Stopped", Toast.LENGTH_SHORT).show();
                            //  IsStopped= true;


                        }
                        return true;
                }
                return false;
            }


        });

    }

    public void StopBenny(){



        if (RequestNotActiveCountdownTimer != null) {
            RequestNotActiveCountdownTimer.cancel();


        }
        if (SuperRequestTidCountdownTimer != null) {
            SuperRequestTidCountdownTimer.cancel();
        }
        if (FeedbackCoolDownCountdowntimer !=null) {
            FeedbackCoolDownCountdowntimer.cancel();
        }
        StopPlayer();
        stop();

    }

    public void PresentBenny(){

        findViewById(R.id.BennyOjne).setAlpha(1);  //Fjern baggrund
        String PathToBennyEyes =   "android.resource://com.example.morte.bennyapp/" + R.raw.idle;
        Uri uriLang =Uri.parse(PathToBennyEyes);
        OjneView.setVideoURI(uriLang);
        OjneView.start();
        PlayMusicFile(RobertIntroduction, 0);

        if (!mRunning) {
            mRunning = true;
            start();
        }


        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {

                SuperRequestTimerStart();                                                //Timer
                RequestNotActiveTimerStart();                                            //Timer
                FeedbackTimerTimerStart();                                               //Timer


                StartRequestRound();
                IsThisFirstTime++;
                StopPlayer();
            }
        });


    }

    public void InitializeAllMusicArrays (){


        RobertIdle = new ArrayList<>();
        RobertCollectRequest = new ArrayList<>();
        RobertKiggeNed = new ArrayList<>();
        RobertBuildRequest = new ArrayList<>();
        RobertPretendRequest = new ArrayList<>();
        RobertHappyFeedback = new ArrayList<>();
        RobertNeutralFeedback = new ArrayList<>();
        RobertBaffledFeedback = new ArrayList<>();
        RobertIntroduction = new ArrayList<>();

        getMusic();
        //    adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
        //    adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList2);

        //  PlayMusicFile(RobertBuildRequest);



    }

    public boolean NotLastOne(String lol){
        if (lol.equals(tempo)){
            return true;

        }
        else
            return false;
    }

    public void PlayMusicFile (ArrayList arrayList, int numberinArray) {

        String lol;
        Uri myUri = null;


        try {
            lol = (String) arrayList.get(numberinArray);                // Okay (String) er ikke mig men android studio der har lavet




            myUri = Uri.parse(lol); // initialize Uri here
            if (mediaPlayer == null) {

                mediaPlayer = new MediaPlayer();
           /* while (mediaPlayer.isPlaying()) {
                Toast.makeText(this, "Hvad er det her?", Toast.LENGTH_SHORT).show();
            }
*/


                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                try {
                    mediaPlayer.setDataSource(getApplicationContext(), myUri);
                } catch (IOException e) {
                    e.printStackTrace();
                    //       Toast.makeText(this, "Exception 1", Toast.LENGTH_SHORT).show();
                }
                try {
                    mediaPlayer.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                    //           Toast.makeText(this, "Exception 2", Toast.LENGTH_SHORT).show();

                }
                mediaPlayer.start();

                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        //    Toast.makeText(BennyEyes.this, "MP reset", Toast.LENGTH_SHORT).show();
                        // mediaPlayer.stop();  https://stackoverflow.com/questions/10453691/mediaplayer-throwing-illegalstateexception-when-calling-onstop
                        StopPlayer();

                        //    mediaPlayer.reset();
                    }
                });

            }
        }
        catch (IndexOutOfBoundsException e){
            //  Toast.makeText(this, "Outofindex", Toast.LENGTH_SHORT).show();
        }

    }

    private void StopPlayer(){
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    public void getMusic(){

        ContentResolver contentResolver = getContentResolver();
        Uri songUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor songCurser = contentResolver.query(songUri,null  ,null,null,null);
        if (songCurser != null && songCurser.moveToFirst()){
            int SongPathData = songCurser.getColumnIndex(MediaStore.Audio.Media.DATA);
            int songtitel = songCurser.getColumnIndex(MediaStore.Audio.Media.TITLE);

            do {
                String currentTitle = songCurser.getString(songtitel);
                String DataPath = songCurser.getString(SongPathData);

                // arrayList.add(currentTitle + "\n" + currentartist);
                char FirstCharInSongFile = currentTitle.charAt(0);
                char SecoundCharInSongFile = currentTitle.charAt(1);

                if (FirstCharInSongFile == 'R' && SecoundCharInSongFile == 'I' ){

                    RobertIdle.add(DataPath);

                }
                else if (FirstCharInSongFile == 'R' && SecoundCharInSongFile == 'C' ){

                    RobertCollectRequest.add(DataPath);
                    //  Toast.makeText(this, "Match for " + FirstCharInSongFile + "and " + SecoundCharInSongFile, Toast.LENGTH_SHORT).show();

                }
                else if (FirstCharInSongFile == 'R' && SecoundCharInSongFile == 'K' ){

                    RobertKiggeNed.add(DataPath);

                }

                else if (FirstCharInSongFile == 'R' && SecoundCharInSongFile == 'B' ){

                    RobertBuildRequest.add(DataPath);

                }

                else if (FirstCharInSongFile == 'R' && SecoundCharInSongFile == 'P' ){

                    RobertPretendRequest.add(DataPath);

                }
                if (FirstCharInSongFile == 'R' && SecoundCharInSongFile == 'Y' ){

                    RobertHappyFeedback.add(DataPath);

                }
                /*
                if (FirstCharInSongFile == 'R' && SecoundCharInSongFile == 'Y' ){

                    RobertNeutralFeedback.add(DataPath);

                }
                */
                if (FirstCharInSongFile == 'R' && SecoundCharInSongFile == 'X' ){

                    RobertBaffledFeedback.add(DataPath);

                }
                if (FirstCharInSongFile == 'R' && SecoundCharInSongFile == 'O' ){

                    RobertIntroduction.add(DataPath);

                }
                else {
                    //    Toast.makeText(this, "No match for " + FirstCharInSongFile + "and " + SecoundCharInSongFile, Toast.LENGTH_SHORT).show();


                }



            } while (songCurser.moveToNext());
        }
        //  Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show();
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
    protected void onPause() {

        if (RequestNotActiveCountdownTimer != null) {
            RequestNotActiveCountdownTimer.cancel();

        }
        if (SuperRequestTidCountdownTimer != null) {
            SuperRequestTidCountdownTimer.cancel();
        }

        if (IdleModeCountdowntimer != null){
            IdleModeCountdowntimer.cancel();
        }

        //  SuperRequestTidCountdownTimer.cancel();
        //mediaPlayer.stop();
        //mediaPlayer.release();
        StopPlayer();
        //NyMp.stop();
        //NyMp.release();
        //   Toast.makeText(this, "", Toast.LENGTH_SHORT).cancel();
        // Log.i("Noise", "==== onStop ===");
        //Stop noise monitoring
        stop();
        finish();



        super.onPause();
    }


    @Override
    public void onStop() {


        //   FeedbackCoolDownCountdowntimer.cancel();
        if (RequestNotActiveCountdownTimer != null) {
            RequestNotActiveCountdownTimer.cancel();

        }
        if (SuperRequestTidCountdownTimer != null) {
            SuperRequestTidCountdownTimer.cancel();
        }

        if (IdleModeCountdowntimer != null){
            IdleModeCountdowntimer.cancel();
        }

        //  SuperRequestTidCountdownTimer.cancel();
        //mediaPlayer.stop();
        //mediaPlayer.release();
        StopPlayer();
        //NyMp.stop();
        //NyMp.release();
        //   Toast.makeText(this, "", Toast.LENGTH_SHORT).cancel();
        // Log.i("Noise", "==== onStop ===");
        //Stop noise monitoring
        stop();
        super.onStop();

    }

    public void FeedbackWhenMicrohoneIsTriggered() {
        findViewById(R.id.BennyOjne).setAlpha(1);  //Fjern baggrund
        if (ReadyForFeedback == true){
            WhatTypeOfEyes();
            ReadyForFeedback = false;
            FeedbackCoolDownCountdowntimer.start();
            RequestNotActiveCountdownTimer.cancel();
            RequestNotActiveCountdownTimer.start();
            PlayFeedBack();
        }
        //  if (ReadyForFeedback == false)
        //          Toast.makeText(this, "I else", Toast.LENGTH_SHORT).show();
        // FeedbackCoolDownCountdowntimer.start();
    }

    public void WhatTypeOfEyes() {

        Random r = new Random();
        int n = r.nextInt(2);   // Så jeg går udfra den ikke tager maks med
        int k = r.nextInt(2);

        if (TypeofFeedback == 1) {


            Log.d("Nem opgave, Alle øjne", "Nem opgave alle ojne: ");
            switch (n) {
                case 0:
                    PlayBennyHappyOjne();
                    CohreneceBetweenEyesAndVoice = 1;
                    break;
                case 1:
                    PlayBennyNotPleasedOjne();

                    CohreneceBetweenEyesAndVoice = 2;
                    break;
                case 2:
                    //     Toast.makeText(this, "Søg på WhatTypeOFEyes og reprogram" + "'", Toast.LENGTH_SHORT).show();
                    break;

            }
        }
        if (TypeofFeedback == 2) {
            Log.d("Svær opgave, 2 slags øj", "Svær opgave 2 slags øjne: ");
            switch (k) {
                case 0:
                    PlayBennyHappyOjne();
                    CohreneceBetweenEyesAndVoice = 1;
                    break;
                case 1:
                    PlayBennyHappyOjne();
                    CohreneceBetweenEyesAndVoice = 1;
                    break;
                case 2:
                    //      Toast.makeText(this, "Søg på WhatTypeOFEyes og reprogram" + "'", Toast.LENGTH_SHORT).show();
                    break;


            }

        }
    }


    public int TempFunctionNyNyNyNY (ArrayList array, int[] TempArray ) {

        int whilerandom = TempArray[0];
//Morten lav et array det er meeget nemmere istedet for at jonglere rundt med temps.
        Random r = new Random();
        int Randomtal = r.nextInt(array.size());
        //      Toast.makeText(this, "" + array.size(), Toast.LENGTH_SHORT).show();
        //   Log.d("Tempfunction", "Ny temp er  " + TempArray[0] + "  gammel temp er   " + TempArray[1] + " Randomtal er" + Randomtal);
        // Array 4 stort, random tal = 2 Temp =3, other temp 2;
        if (TempArray[0] == Randomtal || TempArray[1] == Randomtal) {
            Log.d("Tempfunction", " De var sku ens");

            while (whilerandom != TempArray[0] && whilerandom != TempArray[1]) {
                Random LocalRandom = new Random();
                whilerandom = LocalRandom.nextInt(array.size());

                Randomtal = whilerandom;

                if (Randomtal != TempArray[0] && Randomtal != TempArray[1]) {

                    TempArray[1] = TempArray[0];
                    TempArray[0] = Randomtal;
                    Log.d("De har været ens men nu", "Ny temp er  " + TempArray[0] + "  gammel temp er   " + TempArray[1] + " Randomtal er" + Randomtal);

                    return Randomtal;
                }

            }

        }
        TempArray[1] = TempArray[0];
        TempArray[0] = Randomtal;
        return Randomtal;
    }

    public int TempFunctionNyNyNyyyyyyyyyyy (ArrayList array, int[] TempArray ) {


//Morten lav et array det er meeget nemmere istedet for at jonglere rundt med temps.
        Random r = new Random();
        int Randomtal = r.nextInt(array.size());
        //      Toast.makeText(this, "" + array.size(), Toast.LENGTH_SHORT).show();
        Log.d("Tempfunction", "Ny temp er  " + TempArray[0]+ "  gammel temp er   " + TempArray[1] + " Randomtal er"  + Randomtal);
        // Array 4 stort, random tal = 2 Temp =3, other temp 2;
        if (TempArray[0] == Randomtal || TempArray[1] == Randomtal) {

            for (int i = 0; i <= array.size() - 1; i++) {
                Randomtal = i;

                if (Randomtal != TempArray[0] && Randomtal != TempArray[1]) {

                    TempArray[1] = TempArray[0];
                    TempArray[0] = Randomtal;
                    return Randomtal;
                }

            }

        }
        TempArray[1] = TempArray[0];
        TempArray[0] = Randomtal;
        return Randomtal;
    }

    public int TempFunctionNy (ArrayList array, int WhatTemp, int OtherTemp ) {
        Log.d("Tempfunction", "Ny temp er  " + WhatTemp+ "  gammel temp er   " + OtherTemp);

//Morten lav et array det er meeget nemmere istedet for at jonglere rundt med temps.
        Random r = new Random();
        int Randomtal = r.nextInt(array.size());
        //      Toast.makeText(this, "" + array.size(), Toast.LENGTH_SHORT).show();

        // Array 4 stort, random tal = 2 Temp =3, other temp 2;
        if (WhatTemp == Randomtal || OtherTemp == Randomtal) {

            for (int i = 0; i <= array.size() - 1; i++) {
                Randomtal = i;

                if (Randomtal != WhatTemp && Randomtal != OtherTemp) {

                    return Randomtal;
                }

            }

        }
        return Randomtal;
    }

    public int TempFunction(ArrayList array, int WhatTemp ){


        Random r = new Random();
        int Randomtal = r.nextInt(array.size());
        //      Toast.makeText(this, "" + array.size(), Toast.LENGTH_SHORT).show();
        if (WhatTemp == Randomtal){
            if (Randomtal == array.size()- 1 || Randomtal == array.size()){
                Log.d("Ens Tal", "Sætter tal til 0");

                Randomtal = 0;
            }
            else  if (Randomtal != array.size()  || Randomtal != array.size()-1){
                //  Toast.makeText(this, "" + Randomtal, Toast.LENGTH_SHORT).show();   //Hvor printer den 1.
                Log.d("Ens Tal", "Sætter tal ++");

                Randomtal++;
            }
        }

        return Randomtal;
    }


    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void PreventRepetetion(ArrayList array) {

        String NameOfPath;

        if (array.equals(RobertBuildRequest)) {
            PlayMusicFile(RobertBuildRequest, TempBuildRequestArray[0]);
            TempFunctionNyNyNyyyyyyyyyyy(RobertBuildRequest, TempBuildRequestArray);

            NameOfPath = RobertBuildRequest.get(TempBuildRequestArray[0]);
            String NameOfFile = NameOfPath.substring(NameOfPath.lastIndexOf("/")+1);
            save(NameOfFile);
            //Toast.makeText(this, NameOfFile, Toast.LENGTH_SHORT).show();
        }

        if (array.equals(RobertPretendRequest)) {
            //      Toast.makeText(this, "Pretend", Toast.LENGTH_SHORT).show();
            //     Temp2PretendRequestOfOld = TempPretendRequest;
            //   TempPretendRequest = TempFunctionNy(RobertPretendRequest, TempPretendRequest, Temp2PretendRequestOfOld);
            TempFunctionNyNyNyyyyyyyyyyy(RobertPretendRequest,TempPretendRequestArray);
            PlayMusicFile(RobertPretendRequest, TempPretendRequest);

            NameOfPath = RobertPretendRequest.get(TempPretendRequestArray[0]);
            String NameOfFile = NameOfPath.substring(NameOfPath.lastIndexOf("/")+1);
            save(NameOfFile);
            //Toast.makeText(this, NameOfFile, Toast.LENGTH_SHORT).show();

        }

        if (array.equals(RobertCollectRequest)) {
            //    Toast.makeText(this, "Collect", Toast.LENGTH_SHORT).show();
            //    Temp2CollectRequestOfOld = TempCollectRequest;
            //  TempCollectRequest = TempFunctionNy(RobertCollectRequest, TempCollectRequest, Temp2CollectRequestOfOld);
            TempFunctionNyNyNyyyyyyyyyyy(RobertCollectRequest, TempCollectRequestArray);
            PlayMusicFile(RobertCollectRequest, TempCollectRequestArray[0]);

            NameOfPath = RobertCollectRequest.get(TempCollectRequestArray[0]);
            String NameOfFile = NameOfPath.substring(NameOfPath.lastIndexOf("/")+1);
            save(NameOfFile);
            //Toast.makeText(this, NameOfFile, Toast.LENGTH_SHORT).show();
        }

        if (array.equals(RobertKiggeNed)) {

            //     Toast.makeText(this, "kiggened", Toast.LENGTH_SHORT).show();
            //  Temp2KiggeNedOfOld = TempKiggeNed;
            // TempKiggeNed = TempFunctionNy(RobertKiggeNed, TempKiggeNed, Temp2KiggeNedOfOld);
            TempFunctionNyNyNyyyyyyyyyyy(RobertKiggeNed, TempKiggeNedArray);
            PlayMusicFile(RobertKiggeNed, TempKiggeNedArray[0]);

            NameOfPath = RobertKiggeNed.get(TempKiggeNedArray[0]);
            String NameOfFile = NameOfPath.substring(NameOfPath.lastIndexOf("/")+1);
            save(NameOfFile);
            //Toast.makeText(this, NameOfFile, Toast.LENGTH_SHORT).show();

        }
        if (array.equals(RobertBaffledFeedback)) {

            //   Toast.makeText(this, "Baffeeld", Toast.LENGTH_SHORT).show();
            //  Temp2BaffeldFeedbackOfOld = TempBaffeldFeedback;
            //TempBaffeldFeedback = TempFunctionNy(RobertBaffledFeedback, TempBaffeldFeedback, Temp2BaffeldFeedbackOfOld);
            TempFunctionNyNyNyyyyyyyyyyy(RobertBaffledFeedback, TempBaffeldFeedbackArray);
            PlayMusicFile(RobertBaffledFeedback, TempBaffeldFeedbackArray[0]);

            NameOfPath = RobertBaffledFeedback.get(TempBaffeldFeedbackArray[0]);
            String NameOfFile = NameOfPath.substring(NameOfPath.lastIndexOf("/")+1);
            save(NameOfFile);
            //Toast.makeText(this, NameOfFile, Toast.LENGTH_SHORT).show();

        }

        if (array.equals(RobertHappyFeedback)) {

            PlayMusicFile(RobertHappyFeedback, TempHappyFeedbackArray[0]);
            TempFunctionNyNyNyyyyyyyyyyy(RobertHappyFeedback, TempHappyFeedbackArray);

            NameOfPath = RobertHappyFeedback.get(TempHappyFeedbackArray[0]);
            String NameOfFile = NameOfPath.substring(NameOfPath.lastIndexOf("/")+1);
            save(NameOfFile);
            //Toast.makeText(this, NameOfFile, Toast.LENGTH_SHORT).show();
        }

        if (array.equals(RobertIdle)) {

            TempFunctionNyNyNyyyyyyyyyyy(RobertIdle, TempIdleArray);
            PlayMusicFile(RobertIdle, TempIdleArray[0]);

            NameOfPath = RobertIdle.get(TempIdleArray[0]);
            String NameOfFile = NameOfPath.substring(NameOfPath.lastIndexOf("/")+1);
            save(NameOfFile);
            //Toast.makeText(this, NameOfFile, Toast.LENGTH_SHORT).show();
        }
    }

    public void LevelOfRequestDifficulty() {
        Random r = new Random();
        int n = r.nextInt(180);

        if (n <= HardTaskProperbility) //HardTask;
        {
            Log.d("Ny Request", "Det er en svær request ");
            try {

                PreventRepetetion(RobertBuildRequest);
                //  PlayMusicFile(RobertBuildRequest);
            }
            catch (Surface.OutOfResourcesException lol) {
                //       Toast.makeText(this, "Outofressources 1", Toast.LENGTH_SHORT).show();

            }
            catch (OutOfMemoryError lol){
                //     Toast.makeText(this, "Outofmemory 1", Toast.LENGTH_SHORT).show();
            }

            catch (IndexOutOfBoundsException lol){
                //   Toast.makeText(this, "Outof index 1", Toast.LENGTH_SHORT).show();
            }
            catch (IllegalStateException lol){
                // Toast.makeText(this, "illegal state 1", Toast.LENGTH_SHORT).show();
            }


            TypeofFeedback= 2;

        }

        if (n > EasyTaskProperbility && n <=100) //EzTask
        {
            Log.d("Ny request", "Det er en nem opgave ");
            try {
                PreventRepetetion(RobertCollectRequest);
                //    PlayMusicFile(RobertCollectRequest);
            }
            catch (Surface.OutOfResourcesException lol) {
                //      Toast.makeText(this, "Outofressources 2", Toast.LENGTH_SHORT).show();

            }
            catch (OutOfMemoryError lol){
                //    Toast.makeText(this, "Outofmemory 2", Toast.LENGTH_SHORT).show();
            }

            catch (IndexOutOfBoundsException lol){
                //  Toast.makeText(this, "Outof index 2", Toast.LENGTH_SHORT).show();
            }

            catch (IllegalStateException lol){
                //    Toast.makeText(this, "illegal state 2", Toast.LENGTH_SHORT).show();
            }

            TypeofFeedback = 1;

        }
        if (n > 100 && n < 140) // PretendRequest;
        {

            Log.d("Ny Request", "Det er en pretend request ");
            try {

                PreventRepetetion(RobertPretendRequest);
                //     PlayMusicFile(RobertPretendRequest);
                ItsAPretendRound = true;
            }
            catch (Surface.OutOfResourcesException lol) {
                //  Toast.makeText(this, "Outofressources 1", Toast.LENGTH_SHORT).show();

            }
            catch (OutOfMemoryError lol){
                //Toast.makeText(this, "Outofmemory 1", Toast.LENGTH_SHORT).show();
            }

            catch (IndexOutOfBoundsException lol){
                //       Toast.makeText(this, "Outof index 1", Toast.LENGTH_SHORT).show();
            }
            catch (IllegalStateException lol){
                //     Toast.makeText(this, "illegal state 1", Toast.LENGTH_SHORT).show();
            }


            TypeofFeedback= 2;

        }

        if (n >= 140) // KiggeNedRequest;
        {

            Log.d("Ny Request", "Det er en kigge ned request ");
            try {
                PreventRepetetion(RobertKiggeNed);
                PlayBennyNoFeelingOjne();
                //          PlayMusicFile(RobertKiggeNed);

            }
            catch (Surface.OutOfResourcesException lol) {
                //  Toast.makeText(this, "Outofressources 1", Toast.LENGTH_SHORT).show();

            }
            catch (OutOfMemoryError lol){
                //Toast.makeText(this, "Outofmemory 1", Toast.LENGTH_SHORT).show();
            }

            catch (IndexOutOfBoundsException lol){
                //       Toast.makeText(this, "Outof index 1", Toast.LENGTH_SHORT).show();
            }
            catch (IllegalStateException lol){
                //     Toast.makeText(this, "illegal state 1", Toast.LENGTH_SHORT).show();
            }


            TypeofFeedback= 1; //al slags feedback

        }

    }

    public void PlayFeedBack (){
        // modtag sværhedsgraden af request iform af int
        Random r = new Random();
        int n = r.nextInt(63);

        if (TypeofFeedback == 1){                        //Nem Opgave

            Log.d("Hvilken Feedback", "Alle slags feedback til nem opgave ");

            if (CohreneceBetweenEyesAndVoice ==1 ) //HappyFeedBack
            {


                try{
                    // PlayMusicFile(RobertHappyFeedback);
                    PreventRepetetion(RobertHappyFeedback);

                }
                catch (Surface.OutOfResourcesException lol) {
                    //        Toast.makeText(this, "Outofressources 3", Toast.LENGTH_SHORT).show();

                }
                catch (OutOfMemoryError lol){
                    //      Toast.makeText(this, "Outofmemory 3", Toast.LENGTH_SHORT).show();
                }

                catch (IndexOutOfBoundsException lol){
                    //    Toast.makeText(this, "Outof index 3", Toast.LENGTH_SHORT).show();
                }

                catch (IllegalStateException lol){
                    //  Toast.makeText(this, "illegal state 3", Toast.LENGTH_SHORT).show();
                }

            }

            if (CohreneceBetweenEyesAndVoice == 2) //NotSoHapppyFeedback
            {

                try{
                    //     PlayMusicFile(RobertBaffledFeedback);
                    PreventRepetetion(RobertBaffledFeedback);

                }
                catch (Surface.OutOfResourcesException lol) {
                    //        Toast.makeText(this, "Outofressources 4", Toast.LENGTH_SHORT).show();

                }
                catch (OutOfMemoryError lol){
                    //           Toast.makeText(this, "Outofmemory 4", Toast.LENGTH_SHORT).show();
                }

                catch (IndexOutOfBoundsException lol){
                    //         Toast.makeText(this, "Outof index 4", Toast.LENGTH_SHORT).show();
                }

                catch (IllegalStateException lol){
                    //        Toast.makeText(this, "illegal state 4", Toast.LENGTH_SHORT).show();
                }

            }

        }
        if (TypeofFeedback==2){                          //Svær opgave

            Log.d("Hvilken slags feedback", "Det er en svær opgav så kun 2 slags feedback");

            if (CohreneceBetweenEyesAndVoice == 1) //HappyFeedBack
            {

                try{
//                    PlayMusicFile(RobertHappyFeedback);
                    PreventRepetetion(RobertHappyFeedback);
                }
                catch (Surface.OutOfResourcesException lol) {
                    //       Toast.makeText(this, "Outofressources 6", Toast.LENGTH_SHORT).show();

                }
                catch (OutOfMemoryError lol){
                    //     Toast.makeText(this, "Outofmemory 6", Toast.LENGTH_SHORT).show();
                }

                catch (IndexOutOfBoundsException lol){
                    //   Toast.makeText(this, "Outof index 6", Toast.LENGTH_SHORT).show();
                }
                catch (IllegalStateException lol){
                    // Toast.makeText(this, "illegal state 6", Toast.LENGTH_SHORT).show();
                }

            }

        }
    }

    public void InitBennyOjneArray(){

        BennyHappyOjneArray = new String[]{


                //Insert Happy Eyes
                "android.resource://com.example.morte.bennyapp/" + R.raw.briller,
                "android.resource://com.example.morte.bennyapp/" + R.raw.cool,
                "android.resource://com.example.morte.bennyapp/" + R.raw.crazy,
                "android.resource://com.example.morte.bennyapp/" + R.raw.glad,
                "android.resource://com.example.morte.bennyapp/" + R.raw.hjerteojne,
                "android.resource://com.example.morte.bennyapp/" + R.raw.idle,
                "android.resource://com.example.morte.bennyapp/" + R.raw.kiggerned,
                "android.resource://com.example.morte.bennyapp/" + R.raw.megetglad,
                "android.resource://com.example.morte.bennyapp/" + R.raw.tilfreds,
                "android.resource://com.example.morte.bennyapp/" + R.raw.overrasket,
                "android.resource://com.example.morte.bennyapp/" + R.raw.blinker,




        };

        BennyNoFeelingOjneArray = new String[]{

                //Insert No feeling eyes
                "android.resource://com.example.morte.bennyapp/" + R.raw.blinkerbeggeojne,
                "android.resource://com.example.morte.bennyapp/" + R.raw.idle,
                "android.resource://com.example.morte.bennyapp/" + R.raw.kiggerned,


        };

        BennyNotPleasedOjneArray = new String[]{

                //Insert Not pleased eyes
                "android.resource://com.example.morte.bennyapp/" + R.raw.skeptisk,
                "android.resource://com.example.morte.bennyapp/" + R.raw.sur,
                "android.resource://com.example.morte.bennyapp/" + R.raw.syg,
                "android.resource://com.example.morte.bennyapp/" + R.raw.forvirret,
                "android.resource://com.example.morte.bennyapp/" + R.raw.overrasket,
                "android.resource://com.example.morte.bennyapp/" + R.raw.lider,
                "android.resource://com.example.morte.bennyapp/" + R.raw.crazy,



        };
    }      //Arrays til alle Benny's øjne


    public int Tempfunction4Eyes(String[] string, int WhatTemp ){


        Random r = new Random();
        int Randomtal = r.nextInt(string.length);

        Log.d("Temp for Ojne", "Array navn er" + string + "og maks længden er " + string.length + "vores tal er " + Randomtal + "og det tal vi fik i som paramter er " + WhatTemp);

        //      Toast.makeText(this, "" + array.size(), Toast.LENGTH_SHORT).show();
        if (WhatTemp == Randomtal){
            if (Randomtal == string.length- 1 || Randomtal == string.length){

                Randomtal = 0;
            }
            else  if (Randomtal != string.length  || Randomtal != string.length-1){
                //  Toast.makeText(this, "" + Randomtal, Toast.LENGTH_SHORT).show();   //Hvor printer den 1.
                Log.d("Ens Tal", "Sætter tal ++");

                Randomtal++;
            }
        }

        return Randomtal;
    }

    public void PlayBennyHappyOjne(){



       /* Random r = new Random();
        int ArrayLength = BennyHappyOjneArray.length;
        int nyrandom = r.nextInt(ArrayLength - 0)+0;    //Der er noget galt her. Den går udover det tilltdte   har slettet et +1 og nogle parenteser
        // Toast.makeText(this, "Nummmeret er " + nyrandom, Toast.LENGTH_SHORT).show();
        String PathToBennyEyes = BennyHappyOjneArray[nyrandom];
        */
        TempHappyOjne = Tempfunction4Eyes(BennyHappyOjneArray, TempHappyOjne);
        String PathToBennyEyes = BennyHappyOjneArray[TempHappyOjne];
        Uri uriLang =Uri.parse(PathToBennyEyes);
        OjneView.setVideoURI(uriLang);
        OjneView.start();

    }

    public void PlayBennyNoFeelingOjne(){ //aka kigge ned

        Random r = new Random();
        int ArrayLength = BennyHappyOjneArray.length;
        int nyrandom = r.nextInt(ArrayLength - 0)+0;


        String PathToBennyEyes = BennyNoFeelingOjneArray[2];             //Lig mærke til det kun er en slags øjne her!!
        Uri uriLang =Uri.parse(PathToBennyEyes);
        OjneView.setVideoURI(uriLang);
        OjneView.start();

    }

    public void PlayBennyNotPleasedOjne(){
      /*  Random r = new Random();
        int ArrayLength = BennyNotPleasedOjneArray.length;
        int nyrandom = r.nextInt(ArrayLength - 0)+0;    //Der er noget galt her. Den går udover det tilltdte   har slettet et +1 og nogle parenteser
        String PathToBennyEyes = BennyNotPleasedOjneArray[nyrandom];
        */
        if (IsThisFirstTime != 0){
            TempBaffeledOjne = Tempfunction4Eyes(BennyNotPleasedOjneArray,TempBaffeledOjne);
            String PathToBennyEyes = BennyNotPleasedOjneArray[TempBaffeledOjne];

            Uri uriLang =Uri.parse(PathToBennyEyes);
            OjneView.setVideoURI(uriLang);
            OjneView.start();

        }
        else{

        }


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

        //   Log.d("SONUND", String.valueOf(signalEMA));
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

    public void PlayIdleLine(){


        PreventRepetetion(RobertIdle);
        // PlayMusicFile(RobertIdle);

    }

    private void IdleModCountodwntimerstart(){
        IdleModeCountdowntimer = new CountDownTimer(IdleModeTime, 1000) {

            @Override
            public void onTick(long millisuntillfinish3) {



            }

            @Override
            public void onFinish() {

                PlayIdleLine();

                IdleModeCountdowntimer.cancel();

                IdleModCountodwntimerstart();

            }
        }.start();


    }

    public void IdleLines(){


        //Okay så den er tikker hvert sekund, jeg ved ikke om det er for meget for den stakkels mobil?

        int DerMåMaksGåSåLangTidFørDerKommerEnIdleLine = 15;
        int MinimumFørDerKOmmerEnIdleLine = 8;


        Random r = new Random();
        int LocalIdleChanceNumber= r.nextInt((DerMåMaksGåSåLangTidFørDerKommerEnIdleLine-MinimumFørDerKOmmerEnIdleLine)+1)+MinimumFørDerKOmmerEnIdleLine;   //  ((max - min) + 1) + min;


        IdleChanceNumber++; //Tæller den op hvert sekund
        if (IdleChanceNumber > LocalIdleChanceNumber){


            try{

                PreventRepetetion(RobertIdle);
//                PlayMusicFile(RobertIdle);

            }
            catch (Surface.OutOfResourcesException lol) {
                //         Toast.makeText(this, "Outofressources IDLE", Toast.LENGTH_SHORT).show();

            }
            catch (OutOfMemoryError lol){
                //       Toast.makeText(this, "Outofmemory IDLE", Toast.LENGTH_SHORT).show();
            }

            catch (IndexOutOfBoundsException lol){
                //   Toast.makeText(this, "Outof index IDLE", Toast.LENGTH_SHORT).show();
            }
            catch (IllegalStateException lol){
                //Toast.makeText(this, "illegal state IDLE", Toast.LENGTH_SHORT).show();
            }


            IdleChanceNumber = 0;
        }


    }



    private void SuperRequestTimerStart(){
        SuperRequestTidCountdownTimer = new CountDownTimer(TimeLeftInMillisSuperRequestTime, 1000) {


            @Override
            public void onTick(long millisuntillfinish) {
                TimeLeftInMillisSuperRequestTime = millisuntillfinish;
                UpdateSuperRequestTextview();
                //if (TimeLeftInMillisSuperRequestTime < 3000)
/*
if (TimeLeftInMillisSuperRequestTime > 21000 && TimeLeftInMillisSuperRequestTime < 22500){

    if (ItsAPretendRound){
        Toast.makeText(BennyEyes.this, "Det er en pretend runde og vi runder tidligt af", Toast.LENGTH_SHORT).show();
        StartRequestRound();
        ItsAPretendRound = false;
    }
}
*/

                //   IdleLines();
                // HER KUNNE MAN HAVE EN RANDOM FUNKTION DER SIGER NOGET I STIL MED 1/50 GANGE DEN TIKKER SÅ SIG EN IDLE REPLIK?
                // ELLER NÅR DEN HAR TIKKET X ANTAL GANGE FYR EN IDLE REPLIK
            }

            @Override
            public void onFinish() {
                if (mediaPlayer == null){

                    StartRequestRound();
                }
                else {
                    while (mediaPlayer.isPlaying() || OjneView.isPlaying()){    //Sørger for at den ikke giver ny request mens der er feedback


                    }

                    StartRequestRound();

                }

//            StartRequestRound();
                AmounfOfIdleRounds =0 ;
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

                if (millisuntillfinish2 < 12000 && millisuntillfinish2 > 11000 && TimeLeftInMillisSuperRequestTime > 4000) {
                    if (ItsAPretendRound) {
                        //   Toast.makeText(BennyEyes.this, "Det er en pretend runde og vi runder tidligt af", Toast.LENGTH_SHORT).show();
                        StartRequestRound();
                        ItsAPretendRound = false;
                    }
                    if (!ItsAPretendRound) {
                        Log.d("Over 9000", "Vi giver en idle line" + millisuntillfinish2);
                        PreventRepetetion(RobertIdle);
                    }
                }
/*            if (millisuntillfinish2 < 6000 && millisuntillfinish2 > 5000){
                Log.d("Over 9000", "Vi giver en idle line" + millisuntillfinish2);
                IdleLinesInReqquestNotActive();
            }

*/
            }
            @Override
            public void onFinish() {
                RequestNotActiveeIsRunning = false;
                StartRequestRound();

                AmounfOfIdleRounds++;

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
                AmounfOfIdleRounds =0;

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

    public void StartRequestRound(){
        //   Toast.makeText(this, "vi er i ny request", Toast.LENGTH_SHORT).show();

        if (AmounfOfIdleRounds >= 3){
            IdleModeIsActive = true;
            IdleModCountodwntimerstart();
        }

        else if (AmounfOfIdleRounds < 3) {


/*            while (mediaPlayer.isPlaying()){

            }
            */
            //   Toast.makeText(this, "New Round", Toast.LENGTH_SHORT).show();
            FeedbackCoolDownCountdowntimer.cancel();
            RequestNotActiveCountdownTimer.cancel();
            SuperRequestTidCountdownTimer.cancel();
            LevelOfRequestDifficulty();
            FeedbackCoolDownCountdowntimer.start();
            RequestNotActiveCountdownTimer.start();
            SuperRequestTidCountdownTimer.start();
        }
    }

    public void SetLanguage(Integer WhatLanguage){
        TextView LagnuageText = findViewById(R.id.LanguageTextView);
        if (WhatLanguage == 1){
            LagnuageText.setText("American");
        }
        if (WhatLanguage == 2){
            LagnuageText.setText("Dansk");
        }

    }

    public void OhNoMyEyes(View view) {

        if ( OjneView.isPlaying()){
            //   Toast.makeText(this, "avavav", Toast.LENGTH_SHORT).show();

        }
        else {
            String PathToBennyEyes =   "android.resource://com.example.morte.bennyapp/" + R.raw.blinkerbeggeojne;
            Uri uriLang =Uri.parse(PathToBennyEyes);
            OjneView.setVideoURI(uriLang);
            OjneView.start();


        }
    }

    public void HowManyTimesHvaeEyesBeenTriggered (double MicTriggerValue){
        HowManyTimesHaveIBeenCalledThatManyImSickOfBeingCalledAllTheTime++;

        temp = temp + MicTriggerValue;
        double Average = temp/HowManyTimesHaveIBeenCalledThatManyImSickOfBeingCalledAllTheTime;

        String RunningAvergeFormatted = String.format("TriggerValueEr %f, og funktionen er kørt %d gange.\n Gennemsnittet er %f"
                , MicTriggerValue, HowManyTimesHaveIBeenCalledThatManyImSickOfBeingCalledAllTheTime, Average);
//        Textview wauwetflottextview = findViewById(R.id.NytSejTextView);   Det er bare lige en tentativ.
//        wauwetflottextview.setText(RunningAvergeFormatted);

    }


    // LOGGING SECTION – Ik smid methods ind i eller under!

    public String subFolder = "/LogData";
    public String name = "LogFile_";
    public String currentDate = (String) whichDate();
    public String csv = ".csv";
    public String filename = name + currentDate + csv;
    public String instances = "_instances";
    public String filenameinstance = currentDate + instances + csv;
    private static final String TAG = "MEDIA";
    String eol = System.getProperty("line.separator");

    public HashMap<String, Integer> LogHashmap = new HashMap<String, Integer>();
    public HashMap<String, String> LogInstance = new HashMap<>();

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void save(String key) {

        if (LogHashmap.containsKey(key)) {
            LogHashmap.put(key, LogHashmap.get(key)+1);

            if (!key.equals("Bricks Detected")){
                LogInstance.put(timeStamp(), key);
            }
            writeToFile(LogHashmap, LogInstance);
        }

        else if (!LogHashmap.containsKey(key)) {
            LogHashmap.put(key, 1);

            if (!key.equals("Bricks Detected")) {
                LogInstance.put(timeStamp(), key);
            }
            writeToFile(LogHashmap, LogInstance);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void writeToFile(HashMap<String, Integer> insertHashmap, HashMap<String, String> instanceHashmap) {
        //write to file

        File cacheDir = null;
        File appDirectory = null;

        if (android.os.Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            cacheDir = getApplicationContext().getExternalCacheDir();
            appDirectory = new File(cacheDir + subFolder);
        }

        else {
            cacheDir = getApplicationContext().getCacheDir();
            String BaseFolder = cacheDir.getAbsolutePath();
            appDirectory = new File(BaseFolder + subFolder);
        }

        if (appDirectory != null && !appDirectory.exists()) {
            appDirectory.mkdirs();
        }

        File file = new File(appDirectory, filename);
        File fileInstance = new File(appDirectory, filenameinstance);

        FileOutputStream fos = null;
        ObjectOutputStream out = null;

        try (Writer writer = new FileWriter(file)) {
            for (Map.Entry<String, Integer> entry : insertHashmap.entrySet()) {
                writer.append(entry.getKey()).append(',').append(String.valueOf(entry.getValue())).append(eol);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        try (Writer writer = new FileWriter(fileInstance)){
            for (Map.Entry<String, String> entry : instanceHashmap.entrySet()) {
                writer.append(entry.getKey()).append(',').append(entry.getValue()).append(eol);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        /**try {
         fos = new FileOutputStream(file);
         out = new ObjectOutputStream(fos);
         out.writeObject(LogHashmap);
         System.out.println(LogHashmap);
         } catch (IOException ioe) {
         ioe.printStackTrace();
         } catch (Exception e) {
         e.printStackTrace();
         } finally {
         try {
         if (fos != null) {
         fos.flush();
         fos.close();
         if (out != null) {
         out.flush();
         out.close();
         }
         }
         } catch (Exception e) {

         }
         }**/
    }

    public void readFile(HashMap<String, Integer> insertHashmap, HashMap<String, String> instanceHashmap) {

        File cacheDir = null;
        File appDirectory = null;

        if (android.os.Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            cacheDir = getApplicationContext().getExternalCacheDir();
            appDirectory = new File (cacheDir + subFolder);
        } else {
            cacheDir = getApplicationContext().getCacheDir();
            String BaseFolder = cacheDir.getAbsolutePath();
            appDirectory = new File (BaseFolder + subFolder);
        }

        if (appDirectory != null && !appDirectory.exists()) {
            return;
        }

        File file = new File (appDirectory, filename);

        FileInputStream fis = null;
        ObjectInputStream in = null;



        try {
            BufferedReader br = new BufferedReader(new FileReader(appDirectory + "/" + filename));
            String line = "";
            StringTokenizer st = null;


            while ((line = br.readLine()) != null) {

                st = new StringTokenizer(line, ",");
                while (st.hasMoreTokens()) {

                    String key = st.nextToken();
                    String value = st.nextToken();

                    insertHashmap.put(key, Integer.valueOf(value));
                }
            }
        }

        /**try {
         fis = new FileInputStream(file);
         in = new ObjectInputStream(fis);
         LogHashmap  = (HashMap<String, Integer>) in.readObject();
         Toast.makeText(this, "Count of hashmaps:: " + LogHashmap.size() + " " + LogHashmap, Toast.LENGTH_SHORT).show();


         }**/ catch (FileNotFoundException e) {
            e.printStackTrace();

        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        }/** catch (ClassNotFoundException e) {
         e.printStackTrace();
         }**/ catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            BufferedReader br = new BufferedReader(new FileReader(appDirectory + "/" + filenameinstance));
            String line = "";
            StringTokenizer st = null;


            while ((line = br.readLine()) != null) {

                st = new StringTokenizer(line, ",");
                while (st.hasMoreTokens()) {

                    String key = st.nextToken();
                    String value = st.nextToken();

                    instanceHashmap.put(key, value);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();

        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }



        //Toast.makeText(this, ""+ appDirectory, Toast.LENGTH_SHORT).show();
        //Toast.makeText(this, "Complete hashmap: " + LogHashmap, Toast.LENGTH_SHORT).show();
        //Toast.makeText(this, "currentDate: " + currentDate, Toast.LENGTH_SHORT).show();
    }

    public String whichDate () {
        DateFormat dateFormat = new SimpleDateFormat("dd_MM_yyyy");
        Date date = new Date();

        return dateFormat.format(date);
    }

    public String timeStamp () {
        DateFormat timestamp = new SimpleDateFormat("HH:mm:ss");
        String currentTime = timestamp.format(new Date());

        return currentTime;
    }
}




