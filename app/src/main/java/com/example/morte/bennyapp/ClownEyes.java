package com.example.morte.bennyapp;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.JetPlayer;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.PowerManager;
import android.provider.MediaStore;
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
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;
import java.util.logging.Level;

public class ClownEyes extends AppCompatActivity {

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

        public void run() {
            double amp = mSensor.getAmplitude();
            //Log.i("Noise", "runnable mPollTask");

            updateDisplay("Listening for bricks....", amp);

            if ((amp > mThreshold)) {
                //callForHelp(amp);
                //Log.i("Noise", "==== onCreate ===");
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
    private static long IdleModeTime = 7000;
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
    MediaPlayer NyMp;


    int TempBuildRequest;
    int TempCollectRequest;
    int TempPretendRequest;
    int TempKiggeNed;
    int TempBaffeldFeedback;
    int TempHappyFeedback;
    int TempIdle;

ImageButton HoldtoReleasButton;
Long down, up;

    ImageButton HoldtoChangeCharecter;
    Long downcharecter, upcharecter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

   //     mediaPlayer = new MediaPlayer();
     //   NyMp = new MediaPlayer();
        InitializeAllMusicArrays();
        TypeofFeedback = 0;
        CohreneceBetweenEyesAndVoice = 0;
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);  // fjerner notifikationsbar
        setContentView(R.layout.activity_clown_eyes);



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
                        Toast.makeText(ClownEyes.this, "Down", Toast.LENGTH_SHORT).show();
                        down=System.currentTimeMillis();
                        break;
                    case MotionEvent.ACTION_UP :
                        Toast.makeText(ClownEyes.this, "Up", Toast.LENGTH_SHORT).show();
                        up=System.currentTimeMillis();
                        if(up-down>3000){
                            Toast.makeText(ClownEyes.this, "More than 3", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(ClownEyes.this, "Down", Toast.LENGTH_SHORT).show();
                        downcharecter=System.currentTimeMillis();
                        break;
                    case MotionEvent.ACTION_UP :
                        Toast.makeText(ClownEyes.this, "Up", Toast.LENGTH_SHORT).show();
                        upcharecter=System.currentTimeMillis();
                        if(upcharecter-downcharecter>3000){
                            Toast.makeText(ClownEyes.this, "More than 3", Toast.LENGTH_SHORT).show();
                           // finish();
                            Intent i = new Intent(ClownEyes.this, BennyMain.class);
                            startActivity(i);

                        }
                        return true;
                }
                return false;
            }


        });

    }

    public void PresentBenny(){

        findViewById(R.id.BennyOjne).setAlpha(1);  //Fjern baggrund
        String PathToBennyEyes =   "android.resource://com.example.morte.bennyapp/" + R.raw.klovnidle;
        Uri uriLang =Uri.parse(PathToBennyEyes);
        OjneView.setVideoURI(uriLang);
        OjneView.start();
        PlayMusicFile(RobertIntroduction, 0);




        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {

                SuperRequestTimerStart();                                                //Timer
                RequestNotActiveTimerStart();                                            //Timer
                FeedbackTimerTimerStart();                                               //Timer


                StartRequestRound();

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


      //  Random r = new Random();
//        int SizeOfArray = arrayList.size();
//        int nyrandom = r.nextInt((SizeOfArray - 0)+1)+0;
    //    int nyrandom = r.nextInt(arrayList.size());

        //  lol = (String) arrayList.get(nyrandom);                // Okay (String) er ikke mig men android studio der har lavet
try {
    lol = (String) arrayList.get(numberinArray);                // Okay (String) er ikke mig men android studio der har lavet





    /*
        if (NotLastOne(lol)){

            while (NotLastOne(lol)){
                Random t = new Random();
                int NYTrandom = t.nextInt(arrayList.size());
                tempo = (String) arrayList.get(NYTrandom);

                    if (!NotLastOne(tempo)){
                        myUri = Uri.parse(tempo); // initialize Uri here
                        Toast.makeText(this, "Samme replik som før", Toast.LENGTH_SHORT).show();
                        break;
                }

            }

        }
        else{
            Toast.makeText(this, "Ny replik", Toast.LENGTH_SHORT).show();
             tempo=lol;
            myUri = Uri.parse(lol); // initialize Uri here
        }

*/


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
    Toast.makeText(this, "Outofindex", Toast.LENGTH_SHORT).show();
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

                if (FirstCharInSongFile == 'B' && SecoundCharInSongFile == 'I' ){

                    RobertIdle.add(DataPath);

                }
                else if (FirstCharInSongFile == 'B' && SecoundCharInSongFile == 'C' ){

                    RobertCollectRequest.add(DataPath);
                    //  Toast.makeText(this, "Match for " + FirstCharInSongFile + "and " + SecoundCharInSongFile, Toast.LENGTH_SHORT).show();

                }
                else if (FirstCharInSongFile == 'B' && SecoundCharInSongFile == 'K' ){

                    RobertKiggeNed.add(DataPath);

                }

                else if (FirstCharInSongFile == 'B' && SecoundCharInSongFile == 'B' ){

                    RobertBuildRequest.add(DataPath);

                }

                else if (FirstCharInSongFile == 'B' && SecoundCharInSongFile == 'P' ){

                    RobertPretendRequest.add(DataPath);

                }
                if (FirstCharInSongFile == 'B' && SecoundCharInSongFile == 'Y' ){

                    RobertHappyFeedback.add(DataPath);

                }
                /*
                if (FirstCharInSongFile == 'R' && SecoundCharInSongFile == 'Y' ){

                    RobertNeutralFeedback.add(DataPath);

                }
                */
                if (FirstCharInSongFile == 'B' && SecoundCharInSongFile == 'X' ){

                    RobertBaffledFeedback.add(DataPath);

                }
                if (FirstCharInSongFile == 'B' && SecoundCharInSongFile == 'O' ){

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

        if (mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer.release();
        }
        if (NyMp != null) {
            NyMp.stop();
            NyMp.release();
        }
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

    public int TempFunction(ArrayList array, int WhatTemp ){



        Random r = new Random();
        int Randomtal = r.nextInt(array.size());
        Toast.makeText(this, "" + array.size(), Toast.LENGTH_SHORT).show();
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

    public void PreventRepetetion(ArrayList array) {

        if (array.equals(RobertBuildRequest)) {
            Toast.makeText(this, "Build", Toast.LENGTH_SHORT).show();

            TempBuildRequest = TempFunction(RobertBuildRequest, TempBuildRequest);
            PlayMusicFile(RobertBuildRequest, TempBuildRequest);
        }

        if (array.equals(RobertPretendRequest)) {
            Toast.makeText(this, "Pretend", Toast.LENGTH_SHORT).show();

            TempPretendRequest = TempFunction(RobertPretendRequest, TempPretendRequest);
            PlayMusicFile(RobertPretendRequest, TempPretendRequest);

        }

        if (array.equals(RobertCollectRequest)) {
            Toast.makeText(this, "Collect", Toast.LENGTH_SHORT).show();
            TempCollectRequest = TempFunction(RobertCollectRequest, TempCollectRequest);
            PlayMusicFile(RobertCollectRequest, TempCollectRequest);


        }
        if (array.equals(RobertKiggeNed)) {

            Toast.makeText(this, "kiggened", Toast.LENGTH_SHORT).show();
            TempKiggeNed = TempFunction(RobertKiggeNed, TempKiggeNed);
            PlayMusicFile(RobertKiggeNed, TempKiggeNed);

        }
        if (array.equals(RobertBaffledFeedback)) {

            Toast.makeText(this, "Baffeeld", Toast.LENGTH_SHORT).show();
            TempBaffeldFeedback = TempFunction(RobertBaffledFeedback, TempBaffeldFeedback);
            PlayMusicFile(RobertBaffledFeedback, TempBaffeldFeedback);

        }

        if (array.equals(RobertHappyFeedback)) {

            Toast.makeText(this, "happy", Toast.LENGTH_SHORT).show();
            TempHappyFeedback = TempFunction(RobertHappyFeedback, TempHappyFeedback);
            PlayMusicFile(RobertHappyFeedback, TempHappyFeedback);
        }

        if (array.equals(RobertIdle)) {

            Toast.makeText(this, "idle", Toast.LENGTH_SHORT).show();
            TempIdle = TempFunction(RobertIdle, TempIdle);
            PlayMusicFile(RobertIdle, TempIdle);
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
                "android.resource://com.example.morte.bennyapp/" + R.raw.klovnsolbriller,
                "android.resource://com.example.morte.bennyapp/" + R.raw.klovnoverrasket,
                "android.resource://com.example.morte.bennyapp/" + R.raw.klovnmegetglad,
                "android.resource://com.example.morte.bennyapp/" + R.raw.klovnhjerteojne,
                "android.resource://com.example.morte.bennyapp/" + R.raw.klovnglad,
                "android.resource://com.example.morte.bennyapp/" + R.raw.klovndisko,
                "android.resource://com.example.morte.bennyapp/" + R.raw.klovncrazy,
                "android.resource://com.example.morte.bennyapp/" + R.raw.klovncool,
                "android.resource://com.example.morte.bennyapp/" + R.raw.klovnblink,



        };

        BennyNoFeelingOjneArray = new String[]{

                //Insert No feeling eyes
                "android.resource://com.example.morte.bennyapp/" + R.raw.klovnidle,
                "android.resource://com.example.morte.bennyapp/" + R.raw.klovnblinkerbeggeojne,
                "android.resource://com.example.morte.bennyapp/" + R.raw.klovnkiggerned,




        };

        BennyNotPleasedOjneArray = new String[]{

                //Insert Not pleased eyes
                "android.resource://com.example.morte.bennyapp/" + R.raw.klovnsur,
                "android.resource://com.example.morte.bennyapp/" + R.raw.klovnsyg,
                "android.resource://com.example.morte.bennyapp/" + R.raw.klovnsolbriller,
                "android.resource://com.example.morte.bennyapp/" + R.raw.klovnoverrasket,
                "android.resource://com.example.morte.bennyapp/" + R.raw.klovnlider,
                "android.resource://com.example.morte.bennyapp/" + R.raw.klovnforvirret,
                "android.resource://com.example.morte.bennyapp/" + R.raw.klovndisko,
                "android.resource://com.example.morte.bennyapp/" + R.raw.klovncrazy,



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

    public void PlayBennyNoFeelingOjne(){ //aka kigge ned

        Random r = new Random();
        int ArrayLength = BennyHappyOjneArray.length;
        int nyrandom = r.nextInt(ArrayLength - 0)+0;


        String PathToBennyEyes = BennyNoFeelingOjneArray[2];
        Uri uriLang =Uri.parse(PathToBennyEyes);
        OjneView.setVideoURI(uriLang);
        OjneView.start();

    }

    public void PlayBennyNotPleasedOjne(){
        Random r = new Random();
        int ArrayLength = BennyNotPleasedOjneArray.length;
        int nyrandom = r.nextInt(ArrayLength - 0)+0;    //Der er noget galt her. Den går udover det tilltdte   har slettet et +1 og nogle parenteser
        String PathToBennyEyes = BennyNotPleasedOjneArray[nyrandom];
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

    private void IdleLinesInReqquestNotActive(){

//        PlayMusicFile(RobertIdle);
        PreventRepetetion(RobertIdle);
   //     final MediaPlayer NyMp = new MediaPlayer();
/*
if (mediaPlayer == null) {
    BrickDetected = true;

    Random r = new Random();
//        int SizeOfArray = arrayList.size();
//        int nyrandom = r.nextInt((SizeOfArray - 0)+1)+0;
    int nyrandom = r.nextInt(RobertIdle.size());


    String lol = (String) RobertIdle.get(nyrandom);                // Okay (String) er ikke mig men android studio der har lavet

    Uri myUri = Uri.parse(lol); // initialize Uri here

    NyMp.setAudioStreamType(AudioManager.STREAM_MUSIC);
    try {
        NyMp.setDataSource(getApplicationContext(), myUri);
    } catch (IOException e) {
        e.printStackTrace();
//            Toast.makeText(this, "Exception 1", Toast.LENGTH_SHORT).show();
    }
    try {
        NyMp.prepare();
    } catch (IOException e) {
        e.printStackTrace();
        //          Toast.makeText(this, "Exception 2", Toast.LENGTH_SHORT).show();

    }
    NyMp.start();

    NyMp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
//                Toast.makeText(BennyEyes.this, "MP reset", Toast.LENGTH_SHORT).show();
            // mediaPlayer.stop();  https://stackoverflow.com/questions/10453691/mediaplayer-throwing-illegalstateexception-when-calling-onstop
            NyMp.release();
            BrickDetected = false;
        }
    });

}
else {
    Toast.makeText(this, "Vi er idle", Toast.LENGTH_SHORT).show();
}
*/
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
                        IdleLinesInReqquestNotActive();
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

}




