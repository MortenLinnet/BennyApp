package com.example.morte.bennyapp;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

public class Oldintro extends AppCompatActivity {
    ImageView SwipeView;
    int CurrentPlace;

    ImageView FourDotsView;
    AnimatorSet animatorSet;
    ImageView haandview;
    TextView textbobltext;
    ImageView talebobl;
    TextView infotext;

    ImageButton NextButton;

    String TextboblinformationOne;
    String TextboblinformationTwo;
    String Headline;
    String ButtonText;
    String InfoTextOne;
    String InfroTextTwo;
    String InfoTextThree;
    String InfoTextFour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStrings(1);
        setContentView(R.layout.activity_introduction);


        infotext = findViewById(R.id.infotext);
        infotext.setText(InfoTextOne);
        TextView introduction = findViewById(R.id.IntroductionTextview);
        introduction.setText(Headline);

        TextView buttontext = findViewById(R.id.ButtonTextIntroduction);
        buttontext.setText(ButtonText);


        SwipeView = findViewById(R.id.SwipeView);
        talebobl = findViewById(R.id.talebobl);
        textbobltext = findViewById(R.id.talebobltextintroduction);


        FourDotsView = findViewById(R.id.FourDotsImageView);
        haandview= findViewById(R.id.haandview);
        NextButton = findViewById(R.id.nextbuttonintroduction);
        NextButton.setEnabled(false);
        CurrentPlace = 1;
        HighLightButtons();
        animatorSet = new AnimatorSet();


        SwipeView.setOnTouchListener(new OnSwipeListener(this){

                                         @Override
                                         public void onSwipeRight() {

                                             if (CurrentPlace> 1 ) {
                                                 CurrentPlace--;
                                             }
                                             HighLightButtons();
                                             //   Toast.makeText(Introduction.this, "nr er"+ CurrentPlace, Toast.LENGTH_SHORT).show();

                                             super.onSwipeRight();
                                         }

                                         @Override
                                         public void onSwipeLeft() {

                                             if (CurrentPlace < 4) {
                                                 CurrentPlace++;
                                             }
                                             HighLightButtons();
                                             //    Toast.makeText(Introduction.this, "nr er"+ CurrentPlace, Toast.LENGTH_SHORT).show();


                                             super.onSwipeLeft();
                                         }
                                     }
        );
    }


    public void setStrings (int Id){

        if (Id == 1){

            TextboblinformationOne = "I would like you to find a green brick for me";
            TextboblinformationTwo = "What a cool brick you found";
            Headline = "Introduction";
            ButtonText = "Next";
            InfoTextOne = "Benny bot is an embodied conversational agent, who facilitates play, through speech and tasks. He provides request for both building and collecting";
            InfroTextTwo = "The child then picks up the bricks Benny requested for and feeds him the brick";
            InfoTextThree = "Benny then provides engaging humoroues feedback";
            InfoTextFour = "Before the play begin you need to choose which personality to play with. Each personality have different traits and requests";



        }

        if (Id == 2){


            TextboblinformationOne = "Hej hej mit navn er Benny og jeg er klar til at spise nogle klodser";
            TextboblinformationTwo = "O-M-G den var bare lækkert";
            Headline = "Introduktion";
            ButtonText = "Næste";
            InfoTextOne = "";
            InfroTextTwo = "";
            InfoTextThree ="";
            InfoTextFour = "";

        }

    }

    public void HighLightButtons(){

        if (CurrentPlace == 1){
            FourDotsView.setImageResource(R.drawable.intro1knap);
            talebobl.setImageResource(R.drawable.graatalebobl);
            try {
                lol(R.drawable.graatalebobl,talebobl);

            }
            catch (NullPointerException lol){

                //  Toast.makeText(this, "NullPointer", Toast.LENGTH_SHORT).show();   //Arrrrrrgh
            }
            textbobltext.setText(TextboblinformationOne);
            infotext.setText(InfoTextOne);
            haandview.setImageResource(0);
            findViewById(R.id.leftarrow).setAlpha(0);


        }
        if (CurrentPlace == 2){
            FourDotsView.setImageResource(R.drawable.intro2knap);
            talebobl.setImageResource(0);
            // haandview.setImageResource(R.drawable.haandintroduction);
            lol(R.drawable.haandintroduction, haandview);
            textbobltext.setText("");
            infotext.setText(InfroTextTwo);
            findViewById(R.id.leftarrow).setAlpha(1);

        }
        if (CurrentPlace == 3){
            FourDotsView.setImageResource(R.drawable.intro3knap);
            haandview.setImageResource(0);


            talebobl.setImageResource(R.drawable.graatalebobl);
            lol(R.drawable.graatalebobl, talebobl);


            textbobltext.setText(TextboblinformationTwo);
            RemoveButton();
            infotext.setText(InfoTextThree);
            findViewById(R.id.rightarrow).setAlpha(1);


        }
        if (CurrentPlace == 4){
            FourDotsView.setImageResource(R.drawable.intro4knap);
            talebobl.setImageResource(0);
            textbobltext.setText("");
            EnhanceGoPlayButton();
            infotext.setText(InfoTextFour);
            findViewById(R.id.rightarrow).setAlpha(0);

        }



    }


    public void lol (int src, ImageView view){


        view.setImageResource(src);
        // ObjectAnimator Fadeout = ObjectAnimator.ofFloat(talebobl, "alpha",  0f, .8f);
        //Fadeout.setDuration(1200);
        ObjectAnimator FadeIn = ObjectAnimator.ofFloat(view, "alpha", .4f, 1f);
        FadeIn.setDuration(200);
        //animatorSet.play(Fadeout).before(FadeIn);
        animatorSet.play(FadeIn);


        animatorSet.start();


    }


    public void EnhanceGoPlayButton(){

        NextButton.setImageResource(R.drawable.greennextbutton);


        if (!NextButton.isEnabled()) {
            NextButton.setEnabled(true);

            //ObjectAnimator GoplayButtonFadeOut = ObjectAnimator.ofFloat(NextButton, "alpha", .3f, .6f);
            //GoplayButtonFadeOut.setDuration(100);
            ObjectAnimator GoplaybuttonFadeIn = ObjectAnimator.ofFloat(NextButton, "alpha", .8f, 1f);
            GoplaybuttonFadeIn.setDuration(350);

            AnimatorSet animatorSet = new AnimatorSet();
            //animatorSet.play(GoplayButtonFadeOut).before(GoplaybuttonFadeIn);

            animatorSet.play(GoplaybuttonFadeIn);

            animatorSet.start();
        }
    }


    public void RemoveButton (){

        NextButton.setImageResource(R.drawable.gennemsigtigtnextknapintroduction);
        if (NextButton.isEnabled()) {
            NextButton.setEnabled(false);
            ObjectAnimator GoplayButtonFadeOut = ObjectAnimator.ofFloat(NextButton, "alpha", .6f, 1f);
            GoplayButtonFadeOut.setDuration(550);

            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.play(GoplayButtonFadeOut);

            animatorSet.start();

        }


    }

    public void MovingOnUpMovingOndDown(View view) {

        Intent i = new Intent(this, BennyMain.class);
        startActivity(i);
    }

    public void LeftArrowClick(View view) {

        if (CurrentPlace> 1 ) {
            CurrentPlace--;
        }
        HighLightButtons();
        //   Toast.makeText(Introduction.this, "nr er"+ CurrentPlace, Toast.LENGTH_SHORT).show();

    }

    public void RightArrowClick(View view) {

        if (CurrentPlace < 4) {
            CurrentPlace++;
        }
        HighLightButtons();




    }
}




