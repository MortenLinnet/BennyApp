package com.example.morte.bennyapp;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.ImageFormat;
import android.graphics.Typeface;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.Image;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.File;

public class BennyMain extends AppCompatActivity {
    public static final String BennyPreferences = "BennyPreferences";
    public static int whatText;
    public Integer Language;
    public static Integer AppLanguage;
    int Animationtime = 500;
    int LocationOOfImage = 1800;
    int id;
    String Benny;
    String Clown;
    String Pirate;
    String Settings;
    String TextbobbleRIGHT;
    String TextbobbleLowLeft;
    String TextBobbleTopRight;
    String LetsPlay;
    String SelectMode;
    String ChoosePHeader;
    Animation enter;
    Animation leaave;
    Animation fade;
    ImageButton prev;
    ImageButton next;
    TextView heading;
    ImageView CurrentImageview;
    ImageView NextImageview;


    AnimatorSet animatorset;


    TextView TextViewBobleHojre;
    TextView TextViewBobleDownLeft;
    TextView TextviewBobleOverstLeft;
    TextView playModeText;
    TextView ChoosePersonality;
    ImageView SwipeView;

    int RECORD_AUDIO = 0; //Skal bruges til tilladelse om at optagee lyd
    private static final int MY_PERMISSION_REQUEST = 1;  //Skal bruges til at tilgå external storage



    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences pref = getSharedPreferences(BennyPreferences, Context.MODE_PRIVATE);
        AppLanguage = pref.getInt("AppLanguage", 0);
        SetLanguage(AppLanguage);

        setContentView(R.layout.activity_benny_main);


        Typeface bold_font = Typeface.createFromAsset(getAssets(), "font/effra_Regular.ttf");
        Typeface standard_font = Typeface.createFromAsset(getAssets(), "font/effra_Regular.ttf");



        next = findViewById(R.id.nextbuttton);
        prev = findViewById(R.id.BackButton);
        heading = findViewById(R.id.CharecterTexxtview);
        playModeText = (TextView) findViewById(R.id.playButton);
        playModeText.setTypeface(bold_font);
        heading.setTypeface(bold_font);


        ChoosePersonality = findViewById(R.id.ChoosePersonality);
        TextViewBobleHojre = findViewById(R.id.talebobletilhojreTEXTVIEW);
        TextViewBobleDownLeft = findViewById(R.id.taleboblenederstilvenstreTEXTVIEW);
        TextviewBobleOverstLeft = findViewById(R.id.talebobleoversttilvenstreTEXTVIEW);

        ChoosePersonality.setTypeface(bold_font);
        TextViewBobleHojre.setTypeface(standard_font);
        TextViewBobleDownLeft.setTypeface(standard_font);
        TextviewBobleOverstLeft.setTypeface(standard_font);

        ChoosePersonality.setText(ChoosePHeader);
        playModeText.setText(SelectMode);

        animatorset = new AnimatorSet();

        id = 1;
        CurrentImageview = (ImageView) findViewById(R.id.FrontImageView);
        NextImageview = (ImageView) findViewById(R.id.BackImageView);
        SwipeView = findViewById(R.id.SwipeForNextCharecter);
        fade = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade);


        SwipeView.setOnTouchListener(new OnSwipeListener(this) {

                                             public void onSwipeTop() {

                                             }
            public void onSwipeRight() {


                if (!animatorset.isRunning()) {
                    NextButtonanimationer(CurrentImageview, NextImageview);

                    id++;
                    if (id > 3) {
                        id = 1;
                    }
                    SetStringInTextboxeses(Language);
                    talebobler();


                   // Toast.makeText(BennyMain.this, "" + id, Toast.LENGTH_SHORT).show();
                    //HighLightMarkedCharecter();
                }
            }
            public void onSwipeLeft() {
                if (!animatorset.isRunning()) {

                    PrevButtonAnimmationer(CurrentImageview, NextImageview);

                    id--;
                    if (id < 1) {
                        id = 3;
                    }
                    SetStringInTextboxeses(Language);
                    talebobler();
                  //  Toast.makeText(BennyMain.this, "" + id, Toast.LENGTH_SHORT).show();
                    //   HighLightMarkedCharecter();
                }
            }

            @Override
            public void onClick() {


            }

            @Override
            public void onLooongPress() {

              //  Toast.makeText(BennyMain.this, "LongPress", Toast.LENGTH_SHORT).show();

                super.onLooongPress();
            }
        });





        WindowManager.LayoutParams layout = getWindow().getAttributes();
        layout.screenBrightness = 1F; //https://developer.android.com/reference/android/view/WindowManager.LayoutParams#screenBrightness 1 er max value
        getWindow().setAttributes(layout);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);   // disable sleep også i manifest.

        // Ting vi skal have tilladelse til:
        //Optage Lyd
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO},
                    RECORD_AUDIO);
        }
        // Tilgå External Storage
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSION_REQUEST);

            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSION_REQUEST);

            }

        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSION_REQUEST);
            }
            else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSION_REQUEST);
            }
        }

        else{
            // Tænker bare det er her vi initialiserer vores arrays af lyd.  doStuff();

        }

    }



    @Override
    protected void onResume() {
        super.onResume();
        SetLanguage(AppLanguage);
    }

    public void NextButtonanimationer(ImageView LeaveView, ImageView EnterView) {
        UpdateNextImages();
        //  CurrentImageview.setAlpha(100);
        ObjectAnimator LeaveAnimation = ObjectAnimator.ofFloat(LeaveView, "translationX", 0, LocationOOfImage);
        LeaveAnimation.setDuration(Animationtime);
        //LeaveAnimation.start();


        //NextImageview.setAlpha(100);
        ObjectAnimator EnterAnimation = ObjectAnimator.ofFloat(EnterView, "translationX", -LocationOOfImage, 0);
        EnterAnimation.setDuration(Animationtime);
        //EnterAnimation.start();

       // AnimatorSet animatorSet = new AnimatorSet();

        animatorset.playTogether(LeaveAnimation,EnterAnimation);
        animatorset.start();
   //     animatorSet.playTogether(LeaveAnimation, EnterAnimation);
     //   animatorSet.start();


    }

    public void PrevButtonAnimmationer(ImageView LeaveView, ImageView EnterView) {
        UpdatePrevImage();
        //  CurrentImageview.setAlpha(100);
        ObjectAnimator LeaveAnimation = ObjectAnimator.ofFloat(LeaveView, "translationX", 0, -LocationOOfImage);
        LeaveAnimation.setDuration(Animationtime);
        //LeaveAnimation.start();


        // NextImageview.setAlpha(100);
        ObjectAnimator EnterAnimation = ObjectAnimator.ofFloat(EnterView, "translationX", LocationOOfImage, 0);
        EnterAnimation.setDuration(Animationtime);
        //EnterAnimation.start();

        // AnimatorSet animatorSet = new AnimatorSet();

       animatorset.playTogether(LeaveAnimation,EnterAnimation);
       animatorset.start();
        //animatorSet.playTogether(LeaveAnimation, EnterAnimation);
        //animatorSet.start();


    }

    public void talebobler(){


        ImageView TextBobleOverstVenstre = findViewById(R.id.talebobleoversttilvenstre);
        ImageView TextBobleNederstVenstre = findViewById(R.id.taleboblenederstilvenstre);
        ImageView TextBobleHojre = findViewById(R.id.talebobletilhojre);

        TextviewBobleOverstLeft.setText(TextBobbleTopRight);
        TextViewBobleDownLeft.setText(TextbobbleLowLeft);
        TextViewBobleHojre.setText(TextbobbleRIGHT);

int debugtime= 1;
        int lol= 0;


        //Objekt animationer taleboblerne, fra et sted til et andet
        ObjectAnimator EnterAnimationUpLeft = ObjectAnimator.ofFloat(TextBobleOverstVenstre, "translationY", 300, 0);
        EnterAnimationUpLeft.setDuration(550);

        ObjectAnimator EnterAnimationDownLeft = ObjectAnimator.ofFloat(TextBobleNederstVenstre, "translationY", 300, 0);
        EnterAnimationDownLeft.setDuration(700*debugtime);

        ObjectAnimator EnterAnimationRight = ObjectAnimator.ofFloat(TextBobleHojre, "translationY", 300, 0);
        EnterAnimationRight.setDuration(300);


        //Objektanimation textview fra et sted til et andet

        ObjectAnimator EnterAnimationUpLeftTextView = ObjectAnimator.ofFloat(TextviewBobleOverstLeft, "translationY", 300, 0); // har ændret fra 300 til 400
        EnterAnimationUpLeftTextView.setDuration(550+lol);

        ObjectAnimator EnterAnimationDownLeftTextView = ObjectAnimator.ofFloat(TextViewBobleDownLeft, "translationY", 300, 0); //300 til 1000
        EnterAnimationDownLeftTextView.setDuration(700*debugtime);

        ObjectAnimator EnterAnimationRightTextView = ObjectAnimator.ofFloat(TextViewBobleHojre, "translationY", 300, 0);
        EnterAnimationRightTextView.setDuration(300);


        //Objektanimationer talebobler, fade fra en apha til en anden aplha

        ObjectAnimator UpLeftFadeOut = ObjectAnimator.ofFloat(TextBobleOverstVenstre, "alpha",  0f, .1f);
        UpLeftFadeOut.setDuration(100);
        ObjectAnimator UpLeftFadeIn = ObjectAnimator.ofFloat(TextBobleOverstVenstre, "alpha", .1f, 1f);
        UpLeftFadeIn.setDuration(450);

        ObjectAnimator DownleftFadeOut = ObjectAnimator.ofFloat(TextBobleNederstVenstre, "alpha",  0f, .1f);
        DownleftFadeOut.setDuration(150*debugtime);
        ObjectAnimator DownLeftFadeIn = ObjectAnimator.ofFloat(TextBobleNederstVenstre, "alpha", .1f, 1f);
        DownLeftFadeIn.setDuration(550*debugtime);

        ObjectAnimator HojreFadeOut = ObjectAnimator.ofFloat(TextBobleHojre, "alpha",  0f, .1f);
        HojreFadeOut.setDuration(75);
        ObjectAnimator HojreFadeIn = ObjectAnimator.ofFloat(TextBobleHojre, "alpha", .1f, 1f);
        HojreFadeIn.setDuration(225);


        //Objektanimation textview, fade fra aplha til alpha

        ObjectAnimator UpLeftFadeOutTextview = ObjectAnimator.ofFloat(TextviewBobleOverstLeft, "alpha",  0f, .1f);
        UpLeftFadeOutTextview.setDuration(100);
        ObjectAnimator UpLeftFadeINTextview = ObjectAnimator.ofFloat(TextviewBobleOverstLeft, "alpha", .1f, 1f);
        UpLeftFadeINTextview.setDuration(450);


        ObjectAnimator DownleftFadeOutTextview = ObjectAnimator.ofFloat(TextViewBobleDownLeft, "alpha",  0f, .1f);
        DownleftFadeOutTextview.setDuration(150*debugtime);
        ObjectAnimator DownleftFadeINTextview = ObjectAnimator.ofFloat(TextViewBobleDownLeft, "alpha", .1f, 1f);
        DownleftFadeINTextview.setDuration(550*debugtime);


        ObjectAnimator HojreFadeOutText = ObjectAnimator.ofFloat(TextViewBobleHojre, "alpha",  0f, .1f);
        HojreFadeOutText.setDuration(75);
        ObjectAnimator HojreFadeInTExt = ObjectAnimator.ofFloat(TextViewBobleHojre, "alpha", .1f, 1f);
        HojreFadeInTExt.setDuration(225);



     //   AnimatorSet animatorSet = new AnimatorSet();  //Bruger ikke den her men en global så jeg kan tjekke om den kører oppe i swipe, så den ikke kører flere gange på samme tid

        //textbobler animationer
        animatorset.play(EnterAnimationRight).with(HojreFadeOut).before(HojreFadeIn);
        animatorset.play(EnterAnimationRightTextView).with(HojreFadeOutText).before(HojreFadeInTExt);



        animatorset.play(EnterAnimationDownLeft).with(DownleftFadeOut).before(DownLeftFadeIn);
        animatorset.play(EnterAnimationDownLeftTextView).with(DownleftFadeOutTextview).before(DownleftFadeINTextview);

        //textview animationer
        animatorset.play(EnterAnimationUpLeft).with(UpLeftFadeOut).before(UpLeftFadeIn);
        animatorset.play(EnterAnimationUpLeftTextView).with(UpLeftFadeOutTextview).before(UpLeftFadeINTextview);



        //animatorSet.playTogether(EnterAnimationRight);
        animatorset.start();
    }


    public void next(View view) {

        if (!animatorset.isRunning()) {
            NextButtonanimationer(CurrentImageview, NextImageview);

            id++;
            if (id > 3) {
                id = 1;
            }
            SetStringInTextboxeses(Language);
            talebobler();


            // Toast.makeText(BennyMain.this, "" + id, Toast.LENGTH_SHORT).show();
            //HighLightMarkedCharecter();
        }
    }

    public void back(View view) {


        if (!animatorset.isRunning()) {

            PrevButtonAnimmationer(CurrentImageview, NextImageview);

            id--;
            if (id < 1) {
                id = 3;
            }
            SetStringInTextboxeses(Language);
            talebobler();
            //  Toast.makeText(BennyMain.this, "" + id, Toast.LENGTH_SHORT).show();
            //   HighLightMarkedCharecter();
        }
    }




    public void UpdateNextImages() {
        currentImage();
        NextImage();
    }

    public void UpdatePrevImage() {
        currentImage();
        PreviousImage();
    }

    public void currentImage() {

        Button playB = (Button) findViewById(R.id.playButton);
        TextView charectername = findViewById(R.id.CharecterTexxtview);
        if (id > 3) {
            id = 1;
        }

        switch (id) {
            case 1:
                CurrentImageview.setImageResource(R.drawable.bennyojne);
               // playB.setText(LetsPlay + " " +Pirate);


                charectername.setText(Pirate);
                charectername.startAnimation(AnimationUtils.loadAnimation(BennyMain.this, R.anim.fade));
                break;
            case 2:
                CurrentImageview.setImageResource(R.drawable.piratojne);
                //playB.setText(LetsPlay + " " + Clown);
                charectername.setText(Clown);
                charectername.startAnimation(AnimationUtils.loadAnimation(BennyMain.this, R.anim.fade));
                break;
            case 3:
                CurrentImageview.setImageResource(R.drawable.klovn);
               // playB.setText(LetsPlay + " " + Benny);
                charectername.setText(Benny);
                charectername.startAnimation(AnimationUtils.loadAnimation(BennyMain.this, R.anim.fade));
                break;
        }
    }

    public void PreviousImage() {
        int prevImage = id - 1;
        Button playB = (Button) findViewById(R.id.playButton);
        TextView charectername = findViewById(R.id.CharecterTexxtview);

        if (prevImage < 1) {
            prevImage = 3;
        }

        switch (prevImage) {
            case 1:
                NextImageview.setImageResource(R.drawable.bennyojne);
             //   playB.setText(LetsPlay + " " + Benny);
                charectername.setText(Benny);
                break;
            case 2:
                NextImageview.setImageResource(R.drawable.piratojne);
               // playB.setText(LetsPlay + " " + Pirate);
                charectername.setText(Pirate);
                break;
            case 3:
                NextImageview.setImageResource(R.drawable.klovn);
                //playB.setText(LetsPlay + " " + Clown);
                charectername.setText(Clown);
                break;


        }

    }

    public void NextImage() {
        int NextImage = id + 1;
        if (NextImage > 3) {
            NextImage = 1;
            id = 3;
        }

        switch (NextImage) {
            case 1:
                NextImageview.setImageResource(R.drawable.bennyojne);
                break;
            case 2:
                NextImageview.setImageResource(R.drawable.piratojne);
                break;
            case 3:
                NextImageview.setImageResource(R.drawable.klovn);
                break;


        }

    }

    public void viewMessage(View view) {
        ImageButton clickedButton = (ImageButton) view;
     //   Toast.makeText(this, "virker det her", Toast.LENGTH_SHORT).show();
        Button playB = (Button) findViewById(R.id.playButton);
        switch (clickedButton.getId()) {
            case R.id.imageButton1:

                playB.setText(LetsPlay + " " + Benny);
                whatText = 1;
                break;

            case R.id.imageButton2:

                playB.setText(LetsPlay + " " + Clown);
                whatText = 2;
                break;

            case R.id.imageButton3:
                playB.setText(LetsPlay + " " + Pirate);
                whatText = 3;
                break;

        /*    case R.id.imageButton4:
                playB.setText(LetsPlay + " " + Cactus);
                whatText = 4;
                break;
*/
            case R.id.imageButton5:
                playB.setText(LetsPlay + " " + Pirate);
                whatText = 5;
                break;
        }


    }

    public void IsMicPluggedIn(Context context){


            AudioManager am = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);

            if(am.isWiredHeadsetOn()) {
                // handle headphones plugged in
                Bundle bundle = new Bundle();
                Integer language = Language;
                bundle.putInt("Language", language);
                Intent i = new Intent(this, BennyEyes.class);
                i.putExtras(bundle);
                startActivity(i);

            } else{

                Bundle bundle = new Bundle();
                Integer language = Language;
                bundle.putInt("Language", language);
                Intent i = new Intent(this, MicNotPluggedIn.class);
                i.putExtras(bundle);
                startActivity(i);


            }
        }



    public void onClick(View v) {
        if (id == 1) {
            //Insert name of the class to be redirected to.
          //  IsMicPluggedIn(this);
            Intent i = new Intent(this, BennyEyes.class); startActivityForResult(i, 1);

        }
        if (id == 2) {
            //Insert name of the class to be redirected to.
            //  IsMicPluggedIn(this);
            Intent i = new Intent(this, PirateEyes.class); startActivityForResult(i, 1);

        }
        if (id == 3) {
            //Insert name of the class to be redirected to.
            //  IsMicPluggedIn(this);
            Intent i = new Intent(this, ClownEyes.class); startActivityForResult(i, 1);

        }

        else if (whatText == 2) {
            Intent i = new Intent(this, LoggingData.class);
            startActivity(i);

        } else if (whatText == 3) {

        } else if (whatText == 4) {

        } else if (whatText == 5) {

        }

    }

    public void SetLanguage(int lang){

        Language = lang;

        try {
            Intent intent = getIntent();
            Bundle bundle = intent.getExtras();
            //Integer languageIdentifyer = bundle.getInt("Language");
            //Language = languageIdentifyer;


          //  Toast.makeText(this, "Tal er" + Language, Toast.LENGTH_SHORT).show();

        } catch (NullPointerException e) {
            //  Toast.makeText(this, "Nullpointer catched", Toast.LENGTH_SHORT).show();
        }

        SetStrings(Language);
        SetStringInTextboxeses(Language);

    }


    public void SetStrings(Integer lang) {

        Toast.makeText(this, "lang  er:" + lang, Toast.LENGTH_SHORT).show();
        if (lang == 1) {
            Benny = "Benny Brickeater";
            Pirate = "Cap'n Brick";
            Clown = "Benny Balloon";
            Settings = "Settings";
            LetsPlay = "Let us play ";
            SelectMode = "CHOOSE";
            ChoosePHeader = "Choose personality";


        }
        if (lang == 2) {
            Benny = "Benny Brickeater";
            Pirate = "Kaptajn Klods";
            Clown = "Benny Ballon";
            Settings = "Settings";
            LetsPlay = "Lad os lege ";
            SelectMode = "VÆLG";
            ChoosePHeader = "Vælg personlighed";
        }
    }

    public void SetStringInTextboxeses(Integer lang){

      if (lang == 1) {

        //  Toast.makeText(this, "Er jeg hernede i engelsk?", Toast.LENGTH_SHORT).show();
          EnglishLangTextbobbles();


      }
      if (lang == 2){

          //Toast.makeText(this, "Er jeg hernede i dansk?", Toast.LENGTH_SHORT).show();
          DanishLangTextbobbles();


        }





    }

    public void DanishLangTextbobbles(){

     //   Toast.makeText(this, "Id i Lang er  "+ id, Toast.LENGTH_SHORT).show();

        if (id== 1) {

            TextbobbleRIGHT = "Sulten eftrer røde klodser";
            TextbobbleLowLeft = "Bygge mig et hus";
            TextBobbleTopRight = "Jeg er uslten efter noget der siger miav";
        }


       if (id == 2) {
           TextbobbleRIGHT = "Jeg er en farlig pirat";
           TextbobbleLowLeft = "Giv mig guldmønter";
           TextBobbleTopRight = "Jeg er søsyg";
       }

       else if (id == 3){
                TextbobbleRIGHT = "Bygge mig en ballon";
                TextbobbleLowLeft = "Min klovne sans runger";
                TextBobbleTopRight = "Båt Båt kineser snot";
       }

        }



    public void EnglishLangTextbobbles(){

        if (id== 1) {

            TextbobbleRIGHT = "I'm hungry for red bricks";
            TextbobbleLowLeft = "I have never eaten a house before, can you build me one?";
            TextBobbleTopRight = "I'm hungry for something that says MUUUHHH";

        }

        if (id== 2) {

            TextbobbleRIGHT = "I may be the worst pirate you have ever heard of, but atleast you have heard of me";
            TextbobbleLowLeft = "There is a bounty on my head";
            TextBobbleTopRight = "I'm feeling a bit seasick over here";
        }

       else if (id== 3) {

                TextbobbleRIGHT = "oH MY. My balloon took off, can you build me a new one?";
                TextbobbleLowLeft = "I like to move move it, i like to move it.";
                TextBobbleTopRight = "Honk Honk chineese gue";



        }

    }

    public void Settings(View view) {

        // New activity with options to calibrate, about us and so on.
        Intent i = new Intent(this, Settings.class);
        startActivity(i);
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSION_REQUEST: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {


                        // doStuff();
                    }
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

                    }
                } else  {
                  //  Toast.makeText(this, "You have to give access", Toast.LENGTH_SHORT).show();
                    finish();
                }
                return;
            }
        }

    }


    public void info(View view) {

        String StringBenny1 = null;
        String StringBenny2 = null;
        String StringBenny3 = null;
        Bundle bundle = new Bundle();
        Intent i;

        if (Language == 1) {

            StringBenny1 = "BennyBrickEater is all about sorting them btricks using simpel requests, and sometimes he farts";
            StringBenny2 = "If you are this deep inside of the code and you are not Morten Linnet, you deserve a cookie go buy yourself a cookie, and bring one for Morten ";
            StringBenny3 = "Benny Balloon loves having fun and makeing ballooon animals together with his friends. He once had a leading role in the move IT from 2018. Highly rated on IMDB";
        }

        if (Language == 2) {

            StringBenny1 = "Benny BrickEater er en sej LEGO robot der kan hjælpe din børn med at lege og lære mange spændende ting omkring dyr og simpel matematik";
            StringBenny2 = "Kaptajn Klods er en farlig pirat der har sejlet jorden rundt i hans store piratskib 'Usynkelig III'. Han har lært alternative opdragelsesstrategier som han kan lære dit barn ";
            StringBenny3 = "Benny Ballon er den sjove er Benny's mange splittede personligheder, gennem sjov og leg med både en stereotyp italiensk accent og kunsneriske udfrodringer hjælper han dit barn med at blive en rigtigt cirkus artist";

        }


        switch (id){


            case 1:

                bundle.putString("StringBenny", StringBenny1);
                i = new Intent(BennyMain.this, PopUpActivity.class);
                i.putExtras(bundle);
                startActivity(i);
        break;

            case 2:

                bundle.putString("StringBenny", StringBenny2);
                i = new Intent(BennyMain.this, PopUpActivity.class);
                i.putExtras(bundle);
                startActivity(i);
         break;

            case 3:

                bundle.putString("StringBenny", StringBenny3);
                i = new Intent(BennyMain.this, PopUpActivity.class);
                i.putExtras(bundle);
                startActivity(i);
         break;
        }




    }
}


