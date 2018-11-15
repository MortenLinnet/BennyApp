package com.example.morte.bennyapp;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.ImageFormat;
import android.graphics.Typeface;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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

public class BennyMain extends AppCompatActivity {
    public static int whatText;
    public static int previousEffect;
    public Integer Language;
    String Nurse;
    String Hero;
    String Cowboy;
    String Cactus;
    String Pirate;
    String Settings;
    String LetsPlay;
    String SelectMode;
    ImageView CurrentImageview;
    ImageView NextImageview;
    Animation enter;
    Animation leaave;
    Animation fade;
    Button next;
    Button prev;
    int Animationtime = 500;
    int LocationOOfImage = 1800;
    int id;


    int RECORD_AUDIO = 0; //Skal bruges til tilladelse om at optagee lyd
    private static final int MY_PERMISSION_REQUEST = 1;  //Skal bruges til at tilgå external storage

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_benny_main);

        SetLanguage();

        next = findViewById(R.id.nextbuttton);
        prev = findViewById(R.id.backbutton);
        TextView playModeText = (TextView) findViewById(R.id.playButton);
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "font/bennyNormal.ttf");
        playModeText.setTypeface(custom_font);

        id = 1;
        CurrentImageview = (ImageView) findViewById(R.id.FrontImageView);
        NextImageview = (ImageView) findViewById(R.id.BackImageView);

        fade = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade);
///lololol
        NextImageview.setOnTouchListener(new OnSwipeListener(this) {

            public void onSwipeTop() {

            }

            public void onSwipeRight() {
                NextButtonanimationer(CurrentImageview, NextImageview);
                id++;
                if (id > 3) {
                    id = 1;
                }
                HighLightMarkedCharecter();
            }

            public void onSwipeLeft() {

                PrevButtonAnimmationer(CurrentImageview, NextImageview);
                id--;
                if (id < 1) {
                    id = 3;
                }
                HighLightMarkedCharecter();
            }

            public void onSwipeBottom() {

            }

            @Override
            public void onClick() {

                Intent i = new Intent(BennyMain.this, PopUpActivity.class);
                startActivity(i);
                Toast.makeText(BennyMain.this, "Clicked", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onLooongPress() {

                Toast.makeText(BennyMain.this, "LongPress", Toast.LENGTH_SHORT).show();

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

        } else {
            // Tænker bare det er her vi initialiserer vores arrays af lyd.  doStuff();

        }

    }

    public void SetLanguage(){
        Language = 1;

        try {
            Intent intent = getIntent();
            Bundle bundle = intent.getExtras();
            Integer languageIdentifyer = bundle.getInt("Language");
            Language = languageIdentifyer;


            Toast.makeText(this, "Tal er" + Language, Toast.LENGTH_SHORT).show();

        } catch (NullPointerException e) {
            Toast.makeText(this, "Nullpointer catched", Toast.LENGTH_SHORT).show();
        }

        SetStrings(Language);

    }
    public void SetStrings(Integer lang) {
        if (lang == 1) {
            Nurse = "Nurse";
            Hero = "Hero";
            Cowboy = "Cowboy";
            Cactus = "Cactus";
            Pirate = "Pirate";
            Settings = "Settings";
            LetsPlay = "Let us play ";
            SelectMode = "Select Mode";

        }
        if (lang == 2) {
            Nurse = "Sygeplejerske";
            Hero = "Helt";
            Cowboy = "Kodreng";
            Cactus = "Kaktus";
            Pirate = "Pirat";
            Settings = "Indstillinger";
            LetsPlay = "Lad os lege";
            SelectMode = "Vælg mode";

        }
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

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(LeaveAnimation, EnterAnimation);
        animatorSet.start();


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

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(LeaveAnimation, EnterAnimation);
        animatorSet.start();


    }

    public void next(View view) {

        NextButtonanimationer(CurrentImageview, NextImageview);
        id++;
        if (id > 3) {
            id = 1;
        }
        Toast.makeText(this, "Id er" + " " + id, Toast.LENGTH_SHORT).show();
        HighLightMarkedCharecter();
    }

    public void back(View view) {

        PrevButtonAnimmationer(CurrentImageview, NextImageview);
        id--;
        if (id < 1) {
            id = 3;
        }

        Toast.makeText(this, "Id er" + " " + id, Toast.LENGTH_SHORT).show();
        HighLightMarkedCharecter();
    }


    public void currentImage() {

        Button playB = (Button) findViewById(R.id.playButton);
        TextView charectername = findViewById(R.id.CharecterTexxtview);
        if (id > 3) {
            id = 1;
        }

        switch (id) {
            case 1:
                CurrentImageview.setImageResource(R.drawable.flotfyr);
                playB.setText(LetsPlay + " " + Nurse);
                charectername.setText(Nurse);
                break;
            case 2:
                CurrentImageview.setImageResource(R.drawable.emmet);
                playB.setText(LetsPlay + " " + Hero);
                charectername.setText(Hero);
                break;
            case 3:
                CurrentImageview.setImageResource(R.drawable.darthvader);
                playB.setText(LetsPlay + " " + Cowboy);
                charectername.setText(Cowboy);
                break;


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


    public void PreviousImage() {
        int prevImage = id - 1;
        if (prevImage < 1) {
            prevImage = 3;
        }

        switch (prevImage) {
            case 1:
                NextImageview.setImageResource(R.drawable.flotfyr);
                break;
            case 2:
                NextImageview.setImageResource(R.drawable.emmet);
                break;
            case 3:
                NextImageview.setImageResource(R.drawable.darthvader);
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
                NextImageview.setImageResource(R.drawable.flotfyr);
                break;
            case 2:
                NextImageview.setImageResource(R.drawable.emmet);
                break;
            case 3:
                NextImageview.setImageResource(R.drawable.darthvader);
                break;


        }

    }

    public void setimgview1(View view) {
        id = 1;
        currentImage();
        HighLightMarkedCharecter();
        NextImageview.setImageResource(R.drawable.flotfyr);
        NextImageview.startAnimation(fade);
    }

    public void setimgview3(View view) {

        id = 3;
        currentImage();
        HighLightMarkedCharecter();
        NextImageview.setImageResource(R.drawable.darthvader);
        NextImageview.startAnimation(fade);
    }

    public void setimgview2(View view) {
        id = 2;
        currentImage();
        HighLightMarkedCharecter();
        NextImageview.setImageResource(R.drawable.emmet);
        NextImageview.startAnimation(fade);
    }



    public void viewMessage(View view) {
        ImageButton clickedButton = (ImageButton) view;
        Button playB = (Button) findViewById(R.id.playButton);
        switch (clickedButton.getId()) {
            case R.id.imageButton1:

                playB.setText(LetsPlay + " " + Nurse);
                whatText = 1;
                break;

            case R.id.imageButton2:

                playB.setText(LetsPlay + " " + Hero);
                whatText = 2;
                break;

            case R.id.imageButton3:
                playB.setText(LetsPlay + " " + Cowboy);
                whatText = 3;
                break;

            case R.id.imageButton4:
                playB.setText(LetsPlay + " " + Cactus);
                whatText = 4;
                break;

            case R.id.imageButton5:
                playB.setText(LetsPlay + " " + Pirate);
                whatText = 5;
                break;
        }


    }

    public void onClick(View v) {
        if (id == 1) {
            //Insert name of the class to be redirected to.

            Bundle bundle = new Bundle();
            Integer language = Language;
            bundle.putInt("Language", language);
            Intent i = new Intent(this, BennyEyes.class);
            i.putExtras(bundle);
            startActivity(i);

            //Intent i = new Intent(this, .class);
            //startActivity(i);

        } else if (whatText == 2) {

        } else if (whatText == 3) {

        } else if (whatText == 4) {

        } else if (whatText == 5) {

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
                } else {
                    Toast.makeText(this, "You have to give access", Toast.LENGTH_SHORT).show();
                    finish();
                }
                return;
            }
        }

    }


    public void HighLightMarkedCharecter() {
        Button button1 = findViewById(R.id.buttonselection1);
        Button button2 = findViewById(R.id.buttonselection2);
        Button button3 = findViewById(R.id.buttonselection3);
        switch (id) {
            case 1:
                button1.setBackgroundColor(this.getResources().getColor(R.color.ButtonSelecctionColor));
                button2.setBackgroundColor(this.getResources().getColor(R.color.ButtonNOTSelecctionColor));
                button3.setBackgroundColor(this.getResources().getColor(R.color.ButtonNOTSelecctionColor));
                break;

            case 2:
                button1.setBackgroundColor(this.getResources().getColor(R.color.ButtonNOTSelecctionColor));
                button2.setBackgroundColor(this.getResources().getColor(R.color.ButtonSelecctionColor));
                button3.setBackgroundColor(this.getResources().getColor(R.color.ButtonNOTSelecctionColor));
                break;

            case 3:
                button1.setBackgroundColor(this.getResources().getColor(R.color.ButtonNOTSelecctionColor));
                button2.setBackgroundColor(this.getResources().getColor(R.color.ButtonNOTSelecctionColor));
                button3.setBackgroundColor(this.getResources().getColor(R.color.ButtonSelecctionColor));
                break;

        }


    }
}